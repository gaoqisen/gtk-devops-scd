package com.gtk.common.safe;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.Map;

public class RSAUtils {

    public static void main(String[] args) {
        // createPublicKey();
        try {
            // 读取公钥私钥
//            RSAPublicKey    publicKey = RSAUtils.getPublicKeyString("/Users/jasongao/Documents/workspace/gtk-devops-scd/gtk-devops-common/src/main/resources/public.crt");
////            RSAPrivateKey privateKey = RSAUtils.getRSAPrivateKey("/Users/jasongao/Documents/workspace/gtk-devops-scd/gtk-devops-common/src/main/resources/private.crt", "");
////            String str = "卢彦龙|622427199411135955";
////            System.out.println("明文：" + str);
////            System.out.println("\n公钥加密——私钥解密");
////            String encodedData = RSAUtils.publicEncrypt(str, publicKey); // 传入明文和公钥加密,得到密文
////            System.out.println("密文：" + encodedData);
////            String decodedData = RSAUtils.privateDecrypt(encodedData, privateKey); // 传入密文和私钥,得到明文
////            System.out.println("解密后文字: " + decodedData);
////
////            System.out.println("\n私钥加密——公钥解密");
////
////            encodedData = RSAUtils.privateEncrypt(str, privateKey);// 传入明文和私钥加密,得到密文
////            System.out.println("密文：" + encodedData);
////            decodedData = RSAUtils.publicDecrypt(encodedData, publicKey); // 传入密文和私钥,得到明文
////            System.out.println("解密后文字: " + decodedData);


            String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ3lLa1chATAX7/iJzz6K/tDHfPwNtFnhjK/bU7AwoiFi63R8GaHUH7/D77mpZEx0lCAIcPRnd6mLMp7VfZczXKL9YTYmyOIXi4VS6msJeuuai9FmS6VmKHEPg+HrocscPauwEtmpwo13OnSV3HU5eBSOOuXzPudrP53hCAoZ/OrAgMBAAECgYADIcyqJ6+muHotwDqvBvdgYJBzikxMExWiZBGzAjfZpWyD4io2nnCGVGDnlIOsQaS+XAxPahULmZhB2/h0jpq0puyRXB2m4XwbQQf0HPOwn75afy6JzvTQblT9dBxXi02Dxh5wLx+jnpeJ1lS+AcgTXz1t9OXb1tCoRHHLzvJ0QQJBAObl+hXQYQ9FL0qwItmhn9sT/NTazeoE3WcQpmSbGTfIzIvVQ/pgNRR8AgiDemXnC4sTapobMR74yVHNRkLg2ykCQQCvD3xypFw+OFuD3wr58K/5WOgYUXsoTch4d375wyciInBQKyqPwfXGLAuncwOKJ5BiKf6QPdrnXoejVrPw5cazAkBMnW7YIiaynHBOxhcqQ5j5Zq50Ko484pRciIfNxjEd84D7vS4rPBUg0O0TyhypB3srEKxEqlUoQ8D5ZbW0p0vRAkB50OjkTYLyuPzVkstsiROrCzbWVTzCGv449c4htq7wjc5huufvCSUhNV0WtG3uIQHTjS1a6e/9uEXiX17vbrUJAkBZcLLRuydTVE9RGgjNLQxnFoTz2A19gaTZJTaCWXU5HcMPc0GfOyiDxreaIv2+BCiGsQU6qg3gJX/OV9su+sH5";
        System.out.println(privateDecrypt("Uj4au74N2QAm9Z6//nxxVhtGRjx8/I+0pem8pWB9TirL/8Qn0Na+39QYfh/IBsD/E0z64c34+v5dSpyCOfBj9meRL/Z8zK9KV79cSvH+/vC0AdgYvOslrY7/GBi6GL8wEiSNVPqrYpTgvXeIu6iWZM4Bgu7O7iPxOvjzACYc/lQ=", getPrivateKey(privateKey)));
            System.out.println(privateDecrypt("by6XSbmzHMSNTJHB7Z/XBbxx8YnyrsPosNvYVfYLO4Fm9uPyNMzi607vriTxohfKoXMQsVBbV/iA8fK3kAKPdOAZ9HJhJBH+8AJ8g+fLjywnsKNalkPrVvrLij/YaPNi8V3EZY99XNreRven1ksf5yijmI/KsjZ/xTMk2DFe1Mk=", getPrivateKey(privateKey)));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建公钥私钥
     */
    public static void createPublicKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            System.out.println("公钥：");
            System.out.println("-----BEGIN CERTIFICATE-----");
            System.out.print(new BASE64Encoder().encodeBuffer(publicKey.getEncoded()));
            System.out.println("-----END CERTIFICATE-----");
            System.out.println("私钥：");
            System.out.println("-----BEGIN CERTIFICATE-----");
            System.out.print(new BASE64Encoder().encodeBuffer(privateKey.getEncoded()));
            System.out.println("-----END CERTIFICATE-----");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA"; // ALGORITHM ['ælgərɪð(ə)m] 算法的意思

    public static java.util.Map<String, String> createKeys(int keySize) {
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        // 初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        // 生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        // map装载公钥和私钥
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        // 返回map
        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param publicKey
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     *
     * @param privateKey
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
                    publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
                    privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            // 每个Cipher初始化方法使用一个模式参数opmod，并用此模式初始化Cipher对象。此外还有其他参数，包括密钥key、包含密钥的证书certificate、算法参数params和随机源random。
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
                    privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
                    publicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    // rsa切割解码 , ENCRYPT_MODE,加密数据 ,DECRYPT_MODE,解密数据
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0; // 最大块
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    // 可以调用以下的doFinal（）方法完成加密或解密数据：
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        try {
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultDatas;
    }

    /**
     * 从证书文件中获取公钥
     *
     * @param path
     *            证书文件(如：crt文件)路径
     * @throws CertificateException
     * @throws FileNotFoundException
     */
    static RSAPublicKey getPublicKeyString(String path) throws CertificateException, FileNotFoundException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream(path));
        RSAPublicKey publicKey = (RSAPublicKey) cert.getPublicKey();
        // BASE64Encoder base64Encoder=new BASE64Encoder();
        // String publicKeyString = base64Encoder.encode(publicKey.getEncoded());
        // System.out.println("-----------------公钥--------------------");
        // System.out.println(publicKeyString);
        return publicKey;
    }

    /**
     * 从私钥文件中获取私钥
     *
     * @param path
     *            私钥文件(如：pfx)路径
     * @param password
     *            私钥密码
     * @throws CertificateException
     * @throws FileNotFoundException
     */
    static RSAPrivateKey getRSAPrivateKey(String path, String password) {
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(path);
            // If the keystore password is empty(""), then we have to set
            // to null, otherwise it won't work!!!
            char[] nPassword = null;
            if ((password == null) || password.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = password.toCharArray();
            }
            ks.load(fis, nPassword);
            fis.close();
            // System.out.println("keystore type=" + ks.getType());
            Enumeration enumas = ks.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements())// we are readin just one certificate.
            {
                keyAlias = (String) enumas.nextElement();
                // System.out.println("alias=[" + keyAlias + "]");
            }
            // Now once we know the alias, we could get the keys.
            // System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
            PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
//			Certificate cert = ks.getCertificate(keyAlias);
//			PublicKey pubkey = cert.getPublicKey();
            // System.out.println("cert class = " + cert.getClass().getName());
            // System.out.println("cert = " + cert);
            // System.out.println("public key = " + pubkey);
            RSAPrivateKey pk = (RSAPrivateKey) prikey;
            // System.out.println("private key = " + prikey.toString());
            // System.out.println("private key = " + prikey.);
            return pk;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 转换成十六进制字符串
    public static String Byte2String(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }

    public static byte[] StringToByte(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = b.length - 1; i > -1; i--) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最高位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }
}
