<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.servlet.wap.DateRangeInputFormServlet"%>
<%@ page import="com.gisil.mcds.web.servlet.wap.SummaryReportServlet"%>

<gml ver=1.0>
<card id="Search" title="--- Summary Report ---">
<do type="lsk" label="Back">
<go href="<%=response.encodeRedirectURL(SummaryReportServlet.SERVLET_PATH)%>"/>
</do>
<do type="rsk" label="Submit">
<go href="<%=response.encodeURL( DateRangeInputFormServlet.SERVLET_PATH )%>" method="post">
<postfield name="fromDate" value="$(fromDate)" />
<postfield name="toDate" value="$(toDate)" />
</go>
</do>
<p>
From Dt(ddmmyy):<input name="fromDate" maxlength="6" type="number"/><br/>
To Dt(ddmmyy):<input name="toDate" maxlength="6" type="number"/><br/>
</card>
</gml>
