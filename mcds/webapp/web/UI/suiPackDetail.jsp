<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiPackDetailController" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.gisil.mcds.dmi.PackItemForUI" %>

<% SuiPackDetailController lController = new SuiPackDetailController(request,response); %>
<html>
<head>
<title>Pack Detail</title>
<script language="javascript" type="text/javascript">


</script>
</head>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<h2 align="center"><%=lController.getPack().getTitle() %></h2>
<form name="contentMenu" method="post" action="<%=response.encodeURL("ManageContentServlet?ctId=2") %>">
	<table width="100%" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#C7C7C7">  
			<tr align="center">
				<td>
					<table border=0 width="100%">
						<tr>
							<td><strong>Pack ID:</strong> </td>
							<td><%=lController.getPack().getId() %></td>
							<td><strong>Catalog ID:</strong> </td>
							<td><%=lController.getPack().getCatalogId() %></td>
						</tr>
						<tr>
							<td><strong>Cost:</strong> </td>
							<td><%="Rs." + lController.getPack().getCost() %></td>
							<td><strong>SKU Code:</strong> </td>
							<td><%=lController.getPack().getSku() %></td>
						</tr>
						<tr>
							<td><strong>Status:</strong> </td>
							<td><%=lController.getPack().getStatus() %></td>
							<td><strong>Type:</strong> </td>
							<td><%=lController.getPack().getType() %></td>
						</tr>
						<tr>
							<td><strong>TSCreated:</strong> </td>
							<td><%=lController.getPack().getTsCreated() %></td>
							<td><strong>TSUpdated:</strong> </td>
							<td><%=lController.getPack().getTsUpdated() %></td>
						</tr>
						<tr>
							<td><strong>Description:</strong> </td>
							<td colspan="3" nowrap="wrap"><%=lController.getPack().getDescription() %></td>
						</tr>						
					</table>
				</td>
			</tr>
			<tr align="center">
				<td>
					<table border=1 width="100%">
						<tr>
							<th><strong>Item ID</strong> </th>
							<th><strong>Item Status</strong></th>
							<th><strong>Title</strong></th>
							<th><strong>Type</strong></th>
							<th><strong>TS Created</strong></th>
							<th><strong>TS Updated</strong></th>
						</tr>
						<%ArrayList items = lController.getPack().getItems();
						Iterator itr = items.iterator();
						PackItemForUI contents = null;
						while(itr.hasNext()){
							contents = (PackItemForUI)itr.next();%>
						<tr align="center">
							<td><%=contents.getId() %></td>
							<td><%=contents.getStatus() %></td>
							<td><%=contents.getTitle() %></td>
							<td><%=contents.getType() %></td>
							<td><%=contents.getTsCreated() %></td>
							<td><%=contents.getTsUpdated() %></td>
						</tr>
						<%} %>
						
												
					</table>
				</td>
			</tr>
	</table>
<br>
	  <center><input type="submit" value="Back" /></center>	
</form>

</body>
</html>
