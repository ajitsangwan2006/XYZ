<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiSearchTrxResultController"%>
<%@ page import="com.gisil.mcds.web.servlet.ui.SaveToExcelFileServlet"%>
<%@ page import="com.gisil.mcds.transaction.Transaction"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.gisil.mcds.web.util.Utils"%>

<%SuiSearchTrxResultController lController= new SuiSearchTrxResultController(request, response);%>

<HTML>
<HEAD>
<TITLE>SUI Search Results</TITLE>
<link href="indepay.css" rel="stylesheet" type="text/css">
</HEAD>
<script language="JavaScript" type="text/javascript">
function backBtn()
{
document.forBack.action="<%=response.encodeURL( lController.getQueryString())%>";
document.forBack.submit();
return true;
}
</script>
<body align="center">
<h3 align="center"><u>Transaction Search Result</u></h3>
<FORM name=saveToExcel method=post action="<%=response.encodeURL( SaveToExcelFileServlet.SERVLET_PATH +"?filename=Report.xls&mimetype=application/vnd.ms-excel&&bid=true")%>">
<center><INPUT type="button" value="Back" onClick="backBtn()">
<INPUT type="submit" value="Save To Excel" <%=lController.showExportToExcel() %>></center>
</FORM>
<table width="100%" border=1 align="center" cellpadding="0" cellspacing="0">
	<tr bgcolor="#d3d3af">
		<th>Transaction ID</th>
		<th>DELNO</th>
		<th>MERCHANT ID</th>
		<th>TRXTYPE</th>
		<th>AGGREGATOR ID</th>
		<th>BASE AMOUNT</th>
		<th>COMMISSION AMOUNT</th>
		<th>SURCHARGE</th>
		<th>TOTALAMOUNT</th>
		<th>STATUS</th>
		<th>PARENTTRX ID</th>
		<th>RECON STATUS</th>
		<th>ISREFUND</th>
		<th>TRXREMARKS</th>
		<th>TSCREATED</th>
		<th>TSUPDATED</th>
		<th>MERCHEANT DEBIT AMT</th>
		<th>MERCHANT REMAINING BAL</th>
		<th>MSISDN</th>
		<th>PHONEMAKE</th>
		<th>PHONEMODEL</th>
		<th>AGGREGATOR PHONE CODE</th>
		<th>SKU</th>
		<th>SKUNAME</th>
		<th>PAYMANTMODE</th>
		<th>AGGREGATOR TRX ID</th>
		<th>RESPONSE CODE</th>
		<th>RESPONSE MESSAGE</th>
		<th>DELIVERY MODE</th>
		<th>COMM FORMULA ID</th>
		<th>TSREQUEST</th>
		<th>TSRESPONSE</th>
	</tr>
	
	<%if (lController.gettrxList() == null || lController.gettrxList().isEmpty()) {	%>
	
	<tr>
		<td><strong> No Record Found !!</strong></td>
	</tr>
	<%} else {
					Iterator listIterator = lController.gettrxList().iterator();
						while (listIterator.hasNext()) {
							Transaction transaction = (Transaction) listIterator
									.next();

	%>
	<tr height="25">

		<td><%=(( transaction.getTrxId() != null ) ? transaction.getTrxId() : "") %></td>
		<td><%=(( transaction.getDelNo() != null ) ? transaction.getDelNo() : "")%></td>
		<td><%=(( transaction.getMerchantId() != null )? transaction.getMerchantId() : "")%></td>
		<td><%=(( transaction.getTrxType() != null ) ? transaction.getTrxType() : "") %></td>
		<td><%=(( transaction.getAggregatorId() != null ) ? transaction.getAggregatorId() : "") %></td>
		<td><%=(transaction.getBaseAmt() != null  ? Utils.decimalFormat(transaction.getBaseAmt().doubleValue()) : "")%></td>
		<td><%=(transaction.getCommissionAmt() != null  ? Utils.decimalFormat(transaction.getCommissionAmt().doubleValue()) : "")%></td>
		<td><%=(transaction.getSurchargeAmt() != null  ? Utils.decimalFormat(transaction.getSurchargeAmt().doubleValue()) : "")%></td>
		<td><%=(transaction.getTotalAmt() != null  ? Utils.decimalFormat(transaction.getTotalAmt().doubleValue()) : "")%></td>
		<td><%=(( transaction.getTrxStatus() != null ) ? transaction.getTrxStatus() : "")%></td>
		<td><%=(( transaction.getParentTrxId()!= null ) ? transaction.getParentTrxId() :"")%></td>
		<td><%=(( transaction.getReconStatus() != null ) ? transaction.getReconStatus() :"")%></td>
		<td><%=transaction.isRefunded()%></td>
		<td><%=(( transaction.getRemarks() != null ) ? transaction.getRemarks() : "")%></td>
		<td><%=(( transaction.getTsCreated() != null ) ? transaction.getTsCreated() : "")%></td>
		<td><%=(( transaction.getTsUpdated() != null ) ? transaction.getTsUpdated() : "")%></td>
		<td><%=(transaction.getMerchantAmt() != null  ? Utils.decimalFormat(transaction.getMerchantAmt().doubleValue()) : "")%></td>
		<td><%=(transaction.getMerchantBalance() != null  ? Utils.decimalFormat(transaction.getMerchantBalance().doubleValue()) : "")%></td>
		<td><%=(( transaction.getMsisdn() != null ) ? transaction.getMsisdn() : "")%></td>
		<td><%=(( transaction.getPhoneMake() != null ) ? transaction.getPhoneMake() : "")%></td>
		<td><%=(( transaction.getPhoneModel() != null) ? transaction.getPhoneModel() : "")%></td>
		<td><%=(( transaction.getAggregatorPhoneCode() != null ) ? transaction.getAggregatorPhoneCode() : "") %></td>
		<td><%=(( transaction.getSku() != null ) ? transaction.getSku() : "")%></td>
		<td><%=(( transaction.getSkuName() != null ) ? transaction.getSkuName() : "") %></td>
		<td><%=(( transaction.getPaymentMode() != null ) ? transaction.getPaymentMode() : "") %></td>
		<td><%=(( transaction.getAggregatorTrxId() != null ) ? transaction.getAggregatorTrxId() :"")%></td>
		<td><%=(( transaction.getResponesCode() != null ) ? transaction.getResponesCode() : "")%></td>
		<td><%=(( transaction.getResponseMessage() != null ) ? transaction.getResponseMessage() : "")%></td>
		<td><%=(( transaction.getDeliveryMode().toDbLiteral() != null ) ? transaction.getDeliveryMode().toDbLiteral() : "") %></td>
		<td><%=(( transaction.getCommFormulaId() != null) ? transaction.getCommFormulaId() : "")%></td>
		<td><%=(( transaction.getRequestTs() != null ) ? transaction.getRequestTs() : "")%></td>
		<td><%=(( transaction.getResponseTs() != null) ? transaction.getResponseTs() : "")%></td>
	</tr>
	<%}}%>
</table>
<form name=saveToExcel1 method=post action="<%=response.encodeURL( SaveToExcelFileServlet.SERVLET_PATH +"?filename=Report.xls&mimetype=application/vnd.ms-excel&&bid=true")%>">
<center><INPUT type="button" value="Back" onClick="backBtn()"> <INPUT type="submit" value="Save To Excel" <%=lController.showExportToExcel()%> >
</center>
</form>

<form name="forBack" method="post">
</form>
</BODY>
</HTML>

