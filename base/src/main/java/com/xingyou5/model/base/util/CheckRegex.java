package com.xingyou5.model.base.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.oscache.util.StringUtil;

/**
 * 常用正则验证
 * @author qixiaobing
 *
 */
public class CheckRegex {
	/**
	 * 检查 email输入是否正确 正确的书写格 式为 username@domain
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean checkEmail(String value, int length) {
		if(StringUtil.isEmpty(value)){
			return true;
		}
		return value
				.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
				&& value.length() <= length;
	}

	/**
	 * 检查电话输入 是否正确 正确格 式 012-87654321、0123-87654321、7654321
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean checkTel(String value) {
		if(StringUtil.isEmpty(value)){
			return true;
		}
		return value.matches("^([0-9]{3,4}-)?[0-9]{7,8}$");
	}

	/**
	 * 检查手机输入 是否正确
	 *     11位手机号或者正确的手机号前七位
	 * @param value
	 * @return boolean
	 */
	public static boolean checkMobile(String value) {
		if(StringUtil.isEmpty(value)){
			return true;
		}
		return value.matches("^[0-9]{11}$");
	}

	/**
	 * 检查中文名输 入是否正确
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean checkChineseName(String value, int length) {
		if(StringUtil.isEmpty(value)){
			return true;
		}
		return value.matches("^[\u4e00-\u9fa5]+$") && value.length() <= length;
	}

	/**
	 * 检查HTML 中首尾空行或空格
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean checkBlank(String value) {
		return value.matches("^\\s*|\\s*$");
	}

	/**
	 * 检查字符串是 否含有HTML标签
	 * 
	 * @param value
	 * @return
	 */

	public static boolean checkHtmlTag(String value) {
		return value.matches("<(\\S*?)[^>]*>.*?</\\1>|<.*? />");
	}

	/**
	 * 检查URL是 否合法
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean checkURL(String value) {
		return value.matches("[a-zA-z]+://[^\\s]*");
	}

	/**
	 * 检查IP是否 合法
	 * 
	 * @param value
	 * @return
	 */
	public static boolean checkIP(String value) {
		return value.matches("\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}");
	}

	/**
	 * 检查ID是否 合法，开头必须是大小写字母，其他位可以有大小写字符、数字、下划线
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean checkID(String value) {
		return value.matches("[a-zA-Z][a-zA-Z0-9_]{4,15}$");
	}
	
	/**
	 * 检查数字
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean isNumber(String value) {
		return value.matches("[0-9]*");
	}

	/**
	 * 检查QQ是否 合法，必须是数字，且首位不能为0，最长15位
	 * 
	 * @param value
	 * @return
	 */

	public static boolean checkQQ(String value) {
		return value.matches("[1-9][0-9]{4,13}");
	}

	/**
	 * 检查邮编是否 合法
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean checkPostCode(String value) {
		return value.matches("[1-9]\\d{5}(?!\\d)");
	}

	/**
	 * 检查身份证是 否合法,15位或18位
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean checkIDCard(String value) {
		return value.matches("\\d{15}|\\d{18}");
	}

	/**
	 * 检查输入是否 超出规定长度
	 * 
	 * @param length
	 * @param value
	 * @return boolean
	 */
	public static boolean checkLength(String value, int length) {
		return ((value == null || "".equals(value.trim())) ? 0 : value.length()) <= length;
	}

	/**
	 * 检查是否为空 字符串,空：true,不空:false
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean checkNull(String value) {
		return value == null || "".equals(value.trim());
	}
	/**
	 * <p>Description: 中文+数字+字母校验</p>
	 * @param text
	 * @return
	 */
	public static  boolean checkText(String text){
		text=new String(text.getBytes());//用GBK编码
		Pattern   pattern = Pattern.compile("^[\u4E00-\u9FA5A-Za-z0-9_]+$");  // 中文字符
		Matcher   matcher  = pattern.matcher(text);
		return matcher.matches();
	}
	/**
	 * <p>Description: 取字符串 字节长度</p>
	 * @param text
	 * @return
	 */
	public static int getTextLenth(String text){
		int length = 0;;
		try {
			length = text.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return length;
	}
	public static void main(String[] ars){
		boolean p=checkLength("验证验证验证验证验证验证验证验证验证验证",20);
		System.out.println("验证:"+p);
	}

}

