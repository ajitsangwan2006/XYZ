<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiMCDSMenuController"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiTransactionMenuController" %>
<%@ page import="com.gisil.mcds.web.servlet.ui.AggrListServlet" %>
<%@ page import="com.gisil.mcds.web.servlet.ui.GetPrivilageLevelServlet"%>

<%SuiMCDSMenuController lController =  new SuiMCDSMenuController(request, response);%>
<html>
<head>
<title>Mobile Content Distribution Menu</title>
<script language="javascript">
function backBtn()
{
	window.location="/suinew/operation/suimenu/suimenu.jsp?hit=suinew";
}

function submitForm()
{
	if ( ! ( <%=SuiMCDSMenuController._isPrivilageLevelPresent%> ) )
	{
		document.mcdsMenuForm.action = "<%=response.encodeURL( GetPrivilageLevelServlet.SERVLET_PATH )%>";
		document.mcdsMenuForm.submit();
	}
}
</script>
<link href="indepay.css" rel="stylesheet" type="text/css">
</head>
<body onLoad="submitForm()">
<form name=mcdsMenuForm method=post>
<table width="100%" border=0 cellPadding=0 cellSpacing=0>
	<tr align="center">
		<th colspan="2" height="50">
		<h3><u>Mobile Content Distribution Menu</u></h3>
		</th>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left"><a href="<%=SuiTransactionMenuController.PAGE_PATH %>">Transaction Management</a></td>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left"><a href="<%=AggrListServlet.SERVLET_PATH %>">Content Management</a></td>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left"><a href="suiCommissionMenu.jsp">Commission Management</a></td>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left"><a href="<%=response.encodeURL("configMgtServlet")%>">MCDS System Configuration</a></td>
	</tr>
	<%
   	     if ( lController.isEditMode() )
   	     {
	%>
	<tr>
		<td Width="45%"></td>
		<td align="left"><a href="suiCronjobsMenu.jsp">Cron jobs</a></td>
	</tr>
<%  } else { %>
	<tr>
		<td Width="45%"></td>
		<td align="left"><a href="suiMCDSMenu.jsp"><strike>Cron jobs</strike></a></td>
	</tr>
	<% } %>
	<tr>
		<td Width="45%"></td>
		<td align="left"><a href="<%=response.encodeURL("PhoneMappingServlet?action=view")%>">MCDS Phone Mapping</a></td>
	</tr>

	<tr>
		<td height="50" Width="45%" colspan="2" align="center">
		<input type="button" value="Back" onclick="backBtn()">
		</td>
	</tr>
</table>
</form>
</body>
</html>
