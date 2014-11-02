package com.xingyou5.module.adv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xingyou5.module.adv.dao.AdvertisementDao;
import com.xingyou5.module.adv.entity.Advertisement;
import com.xingyou5.module.adv.service.AdvertisementServer;

@Service
public class AdvertisementServerImpl implements AdvertisementServer {
	
	@Autowired
	private AdvertisementDao advDao;
	
	@Override
	public List<Advertisement> querySlideAdv() {
		
		return advDao.queryByType(ADV_TYPE_SLIDE);
	}

	@Override
	public List<Advertisement> queryFrontAdv() {
		return advDao.queryByType(ADV_TYPE_FRONT);
	}

}
