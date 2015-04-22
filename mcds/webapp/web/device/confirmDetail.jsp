<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.ConfirmDetailController" %>
<%@ page import="com.gisil.mcds.web.util.Utils" %>
<%ConfirmDetailController confirmController = new ConfirmDetailController(request,response);
%>
<gml ver=1.0>
<card id="Ring Tones" title="--- Confirm ---">
<do type="lsk" label="Back">
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.CustomerDetailServlet.SERVLET_PATH)%>" >
<postfield name="contentId" value="<%=confirmController._contentId%>"/>
</go>
</do>
<%if(confirmController._retry > 0){ %>
<do type="rsk" label="Retry">
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.TransactionServlet.SERVLET_PATH)%>" method="post">
<postfield  name="contentId" value="<%=confirmController._contentId %>" />
<postfield  name="retry" value="<%=confirmController._retry %>" />
</go>
</do>
<%}
else{%>
<do type="rsk" label="Buy">
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.TransactionServlet.SERVLET_PATH)%>" method="post">
<postfield  name="contentId" value="<%=confirmController._contentId %>" />
<postfield  name="retry" value="<%=confirmController._retry %>" />
</go>
</do>
<% }%>
<p>
<%=confirmController._title %><br/>
Code: <%=confirmController._code%><br/>
Price: <%=Utils.decimalFormat(Double.parseDouble(confirmController._price))%><br/>
<%=confirmController._make%> Model <%=confirmController._model%><br/>
</p>
</card>
</gml>