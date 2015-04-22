<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.SummaryController" %>
<%SummaryController summaryReportController = new SummaryController(request,response);%>
<wml>
<card id="Search" title="--- Summary Report ---">
<do type="lsk" label="Back">
<go href="contentMenu.jsp" />
</do>
<do type="rsk" label="Select">
<go href="summaryReport.jsp" method="post">
<postfield name="fromDate" value="$(fromDate)" />
<postfield name="toDate" value="$(toDate)" />
</go>
</do>
<p>
From Dt(ddmmyy):<input name="fromDate" maxlength="6"/> <br />
To Dt(ddmmyy):<input name="toDate" maxlength="6"/> <br/>
</p>
<%if(summaryReportController._error!=null){ %>
<p>
<%=summaryReportController._error%>
</p>
<%}%>
</card>
</wml>
