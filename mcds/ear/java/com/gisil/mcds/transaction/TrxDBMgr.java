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
package com.gisil.mcds.transaction;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.dmi.SearchTrxRequest;
import com.gisil.mcds.dmi.TransactionSummaryInfo;
import com.gisil.mcds.util.CommonUtil;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.util.LogUtil;

/**
 * @author amit sachan
 */
public class TrxDBMgr {

	private IDBAccessManager _dbAccessMgr;

	private Logger _log;

	private IdentityHashMap<Thread, Object> threadCache = new IdentityHashMap<Thread, Object>();

	public TrxDBMgr() {
		_dbAccessMgr = DBAccessManagerFactory.getDBAccessManager();
		_log = LogUtil.getLogger(getClass());
	}

	public void deleteTransaction(Integer trxId) {
		Connection conn = null;
		try {
			conn = _dbAccessMgr.getConnection();
			conn.createStatement().executeUpdate(
					"DELETE FROM TRANSACTION WHERE ID = " + trxId);

		} catch (Exception e) {
			_log.log(Level.SEVERE, "delete failed", e);
		} finally {
			DBUtil.close(conn);
		}
	}

	public Transaction loadTransaction(Integer trxId, boolean loadTrxCommission)
			throws MCDSException {
		String sql = "select ID,DELNO,MERCHANTID,TRXTYPE,AGGREGATORID,BASEAMOUNT,COMMISSIONAMOUNT,SURCHARGE"
				+ ",TOTALAMOUNT,STATUS,PARENTTRXID,RECONSTATUS,ISREFUND,TRXREMARKS,TSCREATED,TSUPDATED,"
				+ "MERCHANTDEBITAMT,MERCHANTREMAININGBAL,PAYMANTMODE,MSISDN,PHONEMAKE,PHONEMODEL,"
				+ "AGGREGATORPHONECODE,SKU,SKUNAME,AGGREGATORTRXID,RESPONSECODE,RESPONSEMESSAGE,DELIVERYMODE,"
				+ "COMMFORMULAID,TSREQUEST,TSRESPONSE from TRANSACTION WHERE ID = ?";

		return loadTransaction(sql, trxId, loadTrxCommission, false);
	}

	private Transaction loadTransaction(String sql, Integer trxId,
			boolean loadTrxCommission, boolean historyRecord)
			throws MCDSException {
		Connection conn = null;
		Transaction trx = null;

		try {
			PreparedStatement pstmt = null;
			boolean closeStmt = false;
			if (historyRecord) {
				pstmt = (PreparedStatement) threadCache.get(Thread
						.currentThread());

			}

			if (pstmt == null) {
				conn = _dbAccessMgr.getConnection();
				pstmt = conn.prepareStatement(sql);
				closeStmt = true;
			}
			pstmt.setInt(1, trxId.intValue());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				trx = createTrxInstance(rs);

			}

			if (closeStmt)
				DBUtil.close(pstmt);

			if (trx == null)
				throw new MCDSException(
						"No such transaction exists in database with trxId - "
								+ trxId, -5);

			if (loadTrxCommission) {
				trx.setPartnerCommissions(loadTrxCommission(conn, trxId,
						historyRecord));
			}

		} catch (SQLException e) {
			_log.log(Level.SEVERE, "trx - " + trxId
					+ "loading failed due to sql exception ", e);
			throw new MCDSException(
					"sql exception during loading of trx with this id -"
							+ trxId, e);
		} finally {
			DBUtil.close(conn);
		}

		return trx;
	}

	private Transaction createTrxInstance(ResultSet rs) throws SQLException {
		Transaction trx = new Transaction();

		trx.setTrxId(Integer.valueOf(rs.getInt("ID")));
		trx.setDelNo(rs.getString("DELNO"));
		trx.setMerchantId(CommonUtil.toInteger(rs.getObject("MERCHANTID")));
		trx.setTrxType(rs.getString("TRXTYPE"));
		trx.setBaseAmt(CommonUtil.toDouble((rs.getObject("BASEAMOUNT"))));
		trx.setAggregatorId(CommonUtil.toInteger(rs.getInt("AGGREGATORID")));
		trx.setCommissionAmt(CommonUtil.toDouble(rs
				.getDouble("COMMISSIONAMOUNT")));
		trx.setSurchargeAmt(CommonUtil.toDouble(rs.getDouble("SURCHARGE")));
		trx.setTotalAmt(CommonUtil.toDouble(rs.getDouble("TOTALAMOUNT")));
		trx.setParentTrxId(CommonUtil.toInteger(rs.getString("PARENTTRXID")));
		trx.setReconStatus(rs.getString("RECONSTATUS"));
		trx.setRemarks(rs.getString("TRXREMARKS"));
		trx.setMerchantAmt(CommonUtil
				.toDouble(rs.getObject("MERCHANTDEBITAMT")));
		trx.setMerchantBalance(CommonUtil.toDouble(rs
				.getObject("MERCHANTREMAININGBAL")));
		trx.setSkuName(rs.getString("SKUNAME"));
		trx.setResponseTs(rs.getTimestamp("TSRESPONSE"));
		trx.setSku(rs.getString("SKU"));
		trx.setTrxStatus(CommonUtil.toTrxStatus(rs.getString("STATUS")));
		trx.setMsisdn(rs.getString("MSISDN"));
		trx.setPhoneMake(rs.getString("PHONEMAKE"));
		trx.setPhoneModel(rs.getString("PHONEMODEL"));
		trx.setResponesCode(rs.getString("RESPONSECODE"));
		trx.setResponseMessage(rs.getString("RESPONSEMESSAGE"));
		trx.setAggregatorPhoneCode(rs.getString("AGGREGATORPHONECODE"));
		trx.setTsCreated(rs.getTimestamp("TSCREATED"));
		trx.setTsUpdated(rs.getTimestamp("TSUPDATED"));
		trx.setRequestTs(rs.getTimestamp("TSREQUEST"));
		trx.setResponseTs(rs.getTimestamp("TSRESPONSE"));
		trx.setCommFormulaId(rs.getInt("COMMFORMULAID"));
		trx.setAggregatorTrxId(rs.getInt("AGGREGATORTRXID"));
		trx.setPaymentMode(CommonUtil
				.toPaymentMode(rs.getString("PAYMANTMODE")));
		trx.setDeliveryMode(CommonUtil.toDeliveryMode(rs
				.getString("DELIVERYMODE")));
		return trx;
	}

	private Map<Integer, Number> loadTrxCommission(Connection conn,
			Integer trxId, boolean historyRecord) throws SQLException {
		Map<Integer, Number> map = new HashMap<Integer, Number>();
		String sql = "SELECT PARTNERID ,COMMVALUE FROM  TRANSACTION_COMMISSION WHERE TRXID = ?";
		if (historyRecord) {
			sql = "SELECT PARTNERID ,COMMVALUE FROM  ( "
					+ "SELECT PARTNERID ,COMMVALUE FROM  TRANSACTION_COMMISSION UNION "
					+ "SELECT PARTNERID ,COMMVALUE FROM  TRX_COMMISSION_ARCHIVE"
					+ ") WHERE TRXID = ?";
		}

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, trxId.intValue());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			int pid = rs.getInt(1);
			double comm = rs.getDouble(2);
			map.put(Integer.valueOf(pid), Double.valueOf(comm));
		}

		DBUtil.close(pstmt);

		return map;
	}

	public void updateTrxOps(Transaction trx) throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		/**
		 * UPDATE TRANSACTION SET AGGREGATORTRXID,RESPONSECODE,RESPONSEMESSAGE,
		 * STATUS,TSRESPONSE WHERE ID
		 */
		String sql = "UPDATE TRANSACTION SET MERCHANTDEBITAMT = ? ,MERCHANTREMAININGBAL = ? "
				+ " ,STATUS = ? ,TRXREMARKS =? , ISREFUND = ? WHERE ID = ?";

		_log.info(" sql -" + sql);
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// make the same amount negative
			// Number tempAmount = trx.getMerchantAmt().doubleValue() -
			// (trx.getMerchantAmt().doubleValue()*2);
			pstmt.setDouble(1, trx.getMerchantAmt().doubleValue());
			pstmt.setDouble(2, trx.getMerchantBalance().doubleValue());
			pstmt.setString(3, trx.getTrxStatus().toDBLiteral());
			pstmt.setString(4, trx.getRemarks());
			pstmt.setInt(5, 1);
			pstmt.setInt(6, trx.getTrxId());

			pstmt.executeUpdate();
			_log.info("trx - " + trx.getTrxId()
					+ " updated successfully in database.");
			DBUtil.close(pstmt);

		} catch (SQLException e) {
			_log.log(Level.SEVERE, "trx - " + trx.getTrxId()
					+ "updation failed due to sql exception ", e);
			throw new MCDSException("transaction failed due to database error.");
		} finally {
			DBUtil.close(conn);
		}
	}

	public void updateTrx(Transaction trx) throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		/**
		 * UPDATE TRANSACTION SET AGGREGATORTRXID,RESPONSECODE,RESPONSEMESSAGE,
		 * STATUS,TSRESPONSE WHERE ID
		 */
		String sql = "UPDATE TRANSACTION SET AGGREGATORTRXID = ? ,RESPONSECODE = ? ,RESPONSEMESSAGE = ? "
				+ " ,STATUS = ? ,TSRESPONSE =? WHERE ID = ?";

		_log.info(" sql -" + sql);
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (trx.getAggregatorTrxId() != null) {
				pstmt.setLong(1, trx.getAggregatorTrxId());
			} else {
				pstmt.setNull(1, Types.NUMERIC);
			}
			if (trx.getResponesCode() != null) {
				pstmt.setString(2, trx.getResponesCode());
				pstmt.setString(3, trx.getResponseMessage());
				pstmt.setTimestamp(5, trx.getResponseTs());
			} else {
				pstmt.setNull(2, Types.VARCHAR);
				pstmt.setNull(3, Types.VARCHAR);
				pstmt.setNull(5, Types.TIMESTAMP);
			}
			pstmt.setString(4, trx.getTrxStatus().toDBLiteral());
			pstmt.setLong(6, trx.getTrxId());

			pstmt.executeUpdate();
			_log.info("trx - " + trx.getTrxId()
					+ " updated successfully in database.");
			DBUtil.close(pstmt);

		} catch (SQLException e) {
			_log.log(Level.SEVERE, "trx - " + trx.getTrxId()
					+ "updation failed due to sql exception ", e);
			throw new MCDSException("transaction failed due to database error.");
		} finally {
			DBUtil.close(conn);
		}
	}

	private void insertCommissions(Connection conn, Integer trxId,
			Map<Integer, Number> trxCommissions) throws SQLException {
		String sql = "INSERT INTO TRANSACTION_COMMISSION ( TRXID , PARTNERID ,COMMVALUE ) "
				+ "VALUES ( ? ,? ,? )";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for (Map.Entry<Integer, Number> pair : trxCommissions.entrySet()) {
			pstmt.setInt(1, trxId.intValue());
			pstmt.setInt(2, pair.getKey().intValue());
			pstmt.setDouble(3, pair.getValue().doubleValue());

			pstmt.addBatch();
		}

		int[] result = pstmt.executeBatch();

		DBUtil.close(pstmt);

		if (_log.isLoggable(Level.INFO))
			_log
					.info(" Trx commission batch executed successfully for transaction - "
							+ trxId + " Result -" + CommonUtil.toString(result));
	}

	/*
	 * private void updateTrx(Connection conn, Map<String, Object> trxDataMap)
	 * throws SQLException, MCDSException { // make a local copy of map here.
	 * Map<String, Object> orderedMap = new LinkedHashMap<String,
	 * Object>(trxDataMap); Integer trxId = (Integer)
	 * trxDataMap.get(TrxAttributes.C_ID); String delNo = (String)
	 * trxDataMap.get(TrxAttributes.C_DELNO); Timestamp dt = new
	 * Timestamp(System.currentTimeMillis());
	 * 
	 * _log.info("trx - " + trxId + " delNo - " + delNo + " is going to update
	 * at " + dt); // remove id as it can't be updated..
	 * orderedMap.remove(TrxAttributes.C_ID);
	 * 
	 * StringBuilder prefix = new StringBuilder("UPDATE TRANSACTION SET ");
	 * StringBuilder postfix = new StringBuilder(" WHERE ID = ?");
	 * 
	 * for (String key : orderedMap.keySet()) prefix.append(key).append(" = ?
	 * ,");
	 * 
	 * prefix.setCharAt(prefix.length() - 1, ' ');
	 * 
	 * String sql = prefix.append(postfix).toString();
	 * 
	 * _log.info(" sql - " + sql); PreparedStatement pstmt =
	 * conn.prepareStatement(sql, new int[1]); int index = 1; for (Map.Entry<String,
	 * Object> pair : orderedMap.entrySet()) { String key = pair.getKey();
	 * Object value = pair.getValue(); _log.info("column =" + key + " " + value + "
	 * type =" + sqlType); if (value == null) pstmt.setNull(index, sqlType);
	 * else { try { pstmt.setObject(index, value, sqlType); } catch (Exception
	 * e) { e.printStackTrace(); } }
	 * 
	 * index++; }
	 * 
	 * pstmt.setInt(index, trxId.intValue());
	 * 
	 * int result = pstmt.executeUpdate(); _log.info("trx updated - " + (result ==
	 * 1));
	 * 
	 * DBUtil.close(pstmt);
	 * 
	 * _log.info("trx - " + trxId + " delNo - " + delNo + " is going to update
	 * at " + dt); }
	 */

	public Integer createTrxId() throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		Integer trxId = null;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT transaction_seq.nextval FROM dual");
			if (rs.next()) {
				trxId = rs.getInt("nextval");
			}
			DBUtil.close(st);
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "create transaction failed.", e);
			throw new MCDSException(
					"sql exception during creating new transaction.", e);
		} finally {
			DBUtil.close(conn);
		}

		return trxId;
	}

	public Integer createTrx(Transaction trx) throws MCDSException {
		Connection conn = _dbAccessMgr.getConnection();
		/**
		 * id,delno,aggregatorid,baseamount,commissionamount,totalamount,status,
		 * merchantdebitamt,msisdn,phonemake,phonemodel,aggregatorphonecode,
		 * sku,skuname,commformulaid,
		 */
		String sql = "INSERT INTO TRANSACTION ( ID ,DELNO ,AGGREGATORID ,BASEAMOUNT  ,COMMISSIONAMOUNT"
				+ " ,TOTALAMOUNT ,STATUS ,MERCHANTDEBITAMT ,COMMFORMULAID ,MSISDN ,PHONEMAKE ,PHONEMODEL"
				+ " ,AGGREGATORPHONECODE ,SKU ,SKUNAME, MERCHANTREMAININGBAL, DELIVERYMODE)"
				+ " VALUES( ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? , ?, ?, ?)";

		_log.info(" sql -" + sql);
		try {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			// set values in PSTMT
			pstmt.setLong(1, trx.getTrxId());
			pstmt.setLong(2, Long.parseLong(trx.getDelNo()));
			pstmt.setLong(3, trx.getAggregatorId());
			pstmt.setDouble(4, trx.getBaseAmt().doubleValue());
			pstmt.setDouble(5, trx.getCommissionAmt().doubleValue());
			pstmt.setDouble(6, trx.getTotalAmt().doubleValue());
			pstmt.setString(7, trx.getTrxStatus().toDBLiteral());
			pstmt.setDouble(8, trx.getMerchantAmt().doubleValue());
			pstmt.setLong(9, trx.getCommFormulaId());
			pstmt.setString(10, trx.getMsisdn());
			pstmt.setString(11, trx.getPhoneMake());
			pstmt.setString(12, trx.getPhoneModel());
			pstmt.setString(13, trx.getAggregatorPhoneCode());
			pstmt.setString(14, trx.getSku());
			pstmt.setString(15, trx.getSkuName());
			if (trx.getMerchantBalance() == null) {
				pstmt.setNull(16, Types.DOUBLE);
			} else {
				pstmt.setDouble(16, trx.getMerchantBalance().doubleValue());
			}
			pstmt.setString(17, trx.getDeliveryMode().toDbLiteral());
			pstmt.executeUpdate();

			_log.info("generated trx Id - " + (trx.getTrxId()));
			// Insert Commissions
			if (trx.getPartnerCommissions() != null)
				insertCommissions(conn, trx.getTrxId(), trx
						.getPartnerCommissions());

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "create transaction failed.", e);

			throw new MCDSException(
					"sql exception during creating new transaction.", e);
		} finally {
			DBUtil.close(conn);
		}

		return trx.getTrxId();
	}

	public List<Transaction> loadTransaction(SearchTrxRequest request)
			throws MCDSException {
		Transaction trx = null;
		List<Transaction> _trxDetailsList = new ArrayList<Transaction>();
		Connection conn = DBAccessManagerFactory.getDBAccessManager()
				.getConnection();
		Boolean archive = request.getIsArchive();
		StringBuilder sql = new StringBuilder(
				"select ID,DELNO,MERCHANTID,TRXTYPE,AGGREGATORID,BASEAMOUNT,COMMISSIONAMOUNT,SURCHARGE"
						+ ",TOTALAMOUNT,STATUS,PARENTTRXID,RECONSTATUS,ISREFUND,TRXREMARKS,TSCREATED,TSUPDATED,"
						+ "MERCHANTDEBITAMT,MERCHANTREMAININGBAL,PAYMANTMODE,MSISDN,PHONEMAKE,PHONEMODEL,"
						+ "AGGREGATORPHONECODE,SKU,SKUNAME,AGGREGATORTRXID,RESPONSECODE,RESPONSEMESSAGE,DELIVERYMODE,COMMFORMULAID,"
						+ "TSREQUEST,TSRESPONSE from ("
						+ "(select ID,DELNO,MERCHANTID,TRXTYPE,AGGREGATORID,BASEAMOUNT,COMMISSIONAMOUNT,SURCHARGE"
						+ ",TOTALAMOUNT,STATUS,PARENTTRXID,RECONSTATUS,ISREFUND,TRXREMARKS,TSCREATED,TSUPDATED,"
						+ "MERCHANTDEBITAMT,MERCHANTREMAININGBAL,PAYMANTMODE,MSISDN,PHONEMAKE,PHONEMODEL,"
						+ "AGGREGATORPHONECODE,SKU,SKUNAME,AGGREGATORTRXID,RESPONSECODE,RESPONSEMESSAGE,DELIVERYMODE,COMMFORMULAID,"
						+ "TSREQUEST,TSRESPONSE from TRANSACTION))");

		if (archive) {
			sql.setCharAt(sql.length() - 1, ' ');
			sql
					.append(" UNION "
							+ " ( SELECT TRXID,DELNO,MERCHANTID,TRXTYPE,AGGREGATORID,BASEAMOUNT,COMMISSIONAMOUNT,SURCHARGE"
							+ ",TOTALAMOUNT,STATUS,PARENTTRXID,RECONSTATUS,ISREFUND,TRXREMARKS,TSCREATED,TSUPDATED,"
							+ "MERCHANTDEBITAMT,MERCHANTREMAININGBAL,PAYMANTMODE,MSISDN,PHONEMAKE,PHONEMODEL,"
							+ "AGGREGATORPHONECODE,SKU,SKUNAME,AGGREGATORTRXID,RESPONSECODE,RESPONSEMESSAGE,DELIVERYMODE,COMMFORMULAID,"
							+ "TSREQUEST,TSRESPONSE from TRANSACTION_ARCHIVE ))");
		}
		try {
			boolean where = false;
			List<Object> params = new ArrayList<Object>();

			if (request.getDelNo() != null) {
				where = true;
				sql.append(" WHERE DELNO = ?");
				params.add(Long.parseLong(request.getDelNo()));

			}
			if (request.getTrxId() != null) {
				sql.append(where ? " AND " : " WHERE ");
				sql.append(" ID = ?");
				params.add(Integer.parseInt(request.getTrxId()));
				where = true;
			}
			if (request.getTrxStatus() != null
					&& request.getTrxStatus().length() > 0) {
				sql.append(where ? " AND " : " WHERE ");
				sql.append(" Status = ?");
				params.add(request.getTrxStatus());
				where = true;
			}
			if (request.getFromDate() != null) {
				sql.append(where ? " AND " : " WHERE ");
				sql
						.append(" to_date( to_char(TSREQUEST , 'ddMMyyyy') ,'ddMmyyyy')");
				sql
						.append(" >= to_date( to_char( ? , 'ddMMyyyy') ,'ddMmyyyy')");
				params.add(new Timestamp(request.getFromDate().getTime()));
				where = true;
			}
			if (request.getToDate() != null) {
				sql.append(where ? " AND " : " WHERE ");
				sql
						.append(" to_date( to_char(TSREQUEST , 'ddMMyyyy') ,'ddMmyyyy')");
				sql
						.append(" <= to_date( to_char( ? , 'ddMMyyyy') ,'ddMmyyyy')");
				params.add(new Timestamp(request.getToDate().getTime()));
				where = true;
			}

			if (request.getReconStatus() != null
					&& request.getReconStatus().length() > 0) {
				sql.append(where ? " AND " : " WHERE ");
				sql.append(" RECONSTATUS = ? ");
				params.add(request.getReconStatus());
				where = true;
			}
			_log.info(" sql - " + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			for (int i = 0; i < params.size(); i++) {
				_log.info(params.get(i).toString());
				pstmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				trx = createTrxInstance(rs);
				_trxDetailsList.add(trx);
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

		return _trxDetailsList;
	}

	public ArrayList<TransactionSummaryInfo> getTransactionSummary(int limit,
			long delNo) throws MCDSException {
		ArrayList<TransactionSummaryInfo> transactionList = new ArrayList<TransactionSummaryInfo>();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;

		try {
			conn = _dbAccessMgr.getConnection();
			String sqlQuery = getTransactionSummarySql( delNo);
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sqlQuery);
			int recordCount = 0;
			
			while (rs.next()) 
			{
				if ( recordCount >= limit )
				{
					break;
				}
				TransactionSummaryInfo transaction = new TransactionSummaryInfo();
				long transactionId = rs.getLong("id");
				long requiredTransactionId = getLastFourDigit(transactionId);
				String date = rs.getString("transationdate");
				String responseCode = rs.getString("responsecode");
				transaction.setTransactionId(requiredTransactionId);
				transaction.setTransactionDate(date);
				transaction.setTransactionResponseCode(responseCode);
				transactionList.add(transaction);
				recordCount++;
			}

			DBUtil.close(stmt);

		} catch (SQLException sqlExp) {
			_log
					.info(" SQL Exception Occured while selecting transaction Detail "
							+ sqlExp.toString());
			throw new MCDSException(sqlExp.toString());
		} catch (MCDSException mcdsExp) {
			_log.info("Exception Occured while selecting transaction Detail "
					+ mcdsExp.toString());
			throw mcdsExp;
		} finally {
			DBUtil.close(conn);
		}

		return transactionList;
	}

	public ArrayList<TransactionSummaryInfo> getTransactionSummary(
			Date fromDate, Date toDate, long delNo) {

		ArrayList<TransactionSummaryInfo> transactionList = new ArrayList<TransactionSummaryInfo>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement preparedStmt = null;

		try {
			conn = _dbAccessMgr.getConnection();
			String sqlQuery = getTransactionSummarySql();
			preparedStmt = conn.prepareStatement(sqlQuery);
			preparedStmt.setDate(1, new java.sql.Date(fromDate.getTime()));
			preparedStmt.setDate(2, new java.sql.Date(toDate.getTime()));
			preparedStmt.setLong(3, delNo);
			rs = preparedStmt.executeQuery();

			while (rs.next()) {
				TransactionSummaryInfo transaction = new TransactionSummaryInfo();
				long transactionId = rs.getLong("id");
				long requiredTransactionId = getLastFourDigit(transactionId);
				String date = rs.getString("transationdate");
				String responseCode = rs.getString("responsecode");
				transaction.setTransactionId(requiredTransactionId);
				transaction.setTransactionDate(date);
				transaction.setTransactionResponseCode(responseCode);
				transactionList.add(transaction);
			}
			DBUtil.close(preparedStmt);
		} catch (SQLException sqlExp) {
			System.out
					.println(" SQL Exception Occured while selecting transaction Detail "
							+ sqlExp.toString());
		} catch (MCDSException mcdsExp) {
			System.out
					.println("Exception Occured while selecting transaction Detail "
							+ mcdsExp.toString());
		} finally {
			DBUtil.close(conn);
		}

		return transactionList;
	}

	private String getTransactionSummarySql() {
		StringBuffer sbuff = new StringBuffer();
		sbuff
				.append("SELECT id, to_char( tsrequest, 'ddMMyy' ) transationdate,  responsecode ");
		sbuff.append(" FROM ");
		sbuff.append("TRANSACTION");
		sbuff
				.append(" WHERE to_date( to_char(TSREQUEST , 'ddMMyyyy') ,'ddMmyyyy')");
		sbuff.append(" >= to_date( to_char( ? , 'ddMMyyyy') ,'ddMmyyyy')");
		sbuff
				.append(" AND to_date( to_char(TSREQUEST , 'ddMMyyyy') ,'ddMmyyyy')");
		sbuff.append(" <= to_date( to_char( ? , 'ddMMyyyy') ,'ddMmyyyy')");
		sbuff.append(" AND DELNO = ?  ORDER BY  tsrequest DESC ");
		return sbuff.toString();

	}

	private long getLastFourDigit(long id) {
		long lastFourDigit = id % 10000;

		return lastFourDigit;
	}

	private String getTransactionSummarySql( long delNo) {
		StringBuffer sbuff = new StringBuffer();

		sbuff
				.append("SELECT id , to_char( tsrequest, 'ddMMyy' ) transationdate,  responsecode ");
		sbuff.append(" FROM ");
		sbuff.append("TRANSACTION");
		sbuff.append(" WHERE " );
		sbuff.append(" DELNO = " + delNo);
		sbuff.append(" ORDER BY  tsrequest DESC ");
		return sbuff.toString();
	}

	public void moveProcessedTransactionToArchive() throws MCDSException {
		String sql = "call TRX_ARCHIVE()";
		Connection conn = _dbAccessMgr.getConnection();
		try {
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.execute();
			_log.finer("Process Completed - ");
			cstmt.close();
		} catch (SQLException e) {
			_log.log(Level.SEVERE,
					"sql error while running the archive process", e);
			throw new MCDSException(
					"database error while running archive process.");
		} finally {
			DBUtil.close(conn);
		}
	}

	public List<Integer> findAllTrxIdsForRecon(Integer aggId, Date fromDate,
			Date toDate) throws MCDSException {
		String sql = "SELECT  ID ,STATUS FROM Transaction Where  AGGREGATORID = ? AND"
				+ " to_date( to_char(BOOKINGDT , 'ddMMyyyy') ,'ddMmyyyy') "
				+ "BETWEEN to_date( to_char(?, 'ddMMyyyy') ,'ddMmyyyy') "
				+ "AND to_date( to_char(? , 'ddMMyyyy') ,'ddMmyyyy')";
		Connection conn = DBAccessManagerFactory.getDBAccessManager()
				.getConnection();
		List<Integer> listOfTrxIds = new ArrayList<Integer>();
		try {
			_log.info(" sql - " + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, aggId.intValue());
			pstmt.setTimestamp(2, new Timestamp(fromDate.getTime()));
			pstmt.setTimestamp(3, new Timestamp(toDate.getTime()));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TrxStatus status = TrxStatus.BUYING_SUBMITTED; // CommonUtil.toTrxStatus(rs.getString("STATUS"));
				if (TrxStatus.BUYING_SUBMITTED.compareTo(status) <= 0) {
					// because we must ignore the trx for recon.
					listOfTrxIds.add(new Integer(rs.getInt("ID")));
				}
			}

			DBUtil.close(pstmt);

			return listOfTrxIds;
		} catch (SQLException e) {
			_log.log(Level.SEVERE,
					"database error while searching transactions", e);
			throw new MCDSException(
					"database error while searching ransactions");
		} finally {
			DBUtil.close(conn);
		}
	}

	public Transaction loadHistoricalTransaction(Integer trxId,
			boolean loadTrxCommission) throws MCDSException {
		String sql = historySQL;
		return loadTransaction(sql, trxId, loadTrxCommission, true);
	}

	private String historySQL = "select ID,DELNO,MERCHANTID,TRXTYPE,AGGREGATORID,BASEAMOUNT,COMMISSIONAMOUNT,SURCHARGE"
			+ ",TOTALAMOUNT,STATUS,PARENTTRXID,RECONSTATUS,ISREFUND,TRXREMARKS,TSCREATED,TSUPDATED,"
			+ "MERCHEANTDEBITAMT,MERCHANTREMAININGBAL,PAYMANTMODE,MSISDN,PHONEMAKE,PHONEMODEL,"
			+ "AGGREGATORPHONECODE,SKU,SKUNAME,AGGREGATORTRXID,RESPONSECODE,RESPONSEMESSAGE,COMMFORMULAID,"
			+ "TREQUEST,TRESPONSE from TRANSACTION) "
			+ " UNION "
			+ " ( SELECT TRXID,DELNO,MERCHANTID,TRXTYPE,AGGREGATORID,BASEAMOUNT,COMMISSIONAMOUNT,SURCHARGE"
			+ ",TOTALAMOUNT,STATUS,PARENTTRXID,RECONSTATUS,ISREFUND,TRXREMARKS,TSCREATED,TSUPDATED,"
			+ "MERCHEANTDEBITAMT,MERCHANTREMAININGBAL,PAYMANTMODE,MSISDN,PHONEMAKE,PHONEMODEL,"
			+ "AGGREGATORPHONECODE,SKU,SKUNAME,AGGREGATORTRXID,RESPONSECODE,RESPONSEMESSAGE,COMMFORMULAID,"
			+ "TREQUEST,TRESPONSE from TRANSACTION_ARCHIVE )) WHERE ID = ?";

	public void prepareHistoryTrxLoader() throws MCDSException {
		Thread key = Thread.currentThread();
		PreparedStatement pstmt = (PreparedStatement) threadCache.get(key);
		try {
			if (pstmt == null) {
				pstmt = _dbAccessMgr.getConnection().prepareStatement(
						historySQL);
				threadCache.put(key, pstmt);
			}
			DBUtil.close(pstmt);
		} catch (Exception e) {
			// ignore it
		}

	}

	public void destroyHistoryTrxLoader() throws MCDSException {
		Thread key = Thread.currentThread();
		PreparedStatement pstmt = (PreparedStatement) threadCache.remove(key);
		try {
			if (pstmt != null) {
				DBUtil.close(pstmt.getConnection());
			}
		} catch (Exception e) {
			// ignore it
		}
	}

	public Map<String, Number> loadTrxCommissionMap(Integer trxId,
			boolean historyRecord) throws MCDSException {
		Map<String, Number> map = new HashMap<String, Number>();
		String sql = "SELECT (SELECT PARTNERNAME FROM PARTNER WHERE ID = PARTNERID) ,COMMVALUE FROM  TRANSACTION_COMMISSION c WHERE TRXID = ?";
		if (historyRecord) {
			sql = "SELECT (SELECT PARTNERNAME FROM PARTNER WHERE ID = PARTNERID) ,COMMVALUE FROM  ( "
					+ "SELECT TRXID, PARTNERID ,COMMVALUE FROM  TRANSACTION_COMMISSION UNION "
					+ "SELECT TRXID, PARTNERID ,COMMVALUE FROM  TRX_COMMISSION_ARCHIVE"
					+ ") WHERE TRXID = ?";
		}
		Connection conn = _dbAccessMgr.getConnection();
		try {

			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, trxId.intValue());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String partner = rs.getString(1);
				double comm = rs.getDouble(2);
				map.put(partner, Double.valueOf(comm));
			}

			DBUtil.close(pstmt);
		} catch (SQLException e) {
			_log.severe(" sql error while loading the trx commission map " + e);
			throw new MCDSException(
					"sql error while loading the trx commission map ");
		} finally {
			DBUtil.close(conn);
		}

		return map;
	}

	/**
	 * This method return SKU Code corressponding to transaction ID
	 * 
	 * @param transactionId
	 * @return
	 * @throws MCDSException
	 */
	public String getSKUCodeByTrxId(Integer transactionId) throws MCDSException {
		String skuCode = null;
		String sql = "SELECT SKU FROM TRANSACTION WHERE ID = ?";
		Connection conn = _dbAccessMgr.getConnection();

		try {
			PreparedStatement prepareStmt = conn.prepareStatement(sql);
			prepareStmt.setInt(1, transactionId);
			ResultSet rs = prepareStmt.executeQuery();

			if (rs.next()) {
				skuCode = rs.getString("SKU");

				if (skuCode == null) {
					_log.info("No SKU Code for Trx Id : " + transactionId);
					throw new MCDSException(
							" Error Occured While Serviceing Your Request [ No SKU Code for Trx Id : "
									+ transactionId + " ]");
				}
			} else {
				_log.info("Invalid Trx Id");
				throw new MCDSException("Invalid Trx Id");
			}

			DBUtil.close(prepareStmt);
		}

		catch (SQLException sqlExp) {
			_log.info("Exception Occured while Accessing SKU Code from DB");
			throw new MCDSException(
					"Error Occured While Serviceing Your Request [ Exception Occured while Accessing SKU Code from DB ]");
		} finally {
			DBUtil.close(conn);
		}
		return skuCode;
	}

	/**
	 * 
	 * @param transactionId
	 * @return
	 * @throws MCDSException
	 */
	public Integer getAggIdByTrxId(int transactionId) throws MCDSException {
		Integer aggId = null;
		String sql = "SELECT AGGREGATORID FROM TRANSACTION WHERE ID = ?";
		Connection conn = _dbAccessMgr.getConnection();

		try {
			PreparedStatement prepareStmt = conn.prepareStatement(sql);
			prepareStmt.setInt(1, transactionId);
			ResultSet rs = prepareStmt.executeQuery();

			if (rs.next()) {
				aggId = rs.getInt("AGGREGATORID");

			} else {
				_log.info("Invalid Trx Id");
				throw new MCDSException("Invalid Trx Id");
			}

			DBUtil.close(prepareStmt);
		}

		catch (SQLException sqlExp) {
			_log.info("Exception Occured while Accessing SKU Code from DB");
			throw new MCDSException(
					"Exception Occured while Accessing SKU Code from DB");
		} finally {
			DBUtil.close(conn);
		}
		return aggId;
	}

}
