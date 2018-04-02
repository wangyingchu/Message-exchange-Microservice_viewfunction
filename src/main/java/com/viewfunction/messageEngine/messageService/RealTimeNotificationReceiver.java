package com.viewfunction.messageEngine.messageService;

import com.viewfunction.messageEngine.exception.MessageEngineException;

public interface RealTimeNotificationReceiver {

	public void startReceive() throws MessageEngineException;
	
	public void stopReceive() throws MessageEngineException;
	
	public void setRealTimeNotificationListener(RealTimeNotificationListener realTimeNotificationListener);
}
