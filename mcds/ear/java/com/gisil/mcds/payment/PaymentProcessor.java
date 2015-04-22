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

import java.util.Map;
import java.util.logging.Logger;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.commission.CommissionProcessor;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.util.LogUtil;

/**
 * @TODO document me
 * @author sandeep kadyan
 */
public abstract class PaymentProcessor {

    private CommissionProcessor _commissionProcessor;

    protected Logger _log;

    // taken from themis
    public static final String[] AUTH_RESP_MSG = new String[] { "Authorization Successful", "Merchant is Disabled",
            "Insufficient Funds", "System Internal error while checking merchant balance", };

    public enum PaymentMode {
        CASH {

            @Override
            public int toDBConstant() {
                return 1;
            }
        };

        public String toDbLiteral() {
            return name();
        }

        public abstract int toDBConstant();

    }

    /**
     * @TODO document me
     */
    public PaymentProcessor() {
        _commissionProcessor = CommissionProcessor.getInstance();
        _log = LogUtil.getLogger(getClass());
    }

    public static PaymentProcessor getPaymentProcessor(PaymentMode mode) {
        PaymentProcessor processor = null;
        switch (mode) {
            case CASH:
                processor = new CashPaymentProcessor();
                break;
            default:
                throw new RuntimeException("illegal payment mode[" + mode + "]");
        }

        return processor;
    }

    public abstract void process(Transaction trx) throws MCDSException;

    public abstract void destrory();

    public void processCommission(Transaction trx) throws MCDSException {
        Map<Integer, Number> commissions = trx.getPartnerCommissions();
        // if calculated don't calculate again.
        if (commissions == null) {
            _commissionProcessor.process(trx);
        }

        _log.info("commission processed. delno=" + trx.getDelNo() + " trxId=" + trx.getTrxId());
    }

    /**
     * Authentiacate the transaction for required amount. Return the status code
     * 0 -- OK 1 -- Account disabled 2 -- INSUFFICIENT_FUNDS 3 -- AUTH_ERROR
     */
    public int authenticate(Transaction trx) throws MCDSException {
        return 0;
    }
}
