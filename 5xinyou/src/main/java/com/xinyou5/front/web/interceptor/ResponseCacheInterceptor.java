package com.xinyou5.front.web.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/***
 * 
 * <p> cache control Interceptor For HTTP Response,default cache time : 30 minutes</p>
 * 
 * @author Jacobs Lei
 *
 */
@SuppressWarnings("serial")
public class ResponseCacheInterceptor extends AbstractInterceptor{

	/* 缓存时间,单位分钟 */
	private long cacheTime = Default_Cache_Time;
	
	private static final long Default_Cache_Time = 30;

	public long getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(long cacheTime) {
		if (cacheTime > 1)
			this.cacheTime = cacheTime;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);

		long currentTimeMillis = System.currentTimeMillis();

		response.setHeader("Cache-Control", "private,max-age=" + cacheTime * 60);

		response.setDateHeader("Expires", currentTimeMillis + 1000 * 60	* cacheTime);

		response.setDateHeader("Last-Modified", currentTimeMillis);

		return invocation.invoke();
	}
}
