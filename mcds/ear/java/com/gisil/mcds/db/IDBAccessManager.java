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

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.base.IComponent;

/**
 * 
 * @author ashok kumar
 * 
 */
public interface IDBAccessManager extends IComponent {
	String SYS_DBACCESSMANAGER_IMPL_CLASS = "db.access.mgr.impl";

	public Connection getConnection() throws MCDSException;
}
