package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "NotificationVO")
public class NotificationVO {
	private String notificationType;
	private CommonNotificationVO commonNotificationVO;
	private ActivityTaskNotificationVO activityTaskNotificationVO;
	private ExternalResourceNotificationVO externalResourceNotificationVO;
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public CommonNotificationVO getCommonNotificationVO() {
		return commonNotificationVO;
	}
	public void setCommonNotificationVO(CommonNotificationVO commonNotificationVO) {
		this.commonNotificationVO = commonNotificationVO;
	}
	public ActivityTaskNotificationVO getActivityTaskNotificationVO() {
		return activityTaskNotificationVO;
	}
	public void setActivityTaskNotificationVO(ActivityTaskNotificationVO activityTaskNotificationVO) {
		this.activityTaskNotificationVO = activityTaskNotificationVO;
	}
	public ExternalResourceNotificationVO getExternalResourceNotificationVO() {
		return externalResourceNotificationVO;
	}
	public void setExternalResourceNotificationVO(
			ExternalResourceNotificationVO externalResourceNotificationVO) {
		this.externalResourceNotificationVO = externalResourceNotificationVO;
	}
}