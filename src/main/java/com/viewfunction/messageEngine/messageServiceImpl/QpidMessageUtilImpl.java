package com.viewfunction.messageEngine.messageServiceImpl;

import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.AMQException;
import org.apache.qpid.QpidException;
import org.apache.qpid.client.AMQAnyDestination;
import org.apache.qpid.client.AMQConnection;
import org.apache.qpid.url.URLSyntaxException;

import com.viewfunction.messageEngine.exception.MessageEngineException;
import com.viewfunction.messageEngine.messageService.MessageUtil;
import com.viewfunction.messageEngine.messageService.ObjectMessageEntry;
import com.viewfunction.messageEngine.messageService.TextMessageEntry;
import com.viewfunction.messageEngine.messageService.util.factory.MessageComponentFactory;
import com.viewfunction.messageEngine.util.PropertyHandler;

public class QpidMessageUtilImpl implements MessageUtil{
	
	@Override
	public void sendObjectMessage(String destinationName,String destinationOption,Map<String,Object> dataMap,Map<String,Object> propertyMap) throws MessageEngineException{		
		if(dataMap==null&&propertyMap==null){			
			throw new MessageEngineException();
		}
		Connection connection;
		String destinationConfig=destinationOption!=null?destinationOption:"";
		try {
			connection = new AMQConnection(PropertyHandler.getConnectionInfo());
		    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);		    
		    Destination queue = new AMQAnyDestination("ADDR:"+ destinationName+"; "+destinationConfig);
		    MessageProducer producer = session.createProducer(queue);
		    MapMessage m = session.createMapMessage();		
		    if(dataMap!=null){
		    	Set<String> dataKeySet=dataMap.keySet();		    
			    Iterator<String> keyItor=dataKeySet.iterator();			    
			    while(keyItor.hasNext()){
			    	String currentKeyName=(String)keyItor.next();
			    	Object currentDataObject=dataMap.get(currentKeyName);	
			    	if(currentDataObject instanceof Boolean){
			    		 m.setBoolean(currentKeyName, ((Boolean)currentDataObject).booleanValue());		    		
			    	}else if(currentDataObject instanceof Double){
			    		 m.setDouble(currentKeyName, ((Double)currentDataObject).doubleValue());		    		
			    	}else if(currentDataObject instanceof Float){
			    		 m.setFloat(currentKeyName, ((Float)currentDataObject).floatValue());		    		
			    	}else if(currentDataObject instanceof Integer){
			    		 m.setInt(currentKeyName, ((Integer)currentDataObject).intValue());		    		
			    	}else if(currentDataObject instanceof Long){
			    		 m.setLong(currentKeyName, ((Long)currentDataObject).longValue());		    		
			    	}else if(currentDataObject instanceof String){
			    		 m.setString(currentKeyName, currentDataObject.toString());		    		
			    	}else if(currentDataObject instanceof Short){
			    		 m.setShort(currentKeyName, ((Short)currentDataObject).shortValue());		    		
			    	}else if(currentDataObject instanceof Character){
			    		 m.setChar(currentKeyName, ((Character)currentDataObject).charValue());	    	
			    	}else if(currentDataObject instanceof Byte){
			    		 m.setByte(currentKeyName, ((Byte)currentDataObject).byteValue());	    	
			    	}else if(currentDataObject instanceof Map||currentDataObject instanceof List){
			    		m.setObject(currentKeyName, currentDataObject);			    		
			    	}else{
			    		throw new MessageEngineException();
			    	}		    	
			    }
		    }
		    if(propertyMap!=null){
		    	Set<String> propertyKeySet=propertyMap.keySet();		    
			    Iterator<String> keyItor=propertyKeySet.iterator();			    
			    while(keyItor.hasNext()){
			    	String currentKeyName=(String)keyItor.next();
			    	Object currentDataObject=propertyMap.get(currentKeyName);	
			    	if(currentDataObject instanceof Boolean||
			    		currentDataObject instanceof Double||
			    		currentDataObject instanceof Float||
			    		currentDataObject instanceof Integer||
			    		currentDataObject instanceof Long||
			    		currentDataObject instanceof String||
			    		currentDataObject instanceof Short||
			    		currentDataObject instanceof Byte){
			    		m.setObjectProperty(currentKeyName, currentDataObject);	 			    		    		
			    	}else{
			    		throw new MessageEngineException();
			    	}
			    }
		    }		    
		    producer.send(m);
		    connection.close();		
		} catch (URLSyntaxException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (AMQException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (JMSException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (QpidException e) {
			e.printStackTrace();
			throw new MessageEngineException();
		}
	}
	
	@Override
	public ObjectMessageEntry getObjectMessage(String destinationName,String destinationOption) throws MessageEngineException{		
		Connection connection=null;
		String destinationConfig=destinationOption!=null?destinationOption:"";
		try {
			connection = new AMQConnection(PropertyHandler.getConnectionInfo());
			connection.start();		        
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);		    
			Destination destination = new AMQAnyDestination("ADDR:"+destinationName+"; "+destinationConfig);		
			MessageConsumer consumer = session.createConsumer(destination);			    
			Message msg=consumer.receiveNoWait();				
			if(msg==null){
				return null;
			}else{								
				if(msg instanceof MapMessage){
					long timeStamp=msg.getJMSTimestamp();
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
					return objectMessageEntry;					
				}			
			}								
		} catch (URLSyntaxException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (AMQException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (JMSException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (QpidException e) {
            e.printStackTrace();
            throw new MessageEngineException();
        } finally{
			try {
				if(connection!=null){
					connection.close();
				}				
			} catch (JMSException e) {				
				e.printStackTrace();
			}	
		}
		return null;  
	}
	
	@Override
	public void sendTextMessage(String textMessageContent,String destinationName,String destinationOption) throws MessageEngineException{
		Connection connection;
		String destinationConfig=destinationOption!=null?destinationOption:"";
		try {
			connection = new AMQConnection(PropertyHandler.getConnectionInfo());
		    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		    Destination queue = new AMQAnyDestination("ADDR:"+destinationName+"; "+destinationConfig);
		    MessageProducer producer = session.createProducer(queue);		    
		    TextMessage testMessage=session.createTextMessage();		    
		    testMessage.setText(textMessageContent);		   
		    producer.send(testMessage);
		    connection.close();		
		} catch (URLSyntaxException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (AMQException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (JMSException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (QpidException e) {
            e.printStackTrace();
            throw new MessageEngineException();
        }
    }
	
	@Override
	public TextMessageEntry getTextMessage(String destinationName,String destinationOption) throws MessageEngineException{		
		Connection connection=null;
		String destinationConfig=destinationOption!=null?destinationOption:"";
		try {
			connection = new AMQConnection(PropertyHandler.getConnectionInfo());
			connection.start();		        
		    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);		    
		    Destination destination = new AMQAnyDestination("ADDR:"+destinationName+"; "+destinationConfig);		    
		    MessageConsumer consumer = session.createConsumer(destination);		    
		    Message msg=consumer.receiveNoWait();	
		    if(msg==null){
		    	return null;
		    }else{
		    	if(msg instanceof TextMessage){		    		
		    		 long timeStamp=msg.getJMSTimestamp();
		    		 String replyToAddress=msg.getJMSReplyTo()!=null?msg.getJMSReplyTo().toString():null;
		    		 String textString=((TextMessage)msg).getText();				    	
		    		 TextMessageEntry textMessageEntry=MessageComponentFactory.createTextMessageEntry(textString, replyToAddress, timeStamp);
		    		 return textMessageEntry;
				}		    	
		    }			
		} catch (URLSyntaxException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (AMQException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (JMSException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new MessageEngineException();
		} catch (QpidException e) {
            e.printStackTrace();
            throw new MessageEngineException();
        } finally{
			try {
				if(connection!=null){
					connection.close();
				}				
			} catch (JMSException e) {				
				e.printStackTrace();
			}	
		}
		return null;       
	}
}