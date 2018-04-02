package com.viewfunction.messageEngine.exchange;

import com.viewfunction.messageEngine.messageService.ObjectMessageEntry;
import com.viewfunction.messageEngine.messageService.RealTimeNotificationListener;
import com.viewfunction.messageEngine.messageService.TextMessageEntry;

public class GlobalMessageRealTimePersistanceListener implements RealTimeNotificationListener{

	@Override
	public void receivedTextMessage(TextMessageEntry textMessageEntry) {
		System.out.println(textMessageEntry);		
	}

	@Override
	public void receivedObjectMessage(ObjectMessageEntry objectMessageEntry) {	
		System.out.println("GlobalMessageRealTimePersistanceListener");
		String messageCatalogTypeProperty=objectMessageEntry.getMessageProperty().get(MessageServiceConstant.MESSAGESERVICE_MessageCatalog)!=null?
				objectMessageEntry.getMessageProperty().get(MessageServiceConstant.MESSAGESERVICE_MessageCatalog).toString():null;
		if(messageCatalogTypeProperty!=null&messageCatalogTypeProperty.equals(MessageServiceConstant.MESSAGESERVICE_MessageCatalog_BussinessExchangeMessage)){
			System.out.println(messageCatalogTypeProperty);
		}				
		System.out.println(objectMessageEntry);			
	}
}