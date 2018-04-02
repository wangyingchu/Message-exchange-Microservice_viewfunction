package com.viewfunction.messageEngine.util;

public class ExchangePortInfo {
	private String portAddress;
	private String portConfig;
	private String portListenerClassName;
	
	public ExchangePortInfo(String portAddress,String portConfig,String portListenerClassName){
		this.setPortAddress(portAddress);
		this.setPortConfig(portConfig);
		this.setPortListenerClassName(portListenerClassName);
	}
	
	public String getPortAddress() {
		return portAddress;
	}
	public void setPortAddress(String portAddress) {
		this.portAddress = portAddress;
	}
	public String getPortConfig() {
		return portConfig;
	}
	public void setPortConfig(String portConfig) {
		this.portConfig = portConfig;
	}
	public String getPortListenerClassName() {
		return portListenerClassName;
	}
	public void setPortListenerClassName(String portListenerClassName) {
		this.portListenerClassName = portListenerClassName;
	}
}