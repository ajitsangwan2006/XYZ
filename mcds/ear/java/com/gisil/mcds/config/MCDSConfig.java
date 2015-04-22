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

package com.gisil.mcds.config;
public interface MCDSConfig 
{
    String JNDI_INITIAL_CONTEXT_FACTORY = "mcds.jndi.factory.name";

    String JNDI_INITIAL_CONTEXT_FACTORY_VAL = "weblogic.jndi.WLInitialContextFactory";

    String JNDI_PROVIDER_URL = "mcds.jndi.url";

    String JNDI_PROVIDER_URL_VAL = "t3://localhost:7001";

    String JNDI_SECURITY_PRINCIPAL = "securityPrincipal";

    String JNDI_SECURITY_PRINCIPAL_VAL = "weblogic";

    String JNDI_SECURITY_CREDENTIALS = "securityCredentials";

    String JNDI_SECURITY_CREDENTIALS_VAL = "weblogic";

    String DS_NAME = "mcds.db.ds-name";

    String DS_NAME_VAL = "gisil.mcds.jdbc.mcds-ds";

    String DS_CREDNTIAL = "";
    
    String DS_AUDIT_TRAIL = "mcds.audit.trail";
    
    String MAUJ_MG_DOWNLOADS_VERSION = "aggregator.mauj.mgdownloadsversion";
    
    String MAUJ_DOWNLOAD_SERVER = "aggregator.mauj.mgserver";
    
    String MAUJ_DEFAULT_POS_ID = "aggregator.mauj.default.posid";
    
    String MAUJ_DEFAULT_LOC_ID = "aggregator.mauj.default.locid";
    
    String MAUJ_CONTENT_OVERRIDE = "aggregator.mauj.isoverrideprevious";
    
    String MAUJ_GET_CATALOG_URL = "aggregator.mauj.contenturl";
    
    String MAUJ_GET_PHONE_MAP_URL = "aggregator.mauj.phonemappingurl";
    
    String MAUJ_GET_PURCHASE_URL = "aggregator.mauj.purchaseurl";
    
    String CONTENT_PACK_PARENT_ID = "terminal.contentpack.contentid";
    
    String TERMINAL_MAX_CONTENT_PACK_NAME = "terminal.contentpack.name.size";
    
    String TERMINAL_MAX_WIDTH = "terminal.width";
    
    String TERMINAL_MAX_SONG_TITLE_NAME = "terminal.songtitle.size";
    
    String MAUJ_GET_TRANS_TRACKER_URL = "aggregator.mauj.statusurl";
    
    String THEMIS_JNDI_NAME = "themis.jndi.name";
    
    String MAIL_HOST = "mcds.mail.host";

    String MAIL_PORT = "mcds.mail.host.port";

    String MAIL_CONTENT_TYPE = "text/plain";

    String MAIL_SENDER = "mcds.mail.user";

    String MAIL_RECIPIENT = "mcds.mail.to";

    String ALLOW_REMOTE_STATUS_OF_NON_EX_TRX = "allow.remote.status.non.ex.trx";

    String RETRY_LIMIT = "retry.limit";

    String SUPPORT_EMAIL = "support.email";

    String TRANSPORT_TIMEOUT = "transport.timeout";

    String TRANSPORT_CONNECT_TIMEOUT = "transport.connect.timeout";
    
    String MAIL_SENDER_PASSWORD = "mcds.mail.user.password";
    
    String MAX_TRANSACTION_LIMIT = "terminal.display.transaction.max.limit"; 
    
    String TRANSACTION_HISTORY_AVLDAYS = "transaction.history.avldays";
    
    String MAUJ_COMPARE_PHONE_BY_GENERIC = "mauj.compare.phonebygeneric";
    
    String TERMINAL_TOP10_CONTENT_ID = "terminal.top10.contentid";
    
    String TERMINAL_CONTENTPACK_CONTENT_ID = "terminal.contentpack.contentid";

	String SEARCH_TEXT_MIN_LIMIT = "terminal.search.text.min.limit";
	
	String CMS_NAME = "mcds.cms.name";
	
	String DELIVERY_MODE_SMS = "menu.delivery.smsmode";
	
	String DELIVERY_MODE_BT = "menu.delivery.btmode";
    
	String MAUJ_GET_RESEND_URL = "aggregator.mauj.resendurl";
    
	String MAX_CONTENT_DISPLAY = "terminal.max.content.display";
	
	// Constants from Utils 
	String DEFAULT_COUNTRY_CODE = "default.country.code";

	String TERMINAL_DISPLAY_MAX_LEN = "terminal.display.max.len";

	String TERMINAL_DISPLAY_LABEL_MAX_LEN = "terminal.display.label.max.len";

	String TERMINAL_ROWS_LIMIT = "terminal.rows.limit";

	String  TERMINAL_DISPLAY_TITLE_LIMIT = "terminal.display.title.limit";

    String  TERMINAL_DISPLAY_CODE_LIMIT = "terminal.display.code.limit";
    
    String MAX_SERVICE_EXP_COUNT = "aggregator.max.serviceex.count";
    
    String MIN_EDIT_PRIVILEGE = "min.edit.privilege.level";	
    
    String SIMULATOR_STATUS_FLAG = "simulator.status.flag";
    
    String SIMULATOR_PRE_URL = "simulator.pre.url";
    
    String EDIT_LEVEL = "min.edit.privilege.level"; 
    
    String INPUT_RECON_DATE_FORMAT = "ddMMyy";
    
    String INPUT_RECON_FILE_NAME = "recon" + INPUT_RECON_DATE_FORMAT + ".csv";
    
    String INPUT_RECON_LOG_FILE_NAME = "log" + INPUT_RECON_DATE_FORMAT + ".log";
}
