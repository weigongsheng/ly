package com.xingyou5.model.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xingyou5.model.security.CustomUserDetails;
import com.xingyou5.model.user.dao.mapper.UroleMapper;
import com.xingyou5.model.user.dao.mapper.UserMapper;
import com.xingyou5.model.user.entity.Urole;
import com.xingyou5.model.user.entity.User;


@Service("customUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserMapper<User> userMapper;
	@Autowired
	private UroleMapper<Urole> uroleMapper;
	/**
	 * 通过用户名查找用户信息
	 * @param userName
	 */
	@Override  
    public  UserDetails loadUserByUsername(String userName)  throws UsernameNotFoundException, DataAccessException{  
          
    	User user = userMapper.getAbleUserByName(userName);  
        if (user == null) {  
            throw new UsernameNotFoundException("用户名" + userName + "不存在");  
        }
        //查找祖先用户Id
        Integer createByUserId = user.getCreateByUserId();
        Integer adminUserId = createByUserId;
        boolean flag = true;
    	while(adminUserId!=null&&flag){
    		Integer tempCreateUserId = userMapper.getCreateUserIdByUserId(adminUserId);
    		if(tempCreateUserId==null||tempCreateUserId==0){
    			flag = false;
    		}else{
    			adminUserId = tempCreateUserId;
    		}
    	}
    	//祖先用户Id为空，则设置当前用户Id
    	adminUserId = adminUserId==null||adminUserId==0?user.getUserId():adminUserId;
        List<Urole> uroles = uroleMapper.listNormalUroleByUserId(user.getUserId());
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUserId(user.getUserId());
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setEmail(user.getEmail());
        userDetails.setOperateName(user.getOperateName());
        userDetails.setTel(user.getTel());
        userDetails.setEmployCard(user.getEmployCard());
        userDetails.setUroles(uroles);
        userDetails.setAdminUserId(adminUserId);
        userDetails.setNickName(user.getNickName());
        // 返回CustomUserDetails
        return userDetails;  
    }  

}
