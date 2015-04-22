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

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.payment.PaymentProcessor.PaymentMode;

/**
 * @TODO document me
 * @author Amit Sachan
 */
public class Transaction implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2611280031898210150L;

	public static String TYPE_BUYING = "BUYING";

	public static String TYPE_REVERT = "REVERT";

	private Integer trxId;

	private TrxStatus trxStatus;

	private Integer aggregatorId;

	private String trxType;

	private Number baseAmt;

	private String delNo;

	private Integer merchantId;

	private Map<Integer, Number> partnerCommissions;

	private Number commissionAmt;

	private Number surchargeAmt;

	private Number totalAmt;

	private Integer parentTrxId;

	private String reconStatus;

	private boolean refunded;

	private String msisdn;

	private String phoneMake;

	private String phoneModel;

	private String sku;

	private String skuName;

	private String responesCode;

	private String responseMessage;

	private String aggregatorPhoneCode;

	private String remarks;

	private Number merchantAmt;

	private Number merchantBalance;

	private PaymentMode paymentMode;

	private Timestamp RequestTs;

	private Timestamp ResponseTs;

	private Integer commFormulaId;

	private Transaction _parentTrx;

	private Integer aggregatorTrxId;

	private Timestamp tsCreated;

	private Timestamp tsUpdated;

	private DeliveryMode deliveryMode;

	/** Configrable Name for CMS */
	private String cmsName;

	/** Check and store Retry Limit from here */
	private Integer retryAvl;

	/**
	 * @return Returns the retryAvl.
	 */
	public Integer getRetryAvl() {
		return retryAvl;
	}

	/**
	 * @param retryAvl
	 *            The retryAvl to set.
	 */
	public void setRetryAvl(Integer retryAvl) {
		this.retryAvl = retryAvl;
	}

	public Transaction() {
	}

	/**
	 * @TODO document me
	 * @return Returns the aggregatorId.
	 */
	public Integer getAggregatorId() {
		return aggregatorId;
	}

	/**
	 * @TODO document me
	 * @param aggregatorId
	 *            The aggregatorId to set.
	 */
	public void setAggregatorId(Integer aggregatorId) {
		this.aggregatorId = aggregatorId;
	}

	public void commit() throws MCDSException {
		TrxDBMgr dbMgr = TransactionMgr.getTransactionMgr().getTrxDBMgr();
		if (trxId == null) {
			this.trxId = dbMgr.createTrxId();
		} else {
			dbMgr.createTrx(this);
		}
	}

	public void updateTransaction() throws MCDSException {
		TrxDBMgr dbMgr = TransactionMgr.getTransactionMgr().getTrxDBMgr();
		dbMgr.updateTrx(this);
	}

	public void updateTransactionOps() throws MCDSException {
		TrxDBMgr dbMgr = TransactionMgr.getTransactionMgr().getTrxDBMgr();
		dbMgr.updateTrxOps(this);
	}

	/**
	 * @return Returns the _parentTrx.
	 */
	public Transaction get_parentTrx() {
		return _parentTrx;
	}

	/**
	 * @return Returns the aggregatorPhoneCode.
	 */
	public String getAggregatorPhoneCode() {
		return aggregatorPhoneCode;
	}

	/**
	 * @return Returns the aggregatorTrxId.
	 */
	public Integer getAggregatorTrxId() {
		return aggregatorTrxId;
	}

	/**
	 * @return Returns the baseAmt.
	 */
	public Number getBaseAmt() {
		return baseAmt;
	}

	/**
	 * @return Returns the commFormulaId.
	 */
	public Integer getCommFormulaId() {
		return commFormulaId;
	}

	/**
	 * @return Returns the commissionAmt.
	 */
	public Number getCommissionAmt() {
		return commissionAmt;
	}

	/**
	 * @return Returns the delNo.
	 */
	public String getDelNo() {
		return delNo;
	}

	/**
	 * @return Returns the merchantAmt.
	 */
	public Number getMerchantAmt() {
		return merchantAmt;
	}

	/**
	 * @return Returns the merchantBalance.
	 */
	public Number getMerchantBalance() {
		return merchantBalance;
	}

	/**
	 * @return Returns the merchantId.
	 */
	public Integer getMerchantId() {
		return merchantId;
	}

	/**
	 * @return Returns the msisdn.
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @return Returns the parentTrxId.
	 */
	public Integer getParentTrxId() {
		return parentTrxId;
	}

	/**
	 * @return Returns the partnerCommissions.
	 */
	public Map<Integer, Number> getPartnerCommissions() {
		return partnerCommissions;
	}

	/**
	 * @return Returns the paymentMode.
	 */
	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @return Returns the phoneMake.
	 */
	public String getPhoneMake() {
		return phoneMake;
	}

	/**
	 * @return Returns the reconStatus.
	 */
	public String getReconStatus() {
		return reconStatus;
	}

	/**
	 * @return Returns the refunded.
	 */
	public boolean isRefunded() {
		return refunded;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return Returns the requestTs.
	 */
	public Timestamp getRequestTs() {
		return RequestTs;
	}

	/**
	 * @return Returns the responesCode.
	 */
	public String getResponesCode() {
		return responesCode;
	}

	/**
	 * @return Returns the responseMessage.
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @return Returns the responseTs.
	 */
	public Timestamp getResponseTs() {
		return ResponseTs;
	}

	/**
	 * @return Returns the sku.
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * @return Returns the skuName.
	 */
	public String getSkuName() {
		return skuName;
	}

	/**
	 * @return Returns the surchargeAmt.
	 */
	public Number getSurchargeAmt() {
		return surchargeAmt;
	}

	/**
	 * @return Returns the totalAmt.
	 */
	public Number getTotalAmt() {
		return totalAmt;
	}

	/**
	 * @return Returns the trxId.
	 */
	public Integer getTrxId() {
		return trxId;
	}

	/**
	 * @return Returns the trxStatus.
	 */
	public TrxStatus getTrxStatus() {
		return trxStatus;
	}

	/**
	 * @return Returns the trxType.
	 */
	public String getTrxType() {
		return trxType;
	}

	/**
	 * @param trx
	 *            The _parentTrx to set.
	 */
	public void set_parentTrx(Transaction trx) {
		_parentTrx = trx;
	}

	/**
	 * @param aggregatorPhoneCode
	 *            The aggregatorPhoneCode to set.
	 */
	public void setAggregatorPhoneCode(String aggregatorPhoneCode) {
		this.aggregatorPhoneCode = aggregatorPhoneCode;
	}

	/**
	 * @param aggregatorTrxId
	 *            The aggregatorTrxId to set.
	 */
	public void setAggregatorTrxId(Integer aggregatorTrxId) {
		this.aggregatorTrxId = aggregatorTrxId;
	}

	/**
	 * @param baseAmt
	 *            The baseAmt to set.
	 */
	public void setBaseAmt(Number baseAmt) {
		this.baseAmt = baseAmt;
	}

	/**
	 * @param commFormulaId
	 *            The commFormulaId to set.
	 */
	public void setCommFormulaId(Integer commFormulaId) {
		this.commFormulaId = commFormulaId;
	}

	/**
	 * @param commissionAmt
	 *            The commissionAmt to set.
	 */
	public void setCommissionAmt(Number commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	/**
	 * @param delNo
	 *            The delNo to set.
	 */
	public void setDelNo(String delNo) {
		this.delNo = delNo;
	}

	/**
	 * @param merchantAmt
	 *            The merchantAmt to set.
	 */
	public void setMerchantAmt(Number merchantAmt) {
		this.merchantAmt = merchantAmt;
	}

	/**
	 * @param merchantBalance
	 *            The merchantBalance to set.
	 */
	public void setMerchantBalance(Number merchantBalance) {
		this.merchantBalance = merchantBalance;
	}

	/**
	 * @param merchantId
	 *            The merchantId to set.
	 */
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @param msisdn
	 *            The msisdn to set.
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @param parentTrxId
	 *            The parentTrxId to set.
	 */
	public void setParentTrxId(Integer parentTrxId) {
		this.parentTrxId = parentTrxId;
	}

	/**
	 * @param paymentMode
	 *            The paymentMode to set.
	 */
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @param phoneMake
	 *            The phoneMake to set.
	 */
	public void setPhoneMake(String phoneMake) {
		this.phoneMake = phoneMake;
	}

	/**
	 * @param reconStatus
	 *            The reconStatus to set.
	 */
	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}

	/**
	 * @param refunded
	 *            The refunded to set.
	 */
	public void setRefunded(boolean refunded) {
		this.refunded = refunded;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param requestTs
	 *            The requestTs to set.
	 */
	public void setRequestTs(Timestamp requestTs) {
		RequestTs = requestTs;
	}

	/**
	 * @param responesCode
	 *            The responesCode to set.
	 */
	public void setResponesCode(String responesCode) {
		this.responesCode = responesCode;
		setResponseMessage(ConfigurationManagerImpl
				.getTransactionStatus(responesCode));
	}

	/**
	 * @param responseMessage
	 *            The responseMessage to set.
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	/**
	 * @param responseTs
	 *            The responseTs to set.
	 */
	public void setResponseTs(Timestamp responseTs) {
		ResponseTs = responseTs;
	}

	/**
	 * @param sku
	 *            The sku to set.
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}

	/**
	 * @param skuName
	 *            The skuName to set.
	 */
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	/**
	 * @param surchargeAmt
	 *            The surchargeAmt to set.
	 */
	public void setSurchargeAmt(Number surchargeAmt) {
		this.surchargeAmt = surchargeAmt;
	}

	/**
	 * @param totalAmt
	 *            The totalAmt to set.
	 */
	public void setTotalAmt(Number totalAmt) {
		this.totalAmt = totalAmt;
	}

	/**
	 * @param trxId
	 *            The trxId to set.
	 */
	public void setTrxId(Integer trxId) {
		this.trxId = trxId;
	}

	/**
	 * @param trxStatus
	 *            The trxStatus to set.
	 */
	public void setTrxStatus(TrxStatus trxStatus) {
		this.trxStatus = trxStatus;
	}

	/**
	 * 
	 * @param partnerCommissions
	 */
	public void setPartnerCommissions(Map<Integer, Number> partnerCommissions) {
		this.partnerCommissions = partnerCommissions;
	}

	/**
	 * @param trxType
	 *            The trxType to set.
	 */
	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	/**
	 * @return Returns the phoneModel.
	 */
	public String getPhoneModel() {
		return phoneModel;
	}

	/**
	 * @param phoneModel
	 *            The phoneModel to set.
	 */
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public Timestamp getTsCreated() {
		return tsCreated;
	}

	public void setTsCreated(Timestamp tsCreated) {
		this.tsCreated = tsCreated;
	}

	public Timestamp getTsUpdated() {
		return tsUpdated;
	}

	public void setTsUpdated(Timestamp tsUpdated) {
		this.tsUpdated = tsUpdated;
	}

	/**
	 * @return Returns the deliveryMode.
	 */
	public DeliveryMode getDeliveryMode() {
		return deliveryMode;
	}

	/**
	 * @param deliveryMode
	 *            The deliveryMode to set.
	 */
	public void setDeliveryMode(DeliveryMode deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	/**
	 * @return Returns the getCMSValue.
	 */
	public Number getCMSValue() {
		return getPartnerCommissions().get(998);
	}

	/**
	 * @return Returns the getCMSName.
	 */
	public String getCMSName() {
		if (cmsName != null) {
			return cmsName;
		} else {
			try {
				IConfigurationManager config = ConfigurationManagerImpl
						.getConfigurationManager();
				return config.getConfig(MCDSConfig.CMS_NAME);
			} catch (MCDSException e) {
				// if required do here
				return "";
			}
		}
	}

	/**
	 * @return Returns the setCMSName.
	 */
	public void setCMSName(String cms) {
		this.cmsName = cms;
	}

}
