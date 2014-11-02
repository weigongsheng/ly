package com.xinyou5.front.web.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xingyou5.module.adv.service.AdvertisementServer;
import com.xinyou5.front.action.BaseAction;
/** 基本action用于暂时login
 * @author  
 *
 */
/**
 * @author admin
 *
 */
public class IndexAction extends BaseAction{ 
	
	private static final long serialVersionUID = 4197617264075560936L;
	private List advList;
	private List slideAdvs;

	@Autowired
	private AdvertisementServer advServer;
	
	/**首页 登陆成功
 	 * @return
	 */
	public String index(){
		slideAdvs = advServer.querySlideAdv();
		advList = advServer.queryFrontAdv();
		 return SUCCESS;
	}

	public List getAdvList() {
		return advList;
	}
	
	public String regist(){
		return "toRegist";
	}

	public List getSlideAdvs() {
		return slideAdvs;
	}
	
	
	
}
