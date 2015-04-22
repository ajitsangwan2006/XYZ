package com.gisil.mcds.web.controller.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gisil.mcds.dmi.CommFormulaMappingInfo;
import com.gisil.mcds.web.controller.AbstractJspController;

public class SuiEditCommFormulaMappingController extends AbstractJspController {
	public static final String PAGE_PATH = "suiEditCommFormulaMapping.jsp";
	private String action;
	private CommFormulaMappingInfo commFormulaMapping = null;

	public SuiEditCommFormulaMappingController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		setAction((String)aRequest.getAttribute("action"));
		setCommFormula(( CommFormulaMappingInfo )aRequest.getAttribute("commFormula"));
		
		// TODO Auto-generated constructor stub
	}


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


	public CommFormulaMappingInfo getCommFormula() {
		return commFormulaMapping;
	}


	public void setCommFormula(CommFormulaMappingInfo commFormula) {
		this.commFormulaMapping = commFormula;
	}


}
