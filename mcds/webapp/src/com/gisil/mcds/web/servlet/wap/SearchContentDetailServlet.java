package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.aggregator.mauj.entity.ContentDetailTO;
import com.gisil.mcds.ejb.MCDSRemote;

import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.SessionData;

/**
 * 
 * @author ajit singh
 * 
 */
@SuppressWarnings("serial")
public class SearchContentDetailServlet extends MCDSServiceServlet {

	/**
	 * process the request and response
	 */
	public static final String SERVLET_PATH = "SearchContentDetailServlet";
	@Override
	protected void processIt(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		if (aRequest.getParameter("contentInfo").length() < 3) {
			dispatcher = aRequest.getRequestDispatcher("contentSearch.jsp");
		} else {
			MCDSRemote _remote = getMCDSRemote();

			SessionData sessionData = (SessionData) aRequest.getSession()
					.getAttribute("sessionData");
			ContentDetailTO cdto = new ContentDetailTO();

			if (aRequest.getParameter("contentInfo") != null) {
				sessionData
						.setContentInfo(aRequest.getParameter("contentInfo"));
				aRequest.getSession().setAttribute("sessionData", sessionData);
			}
			cdto = _remote.getSearchContentDetails(sessionData.getSearchBy(),
					sessionData.getContentInfo());
					
			if (cdto.getContents() == null) {
				dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
				aRequest.setAttribute("error", ErrorMessages.EMPTY_CONTENT_LIST);

			} else {
				dispatcher = aRequest.getRequestDispatcher("contentDetail.jsp");
				aRequest.setAttribute("contentsList", cdto.getContents());
				aRequest.setAttribute("isEmptyContentName", cdto
						.isEmptyContentName());
				aRequest.setAttribute("isEmptyContent", cdto.isEmptyContent());
			}
		}
		dispatcher.forward(aRequest, aResponse);

	}

}
