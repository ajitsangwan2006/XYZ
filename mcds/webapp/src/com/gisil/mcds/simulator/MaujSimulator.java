package com.gisil.mcds.simulator;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gisil.mcds.util.LogUtil;

public class MaujSimulator extends HttpServlet 
{
	private final static long  SLEEP_TIME = 200;
	 protected Logger _log;
	 
	public void init( ServletConfig config  ) throws ServletException
	{
		_log = LogUtil.getLogger(getClass());
	}
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException
	{
		
		String skuCode = request.getParameter( "sku" );
		
		if ( skuCode != null && !skuCode.equals("") )
		{
			char lastDigit = getSKUCodeLastDigit( skuCode );
			String responseMessage = getSimulatorResponse( lastDigit );
			_log.info( "--- SKU Code Last Digit : " +lastDigit + " " + responseMessage );
			
			if ( responseMessage.equals( "sleep" ) )
			{
				try
				{
					//responseMessage = "SUCCESS:34707";
					System.out.println( "Before Sleep "  );
					Thread.sleep(SLEEP_TIME);
					System.out.println( "after Sleep "  );
				}
				catch( InterruptedException e )
				{
					System.out.println( "Exception Occured " + e.toString() );
				}
			}
				
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write( responseMessage.getBytes());
	   }
	}
	
	private String getSimulatorResponse( char lastDigit )
	{
		String message = "Not Defined";
		
		if ( lastDigit == '1' )
		{
			message = "SUCCESS:34707";
		}else if ( lastDigit == '3' )
		{
			message = "Error:02";
		}else if ( lastDigit == '9' )
		{
			message = "sleep";			
		}
		else
		{
			message ="sleep";
		}
		
		return message;
	}
		
	
	private char getSKUCodeLastDigit( String skuCode )
	{
		char lastDigit;
		
		if ( skuCode.length() == 1 )
		{
			lastDigit = skuCode.charAt(0);
		}else
		{
			lastDigit = skuCode.charAt( skuCode.length() - 1 );
		}
		return lastDigit;
	}
	
}
