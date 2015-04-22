package com.gisil.mcds.web.controller.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;

public class SuiSearchTrxResultController extends AbstractJspController {

	public static final String PAGE_PATH = "suiSearchTrxResult.jsp";
	
	private List _trxList = null;
	
	private String _queryString = null;
			
	public SuiSearchTrxResultController (HttpServletRequest aRequest, HttpServletResponse aResponse)
	{
		super(aRequest,aResponse);
		
		loadData();
	}
	
	/**
	 * 
	 * 
	 */
	private void loadData() {
		// get all the data if in the request
		if (_request.getAttribute("trxList") != null) 
		{
			_trxList = (List) _request.getAttribute("trxList");
			_request.getSession().setAttribute( "trxList", _trxList );
		}
		setQueryString(SuiSearchTrxController.PAGE_PATH+"?"+(String)_request.getAttribute("queryString"));
		
	}

	/**
	 * @return Returns the _trxList.
	 */
	public List gettrxList() {
		return _trxList;
	}

	public String showExportToExcel(){
		if(_trxList == null || _trxList.isEmpty()){
			return "DISABLED";
		}else{
			return "";
		}
	}

	public String getQueryString() {
		return _queryString;
	}

	public void setQueryString(String aQueryString) {
		this._queryString = aQueryString;
	}
}
