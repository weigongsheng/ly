package com.xingyou5.model.user.dao.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xingyou5.model.base.dao.mapper.BaseSqlMapper;
import com.xingyou5.model.user.entity.Uresource;
import com.xingyou5.model.user.entity.Urole;
 

public interface UroleMapper<T extends Urole> extends BaseSqlMapper<T> {
	
	/**
	 * 查询角色
	 * */
	public List<Urole> searchUroles(Map<String,Object> map);
	public Integer searchUrolesCount(Map<String,Object> map);
	
	/**
	 * 批量新增角色资源
	 * @param uroleId
	 * @param uresourceIds
	 * @return
	 * */
	public void addUroleUresources(@Param("uroleId")Integer uroleId,@Param("uresourceIds")List<Integer> uresourceIds);
	
	/**
	 * 批量删除角色资源
	 * @param uroleId
	 * @param uresourceIds
	 * @return
	 * */
	public void delUroleUresources(@Param("uroleId")Integer uroleId,@Param("uresourceIds")List<Integer> uresourceIds);
	
	/**
	 * 根据角色Id 获得资源Id
	 * @param uroleId
	 * @return list
	 * */
	public List<Integer> listUresourceIdByUroleId(Integer uroleId);
	
	/**
	 * 根据角色Id 获得资源
	 * @param uroleId
	 * @return list
	 * */
	public List<Uresource> listUresourceByUroleId(Integer uroleId);
	
	/**
	 * 激活或禁用角色
	 * @param uroleId
	 * @param status
	 * @return
	 * */
	public void updateStatusByUroleId(@Param("uroleId")Integer uroleId,@Param("status")Integer status);
	
	/**
	 * 根据创建用户Ids  更新角色状态
	 * @param createByUserIds
	 * @param status
	 * @return
	 * */
	public void updateStatusByCuserIds(@Param("createByUserIds")List<Integer> createByUserIds,@Param("status")Integer status);
	
	/**
	 * 根据角色名获得角色Id
	 * @param uroleName
	 * @return Integer uroleId 
	 * */
	public Integer getUroleIdByUroleName(String uroleName);
	
	/**
	 * 通过用户Id查找角色Ids
	 * @param userId
	 * @return list
	 */
	public List<Integer> listNormalUroleIdsByUserId(Integer userId);  
	
	/**
	 * 通过用户Id查找角色
	 * @param userId
	 * @return list
	 */
	public List<Urole> listNormalUroleByUserId(Integer userId);  
	
	/**
	 * 通过用户Id查找角色
	 * @param userId
	 * @return list
	 */
	public List<Urole> listUrolesByUserId(Integer userId);  
	
	/**
	 * 通过用户Id查找创建的角色
	 * @param userId
	 * @return list
	 */
	public List<Urole> listCreateUrolesByUserId(Integer userId);  
	
	/**
	 * 通过用户Ids查找创建的角色Ids
	 * @param userIds
	 * @return list
	 */
	public List<Integer> listCreateUroleIdsByUserIds(@Param("userIds")List<Integer> userIds);  
	
	/**
	 * 根据用户Id获取角色Ids
	 * @param userId
	 * @return list
	 */
	public List<Integer> listUroleIdsByUserId(@Param("userId")Integer userId);  
	
	/**
	 * 根据角色Id 获取用户Ids
	 * @params uroleId
	 * @return list
	 * */
	public List<Integer> listUserIdsByUroleId(Integer uroleId);
}
