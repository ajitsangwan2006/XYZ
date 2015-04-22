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
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.AggregatorInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.ui.SuiAggrListController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;

/**
 * Provides a List of Aggregators 
 * @author 
 *
 */
public class AggrListServlet extends MCDSServiceServlet {

	/**serial Version UID*/
	private static final long serialVersionUID = -8867228825103194123L;
	
	public static final String SERVLET_PATH = "AggrListServlet";
	
	@Override
	protected void processIt(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		List<AggregatorInfo> agrInfoList=null;
		
		try{
			MCDSRemote remote = getMCDSRemote();
			request.getSession().removeAttribute(SuiAggrListController.R_AGG_ID);
			agrInfoList=remote.loadAggregators();
			auditTrail.setAdditionalInfo2("Here get Aggrigator info");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(SuiAggrListController.PAGE_PATH)	;
			request.setAttribute("agrList",agrInfoList);
			requestDispatcher.include(request, response);
			submitAuditTrail(auditTrail);
		} catch (MCDSException mcdsExp) {
			_log.info(mcdsExp.toString());

		}

		
	}


}
