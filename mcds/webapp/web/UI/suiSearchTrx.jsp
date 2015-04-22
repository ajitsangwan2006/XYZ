<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="com.gisil.mcds.web.controller.ui.SuiSearchTrxController"%>
<%@page import="com.gisil.mcds.transaction.Transaction"%>
<%@page import="com.gisil.mcds.web.servlet.ui.SearchTransResultServlet"%>
<%@page import="com.gisil.mcds.transaction.TrxStatus" %>
<%@page import="java.util.Iterator"%>
<%@ page import="com.gisil.mcds.web.util.Utils"%>

<%SuiSearchTrxController lController = new SuiSearchTrxController(request, response);%>

<HTML>
<HEAD>
<TITLE>SUI Search Results</TITLE>
</HEAD>
<script language="JavaScript" type="text/javascript">

function hide(){ 
document.getElementById('transTable').style.visibility = "hidden";
document.getElementById('allbuttons').style.visibility = "visible";
}

function show(){
document.getElementById('transTable').style.visibility = "visible";
document.getElementById('allbuttons').style.visibility = "hidden";
}

function backBtn()
{
document.srchtrx.action="<%=response.encodeURL(com.gisil.mcds.web.controller.ui.SuiTransactionMenuController.PAGE_PATH )%>";
document.srchtrx.submit();
return true;
}

function submitAdvanceSearch( )
{
	
	var result = validateForm( );
	if ( result )
	{
		document.srchtrx.action="<%=response.encodeURL(SearchTransResultServlet.SERVLET_PATH )%>";
		document.srchtrx.submit();
		
	} else
	{
		var message = "Please enter valid Integer Value for TrxID / DelNo ";
		alert( message );
	}

}

function submitAllSearch( )
{
	document.srchtrx.action="<%=response.encodeURL(SearchTransResultServlet.SERVLET_PATH )%>";
	document.srchtrx.submit();
}

function validateForm( )
{
   trxid  =  srchtrx.trxid.value;
   delno = srchtrx.delno.value;
   var result = isNumberInteger(trxid); 
   var result1 = isNumberInteger(delno); 	
   if ( result && result1 )
   return true;
   else
 	return false;
 }

function isNumberInteger(val)
{
	  
      if(val == 'null'){return false;}
            
      if (val.length==0){return true;}
       
      for (var i = 0; i < val.length; i++) {
            var ch = val.charAt(i);
            if (i == 0 && ch == "-") {
                  continue;
            }           
            if (ch < "0" || ch > "9") {
                  return false;
            }
      
      }
   
      return true;
}
</script>

<link href="indepay.css" rel="stylesheet" type="text/css">
<%if (lController.getshowAll()) {%>
<BODY onLoad="hide()">
<%} else {	%>
<BODY onLoad="show()">
<%}	%>
	<FORM name=srchtrx method=post>
		<input type="hidden" name="isSearch" value="true" />
		<table width="70%" height="137" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<th height="50" colspan="2" align="center">
				<h3><u>Search Transaction</u></h3>
				</th>
			</tr>

			<TR>
				<TD width="50%" HEIGHT="35"><input name="searchType" type="radio" value="true"
					onClick="hide();" <%=lController.getshowAll()?"Checked":"" %> > All Transaction</TD>
				<TD noWrap width="50%" HEIGHT="35"><input name="searchType" type="radio" value="false"
					onClick="show();" <%=!lController.getshowAll()?"Checked":"" %> > Advanced Search Transaction</TD>
			</TR>
			<TR>
				<TD noWrap width="50%" HEIGHT="35"><input type="checkbox" name="archive"
					value="<%=lController.getsearchInArchive() %>"> Search in Archive</TD>
			</TR>
		</table>
	<div id="allbuttons" align="center" >
		<INPUT type="button" value="View" onclick="submitAllSearch();" /> <INPUT type="button" value="Back"
			onClick="backBtn()">
	</div>
	<div id="transTable" align="center">
		<table width="70%" height="137" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<TD width="40%" HEIGHT="22" noWrap>Del No:</TD>
				<TD><INPUT type=text name=delno maxlength="12" value='<%=lController.getdelno()!= null?lController.getdelno():""%>'></TD>
			</TR>

			<TR>
				<TD width="40%" HEIGHT="22">Transation Id:</TD>
				<TD><INPUT name=trxid maxlength="15" value='<%=lController.gettrxId()!= null?lController.gettrxId():""%>'></TD>
			</TR>

			<TR>
				<TD width="40%" HEIGHT="22" noWrap>Status of Transactions :</TD>
				<TD><SELECT name="statusTrans">
					<OPTION selected>Select</OPTION>
					<%TrxStatus trxStatus[] = TrxStatus.values();
			for (TrxStatus val : trxStatus) {

				%>
					<OPTION value="<%=val %>" <%=lController.defaultSelected(val.toDBLiteral())%>><%=val%></OPTION>
					<%}

			%>

				</SELECT></TD>
			</TR>
			<TR>
				<TD width="40%" HEIGHT="22" noWrap>Reconcilation Transactions :</TD>
				<TD><SELECT name="reconStatusTrans">
					<OPTION selected>Select</OPTION>
					<OPTION value="true">RECONCILED</OPTION>
					<OPTION value="false">RECON FAILED</OPTION>
				</SELECT></TD>
			</TR>
			<TR>
				<TD HEIGHT="22">From (ddMMyy):</TD>
				<TD HEIGHT="22"><input type="text" name="fromDate" maxlength="6"
					value='<%=lController.getfromDate()!= null?lController.getfromDate():""%>'></TD>
			</TR>

			<TR>
				<TD width="40%" HEIGHT="22">To (ddMMyy):</TD>
				<TD HEIGHT="22"><input type="text" name="toDate" maxlength="6"
					value='<%=lController.gettoDate()!= null?lController.gettoDate():""%>'></TD>
			</TR>
		</table>		
		<INPUT type="submit" value="View" onclick="submitAdvanceSearch();" /> <INPUT type="button" value="Back" onClick="backBtn()">
	</div>
	</FORM>
</BODY>
</HTML>

