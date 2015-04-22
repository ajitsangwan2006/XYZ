<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiAggrListController"%>
<%@ page import="com.gisil.mcds.dmi.AggregatorInfo" %>
<%@ page import="com.gisil.mcds.web.servlet.ui.ManageContentServlet" %>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiMCDSMenuController" %>
<%@ page import="java.util.Iterator"%>
<% SuiAggrListController lController = new SuiAggrListController(request,response); %>

<html>
<head>
<title>Content Management</title>
</head>
<script language="javascript" type="text/javascript">
function backBtn()
{
document.contentMenu.action="<%=SuiMCDSMenuController.PAGE_PATH %>";
document.contentMenu.submit();
return true;
}

</script>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<h3 align="center">Aggregator List</h3>
<form name="contentMenu" method="post">
	<table width="100%" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#C7C7C7">  
			</tr>
			<% 
			if (lController.getAgrInfoList()==null) {%>
				<tr align="center">
					<td height="50">
					<strong>No Record Found </strong>
					</td>
				</tr>
			<%
			}
			else {
			%>
			<tr align="center" bgcolor="#D3D3AF">
			<th>Serial No.</th>
			<th>Aggregator Name</th>
			<th>View/Manage Contents</th>
			<th>Status</th>
			<th>Action(s)</th>
			</tr>
			<%	Iterator listIterator = lController.getAgrInfoList().iterator();
				int counter = 0;
				while (listIterator.hasNext()) {
					counter++;
						AggregatorInfo agrInfo = (AggregatorInfo)listIterator.next();
			%>
			<tr align="center">
						<td><%=counter %></td>
						<td align="left"><%=agrInfo.getAggregatorName()%></td>
						<td><a href="<%=response.encodeURL(ManageContentServlet.SERVLET_PATH)+ "?aggrId=" + agrInfo.getId()%>">Manage</a></td>
						<td><%=agrInfo.getStatus() %></td>
						<td><a href="<%=lController.getEditURL(agrInfo)%>">
						<% if(lController.isEditMode()){ %>Edit
							<%}else{ %><strike>Edit</strike><%} %></a>
						</td>
			</tr>	

			<%
					}
			}
			%>
	</table>
<br>
	  <center><input type="button" value="Back" onclick="backBtn()"/></center>
	
</form>

</body>
</html>