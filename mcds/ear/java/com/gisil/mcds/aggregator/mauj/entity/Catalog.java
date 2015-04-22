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
import java.util.ArrayList;

public class Catalog implements Serializable {

	/**
	 * generated Serial Version Id
	 */
	private static final long serialVersionUID = 4559528703921874853L;

	private String _id = null;

	private ArrayList<ContentPack> _contentPackList = null;

	public Catalog() {

	}

	/**
	 * set id
	 * @param id
	 */
	public void setId(String id) {
		_id = id;
	}

	/**
	 * get id
	 * @return
	 */
	public String getId() {
		return _id;
	}

	/**
	 * set content pack list
	 * @param contentPackList
	 */
	public void setContentPackList(ArrayList<ContentPack> contentPackList) {
		_contentPackList = contentPackList;
	}

	/**
	 * get content pack list
	 * @return
	 */
	public ArrayList<ContentPack> getContentPackList() {
		return _contentPackList;
	}

}
