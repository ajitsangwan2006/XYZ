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

import java.util.Date;

import com.gisil.mcds.transaction.TrxStatus;


/**
 * Class represents Purchase transaction Object 
 * @author
 *
 */
public class PurchaseTrxResponse extends MCDSResponse{

	/**serialVersionUID*/
	private static final long serialVersionUID = 1254662051343003970L;
	
	private Integer _trxId = null;
	private Integer _terminalId = null;
	private Date _date = null;
	private TrxStatus _trxStatus = null;
	private String _content = null;
	private String _skuCode = null;
	private double _price = 0.0;
	private String _cmsName = null;
	private double _cmsValue = 0.0;
	/**
	 * @return Returns the _content.
	 */
	public String getContent() {
		return _content;
	}
	/**
	 * @param _content The _content to set.
	 */
	public void setContent(String _content) {
		this._content = _content;
	}
	/**
	 * @return Returns the _date.
	 */
	public Date getDate() {
		return _date;
	}
	/**
	 * @param _date The _date to set.
	 */
	public void setDate(Date _date) {
		this._date = _date;
	}
	
	/**
	 * @return Returns the _price.
	 */
	public double getPrice() {
		return _price;
	}
	/**
	 * @param _price The _price to set.
	 */
	public void setPrice(double _price) {
		this._price = _price;
	}
	/**
	 * @return Returns the _skuCode.
	 */
	public String getSkuCode() {
		return _skuCode;
	}
	/**
	 * @param code The _skuCode to set.
	 */
	public void setSkuCode(String code) {
		_skuCode = code;
	}
	/**
	 * @return Returns the _status.
	 */
	public TrxStatus getStatus() {
		return _trxStatus;
	}
	/**
	 * @param _status The _status to set.
	 */
	public void setStatus(TrxStatus _status) {
		this._trxStatus = _status;
	}
	/**
	 * @return Returns the _terminalId.
	 */
	public Integer getTerminalId() {
		return _terminalId;
	}
	/**
	 * @param id The _terminalId to set.
	 */
	public void setTerminalId(Integer id) {
		_terminalId = id;
	}
	/**
	 * @return Returns the _trxId.
	 */
	public Integer getTrxId() {
		return _trxId;
	}
	/**
	 * @param id The _trxId to set.
	 */
	public void setTrxId(Integer id) 
	{
		_trxId = id;
	}
	/**
	 * @return Returns the cmsName.
	 */
	public String getCmsName() {
		return _cmsName;
	}
	/**
	 * @param cmsName The cmsName to set.
	 */
	public void setCmsName(String cmsName) {
		this._cmsName = cmsName;
	}
	/**
	 * @return Returns the cmsValue.
	 */
	public double getCmsValue() {
		return _cmsValue;
	}
	/**
	 * @param cmsValue The cmsValue to set.
	 */
	public void setCmsValue(double cmsValue) {
		this._cmsValue = cmsValue;
	}
}
