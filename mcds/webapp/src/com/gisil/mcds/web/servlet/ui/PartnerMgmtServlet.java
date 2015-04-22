package com.gisil.mcds.web.servlet.ui;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.commission.Partner;
import com.gisil.mcds.dmi.PartnerInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.web.controller.ui.SuiAddPartnerController;
import com.gisil.mcds.web.controller.ui.SuiEditPartnerController;
import com.gisil.mcds.web.controller.ui.SuiMessageController;
import com.gisil.mcds.web.controller.ui.SuiViewPartnerController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

public class PartnerMgmtServlet extends MCDSServiceServlet {

    /**
     * serial Version UID
     */
    private static final long serialVersionUID = -6731907984317090790L;

    @Override
    protected void processIt(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        AuditTrail auditTrail = createAuditTrail(request);
        auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
        
        MCDSRemote remote = getMCDSRemote();

        String action = request.getParameter("action");
        try {
            if (action != null && action.equals("view")) {
                List<PartnerInfo> partnerList = getPartner(remote);
                auditTrail.setAdditionalInfo2("Here got Partner Info");
                RequestDispatcher rd = request.getRequestDispatcher(SuiViewPartnerController.PAGE_PATH);
                request.setAttribute("partnerList", partnerList);
                rd.include(request, response);
                submitAuditTrail(auditTrail);
            } else if (action != null && action.equals("save")) {
                Partner partner = new Partner();

                String partnerName = request.getParameter("partnername");
                if (partnerName == "") {
                    throw new MCDSException("Partner name can not be null");
                }

                partner.setId(Integer.parseInt(request.getParameter("id")));
                partner.setPartnerName(request.getParameter("partnername"));
               
                String status = request.getParameter("status");

                if (status.equals("0")) {
                    partner.setStatus(DBUtil.ENABLED);
                } else {
                    partner.setStatus(DBUtil.DISABLED);
                }

                remote.savePartnerValues(partner);
                auditTrail.setAdditionalInfo2("Here Updated Partner Info");
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute(SuiMessageController.R_MESSAGE, "Partner Name' "
                        + request.getParameter("partnername") + "' Updated Successfully");
                request.setAttribute(SuiMessageController.R_BACK_URL, SuiEditPartnerController.BACK_URL);
                rd.include(request, response);
                submitAuditTrail(auditTrail);

            } else if (action != null && action.equals("add")) {
                Partner partner = new Partner();

                String partnerName = request.getParameter("partnername");
                if (partnerName == "") {
                    throw new MCDSException("Partner name can not be null");
                }
                partner.setPartnerName(partnerName);
                partner.setDescription(request.getParameter("description"));
                
                String status = request.getParameter("status");

                if (status.equals("0")) {
                    partner.setStatus(DBUtil.ENABLED);
                } else {
                    partner.setStatus(DBUtil.DISABLED);
                }

                remote.addPartnerValues(partner);
                auditTrail.setAdditionalInfo2("Here Added Partner Info");
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute(SuiMessageController.R_MESSAGE, "Partner Name' "
                        + request.getParameter("partnername") + "' Inaserted Successfully");
                request.setAttribute(SuiMessageController.R_BACK_URL, SuiAddPartnerController.BACK_URL);
                rd.include(request, response);
                submitAuditTrail(auditTrail);
            }

        } catch (Exception e) {
            // TODO: handle exception
            _log.info("Error is:" + e.getMessage());
            if (action != null && action.equals("view")) {
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute("message", e.getMessage());
                request.setAttribute("backUrl", SuiViewPartnerController.BACK_URL);
                rd.include(request, response);
            } else if (action != null && action.equals("save")) {
                SuiEditPartnerController suiEditPartnerController = new SuiEditPartnerController(request,response);
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute("message", e.getMessage());
                request.setAttribute("backUrl", suiEditPartnerController.exceptionBackURL());
                rd.include(request, response);
            } else if (action != null && action.equals("add")) {
                SuiAddPartnerController suiAddPartnerController =new SuiAddPartnerController(request,response);
                RequestDispatcher rd = request.getRequestDispatcher(ErrorMessages.SUI_MESSAGE);
                request.setAttribute("message", e.getMessage());
                request.setAttribute("backUrl", suiAddPartnerController.exceptionBackURL());
                rd.include(request, response);
            }
        }

    }

    private List<PartnerInfo> getPartner(MCDSRemote remote) throws MCDSException, RemoteException {
        List<PartnerInfo> partnerList = null;

        Partner partner = new Partner();

        partnerList = remote.getPartnerList(partner);

        return partnerList;
    }

}