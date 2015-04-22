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

import com.gisil.mcds.web.controller.wap.ContentDetailController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.SessionData;

public class ContentPackDetailServlet extends MCDSServiceServlet {

	/** serialVersionUID */
	private static final long serialVersionUID = -943179665846164447L;

	public static final String SERVLET_PATH = "ContentPackDetailServlet";

	@Override
	protected void processIt(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		ArrayList contentsList = null;
		SessionData _sessionData = (SessionData) aRequest.getSession()
				.getAttribute("sessionData");
		if (_sessionData == null) {
			_sessionData = new SessionData();
		}

		try {
			MCDSRemote _remote = getMCDSRemote();

			if (_sessionData.getMaxRecordsPerPage() == 0) {
				_sessionData.setMaxRecordsPerPage(_remote.getConfigNumber(
						MCDSConfig.MAX_CONTENT_DISPLAY).intValue());
			}

			ContentDetailTO cdto = new ContentDetailTO();
			aRequest.getSession().removeAttribute("contents");
			cdto = _remote.getContentPackDetails(aRequest
					.getParameter("contentId"));
			contentsList = cdto.getContents();
			if (contentsList.size() == 0) {
				dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
				aRequest.setAttribute("backUrl", "contentmenu");
				aRequest.setAttribute("errorMessage", ErrorMessages.NO_RECORD_FOUND);
			} else {
				aRequest.getSession()
						.setAttribute("contentsList", contentsList);
				aRequest.getSession().setAttribute("backType",
						cdto.getBackType());
				aRequest.getSession().setAttribute("isEmptyContentName",
						cdto.isEmptyContentName());
				aRequest.getSession().setAttribute("isEmptyContent",
						cdto.isEmptyContent());
				aRequest.getSession().setAttribute("contentName",
						cdto.getContentName());
				aRequest.getSession().setAttribute("contentId",
						aRequest.getParameter("contentId"));
				dispatcher = aRequest
						.getRequestDispatcher(ContentDetailController.PAGE_PATH);
			}
		} catch (Exception exp) {
			_log.info("Error : " + exp.getMessage());
			aRequest.setAttribute("errorMessage", ErrorMessages.SERVER_NOT_READY);
			aRequest.setAttribute("backUrl", MCDSMenuServlet.SERVLET_PATH);
			dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
		}
		dispatcher.forward(aRequest, aResponse);

	}

}
