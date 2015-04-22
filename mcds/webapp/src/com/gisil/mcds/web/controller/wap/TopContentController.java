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
import com.gisil.mcds.web.servlet.wap.TopContentServlet;

/**
 * Services topContent.jsp
 * @author
 *
 */
public class TopContentController extends AbstractJspController {
	public static final String JSP_PATH = "topContent.jsp";
	private ArrayList _contents = null;
	private String _backUrl = null;
	private String _parentId = null;
	public TopContentController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		super(aRequest, aResponse);
		setContents((ArrayList)_request.getSession().getAttribute("contents"));
		setBackUrl(TopContentServlet.SERVLET_PATH);
		if(_request.getSession().getAttribute("parentId") != null)
			setParentId((String)_request.getSession().getAttribute("parentId"));
	}
	public ArrayList getContents() {
		return _contents;
	}
	public void setContents(ArrayList aContents) {
		this._contents = aContents;
	}
	public String getBackUrl() {
		return _backUrl;
	}
	public void setBackUrl(String aBackUrl) {
		_backUrl = aBackUrl;
	}
	public String getParentId() {
		return _parentId;
	}
	public void setParentId(String aParentId) {
		_parentId = aParentId;
	}

}
