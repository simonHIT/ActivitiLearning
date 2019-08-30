package com.simon.sys.utils;

import com.simon.sys.domain.User;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Simon
 * @date 2019/8/30
 * @time 14:06
 * @description session 工具类
 */
public class SessionUtils {

    /**
     * 获取httpServletRequest 对象
     */
    public static HttpServletRequest getCurrentServletRequest(){
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    /**
     * 获取session对象
     */

    public static HttpSession getCurrentSession(){
        return getCurrentServletRequest().getSession();
    }

    /**
     * 获取当前的用户
     */

    public static User getCurrentUser(){
        return (User) getCurrentSession().getAttribute("user");
    }
}
