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
package com.gisil.mcds.script;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import com.gisil.mcds.aggregator.mauj.Mauj;
import com.gisil.mcds.aggregator.mauj.entity.HandSetMapping;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.base.MCDSLauncher;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.util.EMailAlert;

/**
 * 
 * @author amit sachan
 * 
 */
public class PhoneMappingJob {

	private MCDSLauncher m_launcher = null;

	private static Logger _log;
	
	private StringBuffer _message = new StringBuffer();
    
    IConfigurationManager mgr = null;

	private void init() {

		_log = Logger.getLogger("MCDS Logger");
		_log.info("Initializing mcds .......");

		try {

			m_launcher = MCDSLauncher.getLauncher();
			m_launcher.launch("mcds.config");

			_log.info("MCDS Initialization Complete ....");
		} catch (Throwable t) {
			t.printStackTrace();
			_log.severe("Exception occured while initialzing MCDS .....");
		}

	}

	/**
	 * execute the job
	 * 
	 */
	private void executeJob() {
		_log.info("Initializing Content Update Job .......");
		
		Connection con = null; 
		
		try {
			IDBAccessManager dbManager = DBAccessManagerFactory
					.getDBAccessManager();
			con = dbManager.getConnection();
			Statement st = con.createStatement();

			Mauj maujService = new Mauj();
			ArrayList<HandSetMapping> maps = maujService.getPhoneMapping();
			if (!maps.isEmpty()) {
				if (mgr.getConfigAsBoolean(MCDSConfig.MAUJ_CONTENT_OVERRIDE)) {
					st
							.executeUpdate("UPDATE phone_mapping SET status = 'DISABLED'");
					_log.info("Setting Old Mapping DISABLED.......");
					
					_message.append('\n').append("Setted Old Phone Mapping DISABLED").append('\n');
				}
				feedDatabase(maps);

			} else {
				_log.info("No Response from Mauj.......");
				_message.append('\n').append("No Response from Mauj");
			}

			DBUtil.close(st);

		} catch (Throwable t) {
			t.printStackTrace();
			_log.severe("Exception occured while processing .....");
		}
		finally{
			DBUtil.close(con);
		}
	}

	/**
	 * load the db
	 * 
	 * @param maps
	 */
	private void feedDatabase(ArrayList<HandSetMapping> maps) {
		Connection con = null;
		try {

			Iterator iterator = maps.iterator();
			IDBAccessManager dbManager = DBAccessManagerFactory
					.getDBAccessManager();
			con = dbManager.getConnection();
			PreparedStatement insertMap = con
					.prepareStatement("INSERT INTO phone_mapping(type,aggregatorcode,aggregatormake,aggregatormodel) VALUES(?,?,?,?)");

			while (iterator.hasNext()) {
				HandSetMapping map = (HandSetMapping) iterator.next();
				// Insert in Phone_mapping
				insertMap.setString(1, map.getServiceType());
				insertMap.setString(2, map.getAggCode());
				insertMap.setString(3, map.getAggMake());
				insertMap.setString(4, map.getAggModel());
				insertMap.executeUpdate();

				_log.info("[" + map.getAggCode() + "]");
				_message.append('\n').append("Added Phone Mapping for code : ").append("[" + map.getAggCode() + "]");

			}
			DBUtil.close(insertMap);

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			DBUtil.close(con);
		}

	}

	private void destroy() throws MCDSException {
		EMailAlert.sendMailForDownloadContent("Phone Mapping Download Job", _message.toString());
		m_launcher.shutdown();
	}

	public void refreshPhoneMapping(IConfigurationManager configMgr)
	{
		PhoneMappingJob job = new PhoneMappingJob();
         this.mgr = mgr;
        instantiateLog();
		job.executeJob();
	}
	
	public static void main(String[] args) {

		PhoneMappingJob job = new PhoneMappingJob();
		// Launch the application to get db connection
		job.init();

		// use connection and send request to get details and insert into table
		job.executeJob();

		// Close Connection and all
		try {
			job.destroy();
		} catch (Exception e) {
			_log.info("Failed to ShutDown all resources");
			e.printStackTrace();
		}
	}
    /**
     * instantiate log
     */
    private void instantiateLog(){
        _log = Logger.getLogger("MCDS Logger");
        _log.info("Initializing mcds .......");
    }

}
