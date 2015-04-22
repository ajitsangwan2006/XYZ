package com.gisil.mcds.web.servlet.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.web.controller.ui.SuiSearchTrxController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.SaveAsExcelReport;

public class SaveToExcelFileServlet extends MCDSServiceServlet {

	/**serialVersionUID*/
	private static final long serialVersionUID = 1577661060536999256L;
	
	public static final String SERVLET_PATH = "saveToExcelFile";

	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
			{
			
		try {
			String fileName = request.getParameter("filename");
			List trxList = (List) request.getSession().getAttribute("trxList");
			
			if ( trxList != null )
			{
				request.getSession().removeAttribute( "trxlist");
			}
			else
			{
				throw new MCDSException( "No Record Found" );
			}
			
			HSSFWorkbook report = (HSSFWorkbook) SaveAsExcelReport.exportData(
					trxList, fileName);
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Type", "application/vnd.ms-excel");
			response.addHeader("Content-Disposition",
					"attachment;filename=Report.xls");
			OutputStream out = response.getOutputStream();
			report.write(out);
			out.flush();
			
		} catch (MCDSException exp) {
			_log.info("Exception " + exp.toString());
			goToErrorPage(request, response, exp.getMessage(),
					SuiSearchTrxController.PAGE_PATH);
		}
	}

}
