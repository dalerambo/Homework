package com.netease.homework.onlineShopping.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

@Component
@ServletComponentScan
@WebFilter(filterName = "MainFilter", urlPatterns = {"/","/userInfo"})
public class MainFilter implements Filter  {

	@Override
	public void destroy() {
		
	}

	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("TestFilter1");
        filterChain.doFilter(servletRequest,servletResponse);
    }


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
