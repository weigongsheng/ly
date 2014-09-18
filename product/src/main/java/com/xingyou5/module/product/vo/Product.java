package com.xingyou5.module.product.vo;

import com.xingyou5.module.product.entity.ProductKind;

public class Product {
	private Integer id;
	private Integer kindId;
	private String imgUrl;
	private String description;
	private String price;
	private String orignalPrice;
	private String discount;
	private Integer buyers;
	private ProductKind productKind;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOrignalPrice() {
		return orignalPrice;
	}
	public void setOrignalPrice(String orignalPrice) {
		this.orignalPrice = orignalPrice;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public Integer getBuyers() {
		return buyers;
	}
	public void setBuyers(Integer buyers) {
		this.buyers = buyers;
	}
	public Integer getKindId() {
		return kindId;
	}
	public void setKindId(Integer kindId) {
		this.kindId = kindId;
	}
	public ProductKind getProductKind() {
		return productKind;
	}
	public void setProductKind(ProductKind productKind) {
		this.productKind = productKind;
	}
	
}
