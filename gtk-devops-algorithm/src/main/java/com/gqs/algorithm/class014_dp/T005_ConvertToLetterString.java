package com.gqs.algorithm.class014_dp;

// 转化最小字符
public class T005_ConvertToLetterString {

    public static void main(String[] args) {
        System.out.println(convert("305"));
        System.out.println(dp("305"));
    }

    public static int convert(String str) {
        if(str == null || str.length() == 0) {
            return 0;
        }

        return process(str.toCharArray(), 0);
    }

    /**
     * 递归尝试（没给字符都和后面的所有字符匹配）
     *
     * @param chars 所有的字符
     * @param index 当前是第几个字符
     * @return 匹配到的字符数量
     */
    private static int process(char[] chars, int index) {
        // 走到最后一个字符了，说明前面一直有字符!='0',则当前字符匹配上
        if(chars.length == index) {
            return 1;
        }

        // 只有1～26个字符，如果存在0字符则当前分支未匹配上
        if(chars[index] == '0') {
            return 0;
        }

        // 递归获取下一个字符的所有匹配数
        int val = process(chars, index +1);
        // 没有越界
        if(index + 1 < chars.length
                // 当前未和下一位的的和小于27
                && (chars[index] - '0') * 10 + chars[index + 1] - '0' < 27) {
            val += process(chars, index + 2);
        }
        return val;
    }

    /**
     * 动态规划处理
     *
     * @param str 需要处理的字符串
     * @return 匹配的数量
     */
    public  static int dp(String str) {
        if(str == null || str.isEmpty()){
            return 0;
        }
        // 创建一维dp数组表
        char[] chars = str.toCharArray();
        int n = chars.length;
        int[] dp = new int[n + 1];
        // 根据递归函数得知下标为n的值为1
        dp[n] = 1;
        // 从右往左遍历，从n-1开始
        for (int index = n -1; index >= 0; index--) {
            // 只有1～26个字符，如果存在0字符则当前分支未匹配上
            if(chars[index] == '0') {
                continue;
            }
            // 递归方式是进行递归，此处从dp表获取
            int val = dp[index +1];
            // 没有越界
            if(index + 1 < chars.length
                    // 当前未和下一位的的和小于27
                    && (chars[index] - '0') * 10 + chars[index + 1] - '0' < 27) {
                // 递归方式是进行递归，此处从dp表获取
                val += dp[index + 2];
            }
            dp[index] = val;
        }
        // 根据递归主函数得知获取下标0的值即可
        return dp[0];
    }

}
