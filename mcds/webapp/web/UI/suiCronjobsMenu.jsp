<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiMCDSMenuController" %>
<%@ page import="com.gisil.mcds.web.servlet.ui.RefreshContentServlet"%>

<html>
<head>
<title>Commission Menu</title>
<style type="text/css">
</style>
</head>
<link href="indepay.css" rel="stylesheet" type="text/css">
    <body>
      <table width="100%" border=0 cellPadding=0 cellSpacing=0>
	<tr align="center">
		<td colspan="2" height="50">
		<h3 class="style4"><u>Cron Jobs Menu</u></h3>		</td>
	</tr>	
	<tr>
		<td Width="45%"></td>
		<td align="left">
			<a href="#">Recon Job</a>
		</td>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left">
		<a href="uploadReconFile.jsp">Upload Recon File</a>
		</td>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left">
		<a href="<%=RefreshContentServlet.SERVLET_PATH%>?action=content">Refresh Content</a>
		</td>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left">
		<a href="<%=RefreshContentServlet.SERVLET_PATH%>?action=phonemapping">Refresh Phone Mapping</a>
		</td>
	</tr>
	<tr >
		<td align="center" colspan="2" height="50">
		  <form name="frmBack" action="<%=SuiMCDSMenuController.PAGE_PATH%>" method="get">
			<input type="submit" value="Back" />
			</form>
		</td>
	</TR>
</table>
    
    </body>
</html>