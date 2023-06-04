package com.gqs.algorithm.class_019_Manacher;

public class T002_AddShortestEnd {

    /**
     * 获取最大回文子串长度
     *
     * @param s 字符串
     * @return 最大回文子串
     */
    public static String manacher(String s) {
        if(s == null || s.isEmpty()) {
            return "";
        }

        // 1、将字符串转化为用特殊字符隔开，任何字符都可以，在处理字符的时候不会处理到特殊字符
        char[] str = getChars(s);

        // 回文半径数组（回文半径中的最大值/2就是返回结果）
        int[] pArr = new int[str.length];
        // 当前最右回文右边界R的回文中心点
        int C = -1;
        // 最右回文右边界(R)：只要取得了最大的右边界则R往最右边移动
        int R = -1;
        // 最长包含结束位置
        int maxContainsEnd = -1;
        for (int i = 0; i < str.length; i++) {
            /*
            R > i: i在R内，则获取最小的不用验证的区域。  否则第二种情况。 否则i在R外的最少回文半径是1
            2 * C - i: i对称值的回文半径
            R -i： 当前值(i)的对称值在L~R外， 则当前值(i)的回文半径就是i～R的长度
             */
            pArr[i] = R > i ? Math.min(pArr[2 * C -  i], R -i) : 1;
            // 对比后面的值(当前i的回文数据左右两边都存在值时)
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                // 如果左边和右边的值相等，则回文半径往后扩展，否则结束扩展
                if(str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            // R的值往后扩
            if(i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            if(R == str.length) {
                maxContainsEnd = pArr[i];
                break;
            }
        }
        // 将结束位置前面的字符逆序就是需要添加的字符
        char[] res = new char[s.length() - maxContainsEnd + 1];
        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = str[i * 2 + 1];
        }
        return String.valueOf(res);
    }

    private static char[] getChars(String str) {
        char[] mStr = new char[str.length() * 2 + 1];
        char[] chars = str.toCharArray();
        int index = 0;
        for (int i = 0; i != mStr.length; i++) {
            // 奇数&1为1，偶数&1为0
            mStr[i] = (i & 1) == 0 ? '#' : chars[index++];
        }
        return mStr;
    }


}
