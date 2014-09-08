package com.xingyou5.module.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginSuccessBeforeFilter implements Filter {

	public final static String USER_LOGIN_FLAG = "USER_LOGIN_FLAG";
	private String filterProcessesUrl;

	public UserLoginSuccessBeforeFilter() {
		this.filterProcessesUrl = "/j_spring_security_check";
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (requiresAuthentication(request, response)) {
			request.getSession().setAttribute(USER_LOGIN_FLAG, true);
		}
		chain.doFilter(request, response);
	}
	
	protected boolean requiresAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');
		
		if (pathParamIndex > 0) {
			// strip everything after the first semi-colon
			uri = uri.substring(0, pathParamIndex);
		}
		
		if ("".equals(request.getContextPath())) {
			return uri.endsWith(filterProcessesUrl);
		}
		
		return uri.endsWith(request.getContextPath() + filterProcessesUrl);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

}
