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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.transaction.TrxStatus;
import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.util.ValidationException;

/**
 * Services suiSettleResult.jsp 
 * @author amit sachan
 * 
 */
public class SuiSettleResultController extends AbstractJspController {
	
	public static final String PAGE_PATH = "suiSettleResult.jsp";

	private Transaction trx = null;

	/**
	 * @return Returns the Transaction
	 */
	public Transaction getTrx() {
		return trx;
	}

	/**
	 * @param trx
	 *            The trx to set.
	 */
	public void setTrx(Transaction trx) {
		this.trx = trx;
	}

	public SuiSettleResultController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws MCDSException {
		super(aRequest, aResponse);

		trx = (Transaction) _request.getAttribute("transaction");
		if (trx == null) {
			throw new ValidationException("No Transaction specified");
		}
		
	}
	
	public String getEditMode(){
		String res = "";
		if(isEditMode()){
			//Have access to settle or revert
			if((TrxStatus.SERVICE_EX.equals(trx.getTrxStatus()) || TrxStatus.SERVICE_EX_RETRY.equals(trx.getTrxStatus())) && !trx.isRefunded()){
				res = "";
			}else{
				res = "disabled";
			}
		}else{
			res = "disabled";
		}
		return res;
	}

}
