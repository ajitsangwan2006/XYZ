<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.ContentSearchController" %>

<%ContentSearchController contentSearchController = new ContentSearchController(request,response);%>

<wml>

<card id="Search" title="--- <%=contentSearchController._title %> ---">

<do type="lsk" label="Back">

<go href="<%=response.encodeURL("searchBy.jsp")%>" />


</do>

<do type="rsk" label="Select">

<go href="<%=response.encodeURL(contentSearchController._url)%>" method="post">

<postfield name="contentInfo" value="$(contentInfo)" />


</go>

</do>
<p>
<%=contentSearchController._type %>:<input name="contentInfo" maxlength="17"/> <br />
<br/>
</p>
<p>
Enter at least 3<br/>
<%=contentSearchController._displayString %>
</p>

</card>

</wml>
