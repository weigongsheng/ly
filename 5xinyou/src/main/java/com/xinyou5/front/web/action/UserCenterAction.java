package com.xinyou5.front.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.xingyou5.module.user.UserVo;
import com.xingyou5.module.user.service.SecurityUserService;
import com.xinyou5.front.action.BaseAction;

public class UserCenterAction extends BaseAction {

	@Autowired
	private SecurityUserService seUserService;
	private UserVo user;
	
	
	public String userCenter() throws Exception{
		//user =seUserService.getCurrentUser();
		return SUCCESS;
	}
	
	public String myOrder(){
//		try {
//			Thread.sleep(5*1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return "myOrder";
	}
	
	public String userInfo(){
		
		return "userInfo";
	}
	public String headPic(){
		
		return "headPic";
	}
	
	public String changePwd(){
		
		return "changePwd";
	}
	public String myCoupon(){
		
		return "myCoupon";
	}
 	 
}
