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
package com.gisil.mcds.commission;

import java.util.HashMap;
import java.util.Map;

import com.gisil.mcds.base.MCDSException;

/**
 * The class<code>CommContext</code>is the form of<code>IContext</code>
 * indicates to initializing the commission context
 * 
 * @author sandeep kadyan
 */
class CommContext implements IContext {

	/**
	 * Context instance
	 */
	private Map<String, Object> context;

	/**
	 * Constructs commission context
	 */
	public CommContext() {
		context = new HashMap<String, Object>();
	}

	/**
	 * @return object context
	 */
	public Object getValue(String ctx) throws MCDSException {
		Object o = context.get(ctx);
		if (o == null /* && !context.containsKey(ctx) */)
			throw new MCDSException(
					"context not initialzed properly. Missing {" + ctx + "}");
		return o;
	}

	/**
	 * Set the context cant be null
	 * 
	 * @return Context
	 */
	public Object setContext(String key, Object val) throws MCDSException {
		if (val == null)
			throw new MCDSException("context cannot be set to null.");
		return context.put(key, val);
	}

}
