package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.dmi.TransactionSummaryInfo;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.util.CommonUtil;
import com.gisil.mcds.web.controller.wap.SummaryReportDetailController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.SessionData;

public class SummaryReportMenuServlet extends MCDSServiceServlet {

	/** serialVersionUID */
	private static final long serialVersionUID = 1606647379208084504L;

	public static final String SERVLET_PATH = "summaryReportMenuServlet";

	@Override
	protected void processIt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String summaryReportType = request.getParameter("summaryReportType");
		request.setAttribute("summaryReportType", summaryReportType);
		RequestDispatcher requestDispatcher = null;
		SessionData _sessionData = (SessionData) request.getSession()
				.getAttribute("sessionData");
		if (_sessionData == null) {
			_sessionData = new SessionData();
		}

		if (summaryReportType != null && summaryReportType.equals("date")) {
			requestDispatcher = request
					.getRequestDispatcher("dateRangeInputForm.jsp");
			requestDispatcher.forward(request, response);
		} else if (summaryReportType != null
				&& summaryReportType.equals("last")) {
			try {
				String limit = request.getParameter("limit");
				int recordLimit = Integer.parseInt(limit);
				String merchantPhone = CommonUtil
						.fixMobileNo(getMerchantDelNo(request));
				long terminalId = Long.parseLong(merchantPhone);
				MCDSRemote mcdsRemote = getMCDSRemote();
				
				if ( _sessionData.getMaxRecordsPerPage() == 0 )
				{
					_sessionData.setMaxRecordsPerPage( mcdsRemote.getConfigNumber( MCDSConfig.MAX_CONTENT_DISPLAY ).intValue() );
				}
				
				ArrayList<TransactionSummaryInfo> transactionDetailList = mcdsRemote
						.getTransactionList(recordLimit, terminalId);
				request.setAttribute("transactionsDetail",
						transactionDetailList);
				requestDispatcher = request
						.getRequestDispatcher(SummaryReportDetailController.PAGE_PATH);
				requestDispatcher.forward(request, response);
			} catch (MCDSException e) {
				_log.info("Exception Occured : " + e.getMessage());
				goToErrorPage(request, response, e.getMessage(), MCDSMenuServlet.SERVLET_PATH);
			} catch (NumberFormatException e) {
				_log.info("Exception Occured : " + e.getMessage());
				goToErrorPage(request, response, e.getMessage(), MCDSMenuServlet.SERVLET_PATH);
			}
		}
	}

}
