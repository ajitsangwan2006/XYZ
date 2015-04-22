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

import java.io.Serializable;

/**
 * Class describes required Configuration Parameters
 * @author
 *
 */
public class Configuration implements Serializable
{

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 4096890044388034085L;
	
	private Integer _rowId = null;
	
	private String _paramName = null;
	
	private String _paramValue = null;
	
	private String _paramtype = null;

	private String _paramCategory = null;
	
	private String _paramNewValue = null;
	
	private String _description = null;
	
	private boolean isChanged = false;


	
	public void setRowId( Integer rowId )
	{
		this._rowId = rowId;
	}
	
	public Integer getRowId( )
	{
		return _rowId;
	}
	
	
	/**
	 * @return Returns the _paramCategory.
	 */
	public String getParamCategory() {
		return _paramCategory;
	}

	/**
	 * @param category The _paramCategory to set.
	 */
	public void setParamCategory(String category) {
		_paramCategory = category;
	}

	/**
	 * @return Returns the _paramName.
	 */
	public String getParamName() {
		return _paramName;
	}

	/**
	 * @param name The _paramName to set.
	 */
	public void setParamName(String name) {
		_paramName = name;
	}

	/**
	 * @return Returns the _paramNewValue.
	 */
	public String getParamNewValue() {
		return _paramNewValue;
	}

	/**
	 * @param newValue The _paramNewValue to set.
	 */
	public void setParamNewValue(String newValue) {
		_paramNewValue = newValue;
	}

	/**
	 * @return Returns the _paramtype.
	 */
	public String getParamtype() {
		return _paramtype;
	}

	/**
	 * @param _paramtype The _paramtype to set.
	 */
	public void setParamtype(String _paramtype) {
		this._paramtype = _paramtype;
	}

	/**
	 * @return Returns the _paramValue.
	 */
	public String getParamValue() {
		return _paramValue;
	}

	public void setDescription( String description )
	{
		_description = description;
	}
	
	public String getDescription()
	{
		return _description;
	}
	/**
	 * @param value The _paramValue to set.
	 */
	public void setParamValue(String value) {
		_paramValue = value;
	}

	/**
	 * @return Returns the isChanged.
	 */
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * @param isChanged The isChanged to set.
	 */
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}
	
	
}
