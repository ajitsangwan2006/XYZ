package com.gisil.mcds.web.controller.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.web.controller.AbstractJspController;
import com.gisil.mcds.web.util.ErrorMessages;

public class SuiMessageController extends AbstractJspController {

	public static final String PAGE_PATH = "suiMessage.jsp";

	public static final String R_MESSAGE = "message";

	public static final String R_BACK_URL = "backUrl";

	private String _backURL;

	private String _message;

	/**
	 * @return Returns the _backURL.
	 */
	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		} else {
			return SuiMCDSMenuController.PAGE_PATH;
		}
	}

	public SuiMessageController(HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		super(aRequest, aResponse);
		// TODO Auto-generated constructor stub

		_backURL = (String) _request.getAttribute(R_BACK_URL);

		_message = (String) _request.getAttribute(R_MESSAGE);

	}

	/**
	 * @return Returns the _message.
	 */
	public String getMessage() {
		if (_message != null) {
			return _message;
		} else {
			return ErrorMessages.UNDEFINED_ERROR;
		}
	}

}
