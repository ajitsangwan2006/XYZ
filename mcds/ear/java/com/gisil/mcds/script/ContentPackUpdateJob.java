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
package com.gisil.mcds.script;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import com.gisil.mcds.aggregator.mauj.Mauj;
import com.gisil.mcds.aggregator.mauj.entity.Catalog;
import com.gisil.mcds.aggregator.mauj.entity.ContentPack;
import com.gisil.mcds.aggregator.mauj.entity.Item;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.base.MCDSLauncher;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.util.EMailAlert;

/**
 * @author amit sachan
 */
public class ContentPackUpdateJob {

    private MCDSLauncher m_launcher = null;

    private static Logger _log;

    private StringBuffer _message = new StringBuffer();
    
    IConfigurationManager mgr = null;

    

    private void init() {

        _log = Logger.getLogger("MCDS Logger");
        _log.info("Initializing mcds .......");

        try {
            m_launcher = MCDSLauncher.getLauncher();
            m_launcher.launch("mcds.config");

            mgr = ConfigurationManagerImpl.getConfigurationManager();     

            _log.info("MCDS Initialization Complete ....");
        } catch (Throwable t) {
            t.printStackTrace();
            _log.severe("Exception occured while initialzing MCDS .....");
        }

    }

    /**
     * execute the job
     */
    private void executeJob() {
        _log.info("Initializing Content Update Job .......");
        String currentCatId = null;
        Connection con = null;
        try {
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT catalogid FROM pack WHERE status = 'ENABLED' AND ROWNUM = 1 ");

            if (rs.next()) {
                currentCatId = rs.getString("catalogid");
            } else {
                currentCatId = "1";
            }

            Mauj maujService = new Mauj();
            Catalog content = maujService.getPackageList(currentCatId);
            if (content != null) {
                if (content.getId().equals(currentCatId) && !mgr.getConfigAsBoolean(MCDSConfig.MAUJ_CONTENT_OVERRIDE)) {
                    _log.info("No Update required.......");
                    _message.append('\n').append("No Update is required for content packs.").append('\n');
                    _message.append('\n').append("Cause: No New content on Mauj and Overriding is disabled in config.");
                } else {
                    st.executeUpdate("UPDATE pack SET status = 'DISABLED'");
                    st.executeUpdate("UPDATE contents SET status = 'DISABLED' WHERE parentid = 2");
                    _message.append('\n').append("Setted Old Packs DISABLED").append('\n');
                    feedDatabase(content, mgr.getConfigAsNumber(MCDSConfig.CONTENT_PACK_PARENT_ID).intValue());
                }

            } else {
                _log.info("No Response from Mauj.......");
                _message.append('\n').append("No Response from Mauj");
            }

            DBUtil.close(st);

        } catch (Throwable t) {
            t.printStackTrace();
            _log.severe("Exception occured while processing .....");
        } finally {
            DBUtil.close(con);
        }
    }

    /**
     * load the db
     * 
     * @param content
     * @param parentId
     */
    private void feedDatabase(Catalog content, int parentId) {

        Connection con = null;
        try {

            ResultSet rs;
            String catalogId = content.getId();

            ArrayList<ContentPack> packageList = content.getContentPackList();
            Iterator iterator = packageList.iterator();
            long packId = 0;
            long contentId = 0;
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            while (iterator.hasNext()) {
                // DB connections

                Statement st = con.createStatement();
                PreparedStatement insertContents = con
                        .prepareStatement("INSERT INTO contents(id,contentname,parentid, aggregatorid) VALUES(?,?,?,?)");

                PreparedStatement insertPack = con
                        .prepareStatement("INSERT INTO pack(id,sku,title,type,cost,description,contentid,catalogid) VALUES(?,?,?,?,?,?,?,?)");

                PreparedStatement insertItem = con
                        .prepareStatement("INSERT INTO pack_item(itemid,title,type,packid) VALUES(?,?,?,?)");

                ContentPack contentpk = (ContentPack) iterator.next();

                // create pack and content table entry
                rs = st.executeQuery("SELECT pack_seq.NEXTVAL FROM DUAL");
                if (rs.next()) {
                    packId = rs.getInt("NEXTVAL");
                }
                rs = st.executeQuery("SELECT contents_seq.NEXTVAL FROM DUAL");
                if (rs.next()) {
                    contentId = rs.getInt("NEXTVAL");
                }
                // Insert in Contents
                insertContents.setLong(1, contentId);
                insertContents.setString(2, contentpk.getTitle());
                insertContents.setLong(3, parentId);
                // Default Mauj Aggregator ID
                insertContents.setInt(4, 1);
                insertContents.executeUpdate();

                // Insert in pack
                insertPack.setLong(1, packId);
                insertPack.setString(2, contentpk.getSku());
                insertPack.setString(3, contentpk.getTitle());
                insertPack.setString(4, contentpk.getType());
                insertPack.setDouble(5, contentpk.getCost());
                insertPack.setString(6, contentpk.getDescription());
                insertPack.setLong(7, contentId);
                insertPack.setString(8, catalogId);
                insertPack.executeUpdate();
                _log.info("[" + contentpk.getTitle() + "]");
                _message.append('\n').append("Added Content Pack - [" + contentpk.getTitle() + "] with :");

                ArrayList<Item> itemList = contentpk.getItemList();
                Iterator itemIterator = itemList.iterator();

                while (itemIterator.hasNext()) {
                    Item item = (Item) itemIterator.next();

                    insertItem.setLong(1, item.getItemId());
                    insertItem.setString(2, item.getTitle());
                    insertItem.setString(3, item.getType());
                    insertItem.setLong(4, packId);
                    insertItem.executeUpdate();
                    _log.info("=====Item[" + item.getTitle() + "]");
                    _message.append('\n').append("=====Item[" + item.getTitle() + "]");
                }

                DBUtil.close(insertContents);
                DBUtil.close(insertPack);
                DBUtil.close(insertPack);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            DBUtil.close(con);
        }

    }

    private void destroy() throws MCDSException {
        EMailAlert.sendMailForDownloadContent("Content Pack Download Job", _message.toString());
        m_launcher.shutdown();
    }

    public void refreshContent(IConfigurationManager mgr) {
        ContentPackUpdateJob job = new ContentPackUpdateJob();
        this.mgr = mgr;
        instantiateLog();
        job.executeJob();
    }


    public static void main(String[] args) {

        ContentPackUpdateJob job = new ContentPackUpdateJob();
        // Launch the application to get db connection
        job.init();

        // use connection and send request to get details and insert into table
        job.executeJob();

        try {
            job.destroy();
        } catch (Exception e) {
            _log.info("Failed to ShutDown all resources");
            e.printStackTrace();
        }

    }

    /**
     * instantiate log
     */
    private void instantiateLog() {
        _log = Logger.getLogger("MCDS Logger");
        _log.info("Initializing mcds .......");
    }

}
