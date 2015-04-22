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
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletContext;
import com.gisil.mcds.ejb.MCDSHome;
import com.gisil.mcds.ejb.MCDSRemote;

/**
 * 
 * @author ajit singh
 *
 */
public class AppServer {
	private static AppServer appserver = new AppServer();

	private static ServletContext _config = null;

	/**
	 * getAppServer
	 * @param aConfig
	 * @return
	 */
	public static AppServer getAppServer(ServletContext aConfig) {
		_config = aConfig;
		return appserver;
	}

	/**
	 * getMCDSServer
	 * @return
	 */
	public MCDSRemote getMCDSServer() {

		MCDSRemote remote = lookkupUsingJndi();

		return remote;
	}

	/**
	 * lookkup remote using Jndi name
	 * @return
	 */
	public MCDSRemote lookkupUsingJndi() {
		try {
			String ctxFactory = _config
					.getInitParameter("INITIAL_CONTEXT_FACTORY");
			String ctxProviderUrl = _config.getInitParameter("PROVIDER_URL");
			String principal = _config.getInitParameter("SECURITY_PRINCIPAL");
			String credentials = _config
					.getInitParameter("SECURITY_CREDENTIALS");
			String name = _config.getInitParameter("jndi_name");
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(InitialContext.INITIAL_CONTEXT_FACTORY, ctxFactory);
			env.put(InitialContext.PROVIDER_URL, ctxProviderUrl);
			env.put(InitialContext.SECURITY_PRINCIPAL, principal);
			env.put(InitialContext.SECURITY_CREDENTIALS, credentials);

			InitialContext _defaultContext = new InitialContext(env);
			Object oo = _defaultContext.lookup(name);
			MCDSHome home = (MCDSHome) PortableRemoteObject.narrow(oo,
					MCDSHome.class);

			return home.create();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to lookup the App Server ", e);
		}
	}
}
