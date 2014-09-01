package com.xingyou5.model.base.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * MD5加密工具类.
 */
public class Md5EncryptionUtils {

	/**
	 * 获取加入盐值的加密密码    此方法只供用户密码使用
	 * @param psw 未加密的秘密
	 * @param salt  加密所需的盐值
	 * @return String  返回加密后的秘密
	 * */
	public static String MD5SaltPassword(String psw,String salt){
		Md5PasswordEncoder encoder=new Md5PasswordEncoder();
		encoder.setEncodeHashAsBase64(true);
		return encoder.encodePassword(psw,salt);
	}
	
//	public static void main(String[] args) {
//		Md5PasswordEncoder encoder=new Md5PasswordEncoder();
//		encoder.setEncodeHashAsBase64(true);
//		String hashPass=MD5SaltPassword("rtyuuii","yto123456xl");
//		System.out.println(hashPass);
//		
//    }
}