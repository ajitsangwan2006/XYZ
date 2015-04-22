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

package com.gisil.mcds.dmi;

import java.io.Serializable;

/**
 * Represents Transaction Summary
 * @author
 *
 */
public class TransactionSummaryInfo implements Serializable 
{

	/**
	 *  auto generated serial version ID
	 */
	private static final long serialVersionUID = 851248156892802724L;
	
	private long  _transactionId = 0;
	
	private String _transactionDate = null;
	
	private String _responseCode = null;
	
	public TransactionSummaryInfo()
	{
		
	}
	
	public void setTransactionId( long id )
	{
		_transactionId = id;
	}
	
	public long getTransactionId()
	{
		return _transactionId;
	}

	public void setTransactionDate( String date )
	{
		_transactionDate = date;
	}
	
	public String getTransactionDate()
	{
		return _transactionDate;
	}
	
	public void setTransactionResponseCode( String status )
	{
		_responseCode = status;
	}
	
	public String getTransactionResponseCode()
	{
		return _responseCode;
	}
	
}
