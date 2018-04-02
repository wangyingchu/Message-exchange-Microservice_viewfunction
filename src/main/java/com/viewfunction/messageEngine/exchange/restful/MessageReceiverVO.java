package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MessageReceiverVO")
public class MessageReceiverVO {
	private String receiverId;
	private String receiverDisplayName;
	private String receiverType;
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public String getReceiverDisplayName() {
		return receiverDisplayName;
	}
	public void setReceiverDisplayName(String receiverDisplayName) {
		this.receiverDisplayName = receiverDisplayName;
	}
}