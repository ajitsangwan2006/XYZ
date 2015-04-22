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

/**
 * Class representing a Purchase transaction request
 * @author amit sachan
 * 
 */
public class PurchaseTrxRequest extends MCDSRequest {

	/** serialVersionUID */
	private static final long serialVersionUID = -2903644552469762669L;

	private Integer _contentId = null;

	private String _type = null; // read it from

	// ContentDetailsForPurchaseInfo

	private Integer _trxId = null;

	private String _msisdn = null;

	private Integer _agrId = null;
	

	public PurchaseTrxRequest() {
		// get the agr id from mgr
		setAgrId(1);
	}

	/**
	 * @return Returns the _agrId.
	 */
	public Integer getAgrId() {
		return _agrId;
	}

	/**
	 * @param id
	 *            The _agrId to set.
	 */
	public void setAgrId(Integer id) {
		_agrId = id;
	}

	/**
	 * @return Returns the _msisdn.
	 */
	public String getMsisdn() {
		return _msisdn;
	}

	/**
	 * @param _msisdn
	 *            The _msisdn to set.
	 */
	public void setMsisdn(String _msisdn) {
		this._msisdn = _msisdn;
	}

	/**
	 * @return Returns the _contentId.
	 */
	public Integer getContentId() {
		return _contentId;
	}

	/**
	 * @param id
	 *            The _contentId to set.
	 */
	public void setContentId(Integer id) {
		_contentId = id;
	}

	/**
	 * @return Returns the _contentName.
	 */
	public Integer gettrxId() {
		return _trxId;
	}

	/**
	 * @param name
	 *            The _contentName to set.
	 */
	public void setTrxId(Integer id) {
		_trxId = id;
	}

	/**
	 * @return Returns the _type.
	 */
	public String getType() {
		return _type;
	}

	/**
	 * @param _type
	 *            The _type to set.
	 */
	public void setType(String _type) {
		this._type = _type;
	}

}
