package com.simon.sys.vo;

import java.util.List;

public class WorkFlowVo{
	
	//批量删除使用
	private String[] ids;
	
	private Integer page;
	private Integer limit;

	private String deploymentId;

	private List<String> deploymentIds;
	
	
	//流程部署名称
	private String deploymentName;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getDeploymentName() {
		return deploymentName;
	}
	public void setDeploymentName(String deploymentName) {
		this.deploymentName = deploymentName;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public List<String> getDeploymentIds() {
		return deploymentIds;
	}

	public void setDeploymentIds(List<String> deploymentIds) {
		this.deploymentIds = deploymentIds;
	}
}
