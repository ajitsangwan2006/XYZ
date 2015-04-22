<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="com.gisil.mcds.web.controller.ui.SuiEditConfigurationController" %>
<% 
	SuiEditConfigurationController lController = new SuiEditConfigurationController(request, response );
%>
<HTML>
<HEAD>
<TITLE>Edit Configuration</TITLE>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2900.2180" name=GENERATOR>
</HEAD>
<link href="indepay.css" rel="stylesheet" type="text/css">
<BODY width="100%" height="100%" bgcolor="#EBEBDA">
<script language="JavaScript" type="text/javascript">
function goHome()
{
	window.close();
}


function validateForm( paramValue, paramType )
{
	if ( paramType == 'Integer' )
	{
		if ( isNumberInteger( paramValue ) )
		{
			return true;
		}
		else
		{
		   return false;
		}
	}
	else 
	{
		return true;		
	}

}

function isNumberInteger(val){
	  
      if(val==null){return false;}
      if (val.length==0){return false;}
      for (var i = 0; i < val.length; i++) {
            var ch = val.charAt(i);
            if (i == 0 && ch == "-") {
                  continue;
            }           
            if (ch < "0" || ch > "9") {
                  return false;
            }
      }
      return true;
}


function submitForm( rowid, paramType )
{
	paramValue = editForm.paramvalue.value;
	var result = validateForm(paramValue, paramType );
	if ( result )
	{
		var message = "Configuration has been successfully saved. ";
		alert( message );
		window.opener.location="<%=response.encodeURL( "configMgtServlet" )%>" + "?paramvalue=" +paramValue + "&rowid=" +rowid + "&action=edit";
		window.close();
		
	} else
	{
		var message = "Number input is required for parameter value";
		alert( message );
	}

}


</script>
<%
	String rowId = request.getParameter( "rowid" );
	String paramName = lController.getParamName( rowId );
	String paramValue = lController.getParamValue( );
	String paramType = lController.getParamType( );
%>
<FORM name="editForm" method="post">
<table width="70%" height="137" border="0" align="center" cellpadding="0" cellspacing="0">
<tr bgcolor="#336699"><td height="45" align="center"><b>Edit System Parameter</b></td></tr>
<tr><td>
<TABLE width="100%" align="center" border="0">
	<tr>
	<td>Param Name</td><td><input maxlength="100" name=paramname disabled="disabled" value="<%=paramName%>" > </td>
	</tr>
	<tr>
	<td>Param Value</td>
	<% if ( paramType != null && paramType.equals( "Boolean") ) { %>
	<td>
		<select name="paramvalue">
		<option value="true">true</option>
		<option value="false">false</option>
		</select>
	</td>
	<% } else { %>
	<td><input maxlength="100" name=paramvalue  value="<%=paramValue%>" > </td>
	</tr>
	<% } %>
	<tr>
				<td height="55" colspan="2">
				<div align="center">
					<input type="button" name="btnSave" value="Save" onclick="submitForm(<%=rowId%>, '<%=paramType%>');" /> &nbsp;
					<input type="button" name="btnCancel" value="Cancel" onclick="goHome();"/>
				</div>
				</td>
			</tr>
</TABLE>
</td></tr></table>
</FORM>
</BODY>
</HTML>