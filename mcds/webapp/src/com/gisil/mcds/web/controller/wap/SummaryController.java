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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.util.SessionData;

/**
 * Services summary.jsp
 * @author Ajit Singh
 * 
 */
public class SummaryController extends AbstractJspController {
	public String _error = null;

	public SessionData _sessionData = null;

	/**
	 * SummaryController
	 * 
	 * @param aRequest
	 * @param aResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public SummaryController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		super(aRequest, aResponse);
		_request = aRequest;
		_response = aResponse;
		_sessionData = (SessionData) _request.getSession().getAttribute(
				"sessionData");
		_error = _sessionData.getError();
		_sessionData.setError(null);
		_request.getSession().setAttribute("sessionData", _sessionData);
	}

}
