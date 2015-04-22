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
package com.gisil.mcds.util;

import java.util.Hashtable;

import javax.naming.InitialContext;

import weblogic.jndi.WLInitialContextFactory;

/**
 * Class to test jndi
 * @author
 *
 */
public class TestJndi {

	public static void main(String args[]) {
WLInitialContextFactory.class.getName();
		Hashtable<Object,Object> hash = new Hashtable<Object,Object>();
		try {
			hash.put(InitialContext.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			hash.put(InitialContext.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ctx = new InitialContext(hash);
			ctx.lookup( "gisil.mcds.jdbc.mcds-ds" );
		} catch (Exception exp) 
		{
			System.out.println(exp.toString());
		}
		System.out.println( "Success ") ;

	}

}
