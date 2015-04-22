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

import java.sql.ResultSet;
import java.sql.SQLException;
import com.gisil.mcds.base.MCDSException;

/**
 * The class<code>CommissionFormula</code>is the form of<code>IEvaluatable</code>
 * indicates commission formula information
 * 
 * @author sandeep kadyan
 */
class CommissionFormula implements IEvaluatable {

	/**
	 * Id is commission formula id details
	 */
	private int id;

	/**
	 * Formula id details
	 */
	private int formulaId;

	/**
	 * Is fixed commissiom details
	 */
	private boolean isFixed;

	/**
	 * Value is commission value details
	 */
	private double value;

	/**
	 * Is surcharge fixed deatils
	 */
	private boolean isSurchargeFixed;

	/**
	 * Surcharge details
	 */
	private double surchange;

	/**
	 * Partner id details
	 */
	private Integer partnerId;

    /**
     * Class surcharge details
     */
    private boolean classSurcharge;

	/**
	 * This method is returns the result set object
	 * 
	 * @return Formula
	 */
	public static CommissionFormula create(ResultSet rs) throws SQLException {
		CommissionFormula formula = new CommissionFormula();
		formula.id = rs.getInt("ID");
		formula.formulaId = rs.getInt("FORMULAID");
		formula.partnerId = rs.getInt("PARTNERID");
		formula.isFixed = "FIXED".equals(rs.getString("COMMISSIONTYPE"));
		formula.value = rs.getDouble("COMMVALUE");
		formula.isSurchargeFixed = "FIXED"
				.equals(rs.getString("SURCHARGETYPE"));
		formula.surchange = rs.getDouble("SURCHARGE");
		formula.classSurcharge = rs.getBoolean("ISCLASSSURCHARGE");
		return formula;
	}

	/**
	 * @return Formula id
	 */
	public int getFormulaId() {
		return formulaId;
	}

	/**
	 * Set the formula id
	 */
	public void setFormulaId(int formulaId) {
		this.formulaId = formulaId;
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
	 * @return Is fixed
	 */
	public boolean isFixed() {
		return isFixed;
	}

	/**
	 * Set the fixed
	 */
	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}

	/**
	 * @return Is surcharge fixed
	 */
	public boolean isSurchargeFixed() {
		return isSurchargeFixed;
	}

	/**
	 * Set the surcharge fixed
	 */
	public void setSurchargeFixed(boolean isSurchargeFixed) {
		this.isSurchargeFixed = isSurchargeFixed;
	}

	/**
	 * @return Partner id
	 */
	public Integer getPartnerId() {
		return partnerId;
	}

	/**
	 * Set the partner id
	 */
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return Surcharge
	 */
	public double getSurchange() {
		return surchange;
	}

	/**
	 * Set the surcharge
	 */
	public void setSurchange(double surchange) {
		this.surchange = surchange;
	}

	/**
	 * @return Value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Set the value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return commission + surcharge value
	 */
	public Number evaulate(IContext ctx) throws MCDSException {
		Number baseAmt = (Number) ctx.getValue(IContext.BASE_AMT);

		double comm = isFixed ? value : ((value * baseAmt.doubleValue()) / 100);

		double srge = isSurchargeFixed ? surchange : ((surchange * baseAmt
				.doubleValue()) / 100);

		return Double.valueOf(comm + srge);
	}

	/**
	 * @return Returns the classSurcharge.
	 */
	public boolean isClassSurcharge() {
		return classSurcharge;
	}

	/**
	 * @param classSurcharge The classSurcharge to set.
	 */
	public void setClassSurcharge(boolean classSurcharge) {
		this.classSurcharge = classSurcharge;
	}

	
}
