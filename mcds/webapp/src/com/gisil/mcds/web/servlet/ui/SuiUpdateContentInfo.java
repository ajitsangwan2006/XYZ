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
import com.gisil.mcds.dmi.ContentInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.ui.SuiEditContentInfoController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

/**
 * Services suiEditContentInfo.jsp
 * Services suiContents.jsp
 * Provides an Object of Content Info 
 * @author 
 */

public class SuiUpdateContentInfo extends MCDSServiceServlet {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 3136043007508016581L;

	public static final String SERVLET_PATH = "SuiUpdateContentInfo";

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String contentId = request.getParameter("contentId");
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		
		RequestDispatcher dispatcher = null;
		try {
			MCDSRemote _remote = getMCDSRemote();
			if (request.getParameter("update") != null) {
				if (request.getParameter("update").equals("true")) {
					ContentInfo _contentInfo = new ContentInfo();
					_contentInfo.setAggregatorId(Integer.parseInt(request
							.getParameter("aggId")));
					_contentInfo.setContentName(request
							.getParameter("contentName"));
					_contentInfo.setDeliveryMode(request
							.getParameter("deliveryMode"));
					_contentInfo.setDescription(request
							.getParameter("description"));
					_contentInfo.setStatus(request.getParameter("status"));
					_contentInfo.setContentId(Integer.parseInt(request
							.getParameter("contentId")));
					Boolean flag = _remote.updateContentInfo(_contentInfo);
					if (flag) {
						auditTrail.setAdditionalInfo2("content information Successfully Updated ");
						request.setAttribute("message",	request.getParameter("contentName")+ " updated successfully");
					} else {
						request.setAttribute("message",
								ErrorMessages.NOT_SUCCESSFULL);
						auditTrail.setAdditionalInfo2("content information not updated ");

					}

				}
				request.setAttribute("backUrl", ManageContentServlet.SERVLET_PATH+"?"+request.getQueryString());
				dispatcher = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
			}else{
			ContentInfo contentInfo = _remote.getContentInfo(Long
					.parseLong(contentId));
			auditTrail.setAdditionalInfo3("content information get with content id = "+contentId);
			
			request.setAttribute("contentInfo", contentInfo);
			submitAuditTrail(auditTrail);
			request.setAttribute("backUrl", ManageContentServlet.SERVLET_PATH+"?"+request.getQueryString());
			dispatcher = request.getRequestDispatcher(SuiEditContentInfoController.PAGE_PATH);
			}
		} catch (MCDSException e) {
			e.printStackTrace();
			request.setAttribute("message",ErrorMessages.UNDEFINED_ERROR);
			
			request.setAttribute("backUrl", ManageContentServlet.SERVLET_PATH);
			dispatcher = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
		}
		dispatcher.include(request, response);

	}

}
