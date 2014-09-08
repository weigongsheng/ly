 
package com.xingyou5.module.security.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @description 描述该类功能
 * @author 何碧波<a href="mailto:hebibo@ytoxl.com">hebibo@ytoxl.com</a>
 * @date 2013-12-9
 */
public class ResumeSessionFilter implements Filter {
	Log logger = LogFactory.getLog(ResumeSessionFilter.class);

	@Override
	public void destroy() {
		 
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
			chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	private String getJsessionId(Cookie[] cookies) {
		String jsessionId = "";
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("JSESSIONID")) {
					jsessionId = cookie.getValue();
					break;
				}
			}
		}
		return jsessionId;
	}

}
