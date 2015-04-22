<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gisil.mcds.config.Configuration"%>
<%@ page import="com.gisil.mcds.web.controller.ui.ConfigurationManagementController" %>
<% ConfigurationManagementController lController = new ConfigurationManagementController(request, response);%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Configuration Details</title>
</head>
<link href="indepay.css" rel="stylesheet" type="text/css">
<script language="javaScript" type="text/javascript">

function openEditWindow( rowID )
{
  	window.open("suiEditConfiguration.jsp?popup=true&rowid="+rowID, "mywindow", "height=200,width=500");	
}

function back()
	{
		cfgMgt.action="<%=response.encodeRedirectURL( "suiMCDSMenu.jsp")%>";
		cfgMgt.submit();
	}

function doAction( actionValue )
{
	var answer = true;
	if ( actionValue == 'Apply' )
		answer = confirm('Are you sure you want to apply your changes?');
	if ( actionValue == 'Undo' )
		answer = confirm('Are you sure you want to discard your changes');	
	
	if ( answer == true )
	{	
		document.getElementById('btnApply').style.visible="hidden";
		cfgMgt.action="<%=response.encodeURL( "configMgtServlet" )%>" + "?&action="+actionValue;
		cfgMgt.submit();
	}
	return answer;	
}

</script>

<body>
<FORM name="cfgMgt" method="post">
<table width="70%" cellPadding="0" cellSpacing="0" align="center">
	<TR>
		<TH height="50">
		<h3><u>MCDS - System Parameters</u></h3>
		</TH>
	</TR>
	<% if ( lController.isModified() ) { %>
	
	<tr>
		<td height="52">
		<table bordercolor="#ff0000" width="100%" align="center" border="2">
			<tbody>
				<tr>
					<td align="middle" height="30" color="#330099"><br />
					<div align="center" id="doaction"><strong> Some data has been changed. Need to apply or Undo</strong> <br />
					<br />
					<input name="btnApply" type="button" onclick="doAction('Apply');" value=" Apply Changes " /> &nbsp; 
					<input name="btnUndo" type="button" onclick="doAction('Undo'); return true;" value=" Undo Changes " />
					</div>
					<br />
					</td>
				</tr>

			</tbody>
		</table>
		</td>
	</tr>
	<% } %>

	<%ArrayList list = lController.getConfigurationDataList();
			Iterator iterator = list.iterator();
			String oldCategory = null;

			while (iterator.hasNext()) {
				Configuration cfg = (Configuration) iterator.next();
				String newCategory = cfg.getParamCategory();

				if (!newCategory.equals(oldCategory)) {
					if (oldCategory != null)
						out.write("</table>			</td>		</tr>");
					oldCategory = newCategory;

					%>
	<TR>
		<TD height="30" align="center"><strong><%=newCategory%></strong></TD>
	</TR>
	<TR>
		<TD>
		<table width="100%" border="1" cellpadding="0" cellspacing="0">
			<tr align="center">
				<td width="2%"></td>
				<td width="2%"></td>
				<td width="46%"><strong>Parameter Name</strong></td>
				<td width="56%"><strong>Parameter Value</strong></td>
				<td width="8%"><strong>Action</strong></td>
			</tr>
			<%}%>
			<tr align="center">
			<%  if ( cfg.isChanged() ) { %>
				<TD align="center" style="color: red; font-size:large;">*</td>
				<% } else { %>
				<TD align="center" style="color: red; font-size:large;">&nbsp;&nbsp;&nbsp;</td>
				<% } %>
				<TD align="center">&nbsp;<img title="<%=cfg.getDescription() %>" style="CURSOR: hand" src="question_mark.jpg"
					width="12" height="16" />&nbsp;</TD>
				<td align="left"><%=cfg.getParamName()%></td>
				<% 
				String paramValue = null;
				
				if ( !cfg.isChanged() ) 
				{
					paramValue = cfg.getParamValue();
				}
				else
				{
					paramValue = cfg.getParamNewValue();
				}
				%>
				<td align="left"><%=paramValue%></td>
				<TD align="center"><input name="button2" type="button" onclick="javascript:openEditWindow(<%=cfg.getRowId()%>)"  value="Edit" <%=lController.getEditMode() %>/></TD>
			</tr>
			<%}

		%>
		</table>
		</TD>
	</TR>
	<tr>
		<td height="20" />
	</tr>
	<TR>
		<TD align="center">
		<table bordercolor="#ff0000" width="100%" align="center" border="2" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" height="30" color="#330099"><img title="" style="CURSOR: hand" src="question_mark.jpg" width="12"
					height="16" /> <strong>Take the mouse pointer to this symbol to get information about the property as tool tip.</strong>
				</td>
			</tr>
			<tr>
				<td align="center"><input name="button3" type="button" onclick="back()" value="  Back  " /></td>
			</tr>
		</table>
		</TD>
	</TR>

</table>
</FORM>
</body>
</html>
