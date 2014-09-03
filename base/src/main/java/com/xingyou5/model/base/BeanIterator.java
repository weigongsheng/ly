package com.xingyou5.model.base;

import java.util.Collection;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class BeanIterator {
	@SuppressWarnings("hiding")
	public static <T> void iterateBean(Class<T> cla,IteratorCallback<T> callBack){
		Map<String,T>  allProc = WebApplicationContextUtils
				.getRequiredWebApplicationContext(
						ServletActionContext.getServletContext())
				.getBeansOfType(cla);
		if(allProc!= null && ! allProc.isEmpty()){
			Collection<T> procs = allProc.values();
			for (T data : procs) {
				callBack.iterator(data);
			}
		}
				 
	}
}
