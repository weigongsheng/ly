package com.xingyou5.module.user.service;

import com.xingyou5.module.user.entity.User;

public interface UserChangeNotifierService {
	void onChange(User user);
}
