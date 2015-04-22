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

package com.gisil.mcds.web.listener;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.config.MCDSConfig;
import com.gisil.mcds.ejb.MCDSRemote;
import com.gisil.mcds.util.AppServer;
import com.gisil.mcds.web.util.Utils;

/**
 * 
 * @author ajit singh
 *
 */
public class MCDSTomcatListener implements ServletContextListener 
{
	
	private Logger _log;

	/**
	 * contextInitialized
	 */
	public void contextInitialized(ServletContextEvent contextEvent ) 
	{
		_log = Logger.getLogger("MCDSTomcatListener");
		
		try 
		{
			
			MCDSRemote _remote = AppServer.getAppServer( contextEvent.getServletContext() ).getMCDSServer();
			
			if ( _remote == null )
			{
				throw new MCDSException( "Application  Server not started " );
			}
			else
			{
				Utils.CODE_LIMIT = _remote.getConfigNumber( MCDSConfig.TERMINAL_DISPLAY_CODE_LIMIT ).intValue();
				Utils.TERMINAL_DISPLAY_LABEL_MAX_LEN = _remote.getConfigNumber( MCDSConfig.TERMINAL_DISPLAY_LABEL_MAX_LEN ).intValue();
				Utils.TERMINAL_DISPLAY_MAX_LEN = _remote.getConfigNumber( MCDSConfig.TERMINAL_DISPLAY_MAX_LEN ).intValue();
				Utils.TERMINAL_ROWS_LIMIT = _remote.getConfigNumber( MCDSConfig.TERMINAL_ROWS_LIMIT ).intValue();
				Utils.TITLE_LIMIT = _remote.getConfigNumber( MCDSConfig.TERMINAL_DISPLAY_TITLE_LIMIT ).intValue();
				Utils.DEFAULT_COUNTRY_CODE = _remote.getConfigString( MCDSConfig.DEFAULT_COUNTRY_CODE );
			}
			
		
		} catch (MCDSException mcdsExp) 
		{
			_log.info( "Exception Occured in Tomcat Listener :" +mcdsExp.toString() );

		}
		catch( RemoteException remoteExp )
		{
			_log.info( "Exception Occured :" + remoteExp.toString() );
		}

	}

	/**
	 * contextDestroyed
	 */
	public void contextDestroyed(ServletContextEvent arg0) 
	{

	}

}
