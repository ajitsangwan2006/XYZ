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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.servlet.wap.MCDSMenuServlet;

/**
 * Controller Class for contentPack.jsp
 * @author Ajit Singh
 * 
 */
public class ContentPackController extends AbstractJspController {

	public static final String PAGE_PATH = "contentPack.jsp";
	
	private ArrayList _list;
	

	/**
	 * ContentPackController
	 * 
	 * @param aRequest
	 * @param aResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public ContentPackController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		super(aRequest, aResponse);
		// It shows the current categories to be displayed
		_list = (ArrayList) _request.getSession().getAttribute("contents");

	}
	
	public String getBackURL(){
		if(_sessionData.isCategorySearch())
			return ContentSearchController.PAGE_PATH;
		else
			return MCDSMenuServlet.SERVLET_PATH;
	}

	public int getMaxRecords(){
		return _sessionData.getMaxRecordsPerPage();
	}
	
	/**
	 * @return Returns the list.
	 */
	public ArrayList getList() {
		return _list;
	}
	
	public String getTitle(){
		if(_sessionData.isCategorySearch())
			return "Search Result";
		else
			return "Content Packs";
	}

}
