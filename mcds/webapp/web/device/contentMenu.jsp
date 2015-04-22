<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.ContentMenuController" %>
<%@ page import="com.gisil.mcds.web.servlet.wap.SummaryReportServlet"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator" %>

<%
	ContentMenuController contentMenuController = new ContentMenuController(request,response); 
	int counter = 0;
%>
<gml ver=1.0>
<card id="Ring Tones" title="--- Content Menu ---">
<do type="lsk" label="Back">
<go href="/themis/device/DisplayMenu">
<postfield name="merchantPhone" value="$(T_merchantPhone)"/>
</go>
</do>
<do type="rsk" label="Select">
</do>
<p>
<select>
<%
	HashMap<String, String> contentMenuMap = contentMenuController._menuContentList;
	Set keySet = contentMenuMap.keySet();
	Iterator iterator = keySet.iterator();
	
	while (  iterator.hasNext() )
	{
		String contentId = ( String )iterator.next();
		String itemName = contentMenuMap.get( contentId );
	  if ( itemName.startsWith( "Top" ) )
	  {
%>
<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.TopContentServlet.SERVLET_PATH)%>" method="post">
<postfield name="contentId" value="<%=contentId%>"/>
</go>
</onevent>
<%=++counter%>. <%=itemName%></option>
<%
}
else if ( itemName.indexOf( "Content Packs") != -1 )
{
%>
<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.ContentPackServlet.SERVLET_PATH)%>" method="post"> </go>
</onevent><%=++counter%>. <%=itemName%></option>
<%
}
} 
 %>
<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL("searchBy.jsp")%>" method="post"> 
</go>
</onevent><%=++counter%>. Search Content
</option>
<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL("reprint.jsp")%>" method="post"> 
</go>
</onevent><%=++counter%>. Reprint
</option>
<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL(SummaryReportServlet.SERVLET_PATH)%>" method="post"> 
</go>
</onevent><%=++counter%>. Summary Report
</option>

<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL("resend.jsp")%>" method="post">  

</go>
</onevent><%=++counter%>. Resend
</option>

</select>
</p>
</card>
</gml>