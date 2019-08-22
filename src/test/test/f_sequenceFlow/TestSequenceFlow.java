package test.f_sequenceFlow;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: activiti
 * @description: 连线 测试
 * @author: simon
 * @create: 2019-08-20 18:17
 **/
public class TestSequenceFlow {
    ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程
     */
    @Test
    public void deploymentProcess(){

        RepositoryService repositoryService=processEngine.getRepositoryService();
        Deployment deployment=repositoryService.createDeployment().name("报销流程")
                .addClasspathResource("SequenceFlowBPMN.bpmn")
                .deploy();
        System.out.println("部署成功 流程部署ID："+deployment.getId());
    }


    /**
     * 启动流程并设置流程变量
     */
    @Test
    public void startProcess(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();

        //实际开发使用方法五、六
        String processDefinitionKey="SequenceFlowBPMN";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程启动成功：流程id "+processInstance.getId()+" 流程名字 "+processInstance.getName());
    }

    /**
     * 任务查询
     */
    @Test
    public void queryTask() {

        String assignee="李四";
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

        String taskId="22503";
        TaskService taskService=processEngine.getTaskService();
        taskService.complete(taskId);
        System.out.println("task "+taskId+" completed");
    }

    /**
     * 完成任务 使用流程变量决定流程走向
     */
    @Test
    public void completeTaskByVariable() {

        String taskId="20002";
        Map<String,Object> variables=new HashMap<>();
        variables.put("outcome","重要");
        TaskService taskService=processEngine.getTaskService();
//        taskService.setVariables(taskId,variables);
        taskService.complete(taskId,variables);
        System.out.println("task "+taskId+" completed");
    }

}
