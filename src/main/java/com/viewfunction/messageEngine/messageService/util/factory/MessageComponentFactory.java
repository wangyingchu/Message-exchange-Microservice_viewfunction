package com.viewfunction.messageEngine.messageService.util.factory;

import java.util.Map;

import com.viewfunction.messageEngine.messageService.MessageUtil;
import com.viewfunction.messageEngine.messageService.ObjectMessageEntry;
import com.viewfunction.messageEngine.messageService.RealTimeNotificationListener;
import com.viewfunction.messageEngine.messageService.RealTimeNotificationReceiver;
import com.viewfunction.messageEngine.messageService.TextMessageEntry;
import com.viewfunction.messageEngine.messageServiceImpl.QpidMessageUtilImpl;
import com.viewfunction.messageEngine.messageServiceImpl.QpidObjectMessageEntryImpl;
import com.viewfunction.messageEngine.messageServiceImpl.QpidRealTimeNotificationReceiverImpl;
import com.viewfunction.messageEngine.messageServiceImpl.QpidTextMessageEntryImpl;

public class MessageComponentFactory {
	
	public static MessageUtil createMessageUtil(){
		return new QpidMessageUtilImpl();
	}
	
	public static ObjectMessageEntry createObjectMessageEntry(Map<String, Object> messageData,Map<String, Object> messageProperty,String messageReplyToAddress,long messageTimeStamp){
		return new QpidObjectMessageEntryImpl(messageData,messageProperty,messageReplyToAddress,messageTimeStamp);
	}
	
	public static TextMessageEntry createTextMessageEntry(String messageText,String messageReplyToAddress,long messageTimeStamp){
		return new QpidTextMessageEntryImpl(messageText,messageReplyToAddress,messageTimeStamp);
	}
	
	public static RealTimeNotificationReceiver createRealTimeNotificationReceiver(String destinationName,String destinationOption,RealTimeNotificationListener realTimeNotificationListener){
		return new QpidRealTimeNotificationReceiverImpl(destinationName,destinationOption,realTimeNotificationListener);		
	}
}