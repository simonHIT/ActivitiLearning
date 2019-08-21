package test.i_receiveTask;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;

/**
 * @program: activiti
 * @description: 接收任务测试
 * @author: simon
 * @create: 2019-08-21 10:37
 **/
public class TestReceiveTask {

    private ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程
     */
    @Test
    public void deploymentProcess(){

        RepositoryService repositoryService=processEngine.getRepositoryService();
        Deployment deployment=repositoryService.createDeployment().name("报销流程")
                .addClasspathResource("ReceiveTask.bpmn")
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
        String processDefinitionKey="myProcess";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程启动成功：流程id "+processInstance.getId()+" 流程名字 "+processInstance.getName());

    }

    /**
     * 执行任务
     */
    @Test
    public void executionTask(){

        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        String processInstanceId="2501";
        /**
         * 查询执行对象id
         */
        Execution execution = processEngine.getRuntimeService().createExecutionQuery()
                .processInstanceId(processInstanceId)
                .activityId("receivetask1")
                .singleResult();

        System.out.println("执行实例ID "+execution.getId());
        System.out.println("流程实例ID "+execution.getProcessInstanceId());
        System.out.println("活动ID "+execution.getActivityId());


        /**
         * 设置流程变量
         */
        int value=10000;//通过查询数据库得到
        runtimeService.setVariable(execution.getId(),"当前的销售额",value);

        /**
         * 向后执行一步，如果流程处于等待的状态，使得流程继续执行
         */
        runtimeService.signal(execution.getId());
    }

    /**
     * 继续向下执行一步 发短信
     */
    @Test
    public void sendMessage(){

        RuntimeService runtimeService = processEngine.getRuntimeService();

        String execeutionId="2501";

        /**
         * 从流程变量中获取汇总当日销售额的值
         */
        Integer variable = (Integer) runtimeService.getVariable(execeutionId, "当前的销售额");
        System.out.println(variable);

        System.out.println("发送短信");
        Boolean flag=false;
        int num=0;
        do {
            flag=send();
            num++;
            if (num==10){
                //错误日志
                System.out.println("尝试10次均失败，放弃发送");
                break;
            }
        }while (!flag);
        /**
         * 向下执行一步
         */
        runtimeService.signal(execeutionId);
        System.out.println("流程执行结束");
    }
    private Boolean send(){
        System.out.println("发送成功");
        return true;
    }

    /**
     * 实际写法
     */
    @Test
    public void doTask(){

    }
}
