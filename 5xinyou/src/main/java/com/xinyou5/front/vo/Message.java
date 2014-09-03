package com.xinyou5.front.vo;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;

import com.xingyou5.model.base.SpringContextUtils;
import com.xingyou5.model.base.util.StringUtils;
import com.xingyou5.model.user.CodeConstants;
 

public class Message {	
	private String code;
	private String info;
	private String[] infoValues;
	public Message() {	
	}
	public Message(String code,String info,String[] infoValues ) {
		this.code=code;
		this.info=info;
		this.infoValues = infoValues;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInfo() {
		if(StringUtils.startsWith(info, CodeConstants.INFO_CODE_PRE)){
			try {
				return SpringContextUtils.getContext().getMessage(info, infoValues, Locale.SIMPLIFIED_CHINESE);
			} catch (NoSuchMessageException e) {				
			}			
		}
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String[] getInfoValues() {
		return infoValues;
	}
	public void setInfoValues(String[] infoValues) {
		this.infoValues = infoValues;
	}
}
