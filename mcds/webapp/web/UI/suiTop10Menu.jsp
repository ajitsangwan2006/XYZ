<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="com.gisil.mcds.web.controller.ui.SuiTop10MenuController"%>
<%@page import="java.util.ArrayList"%>
<%SuiTop10MenuController top10MenuController = new SuiTop10MenuController(
			request, response);
			System.out.println("Constructor of conroller is runnin");
			ArrayList<String> contentList = top10MenuController.getContents();
			int noOfPacks = contentList.size();
			System.out.println("Size of Content list is:" + noOfPacks);
			%>
<html>
<head>
<title>Top 10 MENU</title>
</head>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<form name="suiTop10Menu" method="post">
<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
	<tr hieght="25">
	<td align="center" colspan="3" width="100%"><input type="submit" name="addNewContent" value="Add New Catagory" onclick=(action="suiAddContent.jsp")></td>
	</tr>
	<%	int	i = 1;
		int j = 0;
			while (i <= contentList.size()) {
				%>
	<tr height="25">
		
		<td width="5%"><%=i%></td>
		<td width="64%"><%=contentList.get(j)%></td>
		<td width="12%"><input type="submit" name="addContent" value="Add Content" onclick=(action="suiAddContent.jsp"))>
		<td width="12%"><input type="submit" name="addItem" value="Add Item" onclick=(action="suiAddItem.jsp")>
	</tr>
	<%
	j++;
	i++;
}
		%>
<tr height="40"><td align="center" colspan="3" ><input type="button" name="bckButton" value="Back" onclick=(action="suiMainContentMenu.jsp")>
</table>
</form>
</body>
</html>