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

/**
 * Represents Phone Mapping object 
 * @author 
 *
 */
public class PhoneMappingInfo implements Serializable {

	/** serial Version UID */
	private static final long serialVersionUID = 7299904216715853516L;

	private String phoneMake = null;

	private String phoneModel = null;

	private String aggPhoneMake = null;

	private String aggPhoneCode = null;
	
	private String aggPhoneModel = null;
	
	public PhoneMappingInfo() {
	}

	public PhoneMappingInfo(String make, String model) {

		this.setPhoneMake(make);
		this.setPhoneModel(model);
	}

	/**
	 * @return Returns the aggPhoneCode.
	 */
	public String getAggPhoneCode() {
		return aggPhoneCode;
	}

	/**
	 * @param aggPhoneCode
	 *            The aggPhoneCode to set.
	 */
	public void setAggPhoneCode(String aggPhoneCode) {
		this.aggPhoneCode = aggPhoneCode;
	}

	/**
	 * @return Returns the aggPhoneMake.
	 */
	public String getAggPhoneMake() {
		return aggPhoneMake;
	}

	/**
	 * @param aggPhoneMake
	 *            The aggPhoneMake to set.
	 */
	public void setAggPhoneMake(String aggPhoneMake) {
		this.aggPhoneMake = aggPhoneMake;
	}

	/**
	 * @return Returns the phoneMake.
	 */
	public String getPhoneMake() {
		return phoneMake;
	}

	/**
	 * @param phoneMake
	 *            The phoneMake to set.
	 */
	public void setPhoneMake(String phoneMake) {
		this.phoneMake = phoneMake;
	}

	/**
	 * @return Returns the phoneModel.
	 */
	public String getPhoneModel() {
		return phoneModel;
	}

	/**
	 * @param phoneModel
	 *            The phoneModel to set.
	 */
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getAggPhoneModel() {
		return aggPhoneModel;
	}

	public void setAggPhoneModel(String aggCode) {
		this.aggPhoneModel = aggCode;
	}

}
