/*
 * Copyright, GISIL 2006 All rights reserved. The copyright in this work is
 * vested in GISIL and the information contained herein is confidential. This
 * work (either in whole or in part) must not be modified, reproduced, disclosed
 * or disseminated to others or used for purposes other than that for which it
 * is supplied, without the prior written permission of GISIL. If this work or
 * any part hereof is furnished to a third party by virtue of a contract with
 * that party, use of this work by such party shall be governed by the express
 * contractual terms between the GISIL which is a party to that contract and the
 * said party.
 */
package com.gisil.mcds.web.util;

import java.net.URLEncoder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;

import java.util.Date;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author ajit singh
 *
 */
public class Utils {

	// default country code - would be added to numbers that are not in
	// international format
	public static String DEFAULT_COUNTRY_CODE = null;

	public static int TERMINAL_DISPLAY_MAX_LEN = 0;

	public static int TERMINAL_DISPLAY_LABEL_MAX_LEN = 0;

	public static int TERMINAL_ROWS_LIMIT = 0;

	public static int TITLE_LIMIT = 0;

	public static int CODE_LIMIT = 0;

	/**
	 * convert to content display string
	 * @param aTitle
	 * @param aCode
	 * @return
	 */
	public static String convertToContentString(String aTitle, String aCode) {
		String completeTitle = null;
		String returnString = null;
		if (aTitle.length() > TITLE_LIMIT) {
			completeTitle = new String(aTitle.substring(0, TITLE_LIMIT)
					+ " ");

		} else {
			completeTitle = new String(aTitle);
			for (int i = aTitle.length() - 1; i < TITLE_LIMIT; i++)
				completeTitle = completeTitle+" ";
		}
		returnString = completeTitle + aCode;
		return returnString;

	}

	/**
	 * Is mobile no correct
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isMobileNumber(String phoneNumber) throws Exception{
		
			if (!isInInternationalFormat(phoneNumber))
				phoneNumber = convertToInternationalFormat(phoneNumber);

			// In India all mobile numbers start with "9".
			if (phoneNumber.startsWith("+91"))
				return true;
			else
				return false;
		
	}

	/**
	 * Is number in international format
	 * @param number
	 * @return
	 */
	public static boolean isInInternationalFormat(String number) {
		return number.startsWith("+");
	}

	/**
	 * Analyzes the suplied number and brings it in international format. Based
	 * on information gathered from the following web sites o
	 * http://en.wikipedia.org/wiki/Area_code#India o
	 * http://www.immihelp.com/nri/numberscheme.html o
	 * http://en.wikipedia.org/wiki/India_Cellphone_Numbering
	 * 
	 * @param number
	 * @return
	 * @throws HermesException
	 */

	public static String convertToInternationalFormat(String number)
			throws Exception {
		/*
		 * Implelementation Note:- taken from Hermis#commonUtils
		 */

		// number can't be null
		if (number == null)
			throw new Exception("Mobile Number is null");

		// trim the number of any white space
		number = number.trim();

		// number size cant be less than 10
		if (number.length() < 10)
			throw new Exception("Mobile Number must be 10 numeric digits.");

		StringBuffer buffer = new StringBuffer("+");
		String countryCode = DEFAULT_COUNTRY_CODE;

		if (number.startsWith("+")) {
			number = number.substring(1); // strip off the + sign from the
			// number
			countryCode = number.substring(0, 2); // get the country code
			number = number.substring(2); // strip off the country code

		} else {
			if (number.length() >= 12) {
				countryCode = number.substring(0, 2); // get the country code
				number = number.substring(2); // strip off the country code
			} else {
				countryCode = DEFAULT_COUNTRY_CODE;
			}
		}

		if (!countryCode.equalsIgnoreCase(DEFAULT_COUNTRY_CODE))
			throw new Exception("Mobile Number must be 10 numeric digits.");

		try {
			Long.parseLong(number);
		} catch (NumberFormatException nfe) {
			throw new NumberFormatException(
					"Mobile Number must be 10 numeric digits.");
		}

		buffer.append(countryCode);
		buffer.append(number);
		if (buffer.length() > 13)
			throw new Exception("Mobile Number must be 10 numeric digits.");
		return buffer.toString();
	}

	/**
	 * convert to date
	 * @param ddMMyy
	 * @param log
	 * @return
	 */
	public static Date convertToDate(String ddMMyy)throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
		format.setLenient(false);
		Date date = null;
		date = format.parse(ddMMyy);
		return date;
	}

	/**
	 * Get date in ddmmyy format
	 * @param dt
	 * @return
	 */
	public static String formatDate(Date dt) {
		if (dt == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
		return format.format(dt);
	}

	/**
	 * simple date format
	 * @param dt
	 * @param f
	 * @return
	 */
	public static String formatDate(Date dt, String f) {
		if (dt == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(f);
		return format.format(dt);
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> cast(Map<?, ?> map) {
		return (Map<K, V>) map;
	}

	@SuppressWarnings("unchecked")
	public static <E> List<E> cast(List<?> list) {
		return (List<E>) list;
	}

	public static boolean isNotInteger(String val) {
		boolean flag = false;
		try {
			Integer.valueOf(val);

		} catch (Throwable e) {
			flag = true;
		}
		return flag;
	}

	// public static String getTrxInfoRowBgColor(TransactionInfo info, String
	// defaultColor) {
	// String toRet = defaultColor;
	// //
	// return toRet;
	// }

	public static String validateSeatInput(String input, String[] choices,
			int noOfPass) {
		String msg = null;
		if (input == null || input.length() == 0) {
			msg = "Please choose seats.";
		} else {
			String[] inputs = input.split(",");
			if (inputs.length != noOfPass)
				msg = "Please Choose seats equal to Passengers Traveling.";
			else {
				for (int i = 0; i < inputs.length; i++) {
					String seat = inputs[i].trim();
					int j = 0;
					for (; j < choices.length; j++) {
						if (seat.equals(choices[j].trim()))
							break;
					}
					if (j == choices.length) {
						msg = "Seat '" + seat + "' is not a valid choice.";
						break;
					}
				}
			}
		}

		return msg;
	}

	public static boolean isPreviousDate(Date date) {
		boolean flag = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar today = Calendar.getInstance();
		if (cal.get(Calendar.YEAR) <= today.get(Calendar.YEAR)) {
			if (cal.get(Calendar.MONTH) <= today.get(Calendar.MONTH)) {
				if (cal.get(Calendar.DATE) < today.get(Calendar.DATE)) {
					return true;
				}
			}
		}
		return flag;
	}

	public static String encode(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (Throwable e) {
			return url;
		}
	}

	public static String decimalFormat(double no) {
		DecimalFormat df = new DecimalFormat("######.##");
		String val = df.format(no);
		int index = val.indexOf(".");
		if(index == -1)
			val = val+".00";
		else{
			if(val.substring(index+1,val.length()).length()-1 == 0)
				val = val+"0";
		}
		return val;
	}
	
	public static String generateCommissionTableContent(
			Map<Integer, Number> trxComm) {
		StringBuilder sb = new StringBuilder();

		sb
				.append("<table bgcolor=\"#EBEBDA\" width=\"200\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		sb.append("<tr>");
		sb.append("<th width=\"50%\">Partner Id</th>");
		sb.append("<th width=\"50%\">Commission</th>");
		sb.append("</tr>");
		if (trxComm != null) {
			for (Map.Entry<Integer, Number> pair : trxComm.entrySet()) {
				sb.append("<tr>");
				sb.append("<td align=\"left\">");
				sb.append(pair.getKey());
				sb.append("</td>");
				sb.append("<td align=\"left\">");
				sb.append(decimalFormat(pair.getValue() == null ? 0.00 : pair
						.getValue().doubleValue()));
				sb.append("</td>");

				sb.append("</tr>");
			}
		}
		sb.append("<tr>");
		sb.append("<td colspan=\"2\" align=\"center\">");
		sb
				.append("<input name=\"close\" type=\"button\" value=\"Close\" onClick=\"hide();\">");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");

		return sb.toString();

	}

	/**
	 * **** Returns the String after converting to Terminal Display Format
	 */
	public static String convertToDisplayString(String inputString) {

		StringBuffer inputStringBuffer = new StringBuffer(inputString);
		int size = inputStringBuffer.length();
		int checkpoint = TERMINAL_DISPLAY_MAX_LEN;
		int counter = checkpoint - 1;

		while (size > counter) {
			if (inputStringBuffer.charAt(counter) == ' ') {
				while (inputStringBuffer.charAt(counter) == ' ') {
					inputStringBuffer.deleteCharAt(counter);
					size--;
				}
				inputStringBuffer.insert(counter, "<br/>");
				counter = (counter - 1) + 5 + checkpoint; // Five characters
				// for "<br/>",
				size += 5;
			} else {
				int flag = 0;
				int i = counter;
				while (inputStringBuffer.charAt(i) != ' ') {
					i--;
					if (i <= counter - checkpoint) {
						flag = 1;
						break;
					}
				}
				if (flag != 1) {
					inputStringBuffer.insert(i + 1, "<br/>");
					counter = i + 5 + checkpoint; // Five characters for
					// "<br/>",
					size += 5;
				} else {
					// word at the end needs to be hyphenated
					inputStringBuffer.insert(counter, "-<br/>");
					counter = (counter - 1) + 6 + checkpoint; // Six
					// characters
					// for "-<br/>",
					size += 6;
				}
			}
		}
		// m_logger.info("String converted for terminal display->
		// "+inputStringBuffer.toString());
		return inputStringBuffer.toString();

	}

	public static List<String> breakLine(String inString) {
		String string = inString;
		int start = 0;
		int limit = 24;
		int total = string.length();
		int end = Math.min(total, limit);
		List<String> list = new ArrayList<String>();
		while (start < end) {
			String subString = string.substring(start, end);
			list.add(subString);
			start = end;
			end = start + Math.min(total, limit);
			end = Math.min(total, end);
		}
		return list;
	}

	public static String stringFormater(String string, int cap) {
		String str = "";
		if (string != null) {
			if (string.length() > cap) {
				str = string.substring(0, cap);
			} else {
				str = string;
			}
		} else {
			str = "";
		}
		return str;
	}

	public static String paddingString(String s, int n, char c,
			boolean paddingLeft) {
		StringBuffer str = new StringBuffer(s);
		int strLength = str.length();
		if (n > 0 && n > strLength) {
			for (int i = 0; i <= n; i++) {
				if (paddingLeft) {
					if (i < n - strLength)
						str.insert(0, c);
				} else {
					if (i > strLength)
						str.append(c);
				}
			}
		}
		return str.toString();
	}

	public static String getDateOption(Calendar date) {
		StringBuilder sb = new StringBuilder();
		int day = date == null ? Calendar.getInstance().get(Calendar.DATE)
				: date.get(Calendar.DATE);
		sb.append("" + day);
		return sb.toString();
	}
	
	public static String getSubQueryString(String aStr){
		if(aStr != null)
			if(aStr.indexOf("&contentId") != -1 )
				aStr = aStr.substring(0,aStr.indexOf("&contentId")-1);
		
		return aStr;
	}
	
 
	public static String maskNull(Object val) {
		return val == null ? "" : val.toString();
	}
}
