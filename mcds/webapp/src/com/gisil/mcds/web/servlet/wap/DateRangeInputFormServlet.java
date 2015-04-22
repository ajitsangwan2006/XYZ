package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.dmi.TransactionSummaryInfo;
import com.gisil.mcds.ejb.MCDSRemote;

import com.gisil.mcds.util.CommonUtil;
import com.gisil.mcds.web.controller.wap.SummaryReportDetailController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.SessionData;

public class DateRangeInputFormServlet extends MCDSServiceServlet {

	/**
	 * serialVersionUID = 8986630012066137815L SERVLET_PATH =
	 * "rangeInputFormServlet
	 */
	private static final long serialVersionUID = 8986630012066137815L;

	public static final String SERVLET_PATH = "rangeInputFormServlet"; // to be
																		// mapped
																		// into
																		// web.xml

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher reqDispatcher = null;
		String errorMessage = "";
		final String backPageUrl = "dateRangeInputForm.jsp";

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		if (fromDate == null || fromDate.equals("") || toDate == null
				|| toDate.equals("")) {
			goToErrorPage(request, response, ErrorMessages.INSUFFICIENT_INPUT,
					backPageUrl);
		} else {

			try {
				Date fromDt = getDateObj(fromDate);
				Date toDt = getDateObj(toDate);
				MCDSRemote mcdsRemote = getMCDSRemote();
				int historyAvlDays = mcdsRemote.getTransactionHistoryAvlDays();
				boolean flag = isFromDateLessThanToDate(fromDate, toDate);
				boolean flag1 = isTrxAvlInlive(fromDt, historyAvlDays);
				boolean flag2 = isToDateLessThanCurrentDate(toDt);
				boolean isError = false;

				if (flag && flag1 && flag2) {
					String delNo = CommonUtil
							.fixMobileNo(getMerchantDelNo(request));
					ArrayList<TransactionSummaryInfo> transactionDetailList = mcdsRemote
							.getTransactionDetailBetweenDate(fromDt, toDt, Long
									.parseLong(delNo));
					reqDispatcher = request
							.getRequestDispatcher(SummaryReportDetailController.PAGE_PATH);

					SessionData _sessionData = (SessionData) request
							.getSession().getAttribute("sessionData");
					if (_sessionData == null) {
						_sessionData = new SessionData();
					}
					if (_sessionData.getMaxRecordsPerPage() == 0) {
						_sessionData
								.setMaxRecordsPerPage(mcdsRemote
										.getConfigNumber(
												MCDSConfig.MAX_CONTENT_DISPLAY)
										.intValue());
					}

					request.setAttribute("summaryReportType", "date");
					request.setAttribute("transactionsDetail",
							transactionDetailList);
					reqDispatcher.forward(request, response);
				} else if (!flag) {
					isError = true;
					errorMessage = errorMessage
							+ "FromDate Must be Less than ToDate";
				} else if (!flag1) {
					isError = true;
					errorMessage = errorMessage
							+ "Invalid FromDate : FromDate Must be Greater than Today's Date - "
							+ historyAvlDays + " days.";
				} else if (!flag2) {
					isError = true;
					errorMessage = errorMessage
							+ "Invalid ToDate : ToDate must be less than Today's Date";
				}

				if (isError) {
					goToErrorPage(request, response, errorMessage, backPageUrl);
					_log.info("Invalid Date : " + errorMessage);
				}

			} catch (MCDSException mcdsExp) {
				errorMessage = ErrorMessages.INVELID_DATE;
				goToErrorPage(request, response, errorMessage, backPageUrl);
				_log.info("Invalid Date : " + mcdsExp.toString());
			}
		}
	}

	/**
	 * Check if date range come under active days of transactions
	 * 
	 * @param fromDate
	 * @param days
	 * @return
	 */
	private boolean isTrxAvlInlive(Date fromDate, int days) {
		boolean flag = false;
		Calendar calender = new GregorianCalendar();
		calender.add(Calendar.DATE, -(days));
		Date earlierDate = calender.getTime();
		int result = fromDate.compareTo(earlierDate);

		if (result > 0) {
			flag = true;
		}

		return flag;

	}

	/**
	 * Check wether toDt < fromDt or not
	 * 
	 * @param toDate
	 * @return
	 */
	private boolean isToDateLessThanCurrentDate(Date toDate) {
		boolean flag = false;
		Date currentDate = new Date();
		int result = currentDate.compareTo(toDate);

		if (result >= 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * Check where fromDt < toDt or not
	 * 
	 * @param fromDt
	 * @param toDt
	 * @return
	 * @throws MCDSException
	 */
	private boolean isFromDateLessThanToDate(String fromDt, String toDt)
			throws MCDSException {
		boolean flag = false;
		Date fromDate = getDateObj(fromDt);
		Date toDate = getDateObj(toDt);
		int result = fromDate.compareTo(toDate);

		if (result <= 0) {
			flag = true;
		}

		return flag;
	}

	private Date getDateObj(String aDate) throws MCDSException {
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		try {
			dateFormat.setLenient(false);
			date = dateFormat.parse(aDate);
		} catch (ParseException parseExp) {
			throw new MCDSException(parseExp.toString());
		}

		return date;
	}

}
