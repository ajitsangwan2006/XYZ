package com.gisil.mcds.web.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.transaction.Transaction;

public class SaveAsExcelReport {
	public static final String DEFAULT_FILE_NAME = "Report.xls";

	public static Object exportData(List trxList, String fileName)
			throws MCDSException {
		try {
			HSSFWorkbook report = new HSSFWorkbook();
			HSSFSheet sheet = report.createSheet("Report");
			HSSFRow row = sheet.createRow(0);
			HSSFCellStyle style = report.createCellStyle();
			HSSFFont font = report.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
			writeHeader(row, style, getColumns().toArray(new String[0]));
			int lastRowNo = sheet.getLastRowNum();
			writeData(sheet, trxList, lastRowNo);
			return report;
		} catch (Exception exp) {
			throw new MCDSException(
					"Exception occured while Exporting Data to .xls file"
							+ exp.toString());
		}
	}

	private static void writeData(HSSFSheet sheet, List trxList, int lastRowNo) {
		final HSSFRichTextString NULL = new HSSFRichTextString("null");

		Iterator iterator = trxList.iterator();
		lastRowNo = lastRowNo + 1;
		while (iterator.hasNext()) {
			Transaction trx = (Transaction) iterator.next();
			HSSFRow rowNext = sheet.createRow(lastRowNo);
			short colNumber = 0;

			if (trx.getTrxId() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(trx.getTrxId());
			colNumber++;

			if (trx.getDelNo() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getDelNo()));
			colNumber++;

			if (trx.getMerchantId() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(trx.getMerchantId());
			colNumber++;

			if (trx.getTrxType() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getTrxType()));
			colNumber++;

			if (trx.getAggregatorId() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						trx.getAggregatorId());
			colNumber++;

			if (trx.getBaseAmt() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						((trx.getBaseAmt() != null) ? trx.getBaseAmt()
								.doubleValue() : 0.0));
			colNumber++;

			if (trx.getCommissionAmt() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						((trx.getCommissionAmt() != null) ? trx
								.getCommissionAmt().doubleValue() : 0.0));
			colNumber++;
			
			if (trx.getTotalAmt() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						((trx.getTotalAmt() != null) ? trx
								.getTotalAmt().doubleValue() : 0.0));
			colNumber++;


			if (trx.getTrxStatus() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber)
						.setCellValue(
								new HSSFRichTextString(trx.getTrxStatus()
										.toDBLiteral()));
			colNumber++;

			if (trx.getParentTrxId() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber)
						.setCellValue(trx.getParentTrxId());
			colNumber++;

			if (trx.getReconStatus() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getReconStatus()));
			colNumber++;

			rowNext.createCell(colNumber).setCellValue(trx.isRefunded());
			colNumber++;

			if (trx.getAggregatorPhoneCode() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getAggregatorPhoneCode()));
			colNumber++;

			if (trx.getMsisdn() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getMsisdn()));
			colNumber++;

			if (trx.getPhoneMake() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getPhoneMake()));
			colNumber++;

			if (trx.getPhoneModel() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getPhoneModel()));
			colNumber++;

			if (trx.getRemarks() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getRemarks()));
			colNumber++;

			if (trx.getResponesCode() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getResponesCode()));
			colNumber++;

			if (trx.getResponseMessage() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getResponseMessage()));
			colNumber++;

			if (trx.getSku() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getSku()));
			colNumber++;

			if (trx.getSkuName() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getSkuName()));
			colNumber++;

			if (trx.getAggregatorTrxId() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						trx.getAggregatorTrxId());
			colNumber++;

			if (trx.getCommFormulaId() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						trx.getCommFormulaId());
			colNumber++;

			if (trx.getDeliveryMode() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getDeliveryMode()
								.toDbLiteral()));
			colNumber++;

			if (trx.getMerchantAmt() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						((trx.getMerchantAmt() != null) ? trx.getMerchantAmt()
								.doubleValue() : 0.0));
			colNumber++;

			if (trx.getMerchantBalance() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						((trx.getMerchantBalance() != null) ? trx
								.getMerchantBalance().doubleValue() : 0.0));
			colNumber++;

			if (trx.getPaymentMode() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						new HSSFRichTextString(trx.getPaymentMode()
								.toDbLiteral()));
			colNumber++;

			if (trx.getRequestTs() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue( formatDate(	new Date(trx.getRequestTs().getTime())));
			colNumber++;

			if (trx.getResponseTs() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue( formatDate( new Date(trx.getResponseTs().getTime())) );
			colNumber++;

			if (trx.getTsCreated() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(formatDate(	new Date(trx.getTsCreated().getTime())));
			colNumber++;

			if (trx.getTsUpdated() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(formatDate(new Date(trx.getTsUpdated().getTime())) );
			colNumber++;

			if (trx.getSurchargeAmt() == null)
				rowNext.createCell(colNumber).setCellValue(NULL);
			else
				rowNext.createCell(colNumber).setCellValue(
						((trx.getSurchargeAmt() != null) ? trx
								.getSurchargeAmt().doubleValue() : 0.0));
			colNumber++;

			lastRowNo++;
		}

	}

	private static List<String> getColumns() {

		ArrayList<String> al = new ArrayList<String>();
		al.add("ID");
		al.add("DELNO");
		al.add("MERCHANT ID");
		al.add("TRANSACTION TYPE");
		al.add("AGGREGATOR ID");
		al.add("BASE AMOUNT");
		al.add("COMMISSION AMOUNT");
		al.add("TOTAL AMOUNT");
		al.add("STATUS");
		al.add("PARENT TRANSACTION ID");
		al.add("RECONCILIATION STATUS");
		al.add("ISREFUNDED");
		al.add("AGGREGATOR PHONE CODE");
		al.add("MSISDN");
		al.add("PHONEMAKE");
		al.add("PHONEMODEL");
		al.add("TRXREMARKS");
		al.add("RESPONSE CODE");
		al.add("RESPONSE MESSAGE");
		al.add("SKU");
		al.add("SKUNAME");
		al.add("AGGREGATOR TRX ID");
		al.add("COMM FORMULA ID");
		al.add("DELIVERY MODE");
		al.add("MERCHANT AMT");
		al.add("MERCHANT BALANCE");
		al.add("PAYMENT MODE");
		al.add("TSREQUEST");
		al.add("TSRESPONSE");
		al.add("TSCREATED");
		al.add("TSUPDATED");
		al.add("SURCHARGE AMOUNT");
		return al;
	}

	private static void writeHeader(HSSFRow row, HSSFCellStyle style,
			String[] columns) {
		for (short i = 0; i < columns.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(new HSSFRichTextString(columns[i]));
		}
	}
	
	  private static HSSFRichTextString formatDate(Date date) {
	        try {
	            return new HSSFRichTextString((date == null) ? "" : new SimpleDateFormat("dd/MM/yyyy").format(date));
	        } catch (Exception e) 
	        {
	            return new HSSFRichTextString(date == null ? "" : date.toString());
	        }
	    }

}
