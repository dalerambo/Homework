package com.netease.homework.onlineShopping.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

//商品项目类
@Entity
public abstract class Item {

    @Id
    @GeneratedValue
    private Long id;
    
    //商品项目包含的商品（一个product可能对应多个item，item是购物车或账目中的项目）
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    
    //商品项目购买时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")//存日期时使用  
    private Date time;
    
	//商品项目购买数量
    private Integer number;
    

    public Item()
    {
    	time=new Date();
    }

    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}



	public Long getId() {
		return id;
	}

	//用户类型，0表示购物车项目，1表示已购买的账务项目
	public abstract int getItemtype();
}
