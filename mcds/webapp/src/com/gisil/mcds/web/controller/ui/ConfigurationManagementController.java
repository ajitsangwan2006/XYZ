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
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.config.Configuration;
import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * Services suiConfMgtResult.jsp
 * @author amit sachan
 * 
 */
public class ConfigurationManagementController extends AbstractJspController {
	
	private ArrayList<Configuration> _configurationDataList = null;

	public static final String PAGE_PATH = "suiConfMgtResult.jsp";

	public ConfigurationManagementController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		_configurationDataList = (ArrayList) aRequest.getSession()
				.getAttribute("list");
	}

	/**
	 * 
	 * @return Returns isModified
	 */
	public boolean isModified() {
		boolean isModified = false;

		Iterator iterator = _configurationDataList.iterator();

		while (iterator.hasNext()) {
			Configuration cfg = (Configuration) iterator.next();

			if (cfg.isChanged()) {
				isModified = true;
				break;
			}
		}

		return isModified;
	}

	public ArrayList<Configuration> getConfigurationDataList() {
		return _configurationDataList;
	}

	/**
	 * Return the String on the basis of mode.
	 * 
	 * @return
	 */
	public String getEditMode(){
		if(isEditMode()){
			return "";
		}
		return "disabled";
	}

}
