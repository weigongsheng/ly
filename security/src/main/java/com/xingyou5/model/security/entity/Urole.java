package com.xingyou5.model.security.entity;

import java.io.Serializable;

import com.xingyou5.model.security.entity.tbl.UroleTbl;


public class Urole extends UroleTbl implements Serializable{
	public static final int STATUS_UNABLE=0;//0：停用
	public static final int STATUS_ABLE=1;//1：激活
	public static final int[] STATUS=new int[]{STATUS_UNABLE,STATUS_ABLE}; 
	
	//----管理平台    START----//
	public static final Integer ADMIN_YTOXL = 1;//新龙管理员
	public static final Integer ADMIN_CUSTOMER=2;//客户管理员
	public static final Integer ADMIN_SUPPLIER=3;//仓配网点管理员
	//----管理平台    END------//
}
