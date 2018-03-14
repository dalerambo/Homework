package com.netease.homework.onlineShopping.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class CartItem extends Item{

    //加入购物车的用户
    @ManyToOne(fetch = FetchType.LAZY)
//	@ManyToOne(fetch = FetchType.EAGER)
    private Buyer carter;
    
    
    public CartItem()
    {
    	
    }
    
    public CartItem(Buyer carter,Product product, Integer number)
    {
    	this.setCarter(carter);
    	this.setProduct(product);
    	this.setNumber(number);
    }
    
	public Buyer getCarter() {
		return carter;
	}

	public void setCarter(Buyer carter) {
		this.carter = carter;
	}

	@Override
	public int getItemtype()
	{
		return 0;
	}
}
