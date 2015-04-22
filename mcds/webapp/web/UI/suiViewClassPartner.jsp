<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.gisil.mcds.dmi.PartnerInfo"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiViewPartnerController" %>
<% SuiViewPartnerController suiViewPartnerController = new SuiViewPartnerController(request,response); %>
<html>
<head>
<title>View Class Partner</title>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<script>
function add(){
window.location="<%=suiViewPartnerController.getAddUrl()%>";
}
</script>
<link href="indepay.css" rel="stylesheet" type="text/css">
 <body>
 <form name="viewClassPartner" action="suiCommissionMenu.jsp?" method="POST">
 
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr bgcolor=""><td height="45" align="center"><h3><u>View Class Partner</u></h3></td></tr>
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
				<th nowrap height="30">Id</th>
				<th>Partner Name</th>
				<th>Status</th>
				<th nowrap>
				<div  align="center">Action(s)</div>
				</th>
     	   </tr>
     	  <% 
     	    List<PartnerInfo> list = suiViewPartnerController.getPartnerInfo(); 
     	  
     	    Iterator iterator = list.iterator();
     	  	
     	  	while ( iterator.hasNext() )
     	  	{
     	  	 PartnerInfo partner = (PartnerInfo)iterator.next();
     	   %>
     	   <tr>
     	   <td><%=partner.getId()%></td>
     	   <td><%=partner.getPartnerName()%></td>
     	   <td><%=partner.getStatus()%></td>
     	   <td align="center">
     	   <% if(suiViewPartnerController.isEditMode()){ %>
     	   <a href =<%=suiViewPartnerController.getEditUrl(partner) %>>Edit</a>
							<%}else{ %>
			<a href =<%=suiViewPartnerController.getPageUrl()%>><strike>Edit</strike><%}%></a>
			</td>
     	   </tr>
     	   
     	   <%}%>
     	  	
     	   </table>
     	    
    </td>
	</tr>
	<tr>
	  <td align="center">
	  <input name="button2" type="button" onclick="add();"  value="Add" />
      &nbsp;&nbsp;<input name="button1" type="submit" value="Back" />
      </td>
	</tr>
	
   </table>
   </form>
 </body>
</html>