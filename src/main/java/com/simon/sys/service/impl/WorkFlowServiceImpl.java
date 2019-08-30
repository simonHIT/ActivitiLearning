package com.simon.sys.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipInputStream;

import com.simon.sys.constast.SYSConstast;
import com.simon.sys.domain.LeaveBill;
import com.simon.sys.mapper.LeaveBillMapper;
import com.simon.sys.utils.SessionUtils;
import com.simon.sys.vo.act.ActTaskEntity;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simon.sys.service.WorkFlowService;
import com.simon.sys.utils.DataGridView;
import com.simon.sys.vo.WorkFlowVo;
import com.simon.sys.vo.act.ActDeploymentEntity;
import com.simon.sys.vo.act.ActProcessDefinitionEntity;

@Service
public class WorkFlowServiceImpl implements WorkFlowService{
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private FormService formService;
	@Autowired
	private ManagementService managementService;

	@Autowired
	private LeaveBillMapper leaveBillMapper;
	
	
	/**
	 * 查询流程部署信息
	 */
	public DataGridView queryProcessDeploy(WorkFlowVo workFlowVo) {
		if(workFlowVo.getDeploymentName()==null) {
			workFlowVo.setDeploymentName("");
		}
		String name=workFlowVo.getDeploymentName();
		//查询总条数
		long count = repositoryService.createDeploymentQuery().deploymentNameLike("%"+name+"%").count();
		//查询
		int firstResult = (workFlowVo.getPage() - 1) * workFlowVo.getLimit();
		int maxResults=workFlowVo.getLimit();
		List<Deployment> list = repositoryService.createDeploymentQuery().deploymentNameLike("%"+name+"%").listPage(firstResult, maxResults);
		List<ActDeploymentEntity> data=new ArrayList<ActDeploymentEntity>();
		for (Deployment deployment : list) {
			ActDeploymentEntity entity=new ActDeploymentEntity();
			//copy
			BeanUtils.copyProperties(deployment, entity);
			data.add(entity);
		}
		return new DataGridView(count, data);
	}

	/**
	 * 查询流程定义
	 */
	@Override
	public DataGridView queryAllProcessDefinition(WorkFlowVo workFlowVo) {
		if(workFlowVo.getDeploymentName()==null) {
			workFlowVo.setDeploymentName("");
		}
		String name=workFlowVo.getDeploymentName();
		//先根据部署的的名称模糊查询出所有的部署的ID
		List<Deployment> dlist = repositoryService.createDeploymentQuery().deploymentNameLike("%"+name+"%").list();
		Set<String> deploymentIds=new HashSet<>();
		for (Deployment deployment : dlist) {
			deploymentIds.add(deployment.getId());
		}
		long count=0;
		List<ActProcessDefinitionEntity> data=new ArrayList<>();
		if(deploymentIds.size()>0) {
			 count = this.repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIds).count();
			//查询流程部署信息
				int firstResult=(workFlowVo.getPage()-1)*workFlowVo.getLimit();
				int maxResults=workFlowVo.getLimit();
				List<ProcessDefinition> list = this.repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIds).listPage(firstResult, maxResults);
				for (ProcessDefinition pd : list) {
					ActProcessDefinitionEntity entity=new ActProcessDefinitionEntity();
					BeanUtils.copyProperties(pd, entity);
					data.add(entity);
				}
		}
		return new DataGridView(count, data);
	}

	/**
	 * 添加流程部署
	 * @param inputStream
	 * @param deploymentName
	 */
	@Override
	public void addWorkFlow(InputStream inputStream, String deploymentName) {
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		this.repositoryService.createDeployment().name(deploymentName).
				addZipInputStream(zipInputStream).deploy();
		try {
			zipInputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteWorkFlow(String deploymentId) {
		this.repositoryService.deleteDeployment(deploymentId,true);
	}

	/**
	 * 根据部署id获取图片流
	 * @param deploymentId
	 * @return
	 */

	@Override
	public InputStream queryProcessDeploymentImage(String deploymentId) {
		ProcessDefinition processDefinition =
				this.repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
		String diagramResourceName = processDefinition.getDiagramResourceName();
		InputStream resourceAsStream = this.repositoryService.getResourceAsStream(deploymentId, diagramResourceName);

		return resourceAsStream;
	}

	@Override
	public InputStream queryProcessDeploymentImageByTaskId(String taskId) {
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		String deploymentId = processInstance.getDeploymentId();
		InputStream inputStream = queryProcessDeploymentImage(deploymentId);
		return inputStream;
	}

	@Override
	public void startProcess(Integer leaveBillId) {

		String processDefinitionKey = LeaveBill.class.getSimpleName();
		String businessKey = processDefinitionKey + ":" + leaveBillId;
		Map<String,Object> variables=new HashMap<>();

		variables.put("username", SessionUtils.getCurrentUser().getName());
		ProcessInstance processInstance =
				this.runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);

		//更新请假单的状态
		LeaveBill leaveBill = leaveBillMapper.selectByPrimaryKey(leaveBillId);
		leaveBill.setState(SYSConstast.STATE_LEAVEBILL_FIRST);

		leaveBillMapper.updateByPrimaryKeySelective(leaveBill);
	}

	@Override
	public DataGridView queryCurrentUserTask(WorkFlowVo workFlowVo) {

		String assignee=SessionUtils.getCurrentUser().getName();
		long count =this.taskService.createTaskQuery().taskAssignee(assignee).count();

		int firstResult=(workFlowVo.getPage()-1)*workFlowVo.getLimit();
		int maxResults=workFlowVo.getLimit();

		List<Task> taskList =
				this.taskService.createTaskQuery().taskAssignee(assignee).listPage(firstResult,maxResults);

		ArrayList<ActTaskEntity> actTaskEntities = new ArrayList<>();
		for (Task task:taskList
			 ) {
			ActTaskEntity actTaskEntity=new ActTaskEntity();
			BeanUtils.copyProperties(task,actTaskEntity);
			actTaskEntities.add(actTaskEntity);
		}
		return new DataGridView(count,actTaskEntities);
	}

	@Override
	public LeaveBill queryLeaveBillByTaskId(String taskId) {

		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance =
				this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		String businessKey = processInstance.getBusinessKey();
		String[] strings = businessKey.split(":");
		String leaveBillId=strings[1];

		LeaveBill leaveBill = leaveBillMapper.selectByPrimaryKey(Integer.parseInt(leaveBillId));
		return leaveBill;
	}

	@Override
	public List<String> queryOutcomesByTaskId(String taskId) {

		ArrayList<String> names = new ArrayList<>();

		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		String taskName = task.getName();
		String processDefinitionId = task.getProcessDefinitionId();
		String processInstanceId = task.getProcessInstanceId();

		ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		String activityId = processInstance.getActivityId();

		ProcessDefinitionEntity processDefinition =
				(ProcessDefinitionEntity) this.repositoryService.getProcessDefinition(processDefinitionId);

		ActivityImpl activity = processDefinition.findActivity(activityId);

		List<PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();

		if (outgoingTransitions!=null&&outgoingTransitions.size()>0){
			for (PvmTransition p:outgoingTransitions
				 ) {
				String property = p.getProperty("name").toString();
				names.add(property);
			}
		}
		return names;
	}


}
