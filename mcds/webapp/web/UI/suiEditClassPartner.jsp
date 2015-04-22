<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiEditPartnerController"%>
<%SuiEditPartnerController SuiEditPartnerController = new SuiEditPartnerController(request, response);
            %>
<html>
<head>
<title>Edit Class Partner</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<script>
function backBtn(){
window.location="<%=SuiEditPartnerController.getBackURL()%>";
}
</script>
<script>
function reset(){
window.location="<%=SuiEditPartnerController.getResetURL(request)%>";
}
</script>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<form name="edtPartner" action="PartnerMgmtServlet?action=save&id=<%=SuiEditPartnerController.getId()%>" method="post">
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td height="45" align="center"><b>Edit Class Partner</b></td>
	</tr>
	<tr>
		<td height="10" align="center"></td>
	</tr>
	<tr>
		<td height="10" align="center"></td>
	</tr>
	<tr>
		<td align="center" style="font:bold"><%String msg = SuiEditPartnerController.getMsg();
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
				<td><b>Id </td>
				<td><input type="text" maxLength=100 name=id disabled value='<%=SuiEditPartnerController.getId()!=null?SuiEditPartnerController.getId():""%>'></td>
			</tr>
			<tr>
				<td><b>Partner Name </td>
				<td><input type="text" maxLength=100 name=partnername value='<%=SuiEditPartnerController.getPartnerName()!=null?SuiEditPartnerController.getPartnerName():""%>'></td>
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
				<TD><input name="button1" type="submit" onclick="" value="Save" />&nbsp;&nbsp;<input name="button2" type="reset"
					onclick="reset();" value="Reset" /> <input name="button3" type="button" onclick="backBtn();" value="Back" /></TD>
			</tr>
		</table>
		</td>
	</tr>

</table>
</form>
</body>
</html>
