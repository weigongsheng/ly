package com.xingyou5.module.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xingyou5.module.security.service.impl.CustomInvocationSecurityMetadataSourceService;
import com.xingyou5.module.user.CodeConstants;
import com.xingyou5.module.user.UserVo;
import com.xingyou5.module.user.entity.Uresource;
import com.xingyou5.module.user.entity.Urole;
import com.xingyou5.module.user.entity.User;
import com.xingyou5.module.user.service.SecurityUserService;
import com.xingyou5.module.user.service.UresourceService;
import com.xingyou5.module.user.service.UserService;

public class UserLoginSuccessAfterFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(UserLoginSuccessAfterFilter.class);
	
	private static final String LOGIN_ERROR_4 ="4"; //用户冻结跳转
	private static final String LOGIN_ERROR_5 ="5"; //用户删除跳转
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityUserService securityService;
	@Autowired
	private UresourceService uresourceService;
	private String failureUrl;
	private String filterProcessesUrl;
    private String redirectUrl;
   
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		  HttpServletRequest request = (HttpServletRequest) req;
	      HttpServletResponse response = (HttpServletResponse) res;

	      Object flag = request.getSession().getAttribute(UserLoginSuccessBeforeFilter.USER_LOGIN_FLAG);
	      if(flag!=null && flag.equals(true)){
	    	  try {
					UserVo userDetails = securityService.getCurrentUser();
					//设置菜单
					Uresource menuDetail = CustomInvocationSecurityMetadataSourceService.getAllMenuModel();
					List<Urole> uroles = userDetails.getUroles();
					if(uroles!=null){
						List<Integer> roles = new ArrayList<Integer>();
						for(Urole urole:uroles){
							roles.add(urole.getUroleId());
						}
						Uresource newMenuDetail = uresourceService.getMenuModel(menuDetail, roles,true);
						request.getSession().setAttribute("canVisitMenu", newMenuDetail);
						//更新登录时间
						userService.updateLastLoginTimeByUserId(userDetails.getUserId());
					}
					
					//登录成功移出登录失败的session
					Object loginErrorTimes = request.getSession().getAttribute(CodeConstants.LOGIN_ERROR);
					if(loginErrorTimes != null) {
						request.getSession().removeAttribute(CodeConstants.LOGIN_ERROR);
					}
				} catch (Exception e) {
					//logger.error(e.getMessage());
					int times = 0;
					Object loginErrorTimes = request.getSession().getAttribute(CodeConstants.LOGIN_ERROR);
					if(loginErrorTimes == null) {
						request.getSession().setAttribute(CodeConstants.LOGIN_ERROR, times);
					} else {
						times = Integer.valueOf(loginErrorTimes.toString());
						times ++;
						request.getSession().setAttribute(CodeConstants.LOGIN_ERROR, times);
					}
					
					//用户名出错，运行下面
					Object username_repeat = request.getSession().getAttribute(CodeConstants.LOGIN_ERROR_ONE);
					if(username_repeat!=null){
						request.getSession().removeAttribute(CodeConstants.LOGIN_ERROR_ONE);
						if(!userService.existName(String.valueOf(username_repeat))){
							response.sendRedirect(request.getContextPath() + failureUrl);
						}else{
							Integer status = userService.getStatusByUsername(String.valueOf(username_repeat));
							if(status!=null){
								if(status==User.STATUS_FREEZE){
									String error4 = failureUrl.substring(0,failureUrl.length()-1)+LOGIN_ERROR_4;
									response.sendRedirect(request.getContextPath() + error4);
								}else if(status==User.STATUS_UNABLE){
									String error5 = failureUrl.substring(0,failureUrl.length()-1)+LOGIN_ERROR_5;
									response.sendRedirect(request.getContextPath() + error5);
								}
							}
						}
					}
				}
				request.getSession().removeAttribute(UserLoginSuccessBeforeFilter.USER_LOGIN_FLAG);
	      }else{
	    	  //过滤首页url，session为空，则返回登录页
	    	  if (requiresAuthentication(request, response)) {
	    		  try {
					  securityService.getCurrentUser();
				  } catch (Exception e) {
					  response.sendRedirect(request.getContextPath() + redirectUrl);
					  return;
				  }
	  		  }
	      }
          chain.doFilter(request, response);
	}

	public UserLoginSuccessAfterFilter() {
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
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

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}
