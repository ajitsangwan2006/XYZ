package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.util.AppServer;
import com.gisil.mcds.web.controller.wap.TopContentController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.SessionData;

public class TopContentServlet extends MCDSServiceServlet {

	/**
	 * serialVersionUID = -3537049852005238059L
	 * SERVLET_PATH = "TopContentServlet
	 */
	private static final long serialVersionUID = -3537049852005238059L;
	public static final String SERVLET_PATH = "TopContentServlet";

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String _contentId = request.getParameter("contentId");
		RequestDispatcher dispatcher = null;
		MCDSRemote _remote = AppServer.getAppServer(getServletContext())
		.getMCDSServer();
		if(_contentId != null){
			if(_contentId.equals("1")){
				request.getSession().setAttribute("topContentId",_contentId);
				request.getSession().setAttribute("parentId",null);
			}else{
				request.getSession().setAttribute("parentId",_remote.getParentId(_contentId));
			}
			ArrayList _contents = _remote.getTopContents(_contentId);
			request.getSession().setAttribute("contents",_contents);
			dispatcher = request.getRequestDispatcher(TopContentController.JSP_PATH);
		}else{
			if(request.getParameter("parentId") == null || request.getParameter("parentId").equals("null")){
				request.getSession().removeAttribute("contents");
				request.getSession().removeAttribute("parentId");
				dispatcher = request.getRequestDispatcher(MCDSMenuServlet.SERVLET_PATH);
			}else{
				ArrayList _contents = _remote.getTopContents(request.getParameter("parentId"));
				request.getSession().setAttribute("contents",_contents);
				request.getSession().setAttribute("parentId",_remote.getParentId(request.getParameter("parentId")));
				dispatcher = request.getRequestDispatcher(TopContentController.JSP_PATH);
			}
			
		}
		SessionData _sessionData = (SessionData)request.getSession().getAttribute("sessionData");
		if(_sessionData == null){
			_sessionData = new SessionData();
		}
		_sessionData.setEntryPoint("top");
		request.getSession().setAttribute("sessionData",_sessionData);
		dispatcher.forward(request, response);
		
	}

}
