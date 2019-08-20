package com.simon.test.c_processInstance;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: activiti
 * @description: 流程实例测试
 * @author: simon
 * @create: 2019-08-19 11:41
 **/
public class TestProcessInstance {

    private ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();

    /**
     *部署流程
     * 使用classpath
     */
    @Test
    public void deployProcess01(){

        RepositoryService repositoryService=processEngine.getRepositoryService();
        Deployment deployment=repositoryService.createDeployment().name("请假流程001")
                .addClasspathResource("HelloWorld.bpmn")
                .addClasspathResource("HelloWorld.png")
                .deploy();
        System.out.println("部署成功 流程部署ID："+deployment.getId());

    }

    /**
     * 启动流程
     */

    @Test
    public void startProcess(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();

        /**
         * 启动方式一
         * 两个参数
         * parameter processdefId
         * parameter map 流程变量
         */
        /*String processDefId="";
        Map<String,Object> variables=new HashMap<>();
        runtimeService.startProcessInstanceById(processDefId,variables);*/

        /**
         * 启动方式二
         * 两个参数
         * parameter processDefId
         * parameter businessKey 业务ID 与流程实例ID绑定
         */
        /*String processDefId="";
        String businessKey="";
        runtimeService.startProcessInstanceById(processDefId,businessKey);*/

        /**
         * 启动方式三
         * 两个参数
         * parameter processDefId
         * parameter businessKey 业务ID 与流程实例ID绑定
         * parameter map 流程变量
         */
        /*String processDefId="";
        String businessKey="";
        Map<String,Object> variables=new HashMap<>();
        runtimeService.startProcessInstanceById(processDefId,businessKey,variables);*/
        /**
         * 启动方式四
         * 根据key来启动
         */
        /*String processDefinitionKey="";
        runtimeService.startProcessInstanceByKey(processDefinitionKey);*/

        /**
         * 启动方式五
         * 根据key与变量启动
         */
        /*String processDefinitionKey="";
        Map<String,Object> variables=new HashMap<>();
        runtimeService.startProcessInstanceByKey(processDefinitionKey,variables);*/
        /**
         * 启动方式六
         * 根据key与业务Id启动
         */
        /*String processDefinitionKey="";
        String businessKey="";
        runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey);*/

        //实际开发使用方法五、六
        String processDefinitionKey="HelloWorld";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程启动成功：流程id "+processInstance.getId()+" 流程名字 "+processInstance.getName());
    }

    /**
     * 查询个人任务
     */

    @Test
    public void queryMyTask(){
        TaskService taskService=processEngine.getTaskService();

        String assignee="李四";
        //条件
        List<Task> taskList = taskService.createTaskQuery()
//                .taskAssignee(assignee);
//        .processDefinitionId(processDefinitionId);
//                .deploymentId()
//                .deploymentIdIn(new ArrayList<>())
                .taskAssignee(assignee)
        //排序
            .orderByTaskCreateTime().desc()
        //结果
            .list();
        for (Task task:taskList
             ) {
            System.out.println("任务 ：任务ID"+task.getId());
            System.out.println("任务 ：任务名称"+task.getName());
            System.out.println("任务 ：任务执行人"+task.getAssignee());
        }
    }

    /**
     * 办理任务
     */
    @Test
    public  void  completeTask(){
        TaskService taskService=processEngine.getTaskService();

        /**
         * 方法一 id
         */
        /*String taskId="";
        taskService.complete(taskId);*/

        /**
         * 方法二 id
         * 制定流程变量
         */
        /*String taskId="";
        Map<String,Object> variables=new HashMap<>();
        taskService.complete(taskId,variables);*/

        String taskId="2504";
        taskService.complete(taskId);
        System.out.println("任务完成");
    }

    /**
     * 查询流程状态
     * 更新业务表里面的状态
     */
    @Test
    public void getState(){
        RuntimeService runtimeService=processEngine.getRuntimeService();
        /**
         * 已知流程实例ID
         */
        String processInstanceId="2501";

        ProcessInstance processInstance=runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        if (processInstance!=null){
            System.out.println("流程未结束");
        }else {
            System.out.println("流程已结束");
        }



        /**
         * 已知任务实例ID
         */
        /*String taskId="5002";
        TaskService taskService=processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String taskProcessInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance1 = runtimeService.createProcessInstanceQuery().processInstanceId(taskProcessInstanceId)
                .singleResult();
        if (processInstance1!=null){
            System.out.println("流程未结束");
        }else {
            System.out.println("流程已结束");
        }*/
    }

    /**
     * 查询当前的流程实例
     */
    @Test
    public void queryProcessInstance(){
        RuntimeService runtimeService=processEngine.getRuntimeService();
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().list();
        for (ProcessInstance processInstance:processInstanceList
             ) {
            System.out.println("流程执行实例 ID："+processInstance.getId());
            System.out.println("流程执行实例 NAME："+processInstance.getName());
            System.out.println("流程定义 ID："+processInstance.getProcessDefinitionId());
            System.out.println("流程实例 ID："+processInstance.getProcessInstanceId());
            System.out.println("##################################################");
        }
    }

    /**
     * 查询任务历史
     * 查询历史表
     */
    @Test
    public void queryHistoryTask(){

        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().list();
        if (historicTaskInstanceList!=null&&historicTaskInstanceList.size()>0){
            for (HistoricTaskInstance histask:historicTaskInstanceList
                 ) {
                System.out.println("历史任务 ID："+histask.getId());
                System.out.println("历史任务 名称："+histask.getName());
                System.out.println("历史任务 办理人："+histask.getAssignee());
                System.out.println("历史任务 开始时间："+histask.getStartTime());
                System.out.println("历史任务 完成时间："+histask.getEndTime());
                System.out.println("#############################################");
            }
        }
    }

    /**
     * 查询历史流程实例ID
     */
    @Test
    public void queryHistoryProcessInstance(){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricProcessInstance> historicProcessInstances =
                historyService.createHistoricProcessInstanceQuery().list();

        for (HistoricProcessInstance hpi:historicProcessInstances
             ) {
            System.out.println("历史流程实例 id"+hpi.getId());
            System.out.println("历史流程实例 名称"+hpi.getName());
            System.out.println("历史流程实例 部署ID"+hpi.getDeploymentId());
            System.out.println("历史流程实例 流程定义ID"+hpi.getProcessDefinitionId());
            System.out.println("###########################################");

        }

    }

}
