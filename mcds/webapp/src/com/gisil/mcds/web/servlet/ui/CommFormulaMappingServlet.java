package com.gisil.mcds.web.servlet.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.CommFormulaMappingInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
/**
 *Servlet linked with view, edit and add jsps of Commission Formula Mapping
 * @author ajit singh
 *
 */
public class CommFormulaMappingServlet extends MCDSServiceServlet {

	/**
	 * serialVersionUID = -3914895249840690037L
	 * SERVLET_PATH = "CommFormulaMappingServlet"
	 */
	private static final long serialVersionUID = -3914895249840690037L;
	public static final String SERVLET_PATH = "CommFormulaMappingServlet";

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
			// Get commission fromula mapping info from DB
			List commMappingList = _remote.getCommFormulaMapping();	
			auditTrail.setAdditionalInfo2("Commission mapping list received for View");
			rd = request.getRequestDispatcher( "suiCommFormulaMapping.jsp" );
			request.setAttribute( "commMappingList", commMappingList );
		} else if ( action != null && action.equals( "edit" ) )
		{
			String id = request.getParameter( "id" );
			// Get Record From DB
			CommFormulaMappingInfo commFormula =  _remote.getCommFormulaMapping(Integer.parseInt(id));
			auditTrail.setAdditionalInfo2("Commission mapping Info for Id = "+id);
			rd = request.getRequestDispatcher( "suiEditCommFormulaMapping.jsp" );
			request.setAttribute( "action", "editInfo" );
			request.setAttribute( "commFormula", commFormula );
			
		} else if ( action != null && action.equals( "editInfo" )  || action.equals( "addNew" ) ) 
		{
			request.setAttribute("backUrl",CommFormulaMappingServlet.SERVLET_PATH+"?action=view");
			CommFormulaMappingInfo commFormula = new CommFormulaMappingInfo();
			commFormula.setAggregatorId(Long.parseLong(request.getParameter( "aggId" )));
			commFormula.setCommformulaId(Long.parseLong(request.getParameter( "commId" )));
			if(request.getParameter("id") != null && !request.getParameter("id").equals("null"))
				commFormula.setId(Long.parseLong(request.getParameter("id")));
			commFormula.setDelNo(request.getParameter("delNo"));
			rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
			// Get Record From DB
			if(action.equals( "editInfo" )){
				// Update commission fromula mapping info from DB
			if(_remote.isUpdatedCommissionMapping(commFormula)){
				auditTrail.setAdditionalInfo2("Commission mapping info updated successfully with id = "+request.getParameter("id"));
				request.setAttribute( "message", ErrorMessages.UPDATE_SUCCESS );
			}else{
				auditTrail.setAdditionalInfo2("Commission mapping info update Unsuccessfull with id = "+request.getParameter("id"));
				request.setAttribute( "message", ErrorMessages.NOT_SUCCESSFULL );
			}
			}else if(action.equals( "addNew" )){
				try{
					_remote.addCommissionMapping(commFormula);
					auditTrail.setAdditionalInfo2("Commission mapping info added successfully");
					request.setAttribute( "message", ErrorMessages.SUCCESS_ADD );
				}catch(MCDSException e){
					auditTrail.setAdditionalInfo2("Commission mapping info add unsuccessfull");
					request.setAttribute( "message", ErrorMessages.UNSUCCESS_ADD );
				}
			}
			
		}else if( action != null && action.equals("addView"))
		{
			CommFormulaMappingInfo commFormula = new CommFormulaMappingInfo();
			// Get commission fromula mapping info from DB for add page view
			commFormula=_remote.getCommForMap();
			commFormula.setAggregatorId(Long.parseLong("0"));
			commFormula.setAggregatorName("");
			auditTrail.setAdditionalInfo2("Commission mapping info for add view");
			rd = request.getRequestDispatcher( "suiEditCommFormulaMapping.jsp" );
			request.setAttribute( "action", "addNew" );
			request.setAttribute( "commFormula", commFormula );
		}
		}catch (MCDSException e) {
			// TODO: handle exception
			_log.info("Error is:"+e.getMessage());
			request.setAttribute( "message", e.getMessage());
			request.setAttribute("backUrl",CommFormulaMappingServlet.SERVLET_PATH+"?action=view");
			if ( action != null && action.equals( "view" ) )
			{
				auditTrail.setAdditionalInfo2("Can not get commission mapping info for view");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
				request.setAttribute("backUrl","suiCommissionMenu.jsp");
			} else if ( action != null && action.equals( "edit" ) )
			{
				auditTrail.setAdditionalInfo2("Can not get commission mapping info for edit");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
			} else if ( action != null && action.equals( "editInfo" ) )
			{
				auditTrail.setAdditionalInfo2("Can not update commission mapping info for edit");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
			}else if ( action != null && action.equals( "addView" ) )
			{
				auditTrail.setAdditionalInfo2("Can not get commission mapping info for add");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
			} else if ( action != null && action.equals( "addNew" ) )
			{
				auditTrail.setAdditionalInfo2("Can not add commission mapping info for add new");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
			}

		}

		submitAuditTrail( auditTrail );
		rd.include( request, response );
	}

}
