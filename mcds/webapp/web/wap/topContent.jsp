<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%
com.gisil.mcds.aggregator.mauj.entity.TopContentTO TO = null;
com.gisil.mcds.web.controller.wap.TopContentController topContent = new com.gisil.mcds.web.controller.wap.TopContentController(request, response);
int limit = 10;
int total = topContent.getContents().size();
String str = request.getParameter("start");
if(str==null)
str="0";
int start = Integer.parseInt(str);
log("New Start =" + start);
int end = start + Math.min(total, limit);
end = Math.min(total, end);
log("end = "+end);

%>
<wml>

<card id="Ring Tones" title="--- Top 10 Content ---">

<do type="lsk" label="Back">
<%if(start == 0)
    {%>
<go href="<%=response.encodeURL(topContent.getBackUrl())%>" >
<postfield name="parentId" value="<%=topContent.getParentId()%>"/>
</go>
<%}else{
%>
<go href="<%=response.encodeURL("topContent.jap")%>" method="post">
<postfield name="start" value="<%=(start - limit)%>"/>
</go>
<%} %>
</do>

<do type="rsk" label="Select">
</do>

<p>
<select>

	<%
	for (int loop = start; loop < end; loop++)
	{
		TO = (com.gisil.mcds.aggregator.mauj.entity.TopContentTO)topContent.getContents().get(loop);
	%>
<option>
<onevent type="onpick"> 
<%if(TO.getIsParent().equals("1")){ %>
<go href="<%=response.encodeURL(topContent.getBackUrl())%>" method="post"> 
<postfield name="contentId" value="<%=TO.getContentId()%>"/>
<postfield name="parentId" value="<%=topContent.getParentId()%>"/>
</go>
<%}else{ %>
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.CustomerDetailServlet.SERVLET_PATH)%>" method="post"> 
<postfield name="contentId" value="<%=TO.getContentId()%>"/>
<postfield name="parentId" value="<%=topContent.getParentId()%>"/>
</go>
<%} %>
</onevent> 
	<%=""+(loop+1)+". "+TO.getContentName()%>
</option>
	<%

	}
	if (end < total) {
		 
	%>
<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL("topContent.jsp")%>" method="post"> 
<postfield name="start" value="<%=end%>"/>
</go>
</onevent>
More...
</option>

<%
}
	if(topContent.getContents().size() <= 0)
	{
%>
<option>
Empty content list
</option>
<%
	}
%>
</select>
</p>
</card>
</wml>
