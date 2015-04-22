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

package com.gisil.mcds.web.controller.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.dmi.ContentDetailsforUI;
import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.servlet.ui.AggrListServlet;
import com.gisil.mcds.web.servlet.ui.ManageContentServlet;
import com.gisil.mcds.web.servlet.ui.PackDetailServlet;
import com.gisil.mcds.web.servlet.ui.SuiUpdateContentInfo;
import com.gisil.mcds.web.util.Utils;

/**
 * Controller Class for Contents
 * Services suiContents.jsp
 * @author amit sachan
 * 
 */
public class SuiContentsController extends AbstractJspController {

	public static final String PAGE_PATH = "suiContents.jsp";

	public static final String R_CONTENT_ID = "ctId";

	private List list = null;
	
	private String queryString = null;

	public SuiContentsController(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);

		if (_request.getAttribute("contentList") != null) {
			list = (List) _request.getAttribute("contentList");
		}
		setQueryString(request.getQueryString());
	}

	/**
	 * @return Returns the list.
	 */
	public List getList() {
		return list;
	}

	public String getLinkURL(ContentDetailsforUI detail) {
		String url = "";
		if ("PACK".equals(detail.getType())) {
			url = PackDetailServlet.SERVLET_PATH + "?" + R_CONTENT_ID + "="
					+ detail.getContentId();
		} else if (detail.isParent()) {
			url = ManageContentServlet.SERVLET_PATH + "?" + R_CONTENT_ID + "="
					+ detail.getContentId();
		} else {
			// will go to item details servlet
		}

		return url;
	}

	public String getBackURL() {
		String url = "";
		if (getList().isEmpty()) {
			url = AggrListServlet.SERVLET_PATH;
		} else {
			ContentDetailsforUI detailsforUI = (ContentDetailsforUI) getList()
					.get(0);
			if (detailsforUI.getParentId() == null) {
				url = AggrListServlet.SERVLET_PATH;
			} else if(detailsforUI.getParentOfParentId() == null){
				url = ManageContentServlet.SERVLET_PATH ;
			}else{
				url = ManageContentServlet.SERVLET_PATH + "?" + R_CONTENT_ID
				+ "=" + detailsforUI.getParentOfParentId();
			}
		}
		return url;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	public String getEditURL(ContentDetailsforUI detail){
		String url ="";
		if(isEditMode()){
			url=SuiUpdateContentInfo.SERVLET_PATH+"?"+Utils.getSubQueryString(getQueryString())+"&contentId="+detail.getContentId();
		}else{
			url = ManageContentServlet.SERVLET_PATH + "?" + getQueryString();
		}
		return url;
	}
	
	/**
	 * Disable top 10 contents
	 * 
	 */
	
	public String getLinkOnTopTen(ContentDetailsforUI info){
		if(info.getContentId() == 1){
			return ManageContentServlet.SERVLET_PATH;			
		}else{
			return getLinkURL(info);
		}
	}
	
}
