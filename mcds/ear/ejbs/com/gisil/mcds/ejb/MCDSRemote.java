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

import javax.ejb.EJBObject;

import com.gisil.mcds.aggregator.mauj.entity.ContentDetailTO;
import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.commission.ClassSurcharge;
import com.gisil.mcds.commission.Partner;
import com.gisil.mcds.config.Configuration;
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
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.transaction.TrxStatus;

/**
 * Remote interface for MCDS
 * 
 * @author Ajit Singh
 */
public interface MCDSRemote extends EJBObject {

    /**
     * This function find and return the content packs id and content packs
     * names, which we are going to catch in contentPack.jsp
     * 
     * @return
     */
    public ArrayList<PackTypeListInfo> getPackContents(String contentInfo) throws RemoteException, MCDSException;

    /**
     * This function find and return the content packs id and content packs
     * names, which we are going to catch in contentPack.jsp
     * 
     * @param aType
     * @return
     */
    public ArrayList<ContentDetailTO> getSubPackContents(String aType) throws RemoteException;

    /**
     * This will process the pack details and return ContentDetailTO object
     * 
     * @param aPackId
     * @return
     */
    public ContentDetailTO getContentPackDetails(String aPackId) throws RemoteException;

    /**
     * Find the contents using their contentId's
     * 
     * @param aContentId
     * @return
     */
    public ContentDetailTO getSearchContentDetails(String aContents, String aSearchBy) throws RemoteException;

    /**
     * Generate backId for corresponding to current id
     * 
     * @param aBackId
     * @return
     */
    public String getBackId(String aBackId) throws RemoteException;

    /**
     * Generate contents detail using contentId
     * 
     * @param aItemId
     * @return
     */
    public ContentDetailTO getContentDetails(String aItemId) throws RemoteException;

    /**
     * Find the searchable contents using code or title
     * 
     * @param aContents
     * @param aSearchBy
     * @return
     */
    public ContentDetailTO getSearchContentDetails(String aContentId) throws RemoteException;

    /**
     * Submit the audit trail
     * 
     * @param trail
     * @throws RemoteException
     */
    public void submitAuditTrail(AuditTrail trail) throws RemoteException;

    /**
     * Return the max limit of display transactions
     * 
     * @return
     * @throws RemoteException
     */
    public int getMaxTransactionLimit() throws RemoteException;

    /**
     * Find the number which limits the display transaction
     * 
     * @return
     * @throws RemoteException
     */
    public int getTransactionHistoryAvlDays() throws RemoteException;

    /**
     * Return the transaction details using TrxDBMgr
     * 
     * @param recordLimit
     * @return
     * @throws RemoteException
     */
    public ArrayList<TransactionSummaryInfo> getTransactionList(int recordLimit, long terminalId)
            throws RemoteException, MCDSException;

    /**
     * Find the transactions from date to to date
     * 
     * @param fromDate
     * @param toDate
     * @return
     * @throws RemoteException
     */
    public ArrayList<TransactionSummaryInfo> getTransactionDetailBetweenDate(Date fromDate, Date toDate, long delNo)
            throws RemoteException;

    /**
     * Final purchaging of packs and return the transation details
     * 
     * @param trxId
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public Transaction doPurchase(Transaction trx) throws MCDSException, RemoteException;

    /**
     * Request for purchage packs
     * 
     * @param aRequest
     * @param retry
     * @return
     * @throws RemoteException
     */
    public Transaction submitPurchaseRequest(PurchaseTrxRequest aRequest, Transaction aTrx) throws RemoteException,
            MCDSException;

    /**
     * Return the transaction details using transaction id
     * 
     * @param trxId
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public PurchaseTrxResponse loadTransactionDetails(Integer trxId) throws MCDSException, RemoteException;

    /**
     * Return the searched content packs either search by code or title
     * 
     * @param aContentInfo
     * @param aSearchBy
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public ArrayList<ContentDetailTO> getSearchPackContents(String aContentInfo, String aSearchBy)
            throws MCDSException, RemoteException;

    /**
     * Return the first the contets for content menu
     * 
     * @return
     * @throws RemoteException
     * @throws MCDSException
     */
    public HashMap<String, String> getTerminalMenuContent(int aggID) throws RemoteException, MCDSException;

    /**
     * Get Contents list for UI to Show/Manage
     * 
     * @return
     * @throws MCDSException
     */
    public ArrayList<ContentDetailsforUI> getContents(Integer contentId, Integer aggrId) throws MCDSException,
            RemoteException;

    /**
     * return configuration parameter as Number
     * 
     * @param name
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public Number getConfigNumber(String name) throws MCDSException, RemoteException;

    /**
     * @param name
     * @return
     */
    public String getConfigString(String name) throws MCDSException, RemoteException;

    /**
     * This method Load both Local Transaction and Remote Transaction Status.
     * 
     * @param transactionId
     * @return
     * @throws MCDSException
     */
    public TransactionDetail getTransactionStatus(Integer transactionId) throws RemoteException, MCDSException;

    /**
     * Return the searched content packs either search by code or title
     * 
     * @param aContentInfo
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public ArrayList<ContentDetailTO> getSearchPackContents(String aContentInfo) throws MCDSException, RemoteException;

    /**
     * Update the settled transaction status
     * 
     * @param aTrxId
     * @param aStatus
     * @param aDescription
     * @return
     * @throws RemoteException
     * @throws MCDSException
     */
    public Integer updateStatus(Integer aTrxId, TrxStatus aStatus, String aDescription) throws MCDSException,
            RemoteException;

    /**
     * Return the transaction detail
     * 
     * @param aTrxId
     * @return
     * @throws RemoteException
     * @throws MCDSException
     */
    public Transaction getLocalTransactionStatus(Integer aTrxId) throws RemoteException, MCDSException;

    /**
     * Display all transactions on Search Transaction Page
     * 
     * @return
     * @throws RemoteException
     * @throws MCDSException
     */
    public List<Transaction> searchAllTransaction(SearchTrxRequest srchTrxRequest) throws RemoteException,
            MCDSException;

    /**
     * Returns the top 10 contents
     * 
     * @param aContentId
     * @return
     * @throws RemoteException
     */
    public ArrayList getTopContents(String aContentId) throws RemoteException;

    /**
     * Retrun parent id for back purpose in Top 10 contents
     * 
     * @param aContentId
     * @return
     * @throws RemoteException
     */
    public String getParentId(String aContentId) throws RemoteException;

    /**
     * Return the all mode of delivery
     * 
     * @return
     * @throws RemoteException
     */
    public ArrayList getDeliveryMode() throws MCDSException, RemoteException;

    /**
     * confirm the make and model and return the transaction object
     * 
     * @param ptr
     * @param make
     * @param model
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public Transaction confirmDetail(String make, String model) throws MCDSException, RemoteException;

    /**
     * @return
     * @throws RemoteException
     * @throws MCDSException
     */
    public ArrayList<Configuration> loadConfigurationData() throws RemoteException, MCDSException;

    /**
     * @param cfgDataList
     * @return
     * @throws RemoteException
     * @throws MCDSException
     */
    public ArrayList<Configuration> updateConfigurationData(ArrayList<Configuration> cfgDataList)
            throws RemoteException, MCDSException;

    /**
     * Return Aggregators List
     * 
     * @return
     * @throws RemoteException
     * @throws MCDSException
     */
    public List<AggregatorInfo> loadAggregators() throws RemoteException, MCDSException;

    /**
     * Get Pack details
     * 
     * @param contentId
     * @return
     * @throws MCDSException
     */
    public PackDetailForUI getPackDetail(Integer contentId) throws MCDSException, RemoteException;

    /**
     * @param trxId
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public Boolean resendContentReq(Integer trxId) throws MCDSException, RemoteException;

    /**
     * return info about content
     * 
     * @param aContentId
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public ContentInfo getContentInfo(Long aContentId) throws MCDSException, RemoteException;

    /**
     * @param aId
     * @param aType
     * @param aValue
     * @throws MCDSException
     * @throws RemoteException
     */
    public void setContentStatus(String aId, String aType, String aValue) throws MCDSException, RemoteException;

    /**
     * Returns an Aggregator's Information
     * 
     * @param aId
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public AggregatorInfo getAggregatorInfo(Integer aId) throws MCDSException, RemoteException;

    /**
     * Updates Aggregator table
     * 
     * @param aggrInfo
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public Boolean updateAggrInfo(AggregatorInfo aggrInfo) throws MCDSException, RemoteException;

    /**
     * Update the content informations
     * 
     * @param aContentInfo
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public Boolean updateContentInfo(ContentInfo aContentInfo) throws MCDSException, RemoteException;

    /**
     * Get Class Surcharge
     * 
     * @param classCode
     * @param aggId
     * @return
     * @throws MCDSException
     */
    public ClassSurcharge getClassSurcharge(String classCode, int aggId) throws MCDSException, RemoteException;

    /**
     * Add Class Surcharge
     * 
     * @param info
     * @throws MCDSException
     * @throws SQLException
     */
    public void addClassSurcharge(ClassSurcharge info) throws MCDSException, SQLException, RemoteException;

    /**
     * Get All Class Surcharge
     * 
     * @param classCode
     * @param aggId
     * @return
     * @throws MCDSException
     */
    public ArrayList<ClassSurcharge> getClassSurcharge() throws MCDSException, RemoteException;

    /**
     * Return Commission formula info for commission sui
     * 
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public List getCommFormula() throws MCDSException, RemoteException;

    /**
     * Return Commission formula info for commission sui
     * 
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public CommFormulaInfo getFormulas(Integer id) throws MCDSException, RemoteException;

    /**
     * Update the commission formula
     * 
     * @param commFormula
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public Boolean isUpdatedCommission(CommFormulaInfo commFormula) throws MCDSException, RemoteException;

    /**
     * This method is executing sql queries for finding id,aggregator
     * id,commission fourmula id and delno from comm_formula_mapping table
     * 
     * @throws MCDSException
     * @return List
     */
    public List<CommFormulaMappingInfo> getCommFormulaMapping() throws MCDSException, RemoteException;

    /**
     * This method is executing sql queries for finding id,aggregator
     * id,commission fourmula id and delno from comm_formula_mapping table
     * 
     * @throws MCDSException
     * @return List
     */
    public CommFormulaMappingInfo getCommFormulaMapping(Integer id) throws MCDSException, RemoteException;

    /**
     * Update the commission formula mapping
     * 
     * @param commFormula
     * @return
     * @throws MCDSException
     */
    public Boolean isUpdatedCommissionMapping(CommFormulaMappingInfo commFormula) throws MCDSException, RemoteException;

    /**
     * Return Partner name list
     * 
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public List<NameIdInfo> getPartenerNameList() throws MCDSException, RemoteException;

    /**
     * Add the new commission formula into table
     * 
     * @param info
     * @throws MCDSException
     */
    public void addCommission(CommFormulaInfo info) throws MCDSException, RemoteException;

    /**
     * retrun the object for add view
     * 
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public CommFormulaMappingInfo getCommForMap() throws MCDSException, RemoteException;

    /**
     * Add the new commission formula mapping into table
     * 
     * @param info
     * @throws MCDSException
     */
    public void addCommissionMapping(CommFormulaMappingInfo info) throws MCDSException, RemoteException;

    /**
     * Returns a List of Partners if null is provided else returns a particular
     * PartnerInfo
     * 
     * @param partner
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public List<PartnerInfo> getPartnerList(Partner partner) throws MCDSException, RemoteException;

    /**
     * @param partner
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public Boolean updatePartnerInfo(Partner partner) throws MCDSException, RemoteException;

    /**
     * To save Partner Values
     * @param partner
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */

    public void savePartnerValues(Partner partner) throws MCDSException, RemoteException;
    /**
     * To add new  Partner Values
     * @param partner
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public void addPartnerValues(Partner partner) throws MCDSException, RemoteException;
    /**
     * To surcharge Values
     * @param ClassSurcharge
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public void addSurchrage(ClassSurcharge surcharge) throws MCDSException, RemoteException;
    /**
     * To save Surcharge Values
     * @param ClassSurcharge
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public void saveSurchargeValues(ClassSurcharge surcharge)throws MCDSException, RemoteException;
    /**
     * This method is executing sql queries for finding phone mapping info list from phone_mapping table
     * 
     * @throws MCDSException
     * @return List
     */
    public List<PhoneMappingInfo> getPhoneMapping()throws MCDSException, RemoteException;
    /**
     * This method is executing sql queries for finding phone mapping info from phone_mapping table
     * 
     * @throws MCDSException
     * @return List
     */
    public PhoneMappingInfo getPhoneMapping(String aggCode)throws MCDSException, RemoteException;
    /**
     * Update the phone mapping
     * 
     * @param commFormula
     * @return
     * @throws MCDSException
     */
    public Boolean isUpdatedPhoneMapping(PhoneMappingInfo phoneMapping)throws MCDSException, RemoteException;

    /**
     * 
     * @TODO document me
     * @throws MCDSException
     * @throws RemoteException
     */
    public void refreshContent() throws MCDSException, RemoteException;

    public void refreshPhoneMapping()throws MCDSException, RemoteException;
}
