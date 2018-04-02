package com.viewfunction.messageEngine.exchange.restful;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "PersonalMessagesVO")
public class PersonalMessagesVO {
	private int unReadMessageCount;
	private int totalMessageCount;
	private int currentMessageListPageNumber;
	private int totalMessageListPageNumber;
	private List<MessageVO> messagesOfCurrentPage;	
	private String queryCriteria;
	private String queryValue;
	private boolean isLastPage;
	public int getUnReadMessageCount() {
		return unReadMessageCount;
	}
	public void setUnReadMessageCount(int unReadMessageCount) {
		this.unReadMessageCount = unReadMessageCount;
	}
	public int getTotalMessageCount() {
		return totalMessageCount;
	}
	public void setTotalMessageCount(int totalMessageCount) {
		this.totalMessageCount = totalMessageCount;
	}
	public int getCurrentMessageListPageNumber() {
		return currentMessageListPageNumber;
	}
	public void setCurrentMessageListPageNumber(int currentMessageListPageNumber) {
		this.currentMessageListPageNumber = currentMessageListPageNumber;
	}
	public int getTotalMessageListPageNumber() {
		return totalMessageListPageNumber;
	}
	public void setTotalMessageListPageNumber(int totalMessageListPageNumber) {
		this.totalMessageListPageNumber = totalMessageListPageNumber;
	}
	public List<MessageVO> getMessagesOfCurrentPage() {
		return messagesOfCurrentPage;
	}
	public void setMessagesOfCurrentPage(List<MessageVO> messagesOfCurrentPage) {
		this.messagesOfCurrentPage = messagesOfCurrentPage;
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