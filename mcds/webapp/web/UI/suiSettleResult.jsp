<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="com.gisil.mcds.web.util.Utils" %>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiSettleResultController" %>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiSettleTransController" %>
<% SuiSettleResultController lController = new SuiSettleResultController(request, response);%>

<HTML>
<HEAD>
<TITLE>SUI</TITLE>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<script language="JavaScript" type="text/javascript">
function show(){	
  
    document.getElementById('hideShow').style.visibility = "visible";
   
}
function hide(){
	
    document.getElementById('hideShow').style.visibility = "hidden";
  
}
function trimAll( strValue )
 	{

	 	var objRegExp = /^(\s*)$/;
	    
	    if(objRegExp.test(strValue)) {
	       strValue = strValue.replace(objRegExp, '');
	       if( strValue.length == 0)
	          return strValue;
	    }
	   
	   objRegExp = /^(\s*)([\W\w]*)(\b\s*$)/;
	   if(objRegExp.test(strValue)) {
	       //remove leading and trailing whitespace characters 
	       strValue = strValue.replace(objRegExp, '$2');
	    }
	  return strValue;
	}
	function validateNotEmpty( strValue )
	{
	   var strTemp = strValue;	 
	   if(strTemp != '' && strTemp.length > 0){    
	   strTemp = trimAll(strTemp); }
	   if(strTemp != '' && strTemp.length > 0){ 
	     return true;
	   }
	   return false;
	}
	function submit_data()
	{
	  
	  
	  if(!validateNotEmpty(trxstsres.status.value))
   		{
	       alert("Please choose the new status of transaction."); 
	       trxstsres.statusTrans.value="";
	       trxstsres.statusTrans.focus();
	       
	       return false;
   		} 
   		else
     return true;
  }
  
function getData()
   		{

   		if(submit_data())
   		{
   		var r= confirm("Are you sure for this action?");
		if(r )
   		  {
        trxstsres.action="SettleFinalTrx";
   		trxstsres.submit();
   		}
   		}
   		
   		
   		}
   		
   		function back()
   		{
   		Back.action="<%=SuiSettleTransController.PAGE_PATH%>";
   		Back.submit();
   		}
</script>
<link href="indepay.css" rel="stylesheet" type="text/css">
<BODY>
<FORM name="trxstsres" method="post">
<TABLE width="500" hcellSpacing=0 cellPadding=0 align=center>
	<tr>
		<th colspan="2" height="50"><u><strong>TRANSACTION DETAIL</strong></u></th>
	</tr>
	<INPUT name="trxid" type="hidden" value="<%= request.getParameter("trxid")%>"/>	

	<TR>
		<TD height="25">ID :</TD>
		<TD height="25"><INPUT size="30" name="id" disabled value="<%= lController.getTrx().getTrxId()%>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Del Number :</TD>
		<TD height="25"><INPUT size="30" name=delno disabled value="<%= ((lController.getTrx().getDelNo() != null)?lController.getTrx().getDelNo():"")%>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Merchant Id :</TD>
		<TD height="25"><INPUT size="30" name=merchantid disabled value="<%= ((lController.getTrx().getMerchantId() != null)?lController.getTrx().getMerchantId():"")%>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Transaction Type :</TD>
		<TD height="25"><INPUT size="30" name=trxtype disabled value="<%= ((lController.getTrx().getTrxType() != null)?lController.getTrx().getTrxType():"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Aggregator Id :</TD>
		<TD height="25"><INPUT size="30" name=aggid disabled value="<%= ((lController.getTrx().getAggregatorId() != null)?lController.getTrx().getAggregatorId():"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Base Amount :</TD>
		<TD height="25"><INPUT size="30" name=baseamt disabled value="<%= ((lController.getTrx().getBaseAmt() != null)?Utils.decimalFormat(Double.parseDouble(lController.getTrx().getBaseAmt().toString())):"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Commission Amount :</TD>
		<TD height="25"><INPUT size="30" name=commamt disabled value="<%= ((lController.getTrx().getCommissionAmt() != null)?Utils.decimalFormat(Double.parseDouble(lController.getTrx().getCommissionAmt().toString())):"") %> "></TD>
	</TR>
	
	<TR>
		<TD height="25">Total Amount :</TD>
		<TD height="25"><INPUT size="30" name=totamt disabled value="<%= ((lController.getTrx().getTotalAmt() != null)?Utils.decimalFormat(Double.parseDouble(lController.getTrx().getTotalAmt().toString())):"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Merchant Debit Amount :</TD>
		<TD height="25"><INPUT size="30" name=mnchntdebitamt disabled
			value="<%= ((lController.getTrx().getMerchantAmt() != null)?Utils.decimalFormat(Double.parseDouble(lController.getTrx().getMerchantAmt().toString())):"")%>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Merchant Remaining Bal :</TD>
		<TD height="25"><INPUT size="30" name=mchntrembal disabled value="<%= ((lController.getTrx().getMerchantBalance() != null)?Utils.decimalFormat(Double.parseDouble(lController.getTrx().getMerchantBalance().toString())):"")%>"></TD>
	</TR>

	<TR>
		<TD height="25">Surcharge Amount :</TD>
		<TD height="25"><INPUT size="30" name=surchargeamt disabled value="<%= ((lController.getTrx().getSurchargeAmt() != null)?Utils.decimalFormat(Double.parseDouble(lController.getTrx().getSurchargeAmt().toString())):"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Payment Mode :</TD>
		<TD height="25"><INPUT size="30" name=paymode disabled value="<%= ((lController.getTrx().getPaymentMode() != null)?lController.getTrx().getPaymentMode():"")%>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Trx Status :</TD>
		<TD height="25"><INPUT size="30" name=trxstatus disabled value="<%= ((lController.getTrx().getTrxStatus() != null)?lController.getTrx().getTrxStatus():"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Status :</TD>
		<TD height="25">			
			<SELECT name=status STYLE="width: 160px" <%=lController.getEditMode() %>>		
			<OPTION selected>Select</OPTION>
			<OPTION value="settle">Settle</OPTION>
			<OPTION value="revert">Revert</OPTION>
		</SELECT></TD>
	</TR>
	
	<TR>
		<TD height="25">Description :</TD>
		<TD height="25"><textarea name="description" rows="2" cols="28" <%=lController.getEditMode() %>><%=lController.getTrx().getRemarks()== null?"": lController.getTrx().getRemarks() %></textarea></TD>
	</TR>
	
	<TR>
		<TD height="25">Is Refunded :</TD>
		<TD height="25"><INPUT size="30" name=isrefunded disabled value="<%= lController.getTrx().isRefunded() %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Parent Transaction Id :</TD>
		<TD height="25"><INPUT size="30" name=prttrxid disabled value="<%= ((lController.getTrx().getParentTrxId() != null)?lController.getTrx().getParentTrxId():"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Reconciliation Status :</TD>
		<TD height="25"><INPUT size="30" name=recstx disabled value="<%= ((lController.getTrx().getReconStatus() != null)?lController.getTrx().getReconStatus():"")%>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Mobile No. :</TD>
		<TD height="25"><INPUT size="30" name=mobno disabled value="<%= ((lController.getTrx().getMsisdn() != null)?lController.getTrx().getMsisdn():"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Phone Make :</TD>
		<TD height="25"><INPUT size="30" name=phonemake disabled value="<%= ((lController.getTrx().getPhoneMake() != null)?lController.getTrx().getPhoneMake():"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Phone Model :</TD>
		<TD height="25"><INPUT size="30" name=phonemodel disabled value="<%= ((lController.getTrx().getPhoneModel() != null)?lController.getTrx().getPhoneModel():"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">SKU :</TD>
		<TD height="25"><INPUT size="30" name=sku disabled value="<%= ((lController.getTrx().getSku() != null)?lController.getTrx().getSku():"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">SKU Name :</TD>
		<TD height="25"><INPUT size="30" name=skuname disabled value="<%= ((lController.getTrx().getSkuName() != null)?lController.getTrx().getSkuName():"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Aggregator Phone Code :</TD>
		<TD height="25"><INPUT size="30" name=aggphonecode disabled value="<%= ((lController.getTrx().getAggregatorPhoneCode() != null)?lController.getTrx().getAggregatorPhoneCode():"") %>"></TD>
	</TR>
	
	<TR>
		<TD height="25">Aggregator Trx Id :</TD>
		<TD height="25"><INPUT size="30" name=aggtrxid disabled value="<%= ((lController.getTrx().getAggregatorTrxId() != null)?lController.getTrx().getAggregatorTrxId():"") %>"></TD>
	</TR>
	
	<TR>

		<TD height="25">Response Code :</TD>
		<TD height="25"><INPUT size="30" name=rescode disabled value="<%= ((lController.getTrx().getResponesCode() != null)?lController.getTrx().getResponesCode():"")%>"></TD>
	</TR>

	<TR>
		<TD height="25">Response message :</TD>
		<TD height="25"><INPUT size="30" name=resmsg disabled value="<%= ((lController.getTrx().getResponseMessage() != null)?lController.getTrx().getResponseMessage():"")%>"></TD>
	</TR>

	<TR>
		<TD HEIGHT="25">Commission Formula Id :</TD>
		<TD height="25" valign="bottom"><INPUT size="30" name=commformulaid disabled
			value="<%= ((lController.getTrx().getCommFormulaId() != null)?lController.getTrx().getCommFormulaId():"")%>">
			</TD>
	</TR>	
	
	<TR>
		<TD height="25">Request TS :</TD>
		<TD height="25"><INPUT size="30" name=requestts disabled value="<%= ((lController.getTrx().getRequestTs() != null)?lController.getTrx().getRequestTs():"")%>"></TD>
	</TR>
		
	<TR>
		<TD height="25">Response TS :</TD>
		<TD height="25"><INPUT size="30" name=rests disabled value="<%= ((lController.getTrx().getResponseTs() != null)?lController.getTrx().getResponseTs():"")%>"></TD>
	</TR>
	
	<TR>
		<TD height="25">TS Created :</TD>
		<TD height="25"><INPUT size="30" name=tscreated disabled value="<%= ((lController.getTrx().getTsCreated() != null)?lController.getTrx().getTsCreated():"")%>"></TD>
	</TR>
	
	<TR>
		<TD height="25">TS Updated :</TD>
		<TD height="25"><INPUT size="30" name=tsupdated disabled value="<%= ((lController.getTrx().getTsUpdated() != null)?lController.getTrx().getTsUpdated():"")%>"></TD>
	</TR>
	
	<TR>
		<TD height="50" colspan="2" align="center">		
			<input type="button" value="Submit" align="center" onClick="getData()" <%=lController.getEditMode() %>>			
		<INPUT type=button value=Back onClick="back()">
		</TD>
	</TR>
</TABLE>

</FORM>
<form name="Back"></form>
</BODY>
</HTML>
