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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * Services summaryReportMenu.jsp
 * @author Ashok Kumar
 * 
 */
public class SummaryReportMenuController extends AbstractJspController {
	private int _limit = 0;

	/**
	 * SummaryReportMenuController
	 * 
	 * @param aRequest
	 * @param aResponse
	 */
	public SummaryReportMenuController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		Integer limit = (Integer) aRequest.getAttribute("limit");

		if (limit != null) {
			int maxTransLimit = limit.intValue();
			setLimit(maxTransLimit);
		}
	}

	/**
	 * Set the limit
	 * 
	 * @param limit
	 */
	public void setLimit(int limit) {
		_limit = limit;
	}

	/**
	 * Get the limit
	 * 
	 * @return
	 */
	public int getLimit() {
		return _limit;
	}

}
