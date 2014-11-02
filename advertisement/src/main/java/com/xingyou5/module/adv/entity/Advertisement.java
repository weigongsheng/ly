package com.xingyou5.module.adv.entity;

import java.util.Date;

import com.xingyou5.module.product.vo.Product;

public class Advertisement {
	private Integer id;
	private String name;
	private Integer type;
	private Product product;
	private String picUrl;
	private String targetUrl;
	private String description;
	private String recommendation;
	private Date endDate;
	private Integer productId;
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getId() {
		return id;
	}
	
	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getRecommendation() {
		return recommendation;
	}


	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
