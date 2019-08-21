package test.e_history;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @program: activiti
 * @description: 历史记录的查询
 * @author: simon
 * @create: 2019-08-20 16:33
 **/
public class TestHistroyQuery {

    ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();

    /**
     * 查询历史流程实例
     */
    @Test
    public void queryHistoryProcessInstance(){

        HistoryService historyService = processEngine.getHistoryService();

        List<HistoricProcessInstance> historicProcessInstances =
                historyService.createHistoricProcessInstanceQuery().list();
        if (historicProcessInstances!=null&&historicProcessInstances.size()>0){
            for (HistoricProcessInstance his:historicProcessInstances
                 ) {
                System.out.println("历史流程实例 ID"+his.getId());
                System.out.println("历史流程实例 名字"+his.getName());
                System.out.println("历史流程实例 流程定义ID"+his.getProcessDefinitionId());
            }
        }

    }

    /**
     * 查询历史活动
     */
    @Test
    public void queryHistoryActivity(){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> historicActivityInstances =
                historyService.createHistoricActivityInstanceQuery()
                //条件
//        .activityId()
//        .activityInstanceId()
//        .activityTenantId()
//        .activityName()
                //排序
//        .orderByActivityId()
//        .orderByActivityName()
//        .orderByActivityType()

                //结果集
//        .list()
//        .listPage()
                .list();
        if (historicActivityInstances!=null&&historicActivityInstances.size()>0){
            for (HistoricActivityInstance his:historicActivityInstances
                 ) {
                System.out.println("历史活动 ID "+his.getId());
                System.out.println("历史活动 活动ID "+his.getActivityId());
                System.out.println("历史活动 执行实力ID "+his.getExecutionId());
                System.out.println("历史活动 流程定义ID "+his.getProcessDefinitionId());
                System.out.println("历史活动 流程实例ID "+his.getProcessInstanceId());
                System.out.println("历史活动 任务ID "+his.getTaskId());
                System.out.println("#######################################");
            }
        }

    }

    /**
     * 查询历史任务
     */
    @Test
    public void queryHistoryTask(){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                //条件
//        .taskParentTaskId()
//        .deploymentId()
//        .processDefinitionId()
//        .processDefinitionKey()
//        .processDefinitionKeyLike()

                //排序
//        .orderByExecutionId()

                //结果集
//        .list()
//        .listPage()
//        .singleResult()
                .list();

        if (historicTaskInstances!=null&&historicTaskInstances.size()>0){
            for (HistoricTaskInstance his:historicTaskInstances
                 ) {
                System.out.println("历史任务 ID "+his.getId());
                System.out.println("历史任务 ID "+his.getAssignee());
                System.out.println("历史任务 ID "+his.getName());
                System.out.println("历史任务 ID "+his.getExecutionId());
                System.out.println("历史任务 ID "+his.getStartTime());
                System.out.println("历史任务 ID "+his.getEndTime());

            }
        }
    }
}
