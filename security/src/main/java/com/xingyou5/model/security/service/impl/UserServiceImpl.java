package com.xingyou5.model.security.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.xingyou5.model.base.util.Md5EncryptionUtils;
import com.xingyou5.model.base.util.StringUtils;
import com.xingyou5.model.base.web.BasePagination;
import com.xingyou5.model.security.CodeConstants;
import com.xingyou5.model.security.CustomInvocationSecurityMetadataSourceService;
import com.xingyou5.model.security.CustomUserDetails;
import com.xingyou5.model.security.dao.mapper.UroleMapper;
import com.xingyou5.model.security.dao.mapper.UserMapper;
import com.xingyou5.model.security.entity.Uresource;
import com.xingyou5.model.security.entity.Urole;
import com.xingyou5.model.security.entity.User;
import com.xingyou5.model.security.exception.YtoxlUserException;
import com.xingyou5.model.security.service.UresourceService;
import com.xingyou5.model.security.service.UserService;

import freemarker.template.TemplateException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper<User> userMapper;
	@Autowired
	private UroleMapper<Urole> uroleMapper;
	@Autowired
	private UresourceService uresourceService;
	@Autowired
	private CustomInvocationSecurityMetadataSourceService customSecurityMetadataSource;
	@Value("${password_salt}")
	private String passwordSalt;
	@Value("${default_password}")
	private String defaultPassword;
	@Value("${mail.randompsw}")
	private Integer mailRandompsw;
	@Value("${mail.title}")
	private String mailTitle;
	 

	/**
	 * 获取当前登录用户
	 * 
	 * */
	public CustomUserDetails getCurrentUser() throws YtoxlUserException {
		try {
			Object object = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			if (object instanceof CustomUserDetails) {
				CustomUserDetails userDetails = (CustomUserDetails) object;
				return userDetails;
			} else {
				throw new YtoxlUserException();
			}
		} catch (Exception e) {
			throw new YtoxlUserException(CodeConstants.E_USER_0001);// 没有登录;
		}

	}

	/**
	 * 分页查询用户信息
	 * 
	 * @param userModelPage
	 *            :（用户名username，用户状态status（0：停用，1：激活））
	 * @return
	 * @throws YtoxlUserException
	 * */
	 
	public void searchUsers(BasePagination<User> userModelPage)
			throws YtoxlUserException {

		Map<String, Object> searchParams = userModelPage.getSearchParamsMap();
		// 获取当前登录人的用户Id
		Integer userId = getCurrentUser().getUserId();
		// 获取当前登录人的所有子用户Id
		searchParams.put("createByUserIds", listUserIdByUserId(userId));
		if (userModelPage.isNeedSetTotal()) {
			Integer total = userMapper.searchUsersCount(searchParams);
			userModelPage.setTotal(total);
		}
		Collection<User> result = userMapper.searchUsers(searchParams);
		if (result != null) {
			for (User userModel : result) {
				Uresource menuModel = getUserMenu(userModel.getUserId());
				userModel.setMenuModel(menuModel);
			}
		}
		userModelPage.setResult(result);
	}

	/**
	 * 获取UserModel 包含用户角色 （此角色是用户拥有的角色，不包含创建的角色）
	 * 
	 * @param userId
	 * @return User
	 * */
	 
	public User getUserModelHave(Integer userId) {
		User user = userMapper.get(userId);
		if (user != null) {
			User userModel = new User(user);
			List<Urole> uroles = uroleMapper.listUrolesByUserId(userId);
			userModel.setSelectUroles(uroles);
			return userModel;
		}
		return null;
	}

	/**
	 * 获取当前用户角色
	 * 
	 * @return List<Urole>
	 * @throws YtoxlUserException
	 * */
	 
	public List<Urole> getCurrentUserCreateUrole() throws YtoxlUserException {
		CustomUserDetails customUser = getCurrentUser();
		List<Urole> createUroles = uroleMapper
				.listCreateUrolesByUserId(customUser.getUserId());
		return createUroles;
	}

	/**
	 * 获取UserModel 包含用户角色 （此角色是当前用户创建和用户拥有的角色）
	 * 
	 * @param userId
	 * @return User
	 * @throws YtoxlUserException
	 * */
	 
	public User getUserModel(Integer userId) throws YtoxlUserException {
		User user = userMapper.get(userId);
		if (user != null) {
			User userModel = new User(user);
			List<Urole> createUroles = uroleMapper
					.listCreateUrolesByUserId(getCurrentUser().getUserId());
			List<Urole> selectUroles = uroleMapper.listUrolesByUserId(userId);
			List<Urole> unSelectUroles = new ArrayList<Urole>();
			if (createUroles != null) {
				if (selectUroles != null) {
					for (Urole c : createUroles) {
						boolean flag = true;
						for (Urole s : selectUroles) {
							if (c.getUroleId().equals(s.getUroleId())) {
								flag = false;
								break;
							}
						}
						if (flag) {
							unSelectUroles.add(c);
						}
					}
					userModel.setUnSelectUroles(unSelectUroles);
					userModel.setSelectUroles(selectUroles);
				} else {
					userModel.setUnSelectUroles(createUroles);
				}
			}
			return userModel;
		}
		return null;
	}

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return
	 * @throws YtoxlUserException
	 * */
	 
	public void saveUser(User user) throws YtoxlUserException {
		try {
			if (StringUtils.isNotEmpty(user.getPassword())) {
				user.setPassword(Md5EncryptionUtils.MD5SaltPassword(
						user.getPassword(), passwordSalt));
			}
			if (user.getUserId() != null) {
				int c = userMapper.update(user);
				if (c == 0) {
					throw new YtoxlUserException(
							CodeConstants.E_USER_SAVE_FAILURE);
				}
			} else {
				if (repeatUsername(user.getUsername())) {
					Integer userId = getCurrentUser().getUserId();
					// 设置当前登录用户Id
					user.setCreateByUserId(userId);
					// 默认激活
					user.setStatus(User.STATUS_ABLE);
					if (!StringUtils.isNotEmptyTrim(user.getPassword())) {
						user.setPassword(Md5EncryptionUtils.MD5SaltPassword(
								defaultPassword, passwordSalt));
					}
					userMapper.add(user);
				} else {
					throw new YtoxlUserException(
							CodeConstants.E_USERNAME_REPEAT);
				}
			}
		} catch (Exception e) {
			throw new YtoxlUserException(e.getMessage());
		}
	}

	/**
	 * 保存用户信息 此处验证了重复密码
	 * 
	 * @param userModel
	 * @return
	 * @throws YtoxlUserException
	 * */
	 
	public void saveUserModel(User userModel) throws YtoxlUserException {
		String password = userModel.getPassword();
		String repeatPassword = userModel.getRepeatPassword();
		if (StringUtils.isNotEmpty(password)
				&& StringUtils.isNotEmpty(repeatPassword)
				&& password.equals(repeatPassword)) {
			saveUser(userModel);
		} else {
			throw new YtoxlUserException(CodeConstants.E_PASSWORD_DIFF);
		}
	}

	/**
	 * 保存用户角色
	 * 
	 * @param user
	 * @param uroleIds
	 * @return
	 * @throws YtoxlUserException
	 * */
	 
	public void saveUserUroles(User user, List<Integer> uroleIds)
			throws YtoxlUserException {
		saveUser(user);
		Integer userId = user.getUserId();
		// 因用户关联的角色不会太多，而且变动不会频繁。故此处直接删除原有的关联关系，再新增用户角色
		userMapper.delUserUroleByUserId(userId);
		if (uroleIds != null && !uroleIds.isEmpty()) {
			userMapper.addUserUroles(userId, uroleIds);
		}

	}

	/**
	 * 逻辑删除用户，及其子用户，以及其所创建的所有角色
	 * 
	 * @param userId
	 * @return
	 * */
	 
	public void updateUnableUserCascade(Integer userId) {
		// 获取关联的用户Id
		List<Integer> userIds = listUserIdByUserId(userId, User.STATUS_ABLE);
		if (userIds != null && !userIds.isEmpty()) {
			// 将用户状态禁用
			userMapper.updateStatusByUserIds(userIds,
					Integer.valueOf(User.STATUS_UNABLE));
			// 将用户创建的角色禁用
			uroleMapper.updateStatusByCuserIds(userIds,
					Integer.valueOf(Urole.STATUS_UNABLE));
			// 角色变动都需要刷新资源
			customSecurityMetadataSource.init();
		}
	}

	/**
	 * 重置密码，将密码重新设置为默认
	 * 
	 * @param userId
	 * @return
	 * */
	 
	public void updateDefaultPassword(Integer userId) {
		updatePswByUserId(userId, defaultPassword);
	}

	/**
	 * 判断用户名是否重复
	 * 
	 * @param username
	 *            用户名
	 * @return boolean true :非重复 false:重复
	 * */
	 
	public boolean repeatUsername(String username) {
		Integer userId = userMapper.getUserIdByUserName(username);
		if (userId != null && userId != 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断用户名是否重复 去除用户Id，一般编辑时使用
	 * 
	 * @param username
	 *            用户名
	 * @param userId
	 *            用户Id
	 * @return boolean true :非重复 false:重复
	 * */
	 
	public boolean repeatUsername(String username, Integer userId) {
		if (userId == null || userId == 0) {
			return repeatUsername(username);
		}

		Integer uId = userMapper.getUserIdByUserName(username);
		if (uId != null && uId != 0) {
			if (userId.equals(uId)) {
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * 验证用户密码一致性
	 * 
	 * @param username
	 * @param password
	 * @return boolean true:一致 false:不一致
	 * */
	 
	public boolean checkPassword(String username, String password) {
		if (!StringUtils.isNotEmptyTrim(username)
				|| !StringUtils.isNotEmptyTrim(password)) {
			return false;
		}
		User user = userMapper.getByName(username);
		if (user == null) {
			return false;
		}
		String needCheckPsw = Md5EncryptionUtils.MD5SaltPassword(password,
				passwordSalt);
		if (user.getPassword().equals(needCheckPsw)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据用户Id 更新密码
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * */
	 
	public void updatePswByUserId(Integer userId, String password) {
		String newPsw = Md5EncryptionUtils.MD5SaltPassword(password,
				passwordSalt);
		userMapper.updatePswByUserId(userId, newPsw);
	}

	/**
	 * 获取当前登录人的所有可访问资源
	 * 
	 * @return MenuModel
	 * @throws YtoxlUserException
	 * */
	 
	public Uresource getCurrentUserMenu() throws YtoxlUserException {
		return getUserMenu(getCurrentUser().getUserId());
	}

	/**
	 * 获取用户可访问菜单
	 * 
	 * @param userId
	 * @return MenuModel
	 * */
	 
	public Uresource getUserMenu(Integer userId) {
		List<Integer> roles = uroleMapper.listNormalUroleIdsByUserId(userId);
		Uresource allMenuModel = uresourceService.getAllMenuModel();
		return uresourceService.getMenuModel(allMenuModel, roles, false);
	}

	/**
	 * 通过用户Id 获取其所有子用户Id 以及自己 默认获取激活中的用户
	 * 
	 * @param userId
	 * @return list
	 * */
	 
	public List<Integer> listUserIdByUserId(Integer userId) {
		List<Integer> userIds = listChildUserIdByUserId(userId,
				User.STATUS_ABLE);
		if (userIds != null) {
			userIds.add(userId);
		}
		return userIds;
	}

	/**
	 * 通过状态 用户Id 获取其所有子用户Id
	 * 
	 * @param userId
	 * @return list
	 * */
	 
	public List<Integer> listChildUserIdByUserId(Integer userId) {

		List<Integer> userIds = userMapper.listUserIdByCUserId(userId,
				User.STATUS_ABLE);
		ArrayList<Integer> arrayList = new ArrayList<Integer>(userIds);

		if (userIds != null) {
			for (Integer tempUserId : userIds) {
				List<Integer> tempList = listChildUserIdByUserId(tempUserId);
				arrayList.addAll(tempList);
			}
		} else {
			return new ArrayList<Integer>();
		}
		return arrayList;
	}

	/**
	 * 通过用户Id 获取其所有子用户Id 以及自己
	 * 
	 * @param userId
	 * @return list
	 * */
	 
	public List<Integer> listUserIdByUserId(Integer userId, Integer status) {
		List<Integer> userIds = listChildUserIdByUserId(userId, status);
		if (userIds != null) {
			userIds.add(userId);
		}
		return userIds;
	}

	/**
	 * 通过状态 用户Id 获取其所有子用户Id
	 * 
	 * @param userId
	 * @return list
	 * */
	 
	public List<Integer> listChildUserIdByUserId(Integer userId, Integer status) {

		List<Integer> userIds = userMapper.listUserIdByCUserId(userId, status);
		ArrayList<Integer> arrayList = new ArrayList<Integer>(userIds);

		if (userIds != null) {
			for (Integer tempUserId : userIds) {
				List<Integer> tempList = listChildUserIdByUserId(tempUserId,
						status);
				arrayList.addAll(tempList);
			}
		} else {
			return new ArrayList<Integer>();
		}
		return arrayList;
	}

	/**
	 * 更新最近登录时间
	 * 
	 * @param userId
	 * @return
	 * */
	 
	public void updateLastLoginTimeByUserId(Integer userId) {
		userMapper.updateLastLoginTimeByUserId(userId);
	}

	/**
	 * 判断email是否重复
	 * 
	 * @param email
	 * @return boolean true :非重复 false:重复
	 * */
	public boolean repeatEmail(String email) {
		Integer userId = userMapper.getUserIdByEmail(email);
		if (userId != null && userId != 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断email是否重复 去除用户Id 一般编辑时使用
	 * 
	 * @param email
	 * @param userId
	 * @return boolean true :非重复 false:重复
	 * */
	 
	public boolean repeatEmail(String email, Integer userId) {
		if (userId == null || userId == 0) {
			return repeatEmail(email);
		}

		Integer uId = userMapper.getUserIdByEmail(email);
		if (uId != null && uId != 0) {
			if (userId.equals(uId)) {
				return true;
			}
			return false;
		}
		return true;
	}

	 
	public User getByName(String userName) {
		return userMapper.getByName(userName);
	}

	/**
	 * 根据username获取用户状态
	 * 
	 * @param username
	 * @return Integer
	 */
	 
	public Integer getStatusByUsername(String username) {
		return userMapper.getStatusByUsername(username);
	}

	/**
	 * 判断当前登录用户是否有某个url权限
	 * 
	 * @param url
	 * @return true :有 false： 否
	 * */
	 
	public boolean checkUserUrlAuth(String url) {
		// 如果URL为空 则不显示
		if (null != url && !"".equals(url)) {
			try {
				CustomUserDetails userDetails = getCurrentUser();
				if (userDetails != null) {
					Collection<GrantedAuthority> as = userDetails
							.getAuthorities();
					Collection<ConfigAttribute> coll = customSecurityMetadataSource
							.getAttributes(url);
					if (coll == null) {
						return true;
					}
					Iterator<ConfigAttribute> ite = coll.iterator();
					while (ite.hasNext()) {
						ConfigAttribute ca = ite.next();
						String needRole = ((SecurityConfig) ca).getAttribute();
						// ga 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。
						for (GrantedAuthority ga : as) {
							if (needRole.trim()
									.equals(ga.getAuthority().trim())) {
								return true;
							}
						}
					}
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 只创建管理员
	 * 
	 * @param user
	 * @param admin
	 *            ：对应管理员的角色Id
	 * @return
	 * @throws YtoxlUserException
	 * */
	 
	public void addUserNotCreateUserId(User user, Integer admin)
			throws YtoxlUserException {
		if (user == null) {
			throw new YtoxlUserException(CodeConstants.E_USER_ADDERROR);
		}
		if (user.getUserId() == null || user.getUserId() == 0) {
			if (user != null && StringUtils.isNotEmptyTrim(user.getUsername())) {
				if (repeatUsername(user.getUsername())) {
					// 默认激活
					user.setStatus(User.STATUS_ABLE);
					// 默认密码
					user.setPassword(Md5EncryptionUtils.MD5SaltPassword(
							defaultPassword, passwordSalt));
					userMapper.add(user);
					// 赋予角色
					Integer uroleId = admin;
					if (uroleId != null && uroleId != 0
							&& user.getUserId() != null
							&& user.getUserId() != 0) {
						List<Integer> uroleIds = new ArrayList<Integer>();
						uroleIds.add(uroleId);
						userMapper.addUserUroles(user.getUserId(), uroleIds);
						return;
					}
				}
			}
			throw new YtoxlUserException(CodeConstants.E_USER_ADDERROR);
		} else {
			userMapper.update(user);
		}
	}

	/**
	 * 从页面获取所有角色Id
	 * 
	 * @param user
	 * @param userIds
	 * @return
	 * @throws YtoxlUserException
	 * */
	 
	public void saveUserUroles(User user, String uroleIds)
			throws YtoxlUserException {
		if (uroleIds == null) {
			throw new YtoxlUserException(CodeConstants.E_PARAM_NOTCORRECT);
		}
		List<Integer> list = new ArrayList<Integer>();
		if (!uroleIds.equals("")) {
			String[] ids = uroleIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				list.add(Integer.parseInt(ids[i]));
			}
		}
		saveUserUroles(user, list);
	}

	/**
	 * 找回密码
	 * 
	 * @param username
	 * @return String : 返回email
	 * @throws YtoxlUserException
	 * @throws TemplateException
	 * @throws IOException
	 * @throws MessagingException
	 * */
	 
	public String updateForfindbackForMp(String username)
			throws YtoxlUserException, MessagingException, IOException,
			TemplateException {
//		User user = getByName(username);
//		if (user == null) {
//			throw new YtoxlUserException(CodeConstants.E_USERNAME_NOTFOUND);
//		}
//		String email = user.getEmail();
//		if (!StringUtils.isNotEmpty(email)) {
//			throw new YtoxlUserException(CodeConstants.E_EMAIL_NOTFOUND);
//		}
//		String newPsw = "";
//		// 生成随机密码
//		if (mailRandompsw != null) {
//			newPsw = RandomUtils.nextString(mailRandompsw);
//		}
//		// 更新密码
//		updatePswByUserId(user.getUserId(), newPsw);
//		// 发送数据
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("password", newPsw);
//		mailService.sendMail(email, "findback", data, mailTitle);
//		return email;
		return null;
		
	}

	/**
	 * 查询用户，不包含登录用户：条件（用户名 ， 真实姓名，联系电话） 满足任一条件
	 * 
	 * @param userModelPage
	 *            :（用户名username）
	 * @return
	 * @throws YtoxlUserException
	 * */
	 
	public void searchUsersByNameOrTel(BasePagination<User> userModelPage)
			throws YtoxlUserException {
		Map<String, Object> searchParams = userModelPage.getSearchParamsMap();
		// 获取当前登录人的用户Id
		Integer userId = getCurrentUser().getUserId();
		// 获取当前登录人的所有子用户Id
		List<Integer> userIds = listChildUserIdByUserId(userId);
		if (userIds != null && !userIds.isEmpty()) {
			searchParams.put("createByUserIds", userIds);
			if (userModelPage.isNeedSetTotal()) {
				Integer total = userMapper.searchUsersCount(searchParams);
				userModelPage.setTotal(total);
			}
			Collection<User> result = userMapper.searchUsers(searchParams);
			if (result != null) {
				for (User userModel : result) {
					List<Urole> re = uroleMapper.listUrolesByUserId(userModel
							.getUserId());
					userModel.setSelectUroles(re);
				}
			}
			userModelPage.setResult(result);
		}
	}

	/**
	 * 冻结用户，及其子用户，以及其所创建的所有角色
	 * 
	 * @param userId
	 * @return
	 * */
	 
	public void updateFreezeUserCascadeFromAble(Integer userId) {
		// 获取关联的用户Id
		List<Integer> userIds = listUserIdByUserId(userId, User.STATUS_ABLE);
		if (userIds != null && !userIds.isEmpty()) {
			// 将用户状态禁用
			userMapper.updateStatusByUserIds(userIds,
					Integer.valueOf(User.STATUS_FREEZE));
			// 将用户创建的角色禁用
			uroleMapper.updateStatusByCuserIds(userIds,
					Integer.valueOf(Urole.STATUS_UNABLE));
			// 角色变动都需要刷新资源
			customSecurityMetadataSource.init();
		}
	}

	/**
	 * 解冻用户，及其子用户，以及其所创建的所有角色
	 * 
	 * @param userId
	 * @return
	 * */
	 
	public void updateAbleUserCascadeFromFreeze(Integer userId) {
		// 获取关联的被冻结的用户Id
		List<Integer> userIds = listUserIdByUserId(userId, User.STATUS_FREEZE);
		if (userIds != null && !userIds.isEmpty()) {
			// 将用户状态启用
			userMapper.updateStatusByUserIds(userIds,
					Integer.valueOf(User.STATUS_ABLE));
			// 将用户创建的角色启用
			uroleMapper.updateStatusByCuserIds(userIds,
					Integer.valueOf(Urole.STATUS_ABLE));
			// 角色变动都需要刷新资源
			customSecurityMetadataSource.init();
		}
	}

	/**
	 * 查询用户，包含登录用户：条件（用户名 ， 真实姓名，联系电话，邮箱） 任一条件不为空，则需满足此条件
	 * 
	 * @param userModelPage
	 *            （username , operateName , tel , email）
	 * @return
	 * */
	 
	public void searchUsersByName(BasePagination<User> userModelPage)
			throws YtoxlUserException {

		Map<String, Object> searchParams = userModelPage.getSearchParamsMap();
		// 获取当前登录人的用户Id
		Integer userId = getCurrentUser().getUserId();
		// 获取当前登录人的用户Id,以及其所有子用户Id
		searchParams.put("createByUserIds", listUserIdByUserId(userId));
		if (userModelPage.isNeedSetTotal()) {
			Integer total = userMapper.searchByNameCount(searchParams);
			userModelPage.setTotal(total);
		}
		Collection<User> result = userMapper.searchByName(searchParams);
		if (result != null) {
			for (User userModel : result) {
				List<Urole> re = uroleMapper.listUrolesByUserId(userModel
						.getUserId());
				userModel.setSelectUroles(re);
			}
		}
		userModelPage.setResult(result);
	}

	/**
	 * 获取所有有效用户
	 * */
	 
	public List<User> listAbleUser() {
		return userMapper.listAbleUserModel();
	}

	/**
	 * 判断工牌号是否重复
	 * 
	 * @param employCard
	 *            工牌号
	 * @return boolean true :非重复 false:重复
	 * */
	 
	public boolean repeatEmployCard(String employCard) {
		Integer userId = userMapper.getAbleUserIdByemployCard(employCard);
		if (userId != null && userId != 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断工牌号是否重复 去除用户Id，一般编辑时使用
	 * 
	 * @param employCard
	 *            用户名
	 * @param userId
	 *            用户Id
	 * @return boolean true :非重复 false:重复
	 * */
	 
	public boolean repeatEmployCard(String employCard, Integer userId) {
		if (userId == null || userId == 0) {
			return repeatEmployCard(employCard);
		}

		Integer uId = userMapper.getAbleUserIdByemployCard(employCard);
		if (uId != null && uId != 0) {
			if (userId.equals(uId)) {
				return true;
			}
			return false;
		}
		return true;
	}

	 
	public boolean updatePswByUserName(String userName, String password) {

		int rows = userMapper.updatePswByUserName(userName, password);
		if (rows == 1) {
			return true;
		} else {
			return false;
		}
	}

	 
	public void saveUserForSuixingou(User user) throws YtoxlUserException {

		userMapper.add(user);

	}

	 
	public Integer selectUidByEmail(String uuid) throws YtoxlUserException {

		return userMapper.selectUidByEmail(uuid);
	}

	 
	public boolean updateEmailByUserName(String userName, String email) {
		int rows = userMapper.updateEmailByUserName(userName, email);
		if (rows == 1) {
			return true;
		}
		return false;
	}
	
	 
	public User getUserByUserId(Integer userId) throws YtoxlUserException {
		return userMapper.getUserByUserId(userId);
	}

}
