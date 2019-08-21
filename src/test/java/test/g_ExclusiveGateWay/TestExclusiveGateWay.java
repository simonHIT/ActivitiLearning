package test.g_ExclusiveGateWay;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;

/**
 * @program: activiti
 * @description: 排他网关测试
 * @author: simon
 * @create: 2019-08-21 09:26
 **/
public class TestExclusiveGateWay {

    private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();

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

}
