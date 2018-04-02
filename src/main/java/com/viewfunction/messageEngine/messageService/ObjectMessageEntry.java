package com.viewfunction.messageEngine.messageService;

import java.util.Map;

public interface ObjectMessageEntry extends MessageEntry{
	public Map<String,Object> getMessageData();
	public Map<String,Object> getMessageProperty();
}