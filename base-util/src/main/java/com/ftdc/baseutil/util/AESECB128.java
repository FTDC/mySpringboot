package com.ftdc.baseutil.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESECB128 {  
    private static final byte[] staticKey = "9C5B4F3FA8685CDB3656592C7996A0E2".getBytes();  
  
    public static byte[] encrypt(byte[] plainText) {  
        return encrypt(plainText,staticKey);  
    }  
      
    /** 
     * 加密 
     *  
     * @param content 
     *            需要加密的内容 
     * @param enckey 
     *            加密密码 
     * @return 
     */  
    public static byte[] encrypt(byte[] content, byte[] enckey) {  
        try {  
            SecretKeySpec key = new SecretKeySpec(enckey, "AES");  
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// 创建密码器  
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(content);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    public static byte[] decrypt(byte[] cipherText) {  
        return decrypt(cipherText,staticKey);  
    }  
    /** 
     * 解密 
     *  
     * @param content 
     *            待解密内容 
     * @param deckey 
     *            解密密钥 
     * @return 
     */  
    public static byte[] decrypt(byte[] content, byte[] deckey) {  
        try {  
            SecretKeySpec key = new SecretKeySpec(deckey, "AES");  
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// 创建密码器  
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(content);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
/*    public static void main(String[] args) {  
        String content = "123456";  
        String password = "9C5B4F3FA8685CDB3656592C7996A0E2";  
        byte[] mykey = password.getBytes();  
        // 加密  
        System.out.println("加密前：" + content);  
        byte[] encryptResult = encrypt(content.getBytes(), mykey);  
        System.out.println("加密后:"+encryptResult);
        // 解密  
        byte[] decryptResult = decrypt(encryptResult, mykey);  
        System.out.println("解密后：" + new String(decryptResult));  
    }  */
  
}  