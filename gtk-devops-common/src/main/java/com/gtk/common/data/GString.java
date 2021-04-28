package com.gtk.common.data;

import org.springframework.util.StringUtils;

import java.security.SecureRandom;

/**
 * @Date 2019-12-04
 * @Version 1.0
 */
public class GString {

    /**
     * *替代证件号中间值(通用)
     * @Date 2019-12-04 09:26
     * @Param [str]
     * @return String
     **/
    public static String getAsteriskInfo(String str) {
        int length = str.length();
        int le = length/3;
        StringBuilder newer = new StringBuilder();
        for (int i = 0; i < le; i++) {
            newer.append("*");
        }
        if(le>0){
            str = str.substring(0, le) + newer + str.substring(2*le);
        }
        return str;
    }

    /**
     * 校验是否是中文
     * @Date 2019-12-04 09:29 
     * @Param [str]
     * @return boolean
     **/
    public static boolean checkIsChinese(String str){
        String reg = "[\\u4e00-\\u9fa5]+";
        return str.matches(reg);
    }

    /**
     * 获取身份证号后六位
     * @Date 2019-12-04 09:58
     * @Param [cardno]
     * @return java.lang.String
     **/
    public static String getCardNoLastSixChar(String cardno){
        if(StringUtils.isEmpty(cardno)||cardno.length()<15||cardno.length()>18||cardno.length()==17){
            return "000000";
        }
        return cardno.substring(12);
    }

    /**
     * 生成随机字符串的长度
     * @Date 2019-12-04 13:15
     * @Param [length]
     * @return java.lang.String
     **/
    public static String randomString(int length) {
        char[] CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        SecureRandom secRandom = new SecureRandom();
        StringBuilder sRand;
        sRand = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            int randomInt = secRandom.nextInt(CHARS.length);
            String rand = String.valueOf(CHARS[randomInt]);
            sRand.append(rand);
        }
        return sRand.toString();
    }

    /**
     * 生成随机字符串的长度
     * @Date 2019-12-04 13:17
     * @Param [length]
     * @return java.lang.String
     **/
    public static String randomNumString(int length) {
        char[] CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        SecureRandom secRandom = new SecureRandom();
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            int randomInt = secRandom.nextInt(CHARS.length);
            String rand = String.valueOf(CHARS[randomInt]);
            sRand.append(rand);
        }
        return sRand.toString();
    }

    /**
     * 生成随机字符串的长度
     * @Date 2019-12-04 13:17
     * @Param [length]
     * @return java.lang.String
     **/
    public static String randomLetterString(int length) {
        char[] CHARS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        SecureRandom secRandom = new SecureRandom();
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            int randomInt = secRandom.nextInt(CHARS.length);
            String rand = String.valueOf(CHARS[randomInt]);
            sRand.append(rand);
        }
        return sRand.toString();
    }

    /**
     * 生成随机账号
     * @Date 2019-12-04 13:17
     * @Param []
     * @return java.lang.String
     **/
    public static String randomLoginName() {
        return randomLetterString(2)+randomNumString(8);
    }

}
