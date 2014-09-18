package com.xinyou5.front.web.action;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import sun.misc.BASE64Decoder;

import com.google.code.jcaptcha4struts2.core.validation.JCaptchaValidator;
import com.xingyou5.module.base.util.Md5EncryptionUtils;
import com.xingyou5.module.base.util.StringUtils;
import com.xingyou5.module.user.UserInfoService;
import com.xingyou5.module.user.entity.User;
import com.xingyou5.module.user.service.UserService;
import com.xinyou5.front.action.BaseAction;

@SuppressWarnings("restriction")
public class LoginAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(LoginAction.class);
	private static final long serialVersionUID = 2401691415095315055L;

	/**账号重复 */
	public static final String ACCOUNT_REPETITION="2";
	/**邮箱重复 */
	public static final String EMAIL_REPETITION="4";
	/**账号异常*/
	public static final String ACCOUNT_EXCEPTION="1";
	/**账号注册成功*/
	public static final String ACCOUNT_REGISTER_SUCCESS="6";
	/**账号注册用户名错误*/
	public static final String ACCOUNT_USERNAME_ERROR="7";
	/**账号注册密码错误*/
	public static final String ACCOUNT_PASSWORD_ERROR="8";
	/**验证码	 */
	public static final String VERIFICATION_ERROR="5";
	/**用户名	 */
	private String username;
	/**密码	 */
	private String password;
	@Autowired
	private UserService userService;
	//@Autowired
	//private SendMailService sendMailService;
	@Autowired
	private UserInfoService userInfoService;
	
	/**加密盐值	 */
	@Value("${password_salt}")
	private String passwordSaltAction;
	
	private static final String  EMAIL="^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
	private static final BASE64Decoder decoder = new BASE64Decoder(); //初始化64
	
	
	
	public String defaultIndex(){
		return SUCCESS;
	}
	public String defaultRegist(){
		return SUCCESS;
	}
	
	public String email(){
		boolean flag = JCaptchaValidator.validate();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			if(flag){//验证码
				if(StringUtils.isEmpty(username)){
					 setMessage(ACCOUNT_USERNAME_ERROR);//用户账号重复
					 return JSONMSG;
				}
				if(StringUtils.isEmpty(password)){
					 setMessage(ACCOUNT_PASSWORD_ERROR);//用户账号重复
					 return JSONMSG;
				}
				username = new String(decoder.decodeBuffer(username)).replaceAll(" ",""); //解密
				password = new String(decoder.decodeBuffer(password)).replaceAll(" ",""); //解密
				if(userService.existName(username)){
					 setMessage(ACCOUNT_REPETITION);//用户账号重复
					 return JSONMSG;
				}else {
						//验证一把/
				    	Pattern email = Pattern.compile(EMAIL);
						if(!email.matcher(username).matches()){
							if(username.length()<6||username.indexOf("@")>0||username.length()>25){
								 setMessage(ACCOUNT_USERNAME_ERROR);//用户账号错误
								 return JSONMSG;
							}
							if(password.length()<6||password.length()>15){
								 setMessage(ACCOUNT_PASSWORD_ERROR);//用户密码错误
								 return JSONMSG;
							}
						}
						User user = new User();
						user.setUsername(username);//不管是邮件还是手机号都可以作为用户名
						user.setPassword(Md5EncryptionUtils.MD5SaltPassword(password, passwordSaltAction));
						if(email.matcher(username).matches()){//如果是邮箱
							user.setEmail(username);
						}
						userInfoService.addRegister(user);
						//sendMailService.sendMail(user.getEmail(), "注册成功", "恭喜你成为一品网会员。");
						setMessage(ACCOUNT_REGISTER_SUCCESS);//注册成功
						ServletActionContext.getRequest().getSession().setAttribute("register",request.getContextPath()+"/register-perfectInfo.do");
				
				}	
			}else{
				setMessage(VERIFICATION_ERROR);//输入的验证码不正确
				return JSONMSG;
			}
		} catch (Exception e) {
			logger.debug("LoginAction email YtoxlUserException :", e);
			return JSONMSG;
		} 
		return JSONMSG;
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
	
	
	
}
