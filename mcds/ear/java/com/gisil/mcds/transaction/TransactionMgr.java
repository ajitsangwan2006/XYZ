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
package com.gisil.mcds.transaction;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.gisil.mcds.aggregator.AggregatorMgr;
import com.gisil.mcds.aggregator.mauj.Mauj;
import com.gisil.mcds.aggregator.mauj.entity.AbstractTransTrackerResponse;
import com.gisil.mcds.aggregator.mauj.entity.AbstractTransactionTO;
import com.gisil.mcds.base.IComponent;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.commission.ClassSurcharge;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.content.ContentDBMgr;
import com.gisil.mcds.content.ContentMgr;
import com.gisil.mcds.dmi.ContentDetailsForPurchaseInfo;
import com.gisil.mcds.dmi.PhoneMappingInfo;
import com.gisil.mcds.dmi.PurchaseTrxRequest;
import com.gisil.mcds.dmi.PurchaseTrxResponse;
import com.gisil.mcds.payment.PaymentProcessor;
import com.gisil.mcds.payment.PaymentProcessor.PaymentMode;
import com.gisil.mcds.util.CommonUtil;
import com.gisil.mcds.util.LogUtil;

/**
 * Manager class for all Transaction related activities
 * 
 * @author amit sachan
 */
public class TransactionMgr implements IComponent {

	private static TransactionMgr instance;

	private TrxDBMgr _trxDBMgr;

	private Logger _log;

	/**
	 * @TODO document me
	 */
	public TransactionMgr() {
		super();
	}

	public static TransactionMgr getTransactionMgr() {
		return instance;
	}

	public void preStartup() throws MCDSException {
		_log = LogUtil.getLogger(getClass());
		instance = this;
	}

	public void postStartup() throws MCDSException {
		_trxDBMgr = new TrxDBMgr();
	}

	public void shutdown() {
		instance = null;
	}

	public TrxDBMgr getTrxDBMgr() {
		return _trxDBMgr;
	}

	/**
	 * Create new Empty Transaction
	 * 
	 * @return
	 */
	public Transaction newEmptyTrx() {
		Transaction trx = new Transaction();
		// indicate system transaction.
		trx.setTrxId(Integer.valueOf(-1));
		return trx;
	}

	/**
	 * Use the current Transcation and redirect to Transport service to get
	 * response.
	 * 
	 * @param trx
	 * @param retry
	 * @return
	 * @throws MCDSException
	 */
	public Transaction doPurchase(Transaction trx) throws MCDSException {
		int retryOp = trx.getRetryAvl().intValue();
		if (retryOp > 0 && trx.getTrxId() == null)
			throw new MCDSException("retry need the trxId attribute in request");
		try {
			trx.commit();
			processPayment(trx);
			Mauj agg = AggregatorMgr.getAggregatorMgr().getAggregator(
					trx.getAggregatorId());
			agg.doPurchase(trx, (retryOp == 0 ? false : true));
			update(trx);
		} catch (MCDSException e) {
			throw e;
		} finally {
			trx.updateTransaction();
		}

		return trx; 
	//	loadLocalTransaction(trx.getTrxId());
	}

	public Transaction submitPurchaseRequest(PurchaseTrxRequest aRequest,
			Transaction trx) throws MCDSException {
		try {
			trx = createTransaction(aRequest, trx);

		} catch (MCDSException e) {
			_log.info("Error occured while creating transaction request ");
			throw e;

		}

		return trx;

	}

	private void processPayment(Transaction trx) throws MCDSException {
		PaymentProcessor authenticator = getPaymentProcessor(trx
				.getPaymentMode());
		try {
			authenticator.process(trx);
		} finally {
			trx.commit();// make the status permanent in DB.
		}
	}

	private void processRevertPayment(Transaction trx) throws MCDSException {
		PaymentProcessor authenticator = getPaymentProcessor(trx
				.getPaymentMode());
		try {
			authenticator.process(trx);
		} finally {
			trx.updateTransaction();// make the status permanent in DB.
		}
	}

	private PaymentProcessor getPaymentProcessor(PaymentMode mode) {
		if ("cash".equals(mode))
			mode = PaymentMode.CASH;

		return PaymentProcessor.getPaymentProcessor(mode);
	}

	/**
	 * Update the transaction if we got any error from Aggregator
	 * 
	 * @param trx
	 * @throws MCDSException
	 */
	private void update(Transaction trx) throws MCDSException {

		if (trx.getResponesCode() != null && trx.getResponesCode() != "00") {
			trx.setTrxType(Transaction.TYPE_REVERT);
			processRevertPayment(trx);
			// due to themis restriction and business logic
			trx.setMerchantAmt(0);
			trx.setRemarks("Automated revert -- due to error on Aggregator");
			trx.updateTransactionOps();
		}
	}

	/**
	 * Get Transaction details to print recipt
	 * 
	 * @param trxId
	 * @return
	 * @throws MCDSException
	 */
	public PurchaseTrxResponse getTransactionDetails(Integer trxId)
			throws MCDSException {
		Transaction trx = loadLocalTransaction(trxId);
		PurchaseTrxResponse response = new PurchaseTrxResponse();

		// set values in response
		response.setContent(trx.getSkuName());
		response.setDate(trx.getResponseTs());
		response.setPrice(trx.getTotalAmt().doubleValue());
		response.setSkuCode(trx.getSku());
		response.setStatus(trx.getTrxStatus());
		response.setTrxId(trx.getTrxId());
		double cmsValue = 0.0;
		
		if ( trx.getCMSValue() != null  )
		{
			cmsValue = trx.getCMSValue().doubleValue();
		}
		response.setCmsValue( cmsValue);
		response.setCmsName(trx.getCMSName());
		return response;
	}

	/**
	 * Load a transaction by providing a valid Trx ID
	 * 
	 * @param trxId
	 * @return
	 * @throws MCDSException
	 */
	public Transaction loadLocalTransaction(Integer trxId) throws MCDSException {
		TrxDBMgr dbMgr = TransactionMgr.getTransactionMgr().getTrxDBMgr();
		Transaction trx = dbMgr.loadTransaction(trxId, true);
		return trx;
	}

	private Transaction createTransaction(PurchaseTrxRequest request,
			Transaction trx) throws MCDSException {
		ContentDBMgr mgr = ContentMgr.getContentMgr().getContentDBMgr();

		trx.setDelNo(CommonUtil.fixMobileNo(request.getDelNo()));
		trx.setTrxType(Transaction.TYPE_BUYING);
		trx.setAggregatorId(request.getAgrId());
		trx.setTrxStatus(TrxStatus.NEW);
		trx.setPaymentMode(PaymentMode.CASH);
		trx.setMsisdn(CommonUtil.fixMobileNo(request.getMsisdn()));

		// hit DB and get all details.
		ContentDetailsForPurchaseInfo info = mgr.getContentDetailsByID(request
				.getContentId(), request.getType());
		if (info != null) {
			trx.setBaseAmt(info.getCost());
			trx.setSku(info.getSku());
			trx.setSkuName(info.getSkuName());
			PaymentProcessor processer = getPaymentProcessor(trx
					.getPaymentMode());
			try {
				processer.processCommission(trx);
			} catch (MCDSException e) {
				_log.info("Not able to calculate commission");
			}

		} else {
			throw new MCDSException("Error occured while creating request");
		}
		// set Retry Limit after reading from Config Param
		IConfigurationManager config = ConfigurationManagerImpl
				.getConfigurationManager();
		trx.setRetryAvl(config.getConfigAsNumber(MCDSConfig.RETRY_LIMIT)
				.intValue());

		trx.setCMSName(config.getConfig(MCDSConfig.CMS_NAME));

		return trx;
	}

	/**
	 * Get the remote Status for SUI.
	 * 
	 * @param aTransactionId
	 * @return
	 * @throws MCDSException
	 */
	public AbstractTransTrackerResponse getRemoteTransaction(
			Integer aTransactionId) throws MCDSException {
		AbstractTransTrackerResponse transTrackerResponse = null;
		AbstractTransactionTO transactionTO = new AbstractTransactionTO();
		TrxDBMgr dbMgr = TransactionMgr.getTransactionMgr().getTrxDBMgr();
		String skuCode = dbMgr.getSKUCodeByTrxId(aTransactionId);
		transactionTO.setSkuCode(skuCode);
		transactionTO.setTransId(aTransactionId.toString());
		Mauj agg = AggregatorMgr.getAggregatorMgr().getAggregator(
				dbMgr.getAggIdByTrxId(aTransactionId));
		transTrackerResponse = agg.getTransactionStatus(transactionTO);
		return transTrackerResponse;
	}

	/**
	 * 
	 * @param trxId
	 * @return
	 */
	public Boolean resendContent(Integer trxId) throws MCDSException {
		TrxDBMgr dbMgr = TransactionMgr.getTransactionMgr().getTrxDBMgr();
		Transaction trx = null;
		try {
			trx = dbMgr.loadTransaction(trxId, false);
			if ( DeliveryMode.BLUETOOTH.equals( trx.getDeliveryMode() ) ){
				throw new MCDSException( "Cannot resend trx delivered by bluetooth!" );
			}else if (TrxStatus.BUYING.equals(trx.getTrxStatus())) {
				Mauj agg = AggregatorMgr.getAggregatorMgr().getAggregator(
						trx.getAggregatorId());
				return agg.resendContent(trxId);
			} else{
				_log.info("resendContent is returning false always");
				return false;
			}
		} catch (MCDSException e) {
			throw e;
		}

	}

	/**
	 * Revert a transaction for the SUI Ops.
	 * 
	 * @param aTrx
	 * @throws MCDSException
	 */
	public void revertPayment(Transaction trx) throws MCDSException {
		trx.setTrxType(Transaction.TYPE_REVERT);
		processRevertPayment(trx);
		// A business contraint implimented here
		Number trxAmt = trx.getMerchantAmt().doubleValue()
				- (trx.getMerchantAmt().doubleValue() * 2);
		trx.setMerchantAmt(trxAmt);
		trx.updateTransactionOps();

	}

	/**
	 * Settle a transaction by the SUI Ops.
	 * 
	 * @param trx
	 * @return
	 * @throws MCDSException
	 */
	public Integer settleTransaction(Transaction trx) throws MCDSException {
		try {
			Mauj agg = AggregatorMgr.getAggregatorMgr().getAggregator(

			trx.getAggregatorId());
			agg.doPurchase(trx, true);
		} catch (MCDSException e) {
			throw e;
		} finally {
			trx.updateTransaction();
		}
		return trx.getTrxId();
	}

	/**
	 * Return the all mode of delivery
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList getDeliveryMode() throws MCDSException {
		ArrayList<String> deliveryArr = new ArrayList<String>();
		_log.info(""
				+ ConfigurationManagerImpl.getConfigurationManager()
						.getConfigAsBoolean(MCDSConfig.DELIVERY_MODE_SMS));
		if (ConfigurationManagerImpl.getConfigurationManager()
				.getConfigAsBoolean(MCDSConfig.DELIVERY_MODE_SMS))
			deliveryArr.add(DeliveryMode.SMS.toString());
		if (ConfigurationManagerImpl.getConfigurationManager()
				.getConfigAsBoolean(MCDSConfig.DELIVERY_MODE_BT))
			deliveryArr.add(DeliveryMode.BLUETOOTH.toString());
		return deliveryArr;
	}

	public Transaction newTransaction(String aMake, String aModel)
			throws MCDSException {
		ContentDBMgr mgr = ContentMgr.getContentMgr().getContentDBMgr();
		Transaction trx = new Transaction();
		// create Map
		PhoneMappingInfo map = new PhoneMappingInfo(aMake, aModel);
		// Hit db
		mgr.getPhoneCode(map);
		trx.setAggregatorPhoneCode(map.getAggPhoneCode());
		trx.setPhoneMake(map.getAggPhoneMake());
		trx.setPhoneModel(map.getPhoneModel());
		return trx;
	}

	public void shutDown() {

		_log.info("Shutting Down Transaction Manager");

	}
}
