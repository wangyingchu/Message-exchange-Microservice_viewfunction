package com.viewfunction.messageEngine.exchange.restful;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MessageVO")
public class MessageVO {
	private String messageScope;
	private String messageType;	
	private String messageTitle;
	private String messageContent;
	private String messageSenderId;
	private String messageSenderName;
	private List<MessageReceiverVO> messageReceivers;
	private long messageSentDate;
	private String messageId;
	private boolean messageReadStatus;
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	public List<MessageReceiverVO> getMessageReceivers() {
		return messageReceivers;
	}
	public void setMessageReceivers(List<MessageReceiverVO> messageReceivers) {
		this.messageReceivers = messageReceivers;
	}
	public String getMessageScope() {
		return messageScope;
	}
	public void setMessageScope(String messageScope) {
		this.messageScope = messageScope;
	}
	public String getMessageSenderId() {
		return messageSenderId;
	}
	public void setMessageSenderId(String messageSenderId) {
		this.messageSenderId = messageSenderId;
	}
	public String getMessageSenderName() {
		return messageSenderName;
	}
	public void setMessageSenderName(String messageSenderName) {
		this.messageSenderName = messageSenderName;
	}
	public long getMessageSentDate() {
		return messageSentDate;
	}
	public void setMessageSentDate(long messageSentDate) {
		this.messageSentDate = messageSentDate;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public boolean isMessageReadStatus() {
		return messageReadStatus;
	}
	public void setMessageReadStatus(boolean messageReadStatus) {
		this.messageReadStatus = messageReadStatus;
	}	
}