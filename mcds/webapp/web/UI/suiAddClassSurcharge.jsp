<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Iterator"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiAddSurchargeController"%>
<%SuiAddSurchargeController suiAddSurchargeController = new SuiAddSurchargeController(request, response);

            %>
<html>
<head>
<title>Add Class Surcharge</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<script>
function backBtn(){
window.location="<%=suiAddSurchargeController.getBackURL()%>";
}
</script>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<form name="addPartner" action="classSurchageServlet?action=add" method="post">
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td height="45" align="center"><h3>Add Class Surcharge</h3></td>
	</tr>
	<tr>
		<td height="10" align="center"></td>
	</tr>
	<tr>
		<td height="10" align="center"></td>
	</tr>
	<tr>
		<td align="center" style="font:bold"><%String msg = suiAddSurchargeController.getMessage();
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
				<td><b>Class Code </td>
				<td><input type="text" maxLength=100 name=classCode value='<%=suiAddSurchargeController.getClassCode()!= null?suiAddSurchargeController.getClassCode():""%>'></td>
			</tr>
			<tr>
				<td><b>Class Name </td>
				<td><input type="text" maxLength=100 name=className value='<%=suiAddSurchargeController.getClassName()!= null?suiAddSurchargeController.getClassName():""%>'></td>
			</tr>
			<tr>
				<td><b>Aggregator </td>
				<td><select name="agg" STYLE="width: 110px">
					<%HashSet aggregatorNames = suiAddSurchargeController.getAggregatorNames(session);
            Iterator iterator = aggregatorNames.iterator();
            while (iterator.hasNext()) {
                String aggregatorName = (String) iterator.next();

                %>
					<option value="<%=aggregatorName%>"><%=aggregatorName%></option>
					<%}%>
				</select></td>
			</tr>
			<tr>
				<td><b>Surcharge Type </td>
				<td>
				<select name="surchargeType" STYLE="width: 110px">
					<option value="Fixed">Fixed</option>
					<option value="Percentage">Percentage</option>
				</select>
				</td>
			</tr>
			<tr>
				<td><b>Surcharge </td>
				<td><input type="text" maxLength=100 name=surcharge value='<%=suiAddSurchargeController.getSurcharge()!= null?suiAddSurchargeController.getSurcharge():""%>'></td>
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
			<tr>
				<td></td>
				<TD><input name="button1" type="submit" onclick="" value="Add" />&nbsp;&nbsp;<input name="button3" type="button"
					onclick="backBtn();" value="Back" /></TD>
			</tr>
		</table>
		</td>
	</tr>

</table>
</form>
</body>
</html>
