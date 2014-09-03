package com.xingyou5.model.security.service.impl;

 

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.xingyou5.model.security.CustomUserDetails;
import com.xingyou5.model.security.service.SecurityUserService;
import com.xingyou5.model.user.CodeConstants;
import com.xingyou5.model.user.exception.XingYou5UserException;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {
 
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

 
  

}
