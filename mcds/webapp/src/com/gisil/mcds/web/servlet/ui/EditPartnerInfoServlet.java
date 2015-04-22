package com.gisil.mcds.web.servlet.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.commission.Partner;
import com.gisil.mcds.dmi.AggregatorInfo;
import com.gisil.mcds.dmi.EntityDMLRequest;
import com.gisil.mcds.dmi.PartnerInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.ui.SuiEditPartnerController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;

/**
 * 
 * @author amit kumar
 * 
 */

public class EditPartnerInfoServlet extends MCDSServiceServlet {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = -572912304059509068L;
	
	

	public static final String SERVLET_PATH = "EditPartnerInfoServlet";

	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "
				+ getPrevelageLevel(request).toString());
		
		try {

			String action = request.getParameter("action");
			MCDSRemote _remote = getMCDSRemote();

			if (action != null && action.equals("save")) 
			{
				
				String partnerId = request.getParameter( "partnerId");
				String description = request.getParameter( "description" );
				String status = request.getParameter("status");
				
				
				Partner partner = new Partner();
				partner.setId( Integer.parseInt( partnerId ) );
				partner.setDescription( description );
				partner.setStatus( status );
				
			
				System.out.println( "test :" + partnerId);
				System.out.println( "test :" + description );
				System.out.println( "test :" + status);

				_remote.updatePartnerInfo(partner);
				RequestDispatcher dispatcher = request.getRequestDispatcher( PartnerListServlet.SERVLET_PATH );
				dispatcher.include(request, response);

			} else if (action != null && action.equals("edit")) 
			{
				String partnerId = request.getParameter( "partnerId");
				
				Partner partner = new Partner();
				partner.setId(Integer.parseInt(partnerId) );
				
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(SuiEditPartnerController.PAGE_PATH);
				List<PartnerInfo> _partnerinfo = _remote.getPartnerList(partner);
				auditTrail.setAdditionalInfo2("Get the Partner info with id :"+ request.getParameter("partnerId"));
				auditTrail.setAdditionalInfo3( "Partner info Action " +action );
				submitAuditTrail(auditTrail);
				request.setAttribute("partnerInfo", _partnerinfo);
				dispatcher.include(request, response);
			}
		} catch (MCDSException e) {
			_log.info("Cannot Load aggr Info :" + e);

		}
	}

}
