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
package com.gisil.mcds.commission;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gisil.mcds.aggregator.AggregatorMgr;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.dmi.CommFormulaInfo;
import com.gisil.mcds.dmi.CommFormulaMappingInfo;
import com.gisil.mcds.dmi.EntityDMLRequest;
import com.gisil.mcds.dmi.EntityDMLResponse;
import com.gisil.mcds.dmi.NameIdInfo;
import com.gisil.mcds.dmi.PartnerInfo;
import com.gisil.mcds.util.CommonUtil;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.util.LogUtil;

/**
 * The class<code>CommissionDBMgr</code> indicates to commission meta data .
 * This class is executing different sql queries for finding commission formula,
 * finding class surcharge,insert commission in transaction commission table,
 * finding partner,add/delete and modify class surcharge,add/delete and modify
 * commission formula,add/delete and modify commission formula
 * mapping,add/delete and modify partner
 * 
 * @author 
 */
public class CommissionDBMgr {

	/**
	 * Created instance
	 */
	private IDBAccessManager _dbAccessMgr;

	/**
	 * log details
	 */
	private Logger _log;

	/**
	 * constructs the commission data base manager
	 */
	public CommissionDBMgr() {
		super();
		_log = LogUtil.getLogger(CommissionDBMgr.class);
		_dbAccessMgr = DBAccessManagerFactory.getDBAccessManager();
	}

	/**
	 * This method is executing sql queries for finding the commission
	 * id,formula id, partner id,commission type,commission
	 * value,surcharge,surcharge type and is class surcharge in comm_formula
	 * table where formulaid=? and status='e' enabled
	 * 
	 * @throws BustikException
	 * @return List
	 */
	public Collection<CommissionFormula> findCommFormulas(IContext ctx)
			throws MCDSException {
		Collection<CommissionFormula> list = new ArrayList<CommissionFormula>();
		if (_log.isLoggable(Level.FINEST))
			_log.finest("findCommFormulas called with ctx " + ctx);

		final String sql = "SELECT ID,FORMULAID ,PARTNERID ,COMMISSIONTYPE ,COMMVALUE "
				+ ",SURCHARGE ,SURCHARGETYPE ,ISCLASSSURCHARGE "
				+ "FROM COMM_FORMULA WHERE FORMULAID=? AND STATUS='ENABLED'";

		Connection conn = _dbAccessMgr.getConnection();

		try {
			Integer formulaId = lookupFormulaId(conn, ctx);
			if (formulaId == null)
				throw new MCDSException("formula not defined for context - "
						+ ctx);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, formulaId.intValue());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CommissionFormula formula = CommissionFormula.create(rs);
				list.add(formula);
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE,
					"sql exception occured while loading commission formulas.",
					e);
			throw new MCDSException(
					"Database error while loading the commission formulas");
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * This method is executing sql queries for finding the commission formula
	 * id from comm_formula_mapping table where aggregatorid=?,delno=?,and
	 * trxtype=?
	 * 
	 * @throws SQLException
	 * @throws MCDSException
	 * @return Formula id
	 */
	private Integer lookupFormulaId(Connection conn, IContext ctx)
			throws SQLException, MCDSException {
		String sql = "SELECT COMMFORMULAID" + " FROM COMM_FORMULA_MAPPING"
				+ " WHERE AGGREGATORID =? AND DELNO=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		Integer formulaId;
		try {
			pstmt.setObject(1, ctx.getValue(IContext.AGGREGATOR_ID));
			pstmt.setObject(2, ctx.getValue(IContext.MERCHANT_DEL_NO));

			ResultSet rs = pstmt.executeQuery();
			formulaId = null;
			if (rs.next()) {
				_log.fine("formula mapping found.");
				formulaId = Integer.valueOf(rs.getInt(1));
			} else {
				rs.close();// close resultset
				_log.finer("formula mapping specific to delno '"
						+ ctx.getValue(IContext.MERCHANT_DEL_NO)
						+ "' is not defined...");

				pstmt.setObject(1, ctx.getValue(IContext.AGGREGATOR_ID));
				pstmt.setObject(2, "-1");// set with default-one
				// now try with global formula.
				rs = pstmt.executeQuery();
				if (rs.next()) {
					_log.fine("formula mapping found.");
					formulaId = Integer.valueOf(rs.getInt(1));
				}
			}

			if (rs.next())
				_log.warning("multiple formula mapping found.");

		} finally {
			// this will also close the RS.
			DBUtil.close(pstmt);
		}

		_log.info("formula mapping found with formual Id -" + formulaId);
		return formulaId;
	}

	/**
	 * This method is executing sql queries for inserting trxid,partnerid and
	 * commvalue in transaction_commission table
	 * 
	 * @throws MCDSException
	 */
	public void saveCommission(Integer trxId,
			Map<Integer, Number> trxCommissions) throws MCDSException {

		Connection conn = _dbAccessMgr.getConnection();
		String sql = "INSERT INTO TRANSACTION_COMMISSION (TRXID , PARTNERID , COMMVALUE )"
				+ "VALUES (? , ? , ? )";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (Map.Entry<Integer, Number> pair : trxCommissions.entrySet()) {
				pstmt.setInt(1, trxId.intValue());
				pstmt.setInt(2, pair.getKey().intValue());
				pstmt.setDouble(3, pair.getValue().doubleValue());

				pstmt.addBatch();

			}
			int[] result = pstmt.executeBatch();

			if (_log.isLoggable(Level.INFO))
				_log
						.info(" Trx Commission batch executed successfully for transaction - "
								+ trxId
								+ " Result -"
								+ CommonUtil.toString(result));

		} catch (SQLException e) {
			if (e.getErrorCode() != 1) {
				_log.log(Level.SEVERE, "Commission value not saved.", e);
				throw new MCDSException(
						"sql exception during Saving Commission info to Database.",
						e);
			}
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * This method is executing sql queries for finding partner id,parner
	 * name,description, tscreated,teupdated and status from partner table
	 * 
	 * @throws MCDSException
	 * @return List
	 */
	public List<PartnerInfo> getPartnerList(Partner filter)
			throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select ID,PARTNERNAME,DESCRIPTION,TSCREATED,TSUPDATED,STATUS from PARTNER";
		List<PartnerInfo> list = new ArrayList<PartnerInfo>();
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + filter.getId());
		try {
			if (filter != null) {
				if (filter.getId() != null) {
					sql += " WHERE ID = '" + filter.getId() + "'";
				}
			}
			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PartnerInfo partner = new PartnerInfo();
				partner.setId(rs.getInt("ID"));
				partner.setPartnerName(rs.getString("PARTNERNAME"));
				partner.setDescription(rs.getString("DESCRIPTION"));
				partner.setStatus(rs.getString("STATUS"));
				partner.setTsCreated(rs.getTimestamp("TSCREATED"));
				partner.setTsUpdated(rs.getTimestamp("TSUPDATED"));

				list.add(partner);
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "Error while loading Partners.", e);
			throw new MCDSException(
					"sql exception Error while loading Partners from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * This method is requested to add,delete and update commission formula
	 * 
	 * @throws MCDSException
	 * @return Response
	 */
	public EntityDMLResponse updateCommissionFormula(EntityDMLRequest request)
			throws MCDSException {
		EntityDMLResponse response = new EntityDMLResponse(request
				.getEntityType());
		List<CommFormulaInfo> newList = CommonUtil.cast(request
				.getNewEntities());
		List<CommFormulaInfo> updatedList = CommonUtil.cast(request
				.getUpdatedEntities());
		List<Integer> deleteList = CommonUtil.cast(request
				.getDeletedEntityIds());
		Connection conn = _dbAccessMgr.getConnection();
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			if (autoCommit)
				conn.setAutoCommit(false);
			if (newList != null) {
				addCommissionFormulas(conn, newList);
				response.setEntityAdded(newList.size());
			}
			if (updatedList != null) {
				updateCommissionFormulas(conn, updatedList);
				response.setEntityUpdated(updatedList.size());
			}
			if (deleteList != null) {
				deleteCommissionFormulas(conn, deleteList);
				response.setEntityDeleted(deleteList.size());
			}
			_log
					.info("All batch are executed successfuly for commission formula. Now going to commit into database");
			conn.commit();
			_log.info("Commit successfuly for commission formulas");
		} catch (SQLException e) {
			_log
					.severe("Unable to process the request for commission formulas. Reson is :"
							+ e.getMessage());
			throw new MCDSException(e.getMessage(), e);
		} finally {
			DBUtil.setAutoCommitMode(conn, autoCommit);
			DBUtil.close(conn);
		}

		return response;

	}

	/**
	 * This method is executing the sql queries to deleting commission formula
	 * id from comm_formula table
	 * 
	 * @throws SQLException
	 */
	private void deleteCommissionFormulas(Connection conn,
			List<Integer> deleteList) throws SQLException {
		if (deleteList.size() > 0) {
			String sql = "DELETE FROM COMM_FORMULA WHERE ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (Integer id : deleteList) {
				pstmt.setInt(1, id.intValue());
				pstmt.addBatch();
			}

			int[] results = pstmt.executeBatch();
			_log.info("Commission Formula(s) Deleted successfully # "
					+ results.length);
		}
	}

	/**
	 * This method is executing the sql queries for updating formula
	 * id,commission type,commission value,partner
	 * id,status,tsupdated,surcharge,surcharge type,is class surcharge where
	 * id=? in comm_formula table
	 * 
	 * @throws SQLException
	 */
	private void updateCommissionFormulas(Connection conn,
			List<CommFormulaInfo> updated) throws SQLException {
		if (updated.size() > 0) {
			String sql = "UPDATE COMM_FORMULA  set FORMULAID =? ,COMMISSIONTYPE =? ,COMMVALUE=?, PARTNERID=?, STATUS= ? "
					+ ", SURCHARGE =? , SURCHARGETYPE =? , ISCLASSSURCHARGE =? " + "WHERE ID =?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (CommFormulaInfo info : updated) {
				pstmt.setInt(1, info.getFormulaId().intValue());
				pstmt.setString(2, info.getCommissionType());
				pstmt.setDouble(3, info.getCommValue().doubleValue());
				pstmt.setInt(4, info.getPartnerId().intValue());
				pstmt.setString(5, info.getStatus());
				pstmt.setDouble(6, info.getSurchange().doubleValue());
				pstmt.setString(7, info.getSurchargeType());
				if(info.isClassSurchanrge())
					pstmt.setInt(8,1);
				else
					pstmt.setInt(8,0);

				pstmt.setInt(9, info.getId().intValue());
				pstmt.addBatch();
			}
			try {
				int[] results = pstmt.executeBatch();
				_log.info("Commission Formula(s) Updated successfully # "
						+ results.length);
			} catch (SQLException e) {
				int errorCode = e.getErrorCode();
				if (errorCode == 1)
					throw new SQLException(
							"Commission Formula already defined with the provided inputs");
				throw e;
			}
		}
	}

	/**
	 * This method is executing sql queries for inserting formula id,commission
	 * type,commission value,partner id,status,surcharge,surcharge type,is class
	 * surcharge,tscreated,tsupdated in comm_formula table
	 * 
	 * @throws SQLException
	 */
	private void addCommissionFormulas(Connection conn,
			List<CommFormulaInfo> added) throws SQLException {
		if (added.size() > 0) {
			String sql = "insert into COMM_FORMULA  (FORMULAID ,COMMISSIONTYPE ,COMMVALUE ,PARTNERID ,STATUS "
					+ ", SURCHARGE , SURCHARGETYPE ,ISCLASSSURCHARGE )"
					+ " VALUES ( ?, ?, ? ,? ,? ,? ,? ,? )";

			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (CommFormulaInfo info : added) {
				pstmt.setInt(1, info.getFormulaId().intValue());
				pstmt.setString(2, info.getCommissionType());
				pstmt.setDouble(3, info.getCommValue().doubleValue());
				pstmt.setInt(4, info.getPartnerId().intValue());
				pstmt.setString(5, info.getStatus());
				pstmt.setDouble(6, info.getSurchange().doubleValue());
				pstmt.setString(7, info.getSurchargeType());
				if(info.isClassSurchanrge())
					pstmt.setInt(8,1);
				else
					pstmt.setInt(8,0);
				pstmt.addBatch();
			}
			try {
				int[] results = pstmt.executeBatch();
				_log.info("Commission Formula(s) inserted successfully # "
						+ results.length);
			} catch (SQLException e) {
				int errorCode = e.getErrorCode();
				if (errorCode == 1)
					throw new SQLException(
							"Commission Formula already defined with the provided inputs");
				throw e;
			}
		}
	}

	/**
	 * This method is requesting to add,delete and update commission formula
	 * mapping information
	 * 
	 * @throws MCDSException
	 * @return Response
	 */
	public EntityDMLResponse updateCommissionFormulaMapping(
			EntityDMLRequest request) throws MCDSException {
		EntityDMLResponse response = new EntityDMLResponse(request
				.getEntityType());
		List<CommFormulaMappingInfo> added = CommonUtil.cast(request
				.getNewEntities());
		List<CommFormulaMappingInfo> updated = CommonUtil.cast(request
				.getUpdatedEntities());
		List<Integer> deleted = CommonUtil.cast(request.getDeletedEntityIds());
		Connection conn = _dbAccessMgr.getConnection();
		boolean autoCommit = true;
		try {
			autoCommit = conn.getAutoCommit();
			if (autoCommit)
				conn.setAutoCommit(false);
			if (added != null) {
				addCommissionFormulaMappings(conn, added);
				response.setEntityAdded(added.size());
			}
			if (updated != null) {
				updateCommissionFormulaMappings(conn, updated);
				response.setEntityUpdated(updated.size());
			}
			if (deleted != null) {
				deleteCommissionFormulaMappings(conn, deleted);
				response.setEntityDeleted(deleted.size());
			}
			_log
					.info("All batch are executed successfuly for commission formula mapping. Now going to commit into database");
			conn.commit();
			_log.info("Commit successfuly for commission formula mappings");
		} catch (SQLException e) {
			_log
					.severe("Unable to process the request for commission formula mappings. Reson is :"
							+ e.getMessage());
			throw new MCDSException(e.getMessage(), e);
		} finally {
			DBUtil.setAutoCommitMode(conn, autoCommit);
			DBUtil.close(conn);
		}

		return response;
	}

	/**
	 * This method is executing the sql queries to delete commission formula
	 * mapping id from comm_formula_mapping table
	 * 
	 * @throws SQLException
	 */
	private void deleteCommissionFormulaMappings(Connection conn,
			List<Integer> deleted) throws SQLException {
		if (deleted.size() > 0) {
			String sql = "DELETE FROM COMM_FORMULA_MAPPING WHERE ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (Integer id : deleted) {
				pstmt.setInt(1, id.intValue());
				pstmt.addBatch();
			}

			int[] results = pstmt.executeBatch();
			_log.info("Commission Formula Mapping(s) Deleted successfully # "
					+ results.length);
		}
	}

	/**
	 * This method is executing sql queries for updating aggregator
	 * id,commission formula id, trx type and delno in comm_formula_mapping
	 * table
	 * 
	 * @throws SQLException
	 */
	private void updateCommissionFormulaMappings(Connection conn,
			List<CommFormulaMappingInfo> updated) throws SQLException {
		if (updated.size() > 0) {
			String sql = "UPDATE COMM_FORMULA_MAPPING  set AGGREGATORID =? ,COMMFORMULAID =? ,TRXTYPE=?, DELNO=? "
					+ "WHERE ID =?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (CommFormulaMappingInfo info : updated) {
				pstmt.setInt(1, info.getAggregatorId().intValue());
				pstmt.setInt(2, info.getCommformulaId().intValue());
				pstmt.setString(3, info.getTrxType());
				String delNo = CommonUtil.fixMobileNo(info.getDelNo());
				pstmt.setLong(4, Long.parseLong(delNo == null ? "-1" : delNo));
				pstmt.setInt(5, info.getId().intValue());
				pstmt.addBatch();
			}

			try {
				int[] results = pstmt.executeBatch();
				_log
						.info("Commission Formula Mapping(s) Updated successfully # "
								+ results.length);
			} catch (SQLException e) {
				int errorCode = e.getErrorCode();
				if (errorCode == 1)
					throw new SQLException(
							"Some commission mapping(s) with the same details have been added eariler. Please provide different inputs");
			}

		}
	}

	/**
	 * This method is executing sql queries to inserting aggregator
	 * id,commission formula id, trx type and delno in comm_formula_mapping
	 * table
	 * 
	 * @throws SQLException
	 */
	private void addCommissionFormulaMappings(Connection conn,
			List<CommFormulaMappingInfo> added) throws SQLException {
		if (added.size() > 0) {
			String sql = "insert into COMM_FORMULA_MAPPING  (AGGREGATORID ,COMMFORMULAID ,DELNO )"
					+ " VALUES ( ?, ?, ? )";

			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (CommFormulaMappingInfo info : added) {
				_log.info(" values  1- " + info.getAggregatorId()+"  2- "+info.getCommformulaId()+"  3- "+info.getDelNo());
				pstmt.setInt(1, info.getAggregatorId().intValue());
				pstmt.setInt(2, info.getCommformulaId().intValue());
				pstmt.setLong(3, Long.parseLong(info.getDelNo()));

				pstmt.addBatch();
			}
			try {
				int[] results = pstmt.executeBatch();
				_log
						.info("Commission Formula Mapping(s) inserted successfully # "
								+ results.length);
			} catch (SQLException e) {
				int errorCode = e.getErrorCode();
				if (errorCode == 1)
					throw new SQLException(
							"A commission formula mapping with the same details have been added eariler. Please provide different inputs");
				_log.info("Error is : "+e.getMessage());
				throw e;
			}
		}
	}

	

	
	void handleSQLException(SQLException ex) throws MCDSException {
		// String msg = ex.getMessage();

	}

	/**
	 * This method is executing sql queries for finding the id,formula
	 * id,commission type, commission value,partner
	 * id,status,tscreted,tsupdated,surcharge,surcharge type, is class surcharge
	 * from comm_formula table
	 * 
	 * @throws MCDSException
	 * @return list
	 */
	public List<CommFormulaInfo> getCommFormulaList(CommFormulaInfo filter)
			throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select ID ,FORMULAID, COMMISSIONTYPE ,COMMVALUE ,PARTNERID ,STATUS ,TSCREATED ,TSUPDATED "
				+ ",SURCHARGE ,SURCHARGETYPE ,ISCLASSSURCHARGE from COMM_FORMULA";
		List<CommFormulaInfo> list = new ArrayList<CommFormulaInfo>();
		try {
			if (filter != null) {
				boolean where = false;
				if (filter.getStatus() != null) {
					sql += " WHERE STATUS = '" + filter.getStatus() + "'";
					where = true;
				}
				if (where) {
					// append more filter here.
				}
			}

			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CommFormulaInfo info = new CommFormulaInfo();
				info.setId(rs.getInt("ID"));
				info.setFormulaId(rs.getLong("FORMULAID"));
				info.setCommissionType(rs.getString("COMMISSIONTYPE"));
				info.setCommValue(Double.valueOf(rs.getDouble("COMMVALUE")));
				info.setPartnerId(rs.getLong("PARTNERID"));
				info.setSurchange(Double.valueOf(rs.getDouble("SURCHARGE")));
				info.setSurchargeType(rs.getString("SURCHARGETYPE"));
				info.setStatus(rs.getString("STATUS"));
				info.setCreated(rs.getTimestamp("TSCREATED"));
				info.setUpdated(rs.getTimestamp("TSUPDATED"));

				list.add(info);
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log
					.log(Level.SEVERE,
							"Error while loading commission formula.", e);
			throw new MCDSException(
					"sql exception Error while loading commission formula from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * This method is executing sql queries for finding id,aggregator
	 * id,commission fourmula id, trx type and delno from comm_formula_mapping
	 * table
	 * 
	 * @throws MCDSException
	 * @return List
	 */
	public List<CommFormulaMappingInfo> getCommFormulaMappingList(
			CommFormulaMappingInfo filter) throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select ID ,AGGREGATORID ,COMMFORMULAID ,TRXTYPE ,DELNO from COMM_FORMULA_MAPPING";
		List<CommFormulaMappingInfo> list = new ArrayList<CommFormulaMappingInfo>();
		try {
			if (filter != null) {
				boolean where = false;
				if (filter.getAggregatorId() != null) {
					sql += " WHERE AGGREGATORID = '" + filter.getAggregatorId()
							+ "'";
					where = true;
				}
				if (where) {
					// append more filter here.
				}
			}

			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CommFormulaMappingInfo info = new CommFormulaMappingInfo();
				info.setId(rs.getLong("ID"));
				info
						.setAggregatorId(Long.valueOf(rs
								.getInt("AGGREGATORID")));
				info.setCommformulaId(Long.valueOf(rs
						.getInt("COMMFORMULAID")));
				info.setTrxType(rs.getString("TRXTYPE"));
				info.setDelNo(rs.getString("DELNO"));

				list.add(info);
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE,
					"Error while loading commission formula mapping.", e);
			throw new MCDSException(
					"sql exception Error while loading commission formula mapping from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * Find Class Surcharge If any..
	 * 
	 * @param ctx
	 * @return
	 * @throws MCDSException
	 */
	public ClassSurcharge findClassSurcharge(IContext ctx) throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		ClassSurcharge surchage = null;
		String sql = "SELECT CLASSCODE ,CLASSNAME ,SURCHARGE ,SURCHARGETYPE"
				+ " ,STATUS ,AGGREGATORID FROM CLASS_SURCHARGES WHERE CLASSCODE= ? AND AGGREGATORID=? AND STATUS= 'ENABLED'";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, ctx.getValue(IContext.DELIVERY_MODE));
			pstmt.setObject(2, ctx.getValue(IContext.AGGREGATOR_ID));

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				_log.fine("class surchage found for ctx - " + ctx);
				surchage = ClassSurcharge.create(rs);
			} else {
				_log.fine("no class surchage found for ctx -" + ctx);
			}

			if (rs.next())
				_log.warning("multiple class surchage found using first one.");

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE,
					"sql exception occured while loading class surchage.", e);
			throw new MCDSException(
					"Database error while loading class surchage.");
		} finally {
			DBUtil.close(conn);
		}
		return surchage;
	}
	
	/**
	 * Return list of commission formula info for commission sui
	 * @return
	 * @throws MCDSException
	 * @throws RemoteException
	 */
	public List getCommFormula() throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select COMM_FORMULA.ID, COMM_FORMULA.FORMULAID, COMMISSIONTYPE ,COMMVALUE ,PARTNER.PARTNERNAME ,COMM_FORMULA.STATUS ,SURCHARGE ,SURCHARGETYPE ,ISCLASSSURCHARGE, PARTNERID "
				+ "from COMM_FORMULA inner join PARTNER "
				+ "on COMM_FORMULA.PARTNERID = PARTNER.ID";
		List<CommFormulaInfo> list = new ArrayList<CommFormulaInfo>();
		try {
			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CommFormulaInfo info = new CommFormulaInfo();
				info.setId(rs.getInt("ID"));
				info.setFormulaId(rs.getLong("FORMULAID"));
				info.setCommissionType(rs.getString("COMMISSIONTYPE"));
				info.setCommValue(Double.valueOf(rs.getDouble("COMMVALUE")));
				info.setPartnerId(rs.getLong("PARTNERID"));
				info.setSurchange(Double.valueOf(rs.getDouble("SURCHARGE")));
				info.setSurchargeType(rs.getString("SURCHARGETYPE"));
				info.setStatus(rs.getString("STATUS"));
				info.setPartnerName(rs.getString("PARTNERNAME"));
				if(rs.getInt("ISCLASSSURCHARGE")==1)
					info.setIsClassSurchanrge(true);
				else
					info.setIsClassSurchanrge(false);
				list.add(info);
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log
					.log(Level.SEVERE,
							"Error while loading commission formula.", e);
			throw new MCDSException(
					"sql exception Error while loading commission formula from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

	}
	
	/**
	 * Return Commission formula info for commission sui
	 * @return
	 * @throws MCDSException
	 * @throws RemoteException
	 */
	public CommFormulaInfo getFormulas(Integer id) throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select COMM_FORMULA.ID, COMM_FORMULA.FORMULAID, COMMISSIONTYPE ,COMMVALUE ,PARTNER.PARTNERNAME ,COMM_FORMULA.STATUS ,SURCHARGE ,SURCHARGETYPE ,ISCLASSSURCHARGE, PARTNERID "
				+ "from COMM_FORMULA inner join PARTNER "
				+ "on COMM_FORMULA.PARTNERID = PARTNER.ID "
				+ "where COMM_FORMULA.ID = ?";
		CommFormulaInfo info = new CommFormulaInfo();
		try {
			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				
				info.setId(rs.getInt("ID"));
				info.setFormulaId(rs.getLong("FORMULAID"));
				info.setCommissionType(rs.getString("COMMISSIONTYPE"));
				info.setCommValue(Double.valueOf(rs.getDouble("COMMVALUE")));
				info.setPartnerId(rs.getLong("PARTNERID"));
				info.setSurchange(Double.valueOf(rs.getDouble("SURCHARGE")));
				info.setSurchargeType(rs.getString("SURCHARGETYPE"));
				info.setStatus(rs.getString("STATUS"));
				info.setPartnerName(rs.getString("PARTNERNAME"));
				if(rs.getInt("ISCLASSSURCHARGE")==1)
					info.setIsClassSurchanrge(true);
				else
					info.setIsClassSurchanrge(false);
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log
					.log(Level.SEVERE,
							"Error while loading commission formula.", e);
			throw new MCDSException(
					"sql exception Error while loading commission formula from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}
		info.setPartnerNameList(getPartenerNameList());
		return info;

	}
	
	/**
	 * returns the all partner name and there ids
	 * @return
	 * @throws MCDSException
	 */
	public List<NameIdInfo> getPartenerNameList() throws MCDSException {
		
		List<NameIdInfo> list =new ArrayList<NameIdInfo>();
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select ID, PARTNERNAME from PARTNER";
		try {
			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				NameIdInfo info = new NameIdInfo();
				info.setId(rs.getInt("ID"));
				info.setValue(rs.getString("PARTNERNAME"));
				list.add(info);
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log
					.log(Level.SEVERE,
							"Error while loading Partner name.", e);
			throw new MCDSException(
					"sql exception Error while loading Partner name from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

		
	}
	/**
	 * Update the commission formula
	 * @param commFormula
	 * @return
	 * @throws MCDSException
	 */
	public Boolean isUpdatedCommission(CommFormulaInfo commFormula)
			throws MCDSException {
		boolean flag;
		String sql = "UPDATE COMM_FORMULA  set FORMULAID = ?, COMMISSIONTYPE =? ,COMMVALUE=?, PARTNERID=?, STATUS= ? "
				+ ", SURCHARGE =? , SURCHARGETYPE =?, ISCLASSSURCHARGE =? " + "WHERE ID =?";
		Connection conn = _dbAccessMgr.getConnection();
		PreparedStatement pstmt =null;
		try {
		pstmt =	conn.prepareStatement(sql);
		pstmt.setLong(1, commFormula.getFormulaId());
		pstmt.setString(2, commFormula.getCommissionType());
		pstmt.setDouble(3, commFormula.getCommValue().doubleValue());
		pstmt.setInt(4, commFormula.getPartnerId().intValue());
		pstmt.setString(5, commFormula.getStatus());
		pstmt.setDouble(6, commFormula.getSurchange().doubleValue());
		pstmt.setString(7, commFormula.getSurchargeType());
		if(commFormula.isClassSurchanrge())
			pstmt.setInt(8, 1);
		else
			pstmt.setInt(8, 0);
		pstmt.setInt(9, commFormula.getId().intValue());
		
			int results = pstmt.executeUpdate();
			_log.info("Commission Formula Updated successfully # " + results);
			flag = true;
			DBUtil.close(pstmt);
		} catch (SQLException e) {
			flag = false;
			int errorCode = e.getErrorCode();
			if (errorCode == 1)
				throw new MCDSException(
						"Commission Formula already defined with the provided inputs");
			throw new MCDSException(e.getMessage());
		}finally {
			DBUtil.close(conn);
		}
		return flag;
	}
	
	/**
	 * This method is executing sql queries for finding id,aggregator
	 * id,commission fourmula id and delno from comm_formula_mapping
	 * table
	 * 
	 * @throws MCDSException
	 * @return List
	 */
	public List<CommFormulaMappingInfo> getCommFormulaMapping() throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select COMM_FORMULA_MAPPING.ID ,COMM_FORMULA_MAPPING.AGGREGATORID ,AGGREGATORS.AGGREGATORNAME, COMMFORMULAID ,DELNO "
				+ "from COMM_FORMULA_MAPPING "
				+ "inner join AGGREGATORS "
				+ "on COMM_FORMULA_MAPPING.AGGREGATORID = AGGREGATORS.ID";
		List<CommFormulaMappingInfo> list = new ArrayList<CommFormulaMappingInfo>();
		try {
			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CommFormulaMappingInfo info = new CommFormulaMappingInfo();
				info.setId(rs.getLong("ID"));
				info
						.setAggregatorId(Long.valueOf(rs
								.getInt("AGGREGATORID")));
				info.setAggregatorName(rs.getString("AGGREGATORNAME"));
				info.setCommformulaId(Long.valueOf(rs
						.getInt("COMMFORMULAID")));
				info.setDelNo(rs.getString("DELNO"));

				list.add(info);
			}
			//mappingInfo.setAggMap(AggregatorMgr.getAggregatorMgr().getAgrDBMgr().getAggNameList());
			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE,
					"Error while loading commission formula mapping.", e);
			throw new MCDSException(
					"sql exception Error while loading commission formula mapping from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}
	
	/**
	 * This method is executing sql queries for finding id,aggregator
	 * id,commission fourmula id and delno from comm_formula_mapping
	 * table
	 * 
	 * @throws MCDSException
	 * @return List
	 */
	public CommFormulaMappingInfo getCommFormulaMapping(Integer id) throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select ID ,AGGREGATORID ,COMMFORMULAID ,DELNO from COMM_FORMULA_MAPPING where ID=?";
		CommFormulaMappingInfo info = new CommFormulaMappingInfo();
		try {
			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				info.setId(rs.getLong("ID"));
				info
						.setAggregatorId(Long.valueOf(rs
								.getInt("AGGREGATORID")));
				info.setCommformulaId(Long.valueOf(rs
						.getInt("COMMFORMULAID")));
				info.setDelNo(rs.getString("DELNO"));
			}
			info.setAggMap(AggregatorMgr.getAggregatorMgr().getAgrDBMgr().getAggNameList());
			info.setCommId(getCommIdList());
			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE,
					"Error while loading commission formula mapping.", e);
			throw new MCDSException(
					"sql exception Error while loading commission formula mapping from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return info;
	}
	
	/**
	 * returns the all commission ids
	 * @return
	 * @throws MCDSException
	 */
	public List<Integer> getCommIdList() throws MCDSException {
		List<Integer> list =new ArrayList<Integer>();
		Connection conn = _dbAccessMgr.getConnection();
		String sql = "select DISTINCT FORMULAID from COMM_FORMULA";
		try {
			_log.info(" sql - " + sql);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("FORMULAID"));
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log
					.log(Level.SEVERE,
							"Error while loading commission id list.", e);
			throw new MCDSException(
					"sql exception Error while loading commission id list from Database.",
					e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

		
	}
	
	/**
	 * Update the commission formula mapping
	 * @param commFormula
	 * @return
	 * @throws MCDSException
	 */
	public Boolean isUpdatedCommissionMapping(CommFormulaMappingInfo commFormula)
			throws MCDSException {
		boolean flag;
		String sql = "UPDATE COMM_FORMULA_MAPPING  set AGGREGATORID =? ,COMMFORMULAID=?, DELNO=? where ID =?";
		Connection conn = _dbAccessMgr.getConnection();
		PreparedStatement pstmt =null;
		try {
			pstmt =	conn.prepareStatement(sql);
			pstmt.setLong(1, commFormula.getAggregatorId());
			pstmt.setDouble(2, commFormula.getCommformulaId());
			pstmt.setDouble(3, Double.parseDouble(commFormula.getDelNo()));
			pstmt.setDouble(4, commFormula.getId());
			int results = pstmt.executeUpdate();
			_log.info("Commission Formula Updated successfully # " + results);
			flag = true;
			DBUtil.close(pstmt);
		} catch (SQLException e) {
			flag = false;
			int errorCode = e.getErrorCode();
			if (errorCode == 1)
				throw new MCDSException(
						"Commission Formula mapping already defined with the provided inputs");
			throw new MCDSException(e.getMessage());
		}finally {
			DBUtil.close(conn);
		}
		return flag;
	}
	
	/**
	 * Add the new commission formula into table
	 * @param info
	 * @throws MCDSException
	 */
	public void addCommission(CommFormulaInfo info)throws MCDSException{
		Connection conn = null;
		List<CommFormulaInfo> added = new ArrayList<CommFormulaInfo>();
		added.add(info);
		try{
		conn = _dbAccessMgr.getConnection();
		addCommissionFormulas(conn, added);
		}catch(SQLException e){
			throw new MCDSException(e.getMessage());
		}
		finally {
			DBUtil.close(conn);
		}
	}
	
	/**
	 * Add the new commission formula mapping info into table
	 * @param info
	 * @throws MCDSException
	 */
	public void addCommissionMapping(CommFormulaMappingInfo info)throws MCDSException{
		Connection conn = null;
		List<CommFormulaMappingInfo> added = new ArrayList<CommFormulaMappingInfo>();
		added.add(info);
		try{
		conn = _dbAccessMgr.getConnection();
		addCommissionFormulaMappings(conn, added);
		}catch(SQLException e){
			throw new MCDSException(e.getMessage());
		}
		finally {
			DBUtil.close(conn);
		}
	}


	/**
	 * this method is temporary / it may be removed in future
	 * 
	 * @param partner
	 * @return
	 * @throws MCDSException
	 */
	public Boolean updatePartnerInfo(Partner partner) throws MCDSException 
	{
		String sql = "UPDATE  PARTNER SET DESCRIPTION = ? ,"
				+ " STATUS= ? WHERE ID = ?";
		Connection conn = _dbAccessMgr.getConnection();
		Boolean flag = false;
		try {
			
			_log.info("Updating Partner Information" + "   ::  "+ partner.getPartnerName()+"   " + partner.getId() +"  "+ partner.getStatus());
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, partner.getDescription());
			pstmt.setString(2, partner.getStatus());
			pstmt.setInt(3, partner.getId());
			_log.info(sql);
			int i = pstmt.executeUpdate();

			// just to load fresh values
			CommissionProcessor.getInstance().shutdown();
			CommissionProcessor commissionPrcr = new CommissionProcessor();
			commissionPrcr.preStartup();
			commissionPrcr.postStartup();
			
			
			if (i > 0)
				flag = true;
			else
				flag = false;
			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log
					.severe("Unable to process the request for Partners. Reson is :"
							+ e.getMessage());
			throw new MCDSException(e.getMessage(), e);
		} finally {
			DBUtil.close(conn);
		}

		return flag;
	}
	
     /**
     * To get ClassSurcharge Values
     * @param String
     * @param int
     * @return ClassSurcharge
     * @throws MCDSException
     */
	public ClassSurcharge getClassSurcharge(String classCode, int aggId) throws MCDSException{
		Connection conn = _dbAccessMgr.getConnection();
		ClassSurcharge surcharge = null;
		String sql = "SELECT CLASSCODE ,CLASSNAME ,SURCHARGE ,SURCHARGETYPE"
				+ " ,CLASS_SURCHARGES.STATUS ,AGGREGATORID ,AGGREGATORNAME FROM CLASS_SURCHARGES, AGGREGATORS"
                + " WHERE CLASSCODE= ? AND AGGREGATORID=? AND CLASS_SURCHARGES.AGGREGATORID = AGGREGATORS.ID";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classCode);
			pstmt.setLong(2, aggId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				_log.fine("class surchage found for ctx - " + classCode);
				surcharge = ClassSurcharge.create(rs);
			} else {
				_log.fine("no class surchage found for ctx -" + classCode);
			}

			if (rs.next())
				_log.warning("multiple class surchage found using first one.");

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE,
					"sql exception occured while loading class surchage.", e);
			throw new MCDSException(
					"Database error while loading class surchage.");
		} finally {
			DBUtil.close(conn);
		}
		return surcharge;		
	}
    /**
     * To get ClassSurcharge Values
     * @param
     * @return ArrayList
     * @throws MCDSException
     */
	public ArrayList<ClassSurcharge> getClassSurcharge() throws MCDSException{
		Connection conn = _dbAccessMgr.getConnection();
		ArrayList<ClassSurcharge> list = new ArrayList<ClassSurcharge>();
		String sql = "SELECT CLASSCODE ,CLASSNAME ,SURCHARGE ,SURCHARGETYPE"
				+ " ,CLASS_SURCHARGES.STATUS ,AGGREGATORID, AGGREGATORNAME FROM CLASS_SURCHARGES, AGGREGATORS"
                + " WHERE CLASS_SURCHARGES.AGGREGATORID = AGGREGATORS.ID";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				_log.fine("class surchage found");
				ClassSurcharge surcharge = ClassSurcharge.create(rs);
				list.add(surcharge);
			} 
			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE,
					"sql exception occured while loading class surchage.", e);
			throw new MCDSException(
					"Database error while loading class surchage.");
		} finally {
			DBUtil.close(conn);
		}
		return list;		
	}
    
     /**
     * To add ClassSurcharge Values
     * @param ClassSurcharge
     * @return
     * @throws MCDSException
     */
    public void addClassSurcharge(ClassSurcharge surcharge) throws MCDSException{
        String sqlQuery = "INSERT INTO CLASS_SURCHARGES(CLASSCODE,CLASSNAME,AGGREGATORID,SURCHARGETYPE,SURCHARGE,STATUS)" +
                " values(?,?,?,?,?,?)";
        
        PreparedStatement preparedStmt = null;
        Connection conn = null;
       
        try {
            conn = _dbAccessMgr.getConnection();
            
            preparedStmt = conn.prepareStatement(sqlQuery);
            
            
            preparedStmt.setString(1, surcharge.getClassCode());
            preparedStmt.setString(2, surcharge.getClassName());
            preparedStmt.setInt(3, getAggregatorId(surcharge.getAggregatorName(),conn) );
            preparedStmt.setString(4, surcharge.isSurchargeFixed()?"FIXED":"PERCENT");
            preparedStmt.setDouble(5, surcharge.getSurcharge());
            preparedStmt.setString(6, surcharge.getStatus());
            
            checkSurchareg(surcharge,conn);
            
            int result = preparedStmt.executeUpdate();
            if (result == 0)
                throw new MCDSException("Row could not inserted");
        } catch (Exception e) {
            throw new MCDSException(e.getMessage());
        } finally {
            DBUtil.close(conn);
            DBUtil.close(preparedStmt);
        }
        
    }
    /**
     * To get Aggregator Id
     * @param String
     * @param Connection
     * @return int
     * @throws MCDSException
     */
    private int getAggregatorId(String aggregatorName, Connection conn) throws MCDSException {
        // TODO Auto-generated method stub
        String sqlQuery2 = "SELECT ID FROM AGGREGATORS WHERE AGGREGATORNAME=?";
        PreparedStatement preparedStmt2 = null;
        int aggregatorId = 0;
        try {
            preparedStmt2 = conn.prepareStatement(sqlQuery2);
            preparedStmt2.setString(1, aggregatorName);
            ResultSet rs2 = preparedStmt2.executeQuery();
            while (rs2.next()) {
                aggregatorId = rs2.getInt("ID");
            }

        } catch (Exception e) {
            throw new MCDSException(e.toString());
        } finally {
            DBUtil.close(preparedStmt2);
        }
        return aggregatorId;
    }
    /**
     * To check if Surchareg value exist or not
     * @param ClassSurcharge
     * @param Connection
     * @return 
     * @throws MCDSException
     */
    private void checkSurchareg(ClassSurcharge surcharge, Connection conn) throws Exception{
        String sqlQuery = "SELECT * FROM CLASS_SURCHARGES where CLASSCODE=?";
        
        _log.info(sqlQuery);
        
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = conn.prepareStatement(sqlQuery);
            preparedStmt.setString(1, surcharge.getClassCode().trim());
            
            ResultSet rs = preparedStmt.executeQuery();
            if(rs.next())
                throw new Exception("Class Surcharge Already Exist");
        }finally {
            DBUtil.close(preparedStmt);
        }
        
    }
    
    /**
     * To update Partner Values
     * @param Partner
     * @return 
     * @throws MCDSException
     */
    public void savePartnerValues(Partner partner) throws MCDSException{
        String sqlQuery = "UPDATE PARTNER SET PARTNERNAME=?,STATUS=? where ID=?";
        PreparedStatement preparedStmt = null;
   
        Connection conn = _dbAccessMgr.getConnection();
        
        try {
            preparedStmt = conn.prepareStatement(sqlQuery);
            
            preparedStmt.setString(1, partner.getPartnerName());
            preparedStmt.setString(2, partner.getStatus());
            preparedStmt.setInt(3, partner.getId());
            
            int result = preparedStmt.executeUpdate();
            if (result == 0)
                throw new MCDSException("Row could not updated");
        } catch (Exception e) {
            throw new MCDSException(e.toString());
        } finally {
            DBUtil.close(conn);
            DBUtil.close(preparedStmt);
        }

    }
    /**
     * To add Partner Values
     * @param Partner
     * @return 
     * @throws MCDSException
     */
   public void addPartnerValues(Partner partner) throws MCDSException{
        
        String sqlQuery = "INSERT INTO PARTNER(PARTNERNAME,DESCRIPTION,STATUS) values(?,?,?)";
        PreparedStatement preparedStmt = null;
   
        Connection conn = _dbAccessMgr.getConnection();
        
        try {
            preparedStmt = conn.prepareStatement(sqlQuery);
            
            preparedStmt.setString(1, partner.getPartnerName());
            preparedStmt.setString(2, partner.getDescription());
            preparedStmt.setString(3, partner.getStatus());
            
            checkPartnerName(partner,conn);
            
            int result = preparedStmt.executeUpdate();
            if (result == 0)
                throw new MCDSException("Row could not inserted");
        } catch (Exception e) {
            throw new MCDSException(e.getMessage());
        } finally {
            DBUtil.close(conn);
            DBUtil.close(preparedStmt);
        }

    }
   /**
    * To check Partner name exist or not
    * @param Partner
    * @param Connection
    * @return 
    * @throws MCDSException
    */
   private void checkPartnerName(Partner partner, Connection conn) throws Exception{
       String sqlQuery = "SELECT * FROM PARTNER where PARTNERNAME=?";
       
       _log.info(sqlQuery);
       
       PreparedStatement preparedStmt = null;
       try {
           preparedStmt = conn.prepareStatement(sqlQuery);
           preparedStmt.setString(1, partner.getPartnerName().trim());
           
           ResultSet rs = preparedStmt.executeQuery();
           if(rs.next())
               throw new Exception("Partner Name Already Exist");
       }finally {
           DBUtil.close(preparedStmt);
       }
       
   }
   /**
    * To save surcharge values
    * @param ClassSurcharge
    * @return 
    * @throws MCDSException
    */
   public void saveSurchargeValues(ClassSurcharge surcharge) throws MCDSException{
       // TODO Auto-generated method stub
       String sqlQuery="UPDATE CLASS_SURCHARGES SET AGGREGATORID=?,CLASSNAME=?," +
               "SURCHARGE=?,SURCHARGETYPE=?,STATUS=? where CLASSCODE=?";
       PreparedStatement preparedStmt = null;
       String surchargeTpe=null;
       Connection conn = _dbAccessMgr.getConnection();
       if (surcharge.isSurchargeFixed()) {
           surchargeTpe="Fixed";
       } else {
            surchargeTpe="Percentage";
       }
       try {
           preparedStmt = conn.prepareStatement(sqlQuery);
           preparedStmt.setInt(1, getAggregatorId(surcharge.getAggregatorName(),conn));
           preparedStmt.setString(2, surcharge.getClassName());
           preparedStmt.setDouble(3, surcharge.getSurcharge());
           preparedStmt.setString(4, surchargeTpe);
           preparedStmt.setString(5, surcharge.getStatus());
           preparedStmt.setString(6, surcharge.getClassCode());
           
          int result = preparedStmt.executeUpdate();
          if(result==0)
              throw new MCDSException("Row could not updated");
       } catch (Exception e) {
           throw new MCDSException(e.toString());
       } finally {
           DBUtil.close(conn);
           DBUtil.close(preparedStmt);
       }
       
       
   }
}
