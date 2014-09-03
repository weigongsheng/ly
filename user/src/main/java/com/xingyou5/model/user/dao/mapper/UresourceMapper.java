package com.xingyou5.model.user.dao.mapper;



import java.util.List;

import com.xingyou5.model.base.dao.mapper.BaseSqlMapper;
import com.xingyou5.model.user.entity.Uresource;

 


public interface UresourceMapper<T extends Uresource> extends BaseSqlMapper<T> {
	
	
	/**
	 * 获取资源url 角色 roleName
	 * */
	public List<Uresource> listUroleIdsResUrl();
	
	/**
	 * 获取正常资源URL
	 * */
	public List<Uresource> listNormalResources();
	
	
}
