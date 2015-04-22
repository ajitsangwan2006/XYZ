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

import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;

public class ConfigValuesWrapper {

	/**
	 * Retrun the configuration value in String format
	 * 
	 * @param name
	 * @return
	 */
	public static String getValueAsString(String name) {

		IConfigurationManager mgr = ConfigurationManagerImpl
				.getConfigurationManager();
		String value = null;
		try {
			// read value
			value = mgr.getConfig(name);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return value;
	}

	/**
	 * Retrun the configuration value in Number format
	 * 
	 * @param name
	 * @return
	 */
	public static Number getValueAsNumber(String name) {

		IConfigurationManager mgr = ConfigurationManagerImpl
				.getConfigurationManager();
		Number value = null;
		try {
			// read value
			value = mgr.getConfigAsNumber(name);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return value;
	}

	/**
	 * Retrun the configuration value in Boolean format
	 * 
	 * @param name
	 * @return
	 */
	public static Boolean getValueAsBoolean(String name) {

		IConfigurationManager mgr = ConfigurationManagerImpl
				.getConfigurationManager();
		Boolean value = null;
		try {
			// read value
			value = mgr.getConfigAsBoolean(name);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return value;
	}
}
