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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.servlet.wap.SubContentPackServlet;

/**
 * Services contentSearch.jsp 
 * @author Ajit Singh
 *
 */
public class ContentSearchController extends AbstractJspController {
	public final static String PAGE_PATH = "contentSearch.jsp";

	public String _title = null;

	public String _type = null;

	public String _displayString = null;

	public String _searchBy = null;

	public String _fieldType = null;
	
	public String _url = SubContentPackServlet.SERVLET_PATH;

	/**
	 * ContentSearchController
	 * @param aRequest
	 * @param aResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public ContentSearchController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		super(aRequest, aResponse);

		_returnToPagePath = "searchBy.jsp";
		_searchBy = _request.getParameter("searchBy");
		if (_searchBy == null) {
			_searchBy = _sessionData.getSearchBy();
		} else {
			_sessionData.setSearchBy(_searchBy);
			
		}
		if (_searchBy.equals("title")) {
			_title = "Content Name Search";
			_type = "Title";
			_displayString = "Character of title";
			_fieldType = "text";
			_sessionData.setCategorySearch(false);

		} else if (_searchBy.equals("code")) {
			_title = "Content Code Search";
			_type = "Code";
			_displayString = "digit of the code";
			_fieldType = "number";
			_sessionData.setCategorySearch(false);
			
		}else if(_searchBy.equals("category")){
			_title = "Category Search";
			_type = "Category";
			_displayString = "Chars of category";
			
			_sessionData.setCategorySearch(true);
		}
		_request.getSession().setAttribute("sessionData", _sessionData);

	}

}
