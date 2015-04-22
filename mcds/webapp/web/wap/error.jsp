<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.ErrorController" %>
<%
ErrorController lController = new ErrorController( request, response ); 
%>
<wml>
<card id="error" title="---<%=lController.getPageTitle()%>---">
<do type="lsk" label="Back">
<go href="<%=response.encodeURL(lController._backPageurl )%>" method="post">
<postfield name="contentId" value="<%=lController._contentId %>" />
</go>
</do>
<p> Message </p><br />
<%=lController._errorMessage%>
</card>
</wml>