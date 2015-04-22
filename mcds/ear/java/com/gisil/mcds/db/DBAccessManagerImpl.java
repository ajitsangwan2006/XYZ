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
package com.gisil.mcds.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.util.JndiUtil;
import com.gisil.mcds.util.LogUtil;

public class DBAccessManagerImpl implements IDBAccessManager {
	private DataSource _dataSource;

	private static Logger _log = LogUtil.getLogger(DBAccessManagerImpl.class);

	/**
	 * Return the DB connection
	 */
	public Connection getConnection() throws MCDSException {
		final String METHOD_NAME = "getConnection(...)";
		try {

			return _dataSource.getConnection();

		} catch (SQLException sqlExp) {
			throw new MCDSException("Exception In [" + METHOD_NAME + " ] "
					+ "Unable to get the connection from datasource."
					+ sqlExp.toString());
		}

	}

	public void preStartup() throws MCDSException {
		IConfigurationManager configMgr = ConfigurationManagerImpl
				.getConfigurationManager();

		if (configMgr == null) {
			throw new MCDSException(" Configuration manager is not initialized");
		}
		_dataSource = lookUpDataSource(configMgr);

		_log.info("DBAccessManager initialized successfully.");
	}

	/**
	 * Look up the Data Source JNDI
	 * @param config
	 * @return
	 * @throws MCDSException
	 */
	protected DataSource lookUpDataSource(IConfigurationManager config)
			throws MCDSException {
		final String METHOD_NAME = "lookUpDataSource(....)";

		String dataSourceName = config.getConfig(MCDSConfig.DS_NAME);
		DataSource dataSource = (DataSource) JndiUtil
				.jndiLookUp(dataSourceName);

		if (dataSource == null) {
			throw new MCDSException("Exception in [ " + METHOD_NAME
					+ " ] Data Source Not Found ");
		}

		return dataSource;
	}

	
	/**
	 * Tests a DB connection
	 */
	public void postStartup() throws MCDSException {
		Connection conn = getConnection();
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			String testInfo = getDBInfo(metaData);
			System.out.println("Following is Test Info for DB " + testInfo);

		} catch (SQLException sqlExp) {
			throw new MCDSException("Data Base test Faild ");

		}
	}

	/**
	 * Returns the DB informations
	 * @param metaData
	 * @return
	 * @throws MCDSException
	 */
	private String getDBInfo(DatabaseMetaData metaData) throws MCDSException {
		final String METHOD_NAME = "getDBInfo(...)";
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("[");
			sb.append(metaData.getDatabaseProductName());
			sb.append(". Version = " + metaData.getDatabaseProductVersion());
			sb.append(". Driver  = " + metaData.getDriverName() + " ver = "
					+ metaData.getDriverVersion());
			sb.append(". URL     = " + metaData.getURL());
			sb.append(". User    = " + metaData.getUserName());
			sb.append("]");
		} catch (SQLException sqlExp) {
			throw new MCDSException(
					"Exception in Method [ "
							+ METHOD_NAME
							+ " ] ---- Unable to retrieve information about database -----");
		}

		return sb.toString();
	}

	public void shutDown() {
		_dataSource = null;

	}

}
