package com.xingyou5.module.user.entity.tbl;

import java.io.Serializable;
import java.sql.Timestamp;

public class UroleTbl implements Serializable{
	private Integer uroleId;
	private String uroleName;
	private Integer status;
	private Integer createByUserId;
	private Timestamp createTime;

	public Integer getUroleId() {
		return uroleId;
	}

	public void setUroleId(Integer uroleId) {
		this.uroleId = uroleId;
	}

	public String getUroleName() {
		return uroleName;
	}

	public void setUroleName(String uroleName) {
		this.uroleName = uroleName;
	}

	public UroleTbl() {
		super();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreateByUserId() {
		return createByUserId;
	}

	public void setCreateByUserId(Integer createByUserId) {
		this.createByUserId = createByUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
