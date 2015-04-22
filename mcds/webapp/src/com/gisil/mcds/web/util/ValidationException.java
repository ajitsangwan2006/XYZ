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
package com.gisil.mcds.web.util;

/**
 * The class<code>ValidationException</code> is the form of<code>RuntimeException</code>
 * indicates to validation exception at rumtime
 * 
 * @author amit sachan
 */
public class ValidationException extends RuntimeException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -3557246643320668205L;

    /**
     * constructs the validation exception
     */
    public ValidationException() {
        super();
    }

    /**
     * Constructs ths validation exception with supplied message
     * 
     * @param msg
     */
    public ValidationException(String msg) {
        super(msg);
    }

    /**
     * Constructs the validation exception with supplies message and cause
     * 
     * @param msg
     * @param cause
     */
    public ValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs the validation exception with supplied throwable cause
     * 
     * @param cause
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
