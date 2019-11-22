package com.example.beans;

public class ServeForm {
	private String id;          //售后单号
	private String serverId;    //售后操作员职工号
	private String sellId;      //售车单号
	private String serveTime;   //售后时间
	private String satisfy;     //满意程度
	private String serverName;  //售后操作员姓名
	
	public ServeForm() {}

	
	public ServeForm(String id, String serverId, String sellId, String serveTime, String satisfy, String serverName) {
		super();
		this.id = id;
		this.serverId = serverId;
		this.sellId = sellId;
		this.serveTime = serveTime;
		this.satisfy = satisfy;
		this.serverName = serverName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getSellId() {
		return sellId;
	}

	public void setSellId(String sellId) {
		this.sellId = sellId;
	}

	public String getServeTime() {
		return serveTime;
	}

	public void setServeTime(String serveTime) {
		this.serveTime = serveTime;
	}

	public String getSatisfy() {
		return satisfy;
	}

	public void setSatisfy(String satisfy) {
		this.satisfy = satisfy;
	}

	public String getServerName() {
		return serverName;
	}


	public void setServerName(String serverName) {
		this.serverName = serverName;
	}


	@Override
	public String toString() {
		return "ServeForm [id=" + id + ", serverId=" + serverId + ", sellId=" + sellId + ", serveTime=" + serveTime
				+ ", satisfy=" + satisfy + ", serverName=" + serverName + "]";
	}

	
}
