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

package com.gisil.mcds.web.controller.wap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.Utils;

/**
 * Services error.jsp
 * @author
 *
 */
public class ErrorController extends AbstractJspController
{
	public static final String PAGE_PATH = "error.jsp";
	public static final String DEFAULT_PAGE_TITLE = "Error page";
	
	public String _errorMessage = null;
	public String _backPageurl = null;
	public String _contentId = null;
	private String _pageTitle = null;
	
	public ErrorController(HttpServletRequest aRequest, HttpServletResponse aResponse) {
		super(aRequest, aResponse);
	
		String message = (String )aRequest.getAttribute( "errorMessage" );
		String pageTitle = ( String )aRequest.getAttribute( "pageTitle");
		
		if ( message == null )
		{
			message = ErrorMessages.UNDEFINED_ERROR;
		}
		
		if ( pageTitle != null && !pageTitle.equals("" ) )
		{
			_pageTitle = pageTitle;
		}
		else
		{
			_pageTitle = DEFAULT_PAGE_TITLE;
		}
		_backPageurl = ( String )aRequest.getAttribute( "backUrl" );
		if ( _backPageurl == null )
		{
			_backPageurl = "mcdsmenu";
		}
		_contentId = (String)aRequest.getAttribute("contentId");
		_errorMessage = Utils.convertToDisplayString( message );
	}
	
	public String getPageTitle()
	{
		return _pageTitle;
	}

}
