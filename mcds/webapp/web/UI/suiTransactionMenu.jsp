<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiTransactionMenuController" %>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiViewTransStatusController" %>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiSearchTrxController" %>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiSettleTransController" %>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiMCDSMenuController" %>
<% SuiTransactionMenuController lController = new SuiTransactionMenuController(request, response); %>
<html>
<head>
<title>Transaction Menu</title>
<script language="JavaScript" type="text/javascript">
function backBtn()
{
document.frmBack.action="<%=SuiMCDSMenuController.PAGE_PATH %>";
document.frmBack.submit();
return true;
}
</script>
</head>

<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<table width="100%" border=0 cellPadding=0 cellSpacing=0>
	<tr align="center">
		<td colspan="2" height="50">
		<h4><u>Transaction Menu</u></h4></td>
	</tr>	
	<tr>
		<td Width="45%"></td>
		<td align="left">
		<a href="<%=SuiViewTransStatusController.PAGE_PATH %>">View Transaction Status</a>
		</td>
	</tr>
	
	<tr>
		<td Width="45%"></td>
		<td align="left">
		<a href="<%=SuiSearchTrxController.PAGE_PATH %>">Search Transaction</a>
		</td>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left">
		<% if(lController.isEditMode()){ %>
		<a href="<%=SuiSettleTransController.PAGE_PATH %>">Settle Transaction</a>
		<%}else{ %>
		<a href="<%=SuiTransactionMenuController.PAGE_PATH %>"><strike>Settle Transaction</strike></a>
		<%} %>
		</td>
	</tr>

    <tr > <td colspan="2" align="center" height="50">
    <form name="frmBack">
     <input type="submit" value="Back" onClick="backBtn()">
     </form>
     </TD>     
	</tr> 
</table>
</body>
</html>
