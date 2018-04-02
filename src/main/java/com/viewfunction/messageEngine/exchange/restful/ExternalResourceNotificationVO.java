package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ExternalResourceNotificationVO")
public class ExternalResourceNotificationVO {
	private String resourceName;
	private String resourceURL;
	private String notificationId;
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceURL() {
		return resourceURL;
	}
	public void setResourceURL(String resourceURL) {
		this.resourceURL = resourceURL;
	}
	public String getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
}