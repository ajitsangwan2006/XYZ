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

public enum TrxStatus {

    NEW {// done

        @Override
        public String toDBLiteral() {
            return "New";
        }
    },

    AUTHORIZED { // done

        @Override
        public String toDBLiteral() {
            return "Authorized";
        }
    },
    
    AUTH_DENIED { // done

        @Override
        public String toDBLiteral() {
            return "Auth Denied";
        }
    },
    DEBIT_EX { // done

        @Override
        public String toDBLiteral() {
            return "Debit Exception";
        }
    },

    DEBITED { // done

        @Override
        public String toDBLiteral() {
            return "Debited";
        }
    },

    ACCOUNTED_EX { // done

        @Override
        public String toDBLiteral() {
            return "Credit Exception";
        }
    },

    ACCOUNTED { // done

        @Override
        public String toDBLiteral() {
            return "Credited";
        }
    },

    BUYING_SUBMITTED {

        @Override
        public String toDBLiteral() {
            return "Buying Submitted";
        }
    },

    BUYING {

        @Override
        public String toDBLiteral() {
            return "Buying Successful";
        }
    },
    BUYING_DENIED {

        @Override
        public String toDBLiteral() {
            return "Buying Denied";
        }
    },
    SERVICE_EX {

        @Override
        public String toDBLiteral() {
            return "Buying Exception";
        }
    },

    BUYING_RETRY_SUBMITTED {

        @Override
        public String toDBLiteral() {
            return "Buying Retry Submitted";
        }
    },

    SERVICE_EX_RETRY {

        @Override
        public String toDBLiteral() {
            return "Buying Retry Exception";
        }
    },

    SETTELED {

        @Override
        public String toDBLiteral() {
            return "Settled";
        }
    };
    public abstract String toDBLiteral();

    public String toString() {
        return toDBLiteral();
    }

}
