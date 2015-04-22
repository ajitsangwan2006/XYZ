<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.SummaryReportDetailController" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.gisil.mcds.util.CommonUtil" %>
<%@ page import="com.gisil.mcds.web.servlet.wap.SummaryReportServlet"%>
<%
SummaryReportDetailController summaryReportDetailController = new  SummaryReportDetailController(request, response );
int totalTrx =  summaryReportDetailController.getFormatedTransactionListToDisplay().size();
int limit = summaryReportDetailController.getMaxContentDisplayLimit();
String start = request.getParameter( "start" );
if ( start == null )
{
	start = "0";
}
int startIndex = Integer.parseInt( start );
int endIndex = startIndex + Math.min( totalTrx, limit );
endIndex = Math.min( totalTrx, endIndex );
%>
<wml>
<card id="summaryReportDetail" title="--- Summary Report Detail ---">

<do type="lsk" label="Back">
<% 
	if ( startIndex == 0 && summaryReportDetailController.getReportType().equals("last") )
	{
%>
	<go href="<%=response.encodeURL(SummaryReportServlet.SERVLET_PATH)%>"/>
<%
	}
	else if ( startIndex == 0 && summaryReportDetailController.getReportType().equals("date") )
	{
%>
<go href="<%=response.encodeURL("dateRangeInputForm.jsp")%>"/>
<% 
	}
	else if ( startIndex != 0 )
	{
%>
<go href="<%=response.encodeURL( SummaryReportDetailController.PAGE_PATH )%>">
<postfield name="start" value="<%=startIndex - limit%>"/>
</go>
<% } %>
</do>

<%
	if ( endIndex < totalTrx  ) 
	{
%>
<do type="rsk" label="More...">
<go href="<%=response.encodeURL(SummaryReportDetailController.PAGE_PATH )%>">
<postfield name="start" value="<%=endIndex%>"/>
<postfield name="type" value="<%=summaryReportDetailController.getReportType()%>"/>
</go>
</do>
<%
	}
	else
	{
		if ( summaryReportDetailController.getTransactionList().size() > 0 )
		{
%>
<do type="rsk" label="Print">
<print>
<prn>
        INDEPAY<br/><br/>
Dt:<%=summaryReportDetailController.getCurrentDate()%><br/>
TID:<%=CommonUtil.fixMobileNo(request.getSession().getAttribute("merchantPhone").toString())%><br/><br/>
      SUMMARY REPORT<br/><br/><br/>
#  TxnID  #Txn Dt Status<br/>
<%
	ArrayList <String> transactionList = summaryReportDetailController.getFormatedTransactionListForPrinter();
	Iterator iterator = transactionList.iterator();
	while ( iterator.hasNext() )
	{
	  String info =  ( String ) iterator.next();
%>
<%=info%><br/>
<% } %>

<br/>
<br/>
**Thank You for Using**<br/>
       *INDEPAY*
       <br/>
       <br/>
       <br/>
       <br/>
       <br/>
       <br/>
       <br/>
</prn>
</print>
</do>
<%
	}
	}
%>
<br/>
<p>
<select>
<option>#  TxnID   #Txn Dt   Status</option>
	<%
		ArrayList<String> formatedDataList  = summaryReportDetailController.getFormatedTransactionListToDisplay();
		for ( int index = startIndex; index < endIndex; index++ )
		{
	%>
<option><%=formatedDataList.get( index )%></option>
	<%}%>
</select>
</p>
</card>
</wml>
