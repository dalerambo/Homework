package com.netease.homework.onlineShopping.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class Buyer extends User{

	@OneToOne(fetch = FetchType.LAZY)
	Cart cart;
	
	@OneToOne(fetch = FetchType.LAZY)
	Account account;
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public int getUsertype() {
		return 1;
	}

}
