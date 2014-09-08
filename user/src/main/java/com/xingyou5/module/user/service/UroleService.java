package com.xingyou5.module.user.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xingyou5.module.user.entity.Uresource;
import com.xingyou5.module.user.entity.Urole;
import com.xingyou5.module.user.exception.XingYou5UserException;


public interface UroleService {

	 
	/**
	 * 保存角色
	 * @param urole
	 * @return
	 * @throws XingYou5UserException 
	 * */
	public void saveUrole(Urole urole) throws XingYou5UserException ;
	
	/**
	 * 判断角色名是否重复
	 * @param uroleName 角色名
	 * @return boolean true :非重复 false:重复
	 * @throws XingYou5UserException 
	 * */
	public boolean repeatUroleName(String uroleName) throws XingYou5UserException;
	
	/**
	 * 判断角色名是否重复
	 * @param uroleName 角色名
	 * @param uroleId 角色Id
	 * @return boolean true :非重复 false:重复
	 * @throws XingYou5UserException 
	 * */
	public  boolean repeatUroleNameUroleId(String uroleName, Integer uroleId) throws XingYou5UserException;
	
	/**
	 * 获取角色可访问的菜单
	 * @param uroleId
	 * @return MenuModel
	 * */
	public Uresource getUroleMenuMerge(Integer uroleId);

	/**
	 * 获取角色    可访问  的资源
	 * @param uroleId
	 * @return MenuModel
	 * */
	public Uresource getUroleMenu(Integer uroleId);

    /**
     * 保存角色资源
     * @param urole
     * @param uresourceIds
     * @return
     * @throws XingYou5UserException 
     * */
	public void saveUroleUresources(Urole urole, List<Integer> uresourceIds) throws XingYou5UserException;

	/**
	 * 激活或禁用角色
	 * @param uroleI
	 * @param ableOrUnable :true 激活，false 禁用
	 * */
	public void updateActiveUrole(Integer uroleId, boolean ableOrUnable);

	/**
	 * 刷新资源权限
	 * */
	public void refreshResource();

	/**
	 * 通过用户Ids查找创建的角色Ids
	 * @param userIds
	 * @return list
	 */
	public List<Integer> listCreateUroleIdsByUserIds(List<Integer> userIds);  
	


	/**
	 * 通过用户Id查找角色Ids
	 * @param userId
	 * @return list
	 */
	public List<Integer> listNormalUroleIdsByUserId(Integer userId);  
	
	/**
	 * 根据用户Id获取角色Ids
	 * @param userId
	 * @return list
	 */
	public List<Integer> listUroleIdsByUserId(@Param("userId")Integer userId);  
	
	/**
	 * 判断角色是否有关联用户
	 * @param uroleId
	 * @return boolean true:有关联    false：否
	 * */
	public boolean checkUrole(Integer uroleId);
	
	
	/**
	 * 从页面获取所有资源Id
	 * @param urole
	 * @param uresourceIds 
	 * @return
	 * @throws XingYou5UserException 
	 * */
	public  void saveUroleUresources(Urole urole, String uresourceIds) throws XingYou5UserException;
	
	
}
