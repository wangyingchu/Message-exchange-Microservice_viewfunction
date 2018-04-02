package com.viewfunction.messageEngine.messageServiceImpl;

import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.qpid.AMQException;
import org.apache.qpid.QpidException;
import org.apache.qpid.client.AMQAnyDestination;
import org.apache.qpid.client.AMQConnection;
import org.apache.qpid.url.URLSyntaxException;

import com.viewfunction.messageEngine.exception.MessageEngineException;
import com.viewfunction.messageEngine.messageService.RealTimeNotificationListener;
import com.viewfunction.messageEngine.messageService.RealTimeNotificationReceiver;
import com.viewfunction.messageEngine.util.PropertyHandler;

public class QpidRealTimeNotificationReceiverImpl implements RealTimeNotificationReceiver{
	
	private Connection connection=null;
	private Session session=null;
	private String destinationName;
	private String destinationOption;
	private RealTimeNotificationListener realTimeNotificationListener;
	
	public QpidRealTimeNotificationReceiverImpl(String destinationName,String destinationOption,RealTimeNotificationListener realTimeNotificationListener){
		this.destinationName=destinationName;
		this.destinationOption=destinationOption;
		this.realTimeNotificationListener=realTimeNotificationListener;
	}	
	
	@Override
	public void startReceive() throws MessageEngineException{
		Connection connection=null;
		String destinationConfig=destinationOption!=null?destinationOption:"";
		try {
			connection = new AMQConnection(PropertyHandler.getConnectionInfo());
			connection.start();		        
		    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);		    
		    Destination destination = new AMQAnyDestination("ADDR:"+destinationName+"; "+destinationConfig);		    
		    MessageConsumer consumer = session.createConsumer(destination);	
		    
		    QpidMessageListenerImpl qpidDefaultMessageListenerImpl=null;
		    if(this.realTimeNotificationListener!=null){
		    	qpidDefaultMessageListenerImpl=new QpidMessageListenerImpl(this.realTimeNotificationListener);
		    }else{
		    	qpidDefaultMessageListenerImpl=new QpidMessageListenerImpl(new MockRealTimeNotificationListenerImpl());
		    }		   
		    consumer.setMessageListener(qpidDefaultMessageListenerImpl);			    
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
	public void stopReceive() throws MessageEngineException{
		try {
			if(session!=null){
				session.close();
			}
			if(connection!=null){
				connection.close();
			}				
		} catch (JMSException e) {				
			e.printStackTrace();
			throw new MessageEngineException();
		}	
	}
	
	@Override
	public void setRealTimeNotificationListener(RealTimeNotificationListener realTimeNotificationListener) {
		this.realTimeNotificationListener=realTimeNotificationListener;		
	}	
	
	public static void main(String[] args) throws MessageEngineException{		
		QpidRealTimeNotificationReceiverImpl qpidRealTimeNotificationReceiverImpl=new QpidRealTimeNotificationReceiverImpl("topicAAA", "{create: always,node: {type:topic,durable: true}}",null);
		qpidRealTimeNotificationReceiverImpl.startReceive();		
	}
}