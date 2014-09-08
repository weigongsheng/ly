package com.xingyou5.module.security.service.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private boolean customAlwaysUseDefTargUrl;
    
	private RequestCache requestCache = new HttpSessionRequestCache();

	
	
	public CustomAuthenticationSuccessHandler() {
		super();
	}

	public CustomAuthenticationSuccessHandler(String defaultTargetUrl) {
		super(defaultTargetUrl);
	}


	protected boolean isAlwaysUseDefaultTargetUrl() {
		return customAlwaysUseDefTargUrl;
	} 
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        if (isAlwaysUseDefaultTargetUrl() || StringUtils.hasText(request.getParameter(getTargetUrlParameter()))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
    
    
    public boolean isCustomAlwaysUseDefTargUrl() {
    	return customAlwaysUseDefTargUrl;
    }
    
    public void setCustomAlwaysUseDefTargUrl(boolean customAlwaysUseDefTargUrl) {
    	this.customAlwaysUseDefTargUrl = customAlwaysUseDefTargUrl;
    }
}
