package com.gisil.mcds.web.controller.ui;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.dmi.PartnerInfo;
import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * 
 * @author amit kumar
 * 
 */
public class SuiEditPartnerController extends AbstractJspController {

	public static final String PAGE_PATH = "suiEditPartner.jsp";
    
    public static final String BACK_URL = "PartnerMgmtServlet?action=view";

    private static String expBackURL;

	private List _partnerInfo;

	private String id;

	private String partnerName;

	private String description;

	private String status;
    
    private String msg;
    
	public SuiEditPartnerController(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
		setData();
	}

	private void setData() {

	
			
        id= _request.getParameter("id");
            
        partnerName=_request.getParameter("partnerName");
        
        msg =  _request.getParameter("msg");
	}

	public String getDescription() 
	{
		if ( description == null )
		{
			description = "";
		}
		return description.trim();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPartnerName() 
	{
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public void setId(String id){
        this.id = id;
    }
    
	public String getId() {
		return id;
	}

	public String getEditMode() {
		if (isEditMode()) {
			return "";
		} else {
			return "disabled";
		}
	}
    
    public String getBackURL() {
        String url = "PartnerMgmtServlet?action=view";
        return url;
    }
    
    public String getResetURL(HttpServletRequest request){
        String url=null;
        
        url="suiEditClassPartner.jsp";
        
       return url;
    }

    /**
     * @TODO document me
     * @return Returns the msg.
     */
    public String getMsg() {
        return msg;
    }
    
    public String exceptionBackURL(){
        expBackURL = "suiEditClassPartner.jsp?id="+id;
        
        return expBackURL;
    }
}
