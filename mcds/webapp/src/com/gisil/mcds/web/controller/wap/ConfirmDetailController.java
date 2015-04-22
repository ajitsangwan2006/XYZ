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
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * Services confirmDetail.jsp
 * @author ajit singh
 * 
 */
public class ConfirmDetailController extends AbstractJspController {

	public String _contentId = null;

	public String _title = null;

	public String _code = null;

	public String _price = null;

	public String _model = null;

	public String _make = null;

	public String _trxId = null;
	
	public String _mobile = null;

	public int _retry = 0;
	
	public static final String PAGE_PATH = "confirmDetail.jsp";

	/**
	 * ConfirmDetailController
	 * 
	 * @param aRequest
	 * @param aResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public ConfirmDetailController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		super(aRequest, aResponse);
		Transaction trx = (Transaction) _request.getSession().getAttribute("trx");
		_contentId = _request.getParameter("contentId");
		_make = trx.getPhoneMake();
		_model = trx.getPhoneModel();
		_title = trx.getSkuName();
		_code = trx.getSku();
		_mobile = trx.getMsisdn();
		if(trx.getTotalAmt()==null)
			_price = ""+0.00;
		else
			_price = "" + trx.getTotalAmt();
		
		if (_request.getAttribute("retry") != null)
			_retry = Integer.parseInt((String) _request.getAttribute("retry"));
	}
}
