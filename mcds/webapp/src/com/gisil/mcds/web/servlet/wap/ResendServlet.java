package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.base.MCDSException;

import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.Utils;

public class ResendServlet extends MCDSServiceServlet{

	
	/**serialVersionUID*/
	private static final long serialVersionUID = -5033293780680550228L;
	public static final String SERVLET_PATH = "resendServlet";
	
	@Override
	protected void processIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		String backPageUrl = "resend.jsp";
		String transactionId = request.getParameter("trxId");
		if (transactionId == null || transactionId == "") {
			message = ErrorMessages.INSUFFICIENT_INPUT;
			goToErrorPage(request, response, message, backPageUrl);
		} else {
			try {
				Integer trxId = new Integer(transactionId);
				MCDSRemote remote = getMCDSRemote();
				Boolean resendResponse = remote.resendContentReq(trxId); 
				
				if ( resendResponse == null )
				{
					throw new MCDSException();
				}
				else if (resendResponse.equals(false)) 
				{
					message = ErrorMessages.UNSUCCESS_TRX_ID+":"+" " +trxId;
					goToErrorPage(request, response, message, backPageUrl);
				} else if (resendResponse.equals(true)) {

					// For the time being we are making these messaage static. In future we get message from config
					request.setAttribute("pageTitle", "Successful");
					message = ErrorMessages.SUCCESS_TRX_ID +":"+" " +trxId;
					goToErrorPage(request, response,message , MCDSMenuServlet.SERVLET_PATH );
					
				} 
				
			} catch (NumberFormatException nfe) {
				goToErrorPage(request, response, ErrorMessages.INVALID_TRX_ID, backPageUrl);
			} catch (MCDSException mcdsExp) 
			{
				_log.info( "MCDS Exception Occured " +mcdsExp.toString() );
				goToErrorPage(request, response,mcdsExp.getMessage(), backPageUrl);
				
			}
		}
	}


}
