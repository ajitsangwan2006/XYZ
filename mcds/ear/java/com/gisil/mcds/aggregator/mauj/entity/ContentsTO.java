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
package com.gisil.mcds.aggregator.mauj.entity;

import java.io.Serializable;

/**
 * 
 * @author ajit singh
 * 
 */
public class ContentsTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1399823453239457104L;

	private String _contentId = null;

	private String _contentName = null;

	private String _contentCode = null;

	public String getContentCode() {
		return _contentCode;
	}

	public void setContentCode(String code) {
		_contentCode = code;
	}

	public String getContentId() {
		return _contentId;
	}

	public void setContentId(String id) {
		_contentId = id;
	}

	public String getContentName() {
		return _contentName;
	}

	public void setContentName(String name) {
		_contentName = name;
	}

}
