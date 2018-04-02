package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "QueryPersonalMessagesVO")
public class QueryPersonalMessagesVO {
	private String messageScope;
	private String receiverId; 
	private int currentMessageListPageNumber;
	private int messagesNumberPerPage;
	private String queryCriteria;
	private String queryValue;
	public String getMessageScope() {
		return messageScope;
	}
	public void setMessageScope(String messageScope) {
		this.messageScope = messageScope;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public int getCurrentMessageListPageNumber() {
		return currentMessageListPageNumber;
	}
	public void setCurrentMessageListPageNumber(int currentMessageListPageNumber) {
		this.currentMessageListPageNumber = currentMessageListPageNumber;
	}
	public int getMessagesNumberPerPage() {
		return messagesNumberPerPage;
	}
	public void setMessagesNumberPerPage(int messagesNumberPerPage) {
		this.messagesNumberPerPage = messagesNumberPerPage;
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