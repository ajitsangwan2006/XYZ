package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.wap.CustomerDetailController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;

public class CustomerDetailServlet extends MCDSServiceServlet {

	private static final long serialVersionUID = -8396907210440239877L;
	public static final String SERVLET_PATH = "CustomerDetailServlet";

	/**
	 * process the request and response
	 */
	@Override
	protected void processIt(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		aRequest.getSession().removeAttribute("contentList");
		aRequest.getSession().removeAttribute("backType");
		aRequest.getSession().removeAttribute("isEmptyContentName");
		aRequest.getSession().removeAttribute("isEmptyContent");
		aRequest.getSession().removeAttribute("contentName");
		aRequest.getSession().removeAttribute("contentId");
		try{
		MCDSRemote _remote = getMCDSRemote();
		if (!getUserSessionData(aRequest).getEntryPoint().equals("search"))
			aRequest.setAttribute("backId", _remote.getBackId(aRequest
					.getParameter("contentId")));
		RequestDispatcher dispatcher = aRequest
				.getRequestDispatcher(CustomerDetailController.PAGE_PATH);
		dispatcher.forward(aRequest, aResponse);
		}catch(Exception exp){
			//go to error page
		}

	}

}
