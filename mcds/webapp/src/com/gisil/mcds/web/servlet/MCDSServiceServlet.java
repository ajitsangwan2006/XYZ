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

package com.gisil.mcds.web.servlet;
import java.io.IOException;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import weblogic.management.mbeanservers.edit.ValidationException;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.util.AppServer;
import com.gisil.mcds.util.LogUtil;
import com.gisil.mcds.web.controller.wap.ErrorController;
import com.gisil.mcds.web.filter.UISecurityFilter;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.SessionData;
import com.gisil.mcds.web.util.Utils;

/**
 * Common Service servlet to be extended by all 
 * servlets 0implementing its processIt method
 * @author
 *
 */
public abstract class MCDSServiceServlet extends HttpServlet {

    protected Logger _log;

    private MCDSRemote _remote;

    /**
     * @TODO document me
     */
    public MCDSServiceServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        _log = LogUtil.getLogger(getClass());

        super.init(config);
        _log.info("servlet " + getClass() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processIt(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        processIt(request, response);
    }

    protected abstract void processIt(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    protected final void forwardTo(HttpServletRequest request, HttpServletResponse response, String to)
            throws ServletException, IOException {
        to = response.encodeURL(to);

        RequestDispatcher dispatcher = request.getRequestDispatcher(to);
        dispatcher.forward(request, response);
    }

    protected void handleException(HttpServletRequest request, HttpServletResponse response, Throwable error)
            throws ServletException, IOException {
        if (error.getClass() != ValidationException.class)
            _log.log(Level.SEVERE, "Exception occured while servicing", error);
        String errorMsg = error.getMessage();
        request.setAttribute("errorMsg", errorMsg);
        forwardTo(request, response, ErrorMessages.ERROR_PAGE);
    }

    protected String getMerchantDelNo(HttpServletRequest request) {
       String delNo = null;
        if (request.getSession().getAttribute("merchantPhone") != null)
            delNo = (String)request.getSession().getAttribute("merchantPhone");
        else
            throw new IllegalArgumentException("del number not found in session..");
        try {
            delNo = Utils.convertToInternationalFormat(delNo);
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid del no found in session " + delNo);
        }
        return delNo;
    }

    protected SessionData getUserSessionData(HttpServletRequest request) {
        return (SessionData) request.getSession().getAttribute("sessionData");
    }
    

    private String getUserId(HttpServletRequest request) {
        String userId = null;
        HttpSession _session = request.getSession();
        if (request.getParameter("userId") != null)
            userId = request.getParameter("userId");
        else if (request.getAttribute("userId") != null)
            userId = (String) request.getAttribute("userId");
        else if (_session.getAttribute("userId") != null)
            userId = request.getParameter("userId");
        else
            userId = "9999";
        return userId;
    }

    protected AuditTrail createAuditTrail(HttpServletRequest request) {
        AuditTrail trail = new AuditTrail();
        trail.setUserId(getUserId(request));
        trail.setTs(new Timestamp(System.currentTimeMillis()));
        trail.setIpAddress(request.getRemoteAddr());

        return trail;
    }

    protected void submitAuditTrail(AuditTrail trail) {
      try {
         _remote.submitAuditTrail(trail);
       } catch (RemoteException e) {
           _log.warning("Audit trail failed " + e.getMessage());
        }
    }

   
    // code by me//
    protected final void includeResponse(HttpServletRequest request, HttpServletResponse response, String whom)
            throws ServletException, IOException {
        whom = response.encodeURL(whom);

        RequestDispatcher dispatcher = request.getRequestDispatcher(whom);
        dispatcher.include(request, response);
    }

    protected void handleUIException(HttpServletRequest request, HttpServletResponse response, Throwable error)
            throws ServletException, IOException {
        _log.log(Level.SEVERE, "Exception occured while servicing", error);
        String errorMsg = error.getMessage();
        request.setAttribute("errorMsg", errorMsg);
        includeResponse(request, response, "error.jsp");
    }

    protected MCDSRemote getMCDSRemote() {
        if (_remote == null) {
            try {
                _remote = AppServer.getAppServer(getServletContext()).getMCDSServer();
            } catch (Throwable e) {
                throw new RuntimeException("Unable to connect with Application Server");
            }
        }
        return _remote;
    }
    
    public void goToErrorPage( HttpServletRequest request, HttpServletResponse response, String  message, String backUrl ) throws ServletException, IOException
	{
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(ErrorController.PAGE_PATH );
		request.setAttribute( "backUrl", backUrl );
		request.setAttribute( "errorMessage", message);
		requestDispatcher.forward( request, response );		
	}
    
    protected Integer getPrevelageLevel(HttpServletRequest request) {
        Integer prevelageLevel = 0;
        HttpSession _session = request.getSession();
        try{
        	if (request.getParameter(UISecurityFilter.PRIVILAGE_LEVEL) != null)
        		prevelageLevel = Integer.parseInt(request.getParameter(UISecurityFilter.PRIVILAGE_LEVEL));
        	else if (request.getAttribute(UISecurityFilter.PRIVILAGE_LEVEL) != null)
        		prevelageLevel = Integer.parseInt(request.getAttribute(UISecurityFilter.PRIVILAGE_LEVEL).toString());
        	else if (_session.getAttribute(UISecurityFilter.PRIVILAGE_LEVEL) != null)
        		prevelageLevel = Integer.parseInt(request.getParameter(UISecurityFilter.PRIVILAGE_LEVEL));
        }catch(NumberFormatException exp){
        	prevelageLevel = 0;
        }
        return prevelageLevel;
    }

}
