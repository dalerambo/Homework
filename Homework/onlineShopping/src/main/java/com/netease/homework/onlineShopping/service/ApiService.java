package com.netease.homework.onlineShopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.repository.BuyerRepository;
import com.netease.homework.onlineShopping.repository.SellerRepository;

@Service
public class ApiService {

	@Autowired
	private SellerRepository sellerRepository;
	
    @Autowired
	private BuyerRepository buyerRepository;
    
    
    public User findUser(String username,String password)
    {
    	User user=sellerRepository.findByUsernameAndPassword(username,password);
    	if(user==null)
    		user=buyerRepository.findByUsernameAndPassword(username,password);
    	return user;
    }
    
}
