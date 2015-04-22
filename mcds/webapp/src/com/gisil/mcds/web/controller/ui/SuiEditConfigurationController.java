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
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.config.Configuration;
import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * Services suiEditConfiguration.jsp
 * @author 
 *
 */
public class SuiEditConfigurationController extends AbstractJspController 
{
	private ArrayList<Configuration> _list = null;
	private String _paramType = null;
	private String _paramValue = null;

	public SuiEditConfigurationController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		_list = (ArrayList) aRequest.getSession().getAttribute("list");

	}

	/**
	 * 
	 * @return _paramValue
	 */
	public String getParamValue()
	{
		return _paramValue;
	}
	
	/**
	 * 
	 * @return _paramType
	 */
	public String getParamType()
	{
		//System.out.println( "type " + _paramType );
		return _paramType;
	}
	
	/**
	 * 
	 * @param aRowId
	 * @return paramName
	 */
	public String getParamName(String aRowId) {
		String paramName = null;
		int rowId = Integer.parseInt(aRowId);

		Iterator iterator = _list.iterator();
		while (iterator.hasNext()) {
			Configuration cfg = (Configuration) iterator.next();

			if (cfg.getRowId() == rowId) 
			{
				paramName = cfg.getParamName();
				if (cfg.isChanged()) 
				{
					_paramValue = cfg.getParamNewValue();
				}
				else 
				{
					_paramValue = cfg.getParamValue();
				}
				_paramType = extractParamType ( cfg.getParamtype() );
				break;
			}

		}
		return paramName;
	}
	
	
	private String extractParamType( String type )
	{
		String dataType = null;
		
		if ( type != null )
		{
			int index = type.lastIndexOf( "." );
			dataType = type.substring( index + 1 );
		}
		
		return dataType;
	}
/**
	public String getParamValue(String aRowId) {
		String paramValue = null;

		int rowId = Integer.parseInt(aRowId);

		Iterator iterator = _list.iterator();
		while (iterator.hasNext()) {
			Configuration cfg = (Configuration) iterator.next();

			if (cfg.getRowId() == rowId) {
				if (cfg.isChanged()) {
					paramValue = cfg.getParamNewValue();
				} else {
					paramValue = cfg.getParamValue();
				}
				break;
			}

		}

		return paramValue;
	}
*/
	
}
