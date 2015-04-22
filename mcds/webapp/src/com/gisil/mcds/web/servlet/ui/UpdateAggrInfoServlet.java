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
import com.gisil.mcds.dmi.AggregatorInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

/**
 * Services suiEditAggrInfo.jsp
 * Update Aggregator's Details 
 * @author amit kumar
 *
 */

public class UpdateAggrInfoServlet extends MCDSServiceServlet 

{
	
	/**
	 * serialVersionUID
	 */
	
	private static final long serialVersionUID = -2709576266271323607L;
	public static final String SERVLET_PATH = "UpdateAggrInfoServlet";
	
	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		Boolean flag;
		AggregatorInfo aggrInfo=new AggregatorInfo();
		
		RequestDispatcher dispatcher = null;
		String check = "";
		try {
			MCDSRemote _remote=getMCDSRemote();
			if(request.getParameter("url")==null)		
				check = "";
			else
				check = request.getParameter("url");
			aggrInfo.setAggregatorName(request.getParameter("agrName"));
			aggrInfo.setWebURL(check);
			if(request.getParameter("description")==null)		
				check = "";
			else
				check = request.getParameter("description");
			aggrInfo.setDescription(check);
			if(request.getParameter("userName")==null)		
				check = "";
			else
				check = request.getParameter("userName");
			aggrInfo.setUser(check);
			if(request.getParameter("password")==null)		
				check = "";
			else
				check = request.getParameter("password");
			aggrInfo.setPassword(check);		
			aggrInfo.setId(Integer.parseInt(request.getParameter("aggId")));
			aggrInfo.setStatus((String)(request.getParameter("status")));
			flag=_remote.updateAggrInfo(aggrInfo);
			if(flag) {
				
				request.setAttribute("aggId",request.getParameter("aggId"));
				request.setAttribute("message","Update Successfull");
				request.setAttribute("backUrl",AggrListServlet.SERVLET_PATH);
				dispatcher = request.getRequestDispatcher("suiMessage.jsp");
				auditTrail.setAdditionalInfo2("Aggregator info updated successfully");
			}
			else{
				request.setAttribute("aggId",request.getParameter("aggId"));
				request.setAttribute("message","Unable to Update Aggregators Data");
				request.setAttribute("backUrl",EditAggrServlet.SERVLET_PATH);
				dispatcher = request.getRequestDispatcher("suiMessage.jsp");
				auditTrail.setAdditionalInfo2("Aggregator info update unsuccessfull");
			}
		} catch(MCDSException e)
		{
		_log.info("Unable to update Aggregators Data"+e);
		request.setAttribute("aggId",request.getParameter("aggId"));
		request.setAttribute("message",e.getMessage());
		request.setAttribute("backUrl",EditAggrServlet.SERVLET_PATH);
		dispatcher = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
		}
		submitAuditTrail(auditTrail);
		dispatcher.include(request,response);
}
}
