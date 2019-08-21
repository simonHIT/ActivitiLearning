package test.j_personalTask;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

/**
 * @program: activiti
 * @description: 使用监听器指定代理人
 * @author: simon
 * @create: 2019-08-21 11:43
 **/
public class TestPersonalTaskByListener {

    private ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();

    /**
     * 当任务到达相应节点的时候会触发监听器实例来设置代理人
     */
}
