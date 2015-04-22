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
import com.gisil.mcds.transaction.TrxStatus;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

/**
 * Services suiSettleResult.jsp
 * Provides Transaction update status
 * @author
 *
 */
public class SettleFinalTrx extends MCDSServiceServlet {

	/**
	 * serialVersionUID = -8752806015886358422L
	 */
	private static final long serialVersionUID = -8752806015886358422L;
	public static final String SERVLET_PATH = "SettleFinalTrx"; //to be mapped into web.xml

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		auditTrail.setAdditionalInfo2( "Got Transaction ID " +request.getParameter("trxid") );
		auditTrail.setAdditionalInfo3("Got Status "+request.getParameter("status"));
		RequestDispatcher dispatcher = null;
		dispatcher = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
		try{
			int statusId = 0;
			
			MCDSRemote _remote = getMCDSRemote();
			if(request.getParameter("status").equals("revert"))
				statusId = _remote.updateStatus(Integer.parseInt(request.getParameter("trxid")), TrxStatus.ACCOUNTED, request.getParameter("description") );
			else if(request.getParameter("status").equals("settle"))
				statusId = _remote.updateStatus(Integer.parseInt(request.getParameter("trxid")), TrxStatus.SETTELED, request.getParameter("description") );
			auditTrail.setAdditionalInfo4("Update the status for transaction Id : "+request.getParameter("trxid")+request.getParameter("description"));
			_log.info("Status Id = "+statusId);
			if(statusId >= 1)
			{
				request.setAttribute("message",ErrorMessages.UPDATE_SUCCESS);
				request.setAttribute("trxId",request.getParameter("trxid"));
				request.setAttribute("backUrl","suiSettleTrans.jsp");
			}
			else{
				if(request.getParameter("status").equals("settle"))
					request.setAttribute("message",ErrorMessages.CAN_NOT_SETTLE);
				else
					request.setAttribute("message",ErrorMessages.CAN_NOT_REVERT);
				request.setAttribute("backUrl",MCDSSettleTrx.SERVLET_PATH);
			}
			submitAuditTrail( auditTrail );
			
			
		}catch(MCDSException exception){
			_log.info("MCDS Error: "+exception.toString());
			request.setAttribute("message",ErrorMessages.NOT_SUCCESSFULL);
			request.setAttribute("trxId",request.getParameter("trxid"));
			request.setAttribute("backUrl",MCDSSettleTrx.SERVLET_PATH);
		}
		dispatcher.include(request, response);

	}

}
