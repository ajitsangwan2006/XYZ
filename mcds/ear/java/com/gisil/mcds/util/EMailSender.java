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

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.base.MCDSException;

public class EMailSender {

    private static Logger _log = LogUtil.getLogger(EMailSender.class);

    public static void sendMail(String to, String subject, String content) throws MCDSException {

       final Properties props = getMailProperties();

       
        try {
            // Get session
            Session session = Session.getInstance(props,new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(props.getProperty(MCDSConfig.MAIL_SENDER), props
                            .getProperty(MCDSConfig.MAIL_SENDER_PASSWORD));
                }

            });

            // Define message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty(MCDSConfig.MAIL_SENDER)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(content, MCDSConfig.MAIL_CONTENT_TYPE);

            // Send message
            Transport.send(message);
            _log.info("Mail send successfully...");

        } catch (AddressException e) {
            _log.log(Level.SEVERE, "Configuraiotn error", e);
        } catch (MessagingException e) {
            _log.log(Level.SEVERE, "Message sending failed", e);
        } catch (Throwable e) {
            _log.log(Level.SEVERE, "Message sending failed", e);
        }
    }

    public static void sendMail(String to, String subject, String content, File file) throws MCDSException {

        final Properties props = getMailProperties();

        try {
            // Get session
            Session session = Session.getInstance(props, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(props.getProperty(MCDSConfig.MAIL_SENDER), props
                            .getProperty(MCDSConfig.MAIL_SENDER_PASSWORD));
                }

            });

            // Define message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty(MCDSConfig.MAIL_SENDER)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(content);
           
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(file.getName());
            
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            
            // Send message
            Transport.send(message);
            _log.info("Mail send successfully...");

        } catch (AddressException e) {
            _log.log(Level.SEVERE, "Configuraiotn error", e);
        } catch (MessagingException e) {
            _log.log(Level.SEVERE, "Message sending failed", e);
        } catch (Throwable e) {
            _log.log(Level.SEVERE, "Message sending failed", e);
        }
    }

    private static Properties getMailProperties() throws MCDSException {

        IConfigurationManager config = ConfigurationManagerImpl.getConfigurationManager();

        String host = config.getConfig(MCDSConfig.MAIL_HOST);
        String port = config.getConfig(MCDSConfig.MAIL_PORT);
        String emailSender = config.getConfig(MCDSConfig.MAIL_SENDER);
        String emailRecipient = "";
        String emailContent = "";
        String password = config.getConfig(MCDSConfig.MAIL_SENDER_PASSWORD);
        Properties properties = new Properties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put(MCDSConfig.MAIL_SENDER, emailSender);
        properties.put(MCDSConfig.MAIL_SENDER_PASSWORD, password);

        properties.put(MCDSConfig.MAIL_RECIPIENT, emailRecipient);
        properties.put(MCDSConfig.MAIL_CONTENT_TYPE, emailContent);

        return properties;

    }
}
