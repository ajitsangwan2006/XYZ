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
package com.gisil.mcds.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gisil.mcds.payment.PaymentProcessor.PaymentMode;
import com.gisil.mcds.transaction.DeliveryMode;
import com.gisil.mcds.transaction.TrxStatus;


public class CommonUtil {

    private CommonUtil() {
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String getTimeInHHMMSS(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return getTimeInHHMMSS(cal);

    }

    public static String getTimeInHHMMSS(Calendar time) {
        if (time == null)
            return "HH:MM";
        int h = time.get(Calendar.HOUR_OF_DAY);
        int m = time.get(Calendar.MINUTE);
        StringBuilder sb = new StringBuilder();
        if (h < 10)
            sb.append("0");
        sb.append(h).append(":");

        if (m < 10)
            sb.append("0");
        sb.append(m);

        // sb.append(":");
        // if (s < 10)
        // sb.append("0");
        // sb.append(s);

        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> cast(List<?> list) {
        return (List<T>) list;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> cast(Map<?, ?> map) {
        return (Map<K, V>) map;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 12; i++) {
            String time = (i < 10 ? "0" : "") + i + ":23 AM";
            System.out.println(time + " -- > " + getTimeInHHMM(time));
        }
        for (int i = 1; i <= 12; i++) {
            String time = (i < 10 ? "0" : "") + i + ":23 PM";
            System.out.println(time + " -- > " + getTimeInHHMM(time));
        }

    }

    public static Date toDate(Calendar calender) {
        return calender.getTime();
    }

    public static String toString(int... results) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(' ');
        for (int i : results)
            sb.append(i).append(',');
        sb.setCharAt(sb.length() - 1, ' ');
        sb.append(']');
        return sb.toString();
    }

    public static Timestamp toTimeStamp(Date date, String time) {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(true);
        boolean formatIs12 = time.endsWith("PM") || time.endsWith("AM");

        String[] hhmmss = time.split(":");
        if (hhmmss.length < 2)
            throw new IllegalArgumentException("invalid time");

        if (hhmmss.length == 2) {
            String[] newTime = new String[3];
            newTime[0] = hhmmss[0];
            String[] temp = hhmmss[1].split(" ");
            newTime[1] = temp[0];
            newTime[2] = "00";
            if (formatIs12)
                newTime[2] += " " + temp[1];

            hhmmss = newTime;
        }
        int hh = Integer.parseInt(hhmmss[0]);

        if (formatIs12 && time.endsWith("PM") && hh != 12)
            hh += 12;

        cal.set(Calendar.HOUR_OF_DAY, hh);

        cal.set(Calendar.MINUTE, Integer.parseInt(hhmmss[1]));

        if (hhmmss.length == 3) {
            String ss = hhmmss[2];
            if (formatIs12)
                ss = ss.split(" ")[0];

            cal.set(Calendar.SECOND, Integer.parseInt(ss));
        }
        cal.set(Calendar.MILLISECOND, 0);

        return new Timestamp(cal.getTimeInMillis());
    }

    public static Integer toInteger(Object val) {
        if (val == null)
            return null;
        if (val instanceof Number)
            return Integer.valueOf(((Number) val).intValue());

        return Integer.valueOf(val.toString().trim());
    }

    public static Double toDouble(Object val) {
        if (val == null)
            return null;
        if (val instanceof Number)
            return Double.valueOf(((Number) val).doubleValue());

        return Double.valueOf(val.toString());
    }

    
    public static boolean checkCurrenTimeInRange(String stTime, String endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int hh = cal.get(Calendar.HOUR_OF_DAY);
        int mm = cal.get(Calendar.MINUTE);

        int stHH = Integer.parseInt(stTime.substring(0, 2));
        int stMM = Integer.parseInt(stTime.substring(2, 4));

        int endHH = Integer.parseInt(endTime.substring(0, 2));
        int endMM = Integer.parseInt(endTime.substring(2, 4));

        if (stHH > endHH) {
            if (hh > stHH)
                return true;
            if (hh == stHH)
                return (mm >= stMM);
            if (hh < stHH) {
                if (hh < endHH)
                    return true;
                if (hh == endHH)
                    return (mm <= endMM);
                return false;
            }
        } else if (stHH < endHH) {
            if (hh == stHH)
                return (mm >= stMM);
            if (hh < stMM)
                return false;
            if (hh < endHH)
                return true;
            if (hh == endHH)
                return mm <= endHH;
        } else {
            if (hh != stHH)
                return false;

            if (stMM < endHH) {
                if (mm > stMM)
                    return mm <= endMM;
                return false;
            } else if (stMM > endMM) {
                return (mm <= stMM && mm >= endMM);
            }
        }
        return false;
    }

    public static String fixMobileNo(String delNo) {
        if (delNo == null || delNo.length() == 0)
            return null;
        if (delNo.length() == 10)
            delNo = "91" + delNo;
        if (delNo.charAt(0) == '+')
            delNo = delNo.substring(1);
        return delNo;
    }

    public static Date getYesterDayDate() {
        long cuur = System.currentTimeMillis();
        cuur -= (24 * 60 * 60 * 1000L);

        return new Date(cuur);
    }

    public static String getTimeInHHMM(String timeInAMPM) {
        String time = null;
        String[] arg = timeInAMPM.split(" ");
        if (arg.length >= 2) {
            if ("AM".equals(arg[1].trim()))
                time = arg[0].trim();
            else if ("PM".equals(arg[1].trim())) {
                String[] sub = arg[0].split(":");
                int hh = Integer.parseInt(sub[0].trim());
                time = (hh += (hh == 12 ? 0 : 12)) + ":" + (sub[1].trim());
            }
        }
        return time;
    }

    public static boolean isTimePassed(String timeHHMM) {
        Calendar cal = Calendar.getInstance();
        if (timeHHMM == null || timeHHMM.length() != 4)
            throw new IllegalArgumentException(
                    "time is not in valid format. Expected Format is [HHMM] (24 hour format)");
        int hh = Integer.parseInt(timeHHMM.substring(0, 2));
        int mm = Integer.parseInt(timeHHMM.substring(2, 4));

        int currH = cal.get(Calendar.HOUR_OF_DAY);
        int currMin = cal.get(Calendar.MINUTE);
        if (currH > hh)
            return true;
        if (currH == hh)
            return mm >= currMin;

        return false;
    }

    public static String getDateAsString(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }
    
    public static TrxStatus toTrxStatus(String dbLiternal) {
        TrxStatus trxStatus = null;
        for (TrxStatus status : TrxStatus.values()) {
            if (status.toDBLiteral().equals(dbLiternal)) {
                trxStatus = status;
                break;
            }
        }
        return trxStatus;
    }
    
    public static PaymentMode toPaymentMode(String dbLiternal) {
    	PaymentMode paymentMode = null;
        for (PaymentMode status : PaymentMode.values()) {
            if (status.toDbLiteral().equals(dbLiternal)) {
            	paymentMode = status;
                break;
            }
        }
        return paymentMode;
    }
    
    public static DeliveryMode toDeliveryMode(String dbLiternal) {
    	DeliveryMode deliveryMode = null;
        for (DeliveryMode status : DeliveryMode.values()) {
            if (status.toDbLiteral().equals(dbLiternal)) {
            	deliveryMode = status;
                break;
            }
        }
        return deliveryMode;
    }
    
   
}
