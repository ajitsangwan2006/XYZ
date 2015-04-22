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

package com.gisil.mcds.aggregator;

import java.util.logging.Logger;

import com.gisil.mcds.aggregator.mauj.Mauj;
import com.gisil.mcds.base.IComponent;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.util.LogUtil;

/**
 * A reference of this class is used in MCDSBean 
 * to actually call methods of AggregatorDBMgr.java 
 * @author
 *
 */
public class AggregatorMgr implements IComponent{

	private static AggregatorMgr instance;

    private AggregatorDBMgr _agrDBMgr;
    
    private Mauj _mauj;

    private Logger _log;
     
	private String USERNAME = null;
	
	private String PASSWORD = null;
	
	private IConfigurationManager mgr;
	
	private static Integer _serExpCount = 0;
	
    /**
	 * @return Returns the serExpCount.
	 */
	public Integer getSerExpCount() {
		return _serExpCount;
	}

	/**
	 * @param serExpCount The serExpCount to set.
	 */
	public void setSerExpCount(Integer serExpCount) {
		_serExpCount = serExpCount;
	}

	public AggregatorMgr() {
        super();
    }

    public static AggregatorMgr getAggregatorMgr() {
        return instance;
    }

    public void preStartup() throws MCDSException {
        _log = LogUtil.getLogger(getClass());
        instance = this;
    }

    public void postStartup() throws MCDSException {
    	_log.info("Looking up for AggregatorDB");
    	_agrDBMgr = new AggregatorDBMgr();
    	_mauj = new Mauj();
    	USERNAME =_mauj.getUSERNAME();
    	PASSWORD = _mauj.getPASSWORD();
    	mgr = _mauj.getMgr();
    	_log.info("setted Mauj credential");
    }

   public AggregatorDBMgr getAgrDBMgr() {
        return _agrDBMgr;
    }

  
	public void shutDown() {
		 instance = null;
		
	}
	
	public Mauj getAggregator(Integer aggregatorId) {
		// TODO Will Impliement the logic here
        return new Mauj(USERNAME,PASSWORD, mgr);
    }
}
