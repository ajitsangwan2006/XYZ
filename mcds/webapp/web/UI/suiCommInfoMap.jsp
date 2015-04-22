<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="java.util.*" %>

<HTML>
<HEAD>
<TITLE>SUI</TITLE>
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
</script>
<FORM name=formName method=post>
<%
//////
%>
<table width="100%" height="100%" align ="center">
<tr><th height="50"><u> <h3> Transaction Commission - <%=request.getParameter("trxId") %> </h3></u> </th></tr>
<tr><td>
<TABLE width="100%" align="center" border="1">
	<tr>
		<th height="30" width="50%" >Partner Id</th>
		<th height="30" width="50%" >Commission</th>
	</tr>
	<%
	
	Map<Integer, Number> partnerCommissionMap = ( Map<Integer, Number> )request.getSession().getAttribute( "partnerCommMap");
	if ( partnerCommissionMap != null )
	{
		for ( Map.Entry< Integer, Number > pair : partnerCommissionMap.entrySet() )
		{
			%>
			<TR>		
		<TD>
			<%=pair.getKey()%>
		</TD>
		<TD>
		<%=pair.getValue()%>
		</TD>
		</TR>
	<%
	} 
     }
%>
<tr>
		<td align="center" height="30" colspan="2" >
		<input type="button" name="Button" value="Close" onclick="goHome()">
		</td>
	</tr>
</TABLE>
</td></tr></table>
</FORM>
</BODY>
</HTML>