/*
 * Copyright, GISIL 2006 All rights reserved. The copyright in this work is
 * vested in GISIL and the information contained herein is confidential. This
 * work (either in whole or in part) must not be modified, reproduced, disclosed
 * or disseminated to others or used for purposes other than that for which it
 * is supplied, without the prior written permission of GISIL. If this work or
 * any part hereof is furnished to a third party by virtue of a contract with
 * that party, use of this work by such party shall be governed by the express
 * contractual terms between the GISIL which is a party to that contract and the
 * said party.
 */

package com.gisil.mcds.web.filter;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gisil.mcds.util.LogUtil;

/**
 * Filter requests from WAP(Klondike Browser) end
 * @author ajit singh
 *
 */
public class WapSecurityFilter implements Filter {
	private Logger _log;

	private ServletContext _context;

	public static String MCDS_STOP_PARAM = "StopMcds";
	
	public static final String DEFAULT_AGG_ID = "1";

	/**
	 * init
	 */
	public void init(FilterConfig config) throws ServletException {
		_context = config.getServletContext();

	}

	/**
	 * 
	 *WapSecurityFilter
	 */
	public WapSecurityFilter() {
		_log = LogUtil.getLogger(WapSecurityFilter.class);
	}

	/**
	 * doFilter
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		String m_session = httpRequest.getParameter("msession");
		String delNumber = httpRequest.getParameter("merchantPhone");
		String aggregatorId = httpRequest.getParameter( "aggregatorId");
		
		
		if ( aggregatorId == null )
		{
			// Current We are Using  Default Aggregator Id. 
			// In future Aggregator Id actually depends on User ( Which aggregator he select ) 
			aggregatorId = DEFAULT_AGG_ID;
			delNumber = "9822222222";
			session = httpRequest.getSession(true);
			session.setAttribute("m_session", m_session);
			session.setAttribute("merchantPhone", delNumber);
			session.setAttribute("merchantName", "");
			session.setAttribute("aggregatorId",aggregatorId);
		}

		if (m_session != null && m_session.length() > 0) {
			_log.info("valid mcds session " + m_session);

			if (checkForStopped()) {
				_log.info("mcds Service Found to be Stopped.");
				request.setAttribute("dontShowHomeMenu", "true");
				request.setAttribute("errorMsg",
						"Service currently not available.");
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("error.jsp");
				dispatcher.forward(request, response);
				return;
			}
			if (session == null && m_session != null) {
				session = httpRequest.getSession(true);
				session.setAttribute("m_session", m_session);
				session.setAttribute("merchantPhone", delNumber);
				session.setAttribute("merchantName", "");
				session.setAttribute("aggregatorId",aggregatorId);
			}
		}
		_log.info("mcds sessionId- " + m_session + "   session-" + session
				+ " merchant Phone :" + delNumber);
		/*if (session == null || session.getAttribute("m_session") == null) {
			RequestDispatcher dispatcher = httpRequest
					.getRequestDispatcher("error.jsp");
			httpRequest
					.setAttribute("errorMessage",
							"Session is not valid. Please try to access this resource through SUI context.");
			httpRequest.setAttribute("dontShowHomeMenu", "true");
			dispatcher.forward(httpRequest, httpResponse);
			return;
		}*/

		filterChain.doFilter(request, response);

	}

	/**
	 * checkForStopped
	 * @return boolean
	 */
	private boolean checkForStopped() {
		Object o = _context == null ? null : _context
				.getAttribute(MCDS_STOP_PARAM);
		if (o == null)
			return false;

		if (!(o instanceof Boolean))
			return false;

		Boolean stopped = (Boolean) o;
		return stopped.booleanValue();
	}

	/**
	 * destroy
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
