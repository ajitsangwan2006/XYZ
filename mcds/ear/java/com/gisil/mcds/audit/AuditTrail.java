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
package com.gisil.mcds.audit;

import java.io.Serializable;
import java.sql.Timestamp;

public class AuditTrail implements Serializable {
	/**
	 * serial Version UID 
	 */
	private static final long serialVersionUID = 7390258539555192944L;

	// Audit ID
	private int id = 0;

	// User ID
	private String userId = "";

	// Action ID
	private int actionId = 0;

	// Additional Info Column 1
	private String additionalInfo1 = "";

	// Additional Info Column 2
	private String additionalInfo2 = "";

	// Additional Info Column 3
	private String additionalInfo3 = "";

	// Additional Info Column 4
	private String additionalInfo4 = "";

	// Additional Info Column 5
	private String additionalInfo5 = "";

	// Timestamp
	private Timestamp ts = null;

	// IP Address
	private String ipAddress = "";

	private String usrId = "";

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public AuditTrail() {

	}

	/**
	 * @return the actionId
	 */
	public int getActionId() {
		return actionId;
	}

	/**
	 * @param actionId
	 *            the actionId to set
	 */
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	/**
	 * @return the additionalInfo1
	 */
	public String getAdditionalInfo1() {
		return additionalInfo1;
	}

	/**
	 * @param additionalInfo1
	 *            the additionalInfo1 to set
	 */
	public void setAdditionalInfo1(String additionalInfo1) {
		this.additionalInfo1 = additionalInfo1;
	}

	/**
	 * @return the additionalInfo2
	 */
	public String getAdditionalInfo2() {
		return additionalInfo2;
	}

	/**
	 * @param additionalInfo2
	 *            the additionalInfo2 to set
	 */
	public void setAdditionalInfo2(String additionalInfo2) {
		this.additionalInfo2 = additionalInfo2;
	}

	/**
	 * @return the additionalInfo3
	 */
	public String getAdditionalInfo3() {
		return additionalInfo3;
	}

	/**
	 * @param additionalInfo3
	 *            the additionalInfo3 to set
	 */
	public void setAdditionalInfo3(String additionalInfo3) {
		this.additionalInfo3 = additionalInfo3;
	}

	/**
	 * @return the additionalInfo4
	 */
	public String getAdditionalInfo4() {
		return additionalInfo4;
	}

	/**
	 * @param additionalInfo4
	 *            the additionalInfo4 to set
	 */
	public void setAdditionalInfo4(String additionalInfo4) {
		this.additionalInfo4 = additionalInfo4;
	}

	/**
	 * @return the additionalInfo5
	 */
	public String getAdditionalInfo5() {
		return additionalInfo5;
	}

	/**
	 * @param additionalInfo5
	 *            the additionalInfo5 to set
	 */
	public void setAdditionalInfo5(String additionalInfo5) {
		this.additionalInfo5 = additionalInfo5;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the ts
	 */
	public Timestamp getTs() {
		return ts;
	}

	/**
	 * @param ts
	 *            the ts to set
	 */
	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Returns a string equivalent of this object
	 */
	public String toString() {

		StringBuffer buffer = new StringBuffer();

		buffer.append("[");

		buffer.append("" + userId);
		buffer.append(" ," + actionId);
		buffer.append(" ," + additionalInfo1);
		buffer.append(" ," + additionalInfo2);
		buffer.append(" ," + ts);

		buffer.append("]");

		return buffer.toString();
	}
}
