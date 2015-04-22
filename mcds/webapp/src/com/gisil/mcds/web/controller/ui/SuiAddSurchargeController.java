package com.gisil.mcds.web.controller.ui;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gisil.mcds.web.controller.AbstractJspController;


public class SuiAddSurchargeController extends AbstractJspController {

    public static final String PAGE_PATH = "suiEditPartner.jsp";
    
    public static final String BACK_URL ="classSurchageServlet?action=view";

    private static String expBackURL;

    private String classCode = null;
   
    private String className = null;
    
    private String surcharge = null;

    private String msg;

    private HashSet aggregatorNames;
    
    public SuiAddSurchargeController(HttpServletRequest request,
            HttpServletResponse response) {
        super(request, response);
        
        loadData();
    }

  
    
    private void loadData() {
        if (_request.getParameter("classCode") != null) {
            classCode = _request.getParameter("classCode");
        }

        if (_request.getParameter("className") != null) {
            className =  _request.getParameter("className");
        }

        if (_request.getParameter("surcharge") != null) {
            surcharge = _request.getParameter("surcharge");
        }
        
    }



    public String getBackURL() {
        String url = "classSurchageServlet?action=view";
        return url;
    }



    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }



    public String getClassCode() {
        return classCode;
    }



    public void setClassName(String className) {
        this.className = className;
    }



    public String getClassName() {
        return className;
    }



    public void setSurcharge(String surcharge) {
        this.surcharge = surcharge;
    }



    public String getSurcharge() {
        return surcharge;
    }
    
    public String getMessage(){
        msg  = _request.getParameter("msg");
         return msg;
    }
    
    public HashSet getAggregatorNames(HttpSession session){
        aggregatorNames  = (HashSet)session.getAttribute( "aggregatorNames" );
         return aggregatorNames;
    }
    
    public String getExceptionBackURL(){
        
        expBackURL = "suiAddClassSurcharge.jsp?classCode="+classCode+
      "&className="+className+"&surcharge="+surcharge;
          
        return expBackURL;
      }
}

