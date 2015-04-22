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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DBUtil {
	public static final String ENABLED = "ENABLED";

	public static final String DISABLED = "DISABLED";

	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException sqlExp) {

		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlExp) {

			}
		}

	}

	public static void close(Statement pstmt) {
		try {
			ResultSet rs = pstmt.getResultSet();
			if (rs != null)
				rs.close();

			if (pstmt != null)
				pstmt.close();
		} catch (SQLException sqlExp) {
			// oops ignore me
		}
	}

	public static void setAutoCommitMode(Connection conn, boolean autoCommit) {
		try {
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {

		}
	}

}
