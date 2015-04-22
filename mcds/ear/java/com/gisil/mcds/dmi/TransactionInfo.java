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
package com.gisil.mcds.dmi;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * The class<code>TransactionInfo</code> is the form of<code>Serialization</code>
 * indicates the transaction information
 * 
 * @author Amit Sachan
 */
public class TransactionInfo implements Serializable {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = -3351795685572284343L;

	/**
	 * trx Id is transaction details  
	 */
	private Integer trxId;

	/**
	 * del no is merchant mobile details  
	 */
	private String delNo;

	/**
	 * merchant Id is merchant details  
	 */
	private Integer merchantId;

	/**
	 * aggregator Id is aggregator details  
	 */
	private Integer aggregatorId;

	/**
	 * base Amount is total ticket amount after cutting service charge  
	 */
	private Number baseAmt;

	/**
	 * commission amount is redbus commission amount  
	 */
	private Number commissionAmt;

	/**
	 * total amount is ticket amount details  
	 */
	private Number totalAmt;

	/**
	 * trx Status is transaction status details  
	 */
	private String trxStatus;

	private Integer parentTrxId;

	/**
	 * recon Status is reconcilation status details  
	 */
	private String reconStatus;

	private String remarks;

	/**
	 * created Time Stamp is time/date details   
	 */
	private Date createdTs;

	/**
	 * update Time Stamp is updated time/date details  
	 */
	private Date updatedTs;

	/**
	 * merchant amount is total merchant amount details   
	 */
	private Number merchantAmt;

	/**
	 * final Request Time Stamp is final request time/date details  
	 */
	private Date finalRequestTs;

	/**
	 * final Response Time Stamp is final response time/date details  
	 */
	private Date finalResponseTs;

	/**
	 * response Code is aggregator response code  
	 */
	private String responseCode;

	/**
	 * response Message is aggregator message reponse  
	 */
	private String responseMessage;

	/**
	 * merchant Balance is merchant account balance after he booked the ticket    
	 */
	private Number merchantBalance;

	/**
	 * its byDeafult "CASH" mode. and is type of PaymentMode
	 */
	private String paymentMode;

	/**
	 * commission formula Id is commission of redbus details  
	 */
	private Integer commFormulaId;

	/**
	 * trx Type is booking status details  
	 */
	private String trxType;

	private Map<Integer, Number> trxCommission;

	private String msisdn;

	private String phoneMake;

	private String phoneModel;

	private String aggrPhoneCode;

	private String skuCode;

	private String skuName;

	/**
	 * Contruct the transaction information
	 */
	public TransactionInfo() {
		// super();
	}

	/**
	 * @return Returns the aggregatorId.
	 */
	public Integer getAggregatorId() {
		return aggregatorId;
	}

	/**
	 * @param aggregatorId The aggregatorId to set.
	 */
	public void setAggregatorId(Integer aggregatorId) {
		this.aggregatorId = aggregatorId;
	}

	/**
	 * @return Returns the aggrPhoneCode.
	 */
	public String getAggrPhoneCode() {
		return aggrPhoneCode;
	}

	/**
	 * @param aggrPhoneCode The aggrPhoneCode to set.
	 */
	public void setAggrPhoneCode(String aggrPhoneCode) {
		this.aggrPhoneCode = aggrPhoneCode;
	}

	/**
	 * @return Returns the baseAmt.
	 */
	public Number getBaseAmt() {
		return baseAmt;
	}

	/**
	 * @param baseAmt The baseAmt to set.
	 */
	public void setBaseAmt(Number baseAmt) {
		this.baseAmt = baseAmt;
	}

	/**
	 * @return Returns the commFormulaId.
	 */
	public Integer getCommFormulaId() {
		return commFormulaId;
	}

	/**
	 * @param commFormulaId The commFormulaId to set.
	 */
	public void setCommFormulaId(Integer commFormulaId) {
		this.commFormulaId = commFormulaId;
	}

	/**
	 * @return Returns the commissionAmt.
	 */
	public Number getCommissionAmt() {
		return commissionAmt;
	}

	/**
	 * @param commissionAmt The commissionAmt to set.
	 */
	public void setCommissionAmt(Number commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	/**
	 * @return Returns the createdTs.
	 */
	public Date getCreatedTs() {
		return createdTs;
	}

	/**
	 * @param createdTs The createdTs to set.
	 */
	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	/**
	 * @return Returns the delNo.
	 */
	public String getDelNo() {
		return delNo;
	}

	/**
	 * @param delNo The delNo to set.
	 */
	public void setDelNo(String delNo) {
		this.delNo = delNo;
	}

	/**
	 * @return Returns the finalRequestTs.
	 */
	public Date getFinalRequestTs() {
		return finalRequestTs;
	}

	/**
	 * @param finalRequestTs The finalRequestTs to set.
	 */
	public void setFinalRequestTs(Date finalRequestTs) {
		this.finalRequestTs = finalRequestTs;
	}

	/**
	 * @return Returns the finalResponseTs.
	 */
	public Date getFinalResponseTs() {
		return finalResponseTs;
	}

	/**
	 * @param finalResponseTs The finalResponseTs to set.
	 */
	public void setFinalResponseTs(Date finalResponseTs) {
		this.finalResponseTs = finalResponseTs;
	}

	/**
	 * @return Returns the merchantAmt.
	 */
	public Number getMerchantAmt() {
		return merchantAmt;
	}

	/**
	 * @param merchantAmt The merchantAmt to set.
	 */
	public void setMerchantAmt(Number merchantAmt) {
		this.merchantAmt = merchantAmt;
	}

	/**
	 * @return Returns the merchantBalance.
	 */
	public Number getMerchantBalance() {
		return merchantBalance;
	}

	/**
	 * @param merchantBalance The merchantBalance to set.
	 */
	public void setMerchantBalance(Number merchantBalance) {
		this.merchantBalance = merchantBalance;
	}

	/**
	 * @return Returns the merchantId.
	 */
	public Integer getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId The merchantId to set.
	 */
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return Returns the msisdn.
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn The msisdn to set.
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return Returns the parentTrxId.
	 */
	public Integer getParentTrxId() {
		return parentTrxId;
	}

	/**
	 * @param parentTrxId The parentTrxId to set.
	 */
	public void setParentTrxId(Integer parentTrxId) {
		this.parentTrxId = parentTrxId;
	}

	/**
	 * @return Returns the paymentMode.
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode The paymentMode to set.
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return Returns the phoneMake.
	 */
	public String getPhoneMake() {
		return phoneMake;
	}

	/**
	 * @param phoneMake The phoneMake to set.
	 */
	public void setPhoneMake(String phoneMake) {
		this.phoneMake = phoneMake;
	}

	/**
	 * @return Returns the phoneModel.
	 */
	public String getPhoneModel() {
		return phoneModel;
	}

	/**
	 * @param phoneModel The phoneModel to set.
	 */
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	/**
	 * @return Returns the reconStatus.
	 */
	public String getReconStatus() {
		return reconStatus;
	}

	/**
	 * @param reconStatus The reconStatus to set.
	 */
	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the responseCode.
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode The responseCode to set.
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return Returns the responseMessage.
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @param responseMessage The responseMessage to set.
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	/**
	 * @return Returns the skuCode.
	 */
	public String getSkuCode() {
		return skuCode;
	}

	/**
	 * @param skuCode The skuCode to set.
	 */
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * @return Returns the skuName.
	 */
	public String getSkuName() {
		return skuName;
	}

	/**
	 * @param skuName The skuName to set.
	 */
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	/**
	 * @return Returns the totalAmt.
	 */
	public Number getTotalAmt() {
		return totalAmt;
	}

	/**
	 * @param totalAmt The totalAmt to set.
	 */
	public void setTotalAmt(Number totalAmt) {
		this.totalAmt = totalAmt;
	}

	/**
	 * @return Returns the trxCommission.
	 */
	public Map<Integer, Number> getTrxCommission() {
		return trxCommission;
	}

	/**
	 * @param trxCommission The trxCommission to set.
	 */
	public void setTrxCommission(Map<Integer, Number> trxCommission) {
		this.trxCommission = trxCommission;
	}

	/**
	 * @return Returns the trxId.
	 */
	public Integer getTrxId() {
		return trxId;
	}

	/**
	 * @param trxId The trxId to set.
	 */
	public void setTrxId(Integer trxId) {
		this.trxId = trxId;
	}

	/**
	 * @return Returns the trxStatus.
	 */
	public String getTrxStatus() {
		return trxStatus;
	}

	/**
	 * @param trxStatus The trxStatus to set.
	 */
	public void setTrxStatus(String trxStatus) {
		this.trxStatus = trxStatus;
	}

	/**
	 * @return Returns the trxType.
	 */
	public String getTrxType() {
		return trxType;
	}

	/**
	 * @param trxType The trxType to set.
	 */
	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	/**
	 * @return Returns the updatedTs.
	 */
	public Date getUpdatedTs() {
		return updatedTs;
	}

	/**
	 * @param updatedTs The updatedTs to set.
	 */
	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

}
