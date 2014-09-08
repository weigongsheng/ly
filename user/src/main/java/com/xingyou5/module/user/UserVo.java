package com.xingyou5.module.user;

import java.util.List;

import com.xingyou5.module.user.entity.Urole;

public interface UserVo {
	Integer getUserId();
	String getUsername();
	String getPassword();
	List<Urole> getUroles();
}
