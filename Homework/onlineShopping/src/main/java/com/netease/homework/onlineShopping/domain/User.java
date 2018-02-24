package com.netease.homework.onlineShopping.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class User {
	
    @Id
    @GeneratedValue
    private Long id;
	private String username;
	private String password;

	public Long getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	//用户类型，0表示卖家，1表示买家
	public abstract int getUsertype();



}
