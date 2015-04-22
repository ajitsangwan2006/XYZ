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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.dmi.AggregatorInfo;
import com.gisil.mcds.dmi.NameIdInfo;
import com.gisil.mcds.dmi.PhoneMappingInfo;
import com.gisil.mcds.util.LogUtil;

/**
 * The class <code>AggregatorDBMgr</code> get all the available aggregators.
 * 
 * @author <a href ="mailto:amit.sachan@appulse.com">Amit Sachan</a>
 * @version 1.0 <br/>
 */
public class AggregatorDBMgr {

	/**
	 * A IDBAccessManager reference
	 */
	private IDBAccessManager _dbAccessMgr;

	/**
	 * Logger reference
	 */
	private Logger _log;

	/**
	 * Default constructor
	 */
	public AggregatorDBMgr() {
		_dbAccessMgr = DBAccessManagerFactory.getDBAccessManager();
		_log = LogUtil.getLogger(getClass());
	}

	/**
	 * 
	 * @param info
	 * @return
	 * @throws MCDSException
	 */
	public Boolean updateAggregators(AggregatorInfo info) throws MCDSException {
		String sql = "UPDATE  AGGREGATORS SET TUSER= ENCRYPT(?)  , TPASSWORD = ENCRYPT(?) , Description = ? , webURL = ? ,"
					+ " STATUS= ? WHERE ID = ?";
		Connection conn = _dbAccessMgr.getConnection();
		Boolean flag = false;
		try {
			_log.info("Getting Lock on Aggregator Thread...");
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, info.getUser());
			pstmt.setString(2, info.getPassword());
			pstmt.setString(3, info.getDescription());
			pstmt.setString(4, info.getWebURL());
			pstmt.setString(5, info.getStatus());
			pstmt.setInt(6, info.getId());

			int i=pstmt.executeUpdate();
			
			//just to load fresh values
			AggregatorMgr.getAggregatorMgr().shutDown();
			AggregatorMgr aggregatorMgr = new AggregatorMgr();
			aggregatorMgr.preStartup();
			aggregatorMgr.postStartup();
			if(i>0)
			flag = true;
			else
				flag = false;
			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.severe("Unable to process the request for aggregator. Reson is :"
							+ e.getMessage());
			throw new MCDSException(e.getMessage(), e);
		} finally {
			DBUtil.close(conn);
		}

		return flag;

	}

	/**
	 * Method that returns the list of Aggregators More functionality could be
	 * added
	 * 
	 * @return List
	 * @throws MCDSException
	 */
	public List<AggregatorInfo> loadAggregator() throws MCDSException {
		AggregatorInfo agrInfo = null;
		List<AggregatorInfo> _agrInfoList = new ArrayList<AggregatorInfo>();
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select id,AGGREGATORNAME,WEBURL,DESCRIPTION,TSCREATED,TSUPDATED,STATUS,TUSER,TPASSWORD from Aggregators";

		_log.info(" sql - " + sql);

		try {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				agrInfo = createAggregatorInstance(rs);
				_agrInfoList.add(agrInfo);
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE,
					"Transaction Details loading failed due to sql exception ",
					e);
			throw new MCDSException("sql exception during loading of trx ", e);
		} finally {
			DBUtil.close(conn);
		}

		return _agrInfoList;

	}

	/**
	 * Set ResultSet values into AggregatorInfo Object 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private AggregatorInfo createAggregatorInstance(ResultSet rs)
			throws SQLException {
		AggregatorInfo agrInfo = new AggregatorInfo();
		agrInfo.setId(Integer.valueOf(rs.getInt("ID")));
		agrInfo.setAggregatorName(rs.getString("AGGREGATORNAME"));
		agrInfo.setWebURL(rs.getString("WEBURL"));
		agrInfo.setDescription(rs.getString("DESCRIPTION"));
		agrInfo.setCreated(rs.getTimestamp("TSCREATED"));
		agrInfo.setUpdated(rs.getTimestamp("TSUPDATED"));
		agrInfo.setStatus(rs.getString("STATUS"));
		agrInfo.setUser(rs.getString("TUSER"));
		agrInfo.setPassword(rs.getString("TPASSWORD"));

		return agrInfo;
	}

	/**
	 * Method that returns an Aggregators Info More functionality could be added
	 * 
	 * @return List
	 * @throws MCDSException
	 */

	public AggregatorInfo getAggregatorInfo(Integer aId) throws MCDSException {
		AggregatorInfo aInfo = new AggregatorInfo();
		String sql = "select id,AGGREGATORNAME,WEBURL,DESCRIPTION,TSCREATED,TSUPDATED,STATUS,decrypt(TUSER) as TUSER,decrypt(TPASSWORD) as TPASSWORD from Aggregators WHERE ID =?";

		_log.info(sql);
		Connection conn = _dbAccessMgr.getConnection();

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setLong(1, aId);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				aInfo = createAggregatorInstance(rs);
			}
			DBUtil.close(preparedStmt);
		} catch (SQLException sqlexp) {
			throw new MCDSException(sqlexp.toString());
		} finally {
			DBUtil.close(conn);
		}

		return aInfo;
	}
	
	/**
	 * returns the pair of name and there ids of aggrigator
	 * @return
	 * @throws MCDSException
	 */
	public List<NameIdInfo> getAggNameList() throws MCDSException {
		NameIdInfo info =new NameIdInfo();
		List<NameIdInfo> list = new ArrayList<NameIdInfo>();
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select ID, AGGREGATORNAME from AGGREGATORS";
		try {
			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				info.setId(rs.getInt("ID"));
				info.setValue(rs.getString("AGGREGATORNAME"));
				list.add(info);
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log
					.log(Level.SEVERE,
							"Error while loading Aggregators name.", e);
			throw new MCDSException(
					"sql exception Error while loading Aggregators name from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

		
	}
    /**
     * This method is executing sql queries for finding phone mapping info list from phone_mapping table
     * 
     * @throws MCDSException
     * @return List
     */
    public List<PhoneMappingInfo> getPhoneMapping()throws MCDSException{

		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select DISTINCT AGGREGATORCODE, AGGREGATORMAKE, AGGREGATORMODEL "
				+ ",DECODE(MAKE, null,' ', MAKE) as MAKE "
				+ ",DECODE(MODEL, null,' ', MODEL) as MODEL "
				+ "from phone_mapping " + "where status = 'ENABLED' order by AGGREGATORCODE";
		List<PhoneMappingInfo> list = new ArrayList<PhoneMappingInfo>();
		try {
			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PhoneMappingInfo info = new PhoneMappingInfo();
				info.setAggPhoneCode(rs.getString("AGGREGATORCODE"));
				info.setAggPhoneMake(rs.getString("AGGREGATORMAKE"));
				info.setAggPhoneModel(rs.getString("AGGREGATORMODEL"));
				info.setPhoneMake(rs.getString("MAKE"));
				info.setPhoneModel(rs.getString("MODEL"));

				list.add(info);
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "Error while loading Phone Mappings.", e);
			throw new MCDSException(
					"sql exception Error while loading Phone Mappings from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
    }
    /**
     * This method is executing sql queries for finding phone mapping info from phone_mapping table
     * 
     * @throws MCDSException
     * @return List
     */
    public PhoneMappingInfo getPhoneMapping(String aggCode)throws MCDSException{

		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select DISTINCT AGGREGATORCODE, AGGREGATORMAKE, AGGREGATORMODEL "
				+ ",DECODE(MAKE, null,' ', MAKE) as MAKE "
				+ ",DECODE(MODEL, null,' ', MODEL) as MODEL "
				+ "from phone_mapping "
				+ "where AGGREGATORCODE = ? ";
		PhoneMappingInfo info = new PhoneMappingInfo();
		try {
			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,aggCode);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				info.setAggPhoneCode(rs.getString("AGGREGATORCODE"));
				info.setAggPhoneMake(rs.getString("AGGREGATORMAKE"));
				info.setAggPhoneModel(rs.getString("AGGREGATORMODEL"));
				info.setPhoneMake(rs.getString("MAKE"));
				info.setPhoneModel(rs.getString("MODEL"));
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "Error while loading Phone Mappings.", e);
			throw new MCDSException(
					"sql exception Error while loading Phone Mappings from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return info;
    }
    /**
     * Update the phone mapping
     * 
     * @param commFormula
     * @return
     * @throws MCDSException
     */
    public Boolean isUpdatedPhoneMapping(PhoneMappingInfo phoneMapping)throws MCDSException{
		boolean flag;
		String sql = "UPDATE phone_mapping  set MAKE = ?, MODEL =? " + "WHERE AGGREGATORCODE =?";
		Connection conn = _dbAccessMgr.getConnection();
		PreparedStatement pstmt =null;
		try {
		pstmt =	conn.prepareStatement(sql);
		pstmt.setString(1, phoneMapping.getPhoneMake());
		pstmt.setString(2, phoneMapping.getPhoneModel());
		pstmt.setString(3, phoneMapping.getAggPhoneCode());
		_log.info("code :"+phoneMapping.getAggPhoneCode()+" Make: "+phoneMapping.getPhoneMake()+" Model:"+phoneMapping.getPhoneModel());
		
			int results = pstmt.executeUpdate();
			_log.info("Phone Mapping Updated successfully # " + results);
			flag = true;
			DBUtil.close(pstmt);
		} catch (SQLException e) {
			flag = false;
			int errorCode = e.getErrorCode();
			if (errorCode == 1)
				throw new MCDSException(
						"Phone Mapping already defined with the provided inputs");
			throw new MCDSException(e.getMessage());
		}finally {
			DBUtil.close(conn);
		}
		return flag;
    }
	
	

}
