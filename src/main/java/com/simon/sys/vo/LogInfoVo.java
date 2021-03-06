package com.simon.sys.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.simon.sys.domain.LogInfo;

public class LogInfoVo extends LogInfo{
	
	//批量删除使用
	private Integer[] ids;
	
	private Integer page;
	private Integer limit;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime;
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
	
	

}
