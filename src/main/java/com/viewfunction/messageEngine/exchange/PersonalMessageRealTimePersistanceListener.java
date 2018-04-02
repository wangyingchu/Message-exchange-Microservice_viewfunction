package com.viewfunction.messageEngine.exchange;

import java.util.Date;
import java.util.List;

import com.viewfunction.messageEngine.util.ServiceResourceHolder;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;


import com.viewfunction.messageEngine.exchange.persistent.ActivityTaskNotificationInfo;
import com.viewfunction.messageEngine.exchange.persistent.ExternalResourceNotificationInfo;
import com.viewfunction.messageEngine.exchange.persistent.Message;
import com.viewfunction.messageEngine.exchange.persistent.Notification;
import com.viewfunction.messageEngine.messageService.ObjectMessageEntry;
import com.viewfunction.messageEngine.messageService.RealTimeNotificationListener;
import com.viewfunction.messageEngine.messageService.TextMessageEntry;

public class PersonalMessageRealTimePersistanceListener implements RealTimeNotificationListener{

	@Override
	public void receivedTextMessage(TextMessageEntry textMessageEntry) {
		System.out.println(textMessageEntry);		
	}

	@Override
	public void receivedObjectMessage(ObjectMessageEntry objectMessageEntry) {		
		String messageCatalogTypeProperty=objectMessageEntry.getMessageProperty().get(MessageServiceConstant.MESSAGESERVICE_MessageCatalog)!=null?
				objectMessageEntry.getMessageProperty().get(MessageServiceConstant.MESSAGESERVICE_MessageCatalog).toString():null;
		if(messageCatalogTypeProperty!=null&messageCatalogTypeProperty.equals(MessageServiceConstant.MESSAGESERVICE_MessageCatalog_BussinessExchangeMessage)){							
			String messageType=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_MessageType).toString();
			if(messageType.equals(MessageServiceConstant.MESSAGESERVICE_MessageType_MESSAGE)){				
				String messageScope=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageScope).toString();				
				String messageTitle=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageTitle).toString();
				String messageContent=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageContent).toString();
				long messageSentTime=((Long)(objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageSentTime))).longValue();
				String messageSenderId=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageSenderId).toString();
				String messageSenderName=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageSenderName).toString();
				List<String> messageReceiverList=(List<String>)objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverPeoples);						
				String messageReceiversIdList=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiversIdList).toString();
				String messageReceiversNameList=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiversNameList).toString();
				String messageReceiversTypeList=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiversTypeList).toString();					
				String groupId=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageGroupId).toString();
				ObjectContext context = ServiceResourceHolder.getCayenneServerRuntime().getContext();
				for(String receiverId:messageReceiverList){					
					Message newMessage=context.newObject(Message.class);
					newMessage.setMessageScope(messageScope);
					newMessage.setMessageTitle(messageTitle);
					newMessage.setMessageContent(messageContent);
					newMessage.setMessageReadStatus(new Boolean(false));
					newMessage.setMessageReceiverId(receiverId);
					newMessage.setMessageReceiversId(messageReceiversIdList);
					newMessage.setMessageReceiversName(messageReceiversNameList);
					newMessage.setMessageReceiversType(messageReceiversTypeList);
					newMessage.setMessageSenderId(messageSenderId);
					newMessage.setMessageSenderName(messageSenderName);					
					newMessage.setGroupId(groupId);
					newMessage.setMessageSentDate(new Date(messageSentTime));					
				}
				context.commitChanges();				
			}else if(messageType.equals(MessageServiceConstant.MESSAGESERVICE_MessageType_NOTICE)){
				String noticeTitle=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationTitle).toString();				
				String noticeContent=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationContent).toString();				
				String noticeScope=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationScope).toString();
				String noticeSenderId=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationSenderId).toString();
				String noticeSenderName=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationSenderName).toString();				
				long noticeSentTime=((Long)(objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationSentDate))).longValue();	
				String noticeReceiversIdList=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationReceiversIdList).toString();				
				String noticeReceiversNameList=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationReceiversNameList).toString();
				String noticeReceiversTypeList=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationReceiversTypeList).toString();
				List<String> noticeReceiverList=(List<String>)objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverPeoples);				
				Boolean noticeHandleable=(Boolean)objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationHandleable);
				String noticeStatus=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationStatus).toString();				
				String noticeType=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NotificationType).toString();
				String groupId=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_NoticeGroupId).toString();				
				ObjectContext context = ServiceResourceHolder.getCayenneServerRuntime().getContext();
				for(String receiverId:noticeReceiverList){	
					Notification newNotification=context.newObject(Notification.class);
					newNotification.setNotificationScope(noticeScope);
					newNotification.setNotificationTitle(noticeTitle);
					newNotification.setNotificationContent(noticeContent);
					newNotification.setNotificationReadStatus(new Boolean(false));
					newNotification.setNotificationReceiverId(receiverId);
					newNotification.setNotificationReceiversId(noticeReceiversIdList);
					newNotification.setNotificationReceiversName(noticeReceiversNameList);
					newNotification.setNotificationReceiversType(noticeReceiversTypeList);
					newNotification.setNotificationSenderId(noticeSenderId);
					newNotification.setNotificationSenderName(noticeSenderName);					
					newNotification.setGroupId(groupId);
					newNotification.setNotificationSentDate(new Date(noticeSentTime));						
					newNotification.setNotificationHandleable(noticeHandleable);
					newNotification.setNotificationStatus(noticeStatus);
					newNotification.setNotificationType(noticeType);	
									
					if(noticeType.equals(MessageServiceConstant.MESSAGESERVICE_NoticeType_ACTIVITYTASK)){	
						String activityId=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_ActivityId).toString();
						String activityName=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_ActivityName).toString();
						String taskName=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskName).toString();
						String taskDescription="";
						if(objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskDescription)!=null){
							taskDescription=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskDescription).toString();
						}
						String taskDueStatus="";
						if(objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskDueStatus)!=null){
							taskDueStatus=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskDueStatus).toString();
						}
						long taskDueDate=0;
						if(objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskDueDate)!=null){
							taskDueDate=((Long)(objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskDueDate))).longValue();
						}
						String taskRole="";
						if(objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskRole)!=null){
							taskRole=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_ACTIVITYTASK_TaskRole).toString();
						}				
						ActivityTaskNotificationInfo activityTaskNotificationInfo=context.newObject(ActivityTaskNotificationInfo.class);
						activityTaskNotificationInfo.setActivityId(activityId);
						activityTaskNotificationInfo.setActivityName(activityName);
						activityTaskNotificationInfo.setTaskDescription(taskDescription);
						activityTaskNotificationInfo.setTaskDueStatus(taskDueStatus);
						activityTaskNotificationInfo.setTaskDueDate(new Date(taskDueDate));
						activityTaskNotificationInfo.setTaskName(taskName);
						activityTaskNotificationInfo.setTaskRole(taskRole);
						newNotification.setToActivityTaskNotificationInfo(activityTaskNotificationInfo);
					}
										
					if(noticeType.equals(MessageServiceConstant.MESSAGESERVICE_NoticeType_EXTERNALRESOURCE)){						
						String resourceName=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_EXTERNALRESOURCE_ResourceName).toString();
						String resourceURL=objectMessageEntry.getMessageData().get(MessageServiceConstant.MESSAGESERVICE_Property_Notification_EXTERNALRESOURCE_ResourceURL).toString();
						
						ExternalResourceNotificationInfo toExternalResourceNotificationInfo=context.newObject(ExternalResourceNotificationInfo.class);						
						toExternalResourceNotificationInfo.setResourceName(resourceName);
						toExternalResourceNotificationInfo.setResourceURL(resourceURL);						
						newNotification.setToExternalResourceNotificationInfo(toExternalResourceNotificationInfo);					
					}					
				}
				context.commitChanges();			
			}else{
				System.out.println("unsupported MessageType");
			}			
		}					
	}
}