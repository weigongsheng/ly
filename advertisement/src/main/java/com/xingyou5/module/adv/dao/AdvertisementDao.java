package com.xingyou5.module.adv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xingyou5.module.adv.entity.Advertisement;
import com.xingyou5.module.base.dao.mapper.BaseSqlMapper;

public interface AdvertisementDao extends BaseSqlMapper<Advertisement> {
	
	List<Advertisement> queryByType(@Param("type") Integer type);
}
