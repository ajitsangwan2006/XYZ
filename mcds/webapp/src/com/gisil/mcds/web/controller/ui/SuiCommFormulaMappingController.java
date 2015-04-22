package com.gisil.mcds.web.controller.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;

public class SuiCommFormulaMappingController extends AbstractJspController {
	
	public static final String PAGE_URL = "suiCommFormulaMapping.jsp";

	public SuiCommFormulaMappingController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		// TODO Auto-generated constructor stub
	}
	public String getEditMode(){
		if(isEditMode() ){
			return "";
		}else{
			return "disabled";
		}
	}

}
