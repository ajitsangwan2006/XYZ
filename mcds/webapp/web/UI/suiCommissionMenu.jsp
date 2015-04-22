<%@ page language="java" contentType="text/html;charset=UTF-8"%>


<html>
<head>
<title>Commission Menu</title>
<style type="text/css">
<!--
.style4 {color: #000000}
-->
</style>
</head>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<form name="commMenu" action="suiMCDSMenu.jsp" method="post" >

<table width="100%" border=0 cellPadding=0 cellSpacing=0>
	<tr align="center">
		<td colspan="2" height="50">
		<h3 class="style4"><u>Commission Menu</u></h3>		</td>
	</tr>	
	<tr>
		<td Width="45%"></td>
		<td align="left">
			<a href="<%=response.encodeURL("classSurchageServlet?action=view")%>">Class Surcharge</a>
		</td>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left">
		<a href="CommFormulaServlet?action=view&rowId=0">Commission Formula</a>
		</td>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left">
		<a href="<%=response.encodeURL("PartnerMgmtServlet?action=view")%>">Partner</a>
		</td>
	</tr>
	<tr>
		<td Width="45%"></td>
		<td align="left">
		<a href="CommFormulaMappingServlet?action=view&rowId=0">Commission Formula Mapping</a>
		</td>
	</tr>
	<tr >
		<td align="center" colspan="2" height="50">
			<input type="submit" value="Back" />
		</td>
	</TR>
</table>
</form>
</body>
</html>
