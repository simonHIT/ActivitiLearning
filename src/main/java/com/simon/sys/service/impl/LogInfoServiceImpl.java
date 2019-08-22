package com.simon.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.simon.sys.domain.LogInfo;
import com.simon.sys.mapper.LogInfoMapper;
import com.simon.sys.service.LogInfoService;
import com.simon.sys.utils.DataGridView;
import com.simon.sys.vo.LogInfoVo;

@Service
public class LogInfoServiceImpl implements LogInfoService{
	@Autowired
	private LogInfoMapper logInfoMapper;
	@Override
	public void addLogInfo(LogInfoVo infoVo) {
		this.logInfoMapper.insert(infoVo);
	}
	@Override
	public DataGridView queryAllLogInfo(LogInfoVo infoVo) {
		Page<Object> page=PageHelper.startPage(infoVo.getPage(), infoVo.getLimit());
		List<LogInfo> data = this.logInfoMapper.queryAllLogInfo(infoVo);
		return new DataGridView(page.getTotal(), data);
	}
	@Override
	public void deleteLogInfo(LogInfoVo logInfoVo) {
		this.logInfoMapper.deleteByPrimaryKey(logInfoVo.getId());
	}
}
