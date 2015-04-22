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
package com.gisil.mcds.base;

public class MCDSException extends Exception {
	private static final long serialVersionUID = 4857719430480729683L;

	private int _errorCode;

	private Object _info;

	public MCDSException() {
		super();
	}

	public MCDSException(String msg) {
		super(msg);
	}

	public MCDSException(int errorCode) {
		_errorCode = errorCode;
	}

	public MCDSException(String msg, int errorCode) {
		super(msg);
		_errorCode = errorCode;
	}

	public MCDSException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public int getErrorCode() {
		return _errorCode;
	}

	public Object getInfo() {
		return _info;
	}

	public void setErrorCode(int errorCode) {
		_errorCode = errorCode;
	}

	public void setInfo(Object info) {
		_info = info;
	}

}
