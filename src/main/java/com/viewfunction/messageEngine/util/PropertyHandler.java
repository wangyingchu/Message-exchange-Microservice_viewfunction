package com.viewfunction.messageEngine.util;

import com.viewfunction.messageExchangeService.util.RuntimeEnvironmentHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class PropertyHandler {
	private static Properties _properties;		
	public static String AMQP_VIRTUALHOST="AMQP_VIRTUALHOST";
	public static String AMQP_USERNAME="AMQP_USERNAME";
	public static String AMQP_USERPWD="AMQP_USERPWD";
	public static String AMQP_CLIENTID="AMQP_CLIENTID";
	public static String AMQP_BROKERLIST="AMQP_BROKERLIST";
	public static String AMQP_GLOBAL_TOPIC_ADDRESS="AMQP_GLOBAL_TOPIC_ADDRESS";
	public static String AMQP_GLOBAL_TOPIC_CONFIG="AMQP_GLOBAL_TOPIC_CONFIG";
	public static String AMQP_GROUP_QUEUE_ADDRESS="AMQP_GROUP_QUEUE_ADDRESS";
	public static String AMQP_GROUP_QUEUE_CONFIG="AMQP_GROUP_QUEUE_CONFIG";
	public static String AMQP_PERSONAL_QUEUE_ADDRESS="AMQP_PERSONAL_QUEUE_ADDRESS";
	public static String AMQP_PERSONAL_QUEUE_CONFIG="AMQP_PERSONAL_QUEUE_CONFIG";

    public static String AMQP_VIRTUALHOST_VALUE=null;
    public static String AMQP_USERNAME_VALUE=null;
    public static String AMQP_USERPWD_VALUE=null;
    public static String AMQP_CLIENTID_VALUE=null;
    public static String AMQP_BROKERLIST_VALUE=null;
	
	public static String getPerportyValue(String resourceFileName){		
		if(_properties==null){
			_properties=new Properties();
			try {
				_properties.load(new FileInputStream(RuntimeEnvironmentHandler.getApplicationRootPath()+"ServiceConfig.properties"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return _properties.getProperty(resourceFileName);
	}
	
	public static List<ExchangePortInfo> getAddOnExchangePortInfo(){	
		Enumeration propertyEm=_properties.propertyNames();	
		List<ExchangePortInfo> portInfoList=new ArrayList<ExchangePortInfo>();
		while(propertyEm.hasMoreElements()){
			String currentProperty=propertyEm.nextElement().toString();
			if(currentProperty.startsWith("AMQP_ADDON_EXCHANGEPORT_ADDRESS_")){
				String portName=currentProperty.substring(32, currentProperty.length());				
				ExchangePortInfo exchangePortInfo=new ExchangePortInfo(
					_properties.getProperty("AMQP_ADDON_EXCHANGEPORT_ADDRESS_"+portName),
					_properties.getProperty("AMQP_ADDON_EXCHANGEPORT_CONFIG_"+portName),
					_properties.getProperty("AMQP_ADDON_EXCHANGEPORT_LISTENER_"+portName)						
				);
				portInfoList.add(exchangePortInfo);				
			}			
		}
		return portInfoList;		
	}

	public static String getConnectionInfo(){
		if(AMQP_VIRTUALHOST_VALUE==null){
			AMQP_VIRTUALHOST_VALUE=!PropertyHandler.getPerportyValue(PropertyHandler.AMQP_VIRTUALHOST).equals("N/A")?
					PropertyHandler.getPerportyValue(PropertyHandler.AMQP_VIRTUALHOST):"";
		}
		if(AMQP_USERNAME_VALUE==null){AMQP_USERNAME_VALUE= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_USERNAME);}
		if(AMQP_USERPWD_VALUE==null){AMQP_USERPWD_VALUE= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_USERPWD);}
		if(AMQP_CLIENTID_VALUE==null){AMQP_CLIENTID_VALUE= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_CLIENTID);}
		if(AMQP_BROKERLIST_VALUE==null){AMQP_BROKERLIST_VALUE= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_BROKERLIST);}
		String connectionInfo="amqp://"+AMQP_USERNAME_VALUE+":"+AMQP_USERPWD_VALUE+"@"+AMQP_CLIENTID_VALUE+"/"+AMQP_VIRTUALHOST_VALUE+"?brokerlist="+AMQP_BROKERLIST_VALUE;
		return connectionInfo;
	}
}