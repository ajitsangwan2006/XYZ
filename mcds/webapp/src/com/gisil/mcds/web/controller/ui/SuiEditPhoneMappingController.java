package com.gisil.mcds.web.controller.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.dmi.PhoneMappingInfo;
import com.gisil.mcds.web.controller.AbstractJspController;

public class SuiEditPhoneMappingController extends AbstractJspController {
	public static final String PAGE_URL = "suiEditPhoneMapping.jsp";
	private PhoneMappingInfo info = null;
	private String action;

	public SuiEditPhoneMappingController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		// TODO Auto-generated constructor stub
		setAction((String)aRequest.getAttribute("action"));
		setInfo((PhoneMappingInfo)aRequest.getAttribute("phoneMapping"));
	}

	public String getEditMode(){
		if(isEditMode() ){
			return "";
		}else{
			return "disabled";
		}
	}

	public PhoneMappingInfo getInfo() {
		return info;
	}

	public void setInfo(PhoneMappingInfo info) {
		this.info = info;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
