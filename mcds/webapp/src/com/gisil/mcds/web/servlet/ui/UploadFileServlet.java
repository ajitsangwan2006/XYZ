package com.gisil.mcds.web.servlet.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;

public class UploadFileServlet extends MCDSServiceServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3863769258570800257L;
	
	public static final String SERVLET_PATH = "uploadFileServlet";
	
	private static final String FILE_DIR = "D:\\ashok\\";
	/**
	 * 
	 */

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String contentType = request.getContentType();
		RequestDispatcher requestDispatcher = null;
		try
		{
		if ((contentType != null)
				&& (contentType.indexOf("multipart/form-data") >= 0)) 
		{

			String boundryValue = getBoundry(contentType);
			ServletInputStream servletInputStream = request.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					servletInputStream);
			BufferedReader buffReader = new BufferedReader(inputStreamReader);
			String ActualData = "";
			String headerInfo = null;
			boolean isActualData = false;

			String line = null;

			while ((line = buffReader.readLine()) != null) {

				if (line.indexOf(boundryValue) != -1) {
					isActualData = false;
				}
				if (isActualData && !line.equals("") ) 
				{
					if ( ActualData.equals("") )
					{
						ActualData = line;
					}
					else
					{
						ActualData = ActualData + "\r\n" + line;
					}
					
				} else {
					headerInfo = headerInfo + " " + line;
				}

				if (line.indexOf("Content-Type") != -1) {
					isActualData = true;
				}

			}

			String fileName = getFileName(headerInfo);
			String fileFullpath = FILE_DIR + fileName;
			BufferedWriter buffWriter = new BufferedWriter(new FileWriter(
					fileFullpath));
			buffWriter.write(ActualData);
			buffWriter.close();
			buffReader.close();
			requestDispatcher = request.getRequestDispatcher( "suiFileUploadedSuccessfully.jsp" );
			requestDispatcher.include( request, response );
		}
		else
		{
			throw new MCDSException( "Exception Occured while UpLoading file" );
		}
		
		}
		catch( MCDSException e )
		{
			_log.info( "Exception" + e.toString() );
			request.setAttribute("backUrl", "uploadReconFile.jsp" );
			request.setAttribute("message", e.toString() );
			requestDispatcher = request.getRequestDispatcher( "suiMessage.jsp" );
			requestDispatcher.include( request, response );
			
		}
		catch( FileNotFoundException e )
		{
			_log.info("Exception" + e.toString() );
			request.setAttribute("backUrl", "uploadReconFile.jsp" );
			request.setAttribute("message", "Output Directory Not Exist : " + FILE_DIR );
			requestDispatcher = request.getRequestDispatcher( "suiMessage.jsp" );
			requestDispatcher.include( request, response );
		}
	}

	private String getBoundry(String contentType) {
		String boundry = "";
		int index = contentType.lastIndexOf("=");
		boundry = contentType.substring(index + 1);
		boundry = boundry.trim();
		return boundry;
	}

	private String getFileName(String headerInfo) throws MCDSException 
	{
		String fileName = "";
		int index = headerInfo.indexOf("filename=\"");
		headerInfo = headerInfo.substring(index + 10);
		String filePath = headerInfo.substring(0, headerInfo.indexOf("\""));
		index = filePath.lastIndexOf("\\");
		fileName = filePath.substring(index + 1);
		
		if ( fileName == null || fileName.equals("" ) )
		{
			throw new MCDSException( "Please Select a File!");
		}
		return fileName;
	}

}
