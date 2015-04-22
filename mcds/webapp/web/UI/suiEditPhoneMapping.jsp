<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="com.gisil.mcds.dmi.PhoneMappingInfo"%>
<%@page import="com.gisil.mcds.web.servlet.ui.PhoneMappingServlet"%>
<%@page import="com.gisil.mcds.web.controller.ui.SuiEditPhoneMappingController"%>

<html>
<% SuiEditPhoneMappingController controller = new SuiEditPhoneMappingController(request,response);%>
<head>
<title>Edit Phone Mapping</title>
<script language="javascript" type="text/javascript">
 
function backBtn()
{
Back.action="<%=response.encodeURL(PhoneMappingServlet.SERVLET_PATH+"?action=view")%>";
Back.submit();
}

function doSubmit()
   		{
   		
   		comm.action="<%=response.encodeURL(PhoneMappingServlet.SERVLET_PATH+"?action="+controller.getAction())%>";
   		comm.submit();
   	}
</script>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<link href="indepay.css" rel="stylesheet" type="text/css">
 <body>
 <% PhoneMappingInfo info = controller.getInfo();

 %>
 <form name="comm" method="POST">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr ><td height="45" align="center"><h3><u>Edit Phone Mapping</u></h3></td></tr>
 <tr>
		<td height="10" align="center"></td>
</tr>
	<tr>	
		<td height="10" align="center"></td>
	</tr>
<tr>
<td height="44" align="center" valign="top">
<table width="50%" border=0 align="center" cellpadding="0" cellspacing="1">
<tr>
	<td height="25">Aggregator Code:</td>
	<td height="25"><input  maxlength="10" size="30" name=aggCode value="<%=info.getAggPhoneCode()%>" disabled></td>
	</tr>
	<tr>
	<td height="25" >Aggregator Make:</td>
	<td height="25"><input  maxlength="256" size="30" name=aggMake value="<%=info.getAggPhoneMake()%>" disabled></td>
	</tr>
	<tr>
	<td height="25">Aggregator Model:</td>
	<td height="25"><input  maxlength="256" size="30" name=aggModel value="<%=info.getAggPhoneModel()%>" disabled></td>
	</tr>
	<tr>
	<td height="25">Make:</td>
	<td height="25"><input  maxlength="256" size="30" name=make value="<%=info.getPhoneMake()%>"></td>
	</tr>
	<tr>
	<td height="25">Model:</td>
	<td height="25"><input  maxlength="256" size="30" name=model value="<%=info.getPhoneModel()%>"></td>
	</tr>
	<tr>
		<td height="10" align="center"></td>
</tr>
	<tr>	
		<td height="10" align="center"></td>
	</tr>
<tr>
	<TD colspan="2" align="center"><input name="button1" type="submit" onclick="return doSubmit()"  value="Save" />&nbsp;&nbsp;<input name="button2" type="reset" onclick=""  value="Reset" />
	&nbsp;&nbsp;<input name="button3" type="button" onclick="backBtn()"  value="Back" />
	</TD>
</tr>
</table>
</td>
</tr>

</table>
<input type=hidden name=aggCode value="<%=info.getAggPhoneCode()%>" />
</form>
<form name="Back" method="POST"></form>
</body>
</html>