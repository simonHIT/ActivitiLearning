package test.k_groupTask;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: activiti
 * @description: 组任务测试
 * @author: simon
 * @create: 2019-08-21 15:02
 **/
public class TestGroupTask {

    private ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();

    /**
     * 一个任务可以被许多上层领导看见，但是办理人只有一个
     * 只有主任务才可以回退
     */

    /**
     * 方式一 直接修改流程图 写死
     */

    /**
     * 方式二 使用流程变量
     */

    /**
     * 方式三 使用类 监听器
     */

    /**
     * 部署流程
     */
    @Test
    public void deploymentProcess1() {
        //得到流程部署的service
        RepositoryService repositoryService=this.processEngine.getRepositoryService();
        Deployment deployment=repositoryService.createDeployment().name("流程001")
                .addClasspathResource("GroupTask.bpmn")
                .addClasspathResource("GroupTask.png")
                .deploy();
        System.out.println("部署成功：流程部署ID："+deployment.getId());
    }

    @Test
    public void deploymentProcess2() {
        //得到流程部署的service
        RepositoryService repositoryService=this.processEngine.getRepositoryService();
        Deployment deployment=repositoryService.createDeployment().name("流程002")
                .addClasspathResource("GroupTask2.bpmn")
                //.addClasspathResource("GroupTask2.png")
                .deploy();
        System.out.println("部署成功：流程部署ID："+deployment.getId());
    }
    @Test
    public void deploymentProcess3() {
        //得到流程部署的service
        RepositoryService repositoryService=this.processEngine.getRepositoryService();
        Deployment deployment=repositoryService.createDeployment().name("流程003")
                .addClasspathResource("GroupTask3.bpmn")
                .addClasspathResource("GroupTask3.png")
                .deploy();
        System.out.println("部署成功：流程部署ID："+deployment.getId());
    }

    /**
     * 启动流程 流程变量
     */
    @Test
    public void startProcess1() {
        RuntimeService runtimeService=processEngine.getRuntimeService();

        //String processDefinitionId="HelloWorld:1:2504";
        String processDefinitionKeyString="GroupTask";
        /*Map<String,Object> variables=new HashMap<>();
        variables.put("username","张三");*/

        //runtimeService.startProcessInstanceById(processDefinitionId);
        runtimeService.startProcessInstanceByKey(processDefinitionKeyString);
        System.out.println("流程启动成功");

    }
    @Test
    public void startProcess2() {
        RuntimeService runtimeService=processEngine.getRuntimeService();

        //String processDefinitionId="HelloWorld:1:2504";
        String processDefinitionKeyString="GroupTask";
        Map<String,Object> variables=new HashMap<>();
        variables.put("usernames","小A,小B,小C,小D");

        //runtimeService.startProcessInstanceById(processDefinitionId);
        runtimeService.startProcessInstanceByKey(processDefinitionKeyString,variables);
        System.out.println("流程启动成功");

    }

    @Test
    public void startProcess3() {
        RuntimeService runtimeService=processEngine.getRuntimeService();

        //String processDefinitionId="HelloWorld:1:2504";
        String processDefinitionKeyString="GroupTask";
//        Map<String,Object> variables=new HashMap<>();
//        variables.put("usernames","小A,小B,小C,小D");

        //runtimeService.startProcessInstanceById(processDefinitionId);
        runtimeService.startProcessInstanceByKey(processDefinitionKeyString);
        System.out.println("流程启动成功");

    }

    /**
     * 组任务任务查询
     */
    @Test
    public void queryGroupTask() {

        String assignee="李四";
        String candidateUser="小B";

        TaskService taskService=processEngine.getTaskService();
//        List<Task> tasks=taskService.createTaskQuery().taskAssignee(assignee).list();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(candidateUser).list();
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
     * 个人任务任务查询
     */
    @Test
    public void queryTask() {

        String assignee="小A";
//        String candidateUser="小A";

        TaskService taskService=processEngine.getTaskService();
        List<Task> tasks=taskService.createTaskQuery().taskAssignee(assignee).list();
//        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(candidateUser).list();
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
     * 任务拾取
     */
    @Test
    public void claim(){
        String taskId="2504";
        TaskService taskService = processEngine.getTaskService();
        taskService.claim(taskId,"小A");
        System.out.println("任务拾取成功");
    }

    /**
     * 任务回退
     */
    @Test
    public void claimBack(){
        String taskId="2504";
        TaskService taskService = processEngine.getTaskService();
        taskService.setAssignee(taskId,null);
        System.out.println("任务回退成功");
    }

    /**
     * 查询组任务成员列表
     */

    @Test
    public void findGroupUserList(){

        String taskId="2504";
        List<IdentityLink> identityLinkList = processEngine.getTaskService().getIdentityLinksForTask(taskId);
        for (IdentityLink iden:identityLinkList
             ) {
            System.out.println("userId="+iden.getUserId());
            System.out.println("taskId="+iden.getTaskId());
            System.out.println("proDefId="+iden.getProcessDefinitionId());
            System.out.println("proInstanceId="+iden.getProcessInstanceId());
            System.out.println("groupId="+iden.getGroupId());
            System.out.println("type="+iden.getType());
            System.out.println("#############################################");

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
