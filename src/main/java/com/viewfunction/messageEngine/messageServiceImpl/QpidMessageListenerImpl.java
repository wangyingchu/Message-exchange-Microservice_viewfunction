package com.viewfunction.messageEngine.messageServiceImpl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import javax.jms.Message;

import com.viewfunction.messageEngine.messageService.ObjectMessageEntry;
import com.viewfunction.messageEngine.messageService.RealTimeNotificationListener;
import com.viewfunction.messageEngine.messageService.TextMessageEntry;
import com.viewfunction.messageEngine.messageService.util.factory.MessageComponentFactory;

public class QpidMessageListenerImpl implements MessageListener{
	RealTimeNotificationListener realTimeNotificationListener;
	public QpidMessageListenerImpl(RealTimeNotificationListener realTimeNotificationListener){
		this.realTimeNotificationListener=realTimeNotificationListener;
	}
	
	@Override
	public void onMessage(Message msg) {		
		try {
			if(msg instanceof MapMessage){			
				long timeStamp = msg.getJMSTimestamp();
				String replyToAddress=msg.getJMSReplyTo()!=null?msg.getJMSReplyTo().toString():null;
				MapMessage mapMessage=(MapMessage)msg;
				Map<String,Object> dataMap=new HashMap<String,Object>();				
				Enumeration objectValueKeyName=mapMessage.getMapNames();				
				while(objectValueKeyName.hasMoreElements()){
					String keyName=objectValueKeyName.nextElement().toString();												
					dataMap.put(keyName, mapMessage.getObject(keyName));						
				}
				Map<String,Object> propertyMap=new HashMap<String,Object>();
				Enumeration propertyValueKeyName=mapMessage.getPropertyNames();
				while(propertyValueKeyName.hasMoreElements()){						
					String keyName=propertyValueKeyName.nextElement().toString();						
					propertyMap.put(keyName, mapMessage.getObjectProperty(keyName));						
				}					
				ObjectMessageEntry objectMessageEntry=MessageComponentFactory.createObjectMessageEntry(dataMap, propertyMap, replyToAddress, timeStamp);	
				this.realTimeNotificationListener.receivedObjectMessage(objectMessageEntry);
			}
			if(msg instanceof TextMessage){		    		
	    		 long timeStamp=msg.getJMSTimestamp();
	    		 String replyToAddress=msg.getJMSReplyTo()!=null?msg.getJMSReplyTo().toString():null;
	    		 String textString=((TextMessage)msg).getText();				    	
	    		 TextMessageEntry textMessageEntry=MessageComponentFactory.createTextMessageEntry(textString, replyToAddress, timeStamp);
	    		 this.realTimeNotificationListener.receivedTextMessage(textMessageEntry);
			}		    	
		} catch (JMSException e) {				
			e.printStackTrace();
		}
	}
}