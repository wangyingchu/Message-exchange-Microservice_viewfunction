package com.viewfunction.messageEngine.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.viewfunction.messageEngine.exception.MessageEngineException;
import com.viewfunction.messageEngine.exchange.GroupMessageRealTimePersistanceListener;
import com.viewfunction.messageEngine.exchange.GlobalMessageRealTimePersistanceListener;
import com.viewfunction.messageEngine.exchange.PersonalMessageRealTimePersistanceListener;
import com.viewfunction.messageEngine.messageService.RealTimeNotificationListener;
import com.viewfunction.messageEngine.messageService.RealTimeNotificationReceiver;
import com.viewfunction.messageEngine.messageService.util.factory.MessageComponentFactory;
import com.viewfunction.messageExchangeService.util.RuntimeEnvironmentHandler;
import org.apache.cayenne.configuration.server.ServerRuntime;

@WebListener
public class ServiceResourceInitializer implements ServletContextListener{
	
	private static String _GLOBAL_TOPIC_ADDRESS= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_GLOBAL_TOPIC_ADDRESS);
	private static String _GLOBAL_TOPIC_CONFIG= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_GLOBAL_TOPIC_CONFIG);
	private static String _GROUP_QUEUE_ADDRESS= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_GROUP_QUEUE_ADDRESS);
	private static String _GROUP_QUEUE_CONFIG= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_GROUP_QUEUE_CONFIG);
	private static String _PERSONAL_QUEUE_ADDRESS= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_PERSONAL_QUEUE_ADDRESS);
	private static String _PERSONAL_QUEUE_CONFIG= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_PERSONAL_QUEUE_CONFIG);
	private RealTimeNotificationReceiver personalMessageReceiver;
	private RealTimeNotificationReceiver groupMessageReceiver;
	private RealTimeNotificationReceiver globalMessageReceiver;
	private List<RealTimeNotificationReceiver> addOnMessageExchangePortReceiverList;
	private static String cayenneDataBaseProjectFile= RuntimeEnvironmentHandler.getApplicationRootPath()+"dataPersistance/cayenne.xml";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			personalMessageReceiver.stopReceive();
			groupMessageReceiver.stopReceive();
			globalMessageReceiver.stopReceive();
			if(addOnMessageExchangePortReceiverList!=null){
				for(RealTimeNotificationReceiver realTimeNotificationReceiver:addOnMessageExchangePortReceiverList){
					realTimeNotificationReceiver.stopReceive();
				}				
			}
            ServerRuntime cayenneRuntime=ServiceResourceHolder.getCayenneServerRuntime();
			if(cayenneRuntime!=null){
                cayenneRuntime.shutdown();
            }
		} catch (MessageEngineException e) {			
			e.printStackTrace();
		}	
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
        ServerRuntime cayenneRuntime= new ServerRuntime(cayenneDataBaseProjectFile);
        ServiceResourceHolder.setCayenneServerRuntime(cayenneRuntime);
		PersonalMessageRealTimePersistanceListener personalMessageRealTimePersistanceListener=new PersonalMessageRealTimePersistanceListener();		
		personalMessageReceiver= MessageComponentFactory.
				createRealTimeNotificationReceiver(_PERSONAL_QUEUE_ADDRESS,_PERSONAL_QUEUE_CONFIG, personalMessageRealTimePersistanceListener);
		GroupMessageRealTimePersistanceListener groupMessageRealTimePersistanceListener=new GroupMessageRealTimePersistanceListener();			
		groupMessageReceiver=MessageComponentFactory.
				createRealTimeNotificationReceiver(_GROUP_QUEUE_ADDRESS,_GROUP_QUEUE_CONFIG, groupMessageRealTimePersistanceListener);
		GlobalMessageRealTimePersistanceListener globalMessageRealTimePersistanceListener=new GlobalMessageRealTimePersistanceListener();
		globalMessageReceiver=MessageComponentFactory.
				createRealTimeNotificationReceiver(_GLOBAL_TOPIC_ADDRESS,_GLOBAL_TOPIC_CONFIG, globalMessageRealTimePersistanceListener);
		try {
			personalMessageReceiver.startReceive();
			groupMessageReceiver.startReceive();
			globalMessageReceiver.startReceive();			
			List<ExchangePortInfo> exchangePortInfoList= PropertyHandler.getAddOnExchangePortInfo();
			if(exchangePortInfoList.size()>0){
				addOnMessageExchangePortReceiverList=new ArrayList<RealTimeNotificationReceiver>();
				for(ExchangePortInfo currentExchangePortInfo:exchangePortInfoList){
					Class<?> reflectionClass=Class.forName(currentExchangePortInfo.getPortListenerClassName());
					RealTimeNotificationListener currentRealTimePersistanceListener=(RealTimeNotificationListener)reflectionClass.newInstance();					
					RealTimeNotificationReceiver currentRealTimeNotificationReceiver=MessageComponentFactory.
							createRealTimeNotificationReceiver(currentExchangePortInfo.getPortAddress(),currentExchangePortInfo.getPortConfig(), currentRealTimePersistanceListener);
					addOnMessageExchangePortReceiverList.add(currentRealTimeNotificationReceiver);					
				}				
			}
			if(addOnMessageExchangePortReceiverList!=null){
				for(RealTimeNotificationReceiver realTimeNotificationReceiver:addOnMessageExchangePortReceiverList){
					realTimeNotificationReceiver.startReceive();
				}				
			}			
		} catch (MessageEngineException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (InstantiationException e) {			
			e.printStackTrace();
		} catch (IllegalAccessException e) {			
			e.printStackTrace();
		}		
	}
}