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

public class ContentDetailTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8387234629915439222L;

	private ArrayList contents = null;

	private boolean isEmptyContent = true;

	private boolean isEmptyContentName = true;

	private String backType = null;

	private String categoryName = null;

	private String title = null;

	private String code = null;

	private String price = null;
	
	private String type = null;
	
	private String contentName = null;

	/**
	 * get back type
	 * 
	 * @return
	 */
	public String getBackType() {
		return backType;
	}

	/**
	 * set back type
	 * 
	 * @param backType
	 */
	public void setBackType(String backType) {
		this.backType = backType;
	}

	/**
	 * get contents
	 * 
	 * @return
	 */
	public ArrayList getContents() {
		return contents;
	}

	/**
	 * set contents
	 * 
	 * @param contents
	 */
	public void setContents(ArrayList contents) {
		this.contents = contents;
	}

	/**
	 * find contents are empty or not
	 * 
	 * @return
	 */
	public boolean isEmptyContent() {
		return isEmptyContent;
	}

	/**
	 * set the boolean value of isEmptyContent
	 * 
	 * @param isEmptyContent
	 */
	public void setEmptyContent(boolean isEmptyContent) {
		this.isEmptyContent = isEmptyContent;
	}

	/**
	 * find contents name are empty or not
	 * 
	 * @return
	 */
	public boolean isEmptyContentName() {
		return isEmptyContentName;
	}

	/**
	 * set the boolean value of isEmptyContentName
	 * 
	 * @param isEmptyContent
	 */
	public void setEmptyContentName(boolean isEmptyContentName) {
		this.isEmptyContentName = isEmptyContentName;
	}

	/**
	 * get category name
	 * 
	 * @return
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * set category name
	 * 
	 * @param categoryName
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * get code
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * get code
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * get price
	 * 
	 * @return
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * set price
	 * 
	 * @param price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * get title
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * set title
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

}
