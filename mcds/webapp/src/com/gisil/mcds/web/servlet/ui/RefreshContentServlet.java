package com.gisil.mcds.web.servlet.ui;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.script.ContentPackUpdateJob;
import com.gisil.mcds.script.PhoneMappingJob;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;

public class RefreshContentServlet extends MCDSServiceServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5459489056455878515L;
	public static final String SERVLET_PATH = "RefreshContentServlet";
	@Override
	protected void processIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher( "suiCronjobsMenu.jsp" );
        MCDSRemote remote = getMCDSRemote();
        try{ 
            
            if ( action != null && action.equals("content") )
            {
                remote.refreshContent();
            }
            else if ( action != null && action.equals("phonemapping" ) )
            {
                remote.refreshPhoneMapping();
            }
            
            requestDispatcher.include( request, response );     
        } catch (Exception e) {
            // TODO: handle exception
        }
        
	}
}
