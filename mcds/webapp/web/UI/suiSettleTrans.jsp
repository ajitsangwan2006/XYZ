<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.servlet.ui.MCDSSettleTrx" %>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiTransactionMenuController" %>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiSettleTransController" %>
<% SuiSettleTransController lController = new SuiSettleTransController(request, response); %>
<html>
<head>
<title>SUI Settle Transaction</title>
<link href="indepay.css" rel="stylesheet" type="text/css">
</head>
<script language="JavaScript" type="text/javascript">
 	
 	
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
	
	  if(!validateNotEmpty(settlegettrxid.trxid.value))
   		{
	       alert("Transaction Id should not be empty"); 
	       settlegettrxid.trxid.value="";
	       settlegettrxid.trxid.focus();
	       
	       return false;
   		} 		
   		
   		else if(!validateNumeric(settlegettrxid.trxid.value))
   		{
	       alert("Transaction Id should be numeric");
	       settlegettrxid.trxid.value="";
	       settlegettrxid.trxid.focus ();
	      
	       return false;
   		}  
   		else if(ValidateNonZero(settlegettrxid.trxid.value))
   		{
	       alert("Transaction Id should not be zero(0)");
	       settlegettrxid.trxid.value="";
	       settlegettrxid.trxid.focus ();
	      
	      return false;
   		} 
   		 else if(settlegettrxid.trxid.value>9999999999)
  {
     alert("The value of Transaction id should be less than 10 digits.");
     settlegettrxid.trxid.value="";
     settlegettrxid.trxid.focus();
     
     return false;
  }  
  else if(settlegettrxid.trxid.value<0)
  {
     alert("The value of Transaction id should be non negative");
     settlegettrxid.trxid.value="";
     settlegettrxid.trxid.focus();
     
     return false;
  }
    else
     return true;
  }// end of function submit_data();//
  function getTrxId()
   		{
   		//submit_data();//
   		if(submit_data())
   		{
        settlegettrxid.action="<%=MCDSSettleTrx.SERVLET_PATH %>";
   		settlegettrxid.submit();
   		}
   		
   		
   }
   function back(){
   	settlegettrxid.action="<%=SuiTransactionMenuController.PAGE_PATH %>";
   		settlegettrxid.submit();
   }
	</script>
<link href="indepay.css" rel="stylesheet" type="text/css">
<BODY>
<FORM name=settlegettrxid method=post>
<table width="323" border=0 cellPadding=0 cellSpacing=0 ALIGN="CENTER">
	<tr>
		<th colspan="2" height="50">
		<h3><u>Settle Transaction Status</u></h3>
		</th>
	</tr>	
	<tr>
		<TD width="50%"><B>Transaction Id:</B></TD>
		<TD width="50%"><input type="text" name="trxid"></TD>
	</tr>
	<tr>
		<TD colspan="2" height="50" align="center">
			<input type="button" value="Submit" onClick="getTrxId()" <%=lController.getEditMode() %>>&nbsp;<INPUT type="button" value="Back" onClick="back()">
		</TD>
	
	</tr>
</table>
</FORM>
</BODY>
</HTML>
