<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiViewTransStatusController"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiTransactionMenuController"%>
<%@ page import="com.gisil.mcds.web.servlet.ui.TransactionStatusServlet" %>


<%new SuiViewTransStatusController(request,response);%>

<html>
<head>
<title>View Transcation Status</title>
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
	
	function back()
	{
		gettrxid.action="<%=SuiTransactionMenuController.PAGE_PATH %>"
		gettrxid.submit();
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
	
	  if(!validateNotEmpty(gettrxid.trxid.value))
   		{
	       alert("Transaction Id should not be empty"); 
	       gettrxid.trxid.value="";
	       gettrxid.trxid.focus();
	       
	       return false;
   		} 		
   		
   		else if(!validateNumeric(gettrxid.trxid.value))
   		{
	       alert("Transaction Id should be numeric");
	       gettrxid.trxid.value="";
	       gettrxid.trxid.focus ();
	      
	       return false;
   		}  
   		else if(ValidateNonZero(gettrxid.trxid.value))
   		{
	       alert("Transaction Id should not be zero(0)");
	       gettrxid.trxid.value="";
	       gettrxid.trxid.focus ();
	      
	      return false;
   		} 
   		 else if(gettrxid.trxid.value>9999999999)
  {
     alert("The value of Transaction id should be less than 10 digits.");
     gettrxid.trxid.value="";
     gettrxid.trxid.focus();
     
     return false;
  }  
  else if(gettrxid.trxid.value<0)
  {
     alert("The value of Transaction id should be non negative");
     gettrxid.trxid.value="";
     gettrxid.trxid.focus();
     
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
        gettrxid.action="<%=response.encodeURL( TransactionStatusServlet.SERVLET_PATH )%>"; // Any Action Mapped in Web.xml will be specified here.
   		gettrxid.submit();
   		}
   		
   		
   }
	</script>
<link href="indepay.css" rel="stylesheet" type="text/css" />
<BODY>

<FORM name="gettrxid" method="post">
<TABLE cellSpacing=0 cellPadding=0 border=0 align="center">
		<TR>
			<TH colspan="2" HEIGHT="50">
			<h3><u>View Transaction Status</u></h3>
			</TH>
		</TR>		
		<TR>
			<TD align="center">Transaction Id:</TD>
			<TD><input type="text" name="trxid"></TD>
		</TR>
		<TR>
			<TD colspan="2" ALIGN="CENTER"  HEIGHT="50"><input name="button" type="button" onClick="getTrxId()" value="Submit">&nbsp;
			<input type="button" value="Back" onclick="back()"></TD>
	</TR>
</TABLE>
</FORM>
</BODY>
</HTML>
