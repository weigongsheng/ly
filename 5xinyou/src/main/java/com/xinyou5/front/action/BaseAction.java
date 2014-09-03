package com.xinyou5.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ytoxl.yipin.web.bean.Message;

public class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {
	private static final long serialVersionUID = 1L;
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected String message;
	public void setMessage(String code, String info, String[] infoValues) {
		message = new Message(code, info, infoValues);
	}
	
	@Override
	public void setServletResponse(HttpServletResponse resp) {
		response = resp;
	}

	@Override
	public void setServletRequest(HttpServletRequest req) {
		request = req;
	}

}
