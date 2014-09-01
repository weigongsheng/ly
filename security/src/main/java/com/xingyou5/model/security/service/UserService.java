package com.xingyou5.model.security.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import com.xingyou5.model.base.web.BasePagination;
import com.xingyou5.model.security.CustomUserDetails;
import com.xingyou5.model.security.entity.Uresource;
import com.xingyou5.model.security.entity.Urole;
import com.xingyou5.model.security.entity.User;
import com.xingyou5.model.security.exception.YtoxlUserException;

import freemarker.template.TemplateException;

public interface UserService {

	/**
	 * 获取当前登录用户
	 * 
	 * @return CustomUserDetails
	 * @throws YtoxlUserException
	 * */
	public CustomUserDetails getCurrentUser() throws YtoxlUserException;

	/**
	 * 分页查询用户信息
	 * 
	 * @param userModelPage
	 * @return
	 * @throws YtoxlUserException
	 * */
	public void searchUsers(BasePagination<User> userModelPage)
			throws YtoxlUserException;

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return
	 * */
	public void saveUser(User user) throws YtoxlUserException;

	/**
	 * 保存用户信息 此处验证了重复密码
	 * 
	 * @param userModel
	 * @return
	 * @throws YtoxlUserException
	 * */
	public void saveUserModel(User userModel) throws YtoxlUserException;

	/**
	 * 获取用户菜单
	 * 
	 * @param username
	 * */
	public Uresource getUserMenu(Integer userId);

	/**
	 * 通过用户Id 获取其所有子用户Id 以及自己 默认获取激活中的用户
	 * 
	 * @param userId
	 * @return list
	 * */
	public List<Integer> listUserIdByUserId(Integer userId);

	/**
	 * 通过用户Id 获取其所有子用户Id 默认获取激活中的用户
	 * 
	 * @param userId
	 * @return list
	 * */
	public List<Integer> listChildUserIdByUserId(Integer userId);

	/**
	 * 通过用户Id 获取其所有子用户Id 以及自己
	 * 
	 * @param userId
	 * @return list
	 * */
	public List<Integer> listUserIdByUserId(Integer userId, Integer status);

	/**
	 * 通过用户Id 获取其所有子用户Id
	 * 
	 * @param userId
	 * @return list
	 * */
	public List<Integer> listChildUserIdByUserId(Integer userId, Integer status);

	/**
	 * 保存用户角色
	 * 
	 * @param user
	 * @param uroleIds
	 * @return
	 * @throws YtoxlUserException
	 * */
	public void saveUserUroles(User user, List<Integer> uroleIds)
			throws YtoxlUserException;

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
	public boolean repeatUsername(String username);

	/**
	 * 判断用户名是否重复 去除用户Id，一般编辑时使用
	 * 
	 * @param username
	 *            用户名
	 * @param userId
	 *            用户Id
	 * @return boolean true :非重复 false:重复
	 * */
	public boolean repeatUsername(String username, Integer userId);

	/**
	 * 获取UserModel 包含用户角色
	 * 
	 * @param userId
	 * @return User
	 * */
	public User getUserModelHave(Integer userId);

	/**
	 * 获取当前用户角色
	 * 
	 * @return List<Urole>
	 * @throws YtoxlUserException
	 * */
	public List<Urole> getCurrentUserCreateUrole() throws YtoxlUserException;

	/**
	 * 获取当前登录人的所有可访问资源
	 * 
	 * @return MenuModel
	 * @throws YtoxlUserException
	 * */
	public Uresource getCurrentUserMenu() throws YtoxlUserException;

	/**
	 * 获取UserModel 包含用户角色 （此角色是用户创建和用户拥有的角色）
	 * 
	 * @param userId
	 * @return User
	 * @throws YtoxlUserException
	 * */
	public User getUserModel(Integer userId) throws YtoxlUserException;

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
	public boolean repeatEmail(String email);

	/**
	 * 判断email是否重复 去除用户Id 一般编辑时使用
	 * 
	 * @param email
	 * @param userId
	 * @return boolean true :非重复 false:重复
	 * */
	public boolean repeatEmail(String email, Integer userId);

	/**
	 * 通过用户名查找用户信息
	 * 
	 * @param userName
	 * @return user
	 */
	public User getByName(String userName);

	/**
	 * 根据username获取用户状态
	 * 
	 * @param username
	 * @return Integer
	 */
	public Integer getStatusByUsername(String username);

	/**
	 * 判断当前登录用户是否有某个url权限
	 * 
	 * @param url
	 * @return true :有 false： 否
	 * */
	public boolean checkUserUrlAuth(String url);

	/**
	 * 只创建管理员
	 * 
	 * @param user
	 * @param admin
	 *            ：对应管理员的角色
	 * @return
	 * @throws YtoxlUserException
	 * */
	public void addUserNotCreateUserId(User user, Integer admin)
			throws YtoxlUserException;

	/**
	 * 从页面获取所有角色Id
	 * 
	 * @param user
	 * @param userIds
	 * @return
	 * @throws YtoxlUserException
	 * */
	public void saveUserUroles(User user, String uroleIds)
			throws YtoxlUserException;

	/**
	 * 找回密码
	 * 
	 * @param username
	 * @return String ：返回email
	 * @throws YtoxlUserException
	 * @throws TemplateException
	 * @throws IOException
	 * @throws MessagingException
	 * */
	public String updateForfindbackForMp(String username)
			throws YtoxlUserException, MessagingException, IOException,
			TemplateException;

	/**
	 * 查询用户，不包含登录用户：条件（用户名 ， 真实姓名，联系电话） 满足任一条件
	 * 
	 * @param userModelPage
	 * @return
	 * @throws YtoxlUserException
	 * */
	public void searchUsersByNameOrTel(BasePagination<User> userModelPage)
			throws YtoxlUserException;

	/**
	 * 激活用户，及其子用户，以及其所创建的所有角色
	 * 
	 * @param userId
	 * @return
	 * */
	public void updateAbleUserCascadeFromFreeze(Integer userId);

	/**
	 * 冻结用户，及其子用户，以及其所创建的所有角色
	 * 
	 * @param userId
	 * @return
	 * */
	public void updateFreezeUserCascadeFromAble(Integer userId);

	/**
	 * 查询用户，包含登录用户：条件（用户名 ， 真实姓名，联系电话，邮箱） 任一条件不为空，则需满足此条件
	 * 
	 * @param userModelPage
	 * @return
	 * */
	public void searchUsersByName(BasePagination<User> userModelPage)
			throws YtoxlUserException;

	/**
	 * 获取所有有效用户
	 * */
	public List<User> listAbleUser();

	/**
	 * 判断工牌号是否重复
	 * 
	 * @param employCard
	 *            工牌号
	 * @return boolean true :非重复 false:重复
	 * */
	public boolean repeatEmployCard(String employCard);

	/**
	 * 判断工牌号是否重复 去除用户Id，一般编辑时使用
	 * 
	 * @param employCard
	 *            用户名
	 * @param userId
	 *            用户Id
	 * @return boolean true :非重复 false:重复
	 * */
	public boolean repeatEmployCard(String employCard, Integer userId);

	/**
	 * 根据用户名称 更新密码 （注意，密码是明文还是密文，请在调用本方法之前做好转换）
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * */
	public boolean updatePswByUserName(String userName, String password);

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return
	 * */
	public void saveUserForSuixingou(User user) throws YtoxlUserException;

	/**
	 * 根据 uuid获取 uid （随心购 email=uuid）
	 * 
	 * @param user
	 * @return
	 * */
	public Integer selectUidByEmail(String uuid) throws YtoxlUserException;

	/**
	 * 根据用户名称 更新uuid（随心购 email=uuid）
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * */
	public boolean updateEmailByUserName(String userName, String email);
	
	/**
	 * 根据用户Id 查找qq登录的用户信息
	 * 
	 * @param userId
	 * @return user
	 */
	public User getUserByUserId(Integer userId)throws YtoxlUserException;

}
