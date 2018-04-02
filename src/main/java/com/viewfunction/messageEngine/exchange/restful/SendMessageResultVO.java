package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SendMessageResultVO")
public class SendMessageResultVO {
	private boolean sendSuccess;

	public boolean isSendSuccess() {
		return sendSuccess;
	}

	public void setSendSuccess(boolean sendSuccess) {
		this.sendSuccess = sendSuccess;
	}
}