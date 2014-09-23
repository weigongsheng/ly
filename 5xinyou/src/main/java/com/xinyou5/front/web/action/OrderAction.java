package com.xinyou5.front.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xingyou5.module.order.service.OrderService;
import com.xingyou5.module.product.vo.Product;
import com.xinyou5.front.action.BaseAction;

/**
 * @author Administrator
 *
 */
public class OrderAction extends BaseAction {
	private String pids;
	List<Product> product;
	
	@Autowired
	private OrderService orderService;  
	
	/**
	 * 去结算
	 * @return
	 */
	public String settleAccounts(){
//		List<Product> product =orderService.queryOrdersByIds(pids.split(","));
		
		return "settleAccounts";
	}
	/**
	 *  结算
	 * @return
	 */
	public String submit(){
//		List<Product> product =orderService.queryOrdersByIds(pids.split(","));
		return "submit";
	}
	public String getPids() {
		return pids;
	}
	public void setPids(String pids) {
		this.pids = pids;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
}
