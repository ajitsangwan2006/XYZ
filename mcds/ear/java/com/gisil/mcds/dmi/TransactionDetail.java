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

import com.gisil.mcds.aggregator.mauj.entity.AbstractTransTrackerResponse;
import com.gisil.mcds.transaction.Transaction;

public class TransactionDetail implements Serializable
{

	/**serial Version UID	 */
	private static final long serialVersionUID = -3503912912061551635L;
	
	private Transaction _transaction = null;
	private AbstractTransTrackerResponse _transTrackerResponse = null;
	
	public Transaction getTransaction()
	{
		return _transaction;
	}
	public void setTransaction(Transaction _transaction) 
	{
		this._transaction = _transaction;
	}
	
	public AbstractTransTrackerResponse getTransTrackerResponse() 
	{
		return _transTrackerResponse;
	}
	
	public void setTransTrackerResponse
	(
			AbstractTransTrackerResponse trackerResponse) 
	{
		_transTrackerResponse = trackerResponse;
	}
	
}
