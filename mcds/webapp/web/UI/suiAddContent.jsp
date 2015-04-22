
<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<html>
<head>
<title>Add Catagory Page</title>

<script language="javascript" type="text/javascript">
function backBtn()
{
document.suiAddContent.action="suiTop10Menu.jsp";
document.suiAddContent.submit();
return true;
}
</script>

</head>
<link href="indepay.css" rel="stylesheet" type="text/css">
<body>
<br><br>
<form name="suiAddContent" action="SuiAddCatagory.do" method="post">
<table width="60%" border="1" align="center" cellpadding="0" cellspacing="0">
	<tr hieght="25">
		<td width="20%">Content Title/Name</td>
		<td width="80%"><input type="text" name="catagoryName" size="24"></td>
	</tr>
	<tr height="25">
		<td width="20%">Content Description</td>
		<td width="80%"><textarea rows="4" cols="50"> Enter description </textarea></td>
	</tr>

	<tr height="50">
			
		<td align="center" colspan="2">
		<input type="button" name="Back" value="Back" onClick="backBtn();">
		<input type="submit" name="save_continue" value="Save and Continue">
		</td>
	
	</tr>
</table>
</form>
</body>
</html>
