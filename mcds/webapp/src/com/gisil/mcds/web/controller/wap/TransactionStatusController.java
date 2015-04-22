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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.dmi.PurchaseTrxResponse;
import com.gisil.mcds.transaction.TrxStatus;
import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.util.Utils;

/**
 * 
 * Services transactionStatus.jsp
 * @author 
 *
 */
public class TransactionStatusController extends AbstractJspController {

	public static final String PAGE_PATH = "transactionStatus.jsp";
	
	public Integer _trxId = null;

	public Integer _terminalId = null;

	public String _date = null;

	public TrxStatus _status = null;

	public String _statusToDisplay = null;

	public String _content = null;

	public String _skuCode = null;

	public double _price = 0.0;

	public String _cmsName = null;

	public double _cmsValue = 0.0;

	public TransactionStatusController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		PurchaseTrxResponse purchaseRes = (PurchaseTrxResponse) aRequest
				.getAttribute("trxResponse");
		_terminalId = purchaseRes.getTerminalId();
		_trxId = purchaseRes.getTrxId();
		Date date = purchaseRes.getDate();
		_date = getFormatedDate(date);
		_status = purchaseRes.getStatus();
		_statusToDisplay = getStatusToBeDisplay(_status);
		_skuCode = purchaseRes.getSkuCode();
		_price = purchaseRes.getPrice();
		_price = Double.parseDouble( Utils.decimalFormat( _price ) );
		_content = purchaseRes.getContent();
		if ( _content != null )
		{
			int index = _content.indexOf( "-" );
			if ( index != -1 )
			{
				_content = _content.substring( index + 1 ); 
			}
		}
		_cmsName = purchaseRes.getCmsName();
		_cmsValue = purchaseRes.getCmsValue();

	}
	
	

	/**
	 * Return Status to be displayed on screen
	 * 
	 * @param aStatus
	 * @return
	 */
	private String getStatusToBeDisplay(TrxStatus aStatus) {
		String status = "";

		if (aStatus != null) {
			if (aStatus.equals(TrxStatus.BUYING)) {
				status = "Delivered";
			} else if (aStatus.equals(TrxStatus.BUYING_DENIED)) {
				status = "Denied on Mauj";
			} else {
				status = "Service Error";
			}
		} else {
			status = "Service Error";
		}

		return status;
	}

	/**
	 * Convert Date type into String
	 * 
	 * @param aDate
	 * @return
	 */
	private String getFormatedDate(Date aDate) {
		Format formatter = new SimpleDateFormat("dd-MM-yy    HH:mm:ss");
		String date = formatter.format(aDate);
		return date;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean isCMSCharge() {
		if (this._cmsValue > 0.0) {
			return true;

		} else {
			return false;
		}
	}
}
