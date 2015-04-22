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
package com.gisil.mcds.aggregator.mauj.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.gisil.mcds.base.MCDSException;

public class PurchaseResponseParser extends AbstractParser {

	private String _response = null;

	private String _errorCode;

	private Integer _maujTransID;

	private String _responseType = null;

	/**
	 * PurchaseResponseParser
	 * 
	 * @param url
	 * @throws MCDSException
	 */
	public PurchaseResponseParser(String url) throws MCDSException {

		_response = parseResponse(url);

		// parse the reponse
		if (_response.indexOf(":") > 0) {
			_responseType = _response.substring(0, _response.indexOf(":"));

			if (_responseType.equals("SUCCESS")) {
				_maujTransID = Integer.parseInt(_response.substring(
						_response.indexOf(":") + 1).trim());
			} else {
				_errorCode = _response.substring(_response.indexOf(":") + 1)
						.trim();
			}
		}

	}

	/**
	 * This methos parse the reponse
	 * 
	 * @param url
	 * @return
	 * @throws MCDSException
	 */
	public String parseResponse(String url) throws MCDSException {

		final String METHOD_NAME = "parseResponse(...)";

		String response = null;
		InputStream stream = sendGetRequest(url);
		
		if ( stream == null )
		{
			throw new MCDSException( "Remote Server Not Available" );
		}
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		try {
			response = reader.readLine().toString();
		} catch (IOException ioExp) {
			throw new MCDSException("Exception Occured in Method[ "
					+ METHOD_NAME + " ]" + ioExp.toString());
		}
		
		return response;
	}

	/**
	 * @return Returns the _errorCode.
	 */
	public String getErrorCode() {
		return _errorCode;
	}

	/**
	 * @return Returns the _maujTransID.
	 */
	public Integer getMaujTransID() {
		return _maujTransID;
	}

	/**
	 * @return Returns the _response.
	 */
	public String getResponse() {
		return _response;
	}

	/**
	 * @return Returns the _responseType.
	 */
	public String getResponseType() {
		return _responseType;
	}

}
