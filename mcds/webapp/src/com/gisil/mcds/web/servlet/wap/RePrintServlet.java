package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.PurchaseTrxResponse;
import com.gisil.mcds.ejb.MCDSRemote;

import com.gisil.mcds.web.controller.wap.TransactionStatusController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

public class RePrintServlet extends MCDSServiceServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7136700876691751768L;
	
	public static final String SERVLET_PATH = "reprintServlet";

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		String backPageUrl = "reprint.jsp";

		String transactionId = request.getParameter("trxId");

		if (transactionId == null || transactionId == "") {
			message = "InSufficient Input";
			goToErrorPage(request, response, message, backPageUrl);
		} else {
			try {
				Integer trxId = new Integer(transactionId);
				MCDSRemote remote = getMCDSRemote();
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher(TransactionStatusController.PAGE_PATH );
				PurchaseTrxResponse trxResponse = remote
						.loadTransactionDetails(trxId);

				if (trxResponse == null || trxResponse.getSkuCode() == null
						|| trxResponse.getSkuCode().equals("")) {
					message = ErrorMessages.RECORD_NOT_FOUND;
					goToErrorPage(request, response, message, backPageUrl);
				} else {
					request.setAttribute("trxResponse", trxResponse);
					requestDispatcher.forward(request, response);
				}
			} catch (NumberFormatException nfe) {
				goToErrorPage(request, response, ErrorMessages.INVALID_TRX_ID, backPageUrl);
			} catch (MCDSException mcdsExp) 
			{
				String errorMessage = mcdsExp.toString();
				
				if ( errorMessage != null && errorMessage.toLowerCase().indexOf( "no such" ) != -1 )
				{
					// In future we will read message from .COnfig file
					errorMessage = ErrorMessages.INVALID_TRX_ID;
				}
				goToErrorPage(request, response, errorMessage, backPageUrl);
				_log.info( "MCDS Exception Occured " +mcdsExp.toString() );
			}
		}
	}
}
