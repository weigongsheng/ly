package com.xingyou5.model.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xingyou5.model.base.exception.BaseException;
import com.xingyou5.model.user.UserInfoService;
import com.xingyou5.model.user.dao.mapper.UroleMapper;
import com.xingyou5.model.user.dao.mapper.UserInfoMapper;
import com.xingyou5.model.user.dao.mapper.UserMapper;
import com.xingyou5.model.user.entity.Urole;
import com.xingyou5.model.user.entity.User;
import com.xingyou5.model.user.entity.UserInfo;
import com.xingyou5.model.user.exception.UserException;
import com.xingyou5.model.user.service.UserService;

/**
 * 用户
 * 
 * @author 
 * 
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

	private static Logger logger = LoggerFactory
			.getLogger(UserInfoServiceImpl.class);
	@Autowired
	private UserMapper<User> userMapper;
	@Autowired
	private UserInfoMapper<UserInfo> userInfoMapper;
	 
 
	 
	@Autowired
	private UserService userService;
 
	@Value("${defaultPass}")
	private String defaultPass;
	@Value("${password_salt}")
    private String passwordSalt;	
	@Value("${sp_express_connect_timeout}")
	private String connectTimeout;
	@Value("${sp_express_read_timeout}")
	private String readTimeout;
	@Value("${qq.final.contest.key1}")
	private String QQ_FANLI_CONNECT_KEY1;
	@Value("${qq.final.contest.key2}")
	private String QQ_FANLI_CONNECT_KEY2;
	
	@Autowired
	private UroleMapper<Urole> uroleMapper;
	 
	public static final Integer  OPERATION_TYPE_ADD=1;
	public static final Integer  OPERATION_TYPE_DELETE=2;
	public static final Integer  OPERATION_TYPE_UPDATE=3;
	public static final Integer OPERATION_TYPE_UPDATE_PAY=4;
	
	
	/**获得所有商家
	 * @return
	 */
	public List<User> getSeller(){
		return userInfoMapper.getSeller();
	}
	
	/***
	 * 更改用户信息
	 * 
	 * @param user
	 */
	@Transactional
	public void updateUser(User user)  {
		userInfoMapper.updateActiveUser(user);
	}
 

	@Override
	public void updateUserAndUserInfo(UserInfo userInfo) throws Exception {
		try {
			updateUserInfo(userInfo);// 更新userInfo表中的数据
			updateUserRegister(userInfo);// 更新user表中的数据
			return;
		} catch (DataAccessException d) {
			// 抛出异常提示action 其他选项长度超出异常 故注释email重复! 提示
			throw new BaseException("email重复!");
		}

	}

	@Override
	public UserInfo getUserByUserId(Integer userId) throws BaseException {
		UserInfo userInfo = userInfoMapper.getUserInfoByUserId(userId);
		return userInfo;
	}
	

	@Override
	public void updateUserInfo(UserInfo userInfo) throws Exception {
		userInfoMapper.updateUser(userInfo);
	}

	@Override
	public void updateUserRegister(UserInfo userInfo)
			throws BaseException {
		userInfoMapper.updateUserRegister(userInfo);
	}

	@Override
	public void addRegister(User user) throws BaseException,
			UserException {
		List<Integer> list = new ArrayList<Integer>();// 为买家加载角色
		list.add(UserInfo.USER_ROLE_BUYER);
		user.setStatus(User.STATUS_ABLE);// 设置其为激活状态
		user.setCreateByUserId(0);// 设置其为激活状态
		userMapper.add(user);//
		userMapper.addUserUroles(user.getUserId(), list);
		// 注册成功发送邮件

	}

	 
	 
 

	/**
	 * 更新用户状态
	 * 
	 * @param userId
	 * @param status
	 * @throws BaseException
	 */
	@Override
	public void updateUserStauts(List<Integer> userIds, Integer status)
			throws BaseException {
		userMapper.updateStatusByUserIds(userIds, status);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void resetPassword(Integer userId) throws BaseException {
		userService.updateDefaultPassword(userId);
	}

	 
  
 
 

	 
	@Override
	public Integer savePassword(UserInfo userInfo) throws BaseException {
//		try {
//			if (userService.checkPassword(security.getCurrentUser()
//					.getUsername(), userInfo.getUser().getPassword())) {
//				if (StringUtils.isNotEmpty(userInfo.getNewPassword())
//						&& StringUtils.isNotEmpty(userInfo
//								.getConfirmNewPassword())
//						&& userInfo.getNewPassword().equals(
//								userInfo.getConfirmNewPassword())) {
//					User user = userInfo.getUser();
//					user.setUserId(userService.getCurrentUser().getUserId());
//					user.setPassword(userInfo.getNewPassword());
//					userService.saveUser(user);
//				} else {
//					throw new BaseException("与新密码不一致");
//				}
//
//			} else {
//				throw new BaseException("密码错误");
//			}
//		} catch (UserException e) {
//			throw new BaseException(e.getMessage());
//		}
		return null;
	}

 
	/**
	 * 添加卖家的时候判断邮箱是否存在
	 * 
	 * @param email
	 * @param userId
	 * @return boolean true :非重复 false:重复
	 */
	@Override
	public boolean validateEmailIsRepate(String email, Integer userId) {
		User user = userInfoMapper.validateEmailIsRepate(email);
		if (userId == null) {
			if (user != null) {
				return false;
			}
			return true;
		}
		if (user != null) {
			if (user.getUserId().equals(userId)) {
				return true;
			}
			return false;
		}
		return true;
	}


	@Override
	public String validUserNames(List<String> userNames) {
		StringBuilder result = null;
		List<String> unames= userInfoMapper.listValidUserNamesByUsernNames(userNames);
		if(userNames.size() == unames.size()){
			return "成功发送 "+unames.size()+"人";
		}
		
		boolean flag = false;
		result = new StringBuilder("接收人\"");
		for(String uname : userNames){
			if(!unames.contains(uname)){
				flag = true;
				result.append(uname).append(",");			
			}
		}
		if(!flag){
			return "成功发送 "+unames.size()+"人";
		}
		
		String rmessage = result.substring(0, result.length()-1) + "\"不存在 ！";
		
		return rmessage;
	}

	@Override
	public List<UserInfo> listUserInfoByUsernNames(List<String> userNames) {
		return userInfoMapper.listUserInfoByUsernNames(userNames);
	}

	 
	
	@Override
	public void updateUserInfoEmail(Map map) throws BaseException {
		userInfoMapper.updateUserInfoEmail(map);
	}

	@Override
	public void updateUserInfoTel(Map map) throws BaseException {
		userInfoMapper.updateUserInfoTel(map);
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.yipin.base.service.UserInfoService#isEmailRepeat(java.lang.String)
	 */
	@Override
	public boolean isEmailRepeat(String email) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		return userInfoMapper.checkUserInfoByEmailOrMobile(map) != 0;
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.yipin.base.service.UserInfoService#isMobileRepeat(java.lang.String)
	 */
	@Override
	public boolean isMobileRepeat(String mobile) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		return userInfoMapper.checkUserInfoByEmailOrMobile(map) != 0;
	}

	@Override
	public UserInfo getUserInfoById(Integer userId) {
		return userInfoMapper.getUserInfoByUserId(userId);
	}
  
 
 
}
