package com.xingyou5.model.security.service;

import java.util.List;

import com.xingyou5.model.security.entity.Uresource;




public interface UresourceService {
	
	/**
	 * 获取拥有指定角色的 菜单   (只包括可访问)
	 * @param menuModel
	 * @param roleId
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
	 * @return Uresource
	 * */
	public Uresource getMenuModel(Uresource menuModel,Integer roleId,boolean onlyMenu);
	
	/**
	 * 获取拥有角色的 菜单   (只包括可访问)
	 * @param menuModel
	 * @param roles
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源 
	 * @return Uresource
	 * */
	public Uresource getMenuModel(Uresource menuModel,List<Integer> roleIds,boolean onlyMenu);

	/**
	 * 获取拥有单个角色的 菜单    (包括可访问和不可访问)
	 * @param menuModel
	 * @param roleId
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
	 * @return Uresource
	 * */
	public Uresource getUresourceMerge(Uresource menuModel, Integer roleId,boolean onlyMenu);

	/**
	 * 获取拥有多个角色的 菜单  (包括可访问和不可访问)
	 * @param menuModel
	 * @param roles
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
	 * @return Uresource
	 * */
	public Uresource getMenuModelMerge(Uresource menuModel, List<Integer> roleIds,boolean onlyMenu);
	
	/**
	 * 获取资源url 角色 roleName
	 * @return  List  
	 * */
	List<Uresource> listRoleNameResUrl();
	
	/**
	 * 获取所有菜单  
	 * @return Uresource
	 * */
	public Uresource getAllMenuModel();
	
//	/**
//	 * 获取菜单，及对应的角色    系统启动时自动加载的  
//	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
//	 * */
//	public Uresource getMenuModelForSystem(boolean onlyMenu);
//	
}
