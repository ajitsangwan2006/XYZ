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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.aggregator.mauj.entity.AbstractTransTrackerResponse;
import com.gisil.mcds.audit.AuditTrail;
//import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.TransactionDetail;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.web.controller.ui.SuiViewTransStatusController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;


/**
 * Servlet implementation class for Servlet: GetTransactionStatus
 * Services suiViewTransStatus.jsp 
 * Provides transaction details by returning a  TransactionDetail object
 *
 */
 public class TransactionStatusServlet extends  MCDSServiceServlet
 {
    /** serialVersionUID */
	private static final long serialVersionUID = 3455305291497834694L;
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public static final String SERVLET_PATH = "GetTransactionStatus";
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	@Override
	protected void processIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String transactionId = request.getParameter( "trxid" );
		Integer trxId = Integer.parseInt( transactionId );

		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());

		
		

		try
			{
				MCDSRemote _remote = getMCDSRemote();
				// get remote Transaction Status
				AbstractTransTrackerResponse trackerResponse = null;
  		        // Get Local Transaction Status	
				Transaction localTrx = null; 
				TransactionDetail transDetail = _remote.getTransactionStatus( trxId );
				auditTrail.setAdditionalInfo2("Got transaction details with trx id = "+trxId);
				localTrx = transDetail.getTransaction();
				trackerResponse = transDetail.getTransTrackerResponse();
				RequestDispatcher requestDispatcher = request.getRequestDispatcher( "suiViewTransDetail.jsp" );
     			request.setAttribute( "trxResponse", trackerResponse );
				request.setAttribute( "localTrx", localTrx );
			submitAuditTrail(auditTrail);
				requestDispatcher.include( request, response );
			}
			catch( MCDSException mcdsExp )
			{
				_log.info( mcdsExp.toString() );
				RequestDispatcher requestDispatcher = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
				String errorMessage = mcdsExp.toString();
				if ( errorMessage != null && errorMessage.toLowerCase().indexOf( "invalid trx") != -1 )
				{
					errorMessage = ErrorMessages.NO_TRX_EXIST;
				}
				request.setAttribute( "message", errorMessage );
				request.setAttribute( "backUrl", SuiViewTransStatusController.PAGE_PATH  );
				requestDispatcher.include( request, response );
			}

	}   	  	    
}