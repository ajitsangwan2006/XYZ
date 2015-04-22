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
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MCDSRequest implements Serializable {
	/**
	 * del No for merchant information 
	 */
	protected String _delNo;

	/**
	 * 
	 */
	protected String _payMode = "cash";

	/**
	 * client Id for customer information 
	 */
	protected String _clientId;

	/**
	 * new instantce request Id  
	 */
	private int _requestId;

	/**
	 * new Atomic Integer creation for counter 
	 */
	private static AtomicInteger counter = new AtomicInteger();

	/**
	 * Use of reflection API's
	 */
	public MCDSRequest() {
	}

	/**
	 * Constructs the bustik request with supplied delno as a increments
	 * counter.
	 */
	public MCDSRequest(String delno) {
		_delNo = delno;
		_requestId = counter.getAndIncrement();
	}

	/**
	 * Returns requested unique delno
	 */
	public String getUniqueRequestId() {
		String prefix = _clientId;
		if (prefix != null)
			prefix = String.valueOf(_delNo);

		return prefix + "_" + _requestId;
	}

	/**
	 * Set the client id
	 */
	public void setClientId(String clientId) {
		_clientId = clientId;
	}

	/**
	 * Returns the delno as a String
	 */
	public String getDelNo() {
		return _delNo;
	}

	/**
	 * Returns String delno.
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + _delNo + "]";
	}

	/**
	 * @return Returns the _payMode.
	 */
	public String getPayMode() {
		return _payMode;
	}

	/**
	 * @param mode The _payMode to set.
	 */
	public void setPayMode(String mode) {
		_payMode = mode;
	}

	public void setDelNo(String no) {
		_delNo = no;
	}
}
