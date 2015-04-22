
<html>
<head>
<title>Mobile Content</title>
<script language="javascript" type="text/javascript">


</script>
</head>
<link href="indepay.css" rel="stylesheet" type="text/css">

<body>
<h2 align="center">Content Management</h2>
<form name="contentMenu" method="post" action="<%=response.encodeURL("ContentMgtMenu") %>">
	<table width="100%" border="1" align="center" cellpadding="1" cellspacing="1" bordercolor="#C7C7C7">  
			
			<tr align="center" bgcolor="#D3D3AF">
			<th width="10%"><small>Serial No.</th>
			<th width="10%"><small>Aggregator Name</th>
			</tr>
			<%
			java.util.ArrayList menuItemList = (java.util.ArrayList)request.getAttribute("contentList");
			for ( int index = 0; index < menuItemList.size(); index++ )
			{
	  			String itemName = (String)menuItemList.get( index );
	  			if ( itemName.startsWith( "Top" ) )
	  			{
			%>
			<tr>
						<td width="1%"><small><%=index+1 %></td>
						<td width="19%"><small><a href="<%=response.encodeURL("ManageContentServlet") %>"><%=itemName%></a></td>
						
			</tr>	
			<%
				}
				else
				{
			%>
			<tr>
						<td width="1%"><small><%=index+1 %></td>
						<td width="19%"><small><a href="<%=response.encodeURL("ManagePackServlet") %>"><%=itemName%></a></td>
						
			</tr>	
			<%
				}
			}
			%>

	</table>
<br>
	  <center><input type="submit" value="Back" align="center" /></center>
	
</form>

</body>
</html>
