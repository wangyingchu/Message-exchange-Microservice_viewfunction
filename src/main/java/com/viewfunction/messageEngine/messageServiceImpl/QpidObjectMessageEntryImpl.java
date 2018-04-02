package com.viewfunction.messageEngine.messageServiceImpl;

import java.util.Map;

import com.viewfunction.messageEngine.messageService.ObjectMessageEntry;

public class QpidObjectMessageEntryImpl implements ObjectMessageEntry{
	
	private Map<String, Object> messageData;
	private Map<String, Object> messageProperty;
	private String messageReplyToAddress;
	private long messageTimeStamp;
	
	public QpidObjectMessageEntryImpl(Map<String, Object> messageData,Map<String, Object> messageProperty,String messageReplyToAddress,long messageTimeStamp){
		this.messageData=messageData;
		this.messageProperty=messageProperty;
		this.messageReplyToAddress=messageReplyToAddress;
		this.messageTimeStamp=messageTimeStamp;
	}

	@Override
	public Map<String, Object> getMessageData() {		
		return messageData;
	}

	@Override
	public Map<String, Object> getMessageProperty() {		
		return messageProperty;
	}

	@Override
	public long getMessageTimeStamp() {		
		return messageTimeStamp;
	}

	@Override
	public String getMessageReplyToAddress() {		
		return messageReplyToAddress;
	}
}