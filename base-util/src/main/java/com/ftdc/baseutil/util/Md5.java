package com.ftdc.baseutil.util;

import java.security.MessageDigest;

/**
 * md5加密工具类
 * 
 * @author peibenzhen
 *
 */
public class Md5 {
	/**
	 * 将字符串转让成md5格式
	 * 
	 * @param str
	 *            明文
	 * @return 密文
	 */
	public static String md5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}
}
