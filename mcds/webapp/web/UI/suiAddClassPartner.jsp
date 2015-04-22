<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiAddPartnerController"%>
<%SuiAddPartnerController SuiAddPartnerController = new SuiAddPartnerController(request, response);
            %>
<html>
<head>
<title>Add Class Surcharge</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<script>
function backBtn(){
window.location="<%=SuiAddPartnerController.getBackURL()%>";
}
</script>

<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<form name="addPartner" action="PartnerMgmtServlet?action=add" method="post">
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td height="45" align="center"><b>Add Class Partner</b></td>
	</tr>
	<tr>
		<td height="10" align="center"></td>
	</tr>
	<tr>
		<td height="10" align="center"></td>
	</tr>
	<tr>
		<td align="center" style="font:bold"><%String msg = SuiAddPartnerController.getMsg();
            if (msg != null) {%> <b><%out.println(msg);
            }

            %></b> <br />
		<br />

		</td>
	</tr>
	<tr>
		<td height="44" align="center" valign="top">
		<table width="50%" border=0 align="center" cellpadding="0" cellspacing="1">
			<tr>
				<td><b>Partner Name </td>
				<td><input id="partnername" type="text" maxLength=100 name=partnername value=""></td>
			</tr>
			<tr>
				<td><b>Description </td>
				<td><input height="20" type="text" maxLength=150 name=description value='<%=SuiAddPartnerController.getDescription()!= null?SuiAddPartnerController.getDescription():"" %>'></td>
			</tr>
			<tr>
				<td><b>Status</td>
				<td>
				<select name="status">
					<option value="0">Enable</option>
					<option value="1">Disable</option>
				</select>
		   </td>
			<tr>
				<td height="10" align="center"></td>
			</tr>
			<tr>
				<td height="10" align="center"></td>
			</tr>

			<tr>
				<td></td>
				<TD><input name="button1" type="submit" value="Add" />&nbsp;&nbsp;<input name="button3" type="button" onclick="backBtn();" value="Back" /></TD>
			</tr>
		</table>
		</td>
	</tr>

</table>
</form>
</body>
</html>
