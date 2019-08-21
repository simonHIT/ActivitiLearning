package java.test.a_helloworld;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.List;


/**
 * 模拟请假的流程
 * @author wangjianyuan
 *
 */
public class HelloWorld {

	private ProcessEngine processEngine =ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void deploymentProcess() {
		//得到流程部署的service
		RepositoryService repositoryService=this.processEngine.getRepositoryService();
		Deployment deployment=repositoryService.createDeployment().name("请假流程001")
		.addClasspathResource("HelloWorld.bpmn")
		.addClasspathResource("HelloWorld.png")
		.deploy();
		System.out.println("部署成功：流程部署ID："+deployment.getId());
	}
	
	/**
	 * 启动流程
	 */
	@Test
	public void startProcess() {
		RuntimeService runtimeService=processEngine.getRuntimeService();
		
		//String processDefinitionId="HelloWorld:1:2504";
		String processDefinitionKeyString="HelloWorld";
		
		//runtimeService.startProcessInstanceById(processDefinitionId);
		runtimeService.startProcessInstanceByKey(processDefinitionKeyString);
		System.out.println("流程启动成功");
		
	}
	
	/**
	 * 任务查询
	 */
	@Test
	public void queryTask() {
		
		String assignee="王五";
		TaskService taskService=processEngine.getTaskService();
		List<Task> tasks=taskService.createTaskQuery().taskAssignee(assignee).list();
		for (Task task : tasks) {
			System.out.println("任务ID："+task.getId());
			System.out.println("任务ID："+task.getName());
			System.out.println("任务ID："+task.getProcessDefinitionId());
			System.out.println("任务ID："+task.getProcessInstanceId());
			System.out.println("任务ID："+task.getTaskDefinitionKey());
			System.out.println("任务ID："+task.getAssignee());
			
		}
	}
	
	/**
	 * 完成任务
	 */
	@Test
	public void completeTask() {
		
		String taskId="7502";
		TaskService taskService=processEngine.getTaskService();
		taskService.complete(taskId);
		System.out.println("task "+taskId+" completed");
	}
}
