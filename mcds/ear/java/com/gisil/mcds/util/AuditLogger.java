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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.gisil.mcds.audit.AuditTrail;

import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;

public class AuditLogger {

	private Logger _log;

	private DataSource _dataSource;

	/**
	 * @TODO document me
	 */
	public AuditLogger() {
		_log = LogUtil.getLogger(AuditLogger.class);
	}

	/**
	 * Log trail
	 * @param trail
	 */
	public void logTrail(AuditTrail trail) {
		if (LogUtil.TEST_FLAG)
			return;
		if (trail == null)
			return;
		Connection conn = null;
		try {
			init();
			if (_dataSource != null) {
				conn = _dataSource.getConnection();

				// base on the structure provided by Naveen Kumar
				PreparedStatement pstmt = conn
						.prepareStatement("INSERT INTO AuditTrail "
								+ "( USERID ,ACTIONID ,ADDITIONALINFO1 ,"
								+ "ADDITIONALINFO2 ,ADDITIONALINFO3 ,"
								+ "ADDITIONALINFO4, ADDITIONALINFO5, IPADDRESS )"
								+ " VALUES ( ? ,? ,? ,? ,? ,? ,? ,? )");
				int i = 1;
				pstmt.setString(i++, trail.getUserId());
				pstmt.setString(i++, String.valueOf(trail.getActionId()));
				pstmt.setString(i++, trail.getAdditionalInfo1());
				pstmt.setString(i++, trail.getAdditionalInfo2());
				pstmt.setString(i++, trail.getAdditionalInfo3());
				pstmt.setString(i++, trail.getAdditionalInfo4());
				pstmt.setString(i++, trail.getAdditionalInfo5());
				pstmt.setString(i++, trail.getIpAddress());

				int count = pstmt.executeUpdate();
				if (count == 1)
					_log.finest("Audit Log inserted # " + count);

				DBUtil.close(pstmt);

			}
		} catch (Throwable e) {
			_log.log(Level.SEVERE, "error while submitting audit trail. ", e);
		} finally {
			DBUtil.close(conn);
		}
	}

	private void init() throws Exception {
		if (_dataSource == null) {
			IConfigurationManager config = ConfigurationManagerImpl
					.getConfigurationManager();
			String name = config.getConfig(MCDSConfig.DS_AUDIT_TRAIL);

			_dataSource = (DataSource) JndiUtil.jndiLookUp(name);
		}
	}

}
