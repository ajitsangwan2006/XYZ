<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.ContentDetailController" %>
<%@ page import="com.gisil.mcds.web.util.Utils" %>
<%@ page import="com.gisil.mcds.web.servlet.wap.CustomerDetailServlet" %>

<%
ContentDetailController lController = new ContentDetailController(request,response);
int total = lController._codeList.size();
String str = request.getParameter("start");
if(str==null)
str="0";
int start = Integer.parseInt(str);
log("New Start =" + start);
int end = start + Math.min(total, lController.getMaxRecords());
end = Math.min(total, end);
%>

<wml>
<card id="Content Details" title="--- <%=lController._currentTitle%> ---">
<do type="lsk" label="Back">
<%if(start == 0)
    {%>
<go href="<%=response.encodeURL(lController._returnToPagePath)%>" method="post">
<postfield name="contentId" value="<%=lController._backId%>"/>
<postfield name="type" value="<%=lController._backType%>"/>
</go>
<%}else{
   %>
<go href="<%=response.encodeURL(lController._loopUrl)%>" method="post">
<postfield name="start" value="<%=(start - lController.getMaxRecords())%>"/>
<postfield name="contentId" value="<%=lController._categoryId%>"/>
</go>
<%} %>
</do>

<do type="rsk" label="Select">
<go href="<%=response.encodeURL(CustomerDetailServlet.SERVLET_PATH)%>" method="post"> 
<postfield name="contentId" value="<%=lController._categoryId%>"/>
</go>
</do>

<%if(lController._isEmptyContentName)
{ 
 %>
<p>

<select>
<option>
# Code
</option>
<%
for (int loop = start; loop < end; loop++)
	{
%>
<option>
. <%=lController._codeList.get(loop)%>
</option>
<%
}
if (end < total) {
%>
<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL(lController._loopUrl)%>" method="post">
<postfield name="contentId" value="<%=lController._categoryId%>"/>
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
<% 
}else
{
    
String contentTitle = null;%>
<p>
<select>
<option>
# <%=Utils.convertToContentString("Title","Code")%>
</option>
<%
	for (int loop = start; loop < end; loop++)
	{
		contentTitle=Utils.convertToContentString((String)lController._nameList.get(loop),(String)lController._codeList.get(loop));
%>
<option>
. <%=contentTitle%>
</option>
<%
	}

	if (end < total) {
%>
<option>
<onevent type="onpick"> 
<go href="<%=response.encodeURL(lController._loopUrl)%>" method="post">
<postfield name="contentId" value="<%=lController._categoryId%>"/>
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
<%
}
%>

</card>
</wml>