package com.xingyou5.model.user;

import java.util.List;
import java.util.Map;

import org.omg.CORBA.UserException;

import com.xingyou5.model.base.exception.BaseException;
import com.xingyou5.model.user.entity.User;
import com.xingyou5.model.user.entity.UserInfo;

public interface UserInfoService {
	/***
	 * 前台用户的注册
	 * 
	 * @param user
	 * @throws com.xingyou5.model.user.exception.UserException 
	 * @throws BaseException 
	 */
	public void addRegister(User user) throws UserException, BaseException, com.xingyou5.model.user.exception.UserException;

	public UserInfo getUserInfoById(Integer userId);

	boolean isMobileRepeat(String mobile);

	boolean isEmailRepeat(String email);

	void updateUserInfoTel(Map map) throws BaseException;

	void updateUserInfoEmail(Map map) throws BaseException;

	List<UserInfo> listUserInfoByUsernNames(List<String> userNames);

	String validUserNames(List<String> userNames);

	boolean validateEmailIsRepate(String email, Integer userId);

	Integer savePassword(UserInfo userInfo) throws BaseException;

	void updateUserStauts(List<Integer> userIds, Integer status) throws BaseException;

	void resetPassword(Integer userId) throws BaseException;

	void updateUserAndUserInfo(UserInfo userInfo) throws Exception;

	UserInfo getUserByUserId(Integer userId) throws BaseException;

	void updateUserInfo(UserInfo userInfo) throws Exception;

	void updateUserRegister(UserInfo userInfo) throws BaseException;

}
