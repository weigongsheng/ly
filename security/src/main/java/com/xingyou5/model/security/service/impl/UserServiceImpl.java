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
import com.xingyou5.model.security.exception.XingYou5UserException;
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
	public CustomUserDetails getCurrentUser() throws XingYou5UserException {
		try {
			Object object = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			if (object instanceof CustomUserDetails) {
				CustomUserDetails userDetails = (CustomUserDetails) object;
				return userDetails;
			} else {
				throw new XingYou5UserException();
			}
		} catch (Exception e) {
			throw new XingYou5UserException(CodeConstants.E_USER_0001);// 没有登录;
		}

	}

 
 

	/**
	 * 获取当前用户角色
	 * 
	 * @return List<Urole>
	 * @throws XingYou5UserException
	 * */
	 
	public List<Urole> getCurrentUserCreateUrole() throws XingYou5UserException {
		CustomUserDetails customUser = getCurrentUser();
		List<Urole> createUroles = uroleMapper
				.listCreateUrolesByUserId(customUser.getUserId());
		return createUroles;
	}

	 
	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return
	 * @throws XingYou5UserException
	 * */
	 
	public void saveUser(User user) throws XingYou5UserException {
		try {
			if (StringUtils.isNotEmpty(user.getPassword())) {
				user.setPassword(Md5EncryptionUtils.MD5SaltPassword(
						user.getPassword(), passwordSalt));
			}
			if (user.getUserId() != null) {
				int c = userMapper.update(user);
				if (c == 0) {
					throw new XingYou5UserException(
							CodeConstants.E_USER_SAVE_FAILURE);
				}
			} else {
				if (isDuplicateName(user.getUsername())) {
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
					throw new XingYou5UserException(
							CodeConstants.E_USERNAME_REPEAT);
				}
			}
		} catch (Exception e) {
			throw new XingYou5UserException(e.getMessage());
		}
	}

	 

	/**
	 * 保存用户角色
	 * 
	 * @param user
	 * @param uroleIds
	 * @return
	 * @throws XingYou5UserException
	 * */
	 
	public void saveUserUroles(User user, List<Integer> uroleIds)
			throws XingYou5UserException {
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
		List<Integer> userIds = new ArrayList<Integer>();
		userIds.add(userId);
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
	 
	public boolean isDuplicateName(String username) {
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
	 
	public boolean isDuplicateName(String username, Integer userId) {
		if (userId == null || userId == 0) {
			return isDuplicateName(username);
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
	 * @throws XingYou5UserException
	 * */
	 
	public Uresource getCurrentUserMenu() throws XingYou5UserException {
		return getUserResource(getCurrentUser().getUserId());
	}

	/**
	 * 获取用户可访问菜单
	 * 
	 * @param userId
	 * @return MenuModel
	 * */
	 
	public Uresource getUserResource(Integer userId) {
		List<Integer> roles = uroleMapper.listNormalUroleIdsByUserId(userId);
		Uresource allMenuModel = uresourceService.getAllMenuModel();
		return uresourceService.getMenuModel(allMenuModel, roles, false);
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
	public boolean isDulplicateEmail(String email) {
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
	 
	public boolean isDulplicateEmail(String email, Integer userId) {
		if (userId == null || userId == 0) {
			return isDulplicateEmail(email);
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
	 * 从页面获取所有角色Id
	 * 
	 * @param user
	 * @param userIds
	 * @return
	 * @throws XingYou5UserException
	 * */
	 
	public void saveUserUroles(User user, String uroleIds)
			throws XingYou5UserException {
		if (uroleIds == null) {
			throw new XingYou5UserException(CodeConstants.E_PARAM_NOTCORRECT);
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
	 * @throws XingYou5UserException
	 * @throws TemplateException
	 * @throws IOException
	 * @throws MessagingException
	 * */
	 
	public String updateForfindbackForMp(String username)
			throws XingYou5UserException, MessagingException, IOException,
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



	 
	public boolean updatePswByUserName(String userName, String password) {

		int rows = userMapper.updatePswByUserName(userName, password);
		if (rows == 1) {
			return true;
		} else {
			return false;
		}
	}

	 
	 
	public boolean updateEmailByUserName(String userName, String email) {
		int rows = userMapper.updateEmailByUserName(userName, email);
		if (rows == 1) {
			return true;
		}
		return false;
	}

}
