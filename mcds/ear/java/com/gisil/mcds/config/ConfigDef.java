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
package com.gisil.mcds.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gisil.mcds.config.ConfigInfo;

public class ConfigDef {

	private static final long serialVersionUID = 1L;

	private int paramId = 0;

	private String paramName;;

	private String paramValue;

	private String paramCategory;

	private String paramType;

	private int floating = 0;

	private int maxValue = 0;

	private int minValue = 0;

	private String enumValues = null;

	private int dirty = 0;

	private String description = null;

	private String status;

	private transient Object runtimeValue;

	/**
	 * @param paramId
	 */
	public void setParamId(int paramId) {
		this.paramId = paramId;
	}

	/**
	 * @return paramId
	 */
	public int getParamId() {
		return paramId;
	}

	/**
	 * @param paramName
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramValue
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
		synchronized (this) {
			runtimeValue = null;
			setDirty(1);
		}
	}

	/**
	 * @return paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param maxValue
	 */
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @param paramType
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	/**
	 * @return paramType
	 */
	public String getParamType() {
		return paramType;
	}

	/**
	 * @param floating
	 */
	public void setFloating(int floating) {
		this.floating = floating;
	}

	/**
	 * @return floating
	 */
	public int getFloating() {
		return floating;
	}

	/**
	 * @return
	 */
	public boolean isFloating() {
		if (getFloating() == 1)
			return true;
		else
			return false;
	}

	/**
	 * @return
	 */
	public int getMaxValue() {
		return maxValue;
	}

	/**
	 * @param minValue
	 */
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return minValue
	 */
	public int getMinValue() {
		return minValue;
	}

	/**
	 * @param enumValues
	 */
	public void setEnumvalues(String enumValues) {
		this.enumValues = enumValues;
	}

	/**
	 * @return enumValues
	 */
	public String getEnumValues() {
		return enumValues;
	}

	/**
	 * @param dirty
	 */

	public void setDirty(int dirty) {
		this.dirty = dirty;
	}

	/**
	 * @return dirty
	 */
	public int getDirty() {
		return dirty;
	}

	/**
	 * @return
	 */
	public boolean isDirty() {
		if (getDirty() == 1)
			return true;
		else
			return false;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("[");
		buffer.append(paramName);
		buffer.append(" , " + paramValue);
		buffer.append(" , " + description);

		buffer.append("]");

		return buffer.toString();
	}

	public synchronized Object getValueAsObject() {
		if (runtimeValue == null)
			runtimeValue = constructValue();
		return runtimeValue;
	}

	private Object constructValue() {
		if (paramValue == null)
			return null;

		if ("java.lang.Number".equals(paramType)) {// 
			return Double.valueOf(paramValue);
		} else if ("java.lang.Integer".equals(paramType))
			return Integer.valueOf(paramValue);
		else if ("java.lang.Boolean".equals(paramType))
			return Boolean.valueOf(paramValue);

		return paramValue;
	}

	public static ConfigDef create(ResultSet rs) throws SQLException {
		ConfigDef def = new ConfigDef();
		int paramID = rs.getInt("ID");
		String paramCategory = rs.getString("PARAMCATEGORY");
		String paramName = rs.getString("ParamName");
		String paramValue = rs.getString("ParamValue");
		String description = rs.getString("Description");
		String paramType = rs.getString("ParamType");
		int floating = rs.getInt("ISFLOAT");
		int minValue = rs.getInt("MinValue");
		int maxValue = rs.getInt("MaxValue");
		String enumValues = rs.getString("ENUMVALUES");

		def.setParamCategory(paramCategory);
		def.setParamId(paramID);
		def.setParamName(paramName);
		def.setParamValue(paramValue);
		def.setDescription(description);
		def.setParamType(paramType);
		def.setFloating(floating);
		def.setMinValue(minValue);
		def.setMaxValue(maxValue);
		def.setEnumvalues(enumValues);

		return def;
	}

	public String getParamCategory() {
		return paramCategory;
	}

	public void setParamCategory(String paramCategory) {
		this.paramCategory = paramCategory;
	}

	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ConfigInfo toInfo() {
		ConfigInfo info = new ConfigInfo();
		info.setChanged(isDirty());
		info.setParamId(getParamId());
		info.setDescription(getDescription());

		if (enumValues != null) {
			info.setEnumValues(enumValues.split(","));
		}
		info.setFloating(isFloating());
		if (maxValue != 0)
			info.setMaxValue(Integer.valueOf(maxValue));
		if (minValue != 0)
			info.setMinValue(Integer.valueOf(minValue));

		info.setParamCategory(getParamCategory());
		info.setParamName(getParamName());
		info.setParamType(getParamType());
		info.setParamValue(getParamValue());
		info.setStatus(getStatus());

		return info;
	}

}
