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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;
import com.gisil.mcds.util.LogUtil;

/**
 * Filter requests from UI end
 * 
 * @author ajit singh
 * 
 */
public class UISecurityFilter implements Filter {

	private Logger _log;

	/**
	 * @TODO document me
	 */
	public static final String SESSION_ID = "mcdsSessionId";

	public static final String USER_ID = "userId";

	public static final String PRIVILAGE_LEVEL = "userPrivilagesLevel";

	private ServletContext _context;

	private boolean isTestFlag = true;

	public UISecurityFilter() {
		_log = LogUtil.getLogger(UISecurityFilter.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		_log.info(" - " + getClass() + "'s init called");
		_context = config.getServletContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (checkForStopped()) {
			_log.info("MCDS Service Found to be Stopped.");
			request.setAttribute("errorMsg",
					"MCDS Service Found to be Stopped.");
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);
			return;
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (isTestFlag) {
			request.setAttribute(PRIVILAGE_LEVEL, 2);
			request.setAttribute(USER_ID, "vinay");
			request.setAttribute("isTestFlag", isTestFlag );
			
			_log.info("Flag is true");

			chain.doFilter( req, res );	
		} else {
			res.sendRedirect("/suinew/loginsui");

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	private boolean checkForStopped() {
		Object o = _context == null ? null : _context
				.getAttribute("StopHermes");
		if (o == null)
			return false;

		if (!(o instanceof Boolean))
			return false;

		Boolean stopped = (Boolean) o;
		return stopped.booleanValue();
	}

}
