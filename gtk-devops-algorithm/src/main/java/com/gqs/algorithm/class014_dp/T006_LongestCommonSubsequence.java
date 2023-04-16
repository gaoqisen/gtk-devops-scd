package com.gqs.algorithm.class014_dp;

// 链接：https://leetcode.com/problems/longest-common-subsequence/
public class T006_LongestCommonSubsequence {

    public static int longestCommonSubsequence(String s1, String s2) {
        if(s1 == null || s1.isEmpty() || s2 == null || s2.isEmpty()) {
            return 0;
        }
        return process(s1.toCharArray(), s2.toCharArray(), s1.length() - 1, s2.length() - 1);
    }

    private static int process(char[] str1, char[] str2, int i, int j) {
        if(i == 0 && j== 0) {
            return str1[i] == str2[j] ? 1 : 0;
        }
        // 第一个样本只有1个字符
        else if(i == 0) {
            // 这个字符和第二个字符串的最后一个字符相等则返回1
            if(str1[i] == str2[j]) {
                return 1;
            } else {
                // 否则递归获取第一个字符j-1的最长公共子序列
                return process(str1, str2, i, j - 1);
            }
        }
        // 第二个样本只有一个字符
        else if(j == 0) {
            if(str1[i] == str2[j]) {
                return 1;
            } else {
                return process(str1, str2, i - 1, j);
            }
        } else {
            // 不考虑i位置以公共子序列结尾，可能考虑j位置以公共子序列结尾
            int p1 = process(str1, str2, i - 1, j);
            // 不考虑j位置以公共子序列结尾，可能考虑i位置以公共子序列结尾
            int p2 = process(str1, str2, i, j - 1);
            // 就以i和j位置以公共子序列结尾
            int p3 = str1[i] == str2[j] ? (1 + process(str1, str2, i - 1, j -1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    /**
     * 动态规划找到最大公共子序列
     *
     * @param s1 字符串a
     * @param s2 字符串b
     * @return
     */
    public static int longestCommonSubsequence1(String s1, String s2) {
        if (s1 == null || s1.isEmpty() || s2 == null || s2.isEmpty()) {
            return 0;
        }
        // 创建动态规划表
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int n = str1.length;
        int m = str2.length;
        int[][] dp = new int[n][m];

        // 通过递归直接计算第一个值
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;

        // 从下标1开始计算i的值（列）
        for (int i = 1; i < n; i++) {
            dp[i][0] = str1[i]== str2[0] ? 1 : dp[i -1][0];
        }
        // 从下标1开始计算j的值（行）
        for (int j = 1; j < m; j++) {
            dp[0][j] = str1[0]== str2[j] ? 1 : dp[0][j -1];
        }
        // 计算其他情况
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                // 不考虑i位置以公共子序列结尾，可能考虑j位置以公共子序列结尾
                int p1 = dp[i - 1][j];
                // 不考虑j位置以公共子序列结尾，可能考虑i位置以公共子序列结尾
                int p2 = dp[i][j - 1];
                // 就以i和j位置以公共子序列结尾
                int p3 = str1[i] == str2[j] ? (1 + dp[i - 1][j -1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }

        return dp[n-1][m-1];
    }

}
