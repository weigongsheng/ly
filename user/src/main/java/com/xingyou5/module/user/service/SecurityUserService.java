package com.xingyou5.module.user.service;

import com.xingyou5.module.user.UserVo;
import com.xingyou5.module.user.exception.XingYou5UserException;

public interface SecurityUserService {

	/**
	 * 获取当前登录用户
	 * 
	 * @return CustomUserDetails
	 * @throws XingYou5UserException
	 * */
	public UserVo getCurrentUser() throws XingYou5UserException;

}
