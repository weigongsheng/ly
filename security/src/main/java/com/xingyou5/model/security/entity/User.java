package com.xingyou5.model.security.entity;
import java.io.Serializable;
import java.util.List;

import com.xingyou5.model.security.entity.tbl.UserTbl;


public class User extends UserTbl implements Serializable{
	public static final int STATUS_UNABLE=0;//0：停用
	public static final int STATUS_ABLE=1;//1：激活
	public static final int MARK_QQ =1;//1：QQ登录
	public static final int[] STATUS=new int[]{STATUS_UNABLE,STATUS_ABLE}; 
	
	
	//----管理平台    START----//
	public static final int STATUS_FREEZE=2;//2：冻结
	public static final int[] MPSTATUS=new int[]{STATUS_UNABLE,STATUS_ABLE,STATUS_FREEZE}; 
	public User(User user) {
		// TODO Auto-generated constructor stub
	}
	//----管理平台    END------//
	public void setMenuModel(Uresource menuModel) {
		// TODO Auto-generated method stub
		
	}
	public void setSelectUroles(List<Urole> uroles) {
		// TODO Auto-generated method stub
		
	}
	public void setUnSelectUroles(List<Urole> unSelectUroles) {
		// TODO Auto-generated method stub
		
	}
	public String getRepeatPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
