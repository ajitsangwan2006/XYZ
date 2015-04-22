package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.ejb.MCDSRemote;

import com.gisil.mcds.web.servlet.MCDSServiceServlet;

public class SummaryReportServlet  extends MCDSServiceServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 121261264481193069L;
	
	public static final String SERVLET_PATH = "summaryReportServlet";

	@Override
	protected void processIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		RequestDispatcher reqDispatcher = request.getRequestDispatcher( "summaryReportMenu.jsp" );
		MCDSRemote mcdsRemote = getMCDSRemote();
		int limit = mcdsRemote.getMaxTransactionLimit();
		request.setAttribute( "limit", new Integer( limit ) );
		reqDispatcher.forward( request, response );
	}

}
