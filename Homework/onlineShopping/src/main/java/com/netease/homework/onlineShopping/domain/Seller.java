package com.netease.homework.onlineShopping.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Seller extends User{

    @OneToMany(fetch = FetchType.LAZY,mappedBy="seller")    
    private List<Product> products;  
	
	@Override
	public int getUsertype() {
		return 0;
	}
	
	

}