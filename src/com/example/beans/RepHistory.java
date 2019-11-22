package com.example.beans;
/**
 * 进出仓库的所有历史记录
 * @author 19216
 *
 */
public class RepHistory {
	private String id;            //单号
	private String salesmanId;    //业务员号
	private String repositoryId;  //仓库管理员号
	private String modelId;       //轿车型号
	private int count;            //进出数量
	private String time;          //进出时间
	private String tag;           //入库还是出库标记
	private String salesmanName;  //业务员姓名
	private String repositoryName;//仓库管理员姓名
	
	public RepHistory() {}

	public RepHistory(String id, String salesmanId, String repositoryId, String modelId, int count, String time,
			String tag, String salesmanName, String repositoryName) {
		super();
		this.id = id;
		this.salesmanId = salesmanId;
		this.repositoryId = repositoryId;
		this.modelId = modelId;
		this.count = count;
		this.time = time;
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

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
		return "RepHistory [id=" + id + ", salesmanId=" + salesmanId + ", repositoryId=" + repositoryId + ", modelId="
				+ modelId + ", count=" + count + ", time=" + time + ", tag=" + tag + ", salesmanName=" + salesmanName
				+ ", repositoryName=" + repositoryName + "]";
	}
	
}
