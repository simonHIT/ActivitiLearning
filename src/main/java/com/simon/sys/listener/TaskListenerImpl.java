package com.simon.sys.listener;

import com.simon.sys.domain.User;
import com.simon.sys.service.UserService;
import com.simon.sys.utils.SessionUtils;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class TaskListenerImpl implements TaskListener {



    @Override
    public void notify(DelegateTask delegateTask) {
        //得到当前用户
        User currentUser = SessionUtils.getCurrentUser();
        Integer mgr = currentUser.getMgr();

        //获取servletContext对象
        HttpServletRequest currentServletRequest = SessionUtils.getCurrentServletRequest();
        ServletContext servletContext = currentServletRequest.getServletContext();
        //获取spring上下文对象
        WebApplicationContext webApplicationContext =
                WebApplicationContextUtils.getWebApplicationContext(servletContext);

        //获取userService
        UserService userService = webApplicationContext.getBean(UserService.class);
        //查询领导信息
        User userLeader = userService.queryUserById(mgr);

        //设置代理人
        delegateTask.setAssignee(userLeader.getName());
    }
}
