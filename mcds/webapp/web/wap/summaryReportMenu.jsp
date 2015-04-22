<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.SummaryReportMenuController" %>
<%@ page import="com.gisil.mcds.web.servlet.wap.MCDSMenuServlet"%>
<%@ page import="com.gisil.mcds.web.servlet.wap.SummaryReportMenuServlet"%>

<% SummaryReportMenuController controller = new SummaryReportMenuController( request, response); %>
<wml>
<card id ="selection" title="--- Summary Report ---">
<do type ="lsk" label="Back">
<go href="<%=response.encodeURL(MCDSMenuServlet.SERVLET_PATH)%>"/>
</do>
<do type="rsk" label="Select"></do>
<p>
<select>
<option>
<onevent type="onpick">
<go href="<%=response.encodeURL( SummaryReportMenuServlet.SERVLET_PATH )%>" method = "post">
<postfield name="summaryReportType" value="last"/>
<postfield name="limit" value="<%=controller.getLimit()%>"/>
</go>
</onevent>
 Last <%=controller.getLimit()%> Trx
</option>
<option>
<onevent type="onpick">
<go href="<%=response.encodeURL( SummaryReportMenuServlet.SERVLET_PATH )%>" method = "post">
<postfield name="summaryReportType" value="date"/>
</go>
</onevent>
 Date Range
</option>
</select>
</p>
</card>
</wml>