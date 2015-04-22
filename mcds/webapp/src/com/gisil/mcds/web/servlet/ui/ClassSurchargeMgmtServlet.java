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

package com.gisil.mcds.web.servlet.ui;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.commission.ClassSurcharge;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.web.controller.ui.SuiAddSurchargeController;
import com.gisil.mcds.web.controller.ui.SuiEditSurchargeController;
import com.gisil.mcds.web.controller.ui.SuiMessageController;
import com.gisil.mcds.web.controller.ui.SuiViewSurchargeController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

/**
 * Services suiViewClassSurcharge.jsp Services suiEditClassSurcharge.jsp
 * Services
 */

public class ClassSurchargeMgmtServlet extends MCDSServiceServlet {

    /** serialVersionUID */
    private static final long serialVersionUID = -6731907984317090790L;

    public static final String SERVLET_PATH = "classSurchageServlet";

    @Override
    protected void processIt(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        AuditTrail auditTrail = createAuditTrail(request);
        auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
        
        String action = request.getParameter("action");
        try {
            MCDSRemote _remote = getMCDSRemote();
            if (action != null && action.equals("view")) {
                ArrayList<ClassSurcharge> surchargeList = _remote.getClassSurcharge();
                auditTrail.setAdditionalInfo2("Here got Surcharge Info");
                RequestDispatcher rd = request.getRequestDispatcher(SuiViewSurchargeController.PAGE_PATH);
                request.setAttribute("surchargeList", surchargeList);
                rd.include(request, response);
                submitAuditTrail(auditTrail);
            } else if (action != null && action.equals("edit")) {
                int aggId = Integer.parseInt(request.getParameter("aggId"));
                String classCode = request.getParameter("classCode");
                ClassSurcharge info = _remote.getClassSurcharge(classCode, aggId);
                auditTrail.setAdditionalInfo2("Here got Surcharge Info to edit");
                RequestDispatcher rd = request.getRequestDispatcher(SuiEditSurchargeController.PAGE_PATH);
                request.setAttribute("surcharge", info);
                rd.include(request, response);
                submitAuditTrail(auditTrail);
            } else if (action != null && action.equals("add")) {
                ClassSurcharge surcharge = new ClassSurcharge();

                String surchargeStr = request.getParameter("surcharge");

                if (request.getParameter("classCode") == "")
                    throw new MCDSException("Class Code Can Not Be Null");

                if (request.getParameter("className") == "")
                    throw new MCDSException("Class Name Can Not Be Null");

                if (surchargeStr == "")
                    throw new MCDSException("Surcharge Can Not Be Null");
                if (surchargeStr.contains(".")) {
                    if (surchargeStr.substring(surchargeStr.indexOf("."), surchargeStr.length()).length() > 3)
                        throw new MCDSException("Only 2 Digits Allowed After decimal in Surcharge");
                }

                surcharge.setClassCode(request.getParameter("classCode"));
                surcharge.setClassName(request.getParameter("className"));
                surcharge.setAggregatorName(request.getParameter("agg"));

                String surType = request.getParameter("surchargeType");
                surcharge.setSurchargeFixed("Fixed".equals(surType));
                surcharge.setSurcharge(Double.valueOf(surchargeStr));

                String status = request.getParameter("status");

                if (status.equals("0")) {
                    surcharge.setStatus(DBUtil.ENABLED);
                } else {
                    surcharge.setStatus(DBUtil.DISABLED);
                }

                _remote.addSurchrage(surcharge);
                auditTrail.setAdditionalInfo2("Here added Surcharge Info");
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute(SuiMessageController.R_MESSAGE, "Class Code '" + request.getParameter("classCode")
                        + "' Inserted Successfully");
                request.setAttribute(SuiMessageController.R_BACK_URL, SuiAddSurchargeController.BACK_URL);
                rd.include(request, response);
                submitAuditTrail(auditTrail);
            } else if (action != null && action.equals("save")) {
                ClassSurcharge surcharge = new ClassSurcharge();
                
                String surchargeStr = request.getParameter("surcharge");
                
                if (request.getParameter("className") == "")
                    throw new MCDSException("Class Name Can Not Be Null");

                if (request.getParameter("surcharge") == "")
                    throw new MCDSException("Surcharge Can Not Be Null");
                
                if (surchargeStr == "")
                    throw new MCDSException("Surcharge Can Not Be Null");
                if (surchargeStr.contains(".")) {
                    if (surchargeStr.substring(surchargeStr.indexOf("."), surchargeStr.length()).length() > 3)
                        throw new MCDSException("Only 2 Digits Allowed After decimal in Surcharge");
                }
                
                surcharge.setClassCode(request.getParameter("classCode"));
                surcharge.setClassName(request.getParameter("className"));
                surcharge.setAggregatorName(request.getParameter("agg"));
                surcharge.setSurchargeFixed("Fixed".equals(request.getParameter("surchargeType")));
                surcharge.setSurcharge(Double.valueOf(surchargeStr));

                String status = request.getParameter("status");

                if (status.equals("0")) {
                    surcharge.setStatus(DBUtil.ENABLED);
                } else {
                    surcharge.setStatus(DBUtil.DISABLED);
                }

                _remote.saveSurchargeValues(surcharge);
                auditTrail.setAdditionalInfo2("Here updated Surcharge Info");
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute(SuiMessageController.R_MESSAGE, "Class Code '" + request.getParameter("classCode")
                        + "' Updated Successfully");
                request.setAttribute(SuiMessageController.R_BACK_URL, SuiEditSurchargeController.BACK_URL);
                rd.include(request, response);
                submitAuditTrail(auditTrail);
            } else {
                throw new MCDSException("Invalid Action Specified");
            }
        } catch (MCDSException e) {
            _log.info("Error is:" + e.getMessage());
            if (action != null && action.equals("view")) {
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute(SuiMessageController.R_MESSAGE, e.getMessage());
                request.setAttribute(SuiMessageController.R_BACK_URL, SuiViewSurchargeController.BACK_URL);
                rd.include(request, response);
            } else if (action != null && action.equals("edit")) {
                SuiEditSurchargeController suiEditSurchargeController=new SuiEditSurchargeController(request,response);
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute(SuiMessageController.R_MESSAGE, e.getMessage());
                request.setAttribute(SuiMessageController.R_BACK_URL, suiEditSurchargeController.getExceptionBackURL());
                rd.include(request, response);
            } else if (action != null && action.equals("add")) {
                SuiAddSurchargeController suiAddSurchargeController = new SuiAddSurchargeController(request,response);
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute(SuiMessageController.R_MESSAGE, e.getMessage());
                request.setAttribute(SuiMessageController.R_BACK_URL, suiAddSurchargeController.getExceptionBackURL());
                rd.include(request, response);
            } else if (action != null && action.equals("save")) {
                SuiEditSurchargeController suiEditSurchargeController=new SuiEditSurchargeController(request,response);
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute(SuiMessageController.R_MESSAGE, e.getMessage());
                request.setAttribute(SuiMessageController.R_BACK_URL, suiEditSurchargeController.getExceptionBackURL());
                rd.include(request, response);
            }
        }
    }

}
