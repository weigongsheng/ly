package com.xingyou5.module.order.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xingyou5.module.order.entity.Order;
import com.xingyou5.module.order.service.OrderService;
import com.xingyou5.module.product.vo.Product;
import com.xingyou5.module.user.entity.User;

@Service
public class OrderServiceImpl implements OrderService{

	@Override
	public Order createOrder(List<Product> products, User user) {
		 
		return null;
	}

	@Override
	public Order settleAccount(List<Product> products, User user) {
		 
		return null;
	}

	@Override
	public List<Product> queryOrdersByIds(String[] split) {
		// TODO Auto-generated method stub
		return null;
	}

}
