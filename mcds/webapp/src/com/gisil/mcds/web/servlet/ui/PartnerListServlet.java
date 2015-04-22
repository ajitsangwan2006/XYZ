package com.gisil.mcds.web.servlet.ui;

import java.io.IOException;
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

import com.gisil.mcds.web.controller.ui.SuiPartnerListController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;

/**
 * 
 * @author amit kumar
 *
 */
public class PartnerListServlet extends MCDSServiceServlet {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 8539909546311156653L;
	public static final String SERVLET_PATH = "PartnerListServlet";
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
			{
		
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "
				+ getPrevelageLevel(request).toString());
		List<PartnerInfo> partnerList = null;
		
		try 
		{
			MCDSRemote remote = getMCDSRemote();
			request.getSession().removeAttribute(SuiPartnerListController.PARTNER_ID);
			partnerList = remote.getPartnerList(new Partner());
			auditTrail.setAdditionalInfo2("Here get Partner info");
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher(SuiPartnerListController.PAGE_PATH);
			request.setAttribute("partnerList", partnerList);
			requestDispatcher.include(request, response);
			submitAuditTrail(auditTrail);
		} catch (MCDSException mcdsExp) {
			_log.info(mcdsExp.toString());

		}

		
	}


}
