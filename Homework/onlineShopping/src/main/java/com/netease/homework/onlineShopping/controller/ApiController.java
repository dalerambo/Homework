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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.netease.homework.onlineShopping.domain.Buyer;
import com.netease.homework.onlineShopping.domain.CartItem;
import com.netease.homework.onlineShopping.domain.Product;
import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.repository.BuyerRepository;
import com.netease.homework.onlineShopping.repository.CartItemRepository;
import com.netease.homework.onlineShopping.repository.ProductRepository;
import com.netease.homework.onlineShopping.repository.SellerRepository;
import com.netease.homework.onlineShopping.service.ApiService;

@Controller
@RequestMapping("/api")
public class ApiController {
    
	@Autowired
	private ApiService apiService;
	
    @Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@RequestMapping(value = "/login")
	@ResponseBody
    public Object login(ModelAndView modelAndView, HttpSession session, @RequestParam String userName, @RequestParam String password)
    {
		Map<String,Object> result=new HashMap<>();
		User user=apiService.getUser(userName, password);
		
		if(user!=null)
		{
			result.put("result", true);
			session.setAttribute("userId", user.getId());
		}
		else
		{
			result.put("result", false);
		}

		result.put("code", 200);
        return result;
    }
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(ModelAndView modelAndView, HttpSession session, @RequestParam Long id)
    {
		Map<String,Object> result=new HashMap<>();
		
		Long userId=(Long)session.getAttribute("userId");
		if(userId==null)
		{
			result.put("message", "请登录！");
        	result.put("code", 417);
		}
		else
		{
	        User user=apiService.getUser(userId);
	        
	        if(user ==null || user.getUsertype()!=0)
	        {
	        	result.put("message", "非卖家用户无权限删除商品！");
	        	result.put("code", 417);
	        }
	        else
	        {
	        	Product product=productRepository.findById(id);
	        	if(product==null)
	        	{
	        		result.put("message", "商品不存在！");
	            	result.put("code", 417);
	        	}
	        	else if(apiService.isSell(product))
	        	{
	        		result.put("message", "已出售的商品不能删除！");
	            	result.put("code", 417);
	        	}
	        	else
	        	{
//	        		result.put("message", "测试！");
//	            	result.put("code", 417);
	            	
	        		try
	        		{
	        			productRepository.delete(id);
		        		result.put("result", true);
		            	result.put("code", 200);
	        		}
	        		catch(Exception e)
	        		{
	        			result.put("message", e.getMessage());
		            	result.put("code", 417);
	        		}
	        	}
	        }
		}
		
        return result;
    }
	
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object buy(ModelAndView modelAndView, HttpSession session, @RequestParam Long id, @RequestParam Integer num)
    {
		Map<String,Object> result=new HashMap<>();
		
		
		Long userId=(Long)session.getAttribute("userId");
		if(userId==null)
		{
			result.put("message", "请登录！");
        	result.put("code", 417);
		}
		else
		{
	        User user=apiService.getUser(userId);
	        
	        if(user ==null || user.getUsertype()!=1)
	        {
	        	result.put("message", "非买家用户无法购买！");
	        	result.put("code", 417);
	        }
	        else
	        {
	        	Buyer buyer=(Buyer) user;
	        	Product product=productRepository.findById(id);
	        	if(product==null)
	        	{
	        		result.put("message", "商品不存在！");
	            	result.put("code", 417);
	        	}
	        	else if(apiService.isBuy(buyer, product))
	        	{
	        		result.put("message", "已出售的商品不能重复购买！");
	            	result.put("code", 417);
	        	}
	        	else if(num<=0)
	        	{
	        		result.put("message", "购买数量必须大于0！");
	            	result.put("code", 417);
	        	}
	        	else
	        	{
	        		try
	        		{
	        			CartItem  cartItem= cartItemRepository.findByProductAndCarter(product, buyer);
	        			if(cartItem==null)
	        			{
	        				cartItem=new CartItem(buyer,product,num);
	        			}
	        			else
	        			{
	        				cartItem.setNumber(cartItem.getNumber()+num);
	        			}
	        			cartItemRepository.save(cartItem);
		        		result.put("result", true);
		            	result.put("code", 200);
	        		}
	        		catch(Exception e)
	        		{
	        			result.put("message", e.getMessage());
		            	result.put("code", 417);
	        		}
	        	}
	        }
		}
    	
    	return result;
    	
    }

}
