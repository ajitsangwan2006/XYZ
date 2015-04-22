package com.gisil.mcds.aggregator.mauj.entity;

import java.io.Serializable;

public class TopContentTO implements Serializable{
	/**
	 * serialVersionUID = 9025169523285532252L
	 */
	private static final long serialVersionUID = 9025169523285532252L;
	private String contentId;
	private String contentName;
	private String isParent;
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	

}
