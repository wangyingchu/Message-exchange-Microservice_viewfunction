package com.viewfunction.messageEngine.messageService;

import java.util.Map;

import com.viewfunction.messageEngine.exception.MessageEngineException;

public interface MessageUtil {
	public void sendObjectMessage(String destinationName,String destinationOption,Map<String,Object> dataMap,Map<String,Object> propertyMap) throws MessageEngineException;	
	public ObjectMessageEntry getObjectMessage(String destinationName,String destinationOption) throws MessageEngineException;	
	public void sendTextMessage(String textMessageContent,String destinationName,String destinationOption) throws MessageEngineException;	
	public TextMessageEntry getTextMessage(String destinationName,String destinationOption) throws MessageEngineException;
}