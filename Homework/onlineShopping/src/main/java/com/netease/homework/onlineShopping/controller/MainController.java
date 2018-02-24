package com.netease.homework.onlineShopping.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netease.homework.onlineShopping.domain.User;

@Controller
@RequestMapping("/")
public class MainController {

	@RequestMapping(value = "/")
    public ModelAndView index(ModelAndView modelAndView,@RequestParam(required=false) Integer type)
    {
    	
        modelAndView.setViewName("index");
        
        List<String> productList=null;
      
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("user", null);
        modelAndView.addObject("listType", type==null?0:1);//0表示所有内容，1表示未购买的内容
        return modelAndView;
    }
	
	@RequestMapping(value = "/login")
    public ModelAndView login(ModelAndView modelAndView)
    {
        modelAndView.setViewName("login");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/test")
    public ModelAndView test(ModelAndView modelAndView)
    {
    	
        modelAndView.setViewName("test");
        
        List<String> userList=new ArrayList<String>();
        userList.add("admin");
        userList.add("user1");
        userList.add("user2");
        
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }
}
