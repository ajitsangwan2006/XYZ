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
import com.gisil.mcds.web.util.ValidationException;

/**
 * Services suiMCDSMenu.jsp
 * @author
 *
 */
public class SuiMCDSMenuController extends AbstractJspController 
{
	public static final String USER_ID = "userId";

	public static final String PRIVILAGE_LEVEL = "userPrivilagesLevel";

	public static final String PAGE_PATH = "suiMCDSMenu.jsp";
	//public final boolean testFlag = true;
	public static boolean _isPrivilageLevelPresent = false;

	public SuiMCDSMenuController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) 
	{
		super(aRequest, aResponse);
		
		
	    Integer configLevel = (Integer) aRequest.getSession().getAttribute("editlevel");
	    
	    if ( configLevel != null )
	    {
	    	_isPrivilageLevelPresent = true;
	    }
	    
		String privilageLevel = null;
		String userId = null;
		
		Boolean isTestFlag = (Boolean)aRequest.getAttribute("isTestFlag" );
		
		if ( isTestFlag )
		{
			privilageLevel =  ( ( Integer )aRequest.getAttribute( PRIVILAGE_LEVEL ) ).toString();
			userId = (String)aRequest.getAttribute( USER_ID ); 
		}
		else
		{
			privilageLevel = aRequest.getParameter( PRIVILAGE_LEVEL );
			userId = aRequest.getParameter( USER_ID );
		}
		
		if ( ( privilageLevel == null || userId == null ) )
		{
			throw new ValidationException( "privilage Level and userId not found in Http Request" );
		}
		/**
		else
		{
			aRequest.getSession().setAttribute(PRIVILAGE_LEVEL, privilageLevel );
			aRequest.getSession().setAttribute( USER_ID, userId  );
		}
		*/
	}
}
