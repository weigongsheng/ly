package com.xingyou5.model.security.entity;
import java.io.Serializable;

import com.xingyou5.model.security.entity.tbl.UserTbl;


public class User extends UserTbl implements Serializable{
	public static final int STATUS_UNABLE=0;//0：停用
	public static final int STATUS_ABLE=1;//1：激活
	public static final int MARK_QQ =1;//1：QQ登录
	public static final int[] STATUS=new int[]{STATUS_UNABLE,STATUS_ABLE}; 
	
	
	//----管理平台    START----//
	public static final int STATUS_FREEZE=2;//2：冻结
	public static final int[] MPSTATUS=new int[]{STATUS_UNABLE,STATUS_ABLE,STATUS_FREEZE}; 
	//----管理平台    END------//
	
	
	
}
