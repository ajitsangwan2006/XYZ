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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.dmi.AggregatorInfo;
import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * Services suiEditAggrInfo
 * @author amit kumar
 * 
 */
public class SuiEditAggrInfoController extends AbstractJspController {

	public static final String PAGE_PATH = "suiEditAggrInfo.jsp";

	private AggregatorInfo _aggrInfo = null;

	private Integer id;

	private String aggregatorName;

	private String webURL;

	private String description;

	private String user;

	private String password;

	private String status;

	public SuiEditAggrInfoController(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);

		setData();
	}


	/**
	 *  Set all aggregator data
	 */
	private void setData() {

		if (_request.getAttribute("aggrInfo") != null) {
			_aggrInfo = (AggregatorInfo) _request.getAttribute("aggrInfo");

			setAggregatorName(_aggrInfo.getAggregatorName());

			setDescription(_aggrInfo.getDescription());

			setPassword(_aggrInfo.getPassword());

			setUser(_aggrInfo.getUser());

			setWebURL(_aggrInfo.getWebURL());
			
			setStatus(_aggrInfo.getStatus());
		}

	}

	/**
	 * 
	 * @param aggregatorName
	 */
	public void setAggregatorName(String aggregatorName) {
		this.aggregatorName = aggregatorName;
	}

	/**
	 * 
	 * @return aggregatorName
	 */
	public String getAggregatorName() {
		return aggregatorName;
	}

	/**
	 * 
	 * @return description
	 */
	public String getDescription() {
			return description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 
	 * @return webURL
	 */
	public String getWebURL() {
		return webURL;
	}

	/**
	 * 
	 * @param webURL
	 */
	public void setWebURL(String webURL) {
		this.webURL = webURL;
	}

	/**
	 * 
	 * @return Status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getEditMode(){
		if(isEditMode() ){
			return "";
		}else{
			return "disabled";
		}
	}

}
