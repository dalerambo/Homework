package com.netease.homework.onlineShopping.configuration;

import com.netease.homework.onlineShopping.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new LoginCheckInterceptor());

        interceptorRegistration.addPathPatterns("/*");
        interceptorRegistration.excludePathPatterns("/");
        interceptorRegistration.excludePathPatterns("/login");
        interceptorRegistration.excludePathPatterns("/logout");
        interceptorRegistration.excludePathPatterns("/show");

        super.addInterceptors(registry);
    }
}
