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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gisil.mcds.aggregator.mauj.entity.AbstractTransTrackerResponse;
import com.gisil.mcds.aggregator.mauj.entity.TransTrackerResErr;
import com.gisil.mcds.aggregator.mauj.entity.ValidTransTrackerRes;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;

/**
 * 
 * @author amit sachan
 * 
 */
public class TransactionTrackerParser extends AbstractParser {

	IConfigurationManager mgr;

	/**
	 * TransactionTrackerParser
	 * 
	 */
	public TransactionTrackerParser() {
		super();
		mgr = ConfigurationManagerImpl.getConfigurationManager();
	}

	/**
	 * This methos will parse TransactionTracker
	 * 
	 * @param url
	 * @return
	 * @throws MCDSException
	 */
	public AbstractTransTrackerResponse parseTransactionTracker(String url)
			throws MCDSException {
		final String METHOD_NAME = "parseTransactionTracker(...)";
		AbstractTransTrackerResponse response;

		InputStream xmlDataInputStream = sendGetRequest(url);
		try {
			DocumentBuilder builder = _factory.newDocumentBuilder();
			Document document = builder.parse(xmlDataInputStream);
			Element element = document.getDocumentElement();
			if (mgr.getConfig(MCDSConfig.MAUJ_MG_DOWNLOADS_VERSION).equals(
					element.getAttribute("version"))) {
				response = getResponse(element);

			} else {
				throw new MCDSException("Invalid Download version from Mauj");
			}
			return response;
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
	 * This method returns the response transaction tracker
	 * 
	 * @param element
	 * @return
	 */
	private AbstractTransTrackerResponse getResponse(Element element) {
		NodeList nodeList = element.getChildNodes();
		Node rootNode = nodeList.item(0);

		if (rootNode != null && rootNode.getNodeName().equals("trandata")
				&& rootNode.getNodeType() == 1) {
			nodeList = rootNode.getChildNodes();
			// setted it on transdata
			Node node = nodeList.item(0);
			if (node != null && node.getNodeName().equals("mtranid")
					&& node.getNodeType() == 1) {
				ValidTransTrackerRes response = parseValidResponse(node);
				return response;

			} else if (node != null && node.getNodeName().equals("error")
					&& node.getNodeType() == 1) {
				TransTrackerResErr response = new TransTrackerResErr();
				response.setErrorMessage(node.getTextContent());

				return response;
			}
		}
		return null;
	}

	/**
	 * This method generate the valid reponse with parsing
	 * 
	 * @param node
	 * @return
	 */
	private ValidTransTrackerRes parseValidResponse(Node node) {

		ValidTransTrackerRes response = new ValidTransTrackerRes();
		response.setTransId(parseNamedItemValue("id", node));

		NodeList itemSubNodeList = node.getChildNodes();

		for (int index = 0; index < itemSubNodeList.getLength(); index++) {
			Node subNode = itemSubNodeList.item(index);

			if (subNode != null && subNode.getNodeName().equals("msisdn")
					&& subNode.getNodeType() == 1) {
				response.setMsisdnNo(subNode.getTextContent());
			} else if (subNode != null
					&& subNode.getNodeName().equals("phone_make")
					&& subNode.getNodeType() == 1) {
				response.setPhoneMake(subNode.getTextContent());
			} else if (subNode != null
					&& subNode.getNodeName().equals("phone_model")
					&& subNode.getNodeType() == 1) {
				response.setPhoneModel(subNode.getTextContent());
			} else if (subNode != null && subNode.getNodeName().equals("cost")
					&& subNode.getNodeType() == 1) {
				response.setCost(subNode.getTextContent());
			} else if (subNode != null
					&& subNode.getNodeName().equals("sku_code")
					&& subNode.getNodeType() == 1) {
				response.setSku(subNode.getTextContent());
			} else if (subNode != null
					&& subNode.getNodeName().equals("tran_status")
					&& subNode.getNodeType() == 1) {
				response.setTransStatus(subNode.getTextContent());
			} else if (subNode != null
					&& subNode.getNodeName().equals("error_code")
					&& subNode.getNodeType() == 1) {
				response.setErrorCode(subNode.getTextContent());
			} else if (subNode != null && subNode.getNodeName().equals("posid")
					&& subNode.getNodeType() == 1) {
				response.setPosId(subNode.getTextContent());
			} else if (subNode != null && subNode.getNodeName().equals("locid")
					&& subNode.getNodeType() == 1) {
				response.setLocId(subNode.getTextContent());
			} else if (subNode != null
					&& subNode.getNodeName().equals("logtime")
					&& subNode.getNodeType() == 1) {
				response.setLogTime(subNode.getTextContent());
			}

		}
		return response;
	}

}
