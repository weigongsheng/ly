package com.xingyou5.model.user.service;

import com.xingyou5.model.user.entity.User;

public interface UserChangeNotifierService {
	void onChange(User user);
}
