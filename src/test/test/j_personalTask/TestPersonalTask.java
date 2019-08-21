package test.j_personalTask;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: activiti
 * @description: 个人任务测试
 * @author: simon
 * @create: 2019-08-21 11:20
 **/
public class TestPersonalTask {

    private ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
    /**
     * 个人任务制定办理人
     */
    /**
     * 方式一 在流程图 mainConfig中指定
     * 缺点 不灵活
     */

    /**
     * 方式二 使用流程变量
     */

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
     * 启动流程 流程变量
     */
    @Test
    public void startProcess() {
        RuntimeService runtimeService=processEngine.getRuntimeService();

        //String processDefinitionId="HelloWorld:1:2504";
        String processDefinitionKeyString="HelloWorld";
        Map<String,Object> variables=new HashMap<>();
        variables.put("username","张三");

        //runtimeService.startProcessInstanceById(processDefinitionId);
        runtimeService.startProcessInstanceByKey(processDefinitionKeyString,variables);
        System.out.println("流程启动成功");

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

        String taskId="5002";
//        Map<String,Object> variables=new HashMap<>();
//        variables.put("username","李四");
        TaskService taskService=processEngine.getTaskService();
//        taskService.complete(taskId,variables);
        taskService.complete(taskId);
        System.out.println("task "+taskId+" completed");
    }
}
