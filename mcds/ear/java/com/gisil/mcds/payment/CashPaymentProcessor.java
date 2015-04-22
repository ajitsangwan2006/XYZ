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
package com.gisil.mcds.payment;

import java.rmi.RemoteException;
import java.util.logging.Level;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.rmi.PortableRemoteObject;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.transaction.TrxStatus;
import com.gisil.mcds.util.JndiUtil;
import com.gisil.mcds.util.LogUtil;
import com.gisil.themis.common.ThemisException;
import com.gisil.themis.ejb.Themis;
import com.gisil.themis.ejb.ThemisHome;

/**
 * @TODO document me
 * @author sandeep kadyan
 */
public class CashPaymentProcessor extends PaymentProcessor {

    private Themis _themis;

    /**
     * @TODO document me
     */
    public CashPaymentProcessor() {
        super();
    }

    private void doInit() throws MCDSException {
        _log.info("doInit() called");
        try {
        	IConfigurationManager config = ConfigurationManagerImpl.getConfigurationManager();
            String jndiName = config.getConfig(MCDSConfig.THEMIS_JNDI_NAME);
            String lookupName = jndiName;// "java:comp/env/ejb/" + jndiName;
            Object o = JndiUtil.jndiLookUp(lookupName);

            _log.info("Themis object looked up successfully " + o);

            ThemisHome themisHome = (ThemisHome) PortableRemoteObject.narrow(o, ThemisHome.class);
            _log.info("Object is narrowed to themis home " + themisHome);

            _themis = themisHome.create();

            _log.info("themis instance is created" + _themis);

        } catch (EJBException e) {
            _log.log(Level.SEVERE, "EJB Exception occured while looking up themis.", e);
        } catch (CreateException e) {
            _log.log(Level.SEVERE, "Themis EJB Create Exception occured while ceating themis.", e);
        } catch (RemoteException e) {
            _log.log(Level.SEVERE, "Remote Exception occured while ceating themis.", e);
        } catch (MCDSException e) {
            _log.log(Level.SEVERE, "MCDS exception due to configuration error.", e);
        }

        if (_themis == null)
            throw new MCDSException("themis is not available. Can not process transaction ");

    }

    @Override
    public void process(Transaction trx) throws MCDSException {

        int id = trx.getTrxId().intValue();
        String delNo = trx.getDelNo();
        
        _log.fine("Payment process called for trxid=" + id + " and delNo=" + delNo);
       
        if (id < 0)
            throw new MCDSException("Payment can not be processed on holder transaction");

        if (delNo == null)
            throw new MCDSException("Payment can not be processed without delno");

        long delNoForThemisAPI = fixDelNo(delNo);

        // first process the commissions if trx is not an revert.
        if (!Transaction.TYPE_REVERT.equals(trx.getTrxType()))
            processCommission(trx);

        // now get the payment details.
        Number merchantAmt = trx.getMerchantAmt();

        if (merchantAmt == null)
            throw new MCDSException("merchant amount found null for trxId=" + id + " delNo=" + delNo);

        String type = trx.getTrxType();
        boolean doDebit = Transaction.TYPE_BUYING.equals(type);
        
   
        
        try {
           
            if(doDebit){
                int authstatus = authenticate(trx);
                if (authstatus != 0) 
                    trx.setTrxStatus(TrxStatus.AUTH_DENIED);
                _log.fine(" auth status = "+ authstatus);
                if (trx.getTrxStatus() != TrxStatus.AUTHORIZED)
                    throw new MCDSException(PaymentProcessor.AUTH_RESP_MSG[authstatus]);
            }    
            
           Themis themis = getThemisService();
           float balance = doDebit ? themis.debitAndReturnMerchantBalance(delNoForThemisAPI, merchantAmt.floatValue())
                        : themis.creditAndReturnMerchantBalance(delNoForThemisAPI, merchantAmt.floatValue());
            // set the balance to trx
            trx.setMerchantBalance(Double.valueOf(balance));
            trx.setTrxStatus(doDebit ? TrxStatus.DEBITED : TrxStatus.ACCOUNTED);
        } catch (RemoteException e) {
            _log.log(Level.SEVERE, "remote exception occured while doing debit for merchant " + delNoForThemisAPI
                    + " trxId=" + id, e);
            trx.setTrxStatus(doDebit ? TrxStatus.DEBIT_EX : TrxStatus.ACCOUNTED_EX);
        } catch (ThemisException e) {
            _log.log(Level.SEVERE, "themis exception occured while doing debit for merchant " + delNoForThemisAPI
                    + " trxId=" + id, e);
            trx.setTrxStatus(doDebit ? TrxStatus.DEBIT_EX : TrxStatus.ACCOUNTED_EX);
        } finally {
            _log.info("Payment Process Completed. TrxId=" + id + " delNo -" + delNo + " trxstatus - "
                    + trx.getTrxStatus() + " {" + merchantAmt + (doDebit ? " Debited }" : " Credited }"));
        }
    }

    TrxStatus mapTrxStatus(String trxType) {
        TrxStatus status = null;
        if (Transaction.TYPE_BUYING.equals(trxType)) {

        }
        return status;
    }

    private long fixDelNo(String delNo) throws MCDSException {
        try {
            return Long.valueOf(delNo);
        } catch (NumberFormatException e) {
            throw new MCDSException("internal error. del no must be in long format " + e.getMessage());
        }
    }

    private Themis getThemisService() throws MCDSException {
        // if not created yet call the doInit to create the remote object.
        if (_themis == null)
            doInit();

        return _themis;
    }

    @Override
    public void destrory() {
        _log.fine("CashPayment Processor destroy called");
        try {
            if (_themis != null) {
                _themis.remove();
                _log.info("themis removed successfully");
            }
        } catch (RemoteException e) {
            _log.warning("Remote exception occure while destroying the themis instance");
        } catch (RemoveException e) {
            _log.warning("Remove exception occure while destroying the themis instance");
        }
    }

    @Override
    public int authenticate(Transaction trx) throws MCDSException {
        Number amt = trx.getMerchantAmt();
        String delNo = trx.getDelNo();
        int status = -1;
        if (amt == null) {
            // amount is not calculated; use baseAmt instead
            amt = trx.getBaseAmt();
        }
        _log.info(delNo + " Trx - " + trx.getTrxId() + " is going to authenicate for amount = " + amt);
        if (amt != null) {
            long delNoForThemisAPI = fixDelNo(delNo);
            if (LogUtil.TEST_THEMIS_FLAG)
                status = 0;
            else {
                try {
                    Themis service = getThemisService();
                    status = service.checkMerchantTxnAmount(delNoForThemisAPI, amt.floatValue());
                } catch (RemoteException e) {
                    _log.log(Level.SEVERE, "communication error with themis", e);
                    status = Themis.AUTH_ERROR;
                } catch (MCDSException e) {
                    _log.log(Level.SEVERE, "MCDS exception while communicating with themis", e);
                    status = Themis.AUTH_ERROR;
                }
            }

        }
        if (status == 0) {
            trx.setTrxStatus(TrxStatus.AUTHORIZED);
        } else
            trx.setTrxStatus(TrxStatus.AUTH_DENIED);

        return status;
    }
}
