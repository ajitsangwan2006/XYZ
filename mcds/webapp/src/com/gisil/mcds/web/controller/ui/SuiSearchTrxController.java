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

package com.gisil.mcds.web.controller.ui;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * Services suiSearchTrx.jsp 
 * @author amit sachan
 * 
 */
public class SuiSearchTrxController extends AbstractJspController {

	public static final String PAGE_PATH = "suiSearchTrx.jsp";

	private Boolean _searchInArchive = false;

	private Boolean _showAll = true;

	private String _delno = null;

	private String _trxId = null;

	private String _trxStatus = null;

	private Boolean _reconSatus = null;

	private String _toDate = null;

	private String _fromDate = null;
	
	public Boolean _isSearch = false;

	public SuiSearchTrxController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);

		// load data if any
		loadData();

	}

	/**
	 * 
	 * 
	 */
	private void loadData() {
		// get all the data if in the request

		if (_request.getParameter("searchType") != null) {
			_showAll = Boolean.parseBoolean(_request.getParameter("searchType"));
		}

		if (_request.getParameter("archive") != null) {
			_searchInArchive = Boolean.parseBoolean( _request.getParameter("archive"));
		}

		if (_request.getParameter("delno") != null) {
			_delno = (String) _request.getParameter("delno");
		}

		if (_request.getParameter("trxid") != null) {
			_trxId = (String) _request.getParameter("trxid");
		}

		if (_request.getParameter("statusTrans") != null) {
			_trxStatus = (String) _request.getParameter("statusTrans");
		}

		if (_request.getParameter("reconStatusTrans") != null) {
			_reconSatus = Boolean.parseBoolean(_request.getParameter("reconStatusTrans"));
		}

		if (_request.getParameter("fromDate") != null) {
			_fromDate = (String) _request.getParameter("fromDate");
		}

		if (_request.getParameter("toDate") != null) {
			_toDate = (String) _request.getParameter("toDate");
		}
		
		if(_request.getParameter("isSearch") != null){
			_isSearch = true;
		}
	}

	public String defaultSelected(String val){
		if(gettrxStatus() != null && gettrxStatus().equals(val)){
			return "SELECTED";
		}
		return "";
		
	}
	/**
	 * @return Returns the _delno.
	 */
	public String getdelno() {
		return _delno;
	}

	/**
	 * @return Returns the _fromDate.
	 */
	public String getfromDate() {
		return _fromDate;
	}

	/**
	 * @return Returns the _reconSatus.
	 */
	public Boolean getreconSatus() {
		return _reconSatus;
	}

	/**
	 * @return Returns the _searchInArchive.
	 */
	public Boolean getsearchInArchive() {
		return _searchInArchive;
	}

	/**
	 * @return Returns the _showAll.
	 */
	public Boolean getshowAll() {
		return _showAll;
	}

	/**
	 * @return Returns the _toDate.
	 */
	public String gettoDate() {
		return _toDate;
	}

	/**
	 * @return Returns the _trxId.
	 */
	public String gettrxId() {
		return _trxId;
	}

	/**
	 * @return Returns the _trxStatus.
	 */
	public String gettrxStatus() {
		return _trxStatus;
	}
}
