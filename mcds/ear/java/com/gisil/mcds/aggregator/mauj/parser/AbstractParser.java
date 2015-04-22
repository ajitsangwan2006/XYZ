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

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gisil.mcds.aggregator.Transporter;
import com.gisil.mcds.base.MCDSException;

public class AbstractParser {

	DocumentBuilderFactory _factory = null;

	/**
	 * AbstractParser
	 *
	 */
	public AbstractParser() {
		_factory = DocumentBuilderFactory.newInstance();
	}

	/**
	 * send the request for execution
	 * @param url
	 * @return
	 * @throws MCDSException
	 */
	public InputStream sendGetRequest(String url) throws MCDSException {
		InputStream xmlDataStream = null;
		Transporter transporter = new Transporter();
		xmlDataStream = transporter.execute(url);
		return xmlDataStream;
	}

	/**
	 * parse the returned item description
	 * @param name
	 * @param node
	 * @return
	 */
	public String parseNamedItemValue(String name, Node node) {
		String value = null;
		NamedNodeMap namedNodeMap = node.getAttributes();
		Node namedItem = namedNodeMap.getNamedItem(name);

		if (namedItem != null && namedItem.getNodeType() == 2) {
			value = namedItem.getNodeValue();
		}
		return value;
	}

}
