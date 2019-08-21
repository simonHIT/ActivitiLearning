package java.test.b_processdef;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * 查看流程图 根据流程定义ID
     */
    @Test
    public void viewProcessImg(){
        RepositoryService repositoryService=processEngine.getRepositoryService();

        //根据流程定义id查询
        String processDefinitionId="HelloWorld:1:4";
        InputStream processDiagram = repositoryService.getProcessDiagram(processDefinitionId);
        File file=new File("d:/Download/HelloWorld.png");

        try {
            BufferedOutputStream bufferedOutputStream =new BufferedOutputStream(new FileOutputStream(file));

            int len=0;
            byte[] b=new byte[1024];
            while ((len=processDiagram.read(b))!=-1){
                bufferedOutputStream.write(b,0,len);
                bufferedOutputStream.flush();
            }
            bufferedOutputStream.close();
            processDiagram.close();

            System.out.println("查询成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询流程图 根据流程部署Id查询
     */

    @Test
    public void viewProcessdef(){
        RepositoryService repositoryService=processEngine.getRepositoryService();

        String processDeploymentId="1";

        ProcessDefinition processDefinition=
                repositoryService.createProcessDefinitionQuery().deploymentId(processDeploymentId)
                .singleResult();
        String definitionId=processDefinition.getId();

        InputStream processDiagram =repositoryService.getProcessDiagram(definitionId);

        File file=new File("d:/Download/"+processDefinition.getName()+".png");

        try {
            BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(file));

            int len=0;
            byte[] bytes=new byte[1024];
            while ((len=processDiagram.read(bytes))!=-1){
                bufferedOutputStream.write(bytes,0,len);
                bufferedOutputStream.flush();
            }

            bufferedOutputStream.close();
            processDiagram.close();

            System.out.println("查询成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询最新版本的流程定义
     */

    @Test
    public void viewLastVersionDef(){
        Map<String,ProcessDefinition> map=new HashMap<>();

        //查询所有的流程定义根据版本号升序
        RepositoryService repositoryService=processEngine.getRepositoryService();

        List<ProcessDefinition> processDefinitions =
                repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();

        for (ProcessDefinition processDef:processDefinitions
             ) {
            map.put(processDef.getKey(),processDef);
        }

        Collection<ProcessDefinition> values=map.values();

        for (ProcessDefinition pdef:values
             ) {
            System.out.println("流程定义ID："+pdef.getId());
            System.out.println("流程定义名称："+pdef.getName());
            System.out.println("流程定义部署ID："+pdef.getDeploymentId());
            System.out.println("流程定义版本："+pdef.getVersion());
            System.out.println("流程定义bpmn文件名称："+pdef.getResourceName());
            System.out.println("流程定义png文件名称："+pdef.getDiagramResourceName());
        }
    }

    /**
     * 删除key相同，但版本不同的流程定义
     */

    @Test
    public void deleteProcessDefByKey(){
        String pdefKey="HelloWorld";
        RepositoryService repositoryService=processEngine.getRepositoryService();

        //根据key查询流程集合
        List<ProcessDefinition> processDefinitions =
                repositoryService.createProcessDefinitionQuery().processDefinitionKey(pdefKey).list();

        for (ProcessDefinition prodef:processDefinitions
             ) {
            String deploymentId=prodef.getDeploymentId();
            repositoryService.deleteDeployment(deploymentId,true);
        }

    }



}
