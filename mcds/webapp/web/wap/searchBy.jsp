<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.SearchByController" %>

<%new SearchByController(request,response);%>

<wml>

<card id="Search By" title="--- Search By ---">

<do type="lsk" label="Back">

<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.MCDSMenuServlet.SERVLET_PATH) %>" />


</do>

<do type="rsk" label="Select">


</do>

<p>


<select>
<option>
	<onevent type="onpick"> 
		<go href="<%=response.encodeURL("contentSearch.jsp")%>" method="post">
			<postfield name="searchBy" value="title" />
			
		</go>
	</onevent> 
1.  Content Title
</option>
<option>
	<onevent type="onpick"> 
		<go href="<%=response.encodeURL("contentSearch.jsp")%>" method="post">
			<postfield name="searchBy" value="code" />
			
		</go>
	</onevent> 
2. Content Code
</option>
<option>
	<onevent type="onpick"> 
		<go href="<%=response.encodeURL("contentSearch.jsp")%>" method="post">
			<postfield name="searchBy" value="category" />
			
		</go>
	</onevent> 
3. Content Category
</option>

</select>

</p>

</card>

</wml>
