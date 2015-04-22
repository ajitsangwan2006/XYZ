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
package com.gisil.mcds.aggregator.mauj.entity;

public class ValidTransTrackerRes extends AbstractTransTrackerResponse {

	/**serialVersionUID*/
	private static final long serialVersionUID = -7975392777071714740L;

	private String _transId = null;

	private String _msisdnNo = null;

	private String _phoneMake = null;

	private String _phoneModel = null;

	private String _cost = null;

	private String _sku = null;

	private String _transStatus = null;

	private String _errorCode = null;

	private String _posId = null;

	private String _locId = null;

	private String _logTime = null;

	/**
	 * @return Returns the _cost.
	 */
	public String getCost() {
		return _cost;
	}

	/**
	 * @param aCost The _cost to set.
	 */
	public void setCost(String cost) {
		this._cost = cost;
	}

	/**
	 * @return Returns the _locId.
	 */
	public String getLocId() {
		return _locId;
	}

	/**
	 * @param id The _locId to set.
	 */
	public void setLocId(String id) {
		_locId = id;
	}

	/**
	 * @return Returns the _logTime.
	 */
	public String getLogTime() {
		return _logTime;
	}

	/**
	 * @param time The _logTime to set.
	 */
	public void setLogTime(String time) {
		_logTime = time;
	}

	/**
	 * @return Returns the _msisdnNo.
	 */
	public String getMsisdnNo() {
		return _msisdnNo;
	}

	/**
	 * @param no The _msisdnNo to set.
	 */
	public void setMsisdnNo(String no) {
		_msisdnNo = no;
	}

	/**
	 * @return Returns the _phoneMake.
	 */
	public String getPhoneMake() {
		return _phoneMake;
	}

	/**
	 * @param make The _phoneMake to set.
	 */
	public void setPhoneMake(String make) {
		_phoneMake = make;
	}

	/**
	 * @return Returns the _phoneModel.
	 */
	public String getPhoneModel() {
		return _phoneModel;
	}

	/**
	 * @param model The _phoneModel to set.
	 */
	public void setPhoneModel(String model) {
		_phoneModel = model;
	}

	/**
	 * @return Returns the _posId.
	 */
	public String getPosId() {
		return _posId;
	}

	/**
	 * @param id The _posId to set.
	 */
	public void setPosId(String id) {
		_posId = id;
	}

	/**
	 * @return Returns the _sku.
	 */
	public String getSku() {
		return _sku;
	}

	/**
	 * @param _sku The _sku to set.
	 */
	public void setSku(String sku) {
		_sku = sku;
	}

	/**
	 * @return Returns the _transStatus.
	 */
	public String getTransStatus() {
		return _transStatus;
	}

	/**
	 * @param status The _transStatus to set.
	 */
	public void setTransStatus(String status) {
		_transStatus = status;
	}

	public String getTransId() {
		return _transId;
	}

	public void setTransId(String transId) {
		_transId = transId;
	}

	public String getErrorCode() {
		return _errorCode;
	}

	public void setErrorCode(String code) {
		_errorCode = code;
	}
}
