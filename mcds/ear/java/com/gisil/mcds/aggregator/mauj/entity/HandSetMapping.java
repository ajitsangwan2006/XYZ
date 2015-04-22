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

public class HandSetMapping {

	private String _serviceType = null;

	private String _aggMake = null;

	private String _aggModel = null;

	private String _aggCode = null;

	/**
	 * @return Returns the _aggCode.
	 */
	public String getAggCode() {
		return _aggCode;
	}

	/**
	 * @param code The _aggCode to set.
	 */
	public void setAggCode(String code) {
		_aggCode = code;
	}

	/**
	 * @return Returns the _aggMake.
	 */
	public String getAggMake() {
		return _aggMake;
	}

	/**
	 * @param make The _aggMake to set.
	 */
	public void setAggMake(String make) {
		_aggMake = make;
	}

	/**
	 * @return Returns the _aggModel.
	 */
	public String getAggModel() {
		return _aggModel;
	}

	/**
	 * @param model The _aggModel to set.
	 */
	public void setAggModel(String model) {
		_aggModel = model;
	}

	/**
	 * @return Returns the _serviceType.
	 */
	public String getServiceType() {
		return _serviceType;
	}

	/**
	 * @param type The _serviceType to set.
	 */
	public void setServiceType(String type) {
		_serviceType = type;
	}

}
