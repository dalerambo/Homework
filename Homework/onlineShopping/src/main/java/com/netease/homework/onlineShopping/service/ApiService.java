package com.netease.homework.onlineShopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netease.homework.onlineShopping.domain.Buyer;
import com.netease.homework.onlineShopping.domain.Product;
import com.netease.homework.onlineShopping.domain.Seller;
import com.netease.homework.onlineShopping.domain.User;
import com.netease.homework.onlineShopping.repository.AccountItemRepository;
import com.netease.homework.onlineShopping.repository.BuyerRepository;
import com.netease.homework.onlineShopping.repository.SellerRepository;

@Service
public class ApiService {

	@Autowired
	private SellerRepository sellerRepository;
	
    @Autowired
	private BuyerRepository buyerRepository;
    
    @Autowired
	private AccountItemRepository accountItemRepository;
    
    //根据用户名密码查找用户
    public User getUser(String username,String password)
    {
    	User user=sellerRepository.findByUsernameAndPassword(username,password);
    	if(user==null)
    		user=buyerRepository.findByUsernameAndPassword(username,password);
    	return user;
    }
    
    //根据id查找指定用户
	public User getUser(Long userId)
	{
		User user=null;
        if(userId!=null)
        {
        	user=sellerRepository.findById(userId);
        	if(user==null)
        		user=buyerRepository.findById(userId);
        }
        return user;
	}
    
    //货物是否已经被指定买家购买
    public boolean isBuy(Buyer buyer,Product product)
    {
    	return !accountItemRepository.findByProductAndBuyer(product, buyer).isEmpty();
    }
    
    //货物是否已经被卖出
    public boolean isSell(Product product)
    {
    	return !accountItemRepository.findByProduct(product).isEmpty();
    }
    

    
}
