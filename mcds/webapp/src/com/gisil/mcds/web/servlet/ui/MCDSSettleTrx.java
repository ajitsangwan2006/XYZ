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
import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

/**
 * Services suiSettleTrans.jsp
 * Provides Transaction Status
 * @author   
 *
 */
public class MCDSSettleTrx extends MCDSServiceServlet {

	/**
	 * serialVersionUID = 8620224692407609234L;
	 */
	private static final long serialVersionUID = 8620224692407609234L;
	public static final String SERVLET_PATH = "MCDSSettleTrx"; //to be mapped into web.xml
	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		RequestDispatcher dispatcher = request.getRequestDispatcher("suiSettleResult.jsp");
		try{
			MCDSRemote _remote = getMCDSRemote();
			Transaction _transaction = _remote.getLocalTransactionStatus(Integer.parseInt(request.getParameter("trxid")));
			if(_transaction == null)
			{
				request.setAttribute("message", ErrorMessages.INVALID_TRX_ID);
				request.setAttribute("backUrl", "suiSettleTrans.jsp");
				dispatcher = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
			}
			auditTrail.setAdditionalInfo2( "Got Transaction ID " +request.getParameter("trxid") );
			request.setAttribute("transaction",_transaction);
			request.setAttribute("prevelageLevel",getPrevelageLevel(request));
			submitAuditTrail( auditTrail );
			
		}catch(MCDSException exception){
			_log.info("MCDS Error: "+exception.toString());
			request.setAttribute("message", exception.getMessage());
			request.setAttribute("backUrl", "suiSettleTrans.jsp");
			dispatcher = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
		}
		dispatcher.include(request, response);
		

	}

}
