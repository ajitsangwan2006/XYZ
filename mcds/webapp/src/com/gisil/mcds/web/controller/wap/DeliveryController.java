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
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * Services delivery.jsp
 * @author Ajit Singh
 *
 */
public class DeliveryController extends AbstractJspController {

	public static final String PAGE_PATH = "delivery.jsp";
	
	public String _contentId = null;

	public Transaction _trx = null;

	public String _retry = null;
	
	public String _model = null;

	public String _make = null;

	public String _mobile = null;
	
	public ArrayList _deliveryArr = null;

	/**
	 * DeliveryController
	 * @param aRequest
	 * @param aResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public DeliveryController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		super(aRequest, aResponse);

		_contentId = aRequest.getParameter("contentId");
		//_trx = (Transaction)aRequest.getParameter("trx");
		_retry = aRequest.getParameter("retry");
		_make = aRequest.getParameter("make");
		_model = aRequest.getParameter("model");
		_mobile = aRequest.getParameter("mobile");
		_deliveryArr = (ArrayList)aRequest.getAttribute("deliveryMode");

		_request.getSession().setAttribute("trx",_request.getAttribute("trx"));

	}

}
