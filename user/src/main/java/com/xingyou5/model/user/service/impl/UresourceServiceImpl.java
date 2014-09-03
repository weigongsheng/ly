package com.xingyou5.model.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xingyou5.model.user.CompareMenuModel;
import com.xingyou5.model.user.dao.mapper.UresourceMapper;
import com.xingyou5.model.user.entity.Uresource;
import com.xingyou5.model.user.service.UresourceService;

 
@Service
public class UresourceServiceImpl implements UresourceService {
	
	@Autowired
	private UresourceMapper<Uresource> uresourceMapper;
	
	/**
	 * 获取拥有单个角色的 菜单    (只包括可访问)
	 * @param menuModel
	 * @param roleId
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
	 * @return Uresource
	 * */
	public Uresource getMenuModel(Uresource menuModel,Integer roleId,boolean onlyMenu){
		List<Integer> roles = new ArrayList<Integer>();
		roles.add(roleId);
		MenuModelRole(menuModel,roles,onlyMenu);
		return menuModel;
	}
	
	/**
	 * 获取拥有多个角色的 菜单  (只包括可访问)
	 * @param menuModel
	 * @param roles
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
	 * @return Uresource
	 * */
	public Uresource getMenuModel(Uresource menuModel,List<Integer> roles,boolean onlyMenu){
		MenuModelRole(menuModel,roles,onlyMenu);
		return menuModel;
	}
	
	
	/**
	 * 获取拥有单个角色的 菜单    (包括可访问和不可访问)
	 * @param menuModel
	 * @param roleName
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
	 * @return Uresource
	 * */
	public Uresource getUresourceMerge(Uresource menuModel,Integer roleId,boolean onlyMenu){
		List<Integer> roles = new ArrayList<Integer>();
		roles.add(roleId);
		MenuModelRoleMerge(menuModel,roles,onlyMenu);
		return menuModel;
	}
	
	/**
	 * 获取拥有多个角色的 菜单  (包括可访问和不可访问)
	 * @param menuModel
	 * @param roles
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
	 * @return Uresource
	 * */
	public Uresource getMenuModelMerge(Uresource menuModel,List<Integer> roles,boolean onlyMenu){
		MenuModelRoleMerge(menuModel,roles,onlyMenu);
		return menuModel;
	}
	
	/**
	 * 设置子菜单
	 * @param menuModel
	 * @param lists 对应的角色
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
	 * @return
	 * */
	private void MenuModelRole(Uresource menuModel,List<Integer> roles,boolean onlyMenu){
		
		List<Uresource> list = menuModel.getChildMenuModel();
		List<Uresource> newList = new ArrayList<Uresource>(); 
		if(list!=null){
			for(Uresource m : list){
				if(!onlyMenu||m.getIsMenu()!=null&&m.getIsMenu().equals(1)){
					Set<Integer> sets = m.getRoles();
					if(sets!=null){
						boolean hasRole = false;
						for(Integer r : sets){
							for(Integer rs:roles){
								if(r.equals(rs)){
							   		hasRole = true;
							   		break;
							   	}
							}
						}
						if(hasRole){
							MenuModelRole(m,roles,onlyMenu);
							newList.add(m);
						}
					}else{
						MenuModelRole(m,roles,onlyMenu);
						newList.add(m);
					}
				}
			}
			CompareMenuModel comparator=new CompareMenuModel();
		    Collections.sort(newList, comparator);
			menuModel.setChildMenuModel(newList);
		}
	}
	
	/**
	 * 设置子菜单      合并可访问和不可访问菜单
	 * @param menuModel
	 * @param lists 对应的角色
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
	 * @return
	 * */
	private void MenuModelRoleMerge(Uresource menuModel,List<Integer> roles,boolean onlyMenu){
		
		List<Uresource> list = menuModel.getChildMenuModel();
		List<Uresource> newList = new ArrayList<Uresource>(); 
		if(list!=null){
			for(Uresource m : list){
				if(!onlyMenu||m.getIsMenu()!=null&&m.getIsMenu().equals(1)){
					Set<Integer> sets = m.getRoles();
					if(sets!=null){
						boolean hasRole = false;
						for(Integer r : sets){
							for(Integer rs:roles){
								if(r.equals(rs)){
							   		hasRole = true;
							   		break;
							   	}
							}
						}
						MenuModelRoleMerge(m,roles,onlyMenu);
						if(hasRole){
							m.setFlag(Uresource.MENU_FLAG_CAN);
						}else{
							m.setFlag(Uresource.MENU_FLAG_CANNOT);
						}
						newList.add(m);
					}else{
						MenuModelRoleMerge(m,roles,onlyMenu);
						m.setFlag(Uresource.MENU_FLAG_CAN);
						newList.add(m);
					}
				}
			}
			CompareMenuModel comparator=new CompareMenuModel();
		    Collections.sort(newList, comparator);
			menuModel.setChildMenuModel(newList);
		}
	}
	
	/**
	 * 获取资源url 角色 roleName
	 * */
	public List<Uresource> listRoleNameResUrl() {
		return uresourceMapper.listUroleIdsResUrl();
	}
    
	/**
	 * 获取所有菜单  
	 * @return Uresource
	 * */
	public Uresource getAllMenuModel(){
		return null;
//		return (Uresource)CloneObject.clone(CustomInvocationSecurityMetadataSourceService.getAllMenuModel());
	}


}
