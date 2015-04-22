package com.gisil.mcds.web.servlet.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.audit.AuditTrail;
import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.dmi.CommFormulaInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
/**
 *  Servlet linked with view, edit and add jsps of Commission Formula
 * @author ajit singh
 *
 */
public class CommFormulaServlet extends MCDSServiceServlet {

	/**
	 * serialVersionUID = 7938725926312461506L
	 * SERVLET_PATH = "CommFormulaServlet"
	 */
	private static final long serialVersionUID = 7938725926312461506L;
	
	public static final String SERVLET_PATH = "CommFormulaServlet";

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
			// Get commission fromula info from DB
			List commList = _remote.getCommFormula();		
			auditTrail.setAdditionalInfo2("Commission list received for View");	
			rd = request.getRequestDispatcher( "suiCommFormula.jsp" );
			request.setAttribute( "commList", commList );
		} else if ( action != null && action.equals( "edit" ) )
		{
			String id = request.getParameter( "id" );
			// Get Record From DB
			CommFormulaInfo commFormula =  _remote.getFormulas(Integer.parseInt(id));
			auditTrail.setAdditionalInfo2("Commission Info for Id = "+id);
			rd = request.getRequestDispatcher( "suiEditCommFormula.jsp" );
			request.setAttribute( "action", "editInfo" );
			request.setAttribute( "commFormula", commFormula );
			
		} else if ( action != null && ( action.equals( "editInfo" ) || action.equals( "addNew" ) ) )
		{
			CommFormulaInfo commFormula = new CommFormulaInfo();
			commFormula.setFormulaId(Long.parseLong(request.getParameter("formulaId")));
			commFormula.setCommissionType(request.getParameter( "commType" ));
			commFormula.setCommValue(Double.parseDouble(request.getParameter( "commFormula" )));
			if(request.getParameter("id") != null && !request.getParameter("id").equals("null"))
				commFormula.setId(Integer.parseInt(request.getParameter("id")));
			commFormula.setPartnerId(Long.parseLong(request.getParameter("partnerId")));
			commFormula.setStatus(request.getParameter("status"));
			commFormula.setSurchange(Double.parseDouble(request.getParameter("surcharge")));
			commFormula.setSurchargeType(request.getParameter("surchargeType"));
			if(request.getParameter("isClassSurcharge").equals("true"))
				commFormula.setIsClassSurchanrge(true);
			else
				commFormula.setIsClassSurchanrge(false);
			rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
			request.setAttribute("backUrl",CommFormulaServlet.SERVLET_PATH+"?action=view");
			// Get Record From DB
			if(action.equals( "editInfo" )){
				if(_remote.isUpdatedCommission(commFormula)){
					auditTrail.setAdditionalInfo2("Commission info updated successfully with id = "+request.getParameter("id"));
					request.setAttribute( "message", ErrorMessages.UPDATE_SUCCESS );
				}else{
					auditTrail.setAdditionalInfo2("Commission info update Unsuccessfull with id = "+request.getParameter("id"));
					request.setAttribute( "message", ErrorMessages.NOT_SUCCESSFULL );
				}
			}else if(action.equals( "addNew" )){
				try{
					// Update commission fromula info from DB
					_remote.addCommission(commFormula);
					auditTrail.setAdditionalInfo2("Commission info added successfully");
					request.setAttribute( "message", ErrorMessages.SUCCESS_ADD );
				}catch(MCDSException e){
					auditTrail.setAdditionalInfo2("Commission info add unsuccessfull");
					_log.info("Error : "+e.getMessage());
					request.setAttribute( "message", ErrorMessages.UNSUCCESS_ADD );
				}
			}
			
		}else if( action != null && action.equals("addView"))
		{
			CommFormulaInfo commFormula = new CommFormulaInfo();
			commFormula.setPartnerNameList(_remote.getPartenerNameList());
			auditTrail.setAdditionalInfo2("Commission info for add view");
			rd = request.getRequestDispatcher( "suiEditCommFormula.jsp" );
			request.setAttribute( "action", "addNew" );
			request.setAttribute( "commFormula", commFormula );
		}
		}catch (MCDSException e) {
			// TODO: handle exception
			_log.info("Error is:"+e.getMessage());
			if ( action != null && action.equals( "view" ) )
			{
				auditTrail.setAdditionalInfo2("Can not get commission mapping info for view");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
				request.setAttribute( "message", e.getMessage());
				request.setAttribute("backUrl","suiCommissionMenu.jsp");
			} else if ( action != null && action.equals( "edit" ) )
			{
				auditTrail.setAdditionalInfo2("Can not get commission mapping info for edit");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
				request.setAttribute( "message", e.getMessage());
				request.setAttribute("backUrl",CommFormulaServlet.SERVLET_PATH+"?action=view");
			} else if ( action != null && action.equals( "editInfo" ) )
			{
				auditTrail.setAdditionalInfo2("Can not update commission mapping info for edit");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
				request.setAttribute( "message", e.getMessage());
				request.setAttribute("backUrl",CommFormulaServlet.SERVLET_PATH+"?action=view");
			}else if ( action != null && action.equals( "addView" ) )
			{
				auditTrail.setAdditionalInfo2("Can not get commission mapping info for add");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
				request.setAttribute( "message", e.getMessage());
				request.setAttribute("backUrl",CommFormulaMappingServlet.SERVLET_PATH+"?action=view");
			} else if ( action != null && action.equals( "addNew" ) )
			{
				auditTrail.setAdditionalInfo2("Can not add commission mapping info for add new");
				rd = request.getRequestDispatcher( ErrorMessages.SUI_MESSAGE );
				request.setAttribute( "message", e.getMessage());
				request.setAttribute("backUrl",CommFormulaMappingServlet.SERVLET_PATH+"?action=view");
			}

		}

		submitAuditTrail( auditTrail );
		rd.include( request, response );
	}
}
