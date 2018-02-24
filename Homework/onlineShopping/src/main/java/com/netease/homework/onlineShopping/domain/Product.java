package com.netease.homework.onlineShopping.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

//商品类
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    
    //商品名称
    private String title;
    
    //商品展示图
    private String image;
    
    //商品发布的卖家
    @ManyToOne(fetch = FetchType.LAZY)
    private Seller seller;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Long getId() {
		return id;
	}  

}
