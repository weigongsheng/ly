package com.xingyou5.module.product.vo;

 
import java.math.BigDecimal;
import java.util.Date;

import com.xingyou5.module.product.entity.ProductKind;

 

public class Product {
	private Integer id;
	private Integer kindId;
	private String imgUrl;
	private String description;
	private BigDecimal price;
	private BigDecimal orignalPrice;
	private String discount;
	private Integer buyers;
	private ProductKind productKind;
	
	private String name;
	private String title;
	private String recommendation;
	private String listingPrice;
	private String notice;
	private String attentions;
	private String details;
	private Date endDate;
	
	private Long leftTime;
	
	
	
	public Long getLeftTime() {
		if(endDate != null){
			return endDate.getTime() - System.currentTimeMillis();
		}
		return leftTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getListingPrice() {
		return listingPrice;
	}
	public void setListingPrice(String listingPrice) {
		this.listingPrice = listingPrice;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getAttentions() {
		return attentions;
	}
	public void setAttentions(String attentions) {
		this.attentions = attentions;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getOrignalPrice() {
		return orignalPrice;
	}
	public void setOrignalPrice(BigDecimal orignalPrice) {
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
