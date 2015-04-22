package com.gisil.mcds.web.servlet.wap;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gisil.mcds.dmi.PurchaseTrxResponse;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.transaction.Transaction;
import com.gisil.mcds.transaction.TrxStatus;
import com.gisil.mcds.web.controller.wap.ConfirmDetailController;
import com.gisil.mcds.web.controller.wap.TransactionStatusController;
import com.gisil.mcds.web.servlet.MCDSServiceServlet;
import com.gisil.mcds.web.util.ErrorMessages;

public class TransactionServlet extends MCDSServiceServlet {

	/** serialVersionUID */
	private static final long serialVersionUID = 4294877324216454566L;

	public static final String SERVLET_PATH = "TransactionServlet";

	private int retryAvl;

	@Override
	protected void processIt(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		try 
		{
			MCDSRemote _remote = getMCDSRemote();
			Transaction trx = (Transaction) aRequest.getSession().getAttribute(
					"trx");
			trx.setRetryAvl(Integer.parseInt(aRequest.getParameter("retry")));
			trx = _remote.doPurchase(trx);
			PurchaseTrxResponse trxResponse = new PurchaseTrxResponse();
			trxResponse.setContent(trx.getSkuName());
			trxResponse.setDate(trx.getResponseTs());
			trxResponse.setPrice(trx.getTotalAmt().doubleValue());
			trxResponse.setSkuCode(trx.getSku());
			trxResponse.setStatus(trx.getTrxStatus());
			trxResponse.setTrxId(trx.getTrxId());
			trxResponse.setResponseId(trx.getResponesCode());
			trxResponse.setTerminalId(trx.getMerchantId());
			trxResponse.setCmsName(trx.getCMSName());
			trxResponse.setCmsValue(trx.getCMSValue().doubleValue());
			
			String trxStatusMessage = getTrxStatusMessage( trxResponse );
			
			aRequest.getSession().removeAttribute("trx");
			if (TrxStatus.BUYING.equals(trx.getTrxStatus())
					|| TrxStatus.BUYING_DENIED.equals(trx.getTrxStatus())) {
				dispatcher = aRequest
						.getRequestDispatcher(TransactionStatusController.PAGE_PATH);
				aRequest.setAttribute("trxResponse", trxResponse);
			} else if (TrxStatus.SERVICE_EX == trx.getTrxStatus()
					|| TrxStatus.SERVICE_EX_RETRY == trx.getTrxStatus()) {

				retryAvl = trx.getRetryAvl().intValue();
				if (retryAvl > 0) {
					aRequest.setAttribute("retry", retryAvl - 1);
					trx.setRetryAvl(retryAvl - 1);
					aRequest.setAttribute("trx", aRequest.getAttribute("trx"));
					dispatcher = aRequest
							.getRequestDispatcher(ConfirmDetailController.PAGE_PATH);
				} else {
					aRequest.setAttribute("errorMessage", trx.getTrxStatus()
							.toDBLiteral());
					dispatcher = aRequest
							.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
				}

			} else 
				
			{
				aRequest.setAttribute( "errorMessage", trxStatusMessage );
				dispatcher = aRequest.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
			}
				
			/**
				if (TrxStatus.AUTH_DENIED.equals(trx.getTrxStatus())) {

				aRequest
						.setAttribute("errorMessage", ErrorMessages.AUTH_DENIED);
				dispatcher = aRequest
						.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
			} else if (TrxStatus.ACCOUNTED_EX.equals(trx.getTrxStatus())) {
				aRequest.setAttribute("errorMessage",
						ErrorMessages.ACCOUNTED_EX);
				dispatcher = aRequest
						.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
			} else if (TrxStatus.DEBIT_EX.equals(trx.getTrxStatus())) {
				aRequest.setAttribute("errorMessage", ErrorMessages.DEBIT_EX);
				dispatcher = aRequest
						.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
			} else {
				aRequest.setAttribute("errorMessage", trx.getResponseMessage());
				dispatcher = aRequest
						.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
			}
			**/
		} catch (Exception e) {
			_log.info("Server Error: " + e.getMessage());
			aRequest.setAttribute("errorMessage",
					ErrorMessages.SERVER_NOT_READY);
			dispatcher = aRequest
					.getRequestDispatcher(ErrorMessages.ERROR_PAGE);
		}
		
		dispatcher.forward(aRequest, aResponse);

	}
	
	private String getTrxStatusMessage( PurchaseTrxResponse trxResponse  )
	{
		String message = null;
		
		TrxStatus trxStatus = trxResponse.getStatus();
		
		switch( trxStatus )
		{
		
		case  NEW:
		{
			message = ErrorMessages.UNDEFINED_ERROR;
			break;
		}
		case  AUTHORIZED:
		{
			message =  ErrorMessages.UNDEFINED_ERROR;
			break;
		}
		case  AUTH_DENIED:
		{
			message = ErrorMessages.AUTH_DENIED;
			break;
		}
		case  DEBIT_EX:
		{
			message = ErrorMessages.DEBIT_EX;
			break;
		}
		case  DEBITED:
		{
			message =  ErrorMessages.UNDEFINED_ERROR;
			break;
		}
		case  ACCOUNTED_EX:
		{
			message = ErrorMessages.ACCOUNTED_EX;
			break;
		}
		case  ACCOUNTED:
		{
			message =  ErrorMessages.UNDEFINED_ERROR;
			break;
		}
		case  BUYING_SUBMITTED:
		{
			message =  ErrorMessages.UNDEFINED_ERROR;
			break;
		}
		case  BUYING:
		{
			message =  ErrorMessages.UNDEFINED_ERROR;
			break;
		}
		case  BUYING_DENIED:
		{
			int responseCode = Integer.parseInt( trxResponse.getResponseId() );
			
			switch( responseCode )
			{
				case 0 :
			{
			    message =  ErrorMessages.INSUFFICIENT_LOGIN_DATA;
				break;
			}
			case 1 :
			{
				message =  ErrorMessages.AUTNETICATION_FAILED;
				break;
			}
			case 2 :
			{
				message =  ErrorMessages.MSISDN_MISSING;
				break;
			}
			case 3 :
			{
				message =  ErrorMessages.PHONE_MAKE_MISSING;
				break;
			}
			case 4 :
			{
				message =  ErrorMessages.HANDSET_MODEL_MISSING;
				break;
			}
			case 5:
			{
				message =  ErrorMessages.PACKAGEID_SKU_MISSING;
				break;
			}
			case 6 :
			{
				message =  ErrorMessages.INVALID_PACAKGE_REQUEST;
				break;
			}
			case 7 :
			{
				message =  ErrorMessages.SYSTEM_ERROR;
				break;
			}
			case 8 :
			{
				message =  ErrorMessages.POS_INDENTIFIER_MISSING;
				break;
			}
			case 9 :
			{
				message =  ErrorMessages.STORE_INDENTIFIER_MISSING;
				break;
			}
			
			case 10 :
			{
				message =  ErrorMessages.MERCHANT_TRANSACTION_ID_MISSING;
				break;
			}
			case 11:
			{
				message =  ErrorMessages.QUOTA_EXHAUSTED;
				break;
			}
			case 12 :
			{
				message =  ErrorMessages.ACCESS_DENIED;
				break;
			}
			case 13 :
			{
				message =  ErrorMessages.RESERVED_FOR_INTERNAL_ERROR_HANDLING;
				break;
			}
			case 14 :
			{
				message =  ErrorMessages.HANDSET_NOT_SUPPORTED;
				break;
			}
			case 15 :
			{
				message =  ErrorMessages.ALREADY_PURCHASED;
				break;
			}
			case 16 :
			{
				message =  ErrorMessages.DUPLICATE_MERCHANT_TRANID;
				break;
			}
			case 17 :
			{
				message =  ErrorMessages.OPERATOR_NOT_SUPPORTED;
				break;
			}
		}
			
			break;
		}
		case  SERVICE_EX:
		{
			message =  ErrorMessages.UNDEFINED_ERROR;
			break;
		}
		
		case  BUYING_RETRY_SUBMITTED:
		{
			message =  ErrorMessages.UNDEFINED_ERROR;
			break;
		}
		case  SERVICE_EX_RETRY:
		{
			message =  ErrorMessages.UNDEFINED_ERROR;
			break;
		}
		case  SETTELED:
		{
			message =  ErrorMessages.UNDEFINED_ERROR;
			break;
		}
		
		}
		return message;
	}

}
