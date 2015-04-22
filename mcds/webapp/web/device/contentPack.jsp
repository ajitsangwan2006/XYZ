<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.ContentPackController" %>
<%@ page import="com.gisil.mcds.web.servlet.wap.SubContentPackServlet" %>
<%@ page import="com.gisil.mcds.dmi.PackTypeListInfo" %>
<%@page import="java.util.Iterator" %>
<%
ContentPackController lController = new ContentPackController(request,response);
Iterator iter = lController.getList().iterator();
int total = lController.getList().size();
String str = request.getParameter("start");
if(str==null)
str="0";
int start = Integer.parseInt(str);
int end = start + Math.min(total, lController.getMaxRecords());
end = Math.min(total, end);
%>
<gml ver=1.0>
<card id="Pack Types" title="--- <%=lController.getTitle() %> ---">
<do type="lsk" label="Back">
<%
if(start == 0)
{
%>
<go href="<%=response.encodeURL(lController.getBackURL())%>" >
</go>
<%
}else{
%>
<go href="<%=response.encodeURL(ContentPackController.PAGE_PATH)%>" method="post">
<postfield name="start" value="<%=(start - lController.getMaxRecords())%>"/>
</go>
<%
}
%>
</do>
<do type="rsk" label="Select">
</do>
<p>
<select>
<%
int _counter=start+1;
for(int loop = 0; loop<start; loop++)
	iter.next();
while(iter.hasNext()){
	PackTypeListInfo info = (PackTypeListInfo)iter.next();
%>
<option>
	<onevent type="onpick"> 
	<go href="<%=response.encodeURL(SubContentPackServlet.SERVLET_PATH)%>" method="post"> 
	<postfield name="type" value="<%=info.getValue()%>"/>
	</go>
	</onevent> 
<%=""+_counter+". "+ info.getDisplayName()%>
</option>
<%
if(_counter >= end)
	break;
_counter = _counter+1;
}
if (end < total) 
{
%>
<option>
	<onevent type="onpick"> 
	<go href="<%=response.encodeURL("contentPack.jsp")%>" method="post"> 
	<postfield name="start" value="<%=end%>"/>
	</go>
	</onevent>
More...
</option>
<%
}
%>
</select>
</p>
</card>
</gml>
