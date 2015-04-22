package com.gisil.mcds.web.controller.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;


public class SuiAddPartnerController extends AbstractJspController {

    public static final String PAGE_PATH = "suiEditPartner.jsp";
    
    public static final String BACK_URL = "PartnerMgmtServlet?action=view";

    private static String expBackURL;
    
    private String msg = null;

   private String description;

    public SuiAddPartnerController(HttpServletRequest request,
            HttpServletResponse response) {
        super(request, response);
        
        loadData();
    }

  
    
    private void loadData() {              
        description=_request.getParameter("description");
    }



    public String getBackURL() {
        String url = "PartnerMgmtServlet?action=view";
        return url;
    }
    
    /**
     * @TODO document me
     * @return Returns the msg.
     */
    public String getMsg() {
        msg  =  _request.getParameter("msg");
        return msg;
    }
    
    public String exceptionBackURL(){
        expBackURL = "suiAddClassPartner.jsp?description="+description;
        
        return expBackURL;
    }


    /**
     * @TODO document me
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
}
