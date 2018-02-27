package com.netease.homework.onlineShopping.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//商品类
@Entity
public class Product {
	@Id
	@GeneratedValue
	private Long id;

	// 商品名称
	private String title;

	// 商品展示图
	private String image;

	// 商品摘要
	private String summary;

	// 商品正文
	private String detail;

	// 商品当前价格
	private Double price;

	// 商品发布的卖家
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

	public void setId(Long id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
