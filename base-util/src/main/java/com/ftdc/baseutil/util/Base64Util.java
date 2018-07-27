package com.ftdc.baseutil.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Base64加解密工具类
 * 
 * @author mignsong.peng
 *
 */
public class Base64Util {

	/**
	 * @param bytes
	 * @return
	 */
	public static String decode(final byte[] bytes) {
		return new String(Base64.decodeBase64(bytes));
	}
	
	public static byte[] decode(final String bytes) {
		return Base64.decodeBase64(bytes);
	}

	/**
	 * 二进制数据编码为BASE64字符串
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(final byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * 图片转化成base64字符串
	 * 
	 * @return
	 */
	public static String GetImageStr() {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = "d://222.png";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * // base64字符串转化成图片
	 * @param imgStr
	 * @return
	 */
	public static String GenerateImage(String imgStr) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			String upload = "/upload/"+Dttm.ymd();
			File file = new File(upload);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 生成jpeg图片
			String imgFilePath = upload+"/"+S.getUuid()+".jpg";// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return imgFilePath;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
//		String getImageStr = GetImageStr();
//		System.out.println(getImageStr);
//		GenerateImage(getImageStr);
		String encode = encode("123".getBytes());
		String encode2 = encode("123".getBytes());
		System.out.println(encode);
		System.out.println(encode2);
	}
}
