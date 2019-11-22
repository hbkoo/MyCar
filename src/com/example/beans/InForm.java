package com.example.beans;

public class InForm {
	private String id;              //进车单号
	private String salesmanId;      //进车操作员职工号
	private String repId;           //仓库审核员职工号
	private String modelId;         //进车轿车型号
	private String inTime;          //进车时间
	private int count;              //进车数量
	private String tag;             //进车单审核标记
	private String salesmanName;    //业务员姓名
	private String repositoryName;  //仓库审核员姓名
	
	public InForm() {}
	


	public InForm(String id, String salesmanId, String repId, String modelId, String inTime, int count, String tag,
			String salesmanName, String repositoryName) {
		super();
		this.id = id;
		this.salesmanId = salesmanId;
		this.repId = repId;
		this.modelId = modelId;
		this.inTime = inTime;
		this.count = count;
		this.tag = tag;
		this.salesmanName = salesmanName;
		this.repositoryName = repositoryName;
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
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
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



	@Override
	public String toString() {
		return "InForm [id=" + id + ", salesmanId=" + salesmanId + ", repId=" + repId + ", modelId=" + modelId
				+ ", inTime=" + inTime + ", count=" + count + ", tag=" + tag + ", salesmanName=" + salesmanName
				+ ", repositoryName=" + repositoryName + "]";
	}

	
	
}
