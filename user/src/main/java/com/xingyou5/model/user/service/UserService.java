package com.xingyou5.model.user.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import com.xingyou5.model.user.entity.Uresource;
import com.xingyou5.model.user.entity.Urole;
import com.xingyou5.model.user.entity.User;
import com.xingyou5.model.user.exception.XingYou5UserException;

import freemarker.template.TemplateException;

public interface UserService {

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return
	 * */
	public void saveUser(User user) throws XingYou5UserException;

	/**
	 * 获取用户资源
	 * 
	 * @param username
	 * */
	public Uresource getUserResource(Integer userId);

 

	 
	/**
	 * 保存用户角色
	 * 
	 * @param user
	 * @param uroleIds
	 * @return
	 * @throws XingYou5UserException
	 * */
	public void saveUserUroles(User user, List<Integer> uroleIds)
			throws XingYou5UserException;

	/**
	 * 逻辑删除用户，及其子用户，以及其所创建的所有角色
	 * 
	 * @param userId
	 * @return
	 * */
	public void updateUnableUserCascade(Integer userId);

	/**
	 * 重置密码，将密码重新设置为默认
	 * 
	 * @param userId
	 * @return
	 * */
	public void updateDefaultPassword(Integer userId);

	/**
	 * 判断用户名是否重复
	 * 
	 * @param username
	 *            用户名
	 * @return boolean true :非重复 false:重复
	 * */
	public boolean isDuplicateName(String username);

	/**
	 * 判断用户名是否重复 去除用户Id，一般编辑时使用
	 * 
	 * @param username
	 *            用户名
	 * @param userId
	 *            用户Id
	 * @return boolean true :非重复 false:重复
	 * */
	public boolean isDuplicateName(String username, Integer userId);
	 
	/**
	 * 获取当前用户角色
	 * 
	 * @return List<Urole>
	 * @throws XingYou5UserException
	 * */
	public List<Urole> getCurrentUserCreateUrole() throws XingYou5UserException;

	/**
	 * 获取当前登录人的所有可访问资源
	 * 
	 * @return MenuModel
	 * @throws XingYou5UserException
	 * */
	public Uresource getCurrentUserMenu() throws XingYou5UserException;

	 
	/**
	 * 验证用户密码一致性
	 * 
	 * @param username
	 * @param password
	 * @return boolean true:一致 false:不一致
	 * */
	public boolean checkPassword(String username, String password);

	/**
	 * 根据用户Id 更新密码
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * */
	public void updatePswByUserId(Integer userId, String password);

	/**
	 * 更新最近登录时间
	 * 
	 * @param userId
	 * @return
	 * */
	public void updateLastLoginTimeByUserId(Integer userId);

	/**
	 * 判断email是否重复
	 * 
	 * @param email
	 * @return boolean true :非重复 false:重复
	 * */
	public boolean isDulplicateEmail(String email);

	/**
	 * 判断email是否重复 去除用户Id 一般编辑时使用
	 * 
	 * @param email
	 * @param userId
	 * @return boolean true :非重复 false:重复
	 * */
	public boolean isDulplicateEmail(String email, Integer userId);

	/**
	 * 通过用户名查找用户信息
	 * 
	 * @param userName
	 * @return user
	 */
	public User getByName(String userName);

	/**
	 * 判断当前登录用户是否有某个url权限
	 * 
	 * @param url
	 * @return true :有 false： 否
	 * */
	public boolean checkUserUrlAuth(String url);


	/**
	 * 从页面获取所有角色Id
	 * 
	 * @param user
	 * @param userIds
	 * @return
	 * @throws XingYou5UserException
	 * */
	public void saveUserUroles(User user, String uroleIds)
			throws XingYou5UserException;

	/**
	 * 找回密码
	 * 
	 * @param username
	 * @return String ：返回email
	 * @throws XingYou5UserException
	 * @throws TemplateException
	 * @throws IOException
	 * @throws MessagingException
	 * */
	public String updateForfindbackForMp(String username)
			throws XingYou5UserException, MessagingException, IOException,
			TemplateException;
  
	/**
	 * 根据用户名称 更新密码 （注意，密码是明文还是密文，请在调用本方法之前做好转换）
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * */
	public boolean updatePswByUserName(String userName, String password);
 
	/**
	 * 根据用户名称 更新uuid（随心购 email=uuid）
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * */
	public boolean updateEmailByUserName(String userName, String email);
	
	 

}
