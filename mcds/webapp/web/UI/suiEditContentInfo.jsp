<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiEditContentInfoController" %>
<%@ page import="com.gisil.mcds.web.servlet.ui.ManageContentServlet" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.gisil.mcds.web.servlet.ui.SuiUpdateContentInfo" %>
<%	SuiEditContentInfoController lController=new SuiEditContentInfoController(request,response); %>

<html>
<link href="indepay.css" rel="stylesheet" type="text/css">   
<head><title>Edit Content Info</title>
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
	
		if(!validateNotEmpty(EditContentInfo.contentName.value))
		{
			alert("Content Name should not be empty"); 
	       EditContentInfo.contentName.value="";
	       EditContentInfo.contentName.focus();
	       
	       return false;
		}
    	else
     		return true;
  }// end of function submit_data();//
  
function backBtn()
{
Back.action="<%=response.encodeURL(lController.getBackUrl())%>";
Back.submit();
}

function doSubmit()
   		{
   		
   		if(submit_data())
   		{
   		
   		EditContentInfo.action="<%=response.encodeURL(SuiUpdateContentInfo.SERVLET_PATH+"?"+request.getQueryString())%>";
   		EditContentInfo.submit();
   		return true;
   		}else{
   			return false;
   		}
   	}

</script>
</head>
<body onLoad="EditContentInfo.submit.focus()">
<FORM name="EditContentInfo" method="post" > 

<TABLE width="500" hcellSpacing=0 cellPadding=0 align=center>
	<tr>
		<th colspan="2" height="50"><u><strong>Edit Content Info</strong></u></th>
	</tr>
	<TR>
		<TD width="173" height="25">Content Name :</TD>
		<TD width="175" height="25"><INPUT type="text" name="contentName" size="30" value="<%=lController.getContentName() %>" <%=lController.getEditMode() %>></TD>
	</TR>
	
	<TR>
		<TD height="25">Aggregator :</TD>
		<TD height="25">
		<select name="aggId" STYLE="width: 160px" <%=lController.getEditMode() %>>
		<%Iterator it = lController.getAggMap().entrySet().iterator();
    	while (it.hasNext()) {
        	Map.Entry pairs = (Map.Entry)it.next();
        	if(pairs.getKey().equals(lController.getAggregatorId())){%>
	        	<option value="<%=pairs.getKey() %>" selected><%=pairs.getValue() %></option>
	        	<%}else{ %>
	        	<option value="<%=pairs.getKey() %>" ><%=pairs.getValue() %></option>
    			<%}
        	}%>
		
		</select>
		</TD>
	</TR>
	
	<TR>
		<TD height="25">Description :</TD>
		<TD height="25"><textarea rows="2" cols="28" name="description" <%=lController.getEditMode() %>><%=lController.getDescription()%></textarea> </TD>
	</TR>

	<TR>
		<TD height="25">Status :</TD>
		<TD height="25">
		<SELECT name="status" STYLE="width: 160px" <%=lController.getEditMode() %>>
		<%if(lController.getStatus().get(0).equals("ENABLED")){ %>
			<option value="<%=lController.getStatus().get(0) %>" DEFAULT>ENABLE</option>
			<option value="<%=lController.getStatus().get(1) %>">DISABLE</option>
			<%}else{ %>
			<option value="<%=lController.getStatus().get(0) %>" DEFAULT>DISABLE</option>
			<option value="<%=lController.getStatus().get(1) %>">ENABLE</option>
			<%} %>
			
		</SELECT>
		</TD>
	</TR>

	<TR>
		<TD height="25">Delivery Mode :</TD>
		<TD height="25">
		
		<%Iterator itr = lController.getDeliveryMode().iterator();
		String val = ""+itr.next();
		%>
		<SELECT name="deliveryMode" STYLE="width: 160px" disabled>
			<option value="<%=val %>" DEFAULT><%=val %></option>
			<%while(itr.hasNext()){
				val=""+itr.next();
			%>
			<option value="<%=val %>"><%=val %></option>
			<%}
			%>
		</SELECT></TD>
	</TR>

	<TR>
	
		<TD height="50" colspan="2" align="center">
		
		<input type="hidden" name="update" value="true">
		<input type="submit" name="submit" value="Submit" align="center" onClick="return doSubmit()" <%=lController.getEditMode() %>>
		<input type="reset" value="Reset" <%=lController.getEditMode() %>>
		<INPUT type=button value=Back onClick="backBtn()">
		
		</TD>
	</TR>

</TABLE>

</FORM>
<form name="Back" method="post"></form>    
</body>
</html>