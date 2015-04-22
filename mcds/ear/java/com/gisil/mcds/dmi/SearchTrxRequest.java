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
package com.gisil.mcds.dmi;

import java.io.Serializable;
import java.util.Date;

import com.gisil.mcds.base.MCDSException;

/**
 * Class Represents Search Transaction Object
 * @author amit sachan
 *
 */
public class SearchTrxRequest implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -404889219051273829L;

	private Boolean _IsArchive = false;

	private String _DelNo = null;

	private String _TrxId = null;
	
	
	private String _TrxStatus = null;

	private Date _FromDate = null;

	private Date _ToDate = null;

	private String _ReconStatus = null;

	/**
	 * @return Returns the _DelNo.
	 */
	public String getDelNo() {
		return _DelNo;
	}

	/**
	 * @param delNo The _DelNo to set.
	 */
	public void setDelNo(String delNo) throws MCDSException {
		if(delNo != null){
		_DelNo = delNo;
		}
		else{
			throw new MCDSException("Invalid Merchant Phone");
		}
	}

	/**
	 * @return Returns the _FromDate.
	 */
	public Date getFromDate() {
		return _FromDate;
	}

	/**
	 * @param fromDate The _FromDate to set.
	 */
	public void setFromDate(Date fromDate) {
		_FromDate = fromDate;
	}

	/**
	 * @return Returns the _IsArchive.
	 */
	public Boolean getIsArchive() {
		return _IsArchive;
	}

	/**
	 * @param isArchive The _IsArchive to set.
	 */
	public void setIsArchive(Boolean isArchive) {
		_IsArchive = isArchive;
	}

	/**
	 * @return Returns the _ReconStatus.
	 */
	public String getReconStatus() {
		return _ReconStatus;
	}

	/**
	 * @param reconStatus The _ReconStatus to set.
	 */
	public void setReconStatus(String reconStatus) {
		_ReconStatus = reconStatus;
	}

	/**
	 * @return Returns the _ToDate.
	 */
	public Date getToDate() {
		return _ToDate;
	}

	/**
	 * @param toDate The _ToDate to set.
	 */
	public void setToDate(Date toDate) {
		_ToDate = toDate;
	}

	/**
	 * @return Returns the _TrxId.
	 */
	public String getTrxId() {
		return _TrxId;
	}

	/**
	 * @param trxId The _TrxId to set.
	 */
	public void setTrxId(String trxId) throws MCDSException{
		if( trxId != null){
			_TrxId = trxId;
		}else{
			throw new MCDSException("Invalid Transaction ID");
		}
	}

	/**
	 * @return Returns the _TrxStatus.
	 */
	public String getTrxStatus() {
		return _TrxStatus;
	}

	/**
	 * @param trxStatus The _TrxStatus to set.
	 */
	public void setTrxStatus(String trxStatus) {
		_TrxStatus = trxStatus;
	}
}
