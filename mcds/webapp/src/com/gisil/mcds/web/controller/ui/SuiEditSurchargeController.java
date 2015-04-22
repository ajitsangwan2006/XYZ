package com.gisil.mcds.web.controller.ui;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gisil.mcds.web.controller.AbstractJspController;


public class SuiEditSurchargeController extends AbstractJspController{

    public static final String PAGE_PATH = "suiEditClassSurcharge.jsp";
    
    public static final String BACK_URL ="classSurchageServlet?action=view";

    private static String expBackURL;
    
    
     private HashSet aggregatorNames = null;
    
     private String msg = null;
    
    private String classCode= null;
    
    private String className= null;
    
    private String surcharge= null;
    
    
    public SuiEditSurchargeController(HttpServletRequest request,
            HttpServletResponse response) {
        super(request, response);
        
        loadData();
    }
    
    private void loadData() {
        
        classCode= _request.getParameter("classCode");
        
        className= _request.getParameter("className");
        
        surcharge= _request.getParameter("surcharge");
        
        msg= _request.getParameter("msg");
    }

    public String getBackURL() {
        String url = "classSurchageServlet?action=view";
        return url;
    }
    
    public String getResetURL(){
        String url=null;
        
        url="suiEditClassSurcharge.jsp";
       return url;
    }
    
    public HashSet getAggregatorNames(HttpSession session){
        aggregatorNames  = (HashSet)session.getAttribute( "aggregatorNames" );
         return aggregatorNames;
    }
    
    public String getMessage(){
         return msg;
    }

   
    public String getClassCode() {
        return classCode;
    }

    

    /**
     * @TODO document me
     * @return Returns the className.
     */
    public String getClassName() {
        return className;
    }

    

    /**
     * @TODO document me
     * @return Returns the surviceCharge.
     */
    public String getSurviceCharge() {
        return surcharge;
    }
    
    public String getExceptionBackURL(){
      expBackURL = "suiEditClassSurcharge.jsp?classCode="+classCode+
      "&className="+className+"&surcharge="+surcharge;
        
      return expBackURL;
    }
    
    public void setClassCode(){
        _request.setAttribute("classCode", _request.getParameter("classCode"));
    }

}
