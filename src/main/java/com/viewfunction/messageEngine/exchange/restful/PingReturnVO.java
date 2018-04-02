package com.viewfunction.messageEngine.exchange.restful;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PingReturnVO")
public class PingReturnVO {
	private String voName;
	private long tiemStamp;

	public String getVoName() {
		return voName;
	}

	public void setVoName(String voName) {
		this.voName = voName;
	}

	public long getTiemStamp() {
		return tiemStamp;
	}

	public void setTiemStamp(long tiemStamp) {
		this.tiemStamp = tiemStamp;
	}
}