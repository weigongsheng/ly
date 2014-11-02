package com.xingyou5.module.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xingyou5.module.product.dao.ProductDao;
import com.xingyou5.module.product.entity.ProductKind;
import com.xingyou5.module.product.vo.Product;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao productDao;
	
	@Override
	public List<ProductKind> listType(Integer type) {
		List<ProductKind> all = new ArrayList<ProductKind>();
		all.add(buildKind(1,8,"上海"));
		all.add(buildKind(2,5,"苏州"));
		return all;
	}

	private ProductKind buildKind(int i, int j, String string) {
		ProductKind temp = new ProductKind();
		temp.setId(i);
		temp.setCount(j);
		temp.setKindLabel(string);
		return temp;
	}
	
	@Override
	public List<Product> queryProductByType(Integer type){
		List<Product> re = new ArrayList<Product>();
		if(type==1){
			re.addAll(createProduct(1,8,"上海"));
		}else if(type ==2){
			re.addAll(createProduct(2,5,"苏州"));
		}else{
			re.addAll(createProduct(1,8,"上海"));
			re.addAll(createProduct(2,5,"苏州"));
		}
		return re;
	}

	private List<? extends Product> createProduct(int i, int j, String string) {
		List<Product> all = new ArrayList<Product>();
		for (int k = 0; k <j; k++) {
			Product p = new Product();
			p.setKindId(i);
			p.setDescription("【武义 清水湾沁温泉】温泉抢先购，低价吓到你！免费升级房型！泡AAAA级纯天然“...");
			p.setImgUrl("lp_1.png");
			p.setBuyers(10);
			p.setOrignalPrice(new BigDecimal("356"));
			p.setPrice(new BigDecimal("356"));
			p.setDiscount("3.5");
			all.add(p);
		}
		return all;
	}

	@Override
	public Product queryById(Integer pId) {
		return productDao.get(pId);
	}
	 

}
