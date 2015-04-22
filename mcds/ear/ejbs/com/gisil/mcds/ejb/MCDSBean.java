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

package com.gisil.mcds.ejb;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.gisil.mcds.aggregator.AggregatorMgr;
import com.gisil.mcds.aggregator.mauj.entity.AbstractTransTrackerResponse;
import com.gisil.mcds.aggregator.mauj.entity.ContentDetailTO;
import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.commission.ClassSurcharge;
import com.gisil.mcds.commission.CommissionProcessor;
import com.gisil.mcds.commission.CommissionDBMgr;
import com.gisil.mcds.commission.Partner;
import com.gisil.mcds.config.Configuration;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.content.ContentDBMgr;
import com.gisil.mcds.content.ContentMgr;
import com.gisil.mcds.dmi.AggregatorInfo;
import com.gisil.mcds.dmi.CommFormulaInfo;
import com.gisil.mcds.dmi.CommFormulaMappingInfo;
import com.gisil.mcds.dmi.ContentDetailsforUI;
import com.gisil.mcds.dmi.ContentInfo;
import com.gisil.mcds.dmi.NameIdInfo;
import com.gisil.mcds.dmi.PackDetailForUI;
import com.gisil.mcds.dmi.PackTypeListInfo;
import com.gisil.mcds.dmi.PartnerInfo;
import com.gisil.mcds.dmi.PhoneMappingInfo;
import com.gisil.mcds.dmi.PurchaseTrxRequest;
import com.gisil.mcds.dmi.PurchaseTrxResponse;
import com.gisil.mcds.dmi.SearchTrxRequest;
import com.gisil.mcds.dmi.TransactionDetail;
import com.gisil.mcds.dmi.TransactionSummaryInfo;
import com.gisil.mcds.script.ContentPackUpdateJob;
import com.gisil.mcds.script.PhoneMappingJob;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.transaction.TransactionMgr;
import com.gisil.mcds.transaction.TrxDBMgr;
import com.gisil.mcds.transaction.TrxStatus;
import com.gisil.mcds.util.AuditLogger;
import com.gisil.mcds.util.ConfigValuesWrapper;

/**
 * MCDS Bean
 * This single bean contains all methods required 
 * @author
 *
 */
public class MCDSBean implements SessionBean {

	private static final long serialVersionUID = -8468282903540585444L;

	public void setSessionContext(SessionContext arg0) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	private Logger _log;

	private AuditLogger _logger = new AuditLogger();

	/**
	 * @throws CreateException
	 */
	public void ejbCreate() throws CreateException {
		_log = Logger.getLogger("MCDSBean");
	}

	/**
	 * This function find and return the content packs id and content packs
	 * names, which we are going to catch in contentPack.jsp
	 */
	public ArrayList<PackTypeListInfo> getPackContents(String contentInfo) throws MCDSException {
		ContentDBMgr dbMgr = ContentMgr.getContentMgr().getContentDBMgr();
		return dbMgr.getContents(contentInfo);

	}

	/**
	 * This function find and return the content packs id and content packs
	 * names, which we are going to catch in contentPack.jsp
	 */
	public ArrayList<ContentDetailTO> getSubPackContents(String aType) {
		ContentDBMgr dbMgr = ContentMgr.getContentMgr().getContentDBMgr();
		return dbMgr.getSubPackContents(aType);
	}

	public ContentDetailTO getContentPackDetails(String aPackId) {

		ContentDBMgr cdbm = new ContentDBMgr();
		ContentDetailTO cdto = new ContentDetailTO();
		cdto.setContents(cdbm.getPackContents(aPackId));
		cdto.setBackType(cdbm.getBackType(aPackId));
		cdto.setEmptyContent(cdbm.isEmptyContent());
		cdto.setEmptyContentName(cdbm.isEmptyContentName());
		cdto.setContentName(cdbm.getContentName(aPackId));
		return cdto;

	}

	public String getBackId(String aBackId) {
		ContentDBMgr cdbm = new ContentDBMgr();
		return cdbm.getBackId(aBackId);
		/*
		 * BackDetail backDetail = new BackDetail();
		 * 
		 * return backDetail.getBackId(aBackId);
		 */
	}

	public ContentDetailTO getContentDetails(String aItemId) {
		ContentDBMgr cdbm = new ContentDBMgr();
		ContentDetailTO cdto = cdbm.getDetail(aItemId);
		return cdto;
	}

	public String getConfigString(String name) {
		return ConfigValuesWrapper.getValueAsString(name);
	}
	
	/**
	 * Get List of Transactions for the merchant
	 * 
	 * @param recordLimit
	 * @param delNo
	 * @return
	 * @throws MCDSException
	 */
	public ArrayList<TransactionSummaryInfo> getTransactionList(
			int recordLimit, long delNo ) throws MCDSException 
			{
		TrxDBMgr transactionDBManager = new TrxDBMgr();
		ArrayList<TransactionSummaryInfo> transactionList = transactionDBManager
				.getTransactionSummary(recordLimit, delNo  );
		return transactionList;
	}
	
	/**
	 * Get List of Transactions for the merchant on Date Range
	 * 
	 * @param recordLimit
	 * @param delNo
	 * @return
	 * @throws MCDSException
	 */
	public ArrayList<TransactionSummaryInfo> getTransactionDetailBetweenDate(
			Date fromDate, Date toDate, long delNo) {
		TrxDBMgr transactionDBManager = new TrxDBMgr();
		ArrayList<TransactionSummaryInfo> transactionList = transactionDBManager
				.getTransactionSummary(fromDate, toDate , delNo);
		return transactionList;

	}

	public Number getConfigNumber(String name) {
		return ConfigValuesWrapper.getValueAsNumber(name);
	}

	public Boolean getConfig(String name) {
		return ConfigValuesWrapper.getValueAsBoolean(name);
	}

	public ContentDetailTO getSearchContentDetails(String aContents,
			String aSearchBy) {
		ContentDBMgr cdbm = new ContentDBMgr();
		ContentDetailTO cdto = new ContentDetailTO();
		cdto.setContents(cdbm.getSearchContentDetails(aContents, aSearchBy));
		cdto.setEmptyContent(cdbm.isEmptyContent());
		cdto.setEmptyContentName(cdbm.isEmptyContentName());
		return cdto;

	}

	public ContentDetailTO getSearchContentDetails(String aContentId) {
		ContentDBMgr cdbm = new ContentDBMgr();
		ContentDetailTO cdto = cdbm.getSearchDetail(aContentId);
		return cdto;
	}

	public int getMaxTransactionLimit() {
		int limit = 0;
		IConfigurationManager configMgr = ConfigurationManagerImpl
				.getConfigurationManager();
		try {
			Number transLimit = configMgr
					.getConfigAsNumber(MCDSConfig.MAX_TRANSACTION_LIMIT);
			limit = transLimit.intValue();
		} catch (MCDSException mcdsExp) {
			System.out
					.println("Exception Occured while getting trasaction limit from DB "
							+ mcdsExp.toString());
		}

		return limit;
	}

	public int getTransactionHistoryAvlDays() {
		int days = 0;
		IConfigurationManager configMgr = ConfigurationManagerImpl
				.getConfigurationManager();
		try {
			Number historydays = configMgr
					.getConfigAsNumber(MCDSConfig.TRANSACTION_HISTORY_AVLDAYS);
			days = historydays.intValue();
		} catch (MCDSException mcdsExp) {
			_log.info("Exception Occured while getting trasaction limit from DB "
							+ mcdsExp.toString());
		}

		return days;
	}

	public Transaction doPurchase(Transaction trx)
			throws MCDSException {
		_log.info("Find a new Transaction request in DB");
		return TransactionMgr.getTransactionMgr().doPurchase(trx);
	}

	public Transaction submitPurchaseRequest(PurchaseTrxRequest aRequest, Transaction trx) throws MCDSException{

		trx = TransactionMgr.getTransactionMgr()
				.submitPurchaseRequest(aRequest , trx);
		_log.info("Created a successful instance of new Transaction request");
		return trx;
	}

	public PurchaseTrxResponse loadTransactionDetails(Integer trxId)
			throws MCDSException {

		return TransactionMgr.getTransactionMgr().getTransactionDetails(trxId);
	}
/**
 * 
 * @param trxId
 * @return
 * @throws MCDSException
 * @throws RemoteException
 */
	public Boolean resendContentReq(Integer trxId) throws MCDSException, RemoteException
	{	
		return TransactionMgr.getTransactionMgr().resendContent(trxId);
	}
	
	
	public void submitAuditTrail(AuditTrail trail) {
		_logger.logTrail(trail);
	}

	public ArrayList<ContentDetailTO> getSearchPackContents(
			String aContentInfo, String aSearchBy) {
		ContentDBMgr cdbm = new ContentDBMgr();

		return cdbm.getSubPackContents(aContentInfo, aSearchBy);
	}
	/**
	 * Get generic menu list for Terminal
	 * 
	 * @return
	 * @throws MCDSException
	 */
	public HashMap<String, String> getTerminalMenuContent( int aggID ) throws MCDSException {
		ContentDBMgr dbMgr = ContentMgr.getContentMgr().getContentDBMgr();
		HashMap<String, String> contents = dbMgr.getMenuContent( aggID );
		return contents;
	}
	
	/**
	 * Get Contents list for UI to Show/Manage
	 * 
	 * @return
	 * @throws MCDSException
	 */
	public ArrayList<ContentDetailsforUI> getContents(Integer contentId , Integer aggrId) throws MCDSException {
		ContentDBMgr dbMgr = ContentMgr.getContentMgr().getContentDBMgr();
		return dbMgr.getContentDetails(contentId , aggrId);
	}
	/**
	 * Get Pack details
	 * 
	 * @param contentId
	 * @return
	 * @throws MCDSException
	 */
	public PackDetailForUI getPackDetail(Integer contentId) throws MCDSException {
		ContentDBMgr dbMgr = ContentMgr.getContentMgr().getContentDBMgr();
		return dbMgr.getPackDetail(contentId);
	}

	/**
	 * This method Load both Local Transaction and Remote Transaction Status.
	 * 
	 * @param transactionId
	 * @return
	 * @throws MCDSException
	 */
	public TransactionDetail getTransactionStatus(Integer transactionId)
			throws MCDSException {
		TransactionDetail transDetail = new TransactionDetail();
		AbstractTransTrackerResponse transTrackerResponse = TransactionMgr
				.getTransactionMgr().getRemoteTransaction(transactionId);
		Transaction localTrx = TransactionMgr.getTransactionMgr()
				.loadLocalTransaction(transactionId);
		transDetail.setTransaction(localTrx);
		transDetail.setTransTrackerResponse(transTrackerResponse);
		return transDetail;
		
	}

	public ArrayList<ContentDetailTO> getSearchPackContents(String aContentInfo) {
		ContentDBMgr mgr = new ContentDBMgr();

		return mgr.getSubPack(aContentInfo);
	}

	/**
	 * Update Transaction status by OPs users
	 * 
	 * @param aTrxId
	 * @param aStatus
	 * @param aDescription
	 * @return
	 * @throws MCDSException
	 */
	public Integer updateStatus(Integer aTrxId, TrxStatus aStatus,
			String aDescription) throws MCDSException {
		TransactionMgr mgr = TransactionMgr.getTransactionMgr();
		Transaction trx = mgr.loadLocalTransaction(aTrxId);
		if (TrxStatus.ACCOUNTED.equals(aStatus)) {
			trx.setRemarks(aDescription);
			mgr.revertPayment(trx);
		} else if(TrxStatus.SETTELED.equals(aStatus)){
			trx.setRemarks(aDescription);
			mgr.settleTransaction(trx);
		}else{
			throw new MCDSException("Trying to set Invalid Transaction status for TrxId - " + aTrxId);
		}
		return trx.getTrxId();		
	}
	/**
	 * Get Local Transcation 
	 * 
	 * @param aTrxId
	 * @return
	 * @throws MCDSException
	 */
	public Transaction getLocalTransactionStatus(Integer aTrxId)
	throws  MCDSException{
		TransactionMgr mgr = TransactionMgr.getTransactionMgr();
		return mgr.loadLocalTransaction(aTrxId);
	}

	
	public List<Transaction> searchAllTransaction(SearchTrxRequest srchTrxRequest) throws RemoteException, MCDSException
	{
		List<Transaction> trxList=TransactionMgr.getTransactionMgr().getTrxDBMgr().loadTransaction(srchTrxRequest);
		return trxList;
	}
	
	/**
	 * Returns the top 10 contents
	 * @param aContentId
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList getTopContents(String aContentId){
		ContentDBMgr cdbm = new ContentDBMgr();

		return cdbm.topContents(aContentId);
	}
	
	/**
	 * Return parentId for back url generation in TOP 10 contents
	 * @param aContentId
	 * @return
	 */
	public String getParentId(String aContentId){
		ContentDBMgr cdbm = new ContentDBMgr();

		return cdbm.getParentId(aContentId);
	}
	
	/**
	 * Return the all mode of delivery
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList getDeliveryMode() throws MCDSException {
		return TransactionMgr.getTransactionMgr().getDeliveryMode();
	}
	
	/**
	 * confirm the make and model and return the transaction object
	 * @param ptr
	 * @param make
	 * @param model
	 * @return
	 * @throws MCDSException
	 * @throws RemoteException
	 */
	public Transaction confirmDetail(String aMake,
			String aModel) throws MCDSException, RemoteException {
		return TransactionMgr.getTransactionMgr().newTransaction(aMake,aModel);
	}
	
	/**
	 * 
	 * @return
	 * @throws MCDSException
	 */
	public ArrayList<Configuration> loadConfigurationData() throws MCDSException
	{
		
		ArrayList<Configuration> cfgList = ContentMgr.getContentMgr().getContentDBMgr().loadConfiguration();
		return cfgList;
	}
	/**
	 * 
	 * @param cfgDataList
	 * @return
	 * @throws MCDSException
	 */
	public ArrayList<Configuration> updateConfigurationData( ArrayList<Configuration> cfgDataList ) throws  MCDSException
	{
		ArrayList<Configuration> cfgList = ContentMgr.getContentMgr().getContentDBMgr().updateConfigurationData( cfgDataList );
		return cfgList;
	}
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * @throws MCDSException
	 */
	public List<AggregatorInfo> loadAggregators() throws RemoteException, MCDSException
	{

		return AggregatorMgr.getAggregatorMgr().getAgrDBMgr().loadAggregator();	

	}
	
	/**
	 * return info about content
	 * @param aContentId
	 * @return
	 * @throws MCDSException
	 * @throws RemoteException
	 */
	public ContentInfo getContentInfo(Long aContentId) throws MCDSException{
		return ContentMgr.getContentMgr().getContentDBMgr().getContentInfo(aContentId);
	}
	
	public void setContentStatus(String aId, String aType, String aValue) throws MCDSException
	{
		ContentMgr.getContentMgr().getContentDBMgr().setContentStatus(aId, aType, aValue);
	}
	
	/**
	 * Returns an Aggregator's Information
	 * @param aId
	 * @return
	 * @throws MCDSException
	 */
	public AggregatorInfo getAggregatorInfo(Integer aId) throws MCDSException
	{
		return AggregatorMgr.getAggregatorMgr().getAgrDBMgr().getAggregatorInfo(aId);
	}
	
	public Boolean updateAggrInfo(AggregatorInfo aggrInfo) throws MCDSException
	{
		return AggregatorMgr.getAggregatorMgr().getAgrDBMgr().updateAggregators(aggrInfo);	
	}
	
	/**
	 * Update the content informations
	 * @param aContentInfo
	 * @return
	 * @throws MCDSException
	 * @throws RemoteException
	 */
	public Boolean updateContentInfo(ContentInfo aContentInfo) throws MCDSException
	{
		return ContentMgr.getContentMgr().getContentDBMgr().updateContentInfo(aContentInfo);
	}
	
	/**
	 * Get Class Surcharge
	 * 
	 * @param classCode
	 * @param aggId
	 * @return
	 * @throws MCDSException
	 */
	public ClassSurcharge getClassSurcharge(String classCode, int aggId)throws MCDSException{
		CommissionProcessor pr = CommissionProcessor.getInstance();
		return pr.getClassSurcharge(classCode, aggId);
	}
	
	/**
	 * Add Class Surcharge 
	 * 
	 * @param info
	 * @throws MCDSException
	 * @throws SQLException
	 */
	public void addClassSurcharge(ClassSurcharge info)throws MCDSException, SQLException{
		CommissionProcessor pr = CommissionProcessor.getInstance();
		pr.addClassSurcharge(info);
	}
	
	/**
	 * Get All Class Surcharge
	 * 
	 * @param classCode
	 * @param aggId
	 * @return
	 * @throws MCDSException
	 */
	public ArrayList<ClassSurcharge> getClassSurcharge()throws MCDSException{
		CommissionProcessor pr = CommissionProcessor.getInstance();
		return pr.getClassSurcharge();
	}
	
	/**
	 * Return the detail of commission formulas
	 * 
	 * @return
	 * @throws MCDSException
	 */
	public List getCommFormula() throws MCDSException {
		CommissionDBMgr mgr = new CommissionDBMgr();
		return mgr.getCommFormula();
	}

	/**
	 * Return Commission formula info for commission sui
	 * 
	 * @return
	 * @throws MCDSException
	 * @throws RemoteException
	 */
	public CommFormulaInfo getFormulas(Integer id) throws MCDSException {
		CommissionDBMgr mgr = new CommissionDBMgr();

		return mgr.getFormulas(id);
	}

	/**
	 * Update the commission formula
	 * 
	 * @param commFormula
	 * @return
	 * @throws MCDSException
	 * @throws RemoteException
	 */
	public Boolean isUpdatedCommission(CommFormulaInfo commFormula)
			throws MCDSException {
		CommissionDBMgr mgr = new CommissionDBMgr();

		return mgr.isUpdatedCommission(commFormula);
	}

	/**
	 * This method is executing sql queries for finding id,aggregator
	 * id,commission fourmula id and delno from comm_formula_mapping table
	 * 
	 * @throws MCDSException
	 * @return List
	 */
	public List<CommFormulaMappingInfo> getCommFormulaMapping()
			throws MCDSException {
		CommissionDBMgr mgr = new CommissionDBMgr();

		return mgr.getCommFormulaMapping();
	}

	/**
	 * This method is executing sql queries for finding id,aggregator
	 * id,commission fourmula id and delno from comm_formula_mapping table
	 * 
	 * @throws MCDSException
	 * @return List
	 */
	public CommFormulaMappingInfo getCommFormulaMapping(Integer id)
			throws MCDSException {
		CommissionDBMgr mgr = new CommissionDBMgr();

		return mgr.getCommFormulaMapping(id);
	}

	/**
	 * Update the commission formula mapping
	 * 
	 * @param commFormula
	 * @return
	 * @throws MCDSException
	 */
	public Boolean isUpdatedCommissionMapping(CommFormulaMappingInfo commFormula)
			throws MCDSException {
		CommissionDBMgr mgr = new CommissionDBMgr();

		return mgr.isUpdatedCommissionMapping(commFormula);
	}
    

	/**
	 * Return List of  PartnerInfo 
	 * @param partner
	 * @return
	 * @throws MCDSException
	 */
	public List<PartnerInfo> getPartnerList(Partner partner) throws MCDSException 
	{
		return CommissionProcessor.getInstance().getPartners(partner);
	}
	
	/**
	 * 
	 * @param partner
	 * @return
	 * @throws MCDSException
	 */
	public Boolean updatePartnerInfo(Partner partner) throws MCDSException
	{
		
		return CommissionProcessor.getInstance().updatePartnerInfo(partner);
	}
	
	/**
	 * Return Partner name list
	 * 
	 * @return
	 * @throws MCDSException
	 * @throws RemoteException
	 */
	public List<NameIdInfo> getPartenerNameList() throws MCDSException {
		CommissionDBMgr mgr = new CommissionDBMgr();
		return mgr.getPartenerNameList();
	}
	/**
	 * Add the new commission formula into table
	 * @param info
	 * @throws MCDSException
	 */
	public void addCommission(CommFormulaInfo info)throws MCDSException{
		CommissionDBMgr mgr = new CommissionDBMgr();

		mgr.addCommission(info);
	}
	
	/**
	 * Add the new commission formula mapping into table
	 * @param info
	 * @throws MCDSException
	 */
	public void addCommissionMapping(CommFormulaMappingInfo info)throws MCDSException{
		CommissionDBMgr mgr = new CommissionDBMgr();

		mgr.addCommissionMapping(info);
	}
	
	/**
	 * retrun the object for add view
	 * @return
	 * @throws MCDSException
	 * @throws RemoteException
	 */
	public CommFormulaMappingInfo getCommForMap()throws MCDSException{
		CommFormulaMappingInfo info = new CommFormulaMappingInfo();
		CommissionDBMgr mgr = new CommissionDBMgr();
		info.setAggMap(AggregatorMgr.getAggregatorMgr().getAgrDBMgr().getAggNameList());
		info.setCommId(mgr.getCommIdList());
		return info;
	}
     /**
     * To save Partner Values
     * @param partner
     * @return
     * @throws MCDSException
     */
    
    public void savePartnerValues(Partner partner)
    throws MCDSException{
        CommissionProcessor.getInstance().savePartnerValues(partner);
    }
    /**
     * To add new  Partner Values
     * @param partner
     * @return
     * @throws MCDSException
     */
    public void addPartnerValues(Partner partner)
    throws MCDSException{
        CommissionProcessor.getInstance().addPartnerValues(partner);
    }
    /**
     * To surcharge Values
     * @param ClassSurcharge
     * @return
     * @throws MCDSException
     */
    public void addSurchrage(ClassSurcharge surcharge)
    throws MCDSException{
        
        CommissionProcessor.getInstance().addClassSurcharge(surcharge);
    }
    /**
     * To save Surcharge Values
     * @param ClassSurcharge
     * @return
     * @throws MCDSException
     */
    public void saveSurchargeValues(ClassSurcharge surcharge)throws MCDSException{
        CommissionProcessor.getInstance().saveSurchargeValues(surcharge);
    }
    /**
     * This method is executing sql queries for finding phone mapping info list from phone_mapping table
     * 
     * @throws MCDSException
     * @return List
     */
    public List<PhoneMappingInfo> getPhoneMapping()throws MCDSException{
    	return AggregatorMgr.getAggregatorMgr().getAgrDBMgr().getPhoneMapping();
    }
    /**
     * This method is executing sql queries for finding phone mapping info from phone_mapping table
     * 
     * @throws MCDSException
     * @return List
     */
    public PhoneMappingInfo getPhoneMapping(String aggCode)throws MCDSException{
    	return AggregatorMgr.getAggregatorMgr().getAgrDBMgr().getPhoneMapping(aggCode);
    }
    /**
     * Update the phone mapping
     * 
     * @param commFormula
     * @return
     * @throws MCDSException
     */
    public Boolean isUpdatedPhoneMapping(PhoneMappingInfo phoneMapping)throws MCDSException{
    
    /**
     * 
     * @TODO document me
     * @throws MCDSException
     */
    public void refreshContent() throws MCDSException
    {
        ContentPackUpdateJob job = new ContentPackUpdateJob();
        IConfigurationManager configMgr = ConfigurationManagerImpl
        .getConfigurationManager();
        job.refreshContent(configMgr);       
    }
    
    /**
     * 
     * @TODO document me
     * @throws MCDSException
     */
    public void refreshPhoneMapping() throws MCDSException
    {
        PhoneMappingJob job = new PhoneMappingJob();
        IConfigurationManager configMgr = ConfigurationManagerImpl
        .getConfigurationManager();
        job.refreshPhoneMapping(configMgr);
    }    
}
