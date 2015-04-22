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
package com.gisil.mcds.util;

import java.util.logging.Logger;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.config.IConfigurationManager;

/**
 * 
 * @author amit sachan
 *
 */
public class EMailAlert {

    /**
     * @TODO document me
     */
    private static Logger _log = LogUtil.getLogger(EMailSender.class);

    public EMailAlert() {
        super();

    }

    public static void sendMailForDownloadContent(String subject, String content)
            throws MCDSException {
        IConfigurationManager config = ConfigurationManagerImpl.getConfigurationManager();
        String to = config.getConfig(MCDSConfig.SUPPORT_EMAIL);
        String text = content;

        EMailSender.sendMail(to, subject, text);
    }

    private static String exceptionToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] elements = e.getStackTrace();
        sb.append(e.getMessage());
        for (StackTraceElement element : elements) {
            sb.append('\n').append(element.toString());
        }
        Throwable cause = e.getCause();
        if (cause != null) {
            sb.append("Cause By\n");
            sb.append(exceptionToString(cause));
        }

        return sb.toString();
    }

    public static void sendTransportFailureAlert(Integer id) {
        try {
        	IConfigurationManager config = ConfigurationManagerImpl.getConfigurationManager();
            String to = config.getConfig(MCDSConfig.SUPPORT_EMAIL);
            String subject = "[Aggregator Id =" + id + "] is down";
            String text = "\n\n\n" + "[Aggregator Id =" + id + "] is down";

            EMailSender.sendMail(to, subject, text);
        } catch (MCDSException ex) {
            _log.severe("Alert sending failed " + ex.getMessage());
        }
    }

    public static void sendReconProcessReport(String subject, String content) {
        try {
        	IConfigurationManager config = ConfigurationManagerImpl.getConfigurationManager();
            String to = config.getConfig(MCDSConfig.SUPPORT_EMAIL);
            //String subject = subject;;
            String text= content;
            
            EMailSender.sendMail(to, subject, text);
            
        } catch (MCDSException ex) {
            _log.severe("Recon report sending failed " + ex.getMessage());
        }
    }
    /**  
    public static void sendDailyTransactionReports(File file) {
        try {
        	IConfigurationManager config = ConfigurationManagerImpl.getConfigurationManager();
            String to = config.getConfig(MCDSConfig.SUPPORT_EMAIL);
            String subject = "Bustik Reconcilation Report ";
            String text= "Attachment has the daily status report.";
            
            EMailSender.sendMail(to, subject, text,file);
            
        } catch (MCDSException ex) {
            _log.severe("Daily transaciton report sending failed " + ex.getMessage());
        }

    }
*/
    

}
