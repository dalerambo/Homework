package com.netease.homework.onlineShopping.domain;

//用于展示product的包装类
public class ProductView {
	//商品
    private Product product;
    
    //是否已经购买了
    private Boolean isBuy;
    
    //是否已经出售了
    private Boolean isSell;
    
    //购买时的价格
    private Double buyPrice;
    
    public ProductView(Product product)
    {
    	this.product=product;
    }
    
    public ProductView(Product product,Boolean isBuy,Boolean isSell)
    {
    	this.product=product;
    	this.isBuy=isBuy;
    	this.isSell=isSell;
    }
    
    public ProductView(Product product,Boolean isBuy,Boolean isSell, Double buyPrice)
    {
    	this.product=product;
    	this.isBuy=isBuy;
    	this.isSell=isSell;
    	this.setBuyPrice(buyPrice);
    }

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Boolean getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(Boolean isBuy) {
		this.isBuy = isBuy;
	}

	public Boolean getIsSell() {
		return isSell;
	}

	public void setIsSell(Boolean isSell) {
		this.isSell = isSell;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}
}
