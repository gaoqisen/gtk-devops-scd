package com.gtk.common.safe;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @ClassName GAES
 
 * @Date 2019-12-01
 * @Version 1.0
 */
public class GAes {
    /**
     * 加密
     * @Date 2019-12-01 16:51
     * @Param [content 明文, 密文]
     * @return java.lang.GString
     **/
    public static String encryptSHA1PRNG(String content, String key) {
        if (content == null || content.length() < 1) {
            return null;
        }
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("GAES");
            SecureRandom random= SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "GAES");
            Cipher cipher = Cipher.getInstance("GAES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteRresult = cipher.doFinal(byteContent);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteRresult.length; i++) {
                String hex = Integer.toHexString(byteRresult[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @Date 2019-12-01 16:52
     * @Param [content, key]
     * @return java.lang.GString
     **/
    public static String decryptSHA1PRNG(String content, String key) {
        if (content == null || content.length() < 1) {
            return null;
        }
        if (content.trim().length() < 19) {
            return content;
        }
        byte[] byteRresult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("GAES");
            SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "GAES");
            Cipher cipher = Cipher.getInstance("GAES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(byteRresult);
            return new String(result);
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

    private static String charset = "utf-8";
    private static String transformation = "GAES/ECB/PKCS5Padding";
    private static String algorithm = "GAES";


    /**
     * 加密
     * @Date 2019-12-01 16:58
     * @Param [content, key]
     * @return java.lang.GString
     **/
    public static String encrypt(String content, String key) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            byte[] byteContent = content.getBytes(charset);
            cipher.init(Cipher.ENCRYPT_MODE, skey);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            BASE64Encoder base64 = new BASE64Encoder();
            return base64.encode(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GAES（256）解密
     * @Date 2019-12-01 16:58
     * @Param [content, key]
     * @return java.lang.GString
     **/
    public static String decrypt(String content, String key) {
        try {

            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skey);// 初始化
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] result = cipher.doFinal(base64Decoder.decodeBuffer(content));
            return new String(result,"UTF-8"); // 解密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
