package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UnReadInformationNumberVO")
public class UnReadInformationNumberVO {
	private int messageNumber;
	private int notificationNumber;
	public int getMessageNumber() {
		return messageNumber;
	}
	public void setMessageNumber(int messageNumber) {
		this.messageNumber = messageNumber;
	}
	public int getNotificationNumber() {
		return notificationNumber;
	}
	public void setNotificationNumber(int notificationNumber) {
		this.notificationNumber = notificationNumber;
	}
}