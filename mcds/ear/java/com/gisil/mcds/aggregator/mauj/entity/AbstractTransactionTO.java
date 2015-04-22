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

/**
 * @author amit sachan
 * 
 */
public class AbstractTransactionTO {

	private String _skuCode = null;

	private String _transId = null;

	/**
	 * @return Returns the _skuCode.
	 */
	public String getSkuCode() {
		return _skuCode;
	}

	/**
	 * @param code
	 *            The _skuCode to set.
	 */
	public void setSkuCode(String code) {
		_skuCode = code;
	}

	/**
	 * @return Returns the _transId.
	 */
	public String getTransId() {
		return _transId;
	}

	/**
	 * @param id
	 *            The _transId to set.
	 */
	public void setTransId(String id) {
		_transId = id;
	}

	/**
	 * 
	 * @param trxId
	 * @param sku
	 */
	public AbstractTransactionTO(String trxId, String sku) {

		setTransId(trxId);
		setSkuCode(sku);

	}
	
	public AbstractTransactionTO(){
	}
	

}
