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

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.ContentInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

/**
 * Provides info about Content Updation  
 * @author 
 *
 */
public class UpdateContentInfoServlet extends MCDSServiceServlet {

	/**
	 * serial Version UID = 7191215860111203941L
	 */
	private static final long serialVersionUID = 7191215860111203941L;
	public static final String SERVLET_PATH = "UpdateContentInfoServlet";

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		ContentInfo _contentInfo = new ContentInfo();
		_contentInfo.setAggregatorId(Integer.parseInt(request.getParameter("aggId")));
		_contentInfo.setContentName(request.getParameter("contentName"));
		_contentInfo.setDeliveryMode(request.getParameter("deliveryMode"));
		_contentInfo.setDescription(request.getParameter("description"));
		_contentInfo.setStatus(request.getParameter("status"));
		_contentInfo.setContentId(Integer.parseInt(request.getParameter("contentId")));
		
		try{
			MCDSRemote _remote = getMCDSRemote();
			Boolean flag = _remote.updateContentInfo(_contentInfo);
			if(flag){
				request.setAttribute("message",ErrorMessages.SUCCESSFULL);
				request.setAttribute("backUrl",SuiUpdateContentInfo.SERVLET_PATH);
				request.setAttribute("contentId",request.getParameter("contentId"));
				request.setAttribute("queryString",request.getParameter("queryString"));
				dispatcher = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
			}else{
				request.setAttribute("message",ErrorMessages.NOT_SUCCESSFULL);
				request.setAttribute("backUrl",SuiUpdateContentInfo.SERVLET_PATH);
				request.setAttribute("contentId",request.getParameter("contentId"));
				request.setAttribute("queryString",request.getParameter("queryString"));
				dispatcher = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
			}
		}catch (MCDSException mcds) {
			request.setAttribute("message",mcds.getMessage());
			request.setAttribute("backUrl",SuiUpdateContentInfo.SERVLET_PATH);
			request.setAttribute("contentId",request.getParameter("contentId"));
			request.setAttribute("queryString",request.getParameter("queryString"));
			dispatcher = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
		}
		dispatcher.include(request,response);
		
	}

}
