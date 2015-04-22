<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiViewTransDetailController"%>
<%@ page import="com.gisil.mcds.aggregator.mauj.entity.ValidTransTrackerRes"%>
<%@ page import="com.gisil.mcds.aggregator.mauj.entity.TransTrackerResErr"%>
<%@ page import="com.gisil.mcds.transaction.Transaction"%>
<%@ page import="com.gisil.mcds.web.util.Utils"%>

<% 
	SuiViewTransDetailController viewController = new SuiViewTransDetailController( request, response );
%>
<html>
<head>
<title>SUI</title>
</head>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body bgColor=#ebebda width="100%" height="100%">

<FORM name=trxstsres method=post>
<table width="500" border="0" cellspacing="0" cellpadding="0" align="center">
 <tr align="center">
            <th  HEIGHT="50"><h3><u>Transaction Detail</u></h3></th>
  </tr>
   <tr>
    <td>
		<fieldset><legend><b>Remote</b></legend>
		<table width="450" cellPadding=0 cellSpacing=0 align="center">
		 <%
			boolean isValidTrx = viewController.isValidTransResponse();

			if ( isValidTrx ) 
			{
				ValidTransTrackerRes validTrxRsp = viewController.getValidTransResponse();
       %>
		<TR>
            <TD width="50%" HEIGHT="25">Transaction Id :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getTransId()%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Msisdn No. :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getMsisdnNo()%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Phone Make :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getPhoneMake()%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Phone Model :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getPhoneModel()%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Cost :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=Utils.decimalFormat(Double.parseDouble(validTrxRsp.getCost()))%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">SKU Code :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getSku()%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Msisdn No. :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getMsisdnNo()%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Transaction Status :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getTransStatus()%>"></TD>
        </TR>
        <%
         	if ( validTrxRsp.getTransStatus() != null && validTrxRsp.getTransStatus().equalsIgnoreCase( "FAILURE" ) ) 
         	{
        %>
        <TR>
            <TD width="50%" HEIGHT="25">Error Code :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getErrorCode()%>"></TD>
        </TR>
        <% } %>
        <TR>
            <TD width="50%" HEIGHT="25">Pos Id :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getPosId()%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Loc Id :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getLocId()%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Log Time :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=validTrxRsp.getLogTime()%>"></TD>
        </TR>
        <%} 
			else
			{ 
				TransTrackerResErr invalidTrxResponse = viewController.getInvalidTransResponse();        
         %>
		  <TR height="10%">
            <TD colspan="2" align="center"><b><%=invalidTrxResponse.getErrorMessage()%></b></TD>
          </TR>
          <%}%>
		</table>
		</fieldset>
		</td>
	</tr>
	<tr>
    <td width="25">&nbsp;</td>
  </tr>
	<td>
		<fieldset><legend><b>Local</b></legend>
		<table width="450" cellPadding=0 cellSpacing=0 align="center">
		<% 
			Transaction localTrx = viewController.getLocalTransactionDetail();
		   if ( localTrx != null )
		   {
		%>
		
		<TR>
            <TD width="50%" HEIGHT="25">Transaction Id :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=localTrx.getTrxId()%>"></TD>
        </TR>
		<TR>
            <TD width="50%" HEIGHT="25">Transaction Status :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=localTrx.getTrxStatus().toString()%>"></TD>
        </TR>
		<TR>
            <TD width="50%" HEIGHT="25">Aggregator Id :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getAggregatorId())%>"></TD>
        </TR>
		<TR>
            <TD width="50%" HEIGHT="25">Transaction Type :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField(localTrx.getTrxType())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Base Amount :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=Utils.decimalFormat(localTrx.getBaseAmt().doubleValue())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Del No. :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField(localTrx.getDelNo())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Merchant Id :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getMerchantId())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Commisssion Amount :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=Utils.decimalFormat(localTrx.getCommissionAmt().doubleValue())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Surcharge Amount :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=Utils.decimalFormat(localTrx.getSurchargeAmt().doubleValue())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Total Amount :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=Utils.decimalFormat(localTrx.getTotalAmt().doubleValue())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Parent Transaction Id :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getParentTrxId())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Recon Status :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getReconStatus())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Refunded :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=localTrx.isRefunded()%>"></TD>
        </TR>                
        <TR>
            <TD width="50%" HEIGHT="25">Msisdn :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getMsisdn())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Phone Make :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getPhoneMake()) %>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Phone Model :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getPhoneModel() )%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">SKU Code :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getSku() )%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">SKU Name :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getSkuName() )%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Response Code :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getResponesCode() )%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Response Message :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getResponseMessage() )%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Aggregator Phone Code :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getAggregatorPhoneCode() )%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Remarks :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getRemarks() )%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Merchant Amount :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=Utils.decimalFormat(localTrx.getMerchantAmt().doubleValue())%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Merchant Balance :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=Utils.decimalFormat(Double.parseDouble(viewController.convertNullToBlankField(localTrx.getMerchantBalance() )))%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Payment Mode :</TD>
            <% if ( localTrx.getPaymentMode() != null  )  
            	{
            %>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=localTrx.getPaymentMode().toDbLiteral() %>"></TD>
            <% } else { %>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value=""></TD>
            <%} %>
        </TR>
        
        <TR>
            <TD width="50%" HEIGHT="25">Request Time Stamp :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getRequestTs() )%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Response Time Stamp :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getResponseTs() )%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Aggregator Transaction Id :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=viewController.convertNullToBlankField( localTrx.getAggregatorTrxId() )%>"></TD>
        </TR>
        <TR>
            <TD width="50%" HEIGHT="25">Commission Formula Id :</TD>
            <TD width="50%"><INPUT maxLength=100 name=trxId disabled value="<%=localTrx.getCommFormulaId()%>">
            &nbsp;<a href="javascript:winOpen()">View</a></TD>
          <script language="javascript" type="text/javascript">
          function winOpen()
          {
           	var width = 400;
    		var height = 200;
    		var left = parseInt((screen.availWidth/2) - (width/2));
    		var top = parseInt((screen.availHeight/2) - (height/2));
    		var windowFeatures = "width=" + width + ",height=" + height + ",menubar=no,status=no,toolbar=no,scrollbars=no" + left + ",top=" + top + "screenX=" + left + ",screenY=" + top;
          	window.open("suiCommInfoMap.jsp?popup=true&trxId=<%=localTrx.getTrxId()%>", "Welcome", windowFeatures);
          }
          </script>
        </TR>
		<% }else { %>
		 <TR height="10%">
            <TD colspan="2" align="center"><b>No data Found </b></TD>
          </TR>
		<% } %>
		<TR>
           <TD align="center" HEIGHT="50" colspan="2">
           <INPUT name="button" type=button onClick="history.back(-1)" value=Back></TD>
        </TR>
		</table>
		</fieldset>
		</td>
	</tr>
</table>
</FORM>
</body>
</html>