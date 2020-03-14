package com.gtk.common.safe;

import java.security.MessageDigest;

/**
 * @ClassName MD5
 * @Author gaoqisen
 * @Date 2019-12-01
 * @Version 1.0
 */
public class MD5Utils {

    /**
     * @Author gaoqisen
     * @Description //TODO MD5加密 字符
     * @Date 2019-12-01 16:45
     * @Param [var0]
     * @return java.lang.GString
     **/
    public static String getMD5(String var0) {
        return var0 == null ? "" : getMD5(var0.getBytes());
    }
    /**
     * @Author gaoqisen
     * @Description //TODO MD5加密 字节
     * @Date 2019-12-01 16:45
     * @Param [var0]
     * @return java.lang.GString
     **/
    public static String getMD5(byte[] var0) {
        String var1 = null;
        char[] var2 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest var3 = MessageDigest.getInstance("MD5");
            var3.update(var0);
            byte[] var4 = var3.digest();
            char[] var5 = new char[32];
            int var6 = 0;

            for(int var7 = 0; var7 < 16; ++var7) {
                byte var8 = var4[var7];
                var5[var6++] = var2[var8 >>> 4 & 15];
                var5[var6++] = var2[var8 & 15];
            }

            var1 = new String(var5);
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return var1;
    }
}
