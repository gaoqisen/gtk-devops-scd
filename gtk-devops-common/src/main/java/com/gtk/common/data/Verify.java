package com.gtk.common.data;

/**
 * @ClassName Verify
 * @Author gaoqisen
 * @Date 2019-12-04
 * @Version 1.0
 */
public class Verify {

    /**
     * 检测是否中国手机号(台湾省、香港、澳门除外)
     * @Author gaoqisen
     * @Date 2019-12-04 09:57
     * @Param [mobile]
     * @return boolean
     **/
    public static boolean isChinaMobilePhoneNum(String mobile){
        boolean matches = false;
        String CHINA_TELE_PATTERN = "(^1(3[0-9]|4[13567]|5[0-35-9]|6[67]|7[0-8]|8[0-9]|9[189])\\d{8}$)|(^170[059]\\d{7}$)";
        boolean matches0 = mobile.matches(CHINA_TELE_PATTERN);
        if(matches0){
            matches = true;
        }
        return matches;
    }
    /**
     * 校验是否是用户名
     * @Author gaoqisen
     * @Date 2019-12-04 09:59
     * @Param [userName]
     * @return boolean
     **/
    public static boolean isUserName(String userName){
        String USERNAME_PATTERN = "(^[a-zA-Z][a-zA-Z0-9_]{3,29}$)";
        return  userName.matches(USERNAME_PATTERN);
    }

    /**
     * 检测是否有特殊字符（姓名校验）
     * @Author gaoqisen
     * @Date 2019-12-04 10:01
     * @Param [str]
     * @return boolean
     **/
    public static boolean isHaveSpecialChar(String str){
        String reg = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\.\\s\\·]+$";
        return !str.matches(reg);
    }

    /**
     * 密码检查 密码（8-20位数字和字母的组合）
     * (请输入8-20位大写字母，小写字母，数字和特殊字符其中任意三种或三种以上组合)
     * @Author gaoqisen
     * @Date 2019-12-04 13:12
     * @Param [pass]
     * @return boolean
     **/
    public static boolean isCheckPass(String pass) {
        String regex = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$^&*~-]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$^&*~-]+$)(?![0-9\\W_!@#$^&*~-]+$)[a-zA-Z0-9\\W_!@#$^&*~-]{8,20}$";
        return pass.matches(regex);
    }
}
