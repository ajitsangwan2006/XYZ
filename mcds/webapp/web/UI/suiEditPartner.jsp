<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="com.gisil.mcds.web.controller.ui.SuiEditPartnerController"%>
<%@page import="com.gisil.mcds.web.servlet.ui.EditPartnerInfoServlet"%>

<% SuiEditPartnerController lController = new SuiEditPartnerController( request, response ); %>
<html>
<head>
<title>Edit Partner Info</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<link href="indepay.css" rel="stylesheet" type="text/css">

<script language="javascript" type="text/javascript">
function doSubmit( partnerId )
{
var answer = true;
answer = confirm(' Are you sure you want to apply your changes?? ' );
if ( answer == true )
{
	editForm.action="<%=response.encodeRedirectURL(EditPartnerInfoServlet.SERVLET_PATH)%>"+"?action=save" +"&partnerId="+ "<%=request.getParameter("partnerId") %>";
	editForm.submit();
}

}

</script>

 <body>
 <FORM name="editForm" method="post">
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr bgcolor="#336699"><td height="45" align="center"><b>Edit Partner Info</b></td></tr>
 <tr>
		<td height="10" align="center"></td>
</tr>
	<tr>	
		<td height="10" align="center"></td>
	</tr>
</tr>
<tr>
<td height="44" align="center" valign="top">
<table width="50%" border=0 align="center" cellpadding="0" cellspacing="1">
	<tr>
	<td><b>Partner Name </td><td><input  maxLength=100 name="partnerName" value="<%=lController.getPartnerName()%>"  disabled></td>
	</tr>
	<tr>
	<td><b>Status </td>
	<td>
	<select name="status">
	<option value="Enable">Enable</option>
	<option value="Disable">Disable</option>
	</select>
	</td>
	</tr>
	<tr><td><b>Description </td><td><textarea rows="4" cols="29" name="description"><%=lController.getDescription()%></textarea></td>
	</tr>
	
	<tr>
		<td height="10" align="center"></td>
</tr>
	<tr>	
		<td height="10" align="center"></td>
	</tr>
</tr>
<tr>
<td></td>
	<TD><input name="button1" type="button" onclick="doSubmit(<%=lController.getId()%>);"  value="Save" />&nbsp;&nbsp;<input type="reset" value="Reset" />
	&nbsp;&nbsp;<input type=submit value="Back" onclick=(action="<%=com.gisil.mcds.web.servlet.ui.PartnerListServlet.SERVLET_PATH %>") />
	</TD>
</tr>
</table>
</td>
</tr>
</table>
</FORM>
</body>
</html>