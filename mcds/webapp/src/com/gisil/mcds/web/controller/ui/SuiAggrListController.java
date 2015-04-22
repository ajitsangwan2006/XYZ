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

import com.gisil.mcds.dmi.AggregatorInfo;
import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.servlet.ui.AggrListServlet;
import com.gisil.mcds.web.servlet.ui.EditAggrServlet;

/**
 * Returns a list of aggregators
 * @author
 *
 */

public class SuiAggrListController extends AbstractJspController {
	
	public static final String PAGE_PATH = "suiAggrList.jsp";
	
	public static final String R_AGG_ID = "aggrId";
	
	private List agrInfoList = null;
	
	public SuiAggrListController(HttpServletRequest aRequest, HttpServletResponse aResponse)
	{
		super(aRequest,aResponse);		
		
		//get List from request
		agrInfoList= (List)_request.getAttribute("agrList");
	}

	/**
	 * @return Returns the agrInfoList.
	 */
	public List getAgrInfoList() {
		return agrInfoList;
	}
	
	public String getEditURL(AggregatorInfo info){
		String url = "";
		if(isEditMode()){
			url=_response.encodeURL(EditAggrServlet.SERVLET_PATH + "?aggId="+info.getId());
		}else{
			url =AggrListServlet.SERVLET_PATH;
		}
		
		return url;
	}

}
