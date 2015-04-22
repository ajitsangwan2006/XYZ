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

package com.gisil.mcds.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gisil.mcds.web.filter.UISecurityFilter;
import com.gisil.mcds.web.util.SessionData;
import com.gisil.mcds.web.util.Utils;

/**
 * Abstract class for all jsp controllers
 * 
 * @author Ajit Singh
 * 
 */
public abstract class AbstractJspController {

	/** Return to page path */
	public String _returnToPagePath;

	public HttpServletRequest _request;

	public HttpServletResponse _response;

	public SessionData _sessionData;

	public String _contextPath = null;

	/**
	 * 
	 * @param aRequest
	 * @param aResponse
	 */
	public AbstractJspController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		_request = aRequest;
		_response = aResponse;

		_contextPath = aRequest.getContextPath();
		if (aRequest.getSession().getAttribute("sessionData") == null) {
			_sessionData = new SessionData();
			aRequest.getSession().setAttribute("sessionData", _sessionData);
		} else {
			_sessionData = (SessionData) aRequest.getSession().getAttribute(
					"sessionData");
		}
	}

	/**
	 * Set the return path
	 * 
	 * @return
	 */
	public String getReturnToPagePath() {
		return _returnToPagePath;
	}

	public String getDisplayStrForTerminal(String data) {
		if (data != null) {
			data = Utils.convertToDisplayString(data);
		} else {
			data = "";
		}

		return data;
	}

	/**
	 * isEditMode
	 * 
	 * @return
	 */
	public boolean isEditMode() {

		boolean isEditMode = false;
		HttpSession _session = _request.getSession();

		if (_session.getAttribute("editlevel") != null) {
			Integer configLevel = (Integer) _session.getAttribute("editlevel");
			if (getPrevelageLevel(_request) >= configLevel)
				isEditMode = true;
		}

		return isEditMode;
	}

	private Integer getPrevelageLevel(HttpServletRequest request) {
		Integer prevelageLevel = 0;
		HttpSession _session = request.getSession();
		try {
			if (request.getParameter(UISecurityFilter.PRIVILAGE_LEVEL) != null)
				prevelageLevel = Integer.parseInt(request
						.getParameter(UISecurityFilter.PRIVILAGE_LEVEL));
			else if (request.getAttribute(UISecurityFilter.PRIVILAGE_LEVEL) != null)
				prevelageLevel = Integer.parseInt(request.getAttribute(
						UISecurityFilter.PRIVILAGE_LEVEL).toString());
			else if (_session.getAttribute(UISecurityFilter.PRIVILAGE_LEVEL) != null)
				prevelageLevel = Integer.parseInt(request
						.getParameter(UISecurityFilter.PRIVILAGE_LEVEL));
		} catch (NumberFormatException exp) {
			prevelageLevel = 0;
		}
		return prevelageLevel;
	}
}
