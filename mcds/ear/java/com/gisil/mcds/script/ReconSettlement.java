package com.gisil.mcds.script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.base.MCDSLauncher;
import com.gisil.mcds.config.ConfigurationManagerImpl;
import com.gisil.mcds.config.IConfigurationManager;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.util.CommonUtil;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.util.EMailAlert;

public class ReconSettlement {

	private MCDSLauncher m_launcher = null;

	private static Logger _log;

	private StringBuffer _message = new StringBuffer();
	
	private static String infileName;
	
	private static String logfileName;

	private void init() {

		_log = Logger.getLogger("MCDS Logger");
		_log.info("Initializing mcds .......");

		try {

			m_launcher = MCDSLauncher.getLauncher();
			m_launcher.launch("./scripts/mcds.config");

			_log.info("MCDS Initialization Complete ....");
			_message.append('\n').append("MCDS Initialization Complete ....");

		} catch (Throwable t) {
			t.printStackTrace();
			_log.severe("Exception occured while initialzing MCDS .....");
			_message.append('\n').append(
					"Exception occured while initialzing MCDS .....");
		}

	}

	private Transaction createTrxInstance(ResultSet rs) throws SQLException {
		Transaction trx = new Transaction();
		_log.info("Creating Trx Instance");
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
	}// End Of create Trx Instance//

	public void updateRecord(Transaction trx, String status) {

		_log.info("Updating ReconStatus");
		Connection con = null;

		IConfigurationManager mgr = ConfigurationManagerImpl
				.getConfigurationManager();
		try {

			// Statement st = null;//con.createStatement();

			IDBAccessManager dbManager = DBAccessManagerFactory
					.getDBAccessManager();

			con = dbManager.getConnection();

			// st = con.createStatement();
			if (trx.getTrxStatus().toString().equalsIgnoreCase(status)) {
				// st.executeUpdate("UPDATE Transaction set
				// reconstatus='true'");

				String sql = "UPDATE Transaction set reconstatus='true' where ID = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, trx.getTrxId());
				pstmt.executeUpdate();
				_message
						.append('\n')
						.append(
								"UPDATE Transaction set reconstatus='true' where status of trx and csv file match ");
			} else {
				// st.executeUpdate("UPDATE Transaction set
				// reconstatus='false'");

				String sql = "UPDATE Transaction set reconstatus='false' where ID = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, trx.getTrxId());
				pstmt.executeUpdate();
				_message
						.append('\n')
						.append(
								"UPDATE Transaction set reconstatus='false' where status of trx and csv file do not match ");
			}

		} catch (Throwable t) {

			t.printStackTrace();
			_log.severe("Exception occured while processing .....");

		} finally {
			DBUtil.close(con);
		}

	}// End OF update record//

	public Transaction getRecord(Integer trxId, String skuCode, Double cost,
			Integer merchantId, String responseCode) {
		String sql = null;
		if (responseCode.equalsIgnoreCase("null")) {
			sql = "select ID,DELNO,MERCHANTID,TRXTYPE,AGGREGATORID,BASEAMOUNT,COMMISSIONAMOUNT,SURCHARGE"
					+ ",TOTALAMOUNT,STATUS,PARENTTRXID,RECONSTATUS,ISREFUND,TRXREMARKS,TSCREATED,TSUPDATED,"
					+ "MERCHANTDEBITAMT,MERCHANTREMAININGBAL,PAYMANTMODE,MSISDN,PHONEMAKE,PHONEMODEL,"
					+ "AGGREGATORPHONECODE,SKU,SKUNAME,AGGREGATORTRXID,RESPONSECODE,RESPONSEMESSAGE,DELIVERYMODE,"
					+ "COMMFORMULAID,TSREQUEST,TSRESPONSE from TRANSACTION WHERE ID = ? AND SKU = ? AND TOTALAMOUNT=?";
			// and SKU=?and TOTALAMOUNT=?
		} else {
			sql = "select ID,DELNO,MERCHANTID,TRXTYPE,AGGREGATORID,BASEAMOUNT,COMMISSIONAMOUNT,SURCHARGE"
					+ ",TOTALAMOUNT,STATUS,PARENTTRXID,RECONSTATUS,ISREFUND,TRXREMARKS,TSCREATED,TSUPDATED,"
					+ "MERCHANTDEBITAMT,MERCHANTREMAININGBAL,PAYMANTMODE,MSISDN,PHONEMAKE,PHONEMODEL,"
					+ "AGGREGATORPHONECODE,SKU,SKUNAME,AGGREGATORTRXID,RESPONSECODE,RESPONSEMESSAGE,DELIVERYMODE,"
					+ "COMMFORMULAID,TSREQUEST,TSRESPONSE from TRANSACTION WHERE ID = ? AND SKU = ? AND TOTALAMOUNT=? AND MERCHANTID=?";
			// and SKU=?and TOTALAMOUNT=?and MERCHANTID=?
		}


		Connection con = null;
		Transaction trx = null;
		// _log.info("Method Executed For Loadding Successful Trx");
		IConfigurationManager mgr = ConfigurationManagerImpl
				.getConfigurationManager();
		try {

			PreparedStatement pstmt = null;
			boolean closeStmt = false;

			IDBAccessManager dbManager = DBAccessManagerFactory
					.getDBAccessManager();

			con = dbManager.getConnection();

			pstmt = con.prepareStatement(sql);
			if (responseCode.equalsIgnoreCase("null")) {
				pstmt.setInt(1, trxId.intValue());
				pstmt.setString(2, skuCode);
				pstmt.setDouble(3, cost.doubleValue());
			} else {
				pstmt.setInt(1, trxId.intValue());
				pstmt.setString(2, skuCode);
				pstmt.setDouble(3, cost.doubleValue());
				pstmt.setInt(4, merchantId.intValue());
			}
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				trx = createTrxInstance(rs);
				_message
						.append('\n')
						.append(
								"Created Trx Instance for Trxs which were  Successful.merchantId was generated ");

			}
			// _log.info("Found transaction...");
			if (trx == null)
				_log.info("No Such Trx Found");

		} catch (Throwable t) {

			t.printStackTrace();
			_log.severe("Exception occured while processing .....");
			_message.append('\n').append(
					"Exception occured while processing .....");

		} finally {
			DBUtil.close(con);
		}

		return trx;

	}// End Of getRecord Method//

	public static void checkData(String trxId, String merchantId,
			String skuCode, String cost, String status) throws Exception {
		if ((trxId == null || trxId.equals(""))
				|| (merchantId == null || merchantId.equals(""))
				|| (skuCode == null || skuCode.equals(""))
				|| (cost == null || cost.equals(""))
				|| (status == null || status.equals("")))
			// throw new DataNotProperException("Corrupted Data");
			throw new Exception("Corrupted Data");

	}// End of check data function//

	private void destroy() throws MCDSException {
		EMailAlert.sendReconProcessReport("MCDS Reconcilation Process Report", _message.toString());
		m_launcher.shutdown();
	}// End OF Destroy method//

	protected void readData() {
		try {
			String logMsg;

			_log.info("Reading Data From CSV File");
			String line = null;
			ReconSettlement ob = new ReconSettlement();
			BufferedReader br = new BufferedReader(new FileReader(infileName));
			File logFile = new File(logfileName);
			logFile.createNewFile();      
			FileOutputStream fop=new FileOutputStream(logFile);

			while ((line = br.readLine()) != null) {
				int i = line.lastIndexOf(",");
				String responseCode = line.substring(i + 1);
				int fst = line.indexOf(",");
				String trxId = line.substring(0, line.indexOf(","));
				int second = line.indexOf(",", fst + 1);
				String merchantId = line.substring(fst + 1, second);
				int third = line.indexOf(",", second + 1);
				String skuCode = line.substring(second + 1, third);
				int fourth = line.indexOf(",", third + 1);
				String cost = line.substring(third + 1, fourth);
				int fifth = line.indexOf(",", fourth + 1);
				String status = line.substring(fourth + 1, fifth);
				checkData(trxId, merchantId, skuCode, cost, status);
				_message.append('\n').append(
						"Retreived data from csv file and called checkData fn");
				logMsg = "\nRetreived data from csv file and called checkData fn";
				if (responseCode.equalsIgnoreCase("null"))// Transaction
				// Unsuccessful or failed//
				{

					_log.info("Loading Failed Trx");
					Transaction trx = ob.getRecord(Integer.valueOf(trxId),
							skuCode, Double.valueOf(cost), Integer
									.valueOf(merchantId), responseCode);
					
					if (trx.getReconStatus() != null) {
						_log.info("Recon Status Is Already Settled");// Write
																		// Log
						// Information//

						_message.append('\n').append(" Recon Status Is Already Settled");
						logMsg = logMsg + "\n Recon Status Is Already Settled";
					} else

						ob.updateRecord(trx, status);

				} else// Trx successful//
				{

					_log.info("Loading Successful Trx");
					Transaction trx = ob.getRecord(Integer.valueOf(trxId),
							skuCode, Double.valueOf(cost), Integer
									.valueOf(merchantId), responseCode);
					if (trx.getReconStatus() != null) {
						_log.info("Recon Status Is Already Settled");// Write
																		// Log
																		// Infn//
						_message.append('\n').append(
								"Recon Status Is Already Settled");
						logMsg = logMsg + "\n Recon Status Is Already Settled";

					} else
						ob.updateRecord(trx, status);

				}
				fop.write(logMsg.getBytes());
				fop.flush();
		        fop.close();


			}// End Of While//
		} catch (Exception e) {
			// e.printStackTrace();
			_log.info(e.getMessage());
		}

	}// End Of readData function//

	public static void main(String[] args) {
		
		//Variables
        Date date = new Date ();
        date.setDate(-1);
        SimpleDateFormat df = new SimpleDateFormat(MCDSConfig.INPUT_RECON_DATE_FORMAT);
        String dt = df.format(date);
        infileName = MCDSConfig.INPUT_RECON_FILE_NAME.replaceFirst(MCDSConfig.INPUT_RECON_DATE_FORMAT, dt);
        logfileName = MCDSConfig.INPUT_RECON_LOG_FILE_NAME.replaceFirst(MCDSConfig.INPUT_RECON_DATE_FORMAT, dt);

		ReconSettlement ob = new ReconSettlement();
		ob.init();
		ob.readData();
		// Close Connection and all
		try {
			ob.destroy();
		} catch (Exception e) {
			_log.info("Failed to ShutDown all resources");
			e.printStackTrace();
		}

	}// End OF main//
}// End Of Class//
