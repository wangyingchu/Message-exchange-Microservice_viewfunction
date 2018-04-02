package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "QueryPersonalNotificationsVO")
public class QueryPersonalNotificationsVO {
	private String notificationScope;
	private String receiverId; 
	private int currentNotificationListPageNumber;
	private int notificationsNumberPerPage;
	private String queryCriteria;
	private String queryValue;
	public String getNotificationScope() {
		return notificationScope;
	}
	public void setNotificationScope(String notificationScope) {
		this.notificationScope = notificationScope;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public int getCurrentNotificationListPageNumber() {
		return currentNotificationListPageNumber;
	}
	public void setCurrentNotificationListPageNumber(
			int currentNotificationListPageNumber) {
		this.currentNotificationListPageNumber = currentNotificationListPageNumber;
	}
	public int getNotificationsNumberPerPage() {
		return notificationsNumberPerPage;
	}
	public void setNotificationsNumberPerPage(int notificationsNumberPerPage) {
		this.notificationsNumberPerPage = notificationsNumberPerPage;
	}
	public String getQueryCriteria() {
		return queryCriteria;
	}
	public void setQueryCriteria(String queryCriteria) {
		this.queryCriteria = queryCriteria;
	}
	public String getQueryValue() {
		return queryValue;
	}
	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}
}