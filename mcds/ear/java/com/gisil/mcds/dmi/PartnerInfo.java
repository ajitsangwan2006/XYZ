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

/**
 * The class<code>Partner</code>is the form of<code>Serializable</code>
 * and<code>Cloneable</code> indicates to partner details information
 * 
 * @author Amit Sachan
 */
public class PartnerInfo implements Serializable, Cloneable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id is partner id details
     */
    private Integer id;

    /**
     * Partner Name is partner name details
     */
    private String partnerName;

    /**
     * Description is partner description
     */
    private String description;

    /**
     * Status is partner status
     */
    private String status;

    /**
     * Time stamp created is current time
     */
    private Timestamp tsCreated;

    /**
     * Time stamp updated is updated time
     */
    private Timestamp tsUpdated;

    /**
     * Constructs the partner
     */
    public PartnerInfo() {
        super();
    }

    /**
     * @return cloneable object
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return Partner name
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * Set the partner name
     */
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
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
     * @return Time stamp created
     */
    public Timestamp getTsCreated() {
        return tsCreated;
    }

    /**
     * Set the time stamp created
     */
    public void setTsCreated(Timestamp tsCreated) {
        this.tsCreated = tsCreated;
    }

    /**
     * @return Time stamp updated
     */
    public Timestamp getTsUpdated() {
        return tsUpdated;
    }

    /**
     * Set the time stamp
     */
    public void setTsUpdated(Timestamp tsUpdated) {
        this.tsUpdated = tsUpdated;
    }

}
