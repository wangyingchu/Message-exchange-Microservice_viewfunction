package com.viewfunction.messageEngine.exchange.restfulClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.viewfunction.messageEngine.exchange.MessageServiceConstant;
import com.viewfunction.messageEngine.exchange.restful.ActivityTaskNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.CommonNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.ExternalResourceNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.MessageReceiverVO;
import com.viewfunction.messageEngine.exchange.restful.SendActivityTaskNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.SendExternalResourceNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.SendMessageResultVO;

public class RESTClientTestCase {
	
	public static void testSendCommonNotification(){
		CommonNotificationVO commonNotificationVO=new CommonNotificationVO();
		
		commonNotificationVO.setNotificationContent("标准通知内容");
		commonNotificationVO.setNotificationHandleable(true);
		commonNotificationVO.setNotificationReceiverId("ManufacturingEmployeeB");		
		
		List<MessageReceiverVO> receiverList=new ArrayList<MessageReceiverVO>();		
		MessageReceiverVO messageReceiverVO1=new MessageReceiverVO();		
		messageReceiverVO1.setReceiverDisplayName("王颖初");
		commonNotificationVO.setNotificationHandleable(false);
		messageReceiverVO1.setReceiverId("ManufacturingEmployeeB");
		messageReceiverVO1.setReceiverType(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverType_People);		
		receiverList.add(messageReceiverVO1);
		commonNotificationVO.setNotificationReceivers(receiverList);		
		commonNotificationVO.setNotificationScope("viewfunction_inc");
		commonNotificationVO.setNotificationSenderId("system");
		commonNotificationVO.setNotificationSenderName("业务活动管理器");	
		commonNotificationVO.setNotificationStatus("INFO");
		commonNotificationVO.setNotificationTitle("标准通知标题");		
		SendMessageResultVO sendMessageResultVO=NotificationOperationServiceRESTClient.sendCommonNotification(commonNotificationVO);
		System.out.println(sendMessageResultVO);		
	}
	
	public static void testSendActivityTaskNotification(){
		CommonNotificationVO commonNotificationVO=new CommonNotificationVO();
		
		commonNotificationVO.setNotificationContent("setNotificationContent");
		commonNotificationVO.setNotificationHandleable(true);
		commonNotificationVO.setNotificationReceiverId("wangychu");		
		
		List<MessageReceiverVO> receiverList=new ArrayList<MessageReceiverVO>();		
		MessageReceiverVO messageReceiverVO1=new MessageReceiverVO();		
		messageReceiverVO1.setReceiverDisplayName("receiverDisplayName");
		messageReceiverVO1.setReceiverId("ManufacturingEmployeeB");
		messageReceiverVO1.setReceiverType(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverType_People);		
		receiverList.add(messageReceiverVO1);
		commonNotificationVO.setNotificationReceivers(receiverList);		
		commonNotificationVO.setNotificationScope("viewfunction_inc");
		commonNotificationVO.setNotificationSenderId("system");
		commonNotificationVO.setNotificationSenderName("系统消息");	
		commonNotificationVO.setNotificationStatus("INFO");
		commonNotificationVO.setNotificationTitle("setNotificationTitle");			
		
		ActivityTaskNotificationVO activityTaskNotificationVO=new ActivityTaskNotificationVO();
		activityTaskNotificationVO.setActivityId("activityId1");
		activityTaskNotificationVO.setActivityName("activityName1");		
		activityTaskNotificationVO.setTaskDescription("taskDescription1");
		activityTaskNotificationVO.setTaskDueDate(new Date().getTime());
		activityTaskNotificationVO.setTaskDueStatus("taskDueStatus1");
		activityTaskNotificationVO.setTaskName("taskName1");
		activityTaskNotificationVO.setTaskRole("taskRole1");		
		
		SendActivityTaskNotificationVO sendActivityTaskNotificationVO=new SendActivityTaskNotificationVO();
		sendActivityTaskNotificationVO.setActivityTaskNotificationVO(activityTaskNotificationVO);
		sendActivityTaskNotificationVO.setCommonNotificationVO(commonNotificationVO);
		SendMessageResultVO sendMessageResultVO=NotificationOperationServiceRESTClient.sendActivityTaskNotification(sendActivityTaskNotificationVO);
		System.out.println(sendMessageResultVO);
		
	}
	
	public static void testSendExternalResourceNotification(){
		CommonNotificationVO commonNotificationVO=new CommonNotificationVO();
		
		commonNotificationVO.setNotificationContent("setNotificationContent");
		commonNotificationVO.setNotificationHandleable(true);
		commonNotificationVO.setNotificationReceiverId("ManufacturingEmployeeB");		
		
		List<MessageReceiverVO> receiverList=new ArrayList<MessageReceiverVO>();		
		MessageReceiverVO messageReceiverVO1=new MessageReceiverVO();		
		messageReceiverVO1.setReceiverDisplayName("receiverDisplayName");
		messageReceiverVO1.setReceiverId("ManufacturingEmployeeB");
		messageReceiverVO1.setReceiverType(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverType_People);		
		receiverList.add(messageReceiverVO1);
		commonNotificationVO.setNotificationReceivers(receiverList);		
		commonNotificationVO.setNotificationScope("viewfunction_inc");
		commonNotificationVO.setNotificationSenderId("system");
		commonNotificationVO.setNotificationSenderName("系统消息");	
		commonNotificationVO.setNotificationStatus("ERROR");
		commonNotificationVO.setNotificationTitle("setNotificationTitle1");			
		
		ExternalResourceNotificationVO externalResourceNotificationVO=new ExternalResourceNotificationVO();
		externalResourceNotificationVO.setResourceName("vaadin");
		externalResourceNotificationVO.setResourceURL("http://www.vaadin.com");
		
		SendExternalResourceNotificationVO sendExternalResourceNotificationVO=new SendExternalResourceNotificationVO();
		sendExternalResourceNotificationVO.setCommonNotificationVO(commonNotificationVO);
		sendExternalResourceNotificationVO.setExternalResourceNotificationVO(externalResourceNotificationVO);
		SendMessageResultVO sendMessageResultVO=NotificationOperationServiceRESTClient.sendExternalResourceNotification(sendExternalResourceNotificationVO);
		System.out.println(sendMessageResultVO);
		
	}
	
	public static void main(String[] args){
		
		for(int i=0;i<5;i++){
			
			testSendCommonNotification();
		}
		testSendCommonNotification();
		testSendActivityTaskNotification();
		testSendExternalResourceNotification();
	}
}