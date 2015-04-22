package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.web.controller.wap.ContentMenuController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;
import com.gisil.mcds.web.util.SessionData;

public class MCDSMenuServlet extends MCDSServiceServlet 
{

	/**serialVersionUID*/
	private static final long serialVersionUID = -1158779073397265559L;

	public static final String SERVLET_PATH = "mcdsmenu";
	
	@Override
	protected void processIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		SessionData _sessionData = (SessionData)request.getSession().getAttribute("sessionData");
		
		if(_sessionData != null)
		{
			_sessionData.setCategorySearch(false);
			_sessionData.setContentInfo(null);
			_sessionData.setEntryPoint(null);
			_sessionData.setError(null);
			_sessionData.setSearchBy(null);
			request.setAttribute("sessionData", _sessionData);
		}
		try
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher( ContentMenuController.PAGE_PATH );
			String aggregatorID = (String)request.getSession().getAttribute( "aggregatorId");
			int aggID = 0;
			try
			{
				aggID = Integer.parseInt( aggregatorID );
			}
			catch( NumberFormatException nfe )
			{
				throw new MCDSException( ErrorMessages.INVALID_AGGREGATOR );
			}
			MCDSRemote remote = getMCDSRemote();
			HashMap<String, String> mcdsMenuContent = remote.getTerminalMenuContent( aggID);
			request.setAttribute( "contentList", mcdsMenuContent );
            _log.info("Recieved response to display generic main menu.");
			requestDispatcher.forward( request, response );			
		}
		catch( MCDSException mcdsExp )
		{
			_log.info( "MCDS Exception " + mcdsExp.toString() );
			goToErrorPage(request, response, mcdsExp.toString(), SERVLET_PATH );
		}
		
	}

}
