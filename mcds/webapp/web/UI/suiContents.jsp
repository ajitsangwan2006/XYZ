<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiContentsController" %>
<%@ page import="com.gisil.mcds.dmi.ContentDetailsforUI" %>
<%@ page import="java.util.Iterator" %>
<% SuiContentsController lController = new SuiContentsController(request,response); %>
<html>
<head>
<title>Mobile Content</title>
<script language="javascript" type="text/javascript">
function backBtn()
{
document.contentMenu.action="<%=lController.getBackURL() %>";
document.contentMenu.submit();
return true;
}

</script>
</head>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<h3 align="center">Content Management</h3>
<form name="contentMenu" method="post" >
	<table width="100%" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#C7C7C7">  
			
			<tr align="center" bgcolor="#D3D3AF">
			<th>Serial No.</th>
			<th>Name</th>
			<th>View/Manage Contents</th>
			<th>Status</th>
			<th>Action(s)</th>
			</tr>
			<%if (lController.getList().isEmpty()) {	%>
			<tr>
				<td><strong> No Record Found </strong></td>
			</tr>
			<%} else {
					Iterator listIterator = lController.getList().iterator();
					int count = 0;
					while (listIterator.hasNext()) {
						count++;
						ContentDetailsforUI detail = (ContentDetailsforUI) listIterator.next();
			%>
			<tr align="center">
						<td><%=count %></td>
						<td align="left"><%=detail.getContentName()%></td>
						<!-- This block will be wipe out when we have top 10 as well -->
						<td><a href="<%=lController.getLinkOnTopTen(detail) %>">
						<%if(detail.getContentId() == 1){ %>
						<strike>Manage</strike><%}else { %>
						Manage<%} %></a></td>
						<td><%=detail.getStatus() %></td>
						<%if(detail.getContentId() != 1){ %>
						<td><a href="<%=lController.getEditURL(detail)%>">
						<% if(lController.isEditMode()){ %>Edit<%}else{ %><strike>Edit</strike><%} %></a></td>
						<%}else { %>
						<td><strike>Edit</strike></td>
						<%} %>					
			</tr>	
			<%
				}
			}
			%>

	</table>
<br>
	  <center><input type="submit" value="Back" onclick="backBtn()" /></center>
	
</form>

</body>
</html>
