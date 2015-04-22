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
import java.util.ArrayList;

/**
 * Represents Pack detail for UI 
 * @author
 *
 */
public class PackDetailForUI implements Serializable{

	/**
	 * serial Version UID
	 */
	
	private static final long serialVersionUID = -6139869704129576841L;

	private String id = null;
	
	private String catalogId = null;
	
	private String status = null;
	
	private String cost = null;
	
	private String title = null;
	
	private String description = null;
	
	private String sku = null;
	
	private String type = null;
	
	private String tsCreated = null;
	
	private String tsUpdated = null;
	
	private ArrayList<PackItemForUI> items = null;

	public PackDetailForUI(){
		items = new ArrayList<PackItemForUI>();
	}
	/**
	 * @return Returns the catalogId.
	 */
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * @param catalogId The catalogId to set.
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * @return Returns the cost.
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * @param cost The cost to set.
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the items.
	 */
	public ArrayList<PackItemForUI> getItems() {
		return items;
	}

	/**
	 * @param items The items to set.
	 */
	public void setItems(ArrayList<PackItemForUI> items) {
		this.items = items;
	}

	/**
	 * @return Returns the sku.
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * @param sku The sku to set.
	 */
	public void setSku(String sku) {
		this.sku = sku;
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
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Returns the tsCreated.
	 */
	public String getTsCreated() {
		return tsCreated;
	}

	/**
	 * @param tsCreated The tsCreated to set.
	 */
	public void setTsCreated(String tsCreated) {
		this.tsCreated = tsCreated;
	}

	/**
	 * @return Returns the tsUpdated.
	 */
	public String getTsUpdated() {
		return tsUpdated;
	}

	/**
	 * @param tsUpdated The tsUpdated to set.
	 */
	public void setTsUpdated(String tsUpdated) {
		this.tsUpdated = tsUpdated;
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

}
