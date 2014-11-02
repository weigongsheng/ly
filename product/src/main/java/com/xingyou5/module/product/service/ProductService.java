package com.xingyou5.module.product.service;

import java.util.List;

import com.xingyou5.module.product.entity.ProductKind;
import com.xingyou5.module.product.vo.Product;

public interface ProductService {
	List<ProductKind> listType(Integer type);
	
	List<Product> queryProductByType(Integer type);

	Product queryById(Integer pId);
}
