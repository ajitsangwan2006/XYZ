package com.gisil.mcds.web.controller.ui;

import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gisil.mcds.commission.ClassSurcharge;
import com.gisil.mcds.web.controller.AbstractJspController;


public class SuiViewSurchargeController extends AbstractJspController{
	
	public static final String PAGE_PATH = "suiViewClassSurcharge.jsp";
    
    public static final String BACK_URL ="suiCommissionMenu.jsp";

    
    private ArrayList<ClassSurcharge> surchargeList = null;

    public SuiViewSurchargeController(HttpServletRequest request,
            HttpServletResponse response) {
        super(request, response);
        
    }
    
    public String getEditUrl(ClassSurcharge classSurcharge){
        String url=null;
        
        url="suiEditClassSurcharge.jsp?&classCode="+
        classSurcharge.getClassCode()+"&className="+classSurcharge.getClassName()+
        "&surcharge="+classSurcharge.getSurcharge();
        
        return url;
        
    }
    
    public String getPageUrl(){
        String url=null;
        
        url="classSurchageServlet?action=view";
        
        return url;
        
    }
    
    public String getAddUrl(){
        String url=null;
        
        url="suiAddClassSurcharge.jsp";
        
        return url;
        
    }
    
   public ArrayList<ClassSurcharge> getSurchargeList(){
       surchargeList = (ArrayList) _request.getAttribute("surchargeList");
       return surchargeList;
   }
   
   public void setaggregatorNames(HashSet aggregatorNames, HttpSession session){
       
       session.setAttribute("aggregatorNames", aggregatorNames);
   }
   
}
