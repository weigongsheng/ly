package com.xingyou5.model.user.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.xingyou5.model.user.entity.tbl.UresourceTbl;


public class Uresource extends UresourceTbl implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int STATUS_UNABLE=0;//0：停用
	public static final int STATUS_ABLE=1;//1：激活
	public static final int[] STATUS=new int[]{STATUS_UNABLE,STATUS_ABLE}; 
	
	public static final int ISMENU_YES=1;//1：是菜单
	public static final int ISMENU_NO=0;//0：不是菜单
	public static final int[] ISMENU=new int[]{ISMENU_YES,ISMENU_NO}; 
	public static final Integer MENU_FLAG_CAN = 1;//可以访问 
	public static final Integer MENU_FLAG_CANNOT = 2;//不可以访问
	
    private Integer flag; //1:可以访问 ，2：不可以访问
	protected List<Uresource> childMenuModel;
	private Set<Integer> roles;

    private Integer uroleId;
	
	public Integer getUroleId() {
		return uroleId;
	}
	public void setUroleId(Integer uroleId) {
		this.uroleId = uroleId;
	}
	
	public List<Uresource> getChildMenuModel() {
		return childMenuModel;
	}

	public void setChildMenuModel(List<Uresource> childMenuDetail) {
		this.childMenuModel = childMenuDetail;
	}

	public Set<Integer> getRoles() {
		return roles;
	}

	public void setRoles(Set<Integer> roles) {
		this.roles = roles;
	}
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
