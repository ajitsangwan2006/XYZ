package com.gisil.mcds.config;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.gisil.mcds.base.MCDSException;
import com.gisil.mcds.util.LogUtil;

public class ConfigurationManagerImpl implements IConfigurationManager 
{

	public static HashMap<String, String> transactionStatus = new HashMap<String, String>();
	private Map<String, ConfigDef> _configurations;
	
	private ConfigDBManager  store;
	private static IConfigurationManager INSTANCE;
	
	private Logger _log;
	
	
	public ConfigurationManagerImpl( Properties aprops )	
	{
			if ( aprops == null )
			throw new IllegalArgumentException( "Default properties can not be null" );
			
			_configurations = new HashMap<String, ConfigDef>();
			Enumeration<?> keys = aprops.propertyNames();
			
			while( keys.hasMoreElements() )
			{
				String key = ( String )keys.nextElement();
				String value = aprops.getProperty( key );
				ConfigDef configDef = new ConfigDef();
				configDef.setParamId( -1 );
				configDef.setParamName( key );
				configDef.setParamValue( value );
				_configurations.put( key, configDef );
				
			}
			_log = LogUtil.getLogger( getClass());
			INSTANCE = this;
	}
	
	public static IConfigurationManager getConfigurationManager()
	{
		return INSTANCE;
	}

	public synchronized static String getTransactionStatus(String key) {
		String status = (String) transactionStatus.get(key);
		if (status != null) {
			return status;
		} else {
			return "UNKNOWN ERROR";
		}
	}
		
	public String getConfig(String akey) throws MCDSException 
	{
		ConfigDef configDef = getConfigDef( akey );
		return configDef.getParamValue();
		
	}
	
	private ConfigDef getConfigDef( String akey ) throws MCDSException
	{
		ConfigDef def = _configurations.get( akey );
		
		if ( def == null )
			throw new MCDSException( "No Configuration Exist with key [ " + akey + " ]" );
		
		return def;
	}
	

	public Number getConfigAsNumber(String key) throws MCDSException {
		
		return ( Number )getConfigDef( key ).getValueAsObject();
	}

	public boolean getConfigAsBoolean(String key) throws MCDSException {
		
		return ( Boolean )getConfigDef( key ).getValueAsObject();
	}

	public void reload() throws MCDSException 
	{
		Collection<ConfigDef>  config = store.getConfigurations();
		
		for( ConfigDef  def : config )
		{
			_configurations.put( def.getParamName(), def );
		}
		_log.info(" MCDS Configuration loaded # " + config.size());
		
		// load values in transaction status
		
		loadTransactionStatus();
	}

	public void flush() throws MCDSException
	{
		store.updateConfig(_configurations.values());
	}

	public void setConfig(String key, String value) throws MCDSException 
	{
		ConfigDef def = getConfigDef( key );
		def.setParamValue( value );
	}

	public void applyChanges(Collection<ConfigInfo> configInfo) throws MCDSException {
		// TODO Auto-generated method stub
		
	}

	public Collection<ConfigInfo> getConfiguration() throws MCDSException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateConfiguration(Collection<ConfigInfo> configInfo) throws MCDSException {
		// TODO Auto-generated method stub
		
	}

	public void preStartup() throws MCDSException 
	{
		store = new ConfigDBManager();
		
	}

	public void postStartup() throws MCDSException 
	{
		reload();
	}

	public void shutDown() 
	{
		_log.info( "-----Configuration Manager got Shut Down -----" );
		store = null;
	}
	
	private void loadTransactionStatus(){
	
		transactionStatus.put("00","SUCCESS");
		transactionStatus.put("01","INSUFFICIENT LOGIN DATA");		
		transactionStatus.put("02","AUTHENTICATION FAILED");		
		transactionStatus.put("03","MSISDN MISSING");		
		transactionStatus.put("04","PHONE MAKE MISSING");		
		transactionStatus.put("05","HANDSET MODEL MISSING");		
		transactionStatus.put("06","PACKAGEID/SKU MISSING");		
		transactionStatus.put("07","INVALID PACKAGE REQUEST");
		transactionStatus.put("08","SYSTEM ERROR, PLS TRY LATER");
		transactionStatus.put("09","POS INDENTIFIER MISSING");		
		transactionStatus.put("10","STORE INDENTIFIER MISSING");
		transactionStatus.put("11","MERCHANT TRANSACTION ID MISSING");
		transactionStatus.put("12","QUOTA EXHAUSTED");
		transactionStatus.put("13","ACCESS DENIED");
		transactionStatus.put("14","RESERVED FOR INTERNAL ERROR HANDLING");
		transactionStatus.put("15","HANDSET NOT SUPPORTED");
		transactionStatus.put("16","PACKAGEID/SKU ALREADY PURCHASED BY THIS USER");
		transactionStatus.put("17","DUPLICATE MERCHANT TRANID");
		transactionStatus.put("18","OPERATOR NOT SUPPORTED");
	}

}
