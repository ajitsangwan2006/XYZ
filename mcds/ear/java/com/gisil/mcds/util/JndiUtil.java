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
import javax.naming.NamingException;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;

public class JndiUtil {
	private static InitialContext _defaultContext;

	private static IConfigurationManager CONFIGMGR;

	private JndiUtil() {

	}

	public static void setConfig(IConfigurationManager mgr) {
		CONFIGMGR = mgr;
	}

	public static Object jndiLookUp(String name) throws MCDSException {
		return jndiLookUp(getDefaultContext(), name);
	}

	public static Object jndiLookUp(InitialContext ctx, String name)
			throws MCDSException {
		try {
			return ctx.lookup(name);
		} catch (NamingException e) {
			throw new MCDSException("Jndi Lookup failed.", e);
		}
	}

	public static InitialContext getDefaultContext() throws MCDSException {
		final String METHOD_NAME = "getDefaultContext()";

		if (_defaultContext == null) {
			synchronized (JndiUtil.class) {
				if (_defaultContext == null) {
					try {
						IConfigurationManager mgr = CONFIGMGR == null ? ConfigurationManagerImpl
								.getConfigurationManager()
								: CONFIGMGR;
						String ctxFactory = mgr
								.getConfig(MCDSConfig.JNDI_INITIAL_CONTEXT_FACTORY);
						String ctxProviderUrl = mgr
								.getConfig(MCDSConfig.JNDI_PROVIDER_URL);
						String principal = mgr
								.getConfig(MCDSConfig.JNDI_SECURITY_PRINCIPAL);
						String credentials = mgr
								.getConfig(MCDSConfig.JNDI_SECURITY_CREDENTIALS);
						Hashtable<String, String> env = new Hashtable<String, String>();
						env.put(InitialContext.INITIAL_CONTEXT_FACTORY,
								ctxFactory);
						env.put(InitialContext.PROVIDER_URL, ctxProviderUrl);
						env.put(InitialContext.SECURITY_PRINCIPAL, principal);
						env.put(InitialContext.SECURITY_CREDENTIALS,
								credentials);
						_defaultContext = new InitialContext(env);
					} catch (MCDSException icdsExp) {
						throw new MCDSException("Exception in [ " + METHOD_NAME
								+ " ]"
								+ "Unable to create the default context. "
								+ icdsExp.toString());
					} catch (Exception exp) {
						throw new MCDSException(
								"Unable to create the default context. ");
					}
				}

			}

		}
		return _defaultContext;
	}
}