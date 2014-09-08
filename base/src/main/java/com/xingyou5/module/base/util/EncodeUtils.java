package com.xingyou5.module.base.util;


import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.codec.Base64;

public class EncodeUtils {
	private static Logger logger = LoggerFactory.getLogger(EncodeUtils.class);
	private static final String DEFAULT_ENCODE = "UTF-8";

    /**
     *   base64编码,默认按照UTF-8
     */
    public  static String base64Encode(String input){
        String result = null;
        try {
            byte[] encode = input.getBytes(DEFAULT_ENCODE);
            result =  new String(Base64.encode(encode), DEFAULT_ENCODE);
        } catch (UnsupportedEncodingException e) {
        	logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * base64解码,默认按照UTF-8
     * @param input
     * @return
     */
    public static String base64Decode(String input){
        String result = null;
        try {
            byte[] decode = input.getBytes(DEFAULT_ENCODE);
            result = new String(Base64.decode(decode), DEFAULT_ENCODE) ;
        } catch (UnsupportedEncodingException e) {
        	logger.error(e.getMessage());
        }
        return result;
    }
    
    /**
	 * 使用指定的字符集charset对字符串arg进行解码,将解码后的字符串返回.
	 * @param arg  待解码字符串.           
	 * @param charset 字符集
	 * @return
	 */
	public static String decode(String arg, String charset) {
		try {
			return java.net.URLDecoder.decode(arg, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 使用指定的字符集charset对字符串arg进行编码,将编码后的字符串返回.
	 * @param arg  待编码字符串.
	 * @param charset 字符集
	 * @return
	 */
	public static String encode(String arg, String charset) {
		try {
			return java.net.URLEncoder.encode(arg, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
