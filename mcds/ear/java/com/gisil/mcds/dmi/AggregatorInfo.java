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
 * The class<code>AggregatorInfo</code>is the form of<code>Serializable</code>
 * indicates to aggregator detail informations
 * 
 * @author pankaj pagar
 */
public class AggregatorInfo implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -5058980938682997825L;

    /**
     * Id is aggregator id
     */
    private Integer id;

    /**
     * Aggregator Name is aggregator name details
     */
    private String aggregatorName;

    /**
     * Web Unified Resource Locator is aggregator web details
     */
    private String webURL;

    /**
     * Description is aggregator description details
     */
    private String description;

    /**
     * Created is current time stamp
     */
    private Timestamp created;

    /**
     * Updated is updated time stamp
     */
    private Timestamp updated;

    /**
     * Status is aggregator status
     */
    private String status;

    /**
     * User is a aggregator
     */
    private String user;

    /**
     * Password is aggregator password
     */
    private String password;
    
    
    /**
     * @return Returns the aggregatorName.
     */
    public String getAggregatorName() {
        return aggregatorName;
    }

    /**
     * @param aggregatorName
     *            The aggregatorName to set.
     */
    public void setAggregatorName(String aggregatorName) {
        this.aggregatorName = aggregatorName;
    }

    /**
     * @return Returns the created.
     */
    public Timestamp getCreated() {
        return created;
    }

    /**
     * @param created
     *            The created to set.
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /**
     * @return Returns the description
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

    /**
     * @return Returns the updated.
     */
    public Timestamp getUpdated() {
        return updated;
    }

    /**
     * @param updated
     *            The updated to set.
     */
    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    /**
     * @return Returns the webURL.
     */
    public String getWebURL() {
        return webURL;
    }

    /**
     * @param webURL
     *            The webURL to set.
     */
    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * Set the user
     */
    public void setUser(String user) {
        this.user = user;
    }


}
