package com.gisil.mcds.web.util;

import java.util.ArrayList;

import com.gisil.mcds.dmi.TransactionSummaryInfo;

/**
 * 
 * @author ajit singh
 *
 */
public class SessionData {

	private String _entryPoint = null;

	private String _searchBy = null;

	private String _contentInfo = null;

	private String _marchentId = null;

	private String _error = null;

	private String _delNo = null;

	private ArrayList<TransactionSummaryInfo> _trxSummaryList = null;
	
	private boolean _isCategorySearch = false;
	
	private int maxRecordsPerPage;
	/**
	 * @return Returns the maxRecordsPerPage.
	 */
	public int getMaxRecordsPerPage() {
		return maxRecordsPerPage;
	}

	/**
	 * @param maxRecordsPerPage The maxRecordsPerPage to set.
	 */
	public void setMaxRecordsPerPage(int maxRecordsPerPage) {
		this.maxRecordsPerPage = maxRecordsPerPage;
	}

	/**
	 * Set transaction summary list
	 * @param list
	 */
	public void setTrxSummaryList(ArrayList<TransactionSummaryInfo> list) {
		_trxSummaryList = list;
	}

	/**
	 * get transaction summary list
	 * @return
	 */
	public ArrayList<TransactionSummaryInfo> getTrxSummaryList() {
		return _trxSummaryList;
	}

	/**
	 * get entry point
	 * @return
	 */
	public String getEntryPoint() {
		return _entryPoint;
	}

	/**
	 * set entry point
	 * @param aEntryPoint
	 */
	public void setEntryPoint(String aEntryPoint) {
		_entryPoint = aEntryPoint;
	}

	/**
	 * get search by
	 * @return
	 */
	public String getSearchBy() {
		return _searchBy;
	}

	/**
	 * set search by
	 * @param aSearchBy
	 */
	public void setSearchBy(String aSearchBy) {
		_searchBy = aSearchBy;
	}

	/**
	 * get content info
	 * @return
	 */
	public String getContentInfo() {
		return _contentInfo;
	}

	/**
	 * set content info
	 * @param aContentInfo
	 */
	public void setContentInfo(String aContentInfo) {
		_contentInfo = aContentInfo;
	}

	/**
	 * get marchent id
	 * @return
	 */
	public String getMarchentId() {
		return _marchentId;
	}

	/**
	 * set marchent id
	 * @param aMarchentId
	 */
	public void setMarchentId(String aMarchentId) {
		_contentInfo = aMarchentId;
	}

	/**
	 * get error
	 * 
	 * @return
	 */
	public String getError() {
		return _error;
	}

	/**
	 * set error
	 * @param aError
	 */
	public void setError(String aError) {
		_error = aError;
	}

	/**
	 * get del no
	 * @return
	 */
	public String getDelNo() {
		return _delNo;
	}

	/**
	 * set del no
	 */
	public void setDelNo(String aDelNo) {
		_error = aDelNo;
	}

	public boolean isCategorySearch() {
		return _isCategorySearch;
	}

	public void setCategorySearch(boolean isCategorySearch) {
		this._isCategorySearch = isCategorySearch;
	}

}