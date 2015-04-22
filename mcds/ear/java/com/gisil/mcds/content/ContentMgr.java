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
package com.gisil.mcds.content;

import java.util.logging.Logger;

import com.gisil.mcds.base.IComponent;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.util.LogUtil;

/**
 * @TODO document me
 * @author amit sachan
 */
public class ContentMgr implements IComponent {

	private static ContentMgr instance;

	private ContentDBMgr _contentDBMgr;

	private Logger _log;

	/**
	 * @TODO document me
	 */
	public ContentMgr() {
		super();
	}

	public static ContentMgr getContentMgr() {
		return instance;
	}

	public void preStartup() throws MCDSException {
		_log = LogUtil.getLogger(getClass());
		instance = this;
	}

	public void postStartup() throws MCDSException {
		_log.info("Content Manager Started....");
		_contentDBMgr = new ContentDBMgr();
	}

	public void shutDown() {
		_log.info("Stopping Content Manager....");
		instance = null;
	}

	public ContentDBMgr getContentDBMgr() {
		return _contentDBMgr;
	}

}
