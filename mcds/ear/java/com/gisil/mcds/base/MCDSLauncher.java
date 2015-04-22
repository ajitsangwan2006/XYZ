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
package com.gisil.mcds.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.content.ContentMgr;
import com.gisil.mcds.aggregator.AggregatorMgr;
import com.gisil.mcds.commission.CommissionProcessor;
import com.gisil.mcds.transaction.TransactionMgr;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.util.LogUtil;

public class MCDSLauncher {
	private static Logger _log;

	private static MCDSLauncher LAUNCHER = null;

	private boolean _started;

	/**
	 * 
	 *MCDSLauncher
	 */
	public MCDSLauncher() {
		_log = LogUtil.getLogger(MCDSLauncher.class);
	}

	/**
	 * This method load startup properties
	 * @param aconfigFile
	 * @return
	 * @throws MCDSException
	 */
	private Properties loadStartupProperties(String aconfigFile)
			throws MCDSException {
		final String METHOD_NAME = "loadStartupProperties(....)";

		Properties startupProps = new Properties();

		try {
			File file = new File(aconfigFile);
			InputStream inputStream = null;
			if (!file.exists()) {
				inputStream = getClass().getResourceAsStream(aconfigFile);

				if (inputStream == null)
					throw new MCDSException(
							" Default Properties File Not Found [ "
									+ aconfigFile + " ]");

			} else {
				_log
						.info(" -going to load from file "
								+ file.getAbsolutePath());
				inputStream = new FileInputStream(file);
			}

			startupProps.load(inputStream);
		} catch (IOException ioExp) {
			String msg = "io exception occur while loading default properties in Method "
					+ METHOD_NAME;
			_log.info(msg);
			throw new MCDSException(msg);
		}

		return startupProps;
	}

	/**
	 * This method launch the config file
	 * @param aconfigFile
	 * @throws MCDSException
	 */
	public synchronized void launch(String aconfigFile) throws MCDSException {
		if (_started) {
			_log.info("Already Started");
			return;
		}

		/* 
		 *To- Do Later  Call here ( ConfigureLog( ) )
		 */
		Properties defaultProperties = loadStartupProperties(aconfigFile);

		_log.info("MCDS  system is initializing....");
		IConfigurationManager configMgr = new ConfigurationManagerImpl(
				defaultProperties);
		configMgr.preStartup();
		IDBAccessManager dbManager = DBAccessManagerFactory
				.getDBAccessManager();
		dbManager.preStartup();
		dbManager.postStartup();
		configMgr.postStartup();

		TransactionMgr trxMgr = new TransactionMgr();
		AggregatorMgr aggregatorMgr = new AggregatorMgr();
		ContentMgr contentMgr = new ContentMgr();
		CommissionProcessor commissionProcessor = new CommissionProcessor();

		aggregatorMgr.preStartup();
		aggregatorMgr.postStartup();

		commissionProcessor.preStartup();
		commissionProcessor.postStartup();

		trxMgr.preStartup();
		trxMgr.postStartup();

		contentMgr.preStartup();
		contentMgr.postStartup();

		_log.info("MCDS system initialization done.");
	}

	/**
	 * start
	 * @throws MCDSException
	 */
	public void start() throws MCDSException {
		launch("mcds.config");
	}

	/**
	 * getLauncher
	 * @return
	 */
	public static MCDSLauncher getLauncher() {
		if (LAUNCHER == null) {
			LAUNCHER = new MCDSLauncher();
		}

		return LAUNCHER;

	}

	public static void main(String args[]) throws Exception {
		MCDSLauncher launcher = getLauncher();
		launcher.start();
		synchronized (launcher) {
			launcher.wait();
		}

	}

	/**
	 * shutdown
	 * @throws MCDSException
	 */
	public synchronized void shutdown() throws MCDSException {
		if (!_started) {
			_log.info("MCDS is not running...");
		}
		TransactionMgr mgr = TransactionMgr.getTransactionMgr();
		CommissionProcessor processor = CommissionProcessor.getInstance();
		AggregatorMgr aggMgr = AggregatorMgr.getAggregatorMgr();
		IConfigurationManager config = ConfigurationManagerImpl
				.getConfigurationManager();
		IDBAccessManager dbAccessMgr = DBAccessManagerFactory
				.getDBAccessManager();
		ContentMgr contentMgr = ContentMgr.getContentMgr();
		mgr.shutdown();
		processor.shutdown();

		aggMgr.shutDown();
		config.shutDown();
		dbAccessMgr.shutDown();
		contentMgr.shutDown();

		_started = false;
		_log.info("MCDS system shutdown successfully.");
	}

}
