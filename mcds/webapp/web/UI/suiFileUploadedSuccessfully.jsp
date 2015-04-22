<html>
<head>
<title>Upload Successfull</title>
<style type="text/css">
</style>
</head>
<link href="indepay.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function goBack()
{
	document.successForm.action="<%=response.encodeURL("suiCronjobsMenu.jsp")%>";
	document.successForm.submit();
}

</script>
<body>
<form name="successForm">
<table width="70%" cellPadding="0" cellSpacing="0" align="center">
<tr><td>&nbsp;</td>
</tr>
<tr><td>&nbsp;</td>
</tr>
	<tr>
		<td height="52">
		<table bordercolor="#ff0000" width="100%" align="center" border="2">
			<tbody>
				<tr>
					<td align="middle" height="30" color="#330099"><br />
					<div align="center" id="doaction"><strong> File Uploaded Successfully</strong> <br />
					<br />
					<input name="btnback" type="button" onClick="goBack();" value="Back">
					</div>
					<br />
					</td>
				</tr>

			</tbody>
		</table>
		</td>
	</tr>
	</table>
	</form>
</body>
</html>