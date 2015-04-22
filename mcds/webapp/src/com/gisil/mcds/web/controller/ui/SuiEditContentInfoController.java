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

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.dmi.ContentInfo;
import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.util.AllDeliveryMode;
/**
 * Services suiEditContentInfo.jsp
 * @author ajit singh
 *
 */
public class SuiEditContentInfoController extends AbstractJspController {
	private ArrayList _status = null;

	private String _contentName = null;

	private String _description = null;

	private ArrayList _deliveryMode = null;

	private Integer _aggregatorId = null;
	
	private Map _aggMap = null;
	
	private String _backUrl = null;
	
	public static final String PAGE_PATH = "suiEditContentInfo.jsp"; 

	public SuiEditContentInfoController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		ContentInfo contentInfo = (ContentInfo) aRequest
				.getAttribute("contentInfo");
		setAggregatorId(contentInfo.getAggregatorId());
		setContentName(contentInfo.getContentName());
		setDeliveryMode(contentInfo.getDeliveryMode());
		setDescription(contentInfo.getDescription());
		setStatus(contentInfo.getStatus());
		setAggMap(contentInfo.getAggregatorList());
		setBackUrl((String)aRequest.getAttribute("backUrl"));
		System.out.println("Back url is : "+getBackUrl());
	}

	public Integer getAggregatorId() {
		return _aggregatorId;
	}

	public void setAggregatorId(Integer aAggregatorId) {
		this._aggregatorId = aAggregatorId;
	}

	public String getContentName() {
		return _contentName;
	}

	public void setContentName(String aContentName) {
		if(aContentName == null)
			this._contentName = "";
		else
			this._contentName = aContentName;
	}

	public ArrayList getDeliveryMode() {
		return _deliveryMode;
	}


	public void setDeliveryMode(String aDeliveryMode) {
		int loop = 0;
		ArrayList<String> list = new ArrayList<String>();
		list.add(0,"");
		for (AllDeliveryMode mode : AllDeliveryMode.values()){ 
		     if(aDeliveryMode != null)
		    	 if(aDeliveryMode.equals(mode.toString()))
		     	    	 list.add(0,mode.toString());
		    	 else
		    		 list.add(loop+1,mode.toString());
		     else
		    	 list.add(loop+1,mode.toString());
		     loop++;
		}
		this._deliveryMode = list;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		if(description == null)
			this._description = "";
		else
			this._description = description;
	}

	public ArrayList getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		ArrayList<String> list = new ArrayList<String>();
		if(status.equals("ENABLED"))
		{
			list.add("ENABLED");
			list.add("DISABLED");
		}else{
			list.add("DISABLED");
			list.add("ENABLED");
		}
		this._status = list;
	}

	public Map getAggMap() {
		return _aggMap;
	}

	public void setAggMap(Map aAggMap) {
		this._aggMap = aAggMap;
	}
	
	public String getEditMode(){
		String res = "";
		if(isEditMode()){
			res = "";
		}else{
			res = "disabled";
		}
		return res;
	}

	public String getBackUrl() {
		return _backUrl;
	}

	public void setBackUrl(String aUrl) {
		_backUrl = aUrl;
	}

}
