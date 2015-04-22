package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.ContentDetailsForPurchaseInfo;
import com.gisil.mcds.dmi.PurchaseTrxRequest;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.util.CommonUtil;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

public class PreTransactionServlet extends MCDSServiceServlet {
	/**
	 * serialVersionUID = 1611337572682921515L
	 * SERVLET_PATH = "PreTransactionServlet"
	 */
	private static final long serialVersionUID = 1611337572682921515L;
	public static final String SERVLET_PATH = "PreTransactionServlet";

	@Override
	protected void processIt(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		Transaction trx = (Transaction)aRequest.getSession().getAttribute("trx");
		aRequest.getSession().removeAttribute("trx");
		MCDSRemote _remote = getMCDSRemote();
		PurchaseTrxRequest ptr = new PurchaseTrxRequest();
		ptr.setContentId(Integer.parseInt(aRequest.getParameter("contentId")));
		if(getUserSessionData(aRequest).getEntryPoint().equals("top"))
			ptr.setType(ContentDetailsForPurchaseInfo.ITEM);
		else
			ptr.setType(ContentDetailsForPurchaseInfo.PACK);
		ptr.setMsisdn(aRequest.getParameter("mobile").trim());
		ptr.setDelNo(getMerchantDelNo(aRequest));
		trx.setDeliveryMode(CommonUtil.toDeliveryMode(aRequest.getParameter("deliver_by")));
		
		try{
			trx = _remote.submitPurchaseRequest(ptr, trx);
			aRequest.getSession().setAttribute("trx",trx);
			dispatcher = aRequest.getRequestDispatcher("confirmDetail.jsp");
		}catch(MCDSException mcdsexp){
			_log.info("Error : "+mcdsexp.getMessage());
			aRequest.setAttribute("contentId",aRequest.getParameter("contentId"));
			aRequest.setAttribute("errorMessage",mcdsexp.getMessage());
			aRequest.setAttribute("backUrl",ConfirmDetailServlet.SERVLET_PATH);
			dispatcher = aRequest.getRequestDispatcher("error.jsp");
		}catch(Exception exp){
			_log.info("Error : "+exp.getMessage());
			aRequest.setAttribute("errorMessage",ErrorMessages.SERVER_NOT_READY);
			aRequest.setAttribute("backUrl",ConfirmDetailServlet.SERVLET_PATH);
			dispatcher = aRequest.getRequestDispatcher("error.jsp");
		}
		
		dispatcher.forward(aRequest,aResponse);

	}

}
