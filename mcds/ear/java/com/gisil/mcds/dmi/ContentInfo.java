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
import java.util.Map;

/**
 * Represents a Content Info Object
 * @author
 *
 */
public class ContentInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 547398657541677853L;

	private String status;

	private String contentName;

	private String description;

	private Integer aggregatorId;

	private String deliveryMode;
	
	private Integer contentId;
	
	private Map aggregatorList;

	public Integer getAggregatorId() {
		return aggregatorId;
	}

	public void setAggregatorId(Integer aggregatorId) {
		this.aggregatorId = aggregatorId;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Map getAggregatorList() {
		return aggregatorList;
	}

	public void setAggregatorList(Map aggregatorList) {
		this.aggregatorList = aggregatorList;
	}

}
