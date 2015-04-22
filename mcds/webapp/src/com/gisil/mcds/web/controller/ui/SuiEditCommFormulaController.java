package com.gisil.mcds.web.controller.ui;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.dmi.CommFormulaInfo;
import com.gisil.mcds.web.controller.AbstractJspController;

public class SuiEditCommFormulaController extends AbstractJspController {
	public static final String PAGE_PATH = "suiEditCommFormula.jsp";
	private String action;
	private CommFormulaInfo commFormula = null;
	

	public SuiEditCommFormulaController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		setAction((String)aRequest.getAttribute("action"));
		setCommFormula(( CommFormulaInfo )aRequest.getAttribute("commFormula"));
	}


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


	public CommFormulaInfo getCommFormula() {
		return commFormula;
	}


	public void setCommFormula(CommFormulaInfo commFormula) {
		if(getAction().equals("addNew")){
			commFormula.setCommissionType("PERCENT");
			commFormula.setIsClassSurchanrge(false);
			commFormula.setStatus("DISABLED");
			commFormula.setSurchargeType("PERCENT");
		}
		this.commFormula = commFormula;
	}


}
