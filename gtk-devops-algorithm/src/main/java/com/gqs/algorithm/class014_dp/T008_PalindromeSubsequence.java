package com.gqs.algorithm.class014_dp;

// 测试链接：https://leetcode.com/problems/longest-palindromic-subsequence/
public class T008_PalindromeSubsequence {

    public static int longestPalindromeSubseq(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }
        return process(str.toCharArray(), 0, str.length() - 1);
    }

    private static int process(char[] chars, int l, int r){
        // 只有一个字符：（一个字符就是回文），则返回1
        if(l == r) {
            return 1;
        }

        // 两个字符：两个字符相等则是回文，否则不是
        if(l == r - 1) {
            return chars[l] == chars[r] ? 2 : 1;
        }
        // 多个字符则获取每种情况的结果的最大值（max）

        // 不已L结尾,也不已R结尾。两个都不要，直接往中间走 L + 1, R - 1
        int p1 = process(chars, l + 1, r - 1);
        // 已L结尾，不已R结尾.。则R往前走 L , R - 1
        int p2 = process(chars, l, r - 1);
        // 不已L结尾，已R结尾。则L往前走L + 1, R
        int p3 = process(chars, l + 1, r);
        // 已L结尾，也已R结尾。两个字符相等才存在回文，2是两个数都要
        int p4 = chars[l] != chars[r] ? 0 : 2 + process(chars, l + 1, r - 1);
        return Math.max(p1, Math.max(p2, Math.max(p3, p4)));
    }


    public static int dp(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int n = chars.length;
        int[][] dp = new int[n][n];
        // 先将最后一个位置赋值为1
        dp[n - 1][n - 1] = 1;
        // 遍历到n - 1的前一个位置
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = chars[i] == chars[i + 1] ? 2 : 1;
        }

        // l的前两位已经赋值了，则从后面第三个开始
        for (int l = n - 3; l >= 0; l--) {
            // 从l往后2个位置开始遍历
            for (int r = l + 2; r < n ; r++) {
                // 不已L结尾,也不已R结尾。两个都不要，直接往中间走 L + 1, R - 1
                int p1 = dp[l + 1][r - 1];
                // 已L结尾，不已R结尾.。则R往前走 L , R - 1
                int p2 = dp[l][r - 1];
                // 不已L结尾，已R结尾。则L往前走L + 1, R
                int p3 = dp[l + 1][r];
                // 已L结尾，也已R结尾。两个字符相等才存在回文，2是两个数都要
                int p4 = chars[l] != chars[r] ? 0 : (2 + dp[l + 1][r - 1]);
                dp[l][r] = Math.max(p1, Math.max(p2, Math.max(p3, p4)));
            }
        }
        return dp[0][n - 1];
    }


    public static int dp1(String str) {
        if(str == null || str.isEmpty()) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int n = chars.length;
        int[][] dp = new int[n][n];
        // 先将最后一个位置赋值为1
        dp[n - 1][n - 1] = 1;
        // 遍历到n - 1的前一个位置
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = chars[i] == chars[i + 1] ? 2 : 1;
        }

        // l的前两位已经赋值了，则从后面第三个开始
        for (int l = n - 3; l >= 0; l--) {
            // 从l往后2个位置开始遍历
            for (int r = l + 2; r < n ; r++) {
                // 已L结尾，不已R结尾.。则R往前走 L , R - 1
                int p2 = dp[l][r - 1];
                // 不已L结尾，已R结尾。则L往前走L + 1, R
                int p3 = dp[l + 1][r];

                dp[l][r] = Math.max(p2, p3);
                if(chars[l] == chars[r]) {
                    dp[l][r] = Math.max(dp[l][r], (2 + dp[l + 1][r - 1]));
                }
            }
        }
        return dp[0][n - 1];
    }

}
