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

package com.gisil.mcds.web.controller.wap;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;

/**
 * Tests Db connection
 * @author Ashok Kumar
 *
 */
public class DBConnectionTestController {

	/**
	 * DBConnectionTestController
	 * @param req
	 * @param rsp
	 */
	public DBConnectionTestController(HttpServletRequest req,
			HttpServletResponse rsp) {
		try {
			IDBAccessManager dbManager = DBAccessManagerFactory
					.getDBAccessManager();
			Connection conn = dbManager.getConnection();

			if (conn != null) {
				req.setAttribute("message",
						"Got the DB Connection Successfully ");
			} else {
				req.setAttribute("message",
						"Something Happen Wrong, See the Log ");
			}
		} catch (MCDSException mcdsExp) {
			System.out.println(mcdsExp.toString());
		}

	}

}
