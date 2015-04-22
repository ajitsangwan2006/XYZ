package com.gisil.mcds.dmi;

import java.io.Serializable;

public class PackTypeListInfo implements Serializable{

	/**serialVersionUID*/
	private static final long serialVersionUID = -5786379544578833329L;
	
	private String displayName = null;
	
	private String value = null;

	/**
	 * @return Returns the displayName.
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName The displayName to set.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
