package com.xingyou5.module.order.service;

import java.util.List;

import com.xingyou5.module.order.entity.Order;
import com.xingyou5.module.product.vo.Product;
import com.xingyou5.module.user.entity.User;


public interface OrderService {
	
	Order createOrder(List<Product> products,User user);
	
	Order settleAccount(List<Product> products,User user);

	List<Product> queryOrdersByIds(String[] split);
	
}
