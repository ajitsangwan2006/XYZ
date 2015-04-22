package com.gisil.mcds.web.controller.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.servlet.ui.PhoneMappingServlet;

public class SuiListPhoneMappingController extends AbstractJspController 
{
	public static final String PAGE_PATH = "suiListPhoneMapping.jsp";

	public SuiListPhoneMappingController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		// TODO Auto-generated constructor stub
	}
	public String getEditURL(String code){
		if(isEditMode() ){
			return PhoneMappingServlet.SERVLET_PATH +"?action=edit&id=" + code;
		}else{
			return PhoneMappingServlet.SERVLET_PATH +"?action=view";
		}
	}

}
