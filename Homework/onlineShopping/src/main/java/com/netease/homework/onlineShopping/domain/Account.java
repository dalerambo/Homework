package com.netease.homework.onlineShopping.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

//账务类
@Entity
public class Account {
	@Id
	@GeneratedValue
	private Long id;

	// 账务所属买家
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
	private Buyer buyer;

	// 账务中包含的项目
	@OneToMany(fetch = FetchType.LAZY)
	private List<Item> items;

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Long getId() {
		return id;
	}
}