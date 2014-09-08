package com.xingyou5.module.base.util;

import java.util.UUID;

public class RandomUtils extends org.apache.commons.lang.math.RandomUtils {
	
	public static String NUM = "1";// 纯数字

	public static String CHAR = "2";// 纯字符

	public static String NUM_CHAR = "3";// 字符和数字组合
	
	/**
	 * 获取随机数
	 * 
	 * @param type
	 *            随机数类型
	 * @param length
	 *            随机数长度
	 * @return
	 */
	public static String getRandom(String type, int length) {
		String str;

		if (NUM.equals(type))
			str = nextString(length);
		else if (CHAR.equals(type))
			str = getCharacter(length);
		else
			str = getCharacterAndNumber(length);

		return str;
	}

	/**
	 * 获取指定数量的随机数 字符串
	 * @param num
	 * */
	public static String nextString(int num){
		String sRand = "";
		for (int i = 0; i < num; i++) {
			String rand = String.valueOf(RandomUtils.nextInt(10));
			sRand += rand;
		}

		return sRand;
	}
	
	/**
	 * 获取字符的随机
	 * */
	private static String getCharacter(int length) {
		String sRand = "";
		for (int i = 0; i < length; i++) {
			int choice = RandomUtils.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
			sRand += (char) (choice + RandomUtils.nextInt(26));
		}

		return sRand;
	}

	/**
	 * 获取字符和数字组合而成的随机验证码
	 * 
	 * @param length
	 * @return
	 */
	private static String getCharacterAndNumber(int length) {
		String sRand = "";

		for (int i = 0; i < length; i++) {
			String charOrNum = RandomUtils.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {// 字符串
				int choice = RandomUtils.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				sRand += (char) (choice + RandomUtils.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) {// 数字
				sRand += String.valueOf(RandomUtils.nextInt(10));
			}
		}

		return sRand;
	}

	/**
     * 以UUID的策略生成一个长度为32的字符串，在同一时空中具有唯一性。   去掉“-”
     */  
    public static String getUUIDString() {   
        String id = getRandomUUID();   
        id = id.replace("-", "");   
        return id;   
    }

	/**
     * 以UUID的策略生成一个长度为32的字符串，在同一时空中具有唯一性。  保留“-”
     */  
	public static String getRandomUUID(){
		return UUID.randomUUID().toString();
	}
	
}
