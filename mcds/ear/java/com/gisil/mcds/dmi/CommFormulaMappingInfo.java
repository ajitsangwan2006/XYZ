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
import java.util.List;

/**
 * The class<code>CommFormulaMappingInfo</code>is the form of<code>Serializable</code>
 * indicates to commission formula mapping information
 * 
 * @author Amit Sachan
 */
public class CommFormulaMappingInfo implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 3516341858511537895L;

    /**
     * Id is id details
     */
    private Long id;

    /**
     * Aggregator Id is aggregator id details
     */
    private Long aggregatorId;

    /**
     * Commission formula id is commission formula id details
     */
    private Long commformulaId;

    /**
     * Del Number is merchant mobile number
     */
    private String delNo;

    /**
     * Transaction type is transaction type details
     */
    private String trxType;

    /**
     * return the id and name map for aggregator
     */
    private String aggregatorName;
    private List aggMap;
    
    private List<Integer> commId;
    /**
     * @return Aggregator id
     */
    public Long getAggregatorId() {
        return aggregatorId;
    }

    /**
     * Set the aggregator id
     */
    public void setAggregatorId(Long aggregatorId) {
        this.aggregatorId = aggregatorId;
    }

    /**
     * @return Commission formula id
     */
    public Long getCommformulaId() {
        return commformulaId;
    }

    /**
     * Set the commission formula id
     */
    public void setCommformulaId(Long commformulaId) {
        this.commformulaId = commformulaId;
    }

    /**
     * @return Del number
     */
    public String getDelNo() {
        return delNo;
    }

    /**
     * Set the del number
     */
    public void setDelNo(String delNo) {
        this.delNo = delNo;
    }

    /**
     * @return Id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Transaction type
     */
    public String getTrxType() {
        return trxType;
    }

    /**
     * Set the transaction type
     */
    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

	public List getAggMap() {
		return aggMap;
	}

	public void setAggMap(List aggMap) {
		this.aggMap = aggMap;
	}

	public List<Integer> getCommId() {
		return commId;
	}

	public void setCommId(List<Integer> commId) {
		this.commId = commId;
	}

	public String getAggregatorName() {
		return aggregatorName;
	}

	public void setAggregatorName(String aggregatorName) {
		this.aggregatorName = aggregatorName;
	}


}
