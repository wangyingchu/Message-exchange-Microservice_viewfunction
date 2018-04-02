package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MessageOperationResultVO")
public class MessageOperationResultVO {
	private boolean operationResult;

	public boolean isOperationResult() {
		return operationResult;
	}

	public void setOperationResult(boolean operationResult) {
		this.operationResult = operationResult;
	}

}
