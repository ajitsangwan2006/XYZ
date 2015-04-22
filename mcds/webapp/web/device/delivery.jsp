<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.DeliveryController" %>
<%DeliveryController deliveryController = new DeliveryController(request,response);
request.setAttribute("trx",request.getAttribute("trx"));
%>
<gml ver=1.0>
<card id="Ring Tones" title="--- Delivery By ---">
<do type="lsk" label="Back">
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.CustomerDetailServlet.SERVLET_PATH)%>" >
<postfield name="contentId" value="<%=deliveryController._contentId%>"/>
<postfield name="retry" value="<%=deliveryController._retry%>"/>
</go>
</do>

<do type="rsk" label="Submit">
</do>
<p>
Needs Delivery By<br/>
</p>
<p>
<select>
<%java.util.Iterator itr = deliveryController._deliveryArr.iterator(); 
String nextValue = null;
while(itr.hasNext()){
nextValue = (String)itr.next();
%>
<option>
	<onevent type="onpick"> 
		<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.PreTransactionServlet.SERVLET_PATH)%>" method="post">
			<postfield name="deliver_by" value="<%=nextValue %>"/>
			<postfield name="contentId" value="<%=deliveryController._contentId%>"/>
			<postfield name="mobile" value="<%=deliveryController._mobile%>"/>
			<postfield name="retry" value="<%=deliveryController._retry%>"/>
		</go>
	</onevent> 
.  <%=nextValue %>
</option>
<%} %>
</select>
</p>
</card>
</gml>
