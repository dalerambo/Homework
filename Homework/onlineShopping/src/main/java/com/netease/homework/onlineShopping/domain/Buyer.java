package com.netease.homework.onlineShopping.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Buyer extends User{
	
	//购物车中包含的项目
	@OneToMany(fetch = FetchType.LAZY, mappedBy="carter")
//	@OneToMany(fetch = FetchType.EAGER, mappedBy="carter")
	private List<CartItem> cart;

	
	//账务中包含的项目
	@OneToMany(fetch = FetchType.LAZY, mappedBy="buyer")
//	@OneToMany(fetch = FetchType.EAGER, mappedBy="buyer")
	private List<AccountItem> account;
	
	public List<CartItem> getCart() {
		return cart;
	}

	public void setCart(List<CartItem> cart) {
		this.cart = cart;
	}

	public List<AccountItem> getAccount() {
		return account;
	}

	public void setAccount(List<AccountItem> account) {
		this.account = account;
	}

	
	@Override
	public int getUsertype() {
		return 1;
	}

}
