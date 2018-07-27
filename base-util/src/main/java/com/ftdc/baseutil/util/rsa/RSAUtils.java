package com.ftdc.baseutil.util.rsa;

import com.ftdc.baseutil.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加解密
 * 
 * @author jiafuwei created at 2017/6/8
 */
public class RSAUtils {

	private static Logger logger = LoggerFactory.getLogger(RSAUtils.class);

	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	public static final String KEY_ALGORITHM = "RSA";
	private static KeyFactory keyFactory = null;
	public static String PrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALxHff0HPPuQ79oeJ2rTdjEiOS+goVMbys4cEq43BK8+BawSCOnwugAyy4typ0M/k9YJNMEdOcCSHG/eZ2TbhQ+zDSRreCEIln8w9vrb0XvxSuxMb2fYssFnusGhtfKMc+M6FACpAag+AQxI6erICIkpQc1Pujjc9yfxCTpwL6PjAgMBAAECgYBRS+VgviFc1oZafi6y97/PE2Hx6L//7L4zJPgD9Ym2SWeVluv2Z5VIxKYS/lyusuMYxjzbLc1kEzLoMvYeHJNlKFZsOYe2OOV0+MTCrtvRQlqgvSo0yrzh6eXrpve/AQrqh/udv54VIn6PAePqmPiGaPSBmowhpNV+G1vj+SPZ8QJBAOGltAFguiwBpUuCyEwxeUHoCh0qbYQ76nzXrqcFHMlbYyITow5GsuIu7sYWugBBhtGiUSGz2LeORQ62rO18v4cCQQDVmv/gHRXVw2NPztAeoPfmaJdRKt6s0Oa98fNmD5tw062JHlSQlOI1/aJBpSOZvP27tL1OO60frpc195Yhs/fFAkBlVt7902wpQV/0BrtgBMQZhlWsfZaL0cDg6pikqBYilGl0L2+GhNfn3v4bdq94V/VthsF7KmNFJJ79GauWSPVXAkEAmaCsDWT90kt/vuOQvaaqBPYOdpstof9xJQXCOBJbio9DzU2aJo5eD9TGgQ4jh6vslbcNPBohjprEOFVEjiLgvQJAMBvcuyO4AlUdaogfBgq0/voFWajrSNhYlTHUOcG3iL9d7QU9lf2Gn6leByt9q++31Va4nAvD5d3a1OlooeU1IA==";
	public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8R339Bzz7kO/aHidq03YxIjkvoKFTG8rOHBKuNwSvPgWsEgjp8LoAMsuLcqdDP5PWCTTBHTnAkhxv3mdk24UPsw0ka3ghCJZ/MPb629F78UrsTG9n2LLBZ7rBobXyjHPjOhQAqQGoPgEMSOnqyAiJKUHNT7o43Pcn8Qk6cC+j4wIDAQAB";

	static {
		try {
			keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 私钥分段解密方法
	 * 
	 * @param dataStr
	 *            要解密的数据
	 * @return 解密后的原数据
	 * @throws Exception
	 */
	public static String decrypt(String dataStr) throws Exception {
		// 要加密的数据
		// logger.info("要解密的数据:" + dataStr);
		// 对私钥解密
		Key decodePrivateKey = getPrivateKeyFromBase64KeyEncodeStr(PrivateKey);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, decodePrivateKey);
		byte[] data = Base64Util.decode(dataStr);

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		for (int i = 0; inputLen - offSet > 0; offSet = i * MAX_DECRYPT_BLOCK) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}

			out.write(cache, 0, cache.length);
			++i;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		String decodedDataStr = new String(encryptedData, "utf-8");
		// logger.info("私钥解密后的数据:" + decodedDataStr);
		return decodedDataStr;
	}

	public static Key getPrivateKeyFromBase64KeyEncodeStr(String keyStr) {
		byte[] keyBytes = Base64Util.decode(keyStr);
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		Key privateKey = null;
		try {
			privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return privateKey;
	}

	/**
	 * 获取base64加密后的字符串的原始公钥
	 * 
	 * @param keyStr
	 * @return
	 */
	public static Key getPublicKeyFromBase64KeyEncodeStr(String keyStr) {
		byte[] keyBytes = Base64Util.decode(keyStr);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		Key publicKey = null;
		try {
			publicKey = keyFactory.generatePublic(x509KeySpec);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return publicKey;
	}

	/**
	 * 公钥加密方法
	 * 
	 * @param dataStr
	 *            要加密的数据
	 * @param dataStr
	 *            公钥base64字符串
	 * @return 加密后的base64字符串
	 * @throws Exception
	 */
	public static String encryptPublicKey(String dataStr) throws Exception {
		// 要加密的数据
		// logger.info("要加密的数据:" + dataStr);
		byte[] data = dataStr.getBytes();
		// 对公钥解密
		Key decodePublicKey = getPublicKeyFromBase64KeyEncodeStr(publicKey);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey);
		byte[] encodedData = cipher.doFinal(data);
		String encodedDataStr = new String(Base64Util.encode(encodedData));
		// logger.info("公钥加密后的数据:" + encodedDataStr);
		return encodedDataStr;
	}

	/**
	 * 公钥解密
	 * 
	 * @param dataStr
	 * @return
	 * @throws Exception
	 */
	public static String dencryptPublicKey(String dataStr) throws Exception {

		// 对公钥解密
		Key decodePublicKey = getPublicKeyFromBase64KeyEncodeStr(publicKey);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey);
		byte[] data = Base64Util.decode(dataStr);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		String decodedDataStr = new String(encryptedData, "utf-8");
		// logger.info("公钥解密后的数据:" + decodedDataStr);
		return decodedDataStr;
	}

	public static String encryptPrivateKey(String dataStr) throws Exception {
		// 要加密的数据
		// logger.info("要加密的数据:" + dataStr);
		byte[] data = dataStr.getBytes();
		// 对公钥解密
		Key decodePublicKey = getPublicKeyFromBase64KeyEncodeStr(PrivateKey);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey);
		byte[] encodedData = cipher.doFinal(data);
		String encodedDataStr = new String(Base64Util.encode(encodedData));
		// logger.info("私钥加密后的数据:" + encodedDataStr);
		return encodedDataStr;
	}

	/**
	 * 使用公钥进行分段加密
	 * 
	 * @param dataStr
	 *            要加密的数据
	 * @return 公钥base64字符串
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String dataStr) throws Exception {
		// 要加密的数据
		// logger.info("要加密的数据:" + dataStr);
		byte[] data = dataStr.getBytes();
		// 对公钥解密
		Key decodePublicKey = getPublicKeyFromBase64KeyEncodeStr(publicKey);

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		String encodedDataStr = new String(Base64Util.encode(encryptedData));
		// logger.info("公钥加密后的数据:" + encodedDataStr);
		return encodedDataStr;
	}

	/**
	 * 使用私钥进行加密
	 * 
	 * @param dataStr
	 *            要加密的明文
	 * @return 加密后的数据
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String dataStr) throws Exception {
		// logger.info("要加密的数据：" + dataStr);
		byte[] data = dataStr.getBytes();
		// 对公钥解密
		Key decodePublicKey = getPrivateKeyFromBase64KeyEncodeStr(PrivateKey);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey);

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		String encodedDataStr = new String(Base64Util.encode(encryptedData));
		// logger.info("私钥加密后的数据：" + encodedDataStr);
		return encodedDataStr;

	}

	// public static void main(String[] args) throws Exception {
	// // 公钥加密
	// String encryptPublicKey = encryptPublicKey("123456");
	// // 私钥解密
	// decrypt(encryptPublicKey);
	// // 私钥加密
	// // String decryptByPrivateKey = decryptByPrivateKey("123456");
	// // //公钥解密
	// // String dencryptPublicKey = dencryptPublicKey(decryptByPrivateKey);
	// }
}