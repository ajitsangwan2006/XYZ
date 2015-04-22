package com.gisil.mcds.web.controller.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.dmi.PartnerInfo;
import com.gisil.mcds.web.controller.AbstractJspController;


public class SuiViewPartnerController extends AbstractJspController{
 
    public static String PAGE_PATH = "suiViewClassPartner.jsp";
    
    public static final String BACK_URL = "suiCommissionMenu.jsp";
    private List<PartnerInfo> list;

    public SuiViewPartnerController(HttpServletRequest request,
            HttpServletResponse response) {
        super(request, response);
        
    }
    
    public String getEditUrl(PartnerInfo partner){
        String url=null;
        
        url="suiEditClassPartner.jsp?&id="+
        partner.getId()+"&partnerName="+partner.getPartnerName();
        
        return url;
        
    }
    
    public String getAddUrl(){
        String url=null;
        
        url="suiAddClassPartner.jsp";
        
        return url;
        
    }
    
    public String getPageUrl(){
        String url=null;
        
        url="PartnerMgmtServlet?action=view";
        
        return url;
        
    }
    
    public List<PartnerInfo> getPartnerInfo(){
        list = (List)_request.getAttribute( "partnerList" ); 
        return list;
    }
}
