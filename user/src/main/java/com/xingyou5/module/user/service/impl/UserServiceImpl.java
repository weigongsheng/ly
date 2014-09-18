package com.xingyou5.module.user.service.impl;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xingyou5.module.base.util.Md5EncryptionUtils;
import com.xingyou5.module.user.dao.mapper.UroleMapper;
import com.xingyou5.module.user.dao.mapper.UserMapper;
import com.xingyou5.module.user.entity.Uresource;
import com.xingyou5.module.user.entity.Urole;
import com.xingyou5.module.user.entity.User;
import com.xingyou5.module.user.exception.XingYou5UserException;
import com.xingyou5.module.user.service.UresourceService;
import com.xingyou5.module.user.service.UserService;

import freemarker.template.TemplateException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper<User> userMapper;
	@Autowired
	private UroleMapper<Urole> uroleMapper;
	@Autowired
	private UresourceService uresourceService;
 
	@Value("${password_salt}")
	private String passwordSalt;
	@Value("${default_password}")
	private String defaultPassword;
	@Value("${mail.randompsw}")
	private Integer mailRandompsw;
	@Value("${mail.title}")
	private String mailTitle;
	 

	 
 

	@Override
	public Uresource getUserResource(Integer userId) {
		 
		return null;
	}

	@Override
	public boolean existName(String username) {
		Integer userId = userMapper.getUserIdByUserName(username);
		return userId != null && userId != 0 ;
	}

	@Override
	public boolean isDuplicateName(String username, Integer userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDulplicateEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDulplicateEmail(String email, Integer userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveUser(User user) throws XingYou5UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveUserUroles(User user, List<Integer> uroleIds) throws XingYou5UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUnableUserCascade(Integer userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDefaultPassword(Integer userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Urole> getCurrentUserCreateUrole() throws XingYou5UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uresource getCurrentUserMenu() throws XingYou5UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkPassword(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updatePswByUserId(Integer userId, String password) {
		String newPsw = Md5EncryptionUtils.MD5SaltPassword(password,
				passwordSalt);
		userMapper.updatePswByUserId(userId, newPsw);
	}

	@Override
	public void updateLastLoginTimeByUserId(Integer userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getByName(String userName) {
		return userMapper.getByName(userName);
	}

	@Override
	public boolean checkUserUrlAuth(String url) {
		 
		return false;
	}

	@Override
	public void saveUserUroles(User user, String uroleIds) throws XingYou5UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String updateForfindbackForMp(String username) throws XingYou5UserException, MessagingException,
			IOException, TemplateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePswByUserName(String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateEmailByUserName(String userName, String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getStatusByUsername(String valueOf) {
		// TODO Auto-generated method stub
		return null;
	}

}
