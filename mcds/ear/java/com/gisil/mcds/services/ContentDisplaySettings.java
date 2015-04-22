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
package com.gisil.mcds.services;

import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.util.ConfigValuesWrapper;
/**
 * 
 * @author ajit singh
 *
 */
public class ContentDisplaySettings {
	/**
	 * Return the pack name in format string
	 * 
	 * @param aTitle
	 * @return
	 */
	public static String getContentPackTitle(String aTitle) {
		int size = ConfigValuesWrapper.getValueAsNumber(
				MCDSConfig.TERMINAL_MAX_CONTENT_PACK_NAME).intValue();
		if (aTitle.length() < size)
			for (int loop = aTitle.length(); loop <= size; loop++)
				aTitle = aTitle + " ";
		if (aTitle.length() > size)
			aTitle = aTitle.substring(0, size);
		return aTitle;
	}

	/**
	 * Return the content name in format string
	 * 
	 * @param aTitle
	 * @return
	 */
	public static String getPackTitle(String aTitle) {
		int size = ConfigValuesWrapper.getValueAsNumber(
				MCDSConfig.TERMINAL_MAX_CONTENT_PACK_NAME).intValue() - 5;

		if (aTitle.length() > size)
			aTitle = aTitle.substring(0, size);
		return aTitle;
	}

	/**
	 * Retrun the complete line after formating the string
	 * 
	 * @param aTitle
	 * @return
	 */
	public static String getCompleteLine(String aTitle) {
		int size = ConfigValuesWrapper.getValueAsNumber(
				MCDSConfig.TERMINAL_MAX_WIDTH).intValue();

		if (aTitle.length() > size)
			aTitle = aTitle.substring(0, size);
		return aTitle;
	}

	/**
	 * Return the formated song title
	 * 
	 * @param aTitle
	 * @return
	 */
	public static String getSongTitle(String aTitle) {
		int size = ConfigValuesWrapper.getValueAsNumber(
				MCDSConfig.TERMINAL_MAX_SONG_TITLE_NAME).intValue();

		if (aTitle.length() > size)
			aTitle = aTitle.substring(0, size);
		return aTitle;
	}

}
