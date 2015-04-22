<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.CustomerDetailController" %>
<%CustomerDetailController customerDetailController = new CustomerDetailController(request,response);%>
<wml>
<card id="Ring Tones" title="--- Customer Info ---">
<do type="lsk" label="Back">
<go href="<%=response.encodeURL(customerDetailController._backUrl)%>" >
<%if(customerDetailController._entryPoint.equals("top")){ %>
<postfield name="contentId" value="<%=customerDetailController._parentId%>"/>
<%}else{ %>
<postfield name="contentId" value="<%=customerDetailController._contentId%>"/>
<%} %>
</go>
</do>
<do type="rsk" label="Submit">
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.ConfirmDetailServlet.SERVLET_PATH)%>" method="post">
<postfield name="mobile" value="$(mobile)" />
<postfield name="make" value="$(make)" />
<postfield name="model" value="$(model)" />
<postfield  name="contentId" value="<%=customerDetailController._contentId %>" />
</go>
</do>
<p>
Mobile#:<input name="mobile" type="number" maxlength="10"/><br/>
Make:<input name="make" maxlength="12"/><br/>
Model:<input name="model" maxlength="12"/><br/><br/>
<%if(customerDetailController._error != null){ %>
    <%=customerDetailController._error%><input name="text" maxlength="0"/>
<%} %>
</p>
</card>
</wml>