package com.viewfunction.messageEngine.exchange.restful;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CommonNotificationVO")
public class CommonNotificationVO {	
	private String groupId;
	private String notificationContent;
	private boolean notificationHandleable;
	private boolean notificationReadStatus;
	private String notificationReceiverId;
	private List<MessageReceiverVO> notificationReceivers;
	private String notificationScope;
	private String notificationSenderId;
	private String notificationSenderName;
	private long notificationSentDate;
	private String notificationStatus;
	private String notificationTitle;
	private String notificationType;	
	private String notificationId;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getNotificationContent() {
		return notificationContent;
	}
	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}
	public boolean isNotificationHandleable() {
		return notificationHandleable;
	}
	public void setNotificationHandleable(boolean notificationHandleable) {
		this.notificationHandleable = notificationHandleable;
	}
	public boolean isNotificationReadStatus() {
		return notificationReadStatus;
	}
	public void setNotificationReadStatus(boolean notificationReadStatus) {
		this.notificationReadStatus = notificationReadStatus;
	}
	public String getNotificationReceiverId() {
		return notificationReceiverId;
	}
	public void setNotificationReceiverId(String notificationReceiverId) {
		this.notificationReceiverId = notificationReceiverId;
	}
	public List<MessageReceiverVO> getNotificationReceivers() {
		return notificationReceivers;
	}
	public void setNotificationReceivers(List<MessageReceiverVO> notificationReceivers) {
		this.notificationReceivers = notificationReceivers;
	}
	public String getNotificationScope() {
		return notificationScope;
	}
	public void setNotificationScope(String notificationScope) {
		this.notificationScope = notificationScope;
	}
	public String getNotificationSenderId() {
		return notificationSenderId;
	}
	public void setNotificationSenderId(String notificationSenderId) {
		this.notificationSenderId = notificationSenderId;
	}
	public String getNotificationSenderName() {
		return notificationSenderName;
	}
	public void setNotificationSenderName(String notificationSenderName) {
		this.notificationSenderName = notificationSenderName;
	}
	public long getNotificationSentDate() {
		return notificationSentDate;
	}
	public void setNotificationSentDate(long notificationSentDate) {
		this.notificationSentDate = notificationSentDate;
	}
	public String getNotificationStatus() {
		return notificationStatus;
	}
	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
	public String getNotificationTitle() {
		return notificationTitle;
	}
	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
}