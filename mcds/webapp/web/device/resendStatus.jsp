<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<gml ver=1.0>

<card id="reprint" title="--- ReSend Status ---">

<do type="lsk" label="Back">
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.MCDSMenuServlet.SERVLET_PATH)%>"/>
</do>
<p>
Resend Successfull<br/>
Trx Id: <%=request.getParameter("trxId") %>
</p>
</do>
</card>
</gml>