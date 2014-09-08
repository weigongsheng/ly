package com.xinyou5.front.web.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/***
 * <p> no cache control Interceptor For HTTP Response</p>
 * 
 * @author Jacobs Lei
 *
 */
@SuppressWarnings("serial")
public class NoResponseCacheInterceptor  extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setHeader("Cache-Control", "must-revalidate, no-cache, private");
		response.setHeader("Expires", "0");
		//For HTTP 1.0 backward
		response.setHeader("Pragma", "no-cache"); 
		return invocation.invoke();
	}
	
	

}
