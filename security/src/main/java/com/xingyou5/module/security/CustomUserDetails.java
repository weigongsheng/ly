package com.xingyou5.module.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.xingyou5.module.user.UserVo;
import com.xingyou5.module.user.entity.Urole;



public class CustomUserDetails implements UserDetails, Serializable,UserVo {
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String username;
	private String password;
	private String operateName;
	private String tel;
	private String email;
	private String employCard;
	private Integer adminUserId;// 创建用户的祖先用户Id
	private List<Urole> uroles = new ArrayList<Urole>();
	
	private String nickName;

	public CustomUserDetails() {
	}

	/**
	 * 获取用户权限集合,权限使用GrantedAuthority接口表示
	 */
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		for (Urole urole : uroles) {
			list.add(new GrantedAuthorityImpl(String.valueOf(urole.getUroleId())));
		}
		return list;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Urole> getUroles() {
		return uroles;
	}

	public void setUroles(List<Urole> uroles) {
		this.uroles = uroles;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Integer adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getEmployCard() {
		return employCard;
	}

	public void setEmployCard(String employCard) {
		this.employCard = employCard;
	}

	/**
	 * 直接返回true，表示没有过期 (non-Javadoc)
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 直接返回true，表示没有锁定 (non-Javadoc)
	 */
	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 是否禁用 (non-Javadoc)
	 */
	public boolean isEnabled() {
		return true;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
