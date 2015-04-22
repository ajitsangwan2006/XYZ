package com.gisil.mcds.dmi;

import java.io.Serializable;

/**
 * Represents Pack item for UI
 * @author 
 *
 */
public class PackItemForUI implements Serializable{

	/**serialVersionUID*/
	private static final long serialVersionUID = 4662054180236205273L;

	private String status = null;
	
	private String title = null;
	
	private String id = null;
	
	private String type = null;
	
	private String tsCreated = null;
	
	private String tsUpdated = null;

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
