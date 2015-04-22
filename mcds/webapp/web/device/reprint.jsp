<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.gisil.mcds.web.servlet.wap.RePrintServlet"%>
<%@ page import="com.gisil.mcds.web.servlet.wap.MCDSMenuServlet"%>
<gml ver=1.0>
<card id="reprint" title="--- Reprint ---">
<do type="lsk" label="Back">
<go href="<%=response.encodeURL( MCDSMenuServlet.SERVLET_PATH )%>"/>
</do>
<do type="rsk" label="Submit">
<go href="<%=response.encodeURL(RePrintServlet.SERVLET_PATH)%>" method="post">
<postfield name="trxId" value="$(trxId)"/>
</go>
</do>
<p>
Txn ID :<input name="trxId" type="number"/> <br/>
</card>
</gml>