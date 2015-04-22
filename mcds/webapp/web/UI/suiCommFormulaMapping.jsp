<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gisil.mcds.dmi.CommFormulaMappingInfo"%>
<%@page import="com.gisil.mcds.web.servlet.ui.CommFormulaMappingServlet"%>
<%@page import="com.gisil.mcds.web.controller.ui.SuiCommFormulaMappingController"%>
<% SuiCommFormulaMappingController controller = new SuiCommFormulaMappingController(request,response);%>
<html>
<head>
<title>Commission Formula Mapping</title>
<script language="javascript" type="text/javascript">
function addBtn()
{
showComm.action="<%=response.encodeURL(CommFormulaMappingServlet.SERVLET_PATH+"?action=addView")%>";
showComm.submit();
}

</script>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<link href="indepay.css" rel="stylesheet" type="text/css">
 <body>
 <form name="showComm" action="<%=response.encodeURL("suiCommissionMenu.jsp") %>" method="POST">
<table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr ><td height="45" align="center"><h3><u>Commission Formulas Mapping</u></h3></td></tr>
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
				<th nowrap height="30" align="center">ID</th>
				<th align="center">Aggregator Name</th>
				<th align="center">Comm Formula Id</th>
				<th align="center">Del No.</th>
				<th nowrap align="center">
				<div  align="center">Action(s)</div>
				</th>
     	   </tr>
     	  <% 
     	  	ArrayList list = (ArrayList)request.getAttribute( "commMappingList" );  
     	  	Iterator iterator = list.iterator();
     	  	
     	  	while ( iterator.hasNext() )
     	  	{
     	  		CommFormulaMappingInfo commFormula = ( CommFormulaMappingInfo)iterator.next();
     	   %>
     	   <tr>
     	   <td align="center"><%=""+commFormula.getId()%></td>
     	   <td align="center"><%=""+commFormula.getAggregatorName()%></td>
     	   <td align="center"><%=""+commFormula.getCommformulaId()%></td>
     	   <td align="center"><%=""+commFormula.getDelNo()%></td>
     	   <td align="center">
     	   <%if(controller.isEditMode()){ %>
     	   	<a href ="CommFormulaMappingServlet?action=edit&id=<%=""+commFormula.getId()%>">Edit</a>
     	   	<%}else{ %>
     	   <a href ="CommFormulaMappingServlet?action=view&id=<%=""+commFormula.getId()%>">	<strike>Edit</strike></a>
     	   	<%} %>
     	   	</td>
     	   </tr>
     	   <% } %>
     	   	
     	   </table>
     	    
    </td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
			<td align="center">
				<input name="button2" type="button" value="Add" onClick = "addBtn()" <%=controller.getEditMode() %>/>
				<input name="button1" type="submit" value="Back" /></td>
			</tr>
   </table>
   </form>
 </body>
</html>