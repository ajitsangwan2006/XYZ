package com.gisil.mcds.weblogic;



/**
 * Copyright, GISIL	2007	All rights reserved.
 * 
 * The copyright in this work is vested in GISIL and the 
 * information contained herein is confidential.  This 
 * work (either in whole or in part) must not be modified, 
 * reproduced, disclosed or disseminated to others or used 
 * for purposes other than that for which it is supplied, 
 * without the prior written permission of GISIL.  If this 
 * work or any part hereof is furnished to a third party by 
 * virtue of a contract with that party, use of this work by 
 * such party shall be governed by the express contractual 
 * terms between the GISIL which is a party to that contract 
 * and the said party.
 *
 */





import java.util.logging.Logger;

import com.gisil.mcds.base.MCDSLauncher;

import weblogic.application.ApplicationLifecycleEvent;
import weblogic.application.ApplicationLifecycleListener;


	
	public class WeblogicMCDSListener extends ApplicationLifecycleListener
	{
		
		private MCDSLauncher m_launcher = null;
		private static Logger _log;
		
		
		/**
		 * Pre start
		 */
		public void preStart(ApplicationLifecycleEvent evt) 
		{
			
			_log = Logger.getLogger ("MCDS Logger");
			_log.info("Initializing mcds .......");
			System.out.println("into preStart........................");
			
			
			try
			{
			
				m_launcher = MCDSLauncher.getLauncher();
				m_launcher.launch("mcds.config");
				
				_log.info("MCDS Initialization Complete ....");
			}
			catch (Throwable t)
			{
				t.printStackTrace();
				_log.severe ("Exception occured while initialzing MCDS .....");
			}
		}

		
		/**
		 * Pre-Stop
		 */
		public void preStop (ApplicationLifecycleEvent evt)
		{
			Logger logger = Logger.getLogger ("MCDS Logger");
			_log.info("Stopping MCDS .......");
			System.out.println("into preStop........................");
			
			try
			{
				if (m_launcher != null)
					
				//m_launcher.shutdown();
				
				logger.info("MCDS Shutdown gracefully .....");
			}
			catch (Throwable t)
			{
				t.printStackTrace();
				logger.severe ("Exception occured while stopping MCDS .....");
			}
			finally
			{
				m_launcher = null;
			}
		}
	}



