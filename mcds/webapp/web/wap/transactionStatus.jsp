<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.controller.wap.TransactionStatusController" %>
<%@ page import="com.gisil.mcds.web.servlet.wap.MCDSMenuServlet"%>
<%@ page import="com.gisil.mcds.util.CommonUtil" %>
<%TransactionStatusController lController = new TransactionStatusController(request,response);
%>
<wml>
<card id="Ring Tones" title="--- Transaction Status ---">
<do type="lsk" label="Menu">
<go href="<%=response.encodeURL(MCDSMenuServlet.SERVLET_PATH)%>">
</go>
</do>
<%if (lController._statusToDisplay
					.equalsIgnoreCase("Delivered")) {
%>
<do type="rsk" label="Print">
<print>
<prn>
      Merchant Copy<br/>
      -------------<br/>
         INDEPAY<br/><br/>
    TID:<%=CommonUtil.fixMobileNo(request.getSession().getAttribute("merchantPhone").toString())%>
        <br/><br/><br/>
 Dt:<%=lController._date%><br/>
 Trx Id  :<%=lController._trxId%><br/><br/>
     MOBILE CONTENT<br/><br/>
 Status:<%=lController._statusToDisplay%><br/>
 TxnID:<%=lController._trxId%><br/>
 Content:<%=lController._content%><br/>
 Code:<%=lController._skuCode%>
  <% 
	 if ( lController.isCMSCharge() ) 
	 {
		 double price = lController._price - lController._cmsValue;
		 double total = price + lController._cmsValue;
 %><br/>
 Price:Rs.<%=price%><br/>
 <%=lController._cmsName%>: <%=lController._cmsValue%><br/>
 Total:Rs.<%=total%>
 <%  } %>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
      Customer Copy<br/>
      -------------<br/>
         INDEPAY<br/><br/>
    TID:<%=CommonUtil.fixMobileNo(request.getSession().getAttribute("merchantPhone").toString())%>
        <br/><br/><br/>
 Dt:<%=lController._date%><br/>
 Trx Id  :<%=lController._trxId%><br/><br/>
     MOBILE CONTENT<br/><br/>
 Status:<%=lController._statusToDisplay%><br/>
 TxnID:<%=lController._trxId%><br/>
 Content:<%=lController._content%> <br/>
 Code:<%=lController._skuCode%>
   <% 
	 if ( lController.isCMSCharge() ) 
	 {
		 double price = lController._price - lController._cmsValue;
		 double total = price + lController._cmsValue;
 %><br/>
 Price:Rs.<%=price%><br/>
 <%=lController._cmsName%>: <%=lController._cmsValue%><br/>
 Total:Rs.<%=total%>
 <%  } %>

        <br/>
        <br/>
       Thank You<br/>
 ***for using Indepay***
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
   	</prn>
	</print>
</do>
<p>
<%=lController.getDisplayStrForTerminal( "Status: " + lController._statusToDisplay )%><br/>
<%=lController.getDisplayStrForTerminal( "TxnID: "+ lController._trxId )%><br/>
<%=lController.getDisplayStrForTerminal( "Content: " +lController._content)%><br/>
<%=lController.getDisplayStrForTerminal( "Code: " +lController._skuCode)%><br/>
<%=lController.getDisplayStrForTerminal( "Price: Rs. " +lController._price)%>
</p>
<%} else {

				%>
 <p><br/>
 <%=lController._statusToDisplay%>
 </p>
 <%}

  %>
</card>
</wml>