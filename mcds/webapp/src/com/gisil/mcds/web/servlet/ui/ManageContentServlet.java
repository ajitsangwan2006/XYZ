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
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.ContentDetailsforUI;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.ui.SuiAggrListController;
import com.gisil.mcds.web.controller.ui.SuiContentsController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;

/*
 * Services suiEditContentInfo.jsp
 * Services suiMainContentMenu.jsp
 * Services suiPackDetail.jsp
 * Provides a List of Content Details for UI
 */

public class ManageContentServlet extends MCDSServiceServlet {
	/**
	 * serialVersionUID = -3191766346470642123L
	 * SERVLET_PATH = "MCDSServiceServlet"
	 */
	private static final long serialVersionUID = -3191766346470642123L;
	
	public static final String SERVLET_PATH = "ManageContentServlet";

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		
		try
		{
			MCDSRemote _remote = getMCDSRemote();
			 Integer id = null;			
			 Integer aggrId = null;
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(SuiContentsController.PAGE_PATH);
			if(request.getParameter(SuiContentsController.R_CONTENT_ID) != null && request.getParameter(SuiContentsController.R_CONTENT_ID) != ""){
				id = Integer.parseInt(request.getParameter(SuiContentsController.R_CONTENT_ID));
			}
			else if(request.getParameter(SuiAggrListController.R_AGG_ID) != null && request.getParameter(SuiAggrListController.R_AGG_ID) != ""){
					request.getSession().setAttribute(SuiAggrListController.R_AGG_ID,request.getParameter(SuiAggrListController.R_AGG_ID));
				aggrId = Integer.parseInt(request.getParameter(SuiAggrListController.R_AGG_ID));
			}else{
				aggrId = Integer.parseInt((String)request.getSession().getAttribute(SuiAggrListController.R_AGG_ID));
			}
			ArrayList<ContentDetailsforUI> contentsDetail = _remote.getContents(id, aggrId);
			auditTrail.setAdditionalInfo2("Get content details for id = "+id+" and aggrId = "+aggrId);
			submitAuditTrail(auditTrail);
			request.setAttribute( "contentList", contentsDetail );
			requestDispatcher.include( request, response );			
		}
		catch( MCDSException mcdsExp )
		{
			_log.info( "MCDS Exception " + mcdsExp.getMessage() );
			goToErrorPage(request, response, mcdsExp.getMessage(), SERVLET_PATH );
		}

	}

}
