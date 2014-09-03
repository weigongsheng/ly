package com.xinyou5.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.xinyou5.front.vo.Message;

public class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {
	private static final long serialVersionUID = 1L;
	public static final String JSONMSG = "jsonMsg";
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected Message message;
	public void setMessage(String code, String info, String[] infoValues) {
		message = new Message(code, info, infoValues);
	}
	
	public Message getMessage() {
		return message;
	}

	@Override
	public void setServletResponse(HttpServletResponse resp) {
		response = resp;
	}

	@Override
	public void setServletRequest(HttpServletRequest req) {
		request = req;
	}
	
	protected void setMessage(String info) {
		setMessage(null, info, null);
	}
	
	protected void setMessage(String string, String string2) {
		setMessage(string, string2, null);
		
	}

}
