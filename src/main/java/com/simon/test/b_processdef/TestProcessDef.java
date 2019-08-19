package com.simon.test.b_processdef;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;


/**
 * 管理流程定义
 */

public class TestProcessDef {

    //获得流程引擎
    private ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();


    /**
     *部署流程
     * 使用classpath
     */
    @Test
    public void deployProcess01(){

        RepositoryService repositoryService=processEngine.getRepositoryService();
        Deployment deployment=repositoryService.createDeployment().name("请假流程002")
                .addClasspathResource("HelloWorld.bpmn")
                .addClasspathResource("HelloWorld.png")
                .deploy();
        System.out.println("部署成功 流程部署ID："+deployment.getId());

    }

    /**
     * 部署流程
     * 使用zipinputstream
     * 流程图必须是.zip结尾
     */
    @Test
    public void deployProcess02(){
        InputStream inputStream = this.getClass().getResourceAsStream("/HelloWorld.zip");
        System.out.println(inputStream);
        ZipInputStream zipInputStream= new ZipInputStream(inputStream);
        RepositoryService repositoryService=processEngine.getRepositoryService();
        Deployment deployment=repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println("部署成功 部署流程ID ："+deployment.getId());
    }

    /**
     * 查询流程部署信息
     */
    @Test
    public void queryProcessDeploy(){
        RepositoryService repositoryService=processEngine.getRepositoryService();

        //创建流程部署信息的查询
        List<Deployment> deployment=repositoryService.createDeploymentQuery()
        //条件
//        .deploymentId()
//        .deploymentCategory()
//        .deploymentCategoryNotEquals()
//        .deploymentName()
//        .deploymentNameLike()
//        .deploymentTenantId()
        //排序
//        .orderByDeploymentId()
//        .orderByDeploymenTime()
//        .orderByDeploymentName()
//        .orderByTenantId().desc()
        .orderByTenantId().asc()
        //结果集
        .list();
//        .listPage();
//        .singleResult();/*返回单个对象*/
//        .count();
        System.out.println(deployment.toString());

    }

    /**
     * 查询书流程定义信息
     */
    @Test
    public void queryProcessDef(){
        RepositoryService repositoryService=processEngine.getRepositoryService();
        ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery()
                //查询条件
//                .processDefinitionId()
//        .processDefinitionIds()
//        .processDefinitionKey()
//        .processDefinitionKeyLike()
//        .processDefinitionName()
//        .processDefinitionResourceName()
//        .processDefinitionResourceNameLike()
//        .processDefinitionTenantId()
//        .processDefinitionVersion()

        //排序
//        .orderByDeploymentId()
//        .orderByProcessDefinitionCategory()
//        .orderByProcessDefinitionId()
//        .orderByProcessDefinitionKey()
//        .orderByProcessDefinitionName()
//        .orderByProcessDefinitionVersion()
//        .orderByTenantId().desc()
        .orderByTenantId().asc()
        //结果集
//        .list();
//        .listPage();
//        .latestVersion();
//        .singleResult();
        .latestVersion().singleResult();

        System.out.println(
                processDefinition.toString()
        );
    }

    /**
     * 删除流程定义
     */
    @Test
    public void deleteProcessDef(){
        RepositoryService repositoryService=processEngine.getRepositoryService();

        //根据流程部署id删除，如果正在运行，报错
//        repositoryService.deleteDeployment();
        //根据流程部署id删除，如果正在运行，true同样会将正在运行的删除,false 不删除
        repositoryService.deleteDeployment("2501",true);

        //根据流程部署id删除，如果正在运行，同样会将正在运行的删除
//        repositoryService.deleteDeploymentCascade();
    }



}
