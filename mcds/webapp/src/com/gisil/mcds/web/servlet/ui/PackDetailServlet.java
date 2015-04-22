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
import com.gisil.mcds.dmi.PackDetailForUI;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.ui.SuiContentsController;
import com.gisil.mcds.web.controller.ui.SuiPackDetailController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;

/**
 * Provides an Object for pack details for UI
 * Services suiPackDetail.jsp
 * @author
 *
 */
public class PackDetailServlet extends MCDSServiceServlet {

	

	/**serialVersionUID*/
	private static final long serialVersionUID = -5515002010048822617L;
	
	public static final String SERVLET_PATH = "PackDetailServlet";

	@Override
	protected void processIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		PackDetailForUI pack=null;
		
		try{
			MCDSRemote remote = getMCDSRemote();
			pack = remote.getPackDetail(Integer.parseInt(request.getParameter(SuiContentsController.R_CONTENT_ID)));
			auditTrail.setAdditionalInfo2("Got the pack details for contentid "+SuiContentsController.R_CONTENT_ID);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(SuiPackDetailController.PAGE_PATH)	;
			request.setAttribute("pack",pack);
			submitAuditTrail(auditTrail);
			requestDispatcher.include(request, response);
		} catch (MCDSException mcdsExp) {
			_log.info(mcdsExp.toString());

		}
		
	}

}
