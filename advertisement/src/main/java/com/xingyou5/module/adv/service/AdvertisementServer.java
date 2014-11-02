package com.xingyou5.module.adv.service;

import java.util.List;

import com.xingyou5.module.adv.entity.Advertisement;

public interface AdvertisementServer {
	public static int ADV_TYPE_SLIDE=10;
	public static int ADV_TYPE_FRONT=11;
	List<Advertisement> querySlideAdv();
	List<Advertisement> queryFrontAdv();
	
}
