package com.ftdc.baseutil.util;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class Tool {
	/**
	 * 将字典集合按键排序，并拼接为URL参数对（param1=value1&param2=value2...)
	 * 
	 * @param params
	 *            需要转换的字典集合
	 * @return String字符串 拼接完的URL参数对
	 */
	public static String MaptoString(Map<String, String> params) {
		Set<String> keySet = params.keySet();
		String[] keysArr = keySet.toArray(new String[0]);
		Arrays.sort(keysArr);// 对键进行排序
		StringBuilder signedContent = new StringBuilder();
		for (int i = 0; i < keysArr.length; i++) {// 将字典集合转换为URL参数对
			signedContent.append(keysArr[i]).append("=").append(params.get(keysArr[i])).append("&");
		}
		String signedContentStr = signedContent.toString();
		if (signedContentStr.endsWith("&"))
			signedContentStr = signedContentStr.substring(0, signedContentStr.length() - 1);
		return signedContentStr;
	}
	
	public static String convertInputStream2String(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		return sb.toString();
	}
	
	/**
     * 解密方法
     * 
     * @param DESkey
     *            DES加密的私钥
     * @param data
     *            需要进行解密的秘闻
     * @return URL 解密后的内容
     */
	public static String decode(byte[] DESkey, String data) throws Exception {
		DESKeySpec keySpec = new DESKeySpec(DESkey);// 设置密钥参数
		 byte[] DESIV = { 0, 0, 0, 0, 0,0, 0, 0 };// 设置向量
		AlgorithmParameterSpec iv = new IvParameterSpec(DESIV);// 加密算法的参数接口，IvParameterSpec是它的一个实现
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
		SecretKey key = keyFactory.generateSecret(keySpec);// 得到密钥对象

		Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		deCipher.init(Cipher.DECRYPT_MODE, key, iv);
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
		return new String(pasByte, "UTF-8");
	}
}