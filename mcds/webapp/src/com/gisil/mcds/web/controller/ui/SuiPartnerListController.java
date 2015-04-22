package com.gisil.mcds.web.controller.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * 
 * @author amit kumar
 *
 */

public class SuiPartnerListController extends AbstractJspController {

	public static final String PAGE_PATH = "suiPartnerList.jsp";
	
	public static final String PARTNER_ID = "partnerId";
	
	private List partnerList = null;
	
	public SuiPartnerListController (HttpServletRequest aRequest, HttpServletResponse aResponse)
	{
		super(aRequest,aResponse);		
		
		
		partnerList= (List)_request.getAttribute("partnerList");

	}
	
	/**
	 * @return Returns the partnerInfoList.
	 */
	public List getPartnerList() {
		return partnerList;
	}
	
}
