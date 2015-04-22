<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gisil.mcds.commission.ClassSurcharge"%>
<%@ page import="com.gisil.mcds.web.util.Utils"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiViewSurchargeController"%>
<%@ page import="java.util.HashSet"%>

<%SuiViewSurchargeController lController = new SuiViewSurchargeController(request, response); %>
<html>
<head>
<title>View Class Surcharge</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<script>
function add(){
window.location="<%=lController.getAddUrl()%>";
}
</script>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<form name="viewClassSurcharg" action="suiCommissionMenu.jsp?" method="POST">

<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td height="45" align="center">
		<h3><u>View Class Surcharge</u></h3>
		</td>
	</tr>
	<tr>
		<td height="10" align="center">&nbsp;</td>
	</tr>

	<tr>
		<td height="10" align="center">&nbsp;</td>
	</tr>
	<tr>
		<td height="44" align="center" valign="top">
		<table width="100%" border="1" align="center" cellpadding="0" cellspacing="1">
			<tr bgcolor="#D3D3AF">
				<th nowrap height="30">S No.</th>
				<th>Class Code</th>
				<th>Class Name</th>
				<th>Aggregator</th>
				<th>Surcharge Type</th>
				<th>Surcharge</th>
				<th>Status</th>
				<th nowrap>
				<div align="center">Action(s)</div>
				</th>
			</tr>
			<%ArrayList<ClassSurcharge> list = lController.getSurchargeList();
            HashSet aggregatorNames = new HashSet();

            Iterator iterator = list.iterator();
            int serialNo = 1;
            while (iterator.hasNext()) {
                ClassSurcharge classSurcharge = (ClassSurcharge) iterator.next();

                %>
			<tr>
				<td><%=serialNo++%></td>
				<td><%=classSurcharge.getClassCode()%></td>
				<td><%=classSurcharge.getClassName()%></td>
				<td><%=classSurcharge.getAggregatorName()%></td>
				<td><%=classSurcharge.isSurchargeFixed() ? "FIXED" : "PERCENT"%></td>
				<td><%=classSurcharge.getSurcharge()%></td>
				<td><%=classSurcharge.getStatus()%></td>
				<td align="center"><a href="<%=lController.getEditUrl(classSurcharge) %>"> 
				<%if (lController.isEditMode()) {

                %>Edit<%} else {

                %><a href =<%=lController.getPageUrl()%>><strike>Edit</strike><%}

                %></a></td>

			</tr>

			<%aggregatorNames.add(classSurcharge.getAggregatorName());

            }
            lController.setaggregatorNames(aggregatorNames,session);
            

        %>

		</table>

		</td>
	</tr>
	<tr>
		<td height="10" align="center">&nbsp;</td>
	</tr>
	<tr>
		<td align="center"><input type="button" value="Add Class Surcharge" onclick="add();"> &nbsp;&nbsp;<input type="submit"
			value="Back"></td>
	</tr>

</table>
</form>
</body>
</html>
