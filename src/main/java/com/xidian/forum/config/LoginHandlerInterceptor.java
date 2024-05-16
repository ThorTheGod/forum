package com.xidian.forum.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    //登录拦截器
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Integer userId = (Integer) request.getSession().getAttribute("userId");
//        if(userId==null){
//            request.setAttribute("msg","没有权限，请先登录");
//            request.getRequestDispatcher("/login.html").forward(request,response);
//            return false;
//        }else
//            return true;
//    }
}
