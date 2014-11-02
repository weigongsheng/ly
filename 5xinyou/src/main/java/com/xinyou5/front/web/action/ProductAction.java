package com.xinyou5.front.web.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xingyou5.module.product.entity.ProductKind;
import com.xingyou5.module.product.service.ProductService;
import com.xingyou5.module.product.vo.Product;
import com.xinyou5.front.action.BaseAction;

public class ProductAction extends BaseAction {

	private static final long serialVersionUID = 7054914779706212954L;
	@Autowired
	private ProductService productService;
	private Integer t= 0;
	private List<ProductKind> allKind;
	private List<Product> allProduct;
	private Integer proId;
	private Product product;

	public String list() {
		List<ProductKind> temp  = productService.listType(t);
		allKind = new ArrayList<ProductKind>();
		processTotal(allKind,temp);
		allKind.addAll(temp);
		allProduct = productService.queryProductByType(t);
		return "list";
	}
	
	public String detail(){
		product =productService.queryById(proId);
		return "detail";
	}

	private void processTotal(List<ProductKind> allKind2, List<ProductKind> temp) {
		 if(temp == null || temp.isEmpty()){
			 return ;
		 }
		 allKind.add(buildTotal(temp));
	}

	private ProductKind buildTotal(List<ProductKind> temp) {
		ProductKind total = new ProductKind();
		for (ProductKind productKind : temp) {
			total.setCount(total.getCount()+productKind.getCount());
		}
		total.setId(0);
		total.setKindLabel("全国");
		return total;
	}

	public List<ProductKind> getAllKind() {
		return allKind;
	}

	public void setAllKind(List<ProductKind> allKind) {
		this.allKind = allKind;
	}

	public Integer getT() {
		return t;
	}

	public void setT(Integer t) {
		this.t = t;
	}

	public List<Product> getAllProduct() {
		return allProduct;
	}

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer pId) {
		this.proId = pId;
	}

	public Product getProduct() {
		return product;
	}

	

}
