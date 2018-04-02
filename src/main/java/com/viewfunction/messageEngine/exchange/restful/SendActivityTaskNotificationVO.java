package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SendActivityTaskNotificationVO")
public class SendActivityTaskNotificationVO {
	private CommonNotificationVO commonNotificationVO;
	private ActivityTaskNotificationVO activityTaskNotificationVO;
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
}
