<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.SubContentPackController" %>
<%@ page import="com.gisil.mcds.aggregator.mauj.entity.ContentDetailTO" %>
<%@ page import="com.gisil.mcds.web.servlet.wap.ContentPackDetailServlet" %>
<%
ContentDetailTO cdto = null;
SubContentPackController lController = new SubContentPackController(request, response);
int total = lController.getContents().size();
String str = request.getParameter("start");
if(str==null)
str="0";
int start = Integer.parseInt(str);
int end = start + Math.min(total, lController.getMaxRecords());
end = Math.min(total, end);

%>
<gml ver=1.0>

<card id="Content Packs" title="--- <%=lController.getTitle() %> ---">

<do type="lsk" label="Back">
<%if(start == 0)
    {%>
<go href="<%=response.encodeURL(lController.getBackURL())%>" >
</go>
<%}else{
%>
<go href="<%=response.encodeURL(SubContentPackController.PAGE_PATH)%>" method="post">
<postfield name="start" value="<%=(start - lController.getMaxRecords())%>"/>
<postfield name="type" value="<%=lController.getType()%>"/>
</go>
<%} %>
</do>
<p>
<select>

	<%
	for (int loop = start; loop < end; loop++)
	{
			cdto = lController.getContents().get(loop);
	%>
<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL(ContentPackDetailServlet.SERVLET_PATH)%>" method="post"> 
<postfield name="contentId" value="<%=cdto.getCode()%>"/>
<postfield name="contentName" value="<%=cdto.getTitle()%>"/>
</go>
</onevent> 
	<%=""+(loop+1)+". "+cdto.getTitle()%>
</option>
	<%

	}
	if (end < total) {
		 
	%>
<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL(SubContentPackController.PAGE_PATH)%>" method="post"> 
<postfield name="type" value="<%=lController.getType()%>"/>
<postfield name="start" value="<%=end%>"/>
</go>
</onevent>
More...
</option>

<%
}
	if(lController.getContents().size() <= 0)
	{
%>
<option>
No Record Found
</option>
<%
	}
%>

</select>

</p>
<%if(lController.getContents().size() > 0)
{ %>
<do type="rsk" label="Select">
</do>
<%} %>

</card>

</gml>
