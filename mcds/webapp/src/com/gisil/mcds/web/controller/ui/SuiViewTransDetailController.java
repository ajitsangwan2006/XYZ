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

import com.gisil.mcds.aggregator.mauj.entity.AbstractTransTrackerResponse;
import com.gisil.mcds.aggregator.mauj.entity.TransTrackerResErr;
import com.gisil.mcds.aggregator.mauj.entity.ValidTransTrackerRes;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * Services suiViewTransDetail.jsp
 * @author
 *
 */
public class SuiViewTransDetailController extends AbstractJspController
{
	private AbstractTransTrackerResponse _trackerResponse = null; 
	private ValidTransTrackerRes _validTrxResponse = null;
	private TransTrackerResErr _invalidTrxResponse = null;
	private Transaction _localTrx = null; 
	
	public SuiViewTransDetailController(HttpServletRequest aRequest, HttpServletResponse aResponse) 
	{
		super(aRequest, aResponse);
	    _trackerResponse =  ( AbstractTransTrackerResponse )aRequest.getAttribute( "trxResponse" );
	    _localTrx = ( Transaction )aRequest.getAttribute( "localTrx" );
	   // aRequest.setAttribute( "partnerCommMap", _localTrx.getTrxCommission() );
	    aRequest.getSession().setAttribute( "partnerCommMap", _localTrx.getPartnerCommissions() );
	}
	
	public boolean isValidTransResponse() 
	{
		boolean flag = false;
		
		if (_trackerResponse instanceof ValidTransTrackerRes) 
		{
			_validTrxResponse = (ValidTransTrackerRes) _trackerResponse;
			flag = true;
		}
		else
		{
			_invalidTrxResponse = ( TransTrackerResErr )_trackerResponse;
			flag = false;
		}
		
		return flag;
	}
	
	public Transaction getLocalTransactionDetail( )
	{
		return _localTrx;
	}
	public ValidTransTrackerRes getValidTransResponse( )
	{
		return _validTrxResponse;
	}
	
	public TransTrackerResErr getInvalidTransResponse()
	{
		return _invalidTrxResponse;
	}
	
	public String convertNullToBlankField( Object aValue )
	{
		String value = "";
		
		if ( aValue != null )
		{
			value = aValue.toString(); 
		}
		return value;
	}

}
