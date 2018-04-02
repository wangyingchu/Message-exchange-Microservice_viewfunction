package com.viewfunction.messageEngine.exchange.restful;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PersonalNotificationsVO")
public class PersonalNotificationsVO {
	private int unReadNotificationCount;
	private int totalNotificationCount;
	private int currentNotificationListPageNumber;
	private int totalNotificationListPageNumber;
	private List<NotificationVO> notificationsOfCurrentPage;	
	private String queryCriteria;
	private String queryValue;
	private boolean isLastPage;
	public int getUnReadNotificationCount() {
		return unReadNotificationCount;
	}
	public void setUnReadNotificationCount(int unReadNotificationCount) {
		this.unReadNotificationCount = unReadNotificationCount;
	}
	public int getTotalNotificationCount() {
		return totalNotificationCount;
	}
	public void setTotalNotificationCount(int totalNotificationCount) {
		this.totalNotificationCount = totalNotificationCount;
	}
	public int getCurrentNotificationListPageNumber() {
		return currentNotificationListPageNumber;
	}
	public void setCurrentNotificationListPageNumber(
			int currentNotificationListPageNumber) {
		this.currentNotificationListPageNumber = currentNotificationListPageNumber;
	}
	public int getTotalNotificationListPageNumber() {
		return totalNotificationListPageNumber;
	}
	public void setTotalNotificationListPageNumber(
			int totalNotificationListPageNumber) {
		this.totalNotificationListPageNumber = totalNotificationListPageNumber;
	}
	public List<NotificationVO> getNotificationsOfCurrentPage() {
		return notificationsOfCurrentPage;
	}
	public void setNotificationsOfCurrentPage(
			List<NotificationVO> notificationsOfCurrentPage) {
		this.notificationsOfCurrentPage = notificationsOfCurrentPage;
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
	public boolean isLastPage() {
		return isLastPage;
	}
	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
}
