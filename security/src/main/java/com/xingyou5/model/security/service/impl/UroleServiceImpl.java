package com.xingyou5.model.security.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xingyou5.model.base.web.BasePagination;
import com.xingyou5.model.security.CodeConstants;
import com.xingyou5.model.security.CompareMenuModel;
import com.xingyou5.model.security.CustomInvocationSecurityMetadataSourceService;
import com.xingyou5.model.security.dao.mapper.UroleMapper;
import com.xingyou5.model.security.entity.Uresource;
import com.xingyou5.model.security.entity.Urole;
import com.xingyou5.model.security.exception.XingYou5UserException;
import com.xingyou5.model.security.service.UresourceService;
import com.xingyou5.model.security.service.UroleService;
import com.xingyou5.model.security.service.UserService;


@Service
public class UroleServiceImpl implements UroleService {
	
	@Autowired
	private UroleMapper<Urole> uroleMapper;
	@Autowired
	private UresourceService uresourceService;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomInvocationSecurityMetadataSourceService customSecurityMetadataSource ;
	

	/**
	 * 保存角色
	 * @param urole
	 * @return
	 * @throws XingYou5UserException 
	 * */
	 
	public void saveUrole(Urole urole) throws XingYou5UserException {
		try {
				if(urole.getUroleId()!=null){
					uroleMapper.update(urole);
				}else{
					if(repeatUroleName(urole.getUroleName())){
						Integer userId = userService.getCurrentUser().getUserId();
						//设置当前登录用户Id
						urole.setCreateByUserId(userId);
						//默认激活
						urole.setStatus(Urole.STATUS_ABLE);
						uroleMapper.add(urole);
					}else{
						throw new XingYou5UserException(CodeConstants.E_UROLENAME_REPEAT);
					}
				}
		} catch (Exception e) {
			throw new XingYou5UserException(e.getMessage());
		}
	}

	/**
	 * 判断角色名是否重复
	 * @param uroleName 角色名
	 * @return boolean true :非重复 false:重复
	 * @throws XingYou5UserException 
	 * */
	 
	public boolean repeatUroleName(String uroleName) throws XingYou5UserException{
		Integer userId = userService.getCurrentUser().getUserId();
		List<Urole> uroles = uroleMapper.listCreateUrolesByUserId(userId);
		if(uroles==null){
			return true;
		}
		for(Urole urole:uroles){
			if(uroleName.equals(urole.getUroleName())){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 判断角色名是否重复
	 * @param uroleName 角色名
	 * @param uroleId 角色Id
	 * @return boolean true :非重复 false:重复
	 * @throws XingYou5UserException 
	 * */
	 
	public boolean repeatUroleNameUroleId(String uroleName,Integer uroleId) throws XingYou5UserException{
		if(uroleId==null||uroleId==0){
			return repeatUroleName(uroleName);
		}
		Integer userId = userService.getCurrentUser().getUserId();
		List<Urole> uroles = uroleMapper.listCreateUrolesByUserId(userId);
		if(uroles==null){
			return true;
		}
		for(Urole urole:uroles){
			if(!uroleId.equals(urole.getUroleId())){
				if(uroleName.equals(urole.getUroleName())){
					return false;
				}
			}
		}
		return true;
	}
	
	
    /**
     * 保存角色资源
     * @param urole
     * @param uresourceIds
     * @return
     * */
	 
	public void saveUroleUresources(Urole urole,List<Integer> uresourceIds)  throws XingYou5UserException{
        saveUrole(urole);
		Integer uroleId = urole.getUroleId();
		//原有uresourceIds
		List<Integer> beforeUresourceIds = uroleMapper.listUresourceIdByUroleId(uroleId);
		if(uresourceIds!=null&&!uresourceIds.isEmpty()){
			if(beforeUresourceIds!=null&&!beforeUresourceIds.isEmpty()){
				//增加的uresourceIds
				List<Integer> addIds = new ArrayList<Integer>();
				for(Integer id:uresourceIds){
					boolean addFlag = true;
					for(Integer bId:beforeUresourceIds){
						if(bId.equals(id)){
							addFlag = false;
							break;
						}
					}
					if(addFlag){
						addIds.add(id);
					}
				}
				if(!addIds.isEmpty()){
				    uroleMapper.addUroleUresources(uroleId, addIds);
				}
				//删除的uresourceIds
				List<Integer> delIds = new ArrayList<Integer>();
				for(Integer id:beforeUresourceIds){
					boolean delFlag = true;
					for(Integer bId:uresourceIds){
						if(bId.equals(id)){
							delFlag = false;
							break;
						}
					}
					if(delFlag){
						delIds.add(id);
					}
				}
				if(!delIds.isEmpty()){
				    uroleMapper.delUroleUresources(uroleId, delIds);
				}
			}else{//新增新数据
				uroleMapper.addUroleUresources(uroleId, uresourceIds);
			}
		}else{//删除所有原有数据
			if(beforeUresourceIds!=null&&!beforeUresourceIds.isEmpty()){
				uroleMapper.delUroleUresources(uroleId, beforeUresourceIds);
			}
		}
		
		//角色变动都需要刷新资源
		refreshResource();
	}
	
	/**
	 * 激活或禁用角色
	 * @param uroleI
	 * @param ableOrUnable :true 激活，false 禁用
	 * */
	 
	public void updateActiveUrole(Integer uroleId,boolean ableOrUnable){
		if(ableOrUnable){
			uroleMapper.updateStatusByUroleId(uroleId,Integer.valueOf(Urole.STATUS_ABLE));
		}else{
			uroleMapper.updateStatusByUroleId(uroleId,Integer.valueOf(Urole.STATUS_UNABLE));
		}
		//角色变动都需要刷新资源
		refreshResource();
	}
	
	
	/**
	 * 获取角色    可访问和不可访问  的资源
	 * @param uroleId
	 * @return MenuModel
	 * */
	 
	public Uresource getUroleMenuMerge(Integer uroleId){
		Urole urole = uroleMapper.get(uroleId);
		if(urole==null){
			return null;
		}
		List<Integer> roles = new ArrayList<Integer>();
		roles.add(urole.getUroleId());
		Uresource allMenuModel = uresourceService.getAllMenuModel();
		return uresourceService.getMenuModelMerge(allMenuModel, roles,false);
	}
	
	/**
	 * 获取角色    可访问  的资源
	 * @param uroleId
	 * @return MenuModel
	 * */
	 
	public Uresource getUroleMenu(Integer uroleId){
		List<Integer> roles = new ArrayList<Integer>();
		roles.add(uroleId);
		Uresource allMenuModel = uresourceService.getAllMenuModel();
		return uresourceService.getMenuModel(allMenuModel, roles,false);
	}
	
	/**
	 * 刷新资源权限
	 * */
	 
	public void refreshResource(){
		customSecurityMetadataSource.init();
	}
	
	/**
	 * 通过用户Id查找角色Ids
	 * @param userId
	 * @return list
	 */
	 
	public List<Integer> listNormalUroleIdsByUserId(Integer userId){
		return uroleMapper.listNormalUroleIdsByUserId(userId);
	}
	/**
	 * 通过用户Ids查找创建的角色Ids
	 * @param userIds
	 * @return list
	 */
	 
	public List<Integer> listCreateUroleIdsByUserIds(List<Integer> userIds){
		return uroleMapper.listCreateUroleIdsByUserIds(userIds);
	}
	
	/**
	 * 根据用户Id获取角色Ids
	 * @param userId
	 * @return list
	 */
	public List<Integer> listUroleIdsByUserId(Integer userId){
		return uroleMapper.listUroleIdsByUserId(userId);
	}
	
	/**
	 * 判断角色是否有关联用户
	 * @param uroleId
	 * @return boolean true:有关联    false：否
	 * */
	 
	public boolean checkUrole(Integer uroleId){
		List<Integer> userIds = uroleMapper.listUserIdsByUroleId(uroleId);
		if(userIds!=null&&!userIds.isEmpty()){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 从页面获取所有资源Id
	 * @param urole
	 * @param uresourceIds 
	 * @return
	 * @throws XingYou5UserException 
	 * */
	 
	public void saveUroleUresources(Urole urole,String uresourceIds) throws XingYou5UserException{
		if(uresourceIds==null||uresourceIds.equals("")){
			throw new XingYou5UserException(CodeConstants.E_PARAM_NOTCORRECT);
		}
		String[] ids = uresourceIds.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<ids.length;i++){
			list.add(Integer.parseInt(ids[i]));
		}
		saveUroleUresources(urole,list);
	}

	 
	
}
