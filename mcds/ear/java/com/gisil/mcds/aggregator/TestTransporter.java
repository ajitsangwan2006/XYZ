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

import java.util.ArrayList;
import java.util.Iterator;

import com.gisil.mcds.aggregator.mauj.entity.AbstractTransTrackerResponse;
import com.gisil.mcds.aggregator.mauj.entity.Catalog;
import com.gisil.mcds.aggregator.mauj.entity.ContentPack;
import com.gisil.mcds.aggregator.mauj.entity.HandSetMapping;
import com.gisil.mcds.aggregator.mauj.entity.Item;
import com.gisil.mcds.aggregator.mauj.parser.ContentCatalogParser;
import com.gisil.mcds.aggregator.mauj.parser.HandSetMappingParser;
import com.gisil.mcds.aggregator.mauj.parser.PurchaseResponseParser;
import com.gisil.mcds.aggregator.mauj.parser.TransactionTrackerParser;

public class TestTransporter {

	public static void main(String args[]) {
		//testCatalogParser();

		//testTransportParser();
		
		testPhoneMapping();
		
		//testPurchase();

	}

	public static void testCatalogParser() {
		String url = "http://www.mauj.com/retail/get_catalog.php?usr=indepay&pd=Pyx1oxL&ctgid=8&mgver=1";

		ContentCatalogParser parser = new ContentCatalogParser();

		try {
			Catalog catalog = parser.parseContentCatalog(url);

			System.out.println("============= Catalog Info ============");
			System.out.println(" Catalog ID : " + catalog.getId());

			ArrayList<ContentPack> packageList = catalog.getContentPackList();

			Iterator iterator = packageList.iterator();
			int count = 0;
			while (iterator.hasNext()) {
				ContentPack contentpk = (ContentPack) iterator.next();
				System.out
						.println("--------------------- Package Info -------------------");
				System.out.println(" Package No  [" + count
						+ "] :  Package Name : " + contentpk.getTitle());
				count++;
				ArrayList<Item> itemList = contentpk.getItemList();
				Iterator itemIterator = itemList.iterator();

				while (itemIterator.hasNext()) {
					Item item = (Item) itemIterator.next();
					System.out.println("------ Item Name : " + item.getTitle());
				}
				System.out
						.println("--------------------------------------------------------");

			}

		} catch (Exception exp) {
			System.out.println(exp);
		}
	}

	public static void testTransportParser() {

		String url = "http://www.mauj.com/retail/tran_status.php?usr=indepay&pd=Pyx1oxL&sku=T118&posid=1&locid=1&mtranid=9";

		TransactionTrackerParser parser = new TransactionTrackerParser();

		try {
			AbstractTransTrackerResponse response = parser
					.parseTransactionTracker(url);

			System.out.print(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testPhoneMapping() {
		String url = "http://www.mauj.com/retail/service_handset_support.php?usr=indepay&pd=Pyx1oxL";

		HandSetMappingParser parser = new HandSetMappingParser();

		try {
			ArrayList<HandSetMapping> catalog = parser.parseHandSetMapping(url);

			System.out.println("============= Phone Mapping Info ============");
			

			Iterator iterator = catalog.iterator();
			int count = 1;
			while (iterator.hasNext()) {
				HandSetMapping contentpk = (HandSetMapping) iterator.next();
				System.out
						.println("--------------------- Phone Info -------------------");
				System.out.println(" Phone No  [" + count
						+ "] :  Phone Code : " + contentpk.getAggCode());
				
				System.out.println(" Phone Make  [" + contentpk.getAggMake()
						+ "] :  Phone Model : " + contentpk.getAggModel());
				count++;
				

			}

		} catch (Exception exp) {
			System.out.println(exp);
		}
	}
	
	
	public static void testPurchase() {
		String url = "http://www.mauj.com/retail/retail_wrapper.php?usr=indepay&pd=Pyx1oxL&mo=919873091250&sku=T118&mk=NOKIA&ph=NOKIA6600&posid=1&locid=1&mtranid=9";

		

		try {
			
			PurchaseResponseParser parser = new PurchaseResponseParser(url);
			

			System.out.println("============= Purchase Info ============");
		
				System.out.println(" Type  [" + parser.getResponseType()
						+ "] :  code : " + parser.getErrorCode());
				
				
				

			

		} catch (Exception exp) {
			System.out.println(exp);
		}
	}
}
