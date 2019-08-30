package com.simon.sys.service;

import com.simon.sys.domain.LeaveBill;
import com.simon.sys.utils.DataGridView;
import com.simon.sys.vo.WorkFlowVo;


import java.io.InputStream;
import java.util.List;

public interface WorkFlowService {
	//查询流程部署信息
	public DataGridView queryProcessDeploy(WorkFlowVo workFlowVo);
	//查询所有的流程定义
	public DataGridView queryAllProcessDefinition(WorkFlowVo workFlowVo);

	//添加流程定义
	public void addWorkFlow(InputStream inputStream,String deploymentName);

	//删除流程定义
	public void deleteWorkFlow(String deploymentId);

	public InputStream queryProcessDeploymentImage(String depoymentId);

	public InputStream queryProcessDeploymentImageByTaskId(String taskId);

	public void startProcess(Integer leaveBillId);

	public DataGridView queryCurrentUserTask(WorkFlowVo workFlowVo);

	public LeaveBill queryLeaveBillByTaskId(String taskId);

	public List<String> queryOutcomesByTaskId(String taskId);

}
