package com.xingyou5.module.security.service.impl;

 

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.xingyou5.module.user.UserVo;
import com.xingyou5.module.user.exception.XingYou5UserException;
import com.xingyou5.module.user.service.SecurityUserService;

 

@Service
public class SecurityUserServiceImpl implements SecurityUserService {
 
	/**
	 * 获取当前登录用户
	 * 
	 * */
	public UserVo getCurrentUser()  {
		try {
			Object object = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			if (object instanceof UserVo) {
				 return  (UserVo) object;
			} else {
				throw new XingYou5UserException();
			}
		} catch (Exception e) {
			return null;
		}

	}

 
  

}
