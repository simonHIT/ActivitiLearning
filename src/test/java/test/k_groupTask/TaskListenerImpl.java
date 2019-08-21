package java.test.k_groupTask;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @program: activiti
 * @description: 监听器设置组成员
 * @author: simon
 * @create: 2019-08-21 11:46
 **/
public class TaskListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("进来了");
        delegateTask.addCandidateUser("小A");
        delegateTask.addCandidateUser("小B");
        delegateTask.addCandidateUser("小C");
        delegateTask.addCandidateUser("小D");
    }
}
