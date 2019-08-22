package test.d_processVariables;

import model.User;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: activiti
 * @description: 流程变量测试
 * @author: simon
 * @create: 2019-08-20 14:53
 *
 * 相关表 act_ru_variables
 *       act_hi_variables
 **/
public class TestProcessVariables {

    ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程
     */
    @Test
    public void deploymentProcess(){

        RepositoryService repositoryService=processEngine.getRepositoryService();
        Deployment deployment=repositoryService.createDeployment().name("请假流程001")
                .addClasspathResource("HelloWorld.bpmn")
                .addClasspathResource("HelloWorld.png")
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
        String processDefinitionKey="HelloWorld";

        //创建流程启动变量
        Map<String,Object> variables=new HashMap<>();
        variables.put("请假天数",5);
        variables.put("请假原因","生病了");
        variables.put("请假时间",new Date());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey,variables);
        System.out.println("流程启动成功：流程id "+processInstance.getId()+" 流程名字 "+processInstance.getName());
    }

    /**
     * 设置流程变量 方式一 使用runtimeService
     */
    @Test
    public void completeTaskWithVariables(){
        RuntimeService runtimeService = processEngine.getRuntimeService();

        String executionId="2501";

        /**
         * 方式一 分散设置
         */
        /*runtimeService.setVariable(executionId,"请假人","小明");
        System.out.println("流程变量设置成功");*/

        /**
         * 方法二 使用map设置
         */

        /*Map<String,Object> variables=new HashMap<>();
        variables.put("请假天数",6);
        variables.put("请假原因","生病了");
        variables.put("请假时间",new Date());
        runtimeService.setVariables(executionId,variables);
        System.out.println("变量设置成功");*/

        /**
         * 方法三 使用map同时传入类实例作为变量
         */
        /*Map<String,Object> variables=new HashMap<>();
        variables.put("请假天数",6);
        variables.put("请假原因","生病了");
        variables.put("请假时间",new Date());
        variables.put("用户",new User(1,"小明"));
        runtimeService.setVariables(executionId,variables);
        System.out.println("变量设置成功");*/


    }
    /**
     * 方法四 使用taskservice设置流程变量
     */

    @Test
    public void setVariables2(){
        TaskService taskService = processEngine.getTaskService();
        String taskId="2507";
        Map<String,Object> variables=new HashMap<>();
        variables.put("任务ID设置",9);
        taskService.setVariables(taskId,variables);
        System.out.println("流程变量设置成功");
    }

    /**
     * 获取流程变量
     * 需要首先获得流程实例
     * 再由流程实例ID加key获得流程变量
     * 存对象需要设置SerializableId 用于取对象的时候
     */

    @Test
    public void getVariable(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Integer timedays = (Integer) runtimeService.getVariable("2501", "请假天数");
        Date createTime = (Date) runtimeService.getVariable("2501", "请假时间");
        User user = runtimeService.getVariable("2501", "用户", User.class);
        System.out.println("获取流程变量成功");
        System.out.println(timedays);
        System.out.println(createTime.toString());
        System.out.println(user.getId()+user.getName());
        System.out.println("############################################");
    }

    /**
     * 查询历史流程变量
     */

    @Test
    public void queryHistoryVariables(){
        HistoryService historyService = processEngine.getHistoryService();

        /*HistoricVariableInstance results = (HistoricVariableInstance) historyService.createHistoricVariableInstanceQuery()
                .id("2503").singleResult();
        System.out.println(results.toString());*/

        String processInstanceId="2501";
        List<HistoricVariableInstance> historicVariableInstances =
                historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        if (historicVariableInstances!=null&&historicVariableInstances.size()>0){
            for (HistoricVariableInstance his:historicVariableInstances
                 ) {
//                System.out.println(historicVariableInstances.toString());
                System.out.println("ID "+his.getId());
                System.out.println(" 变量值 "+his.getValue());
                System.out.println(" 变量名 "+his.getVariableName());
                System.out.println(" 变量类型 "+his.getVariableTypeName());
                System.out.println("#####################################");

            }
        }

    }
}
