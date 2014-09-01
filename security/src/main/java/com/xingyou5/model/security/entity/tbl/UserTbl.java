package com.xingyou5.model.security.entity.tbl;

import java.io.Serializable;
import java.sql.Timestamp;


public class UserTbl implements Serializable{
	private Integer userId;
	private String username;
	private String password;
	private String operateName;
	private String tel;
	private String email;
	private String employCard;
	private Integer status;
	private Integer createByUserId;
	private Timestamp lastLoginTime;
	private Timestamp createTime;
	private Timestamp updateTime;
	
	private String nickName;
	private Integer mark;
	
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	public Integer getCreateByUserId() {
		return createByUserId;
	}
	public void setCreateByUserId(Integer createByUserId) {
		this.createByUserId = createByUserId;
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
	
	public String getEmployCard() {
		return employCard;
	}
	public void setEmployCard(String employCard) {
		this.employCard = employCard;
	}
	public UserTbl() {
		super();
	}
	public UserTbl(Integer userId, String username, String password,
			String operateName, String tel, String email, String employCard,
			Integer status, Integer createByUserId, Timestamp lastLoginTime,
			Timestamp createTime, Timestamp updateTime) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.operateName = operateName;
		this.tel = tel;
		this.email = email;
		this.employCard = employCard;
		this.status = status;
		this.createByUserId = createByUserId;
		this.lastLoginTime = lastLoginTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public UserTbl(Integer userId, String username, String password,
			String operateName, String tel, String email, String employCard,
			Integer status, Integer createByUserId, Timestamp lastLoginTime,
			Timestamp createTime, Timestamp updateTime,String nickName) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.operateName = operateName;
		this.tel = tel;
		this.email = email;
		this.employCard = employCard;
		this.status = status;
		this.createByUserId = createByUserId;
		this.lastLoginTime = lastLoginTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.nickName = nickName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
