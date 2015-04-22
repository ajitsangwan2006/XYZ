package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gisil.mcds.aggregator.mauj.entity.ContentDetailTO;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.wap.ContentSearchController;
import com.gisil.mcds.web.controller.wap.SubContentPackController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.SessionData;

public class SubContentPackServlet extends MCDSServiceServlet {

	/** serialVersionUID */
	private static final long serialVersionUID = 8464378037192443025L;

	public static final String SERVLET_PATH = "SubContentPackServlet";

	@Override
	protected void processIt(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {

		String backPageUrl = null;

		try {
			MCDSRemote _remote = getMCDSRemote();
			ArrayList<ContentDetailTO> contents = null;
			RequestDispatcher dispatcher = null;
			SessionData _sessionData = (SessionData) aRequest.getSession()
					.getAttribute("sessionData");
			if (_sessionData == null) {
				_sessionData = new SessionData();
			}
			// get Max records per page and set it to session
			_sessionData.setMaxRecordsPerPage(10);
			
			if ( _sessionData.getMaxRecordsPerPage() == 0 )
			{
				_sessionData.setMaxRecordsPerPage( _remote.getConfigNumber( MCDSConfig.MAX_CONTENT_DISPLAY ).intValue() );
			}
			if (getUserSessionData(aRequest).getEntryPoint().equals("search")) {
				if (aRequest.getParameter("contentInfo") != null) {
					if (aRequest.getParameter("contentInfo").length() >= _remote
							.getConfigNumber(MCDSConfig.SEARCH_TEXT_MIN_LIMIT)
							.intValue()) {
						if (getUserSessionData(aRequest).isCategorySearch())
							contents = _remote.getSearchPackContents(aRequest
									.getParameter("contentInfo"));
						else
							contents = _remote.getSearchPackContents(aRequest
									.getParameter("contentInfo"),
									getUserSessionData(aRequest).getSearchBy());
						getUserSessionData(aRequest).setContentInfo(
								aRequest.getParameter("contentInfo"));
					} else {
						dispatcher = aRequest
								.getRequestDispatcher(ContentSearchController.PAGE_PATH);
					}
				} else {
					if (getUserSessionData(aRequest).getContentInfo().length() >= _remote
							.getConfigNumber(MCDSConfig.SEARCH_TEXT_MIN_LIMIT)
							.intValue())
						if (getUserSessionData(aRequest).isCategorySearch())
							contents = _remote
									.getSearchPackContents(getUserSessionData(
											aRequest).getContentInfo());
						else
							contents = _remote.getSearchPackContents(
									getUserSessionData(aRequest)
											.getContentInfo(),
									getUserSessionData(aRequest).getSearchBy());
					else
						dispatcher = aRequest
								.getRequestDispatcher(ContentSearchController.PAGE_PATH);
				}

			} else if (getUserSessionData(aRequest).getEntryPoint().equals(
					"pack")) {
				contents = _remote.getSubPackContents(aRequest
						.getParameter("type"));
			}
			if (contents == null) {
				if (getUserSessionData(aRequest).getEntryPoint().equals("pack")) {
					backPageUrl = ContentPackServlet.SERVLET_PATH;
					goToErrorPage(aRequest, aResponse, ErrorMessages.NO_RECORD_FOUND,
							backPageUrl);
				} else if (getUserSessionData(aRequest).getEntryPoint().equals(
						"search")) {
					goToErrorPage(aRequest, aResponse, ErrorMessages.INSUFFICIENT_INPUT,
							ContentSearchController.PAGE_PATH);
				}

			} else {
				dispatcher = aRequest
						.getRequestDispatcher(SubContentPackController.PAGE_PATH);
				aRequest.getSession().setAttribute("contents", contents);
				dispatcher.forward(aRequest, aResponse);
			}

		} catch (Exception exp) {
			_log.info("Exception Occured :" + exp.toString());
			backPageUrl = MCDSMenuServlet.SERVLET_PATH;
			goToErrorPage(aRequest, aResponse, exp.getMessage(), backPageUrl);
		}

	}

}
