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

import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.service.ApiService;

@Controller
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private ApiService apiService;
	
	@RequestMapping(value = "/login")
	@ResponseBody
    public Object login(ModelAndView modelAndView, HttpSession session, @RequestParam String userName, @RequestParam String password)
    {
		Map<String,Object> result=new HashMap<>();
		User user=apiService.findUser(userName, password);
		
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

}
