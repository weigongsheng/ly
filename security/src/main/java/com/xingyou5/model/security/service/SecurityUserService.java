package com.xingyou5.model.security.service;

import com.xingyou5.model.security.CustomUserDetails;
import com.xingyou5.model.user.exception.XingYou5UserException;

public interface SecurityUserService {

	/**
	 * 获取当前登录用户
	 * 
	 * @return CustomUserDetails
	 * @throws XingYou5UserException
	 * */
	public CustomUserDetails getCurrentUser() throws XingYou5UserException;

}
