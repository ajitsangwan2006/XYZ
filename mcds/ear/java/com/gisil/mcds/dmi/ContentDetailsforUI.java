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

/**
 * This class contains Content details for UI
 * @author
 *
 */
public class ContentDetailsforUI implements Serializable{

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = -1748121826092775305L;

	private boolean isParent;
	
	private String contentName = null;
	
	private String status = null;
	
	private Integer contentId = null;
	
	private Integer parentId = null;
	
	private Integer parentOfParentId = null;
	
	private String type = null;


	/**
	 * @return Returns the parentId.
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId The parentId to set.
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the contentId.
	 */
	public Integer getContentId() {
		return contentId;
	}

	/**
	 * @param contentId The contentId to set.
	 */
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	/**
	 * @return Returns the contentName.
	 */
	public String getContentName() {
		return contentName;
	}

	/**
	 * @param contentName The contentName to set.
	 */
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	/**
	 * @return Returns the isParent.
	 */
	public boolean isParent() {
		return isParent;
	}

	/**
	 * @param isParent The isParent to set.
	 */
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the parentOfParentId.
	 */
	public Integer getParentOfParentId() {
		return parentOfParentId;
	}

	/**
	 * @param parentOfParentId The parentOfParentId to set.
	 */
	public void setParentOfParentId(Integer parentOfParentId) {
		this.parentOfParentId = parentOfParentId;
	}
}
