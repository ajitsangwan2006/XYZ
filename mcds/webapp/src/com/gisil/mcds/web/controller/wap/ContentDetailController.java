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

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gisil.mcds.aggregator.mauj.entity.ContentsTO;
import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.servlet.wap.SubContentPackServlet;

/**
 * Services contentDetail.jsp
 * @author
 *
 */
public class ContentDetailController extends AbstractJspController {

	public List _contentId = null;

	public String _categoryId = null;

	public String _contentCode = null;

	public String _backId = null;

	public List _codeList = null;

	public List _nameList = null;

	public boolean _isEmptyContentName = true;

	public boolean _isEmptyContent = true;

	public String _currentTitle = null;

	public String _backTitle = null;

	public String _entryPoint = null;

	public String _searchBy = null;

	public String _contentInfo = null;

	public String _backType = null;
	
	public String _loopUrl = null;
	
	public static final String PAGE_PATH ="contentDetail.jsp";

	public ContentDetailController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		super(aRequest, aResponse);
		_request = aRequest;
		_response = aResponse;

		_contentInfo = _request.getParameter("contentInfo");
		if (_contentInfo == null) {
			_contentInfo = _sessionData.getContentInfo();
		} else {
			_sessionData.setContentInfo(_contentInfo);
		}
		_entryPoint = _sessionData.getEntryPoint();
		
		_currentTitle = (String)aRequest.getSession().getAttribute("contentName");
		
		if (_entryPoint.equals("search")) {
			_returnToPagePath = SubContentPackServlet.SERVLET_PATH;
			_searchBy = _sessionData.getSearchBy();
			_loopUrl = PAGE_PATH;
			_contentInfo = _sessionData.getContentInfo();
			_categoryId = (String) aRequest.getSession().getAttribute("contentId");
			getContents();
		}else if (_entryPoint.equals("pack")) {
			_returnToPagePath = SubContentPackServlet.SERVLET_PATH;
			_categoryId = (String) aRequest.getSession().getAttribute("contentId");
			_loopUrl = PAGE_PATH;
			getContents();
			getBackType();

		}

		_request.getSession().setAttribute("sessionData", _sessionData);

	}

	
	/**
	 * This function find and return the contents id and content names, which we
	 * are going to catch in categoryDetail.jsp
	 */
	@SuppressWarnings("unchecked")
	public void getContents() throws ServletException, IOException {

		ContentsTO contents = null;
		_contentId = new ArrayList();
		_codeList = new ArrayList();
		_nameList = new ArrayList();
		
		List contentList = (ArrayList)_request.getSession().getAttribute("contentsList");
		
		Iterator itr = contentList.iterator();
		
		
		while(itr.hasNext())
		{
			contents = (ContentsTO)itr.next();
			_contentId.add(contents.getContentId());
			_codeList.add(contents.getContentCode());
			_nameList.add(contents.getContentName());
		}
			_isEmptyContentName =  (Boolean)_request.getSession().getAttribute("isEmptyContentName");
			_isEmptyContent = (Boolean)_request.getSession().getAttribute("isEmptyContent");
		

	}

	
	/**
	 * This function find and return the type of subPack, which we are using for 
	 * back page path subPack genration
	 */
	public void getBackType() {

			_backType = (String)_request.getSession().getAttribute("backType");

	}
	
	public int getMaxRecords(){
		return _sessionData.getMaxRecordsPerPage();
	}

}
