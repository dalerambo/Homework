package com.netease.homework.onlineShopping.interceptor;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

//        return true;
        HttpSession session = httpServletRequest.getSession();
        Long userId=(Long)session.getAttribute("userId");
        //如果没有登陆则跳转登录界面
        if(userId==null)
        {
            httpServletResponse.sendRedirect("/login");
            return false;
        }
        else
        {
            return true;
        }

    }
}
