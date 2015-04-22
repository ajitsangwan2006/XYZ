package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.dmi.PackTypeListInfo;
import com.gisil.mcds.ejb.MCDSRemote;

import com.gisil.mcds.web.controller.wap.ContentPackController;
import com.gisil.mcds.web.controller.wap.ContentSearchController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.SessionData;

public class ContentPackServlet extends MCDSServiceServlet {

	/** serialVersionUID */
	private static final long serialVersionUID = 4441882850392359776L;

	public static final String SERVLET_PATH = "ContentPackServlet";

	private ArrayList<PackTypeListInfo> contents = null;

	@Override
	protected void processIt(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String backURL = null;
		SessionData _sessionData = (SessionData) aRequest.getSession()
				.getAttribute("sessionData");
		if (_sessionData == null) {
			_sessionData = new SessionData();
		}
		_sessionData.setEntryPoint("pack");
		try {			
			MCDSRemote _remote = getMCDSRemote();
			
			//get Max records per page and set it to session
			_sessionData.setMaxRecordsPerPage( _remote.getConfigNumber( MCDSConfig.MAX_CONTENT_DISPLAY ).intValue() );
			
			if (_sessionData.isCategorySearch()) {
				backURL = ContentSearchController.PAGE_PATH;
				if (aRequest.getParameter("contentInfo") != null) {
					contents = _remote.getPackContents(aRequest
							.getParameter("contentInfo"));
					_sessionData.setContentInfo(aRequest
							.getParameter("contentInfo"));
				} else {
					contents = _remote.getPackContents(getUserSessionData(
							aRequest).getContentInfo());
				}
			} else
				contents = _remote.getPackContents(aRequest
						.getParameter("contentInfo"));
			if (contents.size() == 0) {
				aRequest.setAttribute("backUrl", backURL);
				aRequest.setAttribute("errorMessage", ErrorMessages.NO_RECORD_FOUND);
				dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
			} else {
				dispatcher = aRequest.getRequestDispatcher(ContentPackController.PAGE_PATH);
				aRequest.getSession().setAttribute("contents", contents);
			}
			aRequest.getSession().setAttribute("sessionData", _sessionData);
		} catch (Exception e) {
			aRequest.setAttribute("backUrl", backURL);
			aRequest.setAttribute("errorMessage", e.getMessage());
			dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
		}

		dispatcher.forward(aRequest, aResponse);
	}
}
