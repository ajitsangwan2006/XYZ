<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gisil.mcds.dmi.PhoneMappingInfo"%>
<%@page import="com.gisil.mcds.web.controller.ui.SuiListPhoneMappingController"%>
<%@page import="com.gisil.mcds.web.servlet.ui.PhoneMappingServlet"%>
<% SuiListPhoneMappingController lController = new SuiListPhoneMappingController(request,response);%>
<html>
<head>
<title>Phone Mapping</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<link href="indepay.css" rel="stylesheet" type="text/css">
 <body>
 <form name="showComm" action="<%=response.encodeURL("suiMCDSMenu.jsp") %>" method="POST">
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr ><td height="45" align="center"><h3><u>Phone Mapping</u></h3></td></tr>
 <tr>
		<td height="10" align="center"></td>
	</tr>
	
	<tr>
		<td height="10" align="center"></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
	<td height="44" align="center" valign="top">
	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="1">
			<tr bgcolor="#D3D3AF">
				<th nowrap height="30">S.No.</th>
				<th>Aggregator Code</th>
				<th>Aggregator Make</th>
				<th>Aggregator Model</th>
				<th>Make</th>
				<th>Model</th>
				<th nowrap>
				<div  align="center">Action(s)</div>
				</th>
     	   </tr>
     	  <% 
     	  	ArrayList list = (ArrayList)request.getAttribute( "phoneList" );  
     	  	Iterator iterator = list.iterator();
     	  	int count = 1;
     	  	
     	  	while ( iterator.hasNext() )
     	  	{
     	  		PhoneMappingInfo info = ( PhoneMappingInfo)iterator.next();
     	   %>
     	   <tr>
     	   <td align="center"><%=count%></td>
     	   <td align="center"><%=info.getAggPhoneCode()%></td>
     	   <td align="center"><%=info.getAggPhoneMake()%></td>
     	   <td align="center"><%=info.getAggPhoneModel()%></td>
     	   <td align="center"><%=info.getPhoneMake()%></td>
     	   <td align="center"><%=info.getPhoneModel()%></td>
     	   <td align="center">
     	   <a href="<%=lController.getEditURL(info.getAggPhoneCode()) %>">
     	   <% if(lController.isEditMode()){ %>Edit <%}else{ %><strike>Edit</strike><%} %></a>
     	   </td>
     	   </tr>
     	   <%count++; } %>
     	   	
     	   </table>
     	    
    </td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
			<td align="center">
				<input name="button2" type="submit" value="Back" />
			</td>
	</tr>
   </table>
   </form>
 </body>
</html>