<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="com.gisil.mcds.dmi.CommFormulaMappingInfo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.gisil.mcds.web.servlet.ui.CommFormulaMappingServlet"%>
<%@page import="com.gisil.mcds.dmi.NameIdInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gisil.mcds.web.controller.ui.SuiEditCommFormulaMappingController"%>

<html>
<% SuiEditCommFormulaMappingController controller = new SuiEditCommFormulaMappingController(request,response);%>
<head>
<title>Edit Commission Formula Mapping</title>
<script language="javascript" type="text/javascript">
function  validateNumeric( strValue )
 	{
	  var objRegExp  =  /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)/;
	  return objRegExp.test(strValue);
 	}
 	
 	function ValidateNonZero(strValue)
	{
		if(strValue==0){
		return true;
		}
		else
		return false;
	}
	
	
	function trimAll( strValue )
 	{

	 	var objRegExp = /^(\s*)$/;
	    
	    if(objRegExp.test(strValue)) {
	       strValue = strValue.replace(objRegExp, '');
	       if( strValue.length == 0)
	          return strValue;
	    }
	   
	   objRegExp = /^(\s*)([\W\w]*)(\b\s*$)/;
	   if(objRegExp.test(strValue)) {
	       //remove leading and trailing whitespace characters 
	       strValue = strValue.replace(objRegExp, '$2');
	    }
	  return strValue;
	}
	function validateNotEmpty( strValue )
	{
	   var strTemp = strValue;	 
	   if(strTemp != '' && strTemp.length > 0){    
	   strTemp = trimAll(strTemp); }
	   if(strTemp != '' && strTemp.length > 0){ 
	     return true;
	   }
	   return false;
	}
	function submit_data()
	{
	
		if(!validateNotEmpty(comm.delNo.value))
		{
			alert("Del No. should not be empty"); 
	       comm.delNo.value="";
	       comm.delNo.focus();
	       
	       return false;
		}else if(!validateNumeric(comm.delNo.value))
		{
			alert("Del No. should be numeric"); 
	       comm.delNo.value="";
	       comm.delNo.focus();
	       
	       return false;
		}else
     		return true;
  }// end of function submit_data();//
 
function backBtn()
{
Back.action="<%=response.encodeURL(CommFormulaMappingServlet.SERVLET_PATH+"?action=view")%>";
Back.submit();
}

function doSubmit()
   		{
   		
   		if(submit_data())
   		{
   		
   		comm.action="<%=response.encodeURL(CommFormulaMappingServlet.SERVLET_PATH+"?action="+controller.getAction())%>";
   		comm.submit();
   		return true;
   		}else{
   			return false;
   		}
   	}
</script>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<link href="indepay.css" rel="stylesheet" type="text/css">
 <body>
 <% CommFormulaMappingInfo commFormula = controller.getCommFormula();
 List<NameIdInfo> aggList = (ArrayList<NameIdInfo>)commFormula.getAggMap();
 List<Integer> list = commFormula.getCommId();
 Iterator iterator =aggList.iterator();
Iterator listIterator = list.iterator();
 %>
 <form name="comm" method="POST">
 <input type="hidden" name="id" value="<%=""+commFormula.getId() %>">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr ><td height="45" align="center"><h3><u>Edit Commission Formula Mapping</u></h3></td></tr>
 <tr>
		<td height="10" align="center"></td>
</tr>
	<tr>	
		<td height="10" align="center"></td>
	</tr>
<tr>
<td height="44" align="center" valign="top">
<table width="100%" border=0 align="center" cellpadding="0" cellspacing="1">
	<tr><td>Aggregator Name:</td>
	<td>
	<select name="aggId" STYLE="width: 160px">
	<%while(iterator.hasNext()){
		NameIdInfo nameIdInfo = (NameIdInfo)iterator.next();
		if(nameIdInfo.getId().equals(commFormula.getAggregatorId())){%>
	<option value="<%=""+nameIdInfo.getId() %>" Default><%=""+nameIdInfo.getValue() %></option>
	<%}else{ %>
	<option value="<%=""+nameIdInfo.getId() %>" ><%=""+nameIdInfo.getValue() %></option>
	<%}
		}%>
	</select>
	</td>
	</tr>
	<tr><td>Comm Formula Id:</td>
	<td>
	<select name="commId" STYLE="width: 160px">
	<%while(listIterator.hasNext()){
		Object id = listIterator.next();
		if(id.equals(commFormula.getCommformulaId())){%>
	<option value="<%=""+commFormula.getCommformulaId() %>" selected><%=""+commFormula.getCommformulaId() %></option>
	<%}else{ %>
	<option value="<%=""+id %>" ><%=""+id %></option> 
	<%}
		}%>
	</select>
	</td>
	</tr>
	<tr>
	<td>Del No.:</td><td><input  maxlength="10" size="30" name=delNo value="<%=commFormula.getDelNo()==null?"":(""+commFormula.getDelNo())%>"></td>
	</tr>
	<tr>
	
	<tr>
		<td height="10" align="center"></td>
</tr>
	<tr>	
		<td height="10" align="center"></td>
	</tr>
<tr>
<td></td>
	<TD><input name="button1" type="submit" onclick="return doSubmit()"  value="Save" />&nbsp;&nbsp;<input name="button2" type="reset" onclick=""  value="Reset" />
	&nbsp;&nbsp;<input name="button3" type="button" onclick="backBtn()"  value="Back" />
	</TD>
</tr>
</table>
</td>
</tr>

</table>
</form>
<form name="Back" method="POST"></form>
</body>
</html>