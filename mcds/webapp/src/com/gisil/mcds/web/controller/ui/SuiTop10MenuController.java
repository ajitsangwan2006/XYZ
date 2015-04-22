
package com.gisil.mcds.web.controller.ui;
import java.sql.*;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SuiTop10MenuController {

	private ArrayList<String> _topContent = null;

	// public static final String _pagePath = "";

	HttpServletRequest _request = null;

	HttpServletResponse _response = null;

	public SuiTop10MenuController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {

		_request = aRequest;

		_response = aResponse;

		_topContent = new ArrayList<String>();

// Check if any catagories already exists
		
		_topContent = db_Block();

	}

	public ArrayList<String> getContents() {
		return _topContent;
	}

	public ArrayList<String> db_Block() {
		Connection conn = null;
		try {
			// Load and register Oracle driver
			// DriverManager.registerDriver(new
			// oracle.jdbc.driver.OracleDriver());
			// Establish a connection

			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Establish a connection
			System.out.println("Drivers Successfully Loaded");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.20.20:1521:pluto", "osapptmp",
					"osapptmp");

			// conn =
			// DriverManager.getConnection("jdbc:oracle:thin:@192.168.20.20:1521:pluto",
			// "osapptmp", "osapptmp");
			System.out.println("Drivers Successfully Loaded");
			Statement s = conn.createStatement();
			ResultSet rs = s
					.executeQuery("select name from catagory");
			while (rs.next()) {
				_topContent.add(rs.getString("name"));

			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return _topContent;
	}

}