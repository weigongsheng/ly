package com.xinyou5.front.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.google.code.jcaptcha4struts2.core.validation.JCaptchaValidator;
import com.opensymphony.oscache.util.StringUtil;
import com.taobao.api.internal.util.WebUtils;
import com.xingyou5.module.base.SmsSendBean;
import com.xingyou5.module.base.exception.BaseException;
import com.xingyou5.module.base.util.CheckRegex;
import com.xingyou5.module.base.util.CookieUtils;
import com.xingyou5.module.base.util.EncodeUtils;
import com.xingyou5.module.base.util.Md5EncryptionUtils;
import com.xingyou5.module.base.util.StringUtils;
import com.xingyou5.module.user.UserInfoService;
import com.xingyou5.module.user.UserVo;
import com.xingyou5.module.user.entity.User;
import com.xingyou5.module.user.entity.UserInfo;
import com.xingyou5.module.user.exception.UserException;
import com.xingyou5.module.user.service.SecurityUserService;
import com.xingyou5.module.user.service.UroleService;
import com.xingyou5.module.user.service.UserService;
import com.xinyou5.front.action.BaseAction;

/**
 * UserAction用户
 * 
 * @author user
 * 
 */
@SuppressWarnings("restriction")
public class UserAction extends BaseAction {
	/**	 */
	private static final long serialVersionUID = -7725629863752967951L;
	private static Logger logger = LoggerFactory.getLogger(UserAction.class);
	private static final String user_stuas = "0";
	private static final String Status_deniy = "1";// 账号被禁用被禁用 、用户的账号重复
	private static final String pass_img_deniy = "3";// 验证码不正确
	private static final String pass_img_access = "4";// 验证码通过
	private static final String pass_staus_deniy = "5";// 密码不正确、用户的邮箱不正确
	private static final String pass_name_access = "6";// 验证全部通过 、重置密码成功
	private static final String Status_deniy_lgoin = "8";// 禁止登陆
	private static final String pass_name_excption = "7";// 注册异常
	private static final String role_error = "9";// 角色错误
	private static final String LOGIN_USERNAME_EORROR = "10";
	private static final String LOGIN_PASSWORD_EORROR = "11";
	private static final String SKIP = "skip";

	private static final String REG_SUCCESS = "regSuccess";
	private static final String ACCOUNT_SAFE = "accountSafe";

	private static final String quotes = "\"";
	private String remeberName;
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityUserService securityUserService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UroleService uroleService;
	private UserInfo userinfo;
	/**
	 * 验证码
	 */
	private String jCaptchaResponse;
	/** 判断错误次数 */
	private Integer opertNum;
	/** 用户名 */
	private String username;
	/** 盐值 */
	@Value("${password_salt}")
	private String passwordSaltAction;
	/** 密码 */
	private String password;
	private String password1;
	/** 新密码 */
	private String newPassword;
	private String newPswEn;

	/** 邮箱 */
	private String email;

	/** QQ登录回执COde */
	private String code;
	/** 邮件地址 */
	@Value("${jspvar._domain}")
	private String mailUrl;

	@Value("${jspvar._domainName}")
	private String domainName;
	/** 发送邮件的时间 */
	private String sendTime;
	@Value("${resetPasswordTime}")
	private String resetPasswordTime;

	@Value("${app_ID}")
	private String appid;
	@Value("${app_KEY}")
	private String appkey;
	@Value("${scope}")
	private String scope;
	@Value("${redirect_URI}")
	private String redirectURI;

	/** 找回密码 提示信息 */
	private String findPasswordInfo;
	private String active;

	// private String urlBeforeLogin;
	private static final String EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
	// ---------------第三方登录-----------
	/** 账号重复 */
	public static final String ACCOUNT_REPETITION = "2";
	/** 账号异常 */
	public static final String ACCOUNT_EXCEPTION = "1";
	/** 账号注册成功 */
	public static final String ACCOUNT_REGISTER_SUCCESS = "6";
	/** 验证码 */
	public static final String VERIFICATION_ERROR = "5";

	public static final String ACCESS_TOKEN = "access_token";
	public static final String MOBILE_USER_SESSION = "mobileUser";
	private String openId;
	private String nickName;
	private String thirdType;// 0-QQ 1-微博 2-微信 3-来往 4-易信
	private static final BASE64Decoder decoder = new BASE64Decoder(); // 初始化64
	private static final BASE64Encoder encode = new BASE64Encoder();

	/** 短信激活码长度 */
	public static final Integer SMS_CODE_LENGTH = 6;
	/** 短信激活码前缀 */
	public static final String SMS_CODE_PREFIX = "SMS_";
	/** 短信激活码错误 */
	public static final String SMS_CODE_WRONG = "5";
	/** 短信发送异常 */
	public static final String SMS_SEND_EXCEPTION = "2";
	/** 短信未发送 */
	public static final String SMS_SEND_NULL = "7";
	/** 短信发送成功 */
	public static final String SMS_SEND_SUCCESS = "6";
	/** 短信激活码超时 */
	public static final String SMS_OVER_TIME = "3";
	/** 短信激活码 获取时间过短 */
	public static final String SMS_OBTAIN_TIME_ERROR = "4";
	/** 短信手机号重复 */
	public static final String SMS_MOBILE_REPEAT = "8";
	/** 电话 */
	private String phone;
	/** 短信激活码 */
	private String smsCode;
	/** 注册成功用户名 */
	private String userName;

	private String stateType;

	/**
	 * 登陆
	 * 
	 * @return
	 */
	public String checkUserName() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if (StringUtil.isEmpty(username)) {
				setMessage(LOGIN_USERNAME_EORROR);
				return JSONMSG;
			}
			if (StringUtil.isEmpty(password)) {
				setMessage(LOGIN_PASSWORD_EORROR);
				return JSONMSG;
			} else {
				if (password.length() < 6 || password.length() > 25) {
					setMessage(LOGIN_PASSWORD_EORROR);
					return JSONMSG;
				}
			}
			if (opertNum > 3) {
				// ServletActionContext.getRequest().setAttribute("jCaptchaResponse",
				// jCaptchaResponse);
				boolean flag = JCaptchaValidator.validate();
				if (flag) {
					setMessage(pass_img_access);// 验证码输入正确 4
				} else {
					setMessage(pass_img_deniy);// 输入的验证码不正确3
					return JSONMSG;
				}
			}
			username = new String(decoder.decodeBuffer(username)).replaceAll(" ", ""); // 解密
			password = new String(decoder.decodeBuffer(password)).replaceAll(" ", ""); // 解密
			Pattern email = Pattern.compile(EMAIL);
			if (!email.matcher(username).matches()) {// 如果不是邮箱
				if (username.length() < 6 || username.length() > 25) {
					setMessage(LOGIN_USERNAME_EORROR);
					return JSONMSG;
				}
			}
			User user = userService.getByName(username);
			if (user == null) {
				setMessage(user_stuas);// 账号不存在
				return JSONMSG;
			}
			if (user.getStatus().equals(user_stuas)) {
				setMessage(Status_deniy);// 账号被禁用
				return JSONMSG;
			}
			List<Integer> uroleIds = uroleService.listUroleIdsByUserId(user.getUserId());
			if (uroleIds == null || uroleIds.size() == 0) {
				setMessage(user_stuas);// 账号不存在
				return JSONMSG;
			}
			boolean foundRole = false; // 是否发现是【买家】角色
			for (Integer uroleId : uroleIds) {
				if (UserInfo.USER_ROLE_BUYER.equals(uroleId)) {
					foundRole = true;
					break;
				}
			}
			if (!foundRole) {
				setMessage(user_stuas);// 账号不存在
				return JSONMSG;
			}

			/*
			 * if (user.getCreateByUserId() != 0) {
			 * setMessage(Status_deniy_lgoin);// 账号不能登录 return JSONMSG; }
			 */
			if (user.getPassword() != null && !"".equals(user.getPassword())) {
				if (!Md5EncryptionUtils.MD5SaltPassword(password, passwordSaltAction).equals(user.getPassword())) {
					setMessage(pass_staus_deniy);// 密码不正确
					return JSONMSG;
				}
			}
			/*ClGbo0zEHtLGKAvyMOEWvQ==  
			 * l1lAre2C7oXOtTmPC/jPKg==
			 * List<Integer> roles =
			 * uroleService.listNormalUroleIdsByUserId(user.getUserId());
			 * if(!roles.contains(UserInfo.USER_ROLE_BUYER)){
			 * setMessage(role_error);//角色不对 return JSONMSG; }
			 */
			setMessage(pass_name_access);
			request.getSession().setAttribute(MOBILE_USER_SESSION, user);
		} catch (Exception e) {
			logger.error("checkUserName Exception", e.getMessage());
			setMessage(pass_name_excption);
			return JSONMSG;
		}
		// 处理引号
		if ((username.indexOf(quotes) == 0) && (username.lastIndexOf(quotes) == username.length() - 1)) {
			username = username.substring(1, username.length() - 1);
		}
		if (!StringUtils.isEmpty(remeberName)) {
			try {
				CookieUtils.addCookie(response, "yipinusername",
						encode.encode(URLEncoder.encode(username, "utf-8").getBytes()), 10 * 24 * 60 * 60);
			} catch (UnsupportedEncodingException e) {
				logger.error("cookie记住用户名失败 :", e);
			}
		} else {
			CookieUtils.removeCookie(request, response, "yipinusername");
		}
		return JSONMSG;
	}

	/**
	 * 手机端登陆
	 * 
	 * @return
	 */
	public String mobileCheckUserName() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if (StringUtil.isEmpty(username)) {
				setMessage(LOGIN_USERNAME_EORROR);
				return JSONMSG;
			}
			if (StringUtil.isEmpty(password)) {
				setMessage(LOGIN_PASSWORD_EORROR);
				return JSONMSG;
			} else {
				if (password.length() < 6 || password.length() > 25) {
					setMessage(LOGIN_PASSWORD_EORROR);
					return JSONMSG;
				}
			}

			username = new String(decoder.decodeBuffer(username)).replaceAll(" ", ""); // 解密
			password = new String(decoder.decodeBuffer(password)).replaceAll(" ", ""); // 解密
			Pattern email = Pattern.compile(EMAIL);
			if (!email.matcher(username).matches()) {// 如果不是邮箱
				if (username.length() < 6 || username.length() > 25) {
					setMessage(LOGIN_USERNAME_EORROR);
					return JSONMSG;
				}
			}
			User user = userService.getByName(username);
			if (user == null) {
				setMessage(user_stuas);// 账号不存在
				return JSONMSG;
			}
			if (user.getStatus().equals(user_stuas)) {
				setMessage(Status_deniy);// 账号被禁用
				return JSONMSG;
			}
			/*
			 * if (user.getCreateByUserId() != 0) {
			 * setMessage(Status_deniy_lgoin);// 账号不能登录 return JSONMSG; }
			 */
			if (user.getPassword() != null && !"".equals(user.getPassword())) {
				if (!Md5EncryptionUtils.MD5SaltPassword(password, passwordSaltAction).equals(user.getPassword())) {
					setMessage(pass_staus_deniy);// 密码不正确
					return JSONMSG;
				}
			}
			/*
			 * List<Integer> roles =
			 * uroleService.listNormalUroleIdsByUserId(user.getUserId());
			 * if(!roles.contains(UserInfo.USER_ROLE_BUYER)){
			 * setMessage(role_error);//角色不对 return JSONMSG; }
			 */
			setMessage(pass_name_access);
			request.getSession().setAttribute(MOBILE_USER_SESSION, user);
		} catch (Exception e) {
			logger.error("checkUserName Exception", e.getMessage());
			setMessage(pass_name_excption);
			return JSONMSG;
		}
		// 处理引号
		if ((username.indexOf(quotes) == 0) && (username.lastIndexOf(quotes) == username.length() - 1)) {
			username = username.substring(1, username.length() - 1);
		}
		if (!StringUtils.isEmpty(remeberName)) {
			try {
				CookieUtils.addCookie(response, "yipinusername",
						encode.encode(URLEncoder.encode(username, "utf-8").getBytes()), 10 * 24 * 60 * 60);
			} catch (UnsupportedEncodingException e) {
				logger.error("cookie记住用户名失败 :", e);
			}
		} else {
			CookieUtils.removeCookie(request, response, "yipinusername");
		}
		return JSONMSG;
	}

	/**
	 * 我的资料
	 * 
	 * @return
	 */
	public String userInfo() {
		try {
			// 获取当前用户id
			Integer userId = securityUserService.getCurrentUser().getUserId();
			userinfo = userInfoService.getUserInfoById(userId);
			username = securityUserService.getCurrentUser().getUsername();
		} catch (UserException e) {
			logger.error("UserAction userInfo UserException :", e.getMessage());
		} catch (BaseException e) {
			logger.error("UserAction userInfo BaseException :", e.getMessage());
		}
		return "userInfo";
	}

	/**
	 * 获取服务条款
	 * 
	 * @return
	 */
	public String getServiceTerms() {
		return "getServiceTerms";
	}

	/**
	 * 更新密码
	 * 
	 * @return
	 */
	public String updatePassword() throws UserException {
		try {
			// 获取当前用户userName
			User user = userService.getByName(securityUserService.getCurrentUser().getUsername());
			if (StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(user.getPassword())
					&& StringUtils.isNotEmpty(newPassword)) {
				String uActive = Md5EncryptionUtils.MD5SaltPassword(password, passwordSaltAction);
				if (uActive.equals(user.getPassword())) {
					userService.updatePswByUserId(user.getUserId(), newPassword);

					setMessage("status", "success");
					return JSONMSG;
				}
			} else {
				return this.changePsw();
			}

		} catch (NumberFormatException n) {
			logger.error("修改密码是转换数据异常:", n.getMessage());
			setMessage("editPwdErr", "密码修改失败");
			return JSONMSG;
		} catch (Exception e) {
			logger.error("密码修改失败:", e.getMessage());
			setMessage("editPwdErr", "密码修改失败");
			return JSONMSG;
		}
		return JSONMSG;

	}

	public String changePsw() {
		return "changePsw";
	}

	// 修改用户信息 和用户详细详细信息
	public String updateUserInfo() throws Exception {
		try {
			String contactName = new String(decoder.decodeBuffer(userinfo.getContactName()));
			String mobile = new String(decoder.decodeBuffer(userinfo.getMobile()));
			String tel = new String(decoder.decodeBuffer(userinfo.getTel()));
			String companyAddress = userinfo.getCompanyAddress();
			String email = new String(decoder.decodeBuffer(userinfo.getEmail()));
			Integer companyRegionId = userinfo.getCompanyRegionId();
			boolean flagIsNull = StringUtil.isEmpty(mobile) || StringUtil.isEmpty(companyRegionId.toString())
					|| StringUtil.isEmpty(email);
			boolean flagReg = !CheckRegex.checkLength(contactName, 10) || !CheckRegex.checkMobile(mobile)
					|| !CheckRegex.checkTel(tel) || !CheckRegex.checkLength(companyAddress, 100)
					|| !CheckRegex.checkEmail(email, 50);

			// 判断当前用户是否登录 并且只能修改自己的信息
			UserVo customUserDetail = securityUserService.getCurrentUser();
			if (flagIsNull || flagReg) {
				logger.error("表单信息验证失败!");
				setMessage("status", "false");
			} else {
				if (customUserDetail != null && customUserDetail.getUserId().equals(userinfo.getUserId())) {
					userinfo.setContactName(contactName);
					userinfo.setMobile(mobile);
					userinfo.setTel(tel);
					userinfo.setCompanyAddress(companyAddress);
					userinfo.setEmail(email);
					userInfoService.updateUserAndUserInfo(userinfo);
					setMessage("status", "true");
				} else {
					setMessage("status", "false");
				}
			}
		} catch (BaseException e) {
			logger.error("更新失败,", e.getMessage());
			setMessage("status", "false");
		} catch (IOException e) {
			logger.error("更新失败,", e.getMessage());
			setMessage("status", "false");
		}
		return JSONMSG;
	}

	public boolean validateEqualsPwd(String newActive, String uActiveEn) {
		if (StringUtil.isEmpty(newActive) || StringUtil.isEmpty(uActiveEn)) {
			return false;
		} else {
			String newAct = Md5EncryptionUtils.MD5SaltPassword(newPassword, passwordSaltAction);
			String uActEn = Md5EncryptionUtils.MD5SaltPassword(newPswEn, passwordSaltAction);
			if (newAct.equals(uActEn)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 找回密码首页
	 * 
	 * @return
	 */
	public String findPassWord() {
		return "passWordstep1";
	}

	/**
	 * 找回密码验证账号
	 * 
	 * @return
	 */
	public String ajaxCheckEmail() {
		UserVo customUserDetail = null;
		try {
			customUserDetail = securityUserService.getCurrentUser();
		} catch (UserException e1) {
			logger.error("找回密码验证账户用户登录", e1);
		}
		// 找回密码 第二步如果用户没有登录 执行登录流程
		if (customUserDetail == null) {
			if (StringUtil.isEmpty(username)) {
				setMessage(Boolean.FALSE.toString(), "请输入登录名");
			} else {
				try {
					username = new String(decoder.decodeBuffer(username)).replaceAll(" ", "");// 解密
				} catch (IOException e) {
					logger.error("验证密码解密失败", e);
				}
				if (userService.isDulplicateName(username)) {
					setMessage(Boolean.FALSE.toString(), "该用户不存在");
				} else {
					User user = userService.getByName(username);
					email = user.getEmail();
					if (StringUtils.isEmpty(email)) {
						setMessage(Boolean.FALSE.toString(), "您没有设置找回密码邮箱，请联系客服！");
					} else {
						setMessage(Boolean.TRUE.toString(), "");
					}
				}
			}

		}
		return JSONMSG;
	}

	/**
	 * 找回密码验证验证码
	 * 
	 * @return
	 */
	public String ajaxCheckJCaptchaResponse() {
		UserVo customUserDetail = null;
		try {
			customUserDetail = securityUserService.getCurrentUser();
		} catch (UserException e1) {
			logger.error("找回密码验证码处验证用户登录", e1);
		}
		// 找回密码 第二步如果用户没有登录 执行登录流程
		if (customUserDetail == null) {
			if (StringUtil.isEmpty(jCaptchaResponse)) {
				setMessage(Boolean.FALSE.toString(), "请输入验证码");
			} else {
				try {
					boolean flag = JCaptchaValidator.validate();
					if (!flag) {
						setMessage(Boolean.FALSE.toString(), "验证码输入错误");
					} else {
						setMessage(Boolean.TRUE.toString(), "");
					}
				} catch (Exception e) {
					logger.error("找回密码验证码异常", e);
				}
			}
		}
		return JSONMSG;
	}

	/**
	 * 找回密码操作
	 * 
	 * @return
	 */
	public String passwordBackQueryMsg() {
		try {
			UserVo customUserDetail = null;
			try {
				customUserDetail = securityUserService.getCurrentUser();
			} catch (UserException e1) {
				logger.error("找回密码操作，用户未登录", e1);
			}
			// 找回密码 第二步如果用户没有登录 执行登录流程
			if (customUserDetail == null) {
				/*
				 * ServletActionContext.getRequest().setAttribute(
				 * "jCaptchaResponse", jCaptchaResponse); boolean flag =
				 * JCaptchaValidator.validate(); if (flag) { if
				 * (!userService.repeatUsername(username)) {
				 */
				User user = null;
				user = userService.getByName(username);
				if (User.STATUS_ABLE == user.getStatus()) {
					email = user.getEmail();
					StringBuilder sb = new StringBuilder();
					sb.append(mailUrl).append("/user/resetpassword.do?username=")
							.append(EncodeUtils.base64Encode(user.getUsername()));
					// 将用户的username和常量md5加密
					sb.append("&")
							.append("active=")
							.append(EncodeUtils.base64Encode(Md5EncryptionUtils.MD5SaltPassword(user.getUsername(),
									passwordSaltAction)));
					// 添加时间戳
					String timesConstant = System.currentTimeMillis() + ":" + domainName;
					sb.append("&").append("sendTime=").append(EncodeUtils.base64Encode(timesConstant));
					String findPassUrl = sb.toString();
					Map<String, String> map = new HashMap<String, String>();
					map.put("userName", username);
					map.put("findPassUrl", findPassUrl);
					map.put("domainName", domainName);
					// String text = sendEmailService.getMailContent(
					// MailTemplate.TYPE_REPASSWORD, map);
					// sendEmailService.sendMail(user.getEmail(),
					// "找回密码", text);
					return "mailmsg";// 发送邮件跳转到邮件已发送页面
				} else {
					findPasswordInfo = "该用户未激活或被禁用";
					return "noActivate";
				}
				/*
				 * } else { return "accoutError";// 账号不存在 } } else { return
				 * "verificationError";// 验证码错误 }
				 */
			} else {
				return SUCCESS;
			}
		} catch (Exception e) {
			return SUCCESS;
		}
	}

	/**
	 * 检查url有效的时间
	 * 
	 * @return
	 */
	private boolean checkUrlVaildTime() {
		if (StringUtils.isNotEmpty(sendTime)) {
			String sendEmailTime = EncodeUtils.base64Decode(sendTime).split(":")[0];
			long sdTime = Long.parseLong(sendEmailTime);
			long curTime = System.currentTimeMillis();
			int pTime = Integer.parseInt(resetPasswordTime);
			long passwordTime = sdTime + pTime * 60 * 60 * 1000;
			return curTime < passwordTime;
		}
		return false;
	}

	/**
	 * 检查有注册邮箱的唯一性
	 * 
	 * @return
	 */
	public String checkEmailVaild() {
		try {
			if (StringUtils.isNotEmpty(userinfo.getEmail())) {
				String mail = new String(decoder.decodeBuffer(userinfo.getEmail()));
				String userName = securityUserService.getCurrentUser().getUsername();
				User user = userService.getByName(userName);
				// 排除当前用户邮箱
				if (mail.equals(user.getEmail())) {
					setMessage("status", "true");
					return JSONMSG;
				} else {
					// 验证其他邮箱
					boolean status;
					status = userService.isDulplicateEmail(mail);
					if (status) {
						setMessage("status", "true");
						return JSONMSG;
					} else {
						setMessage("status", "false");
						return JSONMSG;
					}
				}
			}
		} catch (UserException e) {
			logger.error("验证邮箱出现异常", e);
			setMessage("status", "error");
			return JSONMSG;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setMessage("status", "error");
		return JSONMSG;
	}

	/**
	 * 检查密码正确性
	 * 
	 * @return
	 */
	public String checkPwdVaild() {
		try {
			if (StringUtils.isNotEmpty(password)) {
				UserVo user = securityUserService.getCurrentUser();
				String userPwd = userService.getByName(user.getUsername()).getPassword();
				String uActive = Md5EncryptionUtils.MD5SaltPassword(password, passwordSaltAction);
				if (userPwd.equals(uActive)) {
					setMessage("status", "true");
					return JSONMSG;
				} else {
					setMessage("status", "false");
					return JSONMSG;
				}
			}
		} catch (UserException e) {
			logger.error("检查密码正确性出现异常", e);
			e.printStackTrace();

		}
		setMessage("status", "error");
		return active;

	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	public String resetPassword() {
		if (!checkUrlVaildTime()) {
			// 过期
			findPasswordInfo = "url已经过期！";
			return "updateInfo";
		}
		return "passWordStep3";
	}

	// 邮件跳转页面的提交
	public String updatePasswordByMail() throws UserException {
		// 获取用户名称、时间戳、加密字符串
		// 1.判断时间是否过期
		// 2.验证url是否正确
		// 3.重置密码
		try {
			if (checkUrlVaildTime()) {
				String uName = EncodeUtils.base64Decode(username);
				User u = userService.getByName(uName);
				String uActive = Md5EncryptionUtils.MD5SaltPassword(u.getUsername(), passwordSaltAction);
				active = EncodeUtils.base64Decode(active);
				if (null != u && uActive.equals(active)) {
					password = new String(decoder.decodeBuffer(password)).replaceAll(" ", "");// 解密
					password1 = new String(decoder.decodeBuffer(password1)).replaceAll(" ", "");// 解密
					if (StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(password1)
							&& password.equals(password1) && password.length() > 5 && password.length() < 16) {
						// 修改密码
						userService.updatePswByUserId(u.getUserId(), password);
						findPasswordInfo = "重置密码成功！";
						return "updateSuccess";
					} else {
						findPasswordInfo = "两次密码输入不一致！";
						return "updateInfo";
					}
				} else {
					// Url被改了
					findPasswordInfo = "url被修改过！";
					return "updateInfo";
				}
			} else {
				// 过期
				findPasswordInfo = "url已经过期！";
				return "updateInfo";
			}
		} catch (NumberFormatException n) {
			logger.error("修复密码是转换数据异常:", n);
			findPasswordInfo = "修改密码失败！";
			return "updateInfo";
		} catch (Exception e) {
			logger.error("密码修复失败:", e);
			findPasswordInfo = "修改密码失败！";
			return "updateInfo";
		}
	}

	/**
	 * 第三方 登录
	 * */
	public String thirdLogin() {
		return null;

	}

	/**
	 * QQ登录
	 * 
	 * @return
	 */
	public void qqLogin() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			response.sendRedirect("http://openapi.qzone.qq.com/oauth/show?which=ConfirmPage&display=pc&response_type=code&client_id="
					+ appid + "&redirect_uri=" + redirectURI + "&scope=" + scope);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * QQ登录回调
	 * 
	 * @return
	 */
	public String qqLoginCallBack() {
		try {
			String tokenUrl = "https://graph.qq.com/oauth2.0/token";
			String openIdUrl = "https://graph.qq.com/oauth2.0/me";
			String userInfo = "https://graph.qq.com/user/get_user_info";
			Map<String, String> tokenParams = new HashMap<String, String>();
			tokenParams.put("grant_type", "authorization_code");
			tokenParams.put("client_id", appid);
			tokenParams.put("redirect_uri", redirectURI);
			tokenParams.put("client_secret", appkey);
			tokenParams.put("code", code);
			String tokenObjStr = WebUtils.doGet(tokenUrl, tokenParams);
			String access_token = "";
			if (tokenObjStr.startsWith("callback")) {
				tokenObjStr = tokenObjStr.substring(tokenObjStr.indexOf("{"), tokenObjStr.lastIndexOf("}") + 1);
				JSONObject tokenObj = JSONObject.fromObject(tokenObjStr);
				// 错误处 暂不处理 可以后拓展
			} else {
				String[] mapStr = tokenObjStr.split("&");
				for (String string : mapStr) {
					String[] prams = string.split("=");
					if (ACCESS_TOKEN.equals(prams[0])) {
						access_token = prams[1];
						break;
					}
				}
			}
			Map<String, String> openIdParams = new HashMap<String, String>();
			openIdParams.put("access_token", access_token);
			String openIdObjStr = WebUtils.doGet(openIdUrl, openIdParams);
			if (openIdObjStr.startsWith("callback")) {
				openIdObjStr = openIdObjStr.substring(openIdObjStr.indexOf("{"), openIdObjStr.lastIndexOf("}") + 1);
				JSONObject openIdObj = JSONObject.fromObject(openIdObjStr);
				if (openIdObj.get("error") != null) {
					// 错误处 暂不处理 可以后拓展
				} else {
					Map<String, String> userInfoParams = new HashMap<String, String>();
					userInfoParams.put("access_token", access_token);
					userInfoParams.put("oauth_consumer_key", appid);
					userInfoParams.put("openid", openIdObj.getString("openid"));
					String userInfoStr = WebUtils.doGet(userInfo, userInfoParams);
					JSONObject userInfoObj = JSONObject.fromObject(userInfoStr);
					if (userInfoObj.getInt("ret") == 0) {
						this.setOpenId(openIdObj.getString("openid"));
						this.setNickName(userInfoObj.getString("nickname"));
						thirdLogin();
						String messageCode = getMessage().getInfo();
						if (messageCode.equals(ACCOUNT_REPETITION) || messageCode.equals(ACCOUNT_REGISTER_SUCCESS)) {
							return SKIP;
						}
					} else {
						// 错误处 暂不处理 可以后拓展
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 验证手机号
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String verifyPhone() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String smsSendBeanKey = SMS_CODE_PREFIX + session.getId();

		Object smsObj = session.getAttribute(smsSendBeanKey);

		// 如果为空 表示为申请激活码
		if (smsObj == null || smsCode == null) {
			setMessage(UserAction.SMS_SEND_NULL, "请获取校验码");
			return JSONMSG;
		}

		SmsSendBean sms = (SmsSendBean) smsObj;
		// 判断是否超时
		if (sms.isOverTime()) {
			setMessage(UserAction.SMS_OVER_TIME);
			session.removeAttribute(smsSendBeanKey);
			return JSONMSG;
		}

		// 验证短信验证码
		if (!sms.checkCode(smsCode)) {
			setMessage(UserAction.SMS_CODE_WRONG, "校验码错误,重新填写");
			return JSONMSG;
		}

		try {
			UserVo cuser = securityUserService.getCurrentUser();
			boolean isRepeat = userInfoService.isMobileRepeat(phone);
			if (isRepeat) {
				setMessage(UserAction.SMS_MOBILE_REPEAT, "手机号码已被使用");
				return JSONMSG;
			} else {
				Map params = new HashMap();
				params.put("userId", cuser.getUserId());
				params.put("mobile", phone);
				userInfoService.updateUserInfoTel(params);
			}
		} catch (BaseException e) {
			setMessage(SMS_SEND_EXCEPTION, "用户未登录");
			logger.debug("用户未登录");
			return JSONMSG;
		}
		setMessage(UserAction.SMS_SEND_SUCCESS, "校验码验证成功");
		return JSONMSG;
	}

	/**
	 * 获取短信验证码
	 * 
	 * @return
	 */
	public String getVerifyCode() {
	/*	CustomUserDetails cuser = null;
		try {
			cuser = securityUserService.getCurrentUser();
			
			boolean isRepeat = userInfoService.isMobileRepeat(phone);
			if (isRepeat) {
				setMessage(UserAction.SMS_MOBILE_REPEAT, "手机号码已被使用");
				return JSONMSG;
			}
		} catch (UserException e) {
			setMessage(SMS_SEND_EXCEPTION, "用户未登录");
			logger.debug("用户未登录");
			return JSONMSG;
		}
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		String smsSendBeanKey = SMS_CODE_PREFIX + session.getId();
		Object obj = session.getAttribute(smsSendBeanKey);
		if (obj != null) {
			SmsSendBean sms = (SmsSendBean) obj;
			// 判断再次获取激活码是否为60秒之后
			if (!sms.isNormalObtainCode()) {
				setMessage(SMS_OBTAIN_TIME_ERROR, "获取激活码时间少于60秒!");
				return JSONMSG;
			}
		}

		String smsCode ="";// RandomNumUtil.getRandom(RandomNumUtil.NUM, SMS_CODE_LENGTH);`
		SmsSendBean sms = new SmsSendBean();
		sms.setCode(smsCode);
		sms.setPhoneNumber(phone);
		sms.setSendTime(new Date());
		SmsMessageUtils.sendMessage(sms.getContext(), sms.getPhoneNumber(),
				SmsMessage.SMS_PRIORITY_5, cuser.getUsername(), cuser.getUsername(),
				MessageTypeEnum.AUTH_CODE.getCode());

		session.setAttribute(smsSendBeanKey, sms);

		setMessage(SMS_SEND_SUCCESS, "获取激活码成功");
		logger.debug(smsSendBeanKey + ", 激活码：" + smsCode);
	 */
		return JSONMSG;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String regSuccess() {
		try {
			UserVo cuser = securityUserService.getCurrentUser();
			userName = cuser.getUsername();

			Map map = new HashMap();
			map.put("userId", cuser.getUserId());
			map.put("telCheckState", UserInfo.USER_TEL_STATE_1);

		//	userInfoService.updateCheckState(map);

			UserInfo userInfo = userInfoService.getUserInfoById(cuser.getUserId());
			if (userInfo.getTelCheckState() == 1 && userInfo.getMailCheckState() == 1) {
				stateType = "3";
			} else if (userInfo.getTelCheckState() == 1) {
				stateType = "2";
			} else {
				stateType = "1";
			}
		} catch (UserException e) {
			logger.error("当前用户未登陆", e);
		}
		return REG_SUCCESS;
	}

	public String accountSafe() {
		try {
			// 获取当前用户id
			Integer userId = securityUserService.getCurrentUser().getUserId();
			userinfo = userInfoService.getUserInfoById(userId);
			username = securityUserService.getCurrentUser().getUsername();
		} catch (UserException e) {
			logger.error("UserAction userInfo UserException :", e.getMessage());
		}
		return ACCOUNT_SAFE;
	}

	public String getRemeberName() {
		return remeberName;
	}

	public void setRemeberName(String remeberName) {
		this.remeberName = remeberName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getOpertNum() {
		return opertNum;
	}

	public void setOpertNum(Integer opertNum) {
		this.opertNum = opertNum;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPswEn() {
		return newPswEn;
	}

	public void setNewPswEn(String newPswEn) {
		this.newPswEn = newPswEn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getFindPasswordInfo() {
		return findPasswordInfo;
	}

	public void setFindPasswordInfo(String findPasswordInfo) {
		this.findPasswordInfo = findPasswordInfo;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public String getJCaptchaResponse() {
		return jCaptchaResponse;
	}

	public void setJCaptchaResponse(String jCaptchaResponse) {
		this.jCaptchaResponse = jCaptchaResponse;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the smsCode
	 */
	public String getSmsCode() {
		return smsCode;
	}

	/**
	 * @param smsCode
	 *            the smsCode to set
	 */
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStateType() {
		return stateType;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
	}

}
