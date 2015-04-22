package com.gisil.mcds.web.servlet.ui;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.ui.SuiMCDSMenuController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;

public class GetPrivilageLevelServlet extends MCDSServiceServlet 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -459977187705771633L;
	public static final String SERVLET_PATH = "privilageLevelServlet";

	@Override
	protected void processIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		MCDSRemote remote = getMCDSRemote();	
		try
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher( SuiMCDSMenuController.PAGE_PATH );
			int  editLevel = remote.getConfigNumber( MCDSConfig.EDIT_LEVEL ).intValue();
			HttpSession session = request.getSession();
			session.setAttribute( "editlevel", editLevel );
			_log.info( "Set Edit Level in Session Attribute" );
			dispatcher.include( request, response );
		}
		catch( MCDSException e )
		{
			_log.info( "Exception Occured while getting privilage Level " +e.toString() );
			goToErrorPage( request, response, e.toString(), SuiMCDSMenuController.PAGE_PATH );
		}
	}

}
