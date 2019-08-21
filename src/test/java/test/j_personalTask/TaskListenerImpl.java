package test.j_personalTask;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @program: activiti
 * @description: 监听器设置代理人
 * @author: simon
 * @create: 2019-08-21 11:46
 **/
public class TaskListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("进来了");
        String assigne="李四";

        //使用解耦的方式 从HttpServletRequest 中获取session
        //再从session中获取当前用户
        //再从用户中获取上级领导，拿到名字
        delegateTask.setAssignee(assigne);
    }
}
