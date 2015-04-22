/*
 * Copyright, GISIL 2006 All rights reserved. The copyright in this work is
 * vested in GISIL and the information contained herein is confidential. This
 * work (either in whole or in part) must not be modified, reproduced, disclosed
 * or disseminated to others or used for purposes other than that for which it
 * is supplied, without the prior written permission of GISIL. If this work or
 * any part hereof is furnished to a third party by virtue of a contract with
 * that party, use of this work by such party shall be governed by the express
 * contractual terms between the GISIL which is a party to that contract and the
 * said party.
 */

package com.gisil.mcds.web.controller.wap;
import java.io.IOException;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.db.DBAccessManagerFactory;
import com.gisil.mcds.db.IDBAccessManager;
import com.gisil.mcds.util.DBUtil;
import com.gisil.mcds.web.controller.AbstractJspController;

/**
 * Services contentDetail.jsp
 * @author Ajit Singh
 * 
 */
public class CategoryDetailController extends AbstractJspController {

	public String _contentId = null;

	public HashMap<String, String> _contents = null;

	public String _backId = null;

	public String _currentTitle = null;

	public String _nextPage = null;;

	public String _backTitle = null;

	public static final String _pagePath = "contentDetail.jsp";

	/**
	 * CategoryDetailController
	 * 
	 * @param aRequest
	 * @param aResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public CategoryDetailController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		super(aRequest, aResponse);

		_sessionData.setEntryPoint("Top 10");
		_request.getSession().setAttribute("sessionData", _sessionData);

		// It shows the current category
		_contentId = aRequest.getParameter("contentId");
		// It shows the current categories to be displayed
		_contents = getContents();
		// It is used to genrate the back URL
		_backId = getBackId();
		// returns the current title to be displayed
		_currentTitle = "Top 10 " + _backTitle;

	}

	/**
	 * This function find and return the contents id and content names, which we
	 * are going to catch in categoryDetail.jsp
	 * 
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public HashMap<String, String> getContents() throws ServletException,
			IOException {

		Connection con = null;

		ResultSet rs = null;

		Statement st = null;

		try {
			IDBAccessManager dbManager = DBAccessManagerFactory
					.getDBAccessManager();
			con = dbManager.getConnection();
			st = con.createStatement();
			rs = st
					.executeQuery("select id, contentname from contents where contentid in( select a.childcontentid from content_group a, content_group b where a.contentid="
							+ _contentId
							+ " and a.childcontentid = b.contentid)");

			if (rs.next()) {
				_contents = new HashMap<String, String>();
				do {
					// Putting all content details in HashMap
					_contents.put((String) rs.getString("contentid"),
							(String) rs.getString("name"));

				} while (rs.next());
			}
			rs = st
					.executeQuery("select contentid from content_group where contentid in(select childcontentid from content_group where contentid in(select childcontentid from content_group where contentid ="
							+ _contentId + "))");

			if (rs.next()) {
				_nextPage = "categoryDetail.jsp";
			} else {
				_nextPage = "contentDetail.jsp";
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (MCDSException mcdsExp) {
			System.out.println(mcdsExp.toString());
		} finally {

			DBUtil.close(con);

		}

		return _contents;
	}

	/**
	 * This function find and return the parent id, which we are using for back
	 * back page path
	 * 
	 * @return
	 */
	public String getBackId() {

		Connection con = null;

		ResultSet rs = null;

		Statement st = null;

		try {
			IDBAccessManager dbManager = DBAccessManagerFactory
					.getDBAccessManager();
			con = dbManager.getConnection();
			st = con.createStatement();
			rs = st
					.executeQuery("select a.contentid, b.name from content_group a inner join content_details b on a.childcontentid = b.contentid where a.childcontentid = "
							+ _contentId);

			if (rs.next()) {
				// Here we are initializing the _backId for return URL
				_backId = rs.getString("contentid");
				_backTitle = rs.getString("name");
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (MCDSException mcdsExp) {
			System.out.println(mcdsExp.toString());
		} finally {

			DBUtil.close(con);

		}

		return _backId;
	}

}
