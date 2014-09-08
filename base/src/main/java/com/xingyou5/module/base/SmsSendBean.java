package com.xingyou5.module.base;


import java.io.Serializable;
import java.util.Date;

import ch.qos.logback.classic.gaffer.PropertyUtil;

import com.xingyou5.module.base.util.DateUtil;
import com.xingyou5.module.base.util.StringUtils;

/**信息发送bean
 * @author zhiming.zeng
 *
 */
public class SmsSendBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1396718343183868022L;
	
	/**短信超時时间	 小时*/
	public static final Double SMS_OVER_TIME = 0.5;
	/**短信获取间隔时间段  秒*/
	public static final Integer SMS_NOMAL_CODE_TIME = 60;
	/**短信模板 */
	public static final String SMS_CONTEXT="sms.tel.message";
	/**短信时间格式 */
	public static final String SMS_DATE_FORMART="yyyy-MM-dd HH:mm:ss";
	
	/**手机号码	 */
	protected String phoneNumber;
	/**短信类型 1注册 2快速注册 3其他	 */
	protected String type;
	/**随机验证码  */
	protected String code;
	/**短信内容	 */
	protected String context;
	/**发送时间	 */
	protected Date sendTime;
	
	/** 判断激活码超时默认 30分钟
	 * @return
	 */
	public boolean isOverTime(){
		return isOverTime(null);
	}
	
	/** 判断激活码超时
	 * @param mistiming 时差
	 * @return
	 */
	public boolean isOverTime(Double mistiming){
		if(mistiming==null){
			return isOverTime(SMS_OVER_TIME);
		}
		return DateUtil.hourInterval(new Date(), sendTime)>mistiming;
	}
	/** 判断当前激活码创建时间时候与当前时间的秒数差
	 * @return true超过60秒 false小于60秒
	 */
	public boolean isNormalObtainCode(){
		return DateUtil.secondInterval(new Date(), sendTime)>SMS_NOMAL_CODE_TIME;
	}
	
	/**判断激活码是否正确
	 * @param code
	 * @return
	 */
	public boolean checkCode(String code){
		return this.code.equals(code)?true:false;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContext() {
		if(StringUtils.isEmpty(context)){//如果为空 
			//return PropertyUtil.getPropertyValue(SMS_CONTEXT, new String[]{this.code,DateUtil.format(this.sendTime, SMS_DATE_FORMART)});
		}
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	
	
}

