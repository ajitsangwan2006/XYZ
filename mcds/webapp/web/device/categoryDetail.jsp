<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.CategoryDetailController" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%
CategoryDetailController categoryController = new CategoryDetailController(request,response);
Set set= categoryController._contents.entrySet();
Iterator iter = set.iterator();
%>
<gml ver=1.0>
<card id="Ring Tones" title="--- <%=categoryController._currentTitle%> ---">
<do type="lsk" label="Back">
<go href="<%=response.encodeURL("categoryDetail.jsp")%>">
<postfield name="contentId" value="<%=categoryController._backId%>"/>
</go>
</do>
<do type="rsk" label="Select">
</do>
<p>
<select>
	<%
	int _counter=1;
	Map.Entry me;
	while(iter.hasNext()){
		me = (Map.Entry)iter.next();
	%>
	<option>
	<onevent type="onpick"> 
	<go href="<%=response.encodeURL(categoryController._nextPage)%>" method="post"> 
	<postfield name="contentId" value="<%=(String)me.getKey()%>"/>
	</go>
	</onevent> 
	<%=""+_counter+". "+(String)me.getValue()%>
	</option>
	<%
	_counter = _counter+1;
	}
	%>
</select>
</p>
</card>
</gml>
