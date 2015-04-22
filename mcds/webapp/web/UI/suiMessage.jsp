<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiMessageController"%>
<%SuiMessageController lController = new SuiMessageController(
					request, response);

			%>
<html>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<FORM name=message method=post action="<%=response.encodeURL(lController.getBackURL())%>">
<table width="323" border=0 cellPadding=0 cellSpacing=0 ALIGN="CENTER">
	<tr>
		<th colspan="2" height="50">
		<h3><u>Message</u></h3>
		</th>
	</tr>
	<tr>
		<TD width="50%" align="center"><B><%=lController.getMessage()%></B></TD>
	</tr>
	<tr>
		<td colspan="2" height="50" align="center">
			<input type="hidden" name="aggId" value="<%=request.getAttribute("aggId") %>"> 
			<input type="hidden" name="trxid" value="<%=request.getAttribute("trxId") %>"> 
			<input type="hidden" name="contentId" value="<%=request.getAttribute("contentId") %>"> 
			<input type="hidden" name="queryString" value="<%=request.getAttribute("queryString") %>"> 
			<input type="submit" value="Back"></TD>
	</tr>
</table>
</FORM>
</body>
</html>
