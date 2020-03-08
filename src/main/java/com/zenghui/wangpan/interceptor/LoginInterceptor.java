package com.zenghui.wangpan.interceptor;

import com.zenghui.wangpan.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptor
 * @Description: 登录拦截器,必须要登录才能访问指定页面
 * @Author zeng
 * @Date 2020/3/8
 **/
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 该方法将在Controller处理之前进行调用
     * 拦截器要在mvc中注册
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从session中获取登录用户
        Object loginUser = request.getSession().getAttribute("loginUser");

        if (loginUser == null){
            //未登录,返回登录页面,这个要加项目路径,我要不知道为什么
            response.sendRedirect("/cloud/user");
            return false;
        }else {
            //已经登录,放行
            return true;
        }
    }
}
