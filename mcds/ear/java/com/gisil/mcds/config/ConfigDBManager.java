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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.util.LogUtil;

public class ConfigDBManager {
	private Logger _log = null;

	public static final String TABLE_NAME = "configuration";

	public static final String SELECT_ALL = "SELECT * FROM";

	/**
	 * 
	 *ConfigDBManager
	 */
	public ConfigDBManager() {
		_log = LogUtil.getLogger(ConfigDBManager.class);
	}

	/**
	 * This method returns the configurations
	 * @return
	 * @throws MCDSException
	 */
	public Collection<ConfigDef> getConfigurations() throws MCDSException {
		final String METHOD_NAME = "getConfiguration(...)";
		ResultSet rs = null;
		Statement stmt = null;

		Collection<ConfigDef> configuration = new ArrayList<ConfigDef>();
		Connection conn = null;
		try {
			IDBAccessManager dbManager = DBAccessManagerFactory
					.getDBAccessManager();
			conn = dbManager.getConnection();
			stmt = conn.createStatement();
			String sql = buildSelectAllQuery();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				ConfigDef info = loadConfigInfo(rs);
				configuration.add(info);
			}
		} catch (SQLException sqlExp) {
			String msg = "SQL Exception occured in method " + METHOD_NAME
					+ " [ " + sqlExp.toString() + " ]";
			_log.info(msg);
			throw new MCDSException(msg);
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(rs);
			DBUtil.close(conn);
		}

		return configuration;
	}

	/**
	 * This method make the selection queries
	 * @return
	 */
	private String buildSelectAllQuery() {
		StringBuffer buff = new StringBuffer();
		buff.append(SELECT_ALL);
		buff.append(" " + TABLE_NAME + " ");
		return buff.toString();
	}

	/**
	 * This method loads the configuration informations
	 * @param rs
	 * @return
	 * @throws MCDSException
	 */
	private ConfigDef loadConfigInfo(ResultSet rs) throws MCDSException {
		final String METHOD_NAME = "loadConfigInfo(...)";
		ConfigDef configurationInfo = new ConfigDef();
		try {
			int paramID = rs.getInt("ID");
			String paramName = rs.getString("PARAMNAME");
			String paramCategory = rs.getString("PARAMCATEGORY");
			String paramValue = rs.getString("PARAMVALUE");
			String description = rs.getString("DESCRIPTION");
			String paramType = rs.getString("PARAMTYPE");
			int floating = rs.getInt("ISFLOAT");
			int minValue = rs.getInt("MINVALUE");
			int maxValue = rs.getInt("MAXVALUE");
			String enumValues = rs.getString("ENUMVALUES");
			String status = rs.getString("STATUS");

			configurationInfo.setParamId(paramID);
			configurationInfo.setParamCategory(paramCategory);
			configurationInfo.setParamName(paramName);
			configurationInfo.setParamValue(paramValue);
			configurationInfo.setDescription(description);
			configurationInfo.setParamType(paramType + "");
			configurationInfo.setFloating(floating);
			configurationInfo.setMinValue(minValue);
			configurationInfo.setMaxValue(maxValue);
			configurationInfo.setEnumvalues(enumValues);
			configurationInfo.setStatus(status);
			configurationInfo.setDirty(0);
			return configurationInfo;

		} catch (SQLException sqlExp) {
			throw new MCDSException(
					" Exception in Method [ "
							+ METHOD_NAME
							+ " ] Unable to extract ICDSConfiguration information from result set"
							+ sqlExp.toString());
		}
	}

	/**
	 * This method updates the configurations in table
	 * @param configuration
	 * @throws MCDSException
	 */
	public void updateConfig(Collection<ConfigDef> configuration)
			throws MCDSException {
		final String METHOD_NAME = "updateConfig(...)";
		if (configuration == null || configuration.size() < 1) {
			return;
		}

		Connection conn = null;
		try {
			IDBAccessManager dbManager = DBAccessManagerFactory
					.getDBAccessManager();
			conn = dbManager.getConnection();
			boolean autoCommit = conn.getAutoCommit();

			if (autoCommit) {
				conn.setAutoCommit(false);
			}

			String updateQuery = buildUpdateQuery();

			PreparedStatement pstmt = conn.prepareStatement(updateQuery);
			boolean executeBatch = false;
			for (ConfigDef def : configuration) {
				if (def.isDirty() && def.getParamId() != -1) {
					pstmt.setString(1, def.getParamValue());
					pstmt.setInt(2, def.getParamId());
					pstmt.addBatch();
					executeBatch = true;
					def.setDirty(0);
				}

			}

			if (executeBatch)
				pstmt.executeBatch();

			conn.commit();

			if (autoCommit)
				conn.setAutoCommit(autoCommit);

		} catch (SQLException sqlExp) {
			String msg = METHOD_NAME
					+ "DB Access Exception during updating of configuration";
			_log.info(msg);
			throw new MCDSException(msg);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * This method helps to complete the update query
	 * @return
	 */
	private String buildUpdateQuery() {
		StringBuffer buff = new StringBuffer();
		buff.append("UPDATE ");
		buff.append(TABLE_NAME);
		buff.append(" SET PARAMVALUE = ? ");
		buff.append(" WHERE ID = ?");
		return buff.toString();
	}

}
