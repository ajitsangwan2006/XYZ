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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.AggregatorInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.ui.SuiEditAggrInfoController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;

/**
 * Services suiAggrList.jsp
 * Provides an Object of AggregatorInfo
 * @author amit kumar
 *
 */

public class EditAggrServlet extends MCDSServiceServlet {

	
	public static final String SERVLET_PATH = "EditAggrServlet";
	
	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = -8203170211011827910L;

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(SuiEditAggrInfoController.PAGE_PATH);
		try{
			MCDSRemote _remote = getMCDSRemote();
		AggregatorInfo _aggrinfo = _remote.getAggregatorInfo(Integer.parseInt(request.getParameter("aggId")));
		auditTrail.setAdditionalInfo2("Get the aggregator info with id :"+request.getParameter("aggId"));
		submitAuditTrail(auditTrail);
		request.setAttribute("aggrInfo",_aggrinfo);
		dispatcher.include(request,response);
		}catch (MCDSException e) {
			_log.info("Cannot Load aggr Info :"+e);
			

		}
	}

}
