package com.netease.homework.onlineShopping.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netease.homework.onlineShopping.domain.Product;
import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.repository.BuyerRepository;
import com.netease.homework.onlineShopping.repository.SellerRepository;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private SellerRepository sellerRepository;
	
    @Autowired
	private BuyerRepository buyerRepository;
    
	@RequestMapping(value = "/")
    public ModelAndView index(ModelAndView modelAndView, HttpSession session, @RequestParam(required=false) Integer type)
    {
    	
        modelAndView.setViewName("index");
        
        List<String> productList=null;
        modelAndView.addObject("productList", productList);
        
        Long id=(Long)session.getAttribute("userId");
        User user=null;
        if(id!=null)
        {
        	user=sellerRepository.findById(id);
        	if(user==null)
        		user=buyerRepository.findById(id);
        }
        modelAndView.addObject("user", user);
        
        
        modelAndView.addObject("listType", type==null?0:1);//0表示所有内容，1表示未购买的内容
        return modelAndView;
    }
	
	@RequestMapping(value = "/login")
    public ModelAndView login(ModelAndView modelAndView)
    {
        modelAndView.setViewName("login");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session)
    {
		session.removeAttribute("userId");
        modelAndView.setViewName("login");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/public")
    public ModelAndView publicItem(ModelAndView modelAndView)
    {
        modelAndView.setViewName("public");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/publicSubmit")
    public ModelAndView publicSubmit(ModelAndView modelAndView,Product product)
    {
        modelAndView.setViewName("publicSubmit");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/account")
    public ModelAndView account(ModelAndView modelAndView)
    {
        modelAndView.setViewName("account");
        
        return modelAndView;
    }
	
	@RequestMapping(value = "/cart")
    public ModelAndView cart(ModelAndView modelAndView)
    {
        modelAndView.setViewName("cart");
        
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
