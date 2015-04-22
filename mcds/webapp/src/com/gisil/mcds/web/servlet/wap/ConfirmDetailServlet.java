package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.web.controller.wap.DeliveryController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.Utils;

public class ConfirmDetailServlet extends MCDSServiceServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1573819692008670919L;
	public static final String SERVLET_PATH = "ConfirmDetailServlet";

	@Override
	protected void processIt(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		boolean _isMobileNo = false;
		RequestDispatcher dispatcher = null;
		Transaction trx = null;
		ArrayList deliveryMode = null;
		
		try{
			_isMobileNo = Utils.isMobileNumber(aRequest.getParameter("mobile"));
		}catch(Exception e){
			_log.info("Error : "+e.getMessage());
			aRequest.setAttribute("contentId",aRequest.getParameter("contentId"));
			aRequest.setAttribute("errorMessage",e.getMessage());
			aRequest.setAttribute("backUrl","CustomerDetailServlet");
			dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
		}
		
		if (!_isMobileNo || aRequest.getParameter("mobile").equals("")) {
			aRequest.setAttribute("contentId",aRequest.getParameter("contentId"));
			aRequest.setAttribute("errorMessage",ErrorMessages.MOBILE_NOT_VAILD);
			aRequest.setAttribute("backUrl","CustomerDetailServlet");
			dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
		}else if(aRequest.getParameter("make").equals(""))
		{
			aRequest.setAttribute("contentId",aRequest.getParameter("contentId"));
			aRequest.setAttribute("errorMessage",ErrorMessages.MAKE_NOT_VAILD);
			aRequest.setAttribute("backUrl","CustomerDetailServlet");
			dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
		}else if(aRequest.getParameter("model").equals(""))
		{
			aRequest.setAttribute("contentId",aRequest.getParameter("contentId"));
			aRequest.setAttribute("errorMessage",ErrorMessages.MODEL_NOT_VAILD);
			aRequest.setAttribute("backUrl","CustomerDetailServlet");
			dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
		}else{
			try{
				MCDSRemote _remote = getMCDSRemote();
				String make = getFirstFourChar( aRequest.getParameter("make").trim().replaceAll("/","-").replaceAll(".","_") );
				trx = _remote.confirmDetail( make, aRequest.getParameter("model").trim().replaceAll("/", "-").replaceAll(".","_"));
				deliveryMode = _remote.getDeliveryMode();
				_log.info("ConfirmDetailServlet Model: "+aRequest.getParameter("model")+" ContentId :"+aRequest.getParameter("contentId"));
				aRequest.setAttribute("trx",trx);
				aRequest.setAttribute("deliveryMode",deliveryMode);
				dispatcher = aRequest.getRequestDispatcher(DeliveryController.PAGE_PATH);
			}catch(MCDSException mcdsexp){
				_log.info("Error : "+mcdsexp.getMessage());
				aRequest.setAttribute("contentId",aRequest.getParameter("contentId"));
				aRequest.setAttribute("errorMessage",mcdsexp.getMessage());
				aRequest.setAttribute("backUrl","CustomerDetailServlet");
				dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
			}
			catch(Exception exp){
				_log.info("Error : "+exp.getMessage());
				aRequest.setAttribute("contentId",aRequest.getParameter("contentId"));
				aRequest.setAttribute("errorMessage",ErrorMessages.SERVER_NOT_READY);
				aRequest.setAttribute("backUrl","CustomerDetailServlet");
				dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
			}	
		}
		dispatcher.forward(aRequest,aResponse);
		

	}
	/**
	 * this method return first four char of incoming data ( if data length more than 4 char )
	 * @param amake
	 * @return
	 */
	private String getFirstFourChar( String amake )
	{
		StringBuffer make = new StringBuffer();
		final int MAX_CHAR_COUNT = 4;
		
		if ( amake != null )
		{
			int length = amake.length();
			
			if ( length <= 4 )
			{
				make.append( amake );
			}
			else
			{
				for ( int count = 0; count < MAX_CHAR_COUNT; count++ )
				{
					make.append( amake.charAt( count ) );
				}
			}
		}
		
		return make.toString();
	}

}
