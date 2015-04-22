<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="com.gisil.mcds.dmi.CommFormulaInfo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.gisil.mcds.dmi.NameIdInfo"%>
<%@page import="com.gisil.mcds.web.servlet.ui.CommFormulaServlet"%>
<%@page import="com.gisil.mcds.web.controller.ui.SuiEditCommFormulaController"%>
<%@page import="com.gisil.mcds.util.DBUtil"%>

<html>
<% SuiEditCommFormulaController controller = new SuiEditCommFormulaController(request,response);%>
<head>
<title>Edit Commission Formula</title>
<script language="javascript" type="text/javascript">
function  validateNumeric( strValue )
 	{
	  var objRegExp  =  /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)/;
	  return objRegExp.test(strValue);
 	}
 	function  validateInteger( strValue )
 	{
	  var objRegExp  =  /^(\d*)$/;
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
	
		if(!validateNotEmpty(comm.formulaId.value))
		{
			alert("Formula Id should not be empty"); 
	       comm.formulaId.value="";
	       comm.formulaId.focus();
	       
	       return false;
		}else if(!validateInteger(comm.formulaId.value))
		{
			alert("Formula Id should be numeric"); 
	       comm.formulaId.value="";
	       comm.formulaId.focus();
	       
	       return false;
		}
		else if(!validateNotEmpty(comm.commFormula.value))
		{
			alert("Comm formula should not be empty"); 
	       comm.commFormula.value="";
	       comm.commFormula.focus();
	       
	       return false;
		}else if(!validateNumeric(comm.commFormula.value))
		{
			alert("Comm formula should be numeric"); 
	       comm.commFormula.value="";
	       comm.commFormula.focus();
	       
	       return false;
		}else if(!validateNotEmpty(comm.surcharge.value))
		{
			alert("Comm formula should not be empty"); 
	       comm.surcharge.value="";
	       comm.surcharge.focus();
	       
	       return false;
		}else if(!validateNumeric(comm.surcharge.value))
		{
			alert("Comm formula should be numeric"); 
	       comm.surcharge.value="";
	       comm.surcharge.focus();
	       
	       return false;
		}else
     		return true;
  }// end of function submit_data();//
 
function backBtn()
{
Back.action="<%=response.encodeURL(CommFormulaServlet.SERVLET_PATH+"?action=view")%>";
Back.submit();
}

function doSubmit()
   		{
   		
   		if(submit_data())
   		{
   		
   		comm.action="<%=response.encodeURL(CommFormulaServlet.SERVLET_PATH+"?action="+controller.getAction())%>";
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
 <% CommFormulaInfo commFormula = controller.getCommFormula();
List list = commFormula.getPartnerNameList();
 Iterator iterator = list.iterator();

 %>
 <form name="comm" method="POST">
 <input type="hidden" name="id" value="<%=""+commFormula.getId() %>">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr ><td height="45" align="center"><h3><u>Edit Commission Formula</u></h3></td></tr>
 <tr>
		<td height="10" align="center"></td>
</tr>
	<tr>	
		<td height="10" align="center"></td>
	</tr>
<tr>
<td height="44" align="center" valign="top">
<table width="100%" border=0 align="center" cellpadding="0" cellspacing="1">
<tr>
	<td width="173" height="25">Commission FormulaID:</td>
	<td width="173" height="25"><input  maxlength="10" size="30" name=formulaId value="<%=commFormula.getFormulaId() == null?"":(""+commFormula.getFormulaId())%>"></td>
	</tr>
	<tr>
	<td width="173" height="25">Commission Formula:</td>
	<td width="173" height="25"><input  maxlength="256" size="30" name=commFormula value="<%=commFormula.getCommValue() == null?"":(""+commFormula.getCommValue())%>"></td>
	</tr>
	<tr>
	<td width="173" height="25">Commission Type:</td>
	<td>
	<select name="commType" STYLE="width: 160px">
	<%if(commFormula.getCommissionType().equals("FIXED")){ %>
	<option value="FIXED" >FIXED</option>
	<option value="PERCENT">PERCENT</option>
	<%}else{ %>
	<option value="PERCENT" >PERCENT</option>
	<option value="FIXED" >FIXED</option>
	<%} %>
	</select>
	</td>
	</tr>
	<tr><td width="173" height="25">Surcharge Type:</td>
	<td>
	<select name="surchargeType" STYLE="width: 160px">
	<%if(commFormula.getSurchargeType().equals("FIXED")){ %>
	<option value="FIXED" >FIXED</option>
	<option value="PERCENT">PERCENT</option>
	<%}else{ %>
	<option value="PERCENT" >PERCENT</option>
	<option value="FIXED" >FIXED</option>
	<%} %>
	</select>
	</td>
	</tr>
	<tr><td width="173" height="25">Surcharge:</td>
	<td width="173" height="25"><input  maxlength="256" size="30" name=surcharge  value="<%=commFormula.getSurchange() == null?"":(""+commFormula.getSurchange())%>"></td>
	</tr>
	<tr><td width="173" height="25">Is ClassSurcharge:</td>
	<td>
	<select name="isClassSurcharge" STYLE="width: 160px">
	<%if(commFormula.isClassSurchanrge()){ %>
	<option value="true" >TRUE</option>
	<option value="false">FALSE</option>
	<%}else{ %>
	<option value="false" >FALSE</option>
	<option value="true" >TRUE</option>
	<%} %>
	</select>
	</td>
	</tr>
	<tr><td width="173" height="25">Status:</td>
	<td>
	<select name="status" STYLE="width: 160px">
	<%if(commFormula.getStatus().equals("ENABLED")){ %>
	<option value="<%=DBUtil.ENABLED %>" >ENABLED</option>
	<option value="<%=DBUtil.DISABLED %>">DISABLED</option>
	<%}else{ %>
	<option value="<%=DBUtil.DISABLED %>">DISABLED</option>
	<option value="<%=DBUtil.ENABLED %>" >ENABLED</option>
	<%} %>
	</select>
	</td>
	</tr>
	<tr><td width="173" height="25">Partner Name:</td>
	<td>
	<select name="partnerId" STYLE="width: 160px">
	<%while(iterator.hasNext()){
		NameIdInfo info = (NameIdInfo)iterator.next();
		if(info.getId().equals(commFormula.getPartnerId())){%>
	<option value="<%=commFormula.getPartnerId() %>" selected="selected"><%=commFormula.getPartnerName() %></option>
	<%}else{ %>
	<option value="<%=info.getId() %>" ><%=info.getValue() %></option>
	<%}
		}%>
	</select>
	</td>
	</tr>
	
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