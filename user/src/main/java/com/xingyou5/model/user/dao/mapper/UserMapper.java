package com.xingyou5.model.user.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xingyou5.model.base.dao.mapper.BaseSqlMapper;
import com.xingyou5.model.user.entity.User;

 
public interface UserMapper<T extends User> extends BaseSqlMapper<T> {

	/**
	 * 根据username获取userId
	 * 
	 * @param username
	 * @return
	 */
	public Integer getUserIdByUserName(@Param("username") String username);

	/**
	 * 通过用户名查找用户信息
	 * 
	 * @param userName
	 */
	public User getByName(String userName);

	/**
	 * 通过用户名查找服务中的用户信息
	 * 
	 * @param userName
	 * @return user
	 */
	public User getAbleUserByName(String userName);

	/**
	 * 查询用户
	 * 
	 * @param map
	 *            （用户名username，用户状态status（0：停用，1：激活））
	 * @return list
	 * */
	public List<User> searchUsers(Map<String, Object> map);

	public Integer searchUsersCount(Map<String, Object> map);

	/**
	 * 通过状态 创建用户Id 获取其子用户Id
	 * 
	 * @param userId
	 * @param status
	 * @return list
	 * */
	public List<Integer> listUserIdByCUserId(@Param("userId") Integer userId,
			@Param("status") Integer status);

	/**
	 * 删除指定用户的用户角色
	 * 
	 * @param userId
	 * @return
	 * */
	public void delUserUroleByUserId(Integer userId);

	/**
	 * 批量新增用户角色
	 * 
	 * @param userId
	 * @param uroleIds
	 * @return
	 * */
	public void addUserUroles(@Param("userId") Integer userId,
			@Param("uroleIds") List<Integer> uroleIds);

	/**
	 * 根据用户Ids 更新用户状态
	 * 
	 * @param userIds
	 * @param status
	 * @return
	 * */
	public void updateStatusByUserIds(@Param("userIds") List<Integer> userIds,
			@Param("status") Integer status);

	/**
	 * 根据用户Id 更新密码
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * */
	public void updatePswByUserId(@Param("userId") Integer userId,
			@Param("password") String password);

	/**
	 * 更新最近登录时间
	 * 
	 * @param userId
	 * @return
	 * */
	public void updateLastLoginTimeByUserId(Integer userId);

	/**
	 * 根据email获取userId
	 * 
	 * @param email
	 * @return
	 */
	public Integer getUserIdByEmail(String email);

	/**
	 * 根据userId获取创建用户Id
	 * 
	 * @param userId
	 * @return Integer
	 */
	public Integer getCreateUserIdByUserId(Integer userId);

	/**
	 * 根据username获取用户状态
	 * 
	 * @param username
	 * @return Integer
	 */
	public Integer getStatusByUsername(String username);

	/**
	 * 查询用户
	 * 
	 * @param map
	 *            （用户名username,用户真实姓名operateName
	 * @return list
	 * */
	public List<User> searchByName(Map<String, Object> map);

	public Integer searchByNameCount(Map<String, Object> map);

	/**
	 * 获取所有有效用户
	 * 
	 * @return List
	 * */
	public List<User> listAbleUserModel();

	/**
	 * 根据employCard获取有效的userId
	 * 
	 * @param employCard
	 * @return
	 */
	public Integer getAbleUserIdByemployCard(
			@Param("employCard") String employCard);

	/**
	 * 根据username 更新密码
	 * 
	 * @param username
	 * @param password
	 * @return
	 * */
	public Integer updatePswByUserName(@Param("userName") String userName,
			@Param("password") String password);

	/**
	 * 根据 pwd获取 uid （随心购 email=uid）
	 * 
	 * @param user
	 * @return
	 * */
	public Integer selectUidByEmail(@Param("uuid") String uuid);

	/**
	 * 根据用户名称 更新uuid（随心购 email=uid）
	 * 
	 * @param user
	 * @return
	 * */
	public Integer updateEmailByUserName(@Param("userName") String userName,
			@Param("email") String email);
	
	/**
	 * 根据用户Id 查找qq登录的用户信息
	 * 
	 * @param userId
	 */
	public User getUserByUserId(@Param("userId")Integer userId);
}
