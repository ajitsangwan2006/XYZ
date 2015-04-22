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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * Services contentMenu.jsp
 * @author Ajit Singh
 *
 */
public class ContentMenuController extends AbstractJspController {

	public HashMap<String, String> _menuContentList = null;

	public static final String PAGE_PATH = "contentMenu.jsp";
	/**
	 * ContentMenuController
	 * @param aRequest
	 * @param aResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public ContentMenuController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		super(aRequest, aResponse);
		_sessionData.setMarchentId(_request.getParameter("marchantID"));
		_menuContentList = ( HashMap ) aRequest.getAttribute("contentList");
		_request.getSession().setAttribute("sessionData", _sessionData);
	}

}
