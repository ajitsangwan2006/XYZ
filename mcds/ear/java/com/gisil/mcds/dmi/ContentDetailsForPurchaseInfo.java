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
 * This class contains Content details for purchase
 * @author amit sachan
 * 
 */
public class ContentDetailsForPurchaseInfo implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8050408730133621131L;

	public static final String ITEM = "Item";

	public static final String PACK = "Pack";

	private Number cost = null;

	private String sku = null;

	private String skuName = null;

	/**
	 * @return Returns the sku.
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * @param sku
	 *            The sku to set.
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}

	/**
	 * @return Returns the skuName.
	 */
	public String getSkuName() {
		return skuName;
	}

	/**
	 * @param skuName
	 *            The skuName to set.
	 */
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	/**
	 * @return Returns the cost.
	 */
	public Number getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            The cost to set.
	 */
	public void setCost(Number cost) {
		this.cost = cost;
	}

}
