<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiPartnerListController"%>
<%@ page import="com.gisil.mcds.dmi.PartnerInfo"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>

<%SuiPartnerListController lcontroller = new SuiPartnerListController(
					request, response);

			%>
<html>
<head>
<title>View Partner List</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<script language="javascript" type="text/javascript">
function backBtn()
{
document.partList.action="<%=com.gisil.mcds.web.controller.ui.SuiMCDSMenuController.PAGE_PATH %>";
document.partList.submit();
return true;
}

</script>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<form name="partList">
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr bgcolor="#336699">
		<td height="45" align="center"><b>View Partner List</b></td>
	</tr>
	<tr>
		<td height="10" align="center"></td>
	</tr>

	<tr>
		<td height="10" align="center"></td>
	</tr>
	<tr>
		<td height="44" align="center" valign="top">
		<table width="100%" border="1" align="center" cellpadding="0" cellspacing="1">
			<tr bgcolor="#D3D3AF">
				<th nowrap height="30">S No.</th>
				<th>Partner Name</th>
				<th>Description</th>
				<th>Status</th>
				<th nowrap>
				<div align="center">Action(s)</div>
				</th>
			</tr>
			<%if (lcontroller.getPartnerList().isEmpty()) {

			%>
			<tr>
				<td><strong> No Record Found </strong></td>
			</tr>


			<%} else {
				ArrayList list = (ArrayList) lcontroller.getPartnerList();
				Iterator iterator = list.iterator();
				int counter = 0;
				while (iterator.hasNext()) {
					counter++;
					PartnerInfo partnerInfo = (PartnerInfo) iterator.next();

					%>
			<tr>
				<input type="hidden" name="partnerId" value="<%=partnerInfo.getId()%>" align="center">
				<td><%=counter%></td>
				<td><%=partnerInfo.getPartnerName()%></td>
				<td><%=partnerInfo.getDescription()%></td>
				<td><%=partnerInfo.getStatus()%></td>
				<td align="center"><a
					href="<%=com.gisil.mcds.web.servlet.ui.EditPartnerInfoServlet.SERVLET_PATH%>?action=edit&partnerId=<%=partnerInfo.getId()%>">Edit</a></td>
			</tr>
			<%}
			}

		%>
		</table>
		</td>
	</tr>
</table>
<br />
<center>
<input type="button" value="Back" onclick="backBtn();" >   </center>
</form>
</body>
</html>
