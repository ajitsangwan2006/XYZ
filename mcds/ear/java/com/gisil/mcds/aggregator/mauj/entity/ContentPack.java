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

/**
 * 
 * @author ashok kumar
 * 
 */
public class ContentPack implements Serializable {

	private static final long serialVersionUID = 1L;

	// ---------------------------------------------------
	// Data Members
	// ---------------------------------------------------
	private long _id = 0;

	private String _status = null;

	private String _sku = null;

	private String _title = null;

	private String _type = null;

	private double _cost = 0.0;

	private String _description = null;

	private long _contentId = 0;

	private ArrayList<Item> _itemList = null;

	// ---------------------------------------------------
	// Default Constructor
	// ---------------------------------------------------

	public ContentPack() {

	}

	// ---------------------------------------------------
	// Member Functions
	// ---------------------------------------------------

	public void setId(long id) {
		_id = id;
	}

	public long getId() {
		return _id;
	}

	public void setContentId(long contentId) {
		_contentId = contentId;
	}

	public long getContentId() {
		return _contentId;
	}

	public void setCost(double cost) {
		_cost = cost;
	}

	public double getCost() {
		return _cost;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public String getStatus() {
		return _status;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public String getSku() {
		return _sku;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getTitle() {
		return _title;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getType() {
		return _type;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getDescription() {
		return _description;
	}

	public void setItemList(ArrayList<Item> itemList) {
		_itemList = itemList;
	}

	public ArrayList getItemList() {
		return _itemList;
	}

}
