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

import java.util.List;

import com.gisil.mcds.base.MCDSException;

/**
 * @TODO document me
 * @author Amit Sachan
 */
public class EntityDMLRequest extends MCDSException {

    private List<?> newEntities;

    private List<?> updatedEntities;

    private List<?> deletedEntityIds;

    private int entityType;

    public interface EntityConstants {

        int ROUTE = 1;

        int AGGREGATOR = 2;

        int COMM_FORMULA = 3;

        int COMM_FORMULA_MAPPING = 4;

        int PARTNER = 5;
        
        int CONFIGURATION= 6;
    }

    /**
     * @TODO document me
     */
    private static final long serialVersionUID = 1L;

    /**
     * @TODO document me
     */
    public EntityDMLRequest(int type) {
        super("-1");
        entityType = type;
    }

    
    public List<?> getDeletedEntityIds() {
        return deletedEntityIds;
    }

    
    public void setDeletedEntityIds(List<?> deletedEntityIds) {
        this.deletedEntityIds = deletedEntityIds;
    }

    
    public List<?> getUpdatedEntities() {
        return updatedEntities;
    }

    
    public void setUpdatedEntities(List<?> updatedEntities) {
        this.updatedEntities = updatedEntities;
    }

    
    public List<?> getNewEntities() {
        return newEntities;
    }

    
    public void setNewEntities(List<?> newEntities) {
        this.newEntities = newEntities;
    }

    
    public int getEntityType() {
        return entityType;
    }
    
    
    
}
