package com.xingyou5.module.user.entity.tbl;

import java.io.Serializable;

public class UresourceTbl implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer uresourceId;
	private String uresourceName;
	private String url;
	private Integer status;
    private Integer isMenu; 
    private Integer rank;
    private Integer parentId;
	public Integer getUresourceId() {
		return uresourceId;
	}
	public void setUresourceId(Integer uresourceId) {
		this.uresourceId = uresourceId;
	}
	public String getUresourceName() {
		return uresourceName;
	}
	public void setUresourceName(String uresourceName) {
		this.uresourceName = uresourceName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public UresourceTbl(Integer uresourceId, String uresourceName, String url,
			Integer status, Integer isMenu, Integer rank, Integer parentId) {
		super();
		this.uresourceId = uresourceId;
		this.uresourceName = uresourceName;
		this.url = url;
		this.status = status;
		this.isMenu = isMenu;
		this.rank = rank;
		this.parentId = parentId;
	}
	public UresourceTbl() {
		super();
	}

}
