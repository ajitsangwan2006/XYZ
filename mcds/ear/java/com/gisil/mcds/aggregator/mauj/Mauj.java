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

package com.gisil.mcds.aggregator.mauj;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.gisil.mcds.aggregator.Aggregator;
import com.gisil.mcds.aggregator.AggregatorMgr;
import com.gisil.mcds.aggregator.mauj.entity.AbstractTransactionTO;
import com.gisil.mcds.aggregator.mauj.entity.Catalog;
import com.gisil.mcds.aggregator.mauj.entity.AbstractTransTrackerResponse;
import com.gisil.mcds.aggregator.mauj.entity.HandSetMapping;
import com.gisil.mcds.aggregator.mauj.entity.TransTrackerResErr;
import com.gisil.mcds.aggregator.mauj.entity.ValidTransTrackerRes;
import com.gisil.mcds.aggregator.mauj.parser.ContentCatalogParser;
import com.gisil.mcds.aggregator.mauj.parser.HandSetMappingParser;
import com.gisil.mcds.aggregator.mauj.parser.PurchaseResponseParser;
import com.gisil.mcds.aggregator.mauj.parser.TransactionTrackerParser;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.transaction.DeliveryMode;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.transaction.TrxStatus;
import com.gisil.mcds.util.EMailAlert;

/**
 * Mauj Service class for all requests
 * 
 * @author amit sachan
 * 
 */
public class Mauj extends Aggregator {

	//private String PRE_URL = null;

	private String USERNAME = null;

	private String PASSWORD = null;

	private StringBuffer _finalURL = new StringBuffer();

	private Logger _log;

	private IConfigurationManager mgr;

	/** Default Constructor */
	public Mauj() {

		_log = Logger.getLogger("Mauj Service Logger");
		mgr = ConfigurationManagerImpl.getConfigurationManager();
		try {
			_log.info("Connecting DB .......");
			IDBAccessManager dbManager = DBAccessManagerFactory
					.getDBAccessManager();

			Connection con = dbManager.getConnection();
			Statement st = con.createStatement();
			_log.info("Fetching user credentials .......");
			ResultSet rs = st
					.executeQuery("SELECT  decrypt(tuser) AS username, decrypt(tpassword)"
							+ "AS tpassword FROM aggregators WHERE aggregatorname = 'Mauj' AND status = 'ENABLED'");

			if (rs.next()) {

				USERNAME = rs.getString("username");
				PASSWORD = rs.getString("tpassword");

			} else {
				_log.info("No Record Found.......");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * Mauj with parameter values
	 * 
	 * @param preUrl
	 * @param userName
	 * @param password
	 * @param mgr
	 */
	public Mauj( String userName, String password,
			IConfigurationManager mgr) {
		USERNAME = userName;
		PASSWORD = password;
		this.mgr = mgr;
	}

	/**
	 * Get Transcation status of a already executed transcation from Indepay
	 * 
	 * @param to
	 * @return
	 * @throws MCDSException
	 */
	public AbstractTransTrackerResponse getTransactionStatus(
			AbstractTransactionTO to) throws MCDSException {

		TransactionTrackerParser parser = new TransactionTrackerParser();
		AbstractTransTrackerResponse response = null;

		try {

			_finalURL.append( mgr.getConfig(MCDSConfig.MAUJ_GET_TRANS_TRACKER_URL)	+ "?");
			_finalURL.append("usr=" + USERNAME + "&pd=" + PASSWORD);
			_finalURL.append("&sku=" + to.getSkuCode().trim());
			_finalURL.append("&posid="
					+ mgr.getConfig(MCDSConfig.MAUJ_DEFAULT_POS_ID));
			_finalURL.append("&locid="
					+ mgr.getConfig(MCDSConfig.MAUJ_DEFAULT_LOC_ID));
			_finalURL.append("&mtranid=" + to.getTransId());
			response = parser.parseTransactionTracker(_finalURL.toString());
		} catch (MCDSException e) {
			throw new MCDSException(
					"Got Error while checking Remote TRansaction Status");
		}
		return response;
	}

	/**
	 * Get the Catalog list from Mauj
	 * 
	 * @param catlogId
	 * @link String
	 * @return
	 * @link Catalog
	 */
	public Catalog getPackageList(String catlogId) {
		Catalog catalog = null;
		ContentCatalogParser parser = new ContentCatalogParser();

		try {
			_finalURL.append( mgr.getConfig(MCDSConfig.MAUJ_GET_CATALOG_URL) + "?");
			_finalURL.append("usr=" + USERNAME + "&pd=" + PASSWORD + "&ctgid="
					+ catlogId);
			_finalURL.append("&mgver="
					+ mgr.getConfig(MCDSConfig.MAUJ_DOWNLOAD_SERVER).trim());

			catalog = parser.parseContentCatalog(_finalURL.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return catalog;
	}

	/**
	 * Get HandSet Mapping from Mauj
	 * 
	 * @return
	 */
	public ArrayList<HandSetMapping> getPhoneMapping() {
		ArrayList<HandSetMapping> map = new ArrayList<HandSetMapping>();
		HandSetMappingParser parser = new HandSetMappingParser();

		try {
			_finalURL.append( mgr.getConfig(MCDSConfig.MAUJ_GET_PHONE_MAP_URL)	+ "?");
			_finalURL.append("usr=" + USERNAME + "&pd=" + PASSWORD);

			map.addAll(parser.parseHandSetMapping(_finalURL.toString()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * Send a purchase request to Mauj or check the status if the boolean shows
	 * retry.
	 * 
	 * @param trx
	 * @param retry
	 * @throws MCDSException
	 */
	public void doPurchase(Transaction trx, boolean retry) throws MCDSException {
		if (DeliveryMode.SMS.equals(trx.getDeliveryMode())) {
			 mgr = ConfigurationManagerImpl.getConfigurationManager();
			_finalURL.append(mgr.getConfig(MCDSConfig.MAUJ_GET_PURCHASE_URL) + "?");
			_finalURL.append("usr=" + USERNAME + "&pd=" + PASSWORD);
			_finalURL.append("&mo=" + trx.getMsisdn() + "&sku=" + trx.getSku()
					+ "&mk=" + trx.getPhoneMake() + "&ph="
					+ trx.getAggregatorPhoneCode());
			_finalURL.append("&posid="
					+ mgr.getConfig(MCDSConfig.MAUJ_DEFAULT_POS_ID) + "&locid="
					+ mgr.getConfig(MCDSConfig.MAUJ_DEFAULT_LOC_ID));
			_finalURL.append("&mtranid=" + trx.getTrxId());

			trx.setTrxStatus(retry ? TrxStatus.BUYING_RETRY_SUBMITTED
					: TrxStatus.BUYING_SUBMITTED);
			if (!retry) 
			{
				PurchaseResponseParser parser = null;
				try
				{
				        parser = new PurchaseResponseParser(
						_finalURL.toString());
				}
				catch( MCDSException e )
				{
					trx.setTrxStatus( retry ? TrxStatus.SERVICE_EX_RETRY : TrxStatus.SERVICE_EX );
					throw e;
				}
				
				if (parser.getResponseType() != null) {

					trx
							.setResponseTs(new Timestamp(System
									.currentTimeMillis()));
					if (parser.getResponseType().equals("SUCCESS")) {
						trx.setTrxStatus(TrxStatus.BUYING);
						trx.setResponesCode("00");
						trx.setAggregatorTrxId(parser.getMaujTransID());
						return;
					} else if (parser.getResponseType().equals("Error")) {

						trx.setTrxStatus(TrxStatus.BUYING_DENIED);
						trx.setResponesCode(parser.getErrorCode());
						return;
					}
					trx.setTrxStatus(retry ? TrxStatus.SERVICE_EX_RETRY
							: TrxStatus.SERVICE_EX);
					//update counter
					updateExpCount();
				}
			} else {
				AbstractTransTrackerResponse TO = null;
				try {
					TO = getTransactionStatus(new AbstractTransactionTO(trx
							.getTrxId().toString(), trx.getSku()));
				} catch (Exception e) {
					trx.setTrxStatus(retry ? TrxStatus.SERVICE_EX_RETRY
							: TrxStatus.SERVICE_EX);
					updateExpCount();
				}
				if (TO instanceof ValidTransTrackerRes) {
					ValidTransTrackerRes validTrans = (ValidTransTrackerRes) TO;
					trx
							.setResponseTs(new Timestamp(System
									.currentTimeMillis()));
					trx.setAggregatorTrxId(Integer.parseInt(validTrans
							.getTransId()));
					if ("SUCCESS".equals(validTrans.getTransStatus())) {
						trx.setTrxStatus(TrxStatus.BUYING);
						trx.setResponesCode("00");
					} else {
						trx.setTrxStatus(TrxStatus.BUYING_DENIED);
						trx.setResponesCode(validTrans.getErrorCode());
					}
				} else if (TO instanceof TransTrackerResErr) {
					throw new MCDSException(
							"No Such Transcation created with Aggregator Looks a REVERT CASE");
				}

			}
		} else if (DeliveryMode.BLUETOOTH.equals(trx.getDeliveryMode())) {
			// call the new API
			/**
			 * set to static successfull till the API arrives
			 */
			trx
			.setResponseTs(new Timestamp(System
					.currentTimeMillis()));
			trx.setTrxStatus(TrxStatus.BUYING);
			trx.setResponesCode("00");
		}

	}
	/**
	 * Service Method to resend Content to Customer Mobile
	 * 
	 * @param trxId
	 * @return
	 */
	public Boolean resendContent(Integer trxId) throws MCDSException {
		Boolean res = null;
		mgr = ConfigurationManagerImpl.getConfigurationManager();
		_finalURL.append( mgr.getConfig(MCDSConfig.MAUJ_GET_RESEND_URL)	+ "?");
		_finalURL.append("usr=" + USERNAME + "&pd=" + PASSWORD);
		_finalURL.append("&mtranid=" + trxId);
		
		PurchaseResponseParser parser = new PurchaseResponseParser(
				_finalURL.toString());

		if (parser.getResponseType() != null) {
			if (parser.getResponseType().equals("SUCCESS")) {				
				res = true;
			} else {
				res = false;
			}
		}else{
			throw new MCDSException("NO Response recieved from Mauj");
		}
		
		return res;
	}
	

	public String getPASSWORD() {
		return PASSWORD;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public IConfigurationManager getMgr() {
		return mgr;
	}

	public void setMgr(IConfigurationManager mgr) {
		this.mgr = mgr;
	}
	
	private void updateExpCount()throws MCDSException{
		Integer count = AggregatorMgr.getAggregatorMgr().getSerExpCount();
		if(count == mgr.getConfigAsNumber(MCDSConfig.MAX_SERVICE_EXP_COUNT).intValue()){
			EMailAlert.sendTransportFailureAlert(1);
			AggregatorMgr.getAggregatorMgr().setSerExpCount(0);
		}else{
			AggregatorMgr.getAggregatorMgr().setSerExpCount(count + 1);
		}
	}
}
