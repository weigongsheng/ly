package com.xingyou5.model.user;

import org.omg.CORBA.UserException;

import com.xingyou5.model.user.entity.User;

public interface UserInfoService {
	/***
	 * 前台用户的注册
	 * 
	 * @param user
	 */
	public void addRegister(User user) throws UserException;

}
