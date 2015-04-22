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
package com.gisil.mcds.web.servlet.ui;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.Configuration;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.ui.ConfigurationManagementController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

/**
 * Services suiEditConfiguration.jsp
 * Services suiConfMgtResult.jsp 
 * Provides an Configuration arraylist 
 *
 * @author 
 */


public class ConfigurationManagementServlet extends MCDSServiceServlet {
	
	/**
	 *  serial Version UID
	 */
		
	private static final long serialVersionUID = 3402103226495660041L;

	public static final String SERVLET_PATH = "configMgtServlet";
	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		String action = request.getParameter("action");
		RequestDispatcher dispatcher = request
				.getRequestDispatcher(ConfigurationManagementController.PAGE_PATH);

		if (action != null && action.equals("edit")) {
			String rowId = request.getParameter("rowid");
			int id = Integer.parseInt(rowId);
			String paramValue = request.getParameter("paramvalue");
			setNewParamValue(id, paramValue, request);
			dispatcher.include(request, response);
		}

		else if (action != null && action.equals("Undo")) {
			unDoChanges(request);
			submitAuditTrail(auditTrail);
			dispatcher.include(request, response);
		} else if (action != null && action.equals("Apply")) {
			// Do DB Insert for all Row ID's having isChanged is true.
			
			try {
				MCDSRemote remote = getMCDSRemote();
				ArrayList<Configuration> configurationDataList = (ArrayList<Configuration>) request.getSession().getAttribute("list");
				ArrayList<Configuration> updatedConfigurationDataList = remote.updateConfigurationData(configurationDataList);
				request.getSession().removeAttribute("list");
				request.getSession().setAttribute("list",updatedConfigurationDataList);
				auditTrail.setAdditionalInfo2("Completed the update of "+configurationDataList);
				submitAuditTrail(auditTrail);
				dispatcher.include(request, response);
			} catch (MCDSException e) {
				_log.info("Error Occured" + e.getMessage());
				RequestDispatcher requestDispatcher = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
				request.setAttribute( "message", e.getMessage() );
				request.setAttribute( "backUrl", response.encodeURL( SERVLET_PATH ) );
				requestDispatcher.include( request, response );
			}
		} else 
		{
			MCDSRemote remote = getMCDSRemote();
			ArrayList<Configuration> configurationDataList = null;
			try {
				configurationDataList = remote.loadConfigurationData();
				request.getSession()
						.setAttribute("list", configurationDataList);
				auditTrail.setAdditionalInfo2("Load the configuration data");
				submitAuditTrail(auditTrail);
				dispatcher.include(request, response);
			} catch (MCDSException mcdsExp) {
				_log.info("Exception " + mcdsExp.getMessage());
				RequestDispatcher requestDispatcher = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
				request.setAttribute( "message", mcdsExp.getMessage() );
				request.setAttribute( "backUrl", response.encodeURL( SERVLET_PATH ) );
				requestDispatcher.include( request, response );
			}

		}
	}
	/**
	 *  This method set the paramValue 
	 * @param rowId
	 * @param paramValue
	 * @param request
	 */
	private void setNewParamValue(int rowId, String paramValue,
			HttpServletRequest request) {
		ArrayList<Configuration> configurationDataList = (ArrayList<Configuration>) request
				.getSession().getAttribute("list");
		Iterator iterator = configurationDataList.iterator();

		while (iterator.hasNext()) {
			Configuration cfg = (Configuration) iterator.next();

			if (cfg.getRowId() == rowId) {
				cfg.setParamNewValue(paramValue);
				cfg.setChanged(true);
				break;
			}

		}

	}
	/**
	 * This method flush the value of paramnewvalue attribute
	 *  and set the isChanged false. 
	 * @param request
	 */

	private void unDoChanges(HttpServletRequest request) {

		ArrayList<Configuration> configurationDataList = (ArrayList<Configuration>) request
				.getSession().getAttribute("list");
		Iterator iterator = configurationDataList.iterator();

		while (iterator.hasNext()) {
			Configuration cfg = (Configuration) iterator.next();
			cfg.setParamNewValue(null);
			cfg.setChanged(false);
		}

	}

}
