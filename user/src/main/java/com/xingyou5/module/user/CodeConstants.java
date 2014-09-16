package com.xingyou5.module.user;

public class CodeConstants {
	public static final String INFO_CODE_PRE = "I_";//页面显示
	public static final String EXCEPTION_CODE_PRE = "E_";//程序中使用
	
	
	
	public static final String E_USER_0001 = "E_USER_0001";//没有登录
	
	public static final String I_USER_SAVE_SUCCESS = "I_USER_SAVE_SUCCESS";//用户保存成功
	public static final String E_USER_SAVE_FAILURE = "E_USER_SAVE_FAILURE";//用户保存失败
	
	public static final String I_UROLE_SAVE_SUCCESS = "I_UROLE_SAVE_SUCCESS";//用户保存成功
	public static final String I_UROLE_SAVE_FAILURE = "I_UROLE_SAVE_FAILURE";//用户保存失败
	
	public static final String E_PASSWORD_DIFF ="E_PASSWORD_DIFF";//密码不一致
	public static final String E_USER_ADDERROR ="E_USER_ADDERROR";//新增用户出错
	public static final String E_USERNAME_REPEAT ="E_USERNAME_REPEAT";//重复用户名
	public static final String I_USERNAME_REPEAT ="I_USERNAME_REPEAT";//重复用户名
	public static final String I_EMAIL_REPEAT ="I_EMAIL_REPEAT";//重复email
	public static final String E_UROLENAME_REPEAT ="E_UROLENAME_REPEAT";//重复角色名
	public static final String I_UROLENAME_REPEAT ="I_UROLENAME_REPEAT";//重复角色名
	public static final String E_PARAM_NOTCORRECT ="E_PARAM_NOTCORRECT";//参数不正确
	
	public static final String I_INDEX_MSG="I_INDEX_MSG"; //请选择查询条件进行查询
	public static final String I_SEARCH_MSG="I_SEARCH_MSG"; //找不到内容
	
	public static final String LOGIN_ERROR = "LOGIN_ERROR";//登录失败记录session
	public static final String LOGIN_ERROR_ONE = "LOGIN_ERROR_ONE";//登录失败记录session1
	
	public static final String E_USERNAME_NOTFOUND = "E_USERNAME_NOTFOUND";//用户名不存在
	public static final String E_EMAIL_NOTFOUND = "E_EMAIL_NOTFOUND";//EMAIL不存在
	public static final String I_SEND_EMAIL_SUCCESS = "I_SEND_EMAIL_SUCCESS";//发送EMAIL成功
	public static final String I_SEND_EMAIL_FAILURE = "I_SEND_EMAIL_FAILURE";//发送EMAIL失败，未知错误
	public static final String I_SEND_CODECHECK_FAILURE = "I_SEND_CODECHECK_FAILURE";//验证码错误
	
	
	public static void main(String[] args) {
		boolean f=true;
		if(f) 
			System.out.println();
		 
	}
}
