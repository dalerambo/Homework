package com.netease.homework.onlineShopping.interceptor;


import com.netease.homework.onlineShopping.annotation.AuthorityEnum;
import com.netease.homework.onlineShopping.annotation.Authorization;
import com.netease.homework.onlineShopping.annotation.QueryTypeEnum;
import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.exception.ApiAuthorizationException;
import com.netease.homework.onlineShopping.exception.PageAuthorizationException;
import com.netease.homework.onlineShopping.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    BusinessService businessService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        HttpSession session = httpServletRequest.getSession();
        Long userId=(Long)session.getAttribute("userId");
        User user=businessService.getUser(userId);
//        httpServletRequest.setAttribute("user",user);

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解，包含了该方法类型是Page的还是Api的信息，以及所需登录权限
            Authorization authorization = handlerMethod.getMethod().getAnnotation(Authorization.class);
            if (authorization != null) {
                QueryTypeEnum queryType=authorization.queryType();
                AuthorityEnum authority=authorization.authority();
                if(queryType== QueryTypeEnum.Page)//如果是页面类的请求
                {
                    if(authority==AuthorityEnum.Seller && (user == null || user.getUsertype()!=0))
                    {
                        throw PageAuthorizationException.REQUIRE_SELLER;
                    }
                    else if(authority==AuthorityEnum.Buyer && (user == null || user.getUsertype()!=1))
                    {
                        throw PageAuthorizationException.REQUIRE_BUYER;
                    }
                }
                else if(queryType== QueryTypeEnum.Api)//如果是api类的请求
                {
                    if(authority==AuthorityEnum.Seller && (user == null || user.getUsertype()!=0))
                    {
                        throw ApiAuthorizationException.REQUIRE_SELLER;
                    }
                    else if(authority==AuthorityEnum.Buyer && (user == null || user.getUsertype()!=1))
                    {
                        throw ApiAuthorizationException.REQUIRE_BUYER;
                    }
                }
            }
        }

        return true;

    }
}
