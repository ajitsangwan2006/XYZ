<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.ContentSearchController" %>
<%ContentSearchController contentSearchController = new ContentSearchController(request,response);%>
<gml ver=1.0>
<card id="Search" title="--- <%=contentSearchController._title %> ---">
<do type="lsk" label="Back">
<go href="<%=response.encodeURL("searchBy.jsp")%>">
</go>
</do>
<do type="rsk" label="Select">
<go href="<%=response.encodeURL(contentSearchController._url)%>" method="post">
<postfield name="contentInfo" value="$(contentInfo)"/>
</go>
</do>
<p>
<%=contentSearchController._type %>: <input name="contentInfo" maxlength="17"/><br/>

Enter at least 3<input name="text1" maxlength="0"/><br/>
<%=contentSearchController._displayString %><input name="text2" maxlength="0"/>
</p>
</card>
</gml>
