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

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;

/**
 * 
 * @author ashok kumar
 * 
 */

public class Transporter {

	private HttpClient _client = null;
	
	private IConfigurationManager mgr = null;

	/**
	 * 
	 * Transporter
	 */
	public Transporter() throws MCDSException{
		mgr = ConfigurationManagerImpl.getConfigurationManager();
		HttpClientParams params = new HttpClientParams();
		params.setConnectionManagerTimeout(mgr.getConfigAsNumber(MCDSConfig.TRANSPORT_CONNECT_TIMEOUT).longValue());
		params.setSoTimeout(mgr.getConfigAsNumber(MCDSConfig.TRANSPORT_TIMEOUT).intValue());
		_client = new HttpClient(params);
	}

	/**
	 * execute url ans return input stream
	 * 
	 * @param url
	 * @return
	 * @throws MCDSException
	 */
	public InputStream execute(String url) throws MCDSException {
		final String METHOD_NAME = "execute(.....)";
		int statusCode = 0;
		InputStream responseBodyStream = null;
		
		if ( mgr.getConfigAsBoolean( MCDSConfig.SIMULATOR_STATUS_FLAG ) )
		{
			url = getSimulatorUrl( url );
		}

		HttpMethod httpMethod = new GetMethod(url);
		try {
			statusCode = _client.executeMethod(httpMethod);

			if (statusCode == HttpStatus.SC_OK) {
				responseBodyStream = httpMethod.getResponseBodyAsStream();
			} else {
				throw new MCDSException(" Method Failed : "
						+ httpMethod.getStatusLine());
			}
		}

		catch (IOException ioExp) {
			System.out
					.println("IOException Occured while sending request to server in method [ "
							+ METHOD_NAME + " ] ");
		}
		return responseBodyStream;
	}
	
	private String  getSimulatorUrl( String url ) throws MCDSException
	{
		String simulatorUrl = "";
		String simulatorPreurl = mgr.getConfig( MCDSConfig.SIMULATOR_PRE_URL );
		String queryString = url.substring( url.indexOf( "?" ) );
		simulatorUrl = simulatorPreurl + queryString;
		return simulatorUrl;
	}
	
}
