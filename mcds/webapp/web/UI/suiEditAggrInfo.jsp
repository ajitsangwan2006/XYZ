<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.ui.SuiEditAggrInfoController" %>
<%@ page import="com.gisil.mcds.web.servlet.ui.AggrListServlet" %>

<%	SuiEditAggrInfoController lController = new SuiEditAggrInfoController(request,response);	%>

<html>
<link href="indepay.css" rel="stylesheet" type="text/css">   
<head><title>Edit Aggregator Info</title>
<script language="javascript" type="text/javascript">

function clearForm() {
   document.getElementsByName('agrName')[0].value ='';
   document.getElementsByName('url')[0].value ='';
   document.getElementsByName('description')[0].value ='';
   document.getElementsByName('userName')[0].value ='';
   document.getElementsByName('password')[0].value ='';

}

function checkForm() 
{
	//javascript functionality to check the form
}
</script>
</head>
<body>
<FORM name="editagrinfo" method="post" action="UpdateAggrInfoServlet" onsubmit="return checkForm()"> 
<TABLE width="500" hcellSpacing=0 cellPadding=0 align=center>
	<tr>
		<th colspan="2" height="50"><h4><u>Edit Aggregator Info</u></h4></th>
	</tr>

	<TR>
		<TD height="25">Aggregator Name :</TD>
		<TD height="25">
			<INPUT type="text" name="agrName" maxlength="256" size="30" value="<%=lController.getAggregatorName() %>" disabled >
		</TD>
	</TR>
	
	<TR>
		<TD height="25">Web Url :</TD>
		<TD height="25">
			<INPUT type="text" name="url" maxlength="256" value="<%=(( lController.getWebURL() != null ) ? lController.getWebURL() : "")%>" size="30" <%=lController.getEditMode() %>>
		</TD>
	</TR>
	
	<TR>
		<TD height="25">Description :</TD>
		<TD height="25"><textarea rows="4" cols="29" name="description" <%=lController.getEditMode()%>><%=(( lController.getDescription() != null ) ? lController.getDescription() : "")%></textarea> </TD>
	</TR>

	<TR>
		<TD height="25">Username :</TD>
		<TD height="25"><INPUT type="text" name="userName" maxlength="512" value="<%=(( lController.getUser() != null ) ? lController.getUser() : "")%>"  size="30" <%=lController.getEditMode() %>></TD>
	</TR>

	<TR>
		<TD height="25">Password :</TD>
		<TD height="25"><INPUT type="password" name="password" maxlength="512" value="<%=(( lController.getPassword() != null) ? lController.getPassword() : "")%>"  size="30" <%=lController.getEditMode() %>></TD>
	</TR>
	
	<tr><td>Status :</td>
		<td height="25">
		<select name="status" STYLE="width: 160px">
		<option value="ENABLED">Enable</option>
		<option value="DISABLED">Disable</option>
		</select>
		</td>
		<tr>
		<td height="25" align="center"></td>
	</tr>
	
	<TR>
	
		<TD height="50" colspan="2" align="center">
		<input type="hidden" name="aggId" value="<%=request.getParameter("aggId") %>" align="center">
		<input type="hidden" name="aggName" value="<%=lController.getAggregatorName() %>" align="center">
		<input type="submit" value="Submit" align="center" <%=lController.getEditMode() %> >
		<input type="Reset" value="Reset" <%=lController.getEditMode() %> >
		<INPUT type=submit value=Back onClick=(action="<%=response.encodeURL(AggrListServlet.SERVLET_PATH )%>")>
		</TD>
	</TR>
</TABLE>
</FORM>    
</body>
</html>
