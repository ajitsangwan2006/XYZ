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
import java.sql.Timestamp;
import java.util.List;

/**
 * The class<code>CommFormulaInfo</code>is the form of<code>Serializable</code>
 * indicates to commission formula information
 * 
 * @author pankaj pagar
 */
public class CommFormulaInfo implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -1672637689585963098L;

    /**
     * Id is commission formula information
     */
    private Integer id;

    /**
     * Formula Id is dividing booking and cancellation ticket
     */
    private Long formulaId;

    /**
     * Partner Id is for red bus distributor or partner
     */
    private Long partnerId;

    /**
     * Commission Type is by default 'F' "Fixed"
     */
    private String commissionType;

    /**
     * commission Value is based on ticket amount
     */
    private Number commValue;

    /**
     * Status is by default 'E' "Enabled"
     */
    private String status;

    /**
     * Created is for current time stamp
     */
    private Timestamp created;

    /**
     * Updated is for updated time stamp
     */
    private Timestamp updated;

    /**
     * Surcharge in Percent on the base amount.
     */
    private Number surchange;

    /**
     * Sucharge Type is in percentage "%"
     */
    private String surchargeType;
	/**
	 * Partener name details
	 */
	private String partnerName;
	
	private List partnerNameList;
	
	private Boolean isClassSurchanrge;
  
    /**
     * @return Commission type
     */
    public String getCommissionType() {
        return commissionType;
    }

    /**
     * Set the commission type
     */
    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    /**
     * @return Commission value
     */
    public Number getCommValue() {
        return commValue;
    }

    /**
     * Set the commission value
     */
    public void setCommValue(Number commValue) {
        this.commValue = commValue;
    }

    /**
     * @return Created current time
     */
    public Timestamp getCreated() {
        return created;
    }

    /**
     * Set the created
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /**
     * @return Formula id
     */
    public Long getFormulaId() {
        return formulaId;
    }

    /**
     * Set the formula id
     */
    public void setFormulaId(Long formulaId) {
        this.formulaId = formulaId;
    }

    /**
     * @return Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return Partner id
     */
    public Long getPartnerId() {
        return partnerId;
    }

    /**
     * Set the partner id
     */
    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * @return Status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Surcharge
     */
    public Number getSurchange() {
        return surchange;
    }

    /**
     * Set the surcharge
     */
    public void setSurchange(Number surchange) {
        this.surchange = surchange;
    }

    /**
     * @return Surcharge type
     */
    public String getSurchargeType() {
        return surchargeType;
    }

    /**
     * Set the surcharge type
     */
    public void setSurchargeType(String surchargeType) {
        this.surchargeType = surchargeType;
    }

    /**
     * @return Updated time
     */
    public Timestamp getUpdated() {
        return updated;
    }

    /**
     * Set the updated
     */
    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
    public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public List getPartnerNameList() {
		return partnerNameList;
	}

	public void setPartnerNameList(List partnerNameList) {
		this.partnerNameList = partnerNameList;
	}

	public Boolean isClassSurchanrge() {
		return isClassSurchanrge;
	}

	public void setIsClassSurchanrge(Boolean isClassSurchanrge) {
		this.isClassSurchanrge = isClassSurchanrge;
	}

}
