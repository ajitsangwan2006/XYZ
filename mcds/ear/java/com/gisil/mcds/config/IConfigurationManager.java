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
package com.gisil.mcds.config;

import java.util.Collection;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.base.IComponent;

public interface IConfigurationManager extends IComponent {
	public String VAS_HOME = "VAS_HOME";

	public String getConfig(String key) throws MCDSException;

	public Number getConfigAsNumber(String key) throws MCDSException;

	public boolean getConfigAsBoolean(String key) throws MCDSException;

	public void reload() throws MCDSException;

	public void flush() throws MCDSException;

	public void setConfig(String key, String value) throws MCDSException;

	public void applyChanges(Collection<ConfigInfo> configInfo)
			throws MCDSException;

	public Collection<ConfigInfo> getConfiguration() throws MCDSException;

	public void updateConfiguration(Collection<ConfigInfo> configInfo)
			throws MCDSException;

}
