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
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gisil.mcds.base.MCDSException;

/**
 * The class<code>ClassSurcharge</code> is the form of<code>IEvaluatable</code>
 * is indicates class surcharge information
 * 
 * @author Amit Sachan
 */
public class ClassSurcharge implements IEvaluatable,Serializable {

    /**
     * Created Null class surcharge instance
     */
    public static final ClassSurcharge NULL_CLASS_SURCHAGE = new ClassSurcharge() {

        /**
         * @return Information evaluatable zero
         */
        @Override
        public Number evaulate(IContext arg0) throws MCDSException {
            return IEvaluatable.ZERO;
        }
    };

    /**
     * Id is class surcharge id
     */
    private int id;

    /**
     * Class code is code details
     */
    private String classCode;

    /**
     * Class name is surcharge name details
     */
    private String className;

    /**
     * Is surcharge fixed is fixed surcharge details
     */
    private boolean isSurchargeFixed;

    /**
     * Surchange details
     */
    private double surcharge;

    /**
     * Status is class surcharge status details
     */
    private String status;

    /**
     * Aggregator id details
     */
    private Integer aggregatorId;

    /**
     * Aggregator name
     */
    private String aggregatorName;
    /**
     * Constructs class surcharge
     */
    public ClassSurcharge() {
        super();
    }

    /**
     * This method is returns the result set for sql
     * 
     * @return Charge
     */
    public static ClassSurcharge create(ResultSet rs) throws SQLException {
        ClassSurcharge charge = new ClassSurcharge();
        charge.classCode = rs.getString("CLASSCODE");
        charge.className = rs.getString("CLASSNAME");
        charge.surcharge = rs.getDouble("SURCHARGE");
        charge.isSurchargeFixed = "FIXED".equals(rs.getString("SURCHARGETYPE"));
        charge.status = rs.getString("STATUS");
        charge.aggregatorId = rs.getInt("AGGREGATORID");
        if(rs.getString("AGGREGATORNAME") != null){
            charge.aggregatorName = rs.getString("AGGREGATORNAME");
        }

        return charge;
    }

    /**
     * @return Aggregator id
     */
    public Integer getAggregatorId() {
        return aggregatorId;
    }

    /**
     * Set the aggregator id
     */
    public void setAggregatorId(Integer aggregatorId) {
        this.aggregatorId = aggregatorId;
    }

    /**
     * 
     * @return
     */
    public String getAggregatorName()
    {
    	return aggregatorName;
    }
    
    public void setAggregatorName( String aggregatorName )
    {
    	this.aggregatorName = aggregatorName;
    }
    /**
     * @return Class code
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * Set the class code
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    /**
     * @return Class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Is surcharge fixed
     */
    public boolean isSurchargeFixed() {
        return isSurchargeFixed;
    }

    /**
     * Set the curcharge fixed
     */
    public void setSurchargeFixed(boolean isSurchargeFixed) {
        this.isSurchargeFixed = isSurchargeFixed;
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
     * @return surcharge
     */
    public double getSurcharge() {
        return surcharge;
    }

    /**
     * Set the surcharge
     */
    public void setSurcharge(double surcharge) {
        this.surcharge = surcharge;
    }

    /**
     * @return Result
     */
    public Number evaulate(IContext ctx) throws MCDSException {
        Number baseAmt = (Number) ctx.getValue(IContext.BASE_AMT);

        double comm = isSurchargeFixed ? surcharge : ((surcharge * baseAmt.doubleValue()) / 100);
        
        return Double.valueOf(comm );
    }

}