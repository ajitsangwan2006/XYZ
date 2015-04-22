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
package com.gisil.mcds.content;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.gisil.mcds.aggregator.mauj.entity.ContentDetailTO;
import com.gisil.mcds.aggregator.mauj.entity.ContentsTO;
import com.gisil.mcds.aggregator.mauj.entity.TopContentTO;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.commission.ClassSurcharge;
import com.gisil.mcds.commission.Partner;
import com.gisil.mcds.config.Configuration;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.dmi.ContentDetailsForPurchaseInfo;
import com.gisil.mcds.dmi.ContentDetailsforUI;
import com.gisil.mcds.dmi.ContentInfo;
import com.gisil.mcds.dmi.PackDetailForUI;
import com.gisil.mcds.dmi.PackItemForUI;
import com.gisil.mcds.dmi.PackTypeListInfo;
import com.gisil.mcds.dmi.PhoneMappingInfo;
import com.gisil.mcds.services.ContentDisplaySettings;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.util.LogUtil;

/**
 * DB Manager Class for Content Related activities.
 * 
 * @author amit sachan
 */
public class ContentDBMgr {

    private IDBAccessManager _dbAccessMgr;

    private Logger _log;

    private IConfigurationManager mgr;

    private boolean _isEmptyContentName = true;

    private boolean _isEmptyContent = true;

    /**
     * ContentDBMgr
     */
    public ContentDBMgr() {
        _dbAccessMgr = DBAccessManagerFactory.getDBAccessManager();
        mgr = ConfigurationManagerImpl.getConfigurationManager();
        _log = LogUtil.getLogger(getClass());
    }

    /**
     * This method get the phone code
     * 
     * @param map
     * @throws MCDSException
     */
    public void getPhoneCode(PhoneMappingInfo map) throws MCDSException {

        if (map.getPhoneMake() == null || map.getPhoneModel() == null) {
            throw new MCDSException("Invalid Phone Map details");
        }
        String sql = "select AGGREGATORCODE,AGGREGATORMAKE from PHONE_MAPPING WHERE ";

        getPhoneCode(sql, mgr.getConfigAsBoolean(MCDSConfig.MAUJ_COMPARE_PHONE_BY_GENERIC), map);
    }

    /**
     * called by getPhoneCode(PhoneMappingInfo map)
     * 
     * @param sql
     * @param isGeneric
     * @param map
     * @throws MCDSException
     */
    private void getPhoneCode(String sql, Boolean isGeneric, PhoneMappingInfo map) throws MCDSException {

        Connection conn = null;
        String postSql = null;
        if (isGeneric) {
            postSql = "LOWER(MAKE)=LOWER('" + map.getPhoneMake() + "') AND LOWER(MODEL)=LOWER('" + map.getPhoneModel()
                    + "')";
        } else {
            postSql = "LOWER(AGGREGATORMAKE)=LOWER('" + map.getPhoneMake() + "') AND LOWER(AGGREGATORMODEL)=LOWER('"
                    + map.getPhoneModel() + "')";
        }
        _log.info("Checking Phone Code by Make-Model==" + sql + postSql);
        try {
            conn = _dbAccessMgr.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql + postSql);

            if (rs.next()) {
                map.setAggPhoneCode(rs.getString("AGGREGATORCODE"));
                map.setAggPhoneMake(rs.getString("AGGREGATORMAKE"));
            } else {
                throw new MCDSException("Invalid Phone Make/Model");
            }
            DBUtil.close(st);
        } catch (SQLException e) {
            _log.info("sql exception during loading of Phone Map with model -" + map.getPhoneModel());
            throw new MCDSException("sql exception during loading of Phone Map with model -" + map.getPhoneModel(), e);
        } finally {
            DBUtil.close(conn);
        }

    }

    /**
     * This method return the content detail purchage informations
     * 
     * @param id
     * @param type
     * @return
     * @throws MCDSException
     */
    public ContentDetailsForPurchaseInfo getContentDetailsByID(Integer id, String type) throws MCDSException {

        ContentDetailsForPurchaseInfo info = null;
        Connection conn = null;
        String sql = null;
        if (ContentDetailsForPurchaseInfo.PACK.equals(type)) {
            sql = "select SKU,SKU || '-' || SUBSTR(TITLE, 1, 19-LENGTH(SKU || '-')) AS TITLE,COST from PACK where CONTENTID ="
                    + id.intValue() + " AND STATUS = 'ENABLED'";
        }

        try {
            conn = _dbAccessMgr.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                info = new ContentDetailsForPurchaseInfo();
                info.setCost(rs.getDouble("COST"));
                info.setSku(rs.getString("SKU"));
                info.setSkuName(rs.getString("TITLE"));
                System.out.println("into getContentDetailsByID(Integer id,String type) and values are " + info.getSku()
                        + info.getSkuName() + info.getCost());
            } else {
                throw new MCDSException("No Record found for ID-" + id);
            }
            DBUtil.close(st);

        } catch (SQLException e) {
            _log.info("sql exception during loading cost of content ID -" + id.intValue());
            throw new MCDSException("sql exception during loading cost of content ID -" + id.intValue(), e);
        } finally {
            DBUtil.close(conn);
        }

        return info;
    }

    /**
     * This returns the main menu contents
     * 
     * @return
     * @throws MCDSException
     */
    public HashMap<String, String> getMenuContent(int aggID) throws MCDSException {
        Connection conn = null;
        HashMap<String, String> mainMenuContent = new HashMap<String, String>();
        Number top10Id = mgr.getConfigAsNumber(MCDSConfig.TERMINAL_TOP10_CONTENT_ID);
        Number packId = mgr.getConfigAsNumber(MCDSConfig.TERMINAL_CONTENTPACK_CONTENT_ID);
        int top10ContentId = top10Id.intValue();
        int contentPackId = packId.intValue();
        String sql = "select ID, CONTENTNAME FROM CONTENTS WHERE ID  IN( ?, ? ) AND  STATUS = 'ENABLED' AND AGGREGATORID = ? ";
        conn = _dbAccessMgr.getConnection();

        try {
            PreparedStatement prepareStmt = conn.prepareStatement(sql);
            prepareStmt.setInt(1, top10ContentId);
            prepareStmt.setInt(2, contentPackId);
            prepareStmt.setInt(3, aggID);
            ResultSet rs = prepareStmt.executeQuery();

            while (rs.next()) {
                String contentName = rs.getString("CONTENTNAME");
                String contentId = rs.getString("ID");
                mainMenuContent.put(contentId, contentName);
            }
            DBUtil.close(prepareStmt);

        } catch (SQLException sqlExp) {
            _log.info("sql exception during getting MCDS menu Content");

            throw new MCDSException("sql exception during MCDS menu Content ");

        } finally {
            DBUtil.close(conn);
        }
        return mainMenuContent;

    }

    /**
     * This function find the contents id and content names, which we are going
     * to catch in contentDetail.jsp
     * 
     * @param aPackId
     * @return
     */
    public ArrayList<ContentsTO> getPackContents(String aPackId) {

        Connection con = null;

        ResultSet rs = null;

        ArrayList<ContentsTO> _contents = new ArrayList<ContentsTO>();
        String sql = "select PACK_ITEM.ITEMID, PACK_ITEM.TITLE from PACK_ITEM "
                + "inner join PACK  on PACK.ID = PACK_ITEM.PACKID where "
                + "PACK.CONTENTID = ? and  PACK.STATUS = 'ENABLED' order by PACK_ITEM.TITLE ASC";

        try {

            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setLong(1, Long.parseLong(aPackId));
            _log.info("value-" + Long.parseLong(aPackId) + "data");
            _log.info(sql);
            rs = prepareStmt.executeQuery();

            while (rs.next()) {

                ContentsTO _contentObjects = new ContentsTO();

                _contentObjects.setContentId(rs.getString("ITEMID"));
                _contentObjects.setContentCode(rs.getString("ITEMID"));

                _contentObjects.setContentName(ContentDisplaySettings.getSongTitle(rs.getString("TITLE")));

                _contents.add(_contentObjects);

                if (_contentObjects.getContentName() != null)
                    _isEmptyContentName = false;

                if (_contentObjects.getContentCode() != null)
                    _isEmptyContent = false;

            }
            DBUtil.close(prepareStmt);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }
        return _contents;
    }

    /**
     * This function find and return the type of subPack, which we are using for
     * back page path subPack genration
     * 
     * @param aPackId
     * @return
     */
    public String getBackType(String aPackId) {

        Connection con = null;

        ResultSet rs = null;

        String backType = null;

        String sql = "select SUBSTR(TYPE, 1, 19) AS TYPE from PACK where CONTENTID = ? order by TYPE ASC";
        try {

            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setLong(1, Long.parseLong(aPackId));

            rs = prepareStmt.executeQuery();

            if (rs.next()) {
                backType = rs.getString("TYPE");

            }
            DBUtil.close(prepareStmt);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }
        return backType;
    }

    public boolean isEmptyContentName() {
        return _isEmptyContentName;
    }

    public boolean isEmptyContent() {
        return _isEmptyContent;
    }

    /**
     * Return the pack contents
     * 
     * @return
     */
    public ArrayList<PackTypeListInfo> getContents(String contentInfo) throws MCDSException {

        ArrayList<PackTypeListInfo> list = new ArrayList<PackTypeListInfo>();
        Connection con = null;
        ResultSet rs = null;
        String sql = null;
        if (contentInfo == null)
            sql = "select distinct TYPE from PACK where STATUS = 'ENABLED' order by TYPE ASC";
        else
            sql = "select distinct TYPE from PACK where lower(TYPE) like '%'||?||'%' and STATUS = 'ENABLED' order by TYPE ASC";

        try {

            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();

            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            if (contentInfo != null)
                prepareStmt.setString(1, contentInfo.toLowerCase());
            rs = prepareStmt.executeQuery();
            while (rs.next()) {
                PackTypeListInfo info = new PackTypeListInfo();
                info.setDisplayName(ContentDisplaySettings.getPackTitle(rs.getString("TYPE")) + " Pack");
                info.setValue(rs.getString("TYPE"));
                list.add(info);
            }
            DBUtil.close(prepareStmt);

        } catch (SQLException se) {
            throw new MCDSException("Unable to fetch Pack Types");
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }
        return list;
    }

    /**
     * This function find and return the content packs id and content packs
     * names, which we are going to catch in contentPack.jsp
     * 
     * @param aType
     * @return
     */
    public ArrayList<ContentDetailTO> getSubPackContents(String aType) {

        ArrayList<ContentDetailTO> contents = new ArrayList<ContentDetailTO>();
        Connection con = null;

        ResultSet rs = null;

        String sql = "select SKU || '-' || SUBSTR(TITLE, 1, 19-LENGTH(SKU || '-')) AS TITLE, CONTENTID from PACK where TYPE = ? and STATUS = 'ENABLED' order by TITLE ASC";
        ContentDetailTO contentDetailTO = null;
        try {
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setString(1, aType);
            rs = prepareStmt.executeQuery();
            while (rs.next()) {
                contentDetailTO = new ContentDetailTO();
                contentDetailTO.setCode(rs.getString("CONTENTID"));
                contentDetailTO.setTitle(rs.getString("TITLE"));
                contents.add(contentDetailTO);
            }
            DBUtil.close(prepareStmt);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }

        return contents;
    }

    /**
     * This method find and return the content packs id and content packs names
     * on the behalf of on search title or id
     * 
     * @param aContentInfo
     * @param aSearchBy
     * @return
     */
    public ArrayList<ContentDetailTO> getSubPackContents(String aContentInfo, String aSearchBy) {
        ArrayList<ContentDetailTO> contents = new ArrayList<ContentDetailTO>();
        Connection con = null;

        ResultSet rs = null;

        String sql = null;
        aContentInfo = aContentInfo.toLowerCase();
        if (aSearchBy.equals("title"))
            sql = "select SKU || '-' || SUBSTR(TITLE, 1, 19-LENGTH(SKU || '-')) AS TITLE, CONTENTID from PACK where lower(TITLE) like '%'||?||'%' and STATUS = 'ENABLED' order by TITLE ASC";
        else if (aSearchBy.equals("code"))
            sql = "select SKU || '-' || SUBSTR(TITLE, 1, 19-LENGTH(SKU || '-')) AS TITLE, CONTENTID from PACK where lower(SKU) like '%'||?||'%' and STATUS = 'ENABLED' order by TITLE ASC";

        try {
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setString(1, aContentInfo);
            rs = prepareStmt.executeQuery();

            while (rs.next()) {
                ContentDetailTO contentDetailTO = new ContentDetailTO();

                contentDetailTO.setCode(rs.getString("CONTENTID"));
                contentDetailTO.setTitle(ContentDisplaySettings.getContentPackTitle(rs.getString("TITLE")));
                contents.add(contentDetailTO);

            }
            DBUtil.close(prepareStmt);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }

        return contents;
    }

    /**
     * Find and set the searching details
     * 
     * @param aContent
     * @param aSearchBy
     */
    public ArrayList<ContentsTO> getSearchContentDetails(String aContent, String aSearchBy) {

        Connection con = null;

        ResultSet rs = null;

        ArrayList<ContentsTO> _contents = new ArrayList<ContentsTO>();

        String sql = null;
        if (aSearchBy.equals("title"))
            sql = "select ID, CONTENTID, SKU || '-' || SUBSTR(TITLE, 1, 19-LENGTH(SKU || '-')) AS TITLE from PACK where TITLE like '%'||?||'%' and STATUS = 'ENABLED' order by TITLE ASC";
        else if (aSearchBy.equals("code"))
            sql = "select ID, CONTENTID, SKU || '-' || SUBSTR(TITLE, 1, 19-LENGTH(SKU || '-')) AS TITLE from PACK where SKU like '%'||?||'%' and STATUS = 'ENABLED' order by TITLE ASC";
        try {
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setString(1, aContent);
            rs = prepareStmt.executeQuery();

            while (rs.next()) {
                ContentsTO _contentObjects = new ContentsTO();
                _contentObjects.setContentId(rs.getString("ID"));
                _contentObjects.setContentCode(rs.getString("CONTENTID"));
                _contentObjects.setContentName(ContentDisplaySettings.getSongTitle(rs.getString("TITLE")));
                _contents.add(_contentObjects);
                if (_contentObjects.getContentName() != null)
                    _isEmptyContentName = false;
                if (_contentObjects.getContentCode() != null)
                    _isEmptyContent = false;

            }
            DBUtil.close(prepareStmt);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }
        return _contents;
    }

    /**
     * Return the back id, using we will generate back url
     * 
     * @param aContentId
     * @return
     */
    public String getBackId(String aContentId) {

        String backId = null;
        Connection con = null;
        ResultSet rs = null;
        String sql = "select PACK.CONTENTID from PACK inner join PACK_ITEM on PACK_ITEM.PACKID = PACK.ID where PACK_ITEM.ITEMID = ?";
        try {
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setLong(1, Long.parseLong(aContentId));
            rs = prepareStmt.executeQuery();
            if (rs.next()) {
                backId = rs.getString("CONTENTID");
            }
            DBUtil.close(prepareStmt);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {
            DBUtil.close(con);
        }
        return backId;
    }

    /**
     * set the details
     * 
     * @param aItemId
     */
    public ContentDetailTO getDetail(String aItemId) {
        Connection con = null;

        ResultSet rs = null;

        String sql = "select PACK.SKU || '-' || SUBSTR(PACK.TITLE, 1, 19-LENGTH(PACK.SKU || '-')) AS CATEGORYNAME, PACK_ITEM.TITLE, PACK.COST from PACK inner join PACK_ITEM on PACK.ID = PACK_ITEM.PACKID where PACK_ITEM.ITEMID = ? order by PACK_ITEM.TITLE ASC";
        ContentDetailTO contentDetailTO = new ContentDetailTO();

        try {
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setLong(1, Long.parseLong(aItemId));
            rs = prepareStmt.executeQuery();
            if (rs.next()) {
                contentDetailTO.setCategoryName(ContentDisplaySettings.getCompleteLine(rs.getString("categoryname")));
                contentDetailTO.setTitle(rs.getString("title"));
                contentDetailTO.setCode(aItemId);
                contentDetailTO.setPrice("" + rs.getFloat("cost"));
            }
            DBUtil.close(prepareStmt);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }
        return contentDetailTO;
    }

    /**
     * set the detail acording to seach element
     * 
     * @param aContentId
     */
    public ContentDetailTO getSearchDetail(String aContentId) {
        Connection con = null;

        ResultSet rs = null;

        String sql = "select TYPE, SKU || '-' || SUBSTR(TITLE, 1, 19-LENGTH(SKU || '-')) AS TITLE, SKU, COST from PACK where ID = ? order by TITLE ASC";
        ContentDetailTO contentDetailTO = new ContentDetailTO();

        try {
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setLong(1, Long.parseLong(aContentId));
            rs = prepareStmt.executeQuery();
            if (rs.next()) {
                contentDetailTO.setType(ContentDisplaySettings.getCompleteLine(rs.getString("type")));
                contentDetailTO.setTitle(rs.getString("title"));
                contentDetailTO.setCode(rs.getString("sku"));
                contentDetailTO.setPrice("" + rs.getFloat("cost"));
            }
            DBUtil.close(prepareStmt);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }
        return contentDetailTO;
    }

    /**
     * This function find and return the content packs id and content packs
     * names, which we are going to catch in contentPack.jsp
     * 
     * @param aType
     * @return
     */
    public ArrayList<ContentDetailTO> getSubPack(String aType) {

        ArrayList<ContentDetailTO> contents = new ArrayList<ContentDetailTO>();
        Connection con = null;

        ResultSet rs = null;

        String sql = "select SKU || '-' || SUBSTR(TITLE, 1, 19-LENGTH(SKU || '-')) AS TITLE, CONTENTID from PACK where lower(TYPE) like '%'||?||'%' and STATUS = 'ENABLED' order by TITLE ASC";
        ContentDetailTO contentDetailTO = null;
        try {
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setString(1, aType.toLowerCase());
            rs = prepareStmt.executeQuery();
            while (rs.next()) {
                contentDetailTO = new ContentDetailTO();
                contentDetailTO.setCode(rs.getString("CONTENTID"));
                contentDetailTO.setTitle(rs.getString("TITLE"));
                contents.add(contentDetailTO);

            }
            DBUtil.close(prepareStmt);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }

        return contents;
    }

    /**
     * This function returns a String representing the name or Title of the
     * Content
     * 
     * @param aContentId
     * @return
     */
    public String getContentName(String aContentId) {

        Connection con = null;

        ResultSet rs = null;

        String contentName = null;

        String sql = "select SUBSTR(TITLE, 1, 19) AS TITLE from PACK where CONTENTID = ? order by TITLE ASC";
        try {

            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setLong(1, Long.parseLong(aContentId));

            rs = prepareStmt.executeQuery();

            if (rs.next()) {
                contentName = rs.getString("TITLE");

            }
            DBUtil.close(prepareStmt);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }
        return contentName;
    }

    /**
     * Returns the top 10 contents
     * 
     * @param aType
     * @return
     */
    public ArrayList topContents(String aContentId) {

        ArrayList<TopContentTO> contents = new ArrayList<TopContentTO>();
        Connection con = null;

        ResultSet rs = null;

        String sql = "select ID, SUBSTR(CONTENTNAME, 1, 19) as CONTENTNAME, 1 as ISCHILD from CONTENTS where PARENTID = ? order by CONTENTNAME ASC";
        try {
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setLong(1, Long.parseLong(aContentId));
            rs = prepareStmt.executeQuery();
            while (rs.next()) {
                TopContentTO topContentTO = new TopContentTO();
                topContentTO.setContentId("" + rs.getLong("ID"));
                topContentTO.setContentName(rs.getString("CONTENTNAME"));
                topContentTO.setIsParent(rs.getString("ISCHILD"));
                contents.add(topContentTO);
            }
            DBUtil.close(prepareStmt);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }

        return contents;
    }

    /**
     * Return parentId for back url generation in TOP 10 contents
     * 
     * @param aContentId
     * @return
     */
    public String getParentId(String aContentId) {
        String parentId = null;
        Connection con = null;

        ResultSet rs = null;

        String sql = "select PARENTID from CONTENTS where ID = ?";
        try {
            IDBAccessManager dbManager = DBAccessManagerFactory.getDBAccessManager();
            con = dbManager.getConnection();
            PreparedStatement prepareStmt = con.prepareStatement(sql);
            prepareStmt.setLong(1, Long.parseLong(aContentId));
            rs = prepareStmt.executeQuery();
            if (rs.next())
                parentId = rs.getString("PARENTID");
            DBUtil.close(prepareStmt);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (MCDSException mcdsExp) {
            _log.info("Server Error : " + mcdsExp.toString());
        } finally {

            DBUtil.close(con);

        }

        return parentId;
    }

    /**
     * this method update the Configuration table with new Data and retrieve all
     * Records.
     * 
     * @param cfgDataList
     * @return
     * @throws MCDSException
     */
    public ArrayList<Configuration> updateConfigurationData(ArrayList<Configuration> cfgDataList) throws MCDSException {
        boolean flag = updateConfiguration(cfgDataList);
        ArrayList<Configuration> updatedConfigurationList = null;

        if (flag) {
            IConfigurationManager configMgr = ConfigurationManagerImpl.getConfigurationManager();
            configMgr.reload();
            updatedConfigurationList = loadConfiguration();
        }

        return updatedConfigurationList;
    }

    /**
     * this method update the Configuration table with new Data only those
     * records having status Changed
     * 
     * @param cfgDataList
     * @return
     * @throws MCDSException
     */
    private boolean updateConfiguration(ArrayList<Configuration> cfgDataList) throws MCDSException {
        boolean flag = false;
        String sql = "UPDATE CONFIGURATION SET PARAMVALUE = ? WHERE ID = ?";
        Connection conn = _dbAccessMgr.getConnection();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (Configuration cfgData : cfgDataList) {
                if (cfgData.isChanged()) {
                    pstmt.setString(1, cfgData.getParamNewValue());
                    pstmt.setInt(2, cfgData.getRowId());
                    pstmt.addBatch();
                }
            }

            int result[] = pstmt.executeBatch();
            _log.info("Configuration :  Updated Successfully : " + result.length);
            flag = true;
            DBUtil.close(pstmt);
        } catch (SQLException e) {
            _log.info("Exception Occured while Updating Configuration Data");
            throw new MCDSException(e.toString());
        } finally {
            DBUtil.close(conn);
        }

        return flag;
    }

    /**
     * this method retrieve all record from Configuration table
     * 
     * @return
     * @throws MCDSException
     */
    public ArrayList<Configuration> loadConfiguration() throws MCDSException {
        ArrayList<Configuration> cfgList = new ArrayList<Configuration>();

        String sql = "SELECT ID, PARAMNAME, PARAMVALUE, PARAMTYPE, PARAMCATEGORY, DESCRIPTION FROM CONFIGURATION ORDER BY PARAMCATEGORY";
        Connection conn = _dbAccessMgr.getConnection();

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                Configuration cfg = new Configuration();
                cfg.setRowId(rs.getInt("ID"));
                cfg.setParamName(rs.getString("PARAMNAME"));
                cfg.setParamValue(rs.getString("PARAMVALUE"));
                cfg.setParamCategory(rs.getString("PARAMCATEGORY"));
                cfg.setParamtype(rs.getString("PARAMTYPE"));
                cfg.setDescription(rs.getString("DESCRIPTION"));
                cfgList.add(cfg);
            }

            DBUtil.close(preparedStmt);
        } catch (SQLException sqlexp) {
            throw new MCDSException(sqlexp.toString());
        } finally {
            DBUtil.close(conn);
        }

        return cfgList;
    }

    /**
     * Get Content Details for UI
     * 
     * @param Id
     * @return
     * @throws MCDSException
     */
    public ArrayList<ContentDetailsforUI> getContentDetails(Integer Id, Integer aggrId) throws MCDSException {

        ArrayList<ContentDetailsforUI> list = new ArrayList<ContentDetailsforUI>();
        String sql = "SELECT BASE.ID, BASE.PARENTID, BASE.CONTENTNAME, BASE.STATUS," + " DECODE(BASE.PARENTID,"
                + " (SELECT PARAMVALUE FROM CONFIGURATION WHERE PARAMNAME = 'terminal.contentpack.contentid'),"
                + "'PACK','OTHER') AS TYPE,(SELECT COUNT(*) FROM CONTENTS WHERE PARENTID =BASE.ID )"
                + " AS CHILDCOUNT FROM CONTENTS BASE WHERE BASE.PARENTID ";
        if (Id == null) {
            sql = sql + "IS NULL AND AGGREGATORID = " + aggrId;
        } else {
            sql = sql + "= " + Id;
        }
        _log.info(sql);
        Connection conn = _dbAccessMgr.getConnection();

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                ContentDetailsforUI detail = new ContentDetailsforUI();
                detail.setContentId(rs.getInt("ID"));
                detail
                        .setParentId(rs.getString("PARENTID") != null ? Integer.parseInt(rs.getString("PARENTID"))
                                : null);
                detail.setContentName(rs.getString("CONTENTNAME"));
                detail.setType(rs.getString("TYPE"));
                detail.setStatus(rs.getString("STATUS"));
                // logic to check is parent
                detail.setParent(rs.getInt("CHILDCOUNT") > 0);
                list.add(detail);
            }
            // parent of parent to generate Back URL
            if (!list.isEmpty() && list.get(0).getParentId() != null) {
                String tSql = "SELECT PARENTID FROM CONTENTS WHERE ID =" + list.get(0).getParentId();
                preparedStmt = conn.prepareStatement(tSql);
                rs = preparedStmt.executeQuery();
                if (rs.next()) {
                    list.get(0).setParentOfParentId(
                            rs.getString("PARENTID") != null ? Integer.parseInt(rs.getString("PARENTID")) : null);
                } else {
                    throw new MCDSException("Wrong DataSetup for Parent Id");
                }
            }

            DBUtil.close(preparedStmt);
        } catch (SQLException sqlexp) {
            throw new MCDSException(sqlexp.toString());
        } finally {
            DBUtil.close(conn);
        }

        return list;
    }

    /**
     * Get Pack Details
     * 
     * @param id
     * @return
     * @throws MCDSException
     */
    public PackDetailForUI getPackDetail(Integer id) throws MCDSException {
        if (id == null) {
            throw new MCDSException("Pack ID should not be null");

        }
        PackDetailForUI pack = null;
        String sql = "SELECT PACK.ID, PACK.CATALOGID, PACK.STATUS, PACK.COST, PACK.TITLE,"
                + " PACK.DESCRIPTION, PACK.SKU, PACK.TYPE ,PACK.TSCREATED, PACK.TSUPDATED ,"
                + " PACK_ITEM.STATUS AS ITEMSTATUS, PACK_ITEM.TYPE AS ITEMTYPE, PACK_ITEM.TITLE AS ITEMTITLE,"
                + " PACK_ITEM.ITEMID,PACK_ITEM.TSCREATED AS ICREATED, PACK_ITEM.TSUPDATED AS IUPDATED"
                + " FROM PACK, PACK_ITEM WHERE PACK.ID = PACK_ITEM.PACKID AND PACK.CONTENTID = ?";

        _log.info(sql);
        Connection conn = _dbAccessMgr.getConnection();

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setLong(1, id);

            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                if (pack == null) {
                    pack = new PackDetailForUI();
                    pack.setId(rs.getString("ID"));
                    pack.setCatalogId(rs.getString("CATALOGID"));
                    pack.setStatus(rs.getString("STATUS"));
                    pack.setCost(rs.getString("COST"));
                    pack.setTitle(rs.getString("TITLE"));
                    pack.setDescription(rs.getString("DESCRIPTION"));
                    pack.setSku(rs.getString("SKU"));
                    pack.setType(rs.getString("TYPE"));
                    pack.setTsCreated(rs.getString("TSCREATED"));
                    pack.setTsUpdated(rs.getString("TSUPDATED"));
                }
                PackItemForUI item = new PackItemForUI();
                item.setId(rs.getString("ITEMID"));
                item.setStatus(rs.getString("ITEMSTATUS"));
                item.setType(rs.getString("ITEMTYPE"));
                item.setTitle(rs.getString("ITEMTITLE"));
                item.setTsCreated(rs.getString("ICREATED"));
                item.setTsUpdated(rs.getString("IUPDATED"));
                // add to Pack
                pack.getItems().add(item);

            }
            DBUtil.close(preparedStmt);
        } catch (SQLException sqlexp) {
            throw new MCDSException(sqlexp.toString());
        } finally {
            DBUtil.close(conn);
        }
        return pack;
    }

    public void setContentStatus(String aId, String aType, String aValue) throws MCDSException {
        // code will come here
    }

    /**
     * return info about content
     * 
     * @param aContentId
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public ContentInfo getContentInfo(Long aContentId) throws MCDSException {
        if (aContentId == null) {
            throw new MCDSException("aContentId should not be null");

        }
        ContentInfo contentInfo = new ContentInfo();
        String sql = "SELECT STATUS, CONTENTNAME, DESCRIPTION, DELIVERYMODE, AGGREGATORID "
                + "FROM CONTENTS WHERE ID = ?";

        _log.info(sql + aContentId);
        Connection conn = _dbAccessMgr.getConnection();

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setLong(1, aContentId);

            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next()) {
                contentInfo.setStatus(rs.getString("STATUS"));
                contentInfo.setContentName(rs.getString("CONTENTNAME"));
                contentInfo.setDescription(rs.getString("DESCRIPTION"));
                contentInfo.setDeliveryMode(rs.getString("DELIVERYMODE"));
                contentInfo.setAggregatorId(rs.getInt("AGGREGATORID"));
            }
            contentInfo.setAggregatorList(getAggregatoList());
            DBUtil.close(preparedStmt);
        } catch (SQLException sqlexp) {
            throw new MCDSException(sqlexp.toString());
        } finally {
            DBUtil.close(conn);
        }
        return contentInfo;

    }

    private Map getAggregatoList() throws MCDSException {
        Map<Integer, String> aggList = new HashMap<Integer, String>();
        String sql = "SELECT ID, AGGREGATORNAME FROM AGGREGATORS ";
        _log.info(sql);
        Connection conn = _dbAccessMgr.getConnection();

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                aggList.put(rs.getInt("ID"), rs.getString("AGGREGATORNAME"));
            }
            DBUtil.close(preparedStmt);
        } catch (Exception e) {
            throw new MCDSException(e.toString());
        } finally {
            DBUtil.close(conn);
        }
        return aggList;

    }

    /**
     * Update the content informations
     * 
     * @param aContentInfo
     * @return
     * @throws MCDSException
     * @throws RemoteException
     */
    public Boolean updateContentInfo(ContentInfo aContentInfo) throws MCDSException {
        boolean flag = false;
        String sql = "UPDATE CONTENTS SET STATUS = ?, CONTENTNAME = ?, DESCRIPTION = ?, AGGREGATORID = ? WHERE ID = ?";
        Connection conn = _dbAccessMgr.getConnection();
        int count = 0;
        try {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            String val = null;
            if (aContentInfo.getStatus() == null)
                val = "";
            else
                val = aContentInfo.getStatus();
            pstmt.setString(1, val);
            if (aContentInfo.getContentName() == null)
                val = "";
            else
                val = aContentInfo.getContentName();
            pstmt.setString(2, val);
            if (aContentInfo.getDescription() == null)
                val = "";
            else
                val = aContentInfo.getDescription();
            pstmt.setString(3, val);

            if (aContentInfo.getAggregatorId() == null)
                val = "0";
            else
                val = "" + aContentInfo.getAggregatorId();
            pstmt.setInt(4, Integer.parseInt(val));
            pstmt.setLong(5, aContentInfo.getContentId());
            count = pstmt.executeUpdate();
            if (count >= 1)
                flag = true;
            DBUtil.close(pstmt);
        } catch (SQLException e) {
            _log.info("Exception Occured while Updating Content Data");
            throw new MCDSException(e.toString());
        } finally {
            DBUtil.close(conn);
        }

        return flag;
    }

}
