package com.viewfunction.messageEngine.exchange.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.viewfunction.messageEngine.exception.MessageEngineException;
import com.viewfunction.messageEngine.exchange.MessageServiceConstant;
import com.viewfunction.messageEngine.exchange.util.DBOperationUtil;
import com.viewfunction.messageEngine.messageService.MessageUtil;
import com.viewfunction.messageEngine.messageService.util.factory.MessageComponentFactory;
import com.viewfunction.messageEngine.util.PropertyHandler;
import org.springframework.stereotype.Service;
/*
import com.viewfunction.vfmab.restful.userManagement.UserBasicInfoVO;
import com.viewfunction.vfmab.restful.userManagement.UserBasicInfoVOList;
import com.viewfunction.vfmab.restfulClient.UserManagementServiceRESTClient;
*/

@Service
@Path("/messageExchangeService")  
@Produces("application/json")
public class MessageExchangeService {	
	
	private static String _GLOBAL_TOPIC_ADDRESS= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_GLOBAL_TOPIC_ADDRESS);
	private static String _GLOBAL_TOPIC_CONFIG= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_GLOBAL_TOPIC_CONFIG);
	private static String _GROUP_QUEUE_ADDRESS= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_GROUP_QUEUE_ADDRESS);
	private static String _GROUP_QUEUE_CONFIG= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_GROUP_QUEUE_CONFIG);
	private static String _PERSONAL_QUEUE_ADDRESS= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_PERSONAL_QUEUE_ADDRESS);
	private static String _PERSONAL_QUEUE_CONFIG= PropertyHandler.getPerportyValue(PropertyHandler.AMQP_PERSONAL_QUEUE_CONFIG);
	
	@GET
    @Path("/ping/")
    @Produces("application/json")
	public PingReturnVO pingService(){		
		PingReturnVO pingReturnVO=new PingReturnVO();
		pingReturnVO.setTiemStamp(new Date().getTime());
		pingReturnVO.setVoName("pingService");
		return pingReturnVO;		
	}
	
	@POST
    @Path("/sendMessage/")
    @Produces("application/json")
	public SendMessageResultVO sendMessage(SendMessageVO sendMessageVO){	
		MessageUtil messageUtil=MessageComponentFactory.createMessageUtil();
		SendMessageResultVO sendMessageResultVO=new SendMessageResultVO();		
		HashMap<String,Object> dataMap=new HashMap<String,Object>();		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageScope, sendMessageVO.getMessageScope());
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageTitle, sendMessageVO.getMessageTitle());								
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageContent, sendMessageVO.getMessageContent());		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageSentTime, new Date().getTime());		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageSenderId, sendMessageVO.getMessageSenderId());
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageSenderName, sendMessageVO.getMessageSenderName());		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_MessageType, sendMessageVO.getMessageType());			
		String groupId=java.util.UUID.randomUUID().toString();
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageGroupId,groupId);		
		
		HashMap<String,Object> propertyMap=new HashMap<String,Object>();
		propertyMap.put(MessageServiceConstant.MESSAGESERVICE_MessageCatalog, MessageServiceConstant.MESSAGESERVICE_MessageCatalog_BussinessExchangeMessage);	
		
		List<MessageReceiverVO> messageReceiverList=sendMessageVO.getMessageReceivers();		
		StringBuffer messageReceiversIdList=new StringBuffer();
		StringBuffer messageReceiversNameList=new StringBuffer();
		StringBuffer messageReceiversTypeList=new StringBuffer();		
		try {
			List<String> groups = new ArrayList<String>();
			List<String> peoples = new ArrayList<String>();
			if(messageReceiverList!=null){
				for(MessageReceiverVO messageReceiverVO:messageReceiverList){
					messageReceiversIdList.append(messageReceiverVO.getReceiverId());
					messageReceiversIdList.append("<>");
					messageReceiversNameList.append(messageReceiverVO.getReceiverDisplayName());
					messageReceiversNameList.append("<>");				
					if(messageReceiverVO.getReceiverType().equals(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverType_Group)){					
						groups.add(messageReceiverVO.getReceiverId());
						messageReceiversTypeList.append("G");
					}
					if(messageReceiverVO.getReceiverType().equals(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverType_People)){							
						peoples.add(messageReceiverVO.getReceiverId());
						messageReceiversTypeList.append("P");
					}
					messageReceiversTypeList.append("<>");
				}	
			}			
			dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiversIdList, messageReceiversIdList.toString());	
			dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiversNameList, messageReceiversNameList.toString());	
			dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiversTypeList, messageReceiversTypeList.toString());								
						
			addPeopleOfGroups(groups,peoples,sendMessageVO.getMessageScope());
			if(peoples.size()>0){
				dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverPeoples, peoples);
				messageUtil.sendObjectMessage(_PERSONAL_QUEUE_ADDRESS,_PERSONAL_QUEUE_CONFIG,dataMap,propertyMap);
			}
			
			sendMessageResultVO.setSendSuccess(true);			
		} catch (MessageEngineException e) {
			sendMessageResultVO.setSendSuccess(false);
			e.printStackTrace();
		}			
		return sendMessageResultVO;		
	}
	
	@POST
    @Path("/fetchMessages/")
    @Produces("application/json")
	public PersonalMessagesVO getPersonalMessages(QueryPersonalMessagesVO queryPersonalMessagesVO){		
		return DBOperationUtil.getPersonalMessages(queryPersonalMessagesVO);
	}
	
	@DELETE
    @Path("/deleteMessage/{messageId}")
    @Produces("application/json")
	public MessageOperationResultVO deleteMessages(@PathParam("messageId") String messageId){		
		MessageOperationResultVO messageOperationResultVO=new MessageOperationResultVO();
		boolean dbOperationResult= DBOperationUtil.deleteMessage(messageId);
		if(dbOperationResult){
			messageOperationResultVO.setOperationResult(true);
		}else{
			messageOperationResultVO.setOperationResult(false);
		}
		return messageOperationResultVO;		
	}	
	
	@PUT
    @Path("/readMessage/{messageId}")
    @Produces("application/json")
	public MessageOperationResultVO readMessage(@PathParam("messageId") String messageId){		
		MessageOperationResultVO messageOperationResultVO=new MessageOperationResultVO();
		boolean dbOperationResult=DBOperationUtil.readMessage(messageId);
		if(dbOperationResult){
			messageOperationResultVO.setOperationResult(true);
		}else{
			messageOperationResultVO.setOperationResult(false);
		}
		return messageOperationResultVO;
	}	
	
	@POST
    @Path("/sendCommonNotification/")
	@Produces({"application/xml", "application/json"})	
	public SendMessageResultVO sendCommonNotification(CommonNotificationVO commonNotificationVO){			
		MessageUtil messageUtil=MessageComponentFactory.createMessageUtil();
		SendMessageResultVO sendMessageResultVO=new SendMessageResultVO();	
		HashMap<String,Object> propertyMap=new HashMap<String,Object>();
		propertyMap.put(MessageServiceConstant.MESSAGESERVICE_MessageCatalog, MessageServiceConstant.MESSAGESERVICE_MessageCatalog_BussinessExchangeMessage);
		HashMap<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_MessageType, MessageServiceConstant.MESSAGESERVICE_MessageType_NOTICE);
		String groupId=java.util.UUID.randomUUID().toString();
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NoticeGroupId,groupId);					
		List<String> groups = new ArrayList<String>();
		List<String> peoples = new ArrayList<String>();		
		setupCommonNotificationDataMap(commonNotificationVO,dataMap,groups,peoples);	
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationType,MessageServiceConstant.MESSAGESERVICE_NoticeType_COMMONNOTICE);
		addPeopleOfGroups(groups,peoples,commonNotificationVO.getNotificationScope());			
		if(peoples.size()>0){
			dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverPeoples, peoples);
			try {
				messageUtil.sendObjectMessage(_PERSONAL_QUEUE_ADDRESS,_PERSONAL_QUEUE_CONFIG,dataMap,propertyMap);
				sendMessageResultVO.setSendSuccess(true);
			} catch (MessageEngineException e) {				
				e.printStackTrace();
				sendMessageResultVO.setSendSuccess(false);
			}
		}			
		return sendMessageResultVO;
	}
	
	@POST
    @Path("/sendActivityTaskNotification/")
	@Produces({"application/xml", "application/json"})
	public SendMessageResultVO sendActivityTaskNotification(SendActivityTaskNotificationVO sendActivityTaskNotificationVO){
		CommonNotificationVO commonNotificationVO=sendActivityTaskNotificationVO.getCommonNotificationVO();		
		MessageUtil messageUtil=MessageComponentFactory.createMessageUtil();
		SendMessageResultVO sendMessageResultVO=new SendMessageResultVO();	
		HashMap<String,Object> propertyMap=new HashMap<String,Object>();
		propertyMap.put(MessageServiceConstant.MESSAGESERVICE_MessageCatalog, MessageServiceConstant.MESSAGESERVICE_MessageCatalog_BussinessExchangeMessage);
		HashMap<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_MessageType, MessageServiceConstant.MESSAGESERVICE_MessageType_NOTICE);
		String groupId=java.util.UUID.randomUUID().toString();
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NoticeGroupId,groupId);					
		List<String> groups = new ArrayList<String>();
		List<String> peoples = new ArrayList<String>();		
		setupCommonNotificationDataMap(commonNotificationVO,dataMap,groups,peoples);
		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationType,MessageServiceConstant.MESSAGESERVICE_NoticeType_ACTIVITYTASK);
		ActivityTaskNotificationVO activityTaskNotificationVO=sendActivityTaskNotificationVO.getActivityTaskNotificationVO();
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_ActivityId,activityTaskNotificationVO.getActivityId());
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_ActivityName,activityTaskNotificationVO.getActivityName());
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskName,activityTaskNotificationVO.getTaskName());
		
		if(activityTaskNotificationVO.getTaskDescription()!=null){
			dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskDescription,activityTaskNotificationVO.getTaskDescription());
		}
		if(activityTaskNotificationVO.getTaskDueStatus()!=null){
			dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskDueStatus,activityTaskNotificationVO.getTaskDueStatus());
				}
		if(activityTaskNotificationVO.getTaskDueDate()!=0){
			dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskDueDate,activityTaskNotificationVO.getTaskDueDate());
		}
		if(activityTaskNotificationVO.getTaskRole()!=null){
			dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskRole,activityTaskNotificationVO.getTaskRole());	
		}
		
		addPeopleOfGroups(groups,peoples,commonNotificationVO.getNotificationScope());			
		if(peoples.size()>0){
			dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverPeoples, peoples);
			try {
				messageUtil.sendObjectMessage(_PERSONAL_QUEUE_ADDRESS,_PERSONAL_QUEUE_CONFIG,dataMap,propertyMap);
				sendMessageResultVO.setSendSuccess(true);
			} catch (MessageEngineException e) {				
				e.printStackTrace();
				sendMessageResultVO.setSendSuccess(false);
			}
		}			
		return sendMessageResultVO;
	}
	
	@POST
    @Path("/sendExternalResourceNotification/")
	@Produces({"application/xml", "application/json"})
	public SendMessageResultVO SendExternalResourceNotification(SendExternalResourceNotificationVO sendExternalResourceNotificationVO){
		CommonNotificationVO commonNotificationVO=sendExternalResourceNotificationVO.getCommonNotificationVO();		
		MessageUtil messageUtil=MessageComponentFactory.createMessageUtil();
		SendMessageResultVO sendMessageResultVO=new SendMessageResultVO();	
		HashMap<String,Object> propertyMap=new HashMap<String,Object>();
		propertyMap.put(MessageServiceConstant.MESSAGESERVICE_MessageCatalog, MessageServiceConstant.MESSAGESERVICE_MessageCatalog_BussinessExchangeMessage);
		HashMap<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_MessageType, MessageServiceConstant.MESSAGESERVICE_MessageType_NOTICE);
		String groupId=java.util.UUID.randomUUID().toString();
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NoticeGroupId,groupId);				
		List<String> groups = new ArrayList<String>();
		List<String> peoples = new ArrayList<String>();		
		setupCommonNotificationDataMap(commonNotificationVO,dataMap,groups,peoples);
		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationType,MessageServiceConstant.MESSAGESERVICE_NoticeType_EXTERNALRESOURCE);		
		ExternalResourceNotificationVO externalResourceNotificationVO=sendExternalResourceNotificationVO.getExternalResourceNotificationVO();		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_Notification_EXTERNALRESOURCE_ResourceName,externalResourceNotificationVO.getResourceName());
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_Notification_EXTERNALRESOURCE_ResourceURL,externalResourceNotificationVO.getResourceURL());		
		
		addPeopleOfGroups(groups,peoples,commonNotificationVO.getNotificationScope());			
		if(peoples.size()>0){
			dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverPeoples, peoples);
			try {
				messageUtil.sendObjectMessage(_PERSONAL_QUEUE_ADDRESS,_PERSONAL_QUEUE_CONFIG,dataMap,propertyMap);
				sendMessageResultVO.setSendSuccess(true);
			} catch (MessageEngineException e) {				
				e.printStackTrace();
				sendMessageResultVO.setSendSuccess(false);
			}
		}			
		return sendMessageResultVO;
	}	
	
	@GET
    @Path("/countUnReadInformationNumber/{messageScope}/{receiverId}")
    @Produces("application/json")
	public UnReadInformationNumberVO countTotalUnReadInformationNumber(@PathParam("messageScope") String messageScope,@PathParam("receiverId") String receiverId){
		return DBOperationUtil.countUnReadInformationNumber(messageScope,receiverId);
	}	

	private void addPeopleOfGroups(List<String> groups,List<String> peoples,String scopeName){
		/*
		for(String groupName:groups){
			//use groupName to get all people of this group
			//add people in peoples List	
			UserBasicInfoVOList userBasicInfoVOList=UserManagementServiceRESTClient.getUserUnitsInfoOfRole(scopeName, groupName);
			List<UserBasicInfoVO> userBasicInfoVOsList=userBasicInfoVOList.getUserBasicInfoVOList();
			for(UserBasicInfoVO userBasicInfoVO:userBasicInfoVOsList){				
				if(!peoples.contains(userBasicInfoVO.getUserId())){
					peoples.add(userBasicInfoVO.getUserId());
				}
			}		
		}
		*/
	}
	
	private void setupCommonNotificationDataMap(CommonNotificationVO commonNotificationVO,HashMap<String,Object> dataMap,List<String> groups,List<String> peoples){
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationContent, commonNotificationVO.getNotificationContent());			
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationScope, commonNotificationVO.getNotificationScope());		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationSenderId,commonNotificationVO.getNotificationSenderId());
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationSenderName,commonNotificationVO.getNotificationSenderName());		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationSentDate,new Date().getTime());	
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationStatus,commonNotificationVO.getNotificationStatus());		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationTitle,commonNotificationVO.getNotificationTitle());		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationType,commonNotificationVO.getNotificationType());
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationHandleable,commonNotificationVO.isNotificationHandleable());		
		
		List<MessageReceiverVO> noticeReceiverList=commonNotificationVO.getNotificationReceivers();			
		
		StringBuffer noticeReceiversIdList=new StringBuffer();
		StringBuffer noticeReceiversNameList=new StringBuffer();
		StringBuffer noticeReceiversTypeList=new StringBuffer();		
		
		for(MessageReceiverVO messageReceiverVO:noticeReceiverList){
			noticeReceiversIdList.append(messageReceiverVO.getReceiverId());
			noticeReceiversIdList.append("<>");
			noticeReceiversNameList.append(messageReceiverVO.getReceiverDisplayName());
			noticeReceiversNameList.append("<>");				
			if(messageReceiverVO.getReceiverType().equals(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverType_Group)){					
				groups.add(messageReceiverVO.getReceiverId());
				noticeReceiversTypeList.append("G");
			}
			if(messageReceiverVO.getReceiverType().equals(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverType_People)){							
				peoples.add(messageReceiverVO.getReceiverId());
				noticeReceiversTypeList.append("P");
			}
			noticeReceiversTypeList.append("<>");
		}		
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationReceiversIdList, noticeReceiversIdList.toString());	
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationReceiversNameList, noticeReceiversNameList.toString());	
		dataMap.put(MessageServiceConstant.MESSAGESERVICE_Property_NotificationReceiversTypeList, noticeReceiversTypeList.toString());			
	}

	@POST
    @Path("/fetchNotifications/")
    @Produces("application/json")
	public PersonalNotificationsVO getPersonalNotifications(QueryPersonalNotificationsVO queryPersonalNotificationsVO){		
		return DBOperationUtil.getPersonalNotifications(queryPersonalNotificationsVO);		
	}
	
	@DELETE
    @Path("/deleteNotification/{notificationId}")
    @Produces("application/json")
	public MessageOperationResultVO deleteNotification(@PathParam("notificationId") String notificationId){		
		MessageOperationResultVO messageOperationResultVO=new MessageOperationResultVO();
		boolean dbOperationResult= DBOperationUtil.deleteNotification(notificationId);
		if(dbOperationResult){
			messageOperationResultVO.setOperationResult(true);
		}else{
			messageOperationResultVO.setOperationResult(false);
		}
		return messageOperationResultVO;		
	}
	
	@PUT
    @Path("/readNotification/{notificationId}")
    @Produces("application/json")
	public MessageOperationResultVO readNotification(@PathParam("notificationId") String notificationId){		
		MessageOperationResultVO messageOperationResultVO=new MessageOperationResultVO();
		boolean dbOperationResult=DBOperationUtil.readNotification(notificationId);
		if(dbOperationResult){
			messageOperationResultVO.setOperationResult(true);
		}else{
			messageOperationResultVO.setOperationResult(false);
		}
		return messageOperationResultVO;
	}	
}