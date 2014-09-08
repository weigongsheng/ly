package com.xinyou5.front.web.action;

import java.util.ArrayList;
import java.util.List;

import com.xinyou5.front.action.BaseAction;
/** 基本action用于暂时login
 * @author  
 *
 */
/**
 * @author admin
 *
 */
public class IndexAction extends BaseAction{ 
	
	private static final long serialVersionUID = 4197617264075560936L;
	private List advList;

	/**首页 登陆成功
 	 * @return
	 */
	public String index(){
		advList = new ArrayList<String>();
		advList.add("one");
		advList.add("two");
		advList.add("three");
		 return SUCCESS;
	}

	public List getAdvList() {
		return advList;
	}
	
	
	
	
}
