package com.simon.sys.service;

import com.simon.sys.utils.DataGridView;
import com.simon.sys.vo.LogInfoVo;

public interface LogInfoService {
	//添加
	void addLogInfo(LogInfoVo infoVo);
	//查询
	DataGridView queryAllLogInfo(LogInfoVo infoVo);
	//删除
	void deleteLogInfo(LogInfoVo logInfoVo);

}
