package com.gqs.algorithm.class014_dp;

public class T016_KillMonster {


    /**
     * 砍怪兽
     *
     * @param n 剩余的血
     * @param m 每次的伤害0～m
     * @param k 还有k次可以砍
     * @return 返回砍死的概率
     */
    public static double kill(int n, int m, int k) {
        if(n < 1 || m < 1 || k < 1) {
            return 0;
        }

        // 所有会出现的情况
        long all = (long) Math.pow(m + 1, k);
        long kill = execute(k, m, n);
        return (double)kill / (double)all;
    }

    /**
     * 砍怪兽
     *
     * @param times 还有*次可以砍
     * @param m 每次的伤害0～m
     * @param hp 剩余的血
     * @return 砍死返回1
     */
    private static long execute(int times, int m, int hp) {
        if(times == 0) {
            return hp <= 0 ? 1 : 0;
        }
        // 如果没有血了，后面的所有情况都是被砍死
        if(hp <= 0) {
            return (long) Math.pow(m + 1, times);
        }
        long ways = 0;
        for (int i = 0; i <= m; i++) {
            ways += execute(times - 1 , m, hp - i);
        }
        return ways;
    }

    /**
     * 动态规划
     *
     * @param n 剩余的血
     * @param m 每次的伤害0～m
     * @param k 还有k次可以砍
     * @return 返回砍死的概率
     */
    public static double dp(int n, int m, int k) {
        if(n < 1 || m < 1 || k < 1) {
            return 0;
        }
        // 所有会出现的情况
        long all = (long) Math.pow(m + 1, k);
        // 创建dp表
        long[][] dp = new long[k + 1][n + 1];
        // 只有0位置的值是1
        dp[0][0] = 1;

        for (int times = 1; times <= k; times++) {
            // 如果没有血了，砍死数量就是后面的所有情况 (m + 1的times次方)
            dp[times][0] = (long) Math.pow(m + 1, times);

            for (int hp = 1; hp <= n; hp++) {
                long ways = 0;
                for (int i = 0; i <= m; i++) {
                    if(hp - i >= 0) {
                        ways += dp[times - 1 ][hp - i];
                    } else {
                        // 如果没有血了，砍死数量就是后面的所有情况
                        ways += (long)Math.pow(m + 1, times - 1);
                    }

                }
                dp[times][hp] = ways;
            }
        }

        // 所有会出现的情况
        long kill = dp[k][n];
        return (double)kill / (double)all;
    }


    /**
     * 动态规划(空间压缩)
     *
     * @param n 剩余的血
     * @param m 每次的伤害0～m
     * @param k 还有k次可以砍
     * @return 返回砍死的概率
     */
    public static double dp1(int n, int m, int k) {
        if(n < 1 || m < 1 || k < 1) {
            return 0;
        }
        // 所有会出现的情况
        long all = (long) Math.pow(m + 1, k);
        // 创建dp表
        long[][] dp = new long[k + 1][n + 1];
        // 只有0位置的值是1
        dp[0][0] = 1;

        for (int times = 1; times <= k; times++) {
            // 如果没有血了，砍死数量就是后面的所有情况 (m + 1的times次方)
            dp[times][0] = (long) Math.pow(m + 1, times);

            for (int hp = 1; hp <= n; hp++) {
                // 替换公式
                dp[times][hp] = dp[times][hp -1] + dp[times - 1][hp];
                // 拿到格子
                if(hp - 1 - m >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - m];
                }
                // 拿不到格子
                else {
                    // 存在负数则也需要减掉
                    dp[times][hp] -= Math.pow(m + 1, times - 1);
                }
            }
        }

        // 所有会出现的情况
        long kill = dp[k][n];
        return (double)kill / (double)all;
    }


    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = kill(N, M, K);
            double ans2 = dp(N, M, K);
            double ans3 = dp(N, M, K);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }


 }
