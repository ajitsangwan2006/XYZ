<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<wml>

<card id="reprint" title="--- ReSend Status ---">

<do type="lsk" label="Back">
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.MCDSMenuServlet.SERVLET_PATH)%>"/>
</do>
Resend Successfull<br/>
Trx Id: <%=request.getParameter("trxId") %>
</do>
</card>
</wml>