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

package com.gisil.mcds.web.servlet.ui;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.SearchTrxRequest;

import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.web.controller.ui.SuiSearchTrxController;
import com.gisil.mcds.web.controller.ui.SuiSearchTrxResultController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.Utils;

/**
 * Services suiSearchTrx.jsp
 * Provides Transaction Details based on Search criteria 
 * @author amit kumar
 *
 */
public class SearchTransResultServlet extends MCDSServiceServlet {

	/**serial Version UID*/
	private static final long serialVersionUID = -2917947493320675361L;

	public static final String SERVLET_PATH = "GetTransSearchResult";
	
	private Boolean isAll; 
	
	private String queryString = "";

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
			{

		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		SearchTrxRequest srchTrxRequest = new SearchTrxRequest();
		
		isAll = Boolean.parseBoolean(request.getParameter("searchType"));
		RequestDispatcher requestDispatcher = null;
		List<Transaction> transactionList = null;
		boolean isTrue = true;
		try {
			MCDSRemote remote = getMCDSRemote(); 
			if(request.getParameter("archive") != null){
				srchTrxRequest.setIsArchive(true);
				queryString = queryString+"archive="+request.getParameter("archive")+"&";
			}
			else
				srchTrxRequest.setIsArchive(false);
			
			if (!isAll) 
			{
				queryString = queryString+"searchType="+request.getParameter("searchType")+"&";
				if (request.getParameter("delno") != null
						&& request.getParameter("delno") != "") {
					try{
						Long delNo = Long.parseLong(request.getParameter("delno"));
					srchTrxRequest.setDelNo(""+delNo);
					queryString = queryString+"delno="+srchTrxRequest.getDelNo()+"&";
					}catch(NumberFormatException n){
						request.setAttribute("message",ErrorMessages.NUMERIC_VALUE_EXP+ " in Del No");
						request.setAttribute("backUrl",SuiSearchTrxController.PAGE_PATH);
						isTrue = false;
					}
				}
				if (request.getParameter("trxid") != null
						&& request.getParameter("trxid") != "") {
					try{
						Long trxId = Long.parseLong(request.getParameter("trxid"));
						srchTrxRequest.setTrxId(""+trxId);
						queryString = queryString+"trxid="+srchTrxRequest.getTrxId()+"&";
						}catch(NumberFormatException n){
							request.setAttribute("message",ErrorMessages.NUMERIC_VALUE_EXP+" in Trx Id");
							request.setAttribute("backUrl",SuiSearchTrxController.PAGE_PATH);
							isTrue = false;
						}
				}
				if (! (request.getParameter("statusTrans").equals("Select"))) 
				{
					srchTrxRequest.setTrxStatus(request.getParameter("statusTrans"));
					queryString = queryString+"statusTrans="+srchTrxRequest.getTrxStatus()+"&";
				}
				if (!(request.getParameter("reconStatusTrans").equals("Select"))) 
				{
					if(Boolean.parseBoolean(request.getParameter("reconStatusTrans")))
						srchTrxRequest.setReconStatus("1");
					else
						srchTrxRequest.setReconStatus("0");
					queryString = queryString+"reconStatusTrans="+srchTrxRequest.getReconStatus()+"&";
				}
				if (request.getParameter("fromDate") != null
						&& request.getParameter("fromDate") != ""){
					if (request.getParameter("toDate") != null
							&& request.getParameter("toDate") != "") {
						try{
							Date fromDate = Utils.convertToDate(request.getParameter("fromDate"));
							Date toDate = Utils.convertToDate(request.getParameter("toDate"));
							queryString = queryString+"fromDate="+request.getParameter("fromDate")+"&"+"toDate="+request.getParameter("toDate")+"&";
							if(fromDate.before(toDate) || fromDate.compareTo(toDate)== 0)
							{
								srchTrxRequest.setFromDate(fromDate);
								srchTrxRequest.setToDate(toDate);
							}else{
								request.setAttribute("message","fromDate must be less then toDate");
								request.setAttribute("backUrl",SuiSearchTrxController.PAGE_PATH);
								isTrue = false;
							}
						}catch(Exception e){
							request.setAttribute("message",ErrorMessages.INVAILD_DATE);
							request.setAttribute("backUrl",SuiSearchTrxController.PAGE_PATH);
							isTrue = false;
						}
						
					}else{
						request.setAttribute("message",ErrorMessages.ENTER_TODATE);
						request.setAttribute("backUrl",SuiSearchTrxController.PAGE_PATH);
						isTrue = false;
					}
					
				}else if (request.getParameter("toDate") != null
						&& request.getParameter("toDate") != "") {
					request.setAttribute("message",ErrorMessages.ENTER_FROMDATE);
					request.setAttribute("backUrl",SuiSearchTrxController.PAGE_PATH);
					isTrue = false;
				}
			}else{
				//requestDispatcher = request.getRequestDispatcher(SuiSearchTrxResultController.PAGE_PATH);
				
			}
			request.setAttribute("queryString",queryString);
			if(isTrue){
				requestDispatcher = request.getRequestDispatcher(SuiSearchTrxResultController.PAGE_PATH);
			}else{
				requestDispatcher = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
			}
			transactionList = remote.searchAllTransaction(srchTrxRequest);
			auditTrail.setAdditionalInfo2("Search the transactions for advance search or simple search");
			submitAuditTrail(auditTrail);
			request.setAttribute("trxList", transactionList);
			requestDispatcher.include(request, response);

		} catch (MCDSException mcdsExp) {
			_log.info(mcdsExp.toString());

		}
	}

}
