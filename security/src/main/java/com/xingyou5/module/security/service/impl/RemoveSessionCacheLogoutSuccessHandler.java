package com.xingyou5.module.security.service.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;


/**
 * @description 描述该类功能
 * @author 何碧波<a href="mailto:hebibo@ytoxl.com">hebibo@ytoxl.com</a>
 * @date 2013-12-4
 */
 
public class RemoveSessionCacheLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

	private String logoutSuccessUrl;
	
	/**
	 * @param logoutSuccessUrl the logoutSuccessUrl to set
	 */
	public void setLogoutSuccessUrl(String logoutSuccessUrl) {
		this.logoutSuccessUrl = logoutSuccessUrl;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (StringUtils.hasText(logoutSuccessUrl)) {
	            this.setDefaultTargetUrl(logoutSuccessUrl);
	       }
		super.handle(request, response, authentication);
		return ;
	}

 
}
