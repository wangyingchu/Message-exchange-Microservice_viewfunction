package com.viewfunction.messageEngine.messageService;

public interface RealTimeNotificationListener {	
	public void receivedTextMessage(TextMessageEntry textMessageEntry);
	public void receivedObjectMessage(ObjectMessageEntry objectMessageEntry);
}