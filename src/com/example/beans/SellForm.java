package com.example.beans;

public class SellForm {
	private String id;             //进车单号
	private String salesmanId;     //进车操作员职工号
	private String repId;          //仓库审核员职工号
	private String modelId;        //进车轿车型号
	private int customId;          //顾客号
	private int count;             //进车数量
	private String outTime;        //进车时间
	private String tag;            //进车单审核标记
	private String salesmanName;   //业务员姓名
	private String repositoryName; //仓库审核员姓名
	private String customName;     //顾客姓名
	
	public SellForm() {	}
	
	public SellForm(String id, String salesmanId, String repId, String modelId, int customId, int count, String outTime,
			String tag, String salesmanName, String repositoryName, String customName) {
		super();
		this.id = id;
		this.salesmanId = salesmanId;
		this.repId = repId;
		this.modelId = modelId;
		this.customId = customId;
		this.count = count;
		this.outTime = outTime;
		this.tag = tag;
		this.salesmanName = salesmanName;
		this.repositoryName = repositoryName;
		this.customName = customName;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSalesmanId() {
		return salesmanId;
	}
	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}
	public String getRepId() {
		return repId;
	}
	public void setRepId(String repId) {
		this.repId = repId;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public int getCustomId() {
		return customId;
	}
	public void setCustomId(int customId) {
		this.customId = customId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	@Override
	public String toString() {
		return "SellForm [id=" + id + ", salesmanId=" + salesmanId + ", repId=" + repId + ", modelId=" + modelId
				+ ", customId=" + customId + ", count=" + count + ", outTime=" + outTime + ", tag=" + tag
				+ ", salesmanName=" + salesmanName + ", repositoryName=" + repositoryName + ", customName=" + customName
				+ "]";
	}

	
}
