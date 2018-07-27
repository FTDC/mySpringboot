package com.ftdc.baseutil.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * 算法工具
 * 
 * @author mingsong.peng
 * @date 2018 03-23 12:23
 */
public class AlgorithmUtil {

	public final static String ENCODING = "UTF-8";

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 生成密钥 自动生成base64 编码后的AES128位密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getAESKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(128);// 要生成多少位，只需要修改这里即可128, 192或256
		SecretKey sk = kg.generateKey();
		byte[] b = sk.getEncoded();
		return parseByte2HexStr(b);
	}

	/**
	 * AES 加密
	 * 
	 * @param base64Key
	 *            base64编码后的 AES key
	 * @param text
	 *            待加密的字符串
	 * @return 加密后的byte[] 数组
	 * @throws Exception
	 */
	public static byte[] getAESEncode(String base64Key, String text) throws Exception {
		byte[] key = parseHexStr2Byte(base64Key);
		SecretKeySpec sKeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
		byte[] bjiamihou = cipher.doFinal(text.getBytes(ENCODING));
		return bjiamihou;
	}

	/**
	 * AES解密
	 * 
	 * @param base64Key
	 *            base64编码后的 AES key
	 * @param text
	 *            待解密的字符串
	 * @return 解密后的byte[] 数组
	 * @throws Exception
	 */
	public static String getAESDecode(String base64Key, byte[] text) throws Exception {
		byte[] key = parseHexStr2Byte(base64Key);
		SecretKeySpec sKeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		byte[] bjiemihou = cipher.doFinal(text);
		return new String(bjiemihou, "UTF-8");
	}

	
	
	/**
	 * 加密
	 * @param content
	 * @param strKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String content,String strKey ) throws Exception {
	    SecretKeySpec skeySpec = getKey(strKey);
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	    byte[] encrypted = cipher.doFinal(content.getBytes());
	    return  encrypted;
	}

	/**
	 * 解密
	 * @param strKey
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(byte[] content,String strKey ) throws Exception {
	    SecretKeySpec skeySpec = getKey(strKey);
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	    byte[] original = cipher.doFinal(content);
	    String originalString = new String(original);
	    return originalString;
	}

	private static SecretKeySpec getKey(String strKey) throws Exception {
	    byte[] arrBTmp = strKey.getBytes();
	    byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

	    for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
	        arrB[i] = arrBTmp[i];
	    }

	    SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

	    return skeySpec;
	}


/*	public static void main(String[] args) throws Exception {
		try {
			// String hexKey = new AlgorithmUtil().getAESKey();
			// System.out.println("16进制秘钥：" + hexKey);
			byte[] encoded = AlgorithmUtil.getAESEncode("9C5B4F3FA8685CDB3656592C7996A0E2", "我要把你嘿嘿嘿");
			System.out.println(encoded);
			// 注意，这里的encoded是不能强转成string类型字符串的
			String decoded = AlgorithmUtil.getAESDecode("9C5B4F3FA8685CDB3656592C7996A0E2", encoded);
			System.out.println(decoded);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(new BASE64Encoder().encode(encrypt("123456", "9C5B4F3FA8685CDB3656592C7996A0E2")));;
	}*/

}