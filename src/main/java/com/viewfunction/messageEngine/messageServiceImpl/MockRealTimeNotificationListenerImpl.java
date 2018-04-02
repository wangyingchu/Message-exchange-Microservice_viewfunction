package com.viewfunction.messageEngine.messageServiceImpl;

import com.viewfunction.messageEngine.messageService.ObjectMessageEntry;
import com.viewfunction.messageEngine.messageService.RealTimeNotificationListener;
import com.viewfunction.messageEngine.messageService.TextMessageEntry;

public class MockRealTimeNotificationListenerImpl implements RealTimeNotificationListener{

	@Override
	public void receivedTextMessage(TextMessageEntry textMessageEntry) {
		System.out.println(textMessageEntry);		
	}

	@Override
	public void receivedObjectMessage(ObjectMessageEntry objectMessageEntry) {
		System.out.println(objectMessageEntry);			
	}
}