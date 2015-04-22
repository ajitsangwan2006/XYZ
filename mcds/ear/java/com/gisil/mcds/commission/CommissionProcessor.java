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
package com.gisil.mcds.commission;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.CommFormulaInfo;
import com.gisil.mcds.dmi.CommFormulaMappingInfo;
import com.gisil.mcds.dmi.PartnerInfo;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.util.LogUtil;

/**
 * The class<code>CommissionProcessor<code>is the form of<code>IComponent</code>
 * indicates commission requirement processor 
 * @author sandeep kadyan
 */
public class CommissionProcessor {

	/**
	 * Created instance
	 */
	private static CommissionProcessor INSTANCE;

	/**
	 * Created instance
	 */
	private CommissionDBMgr _dbMgr;

	/**
	 * Created instance
	 */
	private static Logger _log;

	/**
	 * Constructs commission processor
	 */
	public CommissionProcessor() {
		super();
	}

	/**
	 * @return instance
	 */
	public static CommissionProcessor getInstance() {
		return INSTANCE;
	}

	/**
	 * This method startup commission processor
	 */
	public void preStartup() throws MCDSException {
		_log = LogUtil.getLogger(CommissionProcessor.class);
		INSTANCE = this;
	}

	/**
	 * This method created instance for post startup
	 */
	public void postStartup() throws MCDSException {
		_dbMgr = new CommissionDBMgr();
	}

	/**
	 * Shutdown if its null
	 */
	public void shutdown() {
		INSTANCE = null;
	}

	/**
	 * This method is
	 */
	public void process(Transaction trx) throws MCDSException {
		Map<Integer, Number> partnerCommissions = calculateCommission(trx);
		Integer partnerIdForMerchant = getPartnerIdOfMerchant();
		Number totalCommission = getCumulativeCommission(partnerCommissions);
		Number merchantCommission = partnerCommissions
				.get(partnerIdForMerchant);
		merchantCommission = merchantCommission == null ? Double.valueOf(0.0)
				: merchantCommission;
		boolean isbooking = Transaction.TYPE_BUYING.equals(trx.getTrxType());
		Number merchantDebitCreditAmount = isbooking ? Double.valueOf(trx
				.getBaseAmt().doubleValue()
				+ totalCommission.doubleValue()
				- merchantCommission.doubleValue()) : Double.valueOf(trx
				.getBaseAmt().doubleValue()
				+ merchantCommission.doubleValue());

		_log.info("Commissions for trxId=" + trx.getTrxId() + " delno="
				+ trx.getDelNo() + " totalComm=" + totalCommission
				+ " merchantComm=" + merchantCommission
				+ " merchantDebitAmount=" + merchantDebitCreditAmount);

		trx.setCommissionAmt(totalCommission);
		trx.setMerchantAmt(merchantDebitCreditAmount);
		trx.setPartnerCommissions(partnerCommissions);
		trx.setTotalAmt(trx.getBaseAmt().doubleValue()
				+ trx.getCommissionAmt().doubleValue());

		if (trx.getTrxId() != null && trx.getTrxId() > 0) {
			_dbMgr.saveCommission(trx.getTrxId(), partnerCommissions);

		}
	}

	private Number getCumulativeCommission(
			Map<Integer, Number> partnerCommissions) {
		double total = 0.0;
		for (Number commission : partnerCommissions.values())
			total += commission.doubleValue();

		return Double.valueOf(total);
	}

	private Integer getPartnerIdOfMerchant() {
		// TODO fix me implement me.
		return Integer.valueOf(999);
	}

	public Map<Integer, Number> calculateCommission(Transaction trx)
			throws MCDSException {
		Map<Integer, Number> partnerCommissions = new HashMap<Integer, Number>();

		IContext ctx = buildContext(trx);
		if (trx.getTrxType() == Transaction.TYPE_REVERT) {
			// no forumula will be found for this trx. So we need to return the
			// zero for merchnat only for saving this record in trxcommission
			// table.
			partnerCommissions.put(getPartnerIdOfMerchant(), IEvaluatable.ZERO);
		} else {
			Collection<CommissionFormula> formulae = _dbMgr
					.findCommFormulas(ctx);
			ClassSurcharge classSurcharge = null;
			for (CommissionFormula formula : formulae) {

				Double commission = formula.evaulate(ctx).doubleValue();
				if (formula.isClassSurcharge()) {
                    if (classSurcharge == null) {
                        classSurcharge = _dbMgr.findClassSurcharge(ctx);
                        if (classSurcharge == null) {
                            String msg = "no class surcharge found though commission formula "
                                    + "was defined with class surchage. Using "
                                    + "NULL_CLASS_SURCHARGE for commission formula" + formula.getFormulaId();
                            _log.info(msg);
                            classSurcharge = ClassSurcharge.NULL_CLASS_SURCHAGE;
                        }
                    }
                    commission = Double.valueOf(commission.doubleValue() + classSurcharge.evaulate(ctx).doubleValue());
                }
				partnerCommissions.put(formula.getPartnerId(), commission);
				// set the foruma id to trx.
				trx.setCommFormulaId(Integer.valueOf(formula.getFormulaId()));
			}
		}

		_log.info("commission calculated for trxId " + trx.getTrxId()
				+ " delNo=" + trx.getDelNo() + " - " + partnerCommissions);

		return partnerCommissions;
	}

	private IContext buildContext(Transaction trx) throws MCDSException {
		Number baseAmt = trx.getBaseAmt();
		Integer aggregatorId = trx.getAggregatorId();
		String delNo = trx.getDelNo();
		String deliveryMode = trx.getDeliveryMode().toDbLiteral();
		
		_log.info(" process -{DelNo=" + delNo + " ,baseAmt=" + baseAmt
				+ " ,AggregatorId=" + aggregatorId + " ,delNo=" + delNo + " ,deliveryMode=" + deliveryMode);
		if (baseAmt == null)
			throw new MCDSException(
					"base amount must be set in the transaction for calculating the commission");
		if (aggregatorId == null)
			throw new MCDSException(
					"aggregatorId must be set in the transaction for calculating the commission");
		if (delNo == null)
			throw new MCDSException(
					"delNo must be set in the transaction for calculating the commission");
		if (deliveryMode == null)
			throw new MCDSException(
					"deliveryMode must be set in the transaction for calculating the commission");

		CommContext ctx = new CommContext();
		ctx.setContext(IContext.AGGREGATOR_ID, aggregatorId);
		ctx.setContext(IContext.BASE_AMT, baseAmt);
		ctx.setContext(IContext.MERCHANT_DEL_NO, delNo);
		ctx.setContext(IContext.DELIVERY_MODE,deliveryMode );

		return ctx;
	}
    /**
     * To get  Partner Values
     * @param partner
     * @return List
     * @throws MCDSException
     */
	public List<PartnerInfo> getPartners(Partner filter) throws MCDSException {
		return _dbMgr.getPartnerList(filter);
	}
    /**
     * To get Commision Formula List Values
     * @param CommFormulaInfo
     * @return List
     * @throws MCDSException
     */
	public List<CommFormulaInfo> getCommFormulaList(CommFormulaInfo filter)
			throws MCDSException {
		return _dbMgr.getCommFormulaList(filter);
	}
    /**
     * To ge tCommision Formula Mapping List Values
     * @param CommFormulaMappingInfo
     * @return List
     * @throws MCDSException
     */
	public List<CommFormulaMappingInfo> getCommFormulaMappingList(
			CommFormulaMappingInfo filter) throws MCDSException {
		return _dbMgr.getCommFormulaMappingList(filter);
	}
    /**
     * To get ClassSurcharge Values
     * @param String
     * @param int
     * @return ClassSurcharge
     * @throws MCDSException
     */
	public ClassSurcharge getClassSurcharge(String classCode, int aggId)throws MCDSException{
		return _dbMgr.getClassSurcharge(classCode,aggId);
	}
	
     /**
     * To add ClassSurcharge Values
     * @param ClassSurcharge
     * @return
     * @throws MCDSException
     */
	public void addClassSurcharge(ClassSurcharge info)throws MCDSException{
		_dbMgr.addClassSurcharge(info);
	}
    /**
     * To get ClassSurcharge Values
     * @param
     * @return ArrayList
     * @throws MCDSException
     */
	public ArrayList<ClassSurcharge> getClassSurcharge()throws MCDSException{
		return _dbMgr.getClassSurcharge();
	}
    /**
     * To update Partner Info 
     * @param partner
     * @return Boolean
     * @throws MCDSException
     */
	public Boolean updatePartnerInfo(Partner partner) throws MCDSException {
		return _dbMgr.updatePartnerInfo(partner);
	}
    /**
     * To save new  Partner Values
     * @param partner
     * @return
     * @throws MCDSException
     */
    public void savePartnerValues(Partner partner) throws MCDSException{
        _dbMgr.savePartnerValues(partner);
        
    }
    /**
     * To add new  Partner Values
     * @param partner
     * @return
     * @throws MCDSException
     */
    public void addPartnerValues(Partner partner) throws MCDSException{
        _dbMgr.addPartnerValues(partner);
        
    }
    /**
     * To add new  ClassSurcharge Values
     * @param ClassSurcharge
     * @return
     * @throws MCDSException
     */
    public void saveSurchargeValues(ClassSurcharge surcharge) throws MCDSException{
        _dbMgr.saveSurchargeValues(surcharge);
        
    }
	
	
}
