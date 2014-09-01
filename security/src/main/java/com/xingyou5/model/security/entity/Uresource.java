package com.xingyou5.model.security.entity;

import java.io.Serializable;

import com.xingyou5.model.security.entity.tbl.UresourceTbl;


public class Uresource extends UresourceTbl implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int STATUS_UNABLE=0;//0：停用
	public static final int STATUS_ABLE=1;//1：激活
	public static final int[] STATUS=new int[]{STATUS_UNABLE,STATUS_ABLE}; 
	
	public static final int ISMENU_YES=1;//1：是菜单
	public static final int ISMENU_NO=0;//0：不是菜单
	public static final int[] ISMENU=new int[]{ISMENU_YES,ISMENU_NO}; 
}
