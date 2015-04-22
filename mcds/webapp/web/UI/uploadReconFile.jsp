<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.servlet.ui.UploadFileServlet"%>
<html>
<head>
<title>Upload Recon File</title>
<style type="text/css">
</style>
</head>
<link href="indepay.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function goBack()
{
	document.uploadFileForm.action="<%=response.encodeURL("suiCronjobsMenu.jsp")%>";
	document.uploadFileForm.submit();
}
</script>
<body>
<form name="uploadFileForm" action="<%=UploadFileServlet.SERVLET_PATH%>" method="post" ENCTYPE="multipart/form-data">

<table border=0 cellPadding=0 cellSpacing=0 align="center">
	<TR>
		<TH colspan="2" height="50">
		<h4><u>Upload Recon File</u></h4>
		</TH>
	</TR>
	<TR>
		<TD height="25" align="left"><b>Upload File</b></TD>
		<TD height="25" align="left"><INPUT type="file" name="uploadfile" ></TD>
	</TR>
	<TR>
		<TD>&nbsp;</TD>
	</TR>
	<TR>
		<TD>&nbsp;</TD>
	</TR>
	<TR>
		<TD><input type="submit" name="Submit" value="Submit">&nbsp;&nbsp;<input type="reset" name="Reset" value="Reset">&nbsp;&nbsp;<input type="button" name="Back" value="Back" onClick="goBack();">
	<TR>
</table>
</form>
</body>
</html>
