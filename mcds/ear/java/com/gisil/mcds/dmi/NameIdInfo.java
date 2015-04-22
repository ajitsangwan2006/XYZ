package com.gisil.mcds.dmi;

import java.io.Serializable;

public class NameIdInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1158443640079981679L;

	private Integer id;

	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
