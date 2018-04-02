package com.viewfunction.messageEngine.exchange.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.viewfunction.messageEngine.util.ServiceResourceHolder;
import com.viewfunction.messageExchangeService.util.RuntimeEnvironmentHandler;
import org.apache.cayenne.DataObjectUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import com.viewfunction.messageEngine.exchange.MessageServiceConstant;
import com.viewfunction.messageEngine.exchange.persistent.ActivityTaskNotificationInfo;
import com.viewfunction.messageEngine.exchange.persistent.ExternalResourceNotificationInfo;
import com.viewfunction.messageEngine.exchange.persistent.Message;
import com.viewfunction.messageEngine.exchange.persistent.Notification;
import com.viewfunction.messageEngine.exchange.restful.ActivityTaskNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.CommonNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.ExternalResourceNotificationVO;
import com.viewfunction.messageEngine.exchange.restful.MessageReceiverVO;
import com.viewfunction.messageEngine.exchange.restful.MessageVO;
import com.viewfunction.messageEngine.exchange.restful.NotificationVO;
import com.viewfunction.messageEngine.exchange.restful.PersonalMessagesVO;
import com.viewfunction.messageEngine.exchange.restful.PersonalNotificationsVO;
import com.viewfunction.messageEngine.exchange.restful.QueryPersonalMessagesVO;
import com.viewfunction.messageEngine.exchange.restful.QueryPersonalNotificationsVO;
import com.viewfunction.messageEngine.exchange.restful.UnReadInformationNumberVO;

public class DBOperationUtil {



	public static PersonalMessagesVO getPersonalMessages(QueryPersonalMessagesVO queryPersonalMessagesVO){
		ObjectContext context = ServiceResourceHolder.getCayenneServerRuntime().getContext();
		//Basic query expression
		Expression queryExpression = ExpressionFactory.matchExp(Message.MESSAGE_RECEIVER_ID_PROPERTY, queryPersonalMessagesVO.getReceiverId());
		// create a query returning data rows with page size
		SelectQuery query = new SelectQuery(Message.class,queryExpression);	
		query.addOrdering(Message.MESSAGE_SENT_DATE_PROPERTY, SortOrder.DESCENDING);
		query.setPageSize(queryPersonalMessagesVO.getMessagesNumberPerPage());
		
		query.andQualifier(ExpressionFactory.matchExp(Message.MESSAGE_SCOPE_PROPERTY, queryPersonalMessagesVO.getMessageScope()));		
		if(queryPersonalMessagesVO.getQueryCriteria()!=null&&queryPersonalMessagesVO.getQueryValue()!=null){
			if(queryPersonalMessagesVO.getQueryCriteria().equals(Message.MESSAGE_SENDER_NAME_PROPERTY)){
				query.andQualifier(ExpressionFactory.likeExp(Message.MESSAGE_SENDER_NAME_PROPERTY, "%"+queryPersonalMessagesVO.getQueryValue()+"%"));
			}
			if(queryPersonalMessagesVO.getQueryCriteria().equals(Message.MESSAGE_TITLE_PROPERTY)){
				query.andQualifier(ExpressionFactory.likeExp(Message.MESSAGE_TITLE_PROPERTY, "%"+queryPersonalMessagesVO.getQueryValue()+"%"));
			}
			if(queryPersonalMessagesVO.getQueryCriteria().equals(Message.MESSAGE_SENT_DATE_PROPERTY)){				
				long sentLongDate=Long.parseLong(queryPersonalMessagesVO.getQueryValue());				
				Date sentQueryStartDate=new Date(sentLongDate);				
				Calendar c=Calendar.getInstance();
				c.setTime(sentQueryStartDate);
				c.add(Calendar.DAY_OF_MONTH, 1);				
				Date sentQueryEndDate=c.getTime();				
				query.andQualifier(ExpressionFactory.betweenExp(Message.MESSAGE_SENT_DATE_PROPERTY, sentQueryStartDate, sentQueryEndDate));
			}			
		}				
		
		PersonalMessagesVO personalMessagesVO=new PersonalMessagesVO();		
		//get query filter info
		personalMessagesVO.setQueryCriteria(queryPersonalMessagesVO.getQueryCriteria());
		personalMessagesVO.setQueryValue(queryPersonalMessagesVO.getQueryValue());		

		// the fact that result is paged is transparent
		List messageRows = context.performQuery(query);	
		// This is safe and will NOT trigger a full fetch
		int totalMessageSize = messageRows.size();		
		//get total message count
		personalMessagesVO.setTotalMessageCount(totalMessageSize);		
		
		//get total message list page count
		int pageSize=queryPersonalMessagesVO.getMessagesNumberPerPage();		
		int pageNumberCalculate=(int)totalMessageSize/pageSize;		
		int totalMessageListPageNumber=0;
		if(pageNumberCalculate*pageSize==totalMessageSize){
			totalMessageListPageNumber=pageNumberCalculate;
		}else{
			totalMessageListPageNumber=pageNumberCalculate+1;
		}
		personalMessagesVO.setTotalMessageListPageNumber(totalMessageListPageNumber);
		//get message details
		personalMessagesVO.setLastPage(true);
		int currentpage=queryPersonalMessagesVO.getCurrentMessageListPageNumber();
		personalMessagesVO.setCurrentMessageListPageNumber(currentpage);		
		List<MessageVO> messagesOfCurrentPage=new ArrayList<MessageVO>();	
		personalMessagesVO.setMessagesOfCurrentPage(messagesOfCurrentPage);
		for(int i=(currentpage-1)*pageSize;i<(currentpage)*pageSize;i++){			
			if(i<totalMessageSize){
				if(i==totalMessageSize-1){
					personalMessagesVO.setLastPage(true);
				}else{
					personalMessagesVO.setLastPage(false);
				}
				Message currentMessage = (Message)messageRows.get(i);
				MessageVO currentMessageVO=new MessageVO();				
				String pkVale=(String)currentMessage.getObjectId().getIdSnapshot().get(Message.MESSAGE_ID_PK_COLUMN);
				currentMessageVO.setMessageId(pkVale);				
				currentMessageVO.setMessageScope(currentMessage.getMessageScope());
				currentMessageVO.setMessageType(MessageServiceConstant.MESSAGESERVICE_MessageType_MESSAGE);
				currentMessageVO.setMessageTitle(currentMessage.getMessageTitle());
				currentMessageVO.setMessageContent(currentMessage.getMessageContent());
				currentMessageVO.setMessageSenderId(currentMessage.getMessageSenderId());
				currentMessageVO.setMessageSenderName(currentMessage.getMessageSenderName());
				currentMessageVO.setMessageSentDate(currentMessage.getMessageSentDate().getTime());
				currentMessageVO.setMessageReadStatus(currentMessage.getMessageReadStatus());				
				List<MessageReceiverVO> messageReceivers=getMessageReceiversList(currentMessage.getMessageReceiversId(),currentMessage.getMessageReceiversName(),currentMessage.getMessageReceiversType());
				currentMessageVO.setMessageReceivers(messageReceivers);
				messagesOfCurrentPage.add(currentMessageVO);					
			}else{				
				break;
			}			
		}				
		//get unread message count
		query.andQualifier(ExpressionFactory.matchExp(Message.MESSAGE_READ_STATUS_PROPERTY,false));
		query.setPageSize(1);
		List unReadMessageRows = context.performQuery(query);		
		int totalUnReadMessageSize = unReadMessageRows.size();
		personalMessagesVO.setUnReadMessageCount(totalUnReadMessageSize);
        return personalMessagesVO;
	}
	
	public static boolean deleteMessage(String messageId){			
		ObjectContext context = ServiceResourceHolder.getCayenneServerRuntime().getContext();
		ObjectId id = new ObjectId("Message", Message.MESSAGE_ID_PK_COLUMN, messageId);		
		Message messageToDelete=(Message)DataObjectUtils.objectForPK(context, id);		
		context.deleteObject(messageToDelete);
		context.commitChanges();
		return true;
	}
	
	public static boolean readMessage(String messageId){	
		ObjectContext context = ServiceResourceHolder.getCayenneServerRuntime().getContext();
		ObjectId id = new ObjectId("Message", Message.MESSAGE_ID_PK_COLUMN, messageId);		
		Message currentMessage=(Message)DataObjectUtils.objectForPK(context, id);
		currentMessage.setMessageReadStatus(true);		
		context.commitChanges();
		return true;
	}
	
	private static List<MessageReceiverVO> getMessageReceiversList(String messageReceiversId,String messageReceiversName,String messageReceiversType){		
		List<MessageReceiverVO> messageReceivers=new ArrayList<MessageReceiverVO>();
		String[] receiversIDArray=messageReceiversId.split("<>");
		String[] receiversNameArray=messageReceiversName.split("<>");
		String[] receiversTypeArray=messageReceiversType.split("<>");		
		for(int i=0;i<receiversIDArray.length;i++){
			String currentReceiversId=receiversIDArray[i];
			String currentReceiversName=receiversNameArray[i];
			String currentReceiversType=receiversTypeArray[i];			
			if(!currentReceiversId.trim().equals("")){
				MessageReceiverVO messageReceiverVO=new MessageReceiverVO();
				messageReceiverVO.setReceiverId(currentReceiversId);
				messageReceiverVO.setReceiverDisplayName(currentReceiversName);
				if(currentReceiversType.equals("P")){
					messageReceiverVO.setReceiverType(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverType_People);
				}
				if(currentReceiversType.equals("G")){
					messageReceiverVO.setReceiverType(MessageServiceConstant.MESSAGESERVICE_Property_MessageReceiverType_Group);
				}
				messageReceivers.add(messageReceiverVO);				
			}			
		}		
		return messageReceivers;
	}	
	
	public static PersonalNotificationsVO getPersonalNotifications(QueryPersonalNotificationsVO queryPersonalNotificationsVO){
		ObjectContext context = ServiceResourceHolder.getCayenneServerRuntime().getContext();
		//Basic query expression
		Expression queryExpression = ExpressionFactory.matchExp(Notification.NOTIFICATION_RECEIVER_ID_PROPERTY, queryPersonalNotificationsVO.getReceiverId());
		// create a query returning data rows with page size
		SelectQuery query = new SelectQuery(Notification.class,queryExpression);	
		query.addOrdering(Notification.NOTIFICATION_SENT_DATE_PROPERTY, SortOrder.DESCENDING);
		query.setPageSize(queryPersonalNotificationsVO.getNotificationsNumberPerPage());
		
		query.andQualifier(ExpressionFactory.matchExp(Notification.NOTIFICATION_SCOPE_PROPERTY, queryPersonalNotificationsVO.getNotificationScope()));		
		if(queryPersonalNotificationsVO.getQueryCriteria()!=null&&queryPersonalNotificationsVO.getQueryValue()!=null){
			if(queryPersonalNotificationsVO.getQueryCriteria().equals(Message.MESSAGE_SENDER_NAME_PROPERTY)){
				query.andQualifier(ExpressionFactory.likeExp(Notification.NOTIFICATION_SENDER_NAME_PROPERTY, "%"+queryPersonalNotificationsVO.getQueryValue()+"%"));
			}
			if(queryPersonalNotificationsVO.getQueryCriteria().equals(Message.MESSAGE_TITLE_PROPERTY)){
				query.andQualifier(ExpressionFactory.likeExp(Notification.NOTIFICATION_TITLE_PROPERTY, "%"+queryPersonalNotificationsVO.getQueryValue()+"%"));
			}
			if(queryPersonalNotificationsVO.getQueryCriteria().equals(Message.MESSAGE_SENT_DATE_PROPERTY)){				
				long sentLongDate=Long.parseLong(queryPersonalNotificationsVO.getQueryValue());				
				Date sentQueryStartDate=new Date(sentLongDate);				
				Calendar c=Calendar.getInstance();
				c.setTime(sentQueryStartDate);
				c.add(Calendar.DAY_OF_MONTH, 1);				
				Date sentQueryEndDate=c.getTime();				
				query.andQualifier(ExpressionFactory.betweenExp(Notification.NOTIFICATION_SENT_DATE_PROPERTY, sentQueryStartDate, sentQueryEndDate));
			}			
		}				
		
		PersonalNotificationsVO personalNotificationsVO=new PersonalNotificationsVO();
		//get query filter info
		personalNotificationsVO.setQueryCriteria(queryPersonalNotificationsVO.getQueryCriteria());
		personalNotificationsVO.setQueryValue(queryPersonalNotificationsVO.getQueryValue());	
		
		// the fact that result is paged is transparent
		List notificationRows = context.performQuery(query);	
		// This is safe and will NOT trigger a full fetch
		int totalNotifictionSize = notificationRows.size();		
		//get total notification count
		personalNotificationsVO.setTotalNotificationCount(totalNotifictionSize);
		
		//get total message list page count
		int pageSize=queryPersonalNotificationsVO.getNotificationsNumberPerPage();		
		int pageNumberCalculate=(int)totalNotifictionSize/pageSize;		
		int totalNotificationListPageNumber=0;
		if(pageNumberCalculate*pageSize==totalNotifictionSize){
			totalNotificationListPageNumber=pageNumberCalculate;
		}else{
			totalNotificationListPageNumber=pageNumberCalculate+1;
		}
		personalNotificationsVO.setTotalNotificationListPageNumber(totalNotificationListPageNumber);
		//get message details
		personalNotificationsVO.setLastPage(true);
		
		int currentpage=queryPersonalNotificationsVO.getCurrentNotificationListPageNumber();
		personalNotificationsVO.setCurrentNotificationListPageNumber(currentpage);		
		List<NotificationVO> notificationsOfCurrentPage=new ArrayList<NotificationVO>();	
		personalNotificationsVO.setNotificationsOfCurrentPage(notificationsOfCurrentPage);		
		for(int i=(currentpage-1)*pageSize;i<(currentpage)*pageSize;i++){			
			if(i<totalNotifictionSize){
				if(i==totalNotifictionSize-1){
					personalNotificationsVO.setLastPage(true);
				}else{
					personalNotificationsVO.setLastPage(false);
				}
				Notification currentNotification = (Notification)notificationRows.get(i);				
				NotificationVO currentNotificationVO=new NotificationVO();
				CommonNotificationVO commonNotificationVO=new CommonNotificationVO();
				currentNotificationVO.setCommonNotificationVO(commonNotificationVO);
				currentNotificationVO.setNotificationType(currentNotification.getNotificationType());				
				String pkVale=(String)currentNotification.getObjectId().getIdSnapshot().get(Notification.NOTIFICATION_ID_PK_COLUMN);
				commonNotificationVO.setNotificationId(pkVale);
				
				commonNotificationVO.setNotificationContent(currentNotification.getNotificationContent());
				commonNotificationVO.setNotificationHandleable(currentNotification.getNotificationHandleable());
				commonNotificationVO.setNotificationReadStatus(currentNotification.getNotificationReadStatus());
				commonNotificationVO.setNotificationReceiverId(currentNotification.getNotificationReceiverId());				
				commonNotificationVO.setNotificationScope(currentNotification.getNotificationScope());
				commonNotificationVO.setNotificationSenderId(currentNotification.getNotificationSenderId());
				commonNotificationVO.setNotificationSenderName(currentNotification.getNotificationSenderName());
				commonNotificationVO.setNotificationSentDate(currentNotification.getNotificationSentDate().getTime());
				commonNotificationVO.setNotificationStatus(currentNotification.getNotificationStatus());
				commonNotificationVO.setNotificationTitle(currentNotification.getNotificationTitle());
				commonNotificationVO.setNotificationType(currentNotification.getNotificationType());				
				List<MessageReceiverVO> notificationReceivers=getMessageReceiversList(currentNotification.getNotificationReceiversId(),currentNotification.getNotificationReceiversName(),currentNotification.getNotificationReceiversType());
				commonNotificationVO.setNotificationReceivers(notificationReceivers);
				
				if(commonNotificationVO.getNotificationType().equals(MessageServiceConstant.MESSAGESERVICE_NoticeType_ACTIVITYTASK)){
					ActivityTaskNotificationVO activityTaskNotificationVO=new ActivityTaskNotificationVO();
					currentNotificationVO.setActivityTaskNotificationVO(activityTaskNotificationVO);
					ActivityTaskNotificationInfo activityTaskNotificationInfo=currentNotification.getToActivityTaskNotificationInfo();
					if(activityTaskNotificationInfo!=null){
						activityTaskNotificationVO.setActivityId(activityTaskNotificationInfo.getActivityId());
						activityTaskNotificationVO.setActivityName(activityTaskNotificationInfo.getActivityName());
						activityTaskNotificationVO.setNotificationId(activityTaskNotificationInfo.getNotificationId());
						activityTaskNotificationVO.setTaskDescription(activityTaskNotificationInfo.getTaskDescription());
						activityTaskNotificationVO.setTaskDueDate(activityTaskNotificationInfo.getTaskDueDate().getTime());
						activityTaskNotificationVO.setTaskDueStatus(activityTaskNotificationInfo.getTaskDueStatus());
						activityTaskNotificationVO.setTaskName(activityTaskNotificationInfo.getTaskName());
						activityTaskNotificationVO.setTaskRole(activityTaskNotificationInfo.getTaskRole());						
					}					
				}
				if(commonNotificationVO.getNotificationType().equals(MessageServiceConstant.MESSAGESERVICE_NoticeType_EXTERNALRESOURCE)){
					ExternalResourceNotificationVO externalResourceNotificationVO=new ExternalResourceNotificationVO();
					currentNotificationVO.setExternalResourceNotificationVO(externalResourceNotificationVO);					
					ExternalResourceNotificationInfo externalResourceNotificationInfo=currentNotification.getToExternalResourceNotificationInfo();
					if(externalResourceNotificationInfo!=null){
						externalResourceNotificationVO.setNotificationId(externalResourceNotificationInfo.getNotificationId());
						externalResourceNotificationVO.setResourceName(externalResourceNotificationInfo.getResourceName());
						externalResourceNotificationVO.setResourceURL(externalResourceNotificationInfo.getResourceURL());
					}					
				}					
				notificationsOfCurrentPage.add(currentNotificationVO);					
			}else{				
				break;
			}			
		}
		//get unread notification count
		query.andQualifier(ExpressionFactory.matchExp(Notification.NOTIFICATION_READ_STATUS_PROPERTY ,false));
		query.setPageSize(1);
		List unReadNotificationRows = context.performQuery(query);		
		int totalUnReadMessageSize = unReadNotificationRows.size();
		personalNotificationsVO.setUnReadNotificationCount(totalUnReadMessageSize);	
		return personalNotificationsVO;	
	}
	
	public static boolean deleteNotification(String notificationId){			
		ObjectContext context = ServiceResourceHolder.getCayenneServerRuntime().getContext();
		ObjectId id = new ObjectId("Notification",Notification.NOTIFICATION_ID_PK_COLUMN, notificationId);		
		Notification notificationToDelete=(Notification)DataObjectUtils.objectForPK(context, id);		
		context.deleteObject(notificationToDelete);
		context.commitChanges();
		return true;
	}
	
	public static boolean readNotification(String notificationId){	
		ObjectContext context = ServiceResourceHolder.getCayenneServerRuntime().getContext();
		ObjectId id = new ObjectId("Notification", Notification.NOTIFICATION_ID_PK_COLUMN, notificationId);		
		Notification currentNotification=(Notification)DataObjectUtils.objectForPK(context, id);
		currentNotification.setNotificationReadStatus(true);		
		context.commitChanges();
		return true;
	}
	
	public static UnReadInformationNumberVO countUnReadInformationNumber(String messageScope,String receiverId){
		UnReadInformationNumberVO unReadInformationNumberVO=new UnReadInformationNumberVO();
		
		ObjectContext context = ServiceResourceHolder.getCayenneServerRuntime().getContext();
		//Basic query expression
		Expression queryUnreadMessageExpression = ExpressionFactory.matchExp(Message.MESSAGE_RECEIVER_ID_PROPERTY, receiverId);
		SelectQuery queryMessage = new SelectQuery(Message.class,queryUnreadMessageExpression);	
		queryMessage.andQualifier(ExpressionFactory.matchExp(Message.MESSAGE_SCOPE_PROPERTY, messageScope));
		queryMessage.andQualifier(ExpressionFactory.matchExp(Message.MESSAGE_READ_STATUS_PROPERTY, false));
		queryMessage.setPageSize(1);
		List unReadMessageRows = context.performQuery(queryMessage);
		// This is safe and will NOT trigger a full fetch
		int totalUnReadMessageSize = unReadMessageRows.size();
		unReadInformationNumberVO.setMessageNumber(totalUnReadMessageSize);
		//Basic query expression
		Expression queryUnreadNotificationExpression = ExpressionFactory.matchExp(Notification.NOTIFICATION_RECEIVER_ID_PROPERTY, receiverId);
		SelectQuery queryNotification = new SelectQuery(Notification.class,queryUnreadNotificationExpression);
		queryNotification.andQualifier(ExpressionFactory.matchExp(Notification.NOTIFICATION_SCOPE_PROPERTY, messageScope));
		queryNotification.andQualifier(ExpressionFactory.matchExp(Notification.NOTIFICATION_READ_STATUS_PROPERTY, false));
		queryNotification.setPageSize(1);
		List unReadNotificationRows = context.performQuery(queryNotification);
		// This is safe and will NOT trigger a full fetch
		int totalUnReadNotificationSize = unReadNotificationRows.size();
		unReadInformationNumberVO.setNotificationNumber(totalUnReadNotificationSize);
		return unReadInformationNumberVO;
	}
}