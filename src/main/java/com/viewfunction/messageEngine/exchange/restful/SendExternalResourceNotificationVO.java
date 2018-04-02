package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SendExternalResourceNotificationVO")
public class SendExternalResourceNotificationVO {
	private CommonNotificationVO commonNotificationVO;
	private ExternalResourceNotificationVO externalResourceNotificationVO;
	public CommonNotificationVO getCommonNotificationVO() {
		return commonNotificationVO;
	}
	public void setCommonNotificationVO(CommonNotificationVO commonNotificationVO) {
		this.commonNotificationVO = commonNotificationVO;
	}
	public ExternalResourceNotificationVO getExternalResourceNotificationVO() {
		return externalResourceNotificationVO;
	}
	public void setExternalResourceNotificationVO(
			ExternalResourceNotificationVO externalResourceNotificationVO) {
		this.externalResourceNotificationVO = externalResourceNotificationVO;
	}
}