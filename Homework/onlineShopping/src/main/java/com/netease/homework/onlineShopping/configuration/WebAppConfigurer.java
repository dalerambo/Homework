package com.netease.homework.onlineShopping.configuration;

import com.netease.homework.onlineShopping.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    //关键，将拦截器作为bean写入配置中
    @Bean
    public LoginCheckInterceptor loginCheckInterceptor(){
        return new LoginCheckInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginCheckInterceptor());

        interceptorRegistration.addPathPatterns("/**");
//        interceptorRegistration.excludePathPatterns("/");
//        interceptorRegistration.excludePathPatterns("/login");
//        interceptorRegistration.excludePathPatterns("/logout");
//        interceptorRegistration.excludePathPatterns("/show");

        super.addInterceptors(registry);
    }
}
