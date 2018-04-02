package com.viewfunction.messageEngine.exchange.restfulClient;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.viewfunction.messageEngine.exchange.restful.CommonNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.SendActivityTaskNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.SendExternalResourceNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.SendMessageResultVO;


public class NotificationOperationServiceRESTClient {
	
	public static SendMessageResultVO sendCommonNotification(CommonNotificationVO commonNotificationVO){
		WebClient client = WebClient.create(RESTClientConfigUtil.getREST_baseURLValue());
		client.path("messageExchangeService/sendCommonNotification");		
		client.type("application/xml").accept("application/xml");
		Response response = client.post(commonNotificationVO);		
		SendMessageResultVO sendMessageResultVO=response.readEntity(SendMessageResultVO.class);
		return sendMessageResultVO;	
	}
	
	public static SendMessageResultVO sendActivityTaskNotification(SendActivityTaskNotificationVO sendActivityTaskNotificationVO){
		WebClient client = WebClient.create(RESTClientConfigUtil.getREST_baseURLValue());
		client.path("messageExchangeService/sendActivityTaskNotification");		
		client.type("application/xml").accept("application/xml");
		Response response = client.post(sendActivityTaskNotificationVO);		
		SendMessageResultVO sendMessageResultVO=response.readEntity(SendMessageResultVO.class);
		return sendMessageResultVO;	
	}
	
	public static SendMessageResultVO sendExternalResourceNotification(SendExternalResourceNotificationVO sendExternalResourceNotificationVO){
		WebClient client = WebClient.create(RESTClientConfigUtil.getREST_baseURLValue());
		client.path("messageExchangeService/sendExternalResourceNotification");		
		client.type("application/xml").accept("application/xml");
		Response response = client.post(sendExternalResourceNotificationVO);		
		SendMessageResultVO sendMessageResultVO=response.readEntity(SendMessageResultVO.class);
		return sendMessageResultVO;	
	}
}