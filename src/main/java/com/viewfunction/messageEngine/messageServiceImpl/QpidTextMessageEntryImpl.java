package com.viewfunction.messageEngine.messageServiceImpl;

import com.viewfunction.messageEngine.messageService.TextMessageEntry;

public class QpidTextMessageEntryImpl implements TextMessageEntry{
	
	private long messageTimeStamp;
	private String messageReplyToAddress;
	private String messageText;
	
	public QpidTextMessageEntryImpl(String messageText,String messageReplyToAddress,long messageTimeStamp){
		this.messageTimeStamp=messageTimeStamp;
		this.messageReplyToAddress=messageReplyToAddress;
		this.messageText=messageText;
	}

	@Override
	public long getMessageTimeStamp() {		
		return this.messageTimeStamp;
	}

	@Override
	public String getMessageReplyToAddress() {		
		return this.messageReplyToAddress;
	}

	@Override
	public String getMessageText() {		
		return this.messageText;
	}
}