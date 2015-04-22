package com.gisil.mcds.web.servlet.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.PhoneMappingInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.ui.SuiEditPhoneMappingController;
import com.gisil.mcds.web.controller.ui.SuiMCDSMenuController;
import com.gisil.mcds.web.controller.ui.SuiListPhoneMappingController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

public class PhoneMappingServlet extends MCDSServiceServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4036144998616591492L;
	public static final String SERVLET_PATH = "PhoneMappingServlet";

	public PhoneMappingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AuditTrail auditTrail = createAuditTrail(request);
		auditTrail.setAdditionalInfo1("PrevelageLevel = "+getPrevelageLevel(request).toString());
		String action = request.getParameter( "action" );
		RequestDispatcher rd = null;
		try{
		MCDSRemote _remote = getMCDSRemote();
		if ( action != null && action.equals( "view" ) )
		{
			List phoneMappingList = _remote.getPhoneMapping();	
			auditTrail.setAdditionalInfo2("Phone mapping list received for View");
			rd = request.getRequestDispatcher( SuiListPhoneMappingController.PAGE_PATH );
			request.setAttribute( "phoneList", phoneMappingList );
		} else if ( action != null && action.equals( "edit" ) )
		{
			String id = request.getParameter( "id" );
			// Get Record From DB
			PhoneMappingInfo phoneMapping =  _remote.getPhoneMapping(id);
			auditTrail.setAdditionalInfo2("Phone mapping Info for Id = "+id);
			rd = request.getRequestDispatcher( SuiEditPhoneMappingController.PAGE_URL );
			request.setAttribute( "action", "editInfo" );
			request.setAttribute( "phoneMapping", phoneMapping );
			
		} else if ( action != null && action.equals( "editInfo" )) 
		{
			request.setAttribute("backUrl",PhoneMappingServlet.SERVLET_PATH+"?action=view");
			PhoneMappingInfo phoneMapping = new PhoneMappingInfo();
			phoneMapping.setAggPhoneCode(request.getParameter( "aggCode" ));
			phoneMapping.setPhoneMake(request.getParameter("make"));
			phoneMapping.setPhoneModel(request.getParameter("model"));
			rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
			// Get Record From DB
			if(_remote.isUpdatedPhoneMapping(phoneMapping)){
				auditTrail.setAdditionalInfo2("Phone mapping info updated successfully with agg code = "+request.getParameter("aggCode"));
				request.setAttribute( "message", ErrorMessages.UPDATE_SUCCESS );
			}else{
				auditTrail.setAdditionalInfo2("Commission mapping info update Unsuccessfull with agg code = "+request.getParameter("aggCode"));
				request.setAttribute( "message", ErrorMessages.NOT_SUCCESSFULL );
			}
			
		}
		}catch (MCDSException e) {
			// TODO: handle exception
			_log.info("Error is:"+e.getMessage());
			request.setAttribute( "message", e.getMessage());
			request.setAttribute("backUrl",PhoneMappingServlet.SERVLET_PATH+"?action=view");
			if ( action != null && action.equals( "view" ) )
			{
				auditTrail.setAdditionalInfo2("Can not get phone mapping info for view");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
				request.setAttribute("backUrl",SuiMCDSMenuController.PAGE_PATH);
			} else if ( action != null && action.equals( "edit" ) )
			{
				auditTrail.setAdditionalInfo2("Can not get phone mapping info for edit");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
			} else if ( action != null && action.equals( "editInfo" ) )
			{
				auditTrail.setAdditionalInfo2("Can not update phone mapping info for edit");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
			}

		}

		submitAuditTrail( auditTrail );
		rd.include( request, response );
	}

}


