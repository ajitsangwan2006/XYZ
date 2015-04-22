<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<wml>

<card id="reprint" title="--- Resend ---">

<do type="lsk" label="Back">
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.MCDSMenuServlet.SERVLET_PATH)%>"/>
</do>

<do type="rsk" label="Submit">
<go href="<%=response.encodeURL(com.gisil.mcds.web.servlet.wap.ResendServlet.SERVLET_PATH)%>" method="post">
<postfield name="trxId" value="$(trxId)"/>
</go>
</do>
<p>
Txn ID :<input name="trxId"/> <br />
</card>
</wml>