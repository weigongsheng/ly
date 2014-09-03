package com.xingyou5.model.base;

import java.util.Collection;
import java.util.Map;

public class BeanIterator {
	public static <T> void iterateBean(Class<T> cla,IteratorCallback<T> callBack){
		Map<String,T>  allProc =  SpringContextUtils.getContext().getBeansOfType(cla);
		if(allProc!= null && ! allProc.isEmpty()){
			Collection<T> procs = allProc.values();
			for (T data : procs) {
				callBack.iterator(data);
			}
		}
				 
	}
}
