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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gisil.mcds.aggregator.mauj.entity.HandSetMapping;
import com.gisil.mcds.base.MCDSException;

public class HandSetMappingParser extends AbstractParser {

	public HandSetMappingParser() {
		super();
	}

	/**
	 * parse handset mapping
	 * 
	 * @param url
	 * @return
	 * @throws MCDSException
	 */
	public ArrayList<HandSetMapping> parseHandSetMapping(String url)
			throws MCDSException {
		final String METHOD_NAME = "parseHandSetMapping(...)";
		ArrayList<HandSetMapping> handSetMappings = new ArrayList<HandSetMapping>();

		InputStream xmlDataInputStream = sendGetRequest(url);
		try {
			DocumentBuilder builder = _factory.newDocumentBuilder();
			Document document = builder.parse(xmlDataInputStream);
			Element element = document.getDocumentElement();
			if (element != null
					&& element.getNodeName().equals("Service_Support")
					&& element.getNodeType() == 1) {

				NodeList list = element.getChildNodes();

				handSetMappings = parseMapping(list);

			}
			return handSetMappings;
		} catch (ParserConfigurationException parserConfExp) {
			throw new MCDSException("Exception Occured in Method[ "
					+ METHOD_NAME + " ]" + parserConfExp.toString());
		} catch (IOException ioExp) {
			throw new MCDSException("Exception Occured in Method[ "
					+ METHOD_NAME + " ]" + ioExp.toString());
		} catch (SAXException saxExp) {
			throw new MCDSException("Exception Occured in Method[ "
					+ METHOD_NAME + " ]" + saxExp.toString());
		}

	}

	/**
	 * parse the mappings
	 * 
	 * @param serviceList
	 * @return
	 */
	private ArrayList<HandSetMapping> parseMapping(NodeList serviceList) {

		ArrayList<HandSetMapping> mapping = new ArrayList<HandSetMapping>();

		for (int index = 0; index < serviceList.getLength(); index++) {

			mapping.addAll(getMappingforService(serviceList.item(index)));
		}

		return mapping;
	}

	/**
	 * get mapping for service
	 * 
	 * @param rootNode
	 * @return
	 */
	private ArrayList<HandSetMapping> getMappingforService(Node rootNode) {

		ArrayList<HandSetMapping> mapping = new ArrayList<HandSetMapping>();
		String serviceName = parseNamedItemValue("name", rootNode);

		NodeList list = rootNode.getChildNodes();
		for (int index = 0; index < list.getLength(); index++) {

			Node node = list.item(index);
			HandSetMapping map = new HandSetMapping();
			map.setServiceType(serviceName);
			map.setAggCode(node.getTextContent());
			if (node.getTextContent().length() > 4) {
				map.setAggMake(node.getTextContent().substring(0, 4));
			} else {
				map.setAggMake(node.getTextContent());
			}
			map.setAggModel(parseNamedItemValue("name", node));
			mapping.add(map);
		}

		return mapping;
	}
}
