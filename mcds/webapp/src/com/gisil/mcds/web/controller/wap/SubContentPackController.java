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
import com.gisil.mcds.aggregator.mauj.entity.ContentDetailTO;
import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.servlet.wap.ContentPackServlet;

/**
 * Services subContentPackController.java
 * @author
 *
 */
public class SubContentPackController extends AbstractJspController {

	private ArrayList<ContentDetailTO> _contents = null;
	
	public static final String PAGE_PATH = "subContentPack.jsp";
	
	private String _type = null;
	
	private String _backURL = null;
	
	private String _title;
	
	public SubContentPackController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		super(aRequest, aResponse);
		// It shows the current categories to be displayed
		setContents();
		_type = aRequest.getParameter("type");
		// It is used to genrate the back URL
		if(_sessionData.getEntryPoint().equals("search")){
			_backURL = ContentSearchController.PAGE_PATH;
			_title = "Search Result";
			}else if(_sessionData.getEntryPoint().equals("pack")){
			_backURL = ContentPackServlet.SERVLET_PATH;
			_title = "Content Pack";
			}
	}

	/**
	 * This function find and return the subcontent packs id and subcontent
	 * packs names, which we are going to catch in subContentPack.jsp
	 */
	@SuppressWarnings("unchecked")
	public void setContents() throws ServletException,
			IOException {
		_contents = (ArrayList)_request.getSession().getAttribute("contents");

	}

	/**
	 * @return Returns the _backURL.
	 */
	public String getBackURL() {
		return _backURL;
	}

	/**
	 * @return Returns the _contents.
	 */
	public ArrayList<ContentDetailTO> getContents() {
		return _contents;
	}

	/**
	 * @return Returns the _type.
	 */
	public String getType() {
		return _type;
	}

	public int getMaxRecords(){
		return _sessionData.getMaxRecordsPerPage();
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return _title;
	}
}
