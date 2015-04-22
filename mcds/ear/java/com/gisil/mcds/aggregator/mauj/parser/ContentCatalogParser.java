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

import com.gisil.mcds.aggregator.mauj.entity.Catalog;
import com.gisil.mcds.aggregator.mauj.entity.ContentPack;
import com.gisil.mcds.aggregator.mauj.entity.Item;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;

/**
 * 
 * @author ashok kumar
 * 
 */

public class ContentCatalogParser extends AbstractParser {

	public ContentCatalogParser() {
		super();
	}

	/**
	 * parse the content catalog
	 * 
	 * @param url
	 * @return
	 * @throws MCDSException
	 */
	public Catalog parseContentCatalog(String url) throws MCDSException {
		final String METHOD_NAME = "parseContentCatalog(...)";
		IConfigurationManager mgr = ConfigurationManagerImpl
				.getConfigurationManager();
		Catalog catalog = new Catalog();

		InputStream xmlDataInputStream = sendGetRequest(url);
		try {
			DocumentBuilder builder = _factory.newDocumentBuilder();
			Document document = builder.parse(xmlDataInputStream);
			Element element = document.getDocumentElement();

			// check If the downloads version is same as provided by Mauj
			if (mgr.getConfig(MCDSConfig.MAUJ_MG_DOWNLOADS_VERSION).equals(
					element.getAttribute("version"))) {

				NodeList nodeList = element.getChildNodes();

				Node rootNode = nodeList.item(0);

				if (rootNode != null
						&& rootNode.getNodeName().equals("catalog")
						&& rootNode.getNodeType() == 1) {

					catalog.setId(parseNamedItemValue("id", rootNode));
					ArrayList<ContentPack> contentPackList = parseContentPackages(rootNode);
					catalog.setContentPackList(contentPackList);
				}
			}
			return catalog;

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
	 * parse the content packages
	 * 
	 * @param rootNode
	 * @return
	 */
	private ArrayList<ContentPack> parseContentPackages(Node rootNode) {
		ArrayList<ContentPack> contentPackList = new ArrayList<ContentPack>();
		NodeList childList = rootNode.getChildNodes();

		for (int index = 0; index < childList.getLength(); index++) {
			ContentPack contentPack = null;

			Node childNode = childList.item(index);

			if (childNode != null
					&& childNode.getNodeName().equals("package_type")
					&& childNode.getNodeType() == 1) {
				contentPack = parseContentPack(childNode);
				String packageType = parseNamedItemValue("section", childNode);
				contentPack.setType(packageType);

			}
			contentPackList.add(contentPack);
		}
		return contentPackList;
	}

	/**
	 * parse the content pack
	 * 
	 * @param packageTypeNode
	 * @return
	 */
	private ContentPack parseContentPack(Node packageTypeNode) {
		ContentPack contentPack = new ContentPack();
		ArrayList<Item> contentPackItemList = new ArrayList<Item>();

		NodeList packageTypeSubNodeList = packageTypeNode.getChildNodes();

		for (int count = 0; count < packageTypeSubNodeList.getLength(); count++) {
			Node packageNode = packageTypeSubNodeList.item(count);

			if (packageNode != null
					&& packageNode.getNodeName().equals("package")
					&& packageNode.getNodeType() == 1) {
				String skuCode = parseNamedItemValue("skucode", packageNode);
				contentPack.setSku(skuCode);

				NodeList packageSubNodeList = packageNode.getChildNodes();

				for (int index = 0; index < packageSubNodeList.getLength(); index++) {
					Node packageSubNode = packageSubNodeList.item(index);

					if (packageSubNode != null
							&& packageSubNode.getNodeName().equals(
									"package_name")
							&& packageSubNode.getNodeType() == 1) {
						String packageTitle = packageSubNode.getTextContent();
						contentPack.setTitle(packageTitle);
					} else if (packageSubNode != null
							&& packageSubNode.getNodeName().equals(
									"package_cost")
							&& packageSubNode.getNodeType() == 1) {
						String packageCost = packageSubNode.getTextContent();
						double cost = Double.parseDouble(packageCost);
						contentPack.setCost(cost);
					} else if (packageSubNode != null
							&& packageSubNode.getNodeName().equals(
									"package_description")
							&& packageSubNode.getNodeType() == 1) {
						String pkgDescription = packageSubNode.getTextContent();
						contentPack.setDescription(pkgDescription);
					} else if (packageSubNode != null
							&& packageSubNode.getNodeName().equals("item")
							&& packageSubNode.getNodeType() == 1) {
						Item item = parseItemAttribute(packageSubNode);
						contentPackItemList.add(item);
					}
				}

			}

		}

		contentPack.setItemList(contentPackItemList);

		return contentPack;
	}

	/**
	 * parse item attribute
	 * 
	 * @param itemNode
	 * @return
	 */
	private Item parseItemAttribute(Node itemNode) {
		Item item = new Item();
		NodeList itemSubNodeList = itemNode.getChildNodes();
		String itemId = parseNamedItemValue("id", itemNode);
		long id = Long.parseLong(itemId);
		item.setItemId(id);

		// For Item type
		String itemType = parseNamedItemValue("mime", itemNode);
		item.setType(itemType);

		for (int index = 0; index < itemSubNodeList.getLength(); index++) {
			Node itemSubNode = itemSubNodeList.item(index);

			if (itemSubNode != null
					&& itemSubNode.getNodeName().equals("title")
					&& itemSubNode.getNodeType() == 1) {
				item.setTitle(itemSubNode.getTextContent());
			}

		}

		return item;
	}

}
