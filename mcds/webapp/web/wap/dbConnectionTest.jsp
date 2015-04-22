<%@page import="com.gisil.mcds.web.controller.wap.DBConnectionTestController"%>


<%
	new DBConnectionTestController( request, response );
	String message = (String)request.getAttribute("message");
%>
<%=message%>
