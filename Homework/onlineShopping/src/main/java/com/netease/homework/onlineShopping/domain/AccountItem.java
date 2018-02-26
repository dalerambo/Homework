package com.netease.homework.onlineShopping.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class AccountItem extends Item{

    //买家
    @ManyToOne(fetch = FetchType.LAZY)
    private Buyer buyer;
    
	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	@Override
	public int getItemtype()
	{
		return 1;
	}
}
