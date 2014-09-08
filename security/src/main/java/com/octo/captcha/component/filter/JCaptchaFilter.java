package com.octo.captcha.component.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.xingyou5.module.user.CodeConstants;

/**
 * 针对 JCaptcha 专门的过滤器(Filter)
 * 
 * @author liukai
 * 
 */
public class JCaptchaFilter implements Filter {
	
	public static final String MPCHECKCODE = "1";//1:管理平台使用，验证码校验
	
	// 默认值定义
	public static final String DEFAULT_FILTER_PROCESSES_URL = "/j_spring_security_check";
	public static final String DEFAULT_CAPTCHA_PARAMTER_NAME = "j_captcha";

	private static Logger logger = LoggerFactory
			.getLogger(JCaptchaFilter.class);

	private String failureUrl;
	private String filterProcessesUrl = DEFAULT_FILTER_PROCESSES_URL;
	private CaptchaService captchaService;
	private String captchaParamterName = DEFAULT_CAPTCHA_PARAMTER_NAME;
	private String autoPassValue;
	private String mpCheckCode;

	

	/**
	 * Filter回调初始化函数.
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest theRequest,
			ServletResponse theResponse, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) theRequest;
		HttpServletResponse response = (HttpServletResponse) theResponse;
		String servletPath = request.getServletPath();
		// 符合filterProcessesUrl为验证处理请求,其余为生成验证图片请求.
		if (StringUtils.startsWith(servletPath, filterProcessesUrl)) {
			boolean validated = validateCaptchaChallenge(request);
			if (!validated) {
				request.getSession(true).setAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY, request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY));
				redirectFailureUrl(request, response);				
				return;
			}else{
				//记录登录用户名
				request.getSession(true).setAttribute(CodeConstants.LOGIN_ERROR_ONE, request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY));
			}
		}			
		chain.doFilter(request, response);		
	}

	/**
	 * Filter回调退出函数.
	 */
	public void destroy() {		

	}
	/**
	 * 验证验证码.
	 */
	protected boolean validateCaptchaChallenge(final HttpServletRequest request) {
		try {
			Object loginErrorTimes = request.getSession().getAttribute(CodeConstants.LOGIN_ERROR);
			String captchaID = request.getSession().getId();
			logger.debug("captchaID:" + captchaID);
			String challengeResponse = request
					.getParameter(captchaParamterName);
			logger.debug("challengeResponse:" + challengeResponse);
			// 自动通过值存在时,检验输入值是否等于自动通过值
			if (StringUtils.isNotBlank(autoPassValue)
					&& autoPassValue.equals(challengeResponse)) {
				return true;
			}
			if(MPCHECKCODE.equals(mpCheckCode)&&(loginErrorTimes==null || Integer.valueOf(loginErrorTimes.toString()) < 2))
				return true;
			return captchaService.validateResponseForID(captchaID,
					challengeResponse);
		} catch (CaptchaServiceException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 跳转到失败页面.
	 * 
	 * 可在子类进行扩展, 比如在session中放入SpringSecurity的Exception.
	 */
	protected void redirectFailureUrl(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		logger.debug("跳转到失败页面:" + request.getContextPath() + failureUrl);
		response.sendRedirect(request.getContextPath() + failureUrl);
	}
	
	

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public CaptchaService getCaptchaService() {
		return captchaService;
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	public String getCaptchaParamterName() {
		return captchaParamterName;
	}

	public void setCaptchaParamterName(String captchaParamterName) {
		this.captchaParamterName = captchaParamterName;
	}

	public String getAutoPassValue() {
		return autoPassValue;
	}

	public void setAutoPassValue(String autoPassValue) {
		this.autoPassValue = autoPassValue;
	}

	public String getMpCheckCode() {
		return mpCheckCode;
	}

	public void setMpCheckCode(String mpCheckCode) {
		this.mpCheckCode = mpCheckCode;
	}

}
