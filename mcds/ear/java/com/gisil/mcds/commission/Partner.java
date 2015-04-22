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
package com.gisil.mcds.commission;

import java.io.Serializable;

/**
 * The class<code>Partner</code> indicates partner details
 * 
 * @author amit sachan
 */
public class Partner implements Serializable{

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 4496832399561771469L;

	/**
	 * Id is partner id
	 */
	private Integer id;

	/**
	 * Partner name is partner name details
	 */
	private String PartnerName;

	/**
	 * Description is partner description
	 */
	private String Description;

	/**
	 * Status is partner status
	 */
	private String status;

	/**
	 * Constructs partner
	 */
	public Partner() {
		super();
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		Description = description;
	}

	/**
	 * @return Returns the id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return Returns the partnerName.
	 */
	public String getPartnerName() {
		return PartnerName;
	}

	/**
	 * @param partnerName
	 *            The partnerName to set.
	 */
	public void setPartnerName(String partnerName) {
		PartnerName = partnerName;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
