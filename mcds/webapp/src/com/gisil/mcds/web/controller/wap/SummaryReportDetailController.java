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

package com.gisil.mcds.web.controller.wap;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.dmi.TransactionSummaryInfo;
import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.util.SessionData;
import com.gisil.mcds.web.util.Utils;

/**
 * Services summaryReportDetail.jsp
 * @author
 *
 */
public class SummaryReportDetailController extends AbstractJspController {
	private String _reportType = "";

	final int MAX_TRANSACTION_LEN = 4;
	
	public static final String PAGE_PATH = "summaryReportDetail.jsp";

	private ArrayList<TransactionSummaryInfo> _transactionList = null;

	private ArrayList<String> _formatedTransactionListForPrinter = null;

	private ArrayList<String> _formatedTransactionListToDisplay = null;

	private int maxRecordLimit = 0;
	
	public SummaryReportDetailController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) 
	{
		super(aRequest, aResponse);

		_sessionData = (SessionData) aRequest.getSession().getAttribute(
				"sessionData");

		if (_sessionData == null) {
			_sessionData = new SessionData();
		}
		
		maxRecordLimit = _sessionData.getMaxRecordsPerPage();
		
		String reportType = (String) aRequest.getAttribute("summaryReportType");

		if (reportType != null) {
			_sessionData.setEntryPoint(reportType);
			setReportType(reportType);
		} else {
			// Get from Session
			reportType = _sessionData.getEntryPoint();
			setReportType(reportType);
		}

		ArrayList<TransactionSummaryInfo> transactionList = (ArrayList) aRequest
				.getAttribute("transactionsDetail");

		if (transactionList != null) {
			// ArrayList<String> list =
			// getFormatedTransactionList(transactionList);
			// ArrayList<String> listToDisplay =
			// getFormatedTransactionListToDisplay(transactionList);
			// _sessionData.setTrxSummaryList(list);
			_sessionData.setTrxSummaryList(transactionList);
			setTransactionList(transactionList);
			aRequest.getSession().setAttribute("sessionData", _sessionData);
		} else {
			// Get Trx list from Session
			_sessionData = (SessionData) aRequest.getSession().getAttribute(
					"sessionData");
			setTransactionList(_sessionData.getTrxSummaryList());
		}
	}

	public int getMaxContentDisplayLimit()
	{
		return maxRecordLimit;
	}
	private void setTransactionList(ArrayList<TransactionSummaryInfo> list) {
		_transactionList = list;
	}

	public ArrayList<TransactionSummaryInfo> getTransactionList() {
		return _transactionList;
	}

	public void setReportType(String reportType) {
		_reportType = reportType;
	}

	public String getReportType() {
		return _reportType;
	}

	public ArrayList<String> getFormatedTransactionListForPrinter() {
		ArrayList<TransactionSummaryInfo> transactionList = getTransactionList();
		ArrayList<String> list = new ArrayList<String>();

		if (transactionList != null && !transactionList.isEmpty()) {
			Iterator iterator = transactionList.iterator();
			int count = 1;
			while (iterator.hasNext()) {

				String info = "";
				TransactionSummaryInfo trans = (TransactionSummaryInfo) iterator
						.next();
				long id = trans.getTransactionId();
				String transId = new Long(id).toString();
				transId = Utils.paddingString(transId, 6, ' ', true);
				String transDate = trans.getTransactionDate();
				transDate = Utils.paddingString(transDate, 8, ' ', true);
				String status = trans.getTransactionResponseCode();
				String statusToBeDisplay = getTransactionStatusToBeDisplay(status);
				statusToBeDisplay = Utils.paddingString(statusToBeDisplay, 3,
						' ', true);

				if (count < 10) {
					info = count + " " + transId + " " + transDate + " "
							+ statusToBeDisplay;
				} else {
					info = count + "" + transId + " " + transDate + " "
							+ statusToBeDisplay;
				}
				list.add(info);
				count++;
			}
		} else {
			String message = "No Record Found";
			list.add(message);
		}

		return list;
	}

	public ArrayList<String> getFormatedTransactionListToDisplay() {
		ArrayList<TransactionSummaryInfo> transactionList = getTransactionList();
		ArrayList<String> list = new ArrayList<String>();
		if (transactionList != null && !transactionList.isEmpty()) {
			Iterator iterator = transactionList.iterator();
			int count = 1;
			while (iterator.hasNext()) {
				String info = "";
				TransactionSummaryInfo trans = (TransactionSummaryInfo) iterator
						.next();
				long id = trans.getTransactionId();
				String transId = new Long(id).toString();
				transId = Utils.paddingString(transId, 6, ' ', true);
				String transDate = trans.getTransactionDate();
				transDate = Utils.paddingString(transDate, 8, ' ', true);
				String status = trans.getTransactionResponseCode();
				String statusToBeDisplay = getTransactionStatusToBeDisplay(status);
				statusToBeDisplay = Utils.paddingString(statusToBeDisplay, 3,
						' ', true);

				if (count < 10) {
					info = count + " " + transId + "    " + transDate + "   "
							+ statusToBeDisplay;
				} else {
					info = count + "" + transId + "   " + transDate + "   "
							+ statusToBeDisplay;
				}
				list.add(info);
				count++;
			}

		} else {
			String message = "No Record Found";
			list.add(message);
		}

		return list;
	}
	
	private String getTransactionStatusToBeDisplay(String aStatus) {
		String tStatus = "";

		if (aStatus != null) {
			try {
				int status = Integer.parseInt(aStatus);

				if (status == 0) {
					tStatus = "S";
				} else if (status >= 1 && status <= 18) {
					tStatus = "F";
				} else {
					tStatus = "U";
				}
			} catch (NumberFormatException nfe) {
				tStatus = "U";
			}

		} else {
			tStatus = "U";
		}

		return tStatus;
	}

	public String getCurrentDate() {
		Date date = new Date();
		Format formatter = new SimpleDateFormat("dd-MM-yy    HH:mm:ss");
		String currentDate = formatter.format(date);
		return currentDate;
	}
	
	
}
