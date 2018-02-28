package com.netease.homework.onlineShopping.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class AccountItem extends Item{

    //买家
    @ManyToOne(fetch = FetchType.LAZY)
    private Buyer buyer;
    
    //商品项目购买时的价格
    private Double price;
    
	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public int getItemtype()
	{
		return 1;
	}
}
