package com.gqs.algorithm.class014_dp;

// 背包问题
public class T004_Knapsack {

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7};
        int[] values = { 5, 6, 3, 19};
        int bag = 6;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }

    public static int maxValue(int[] w, int[] v, int bag) {
        // 数据有效检查
        if(w == null || v == null || w.length != v.length || w.length < 1) {
            return 0;
        }
        // 下标从0开始计算货物最大价值
        return process(w, v, 0, bag);
    }

    /**
     * 递归处理背包问题
     *
     * @param w 重量数组
     * @param v 价值数组
     * @param index 当前处理货物的下标
     * @param bag 当前背包数量
     * @return 当前下标往后的最大价值, -1表示背包放不下了
     */
    private static int process(int[] w, int[] v, int index, int bag) {
        // 背包装完了
        if(bag < 0) {
            return -1;
        }
        // 货物装完了
        if(index == w.length) {
            return 0;
        }

        // 不要当前的货物
        int p1 = process(w, v, index + 1, bag);
        // 要当前的获取
        int p2 = process(w, v, index + 1, bag - w[index]);
        // 如果背包容量不够则价值为0，否则价值等于下一个最大价值加上当前下标价值
        p2 = p2 == -1 ? 0 : p2 + v[index];
        return Math.max(p1, p2);
    }

    public static int dp(int[] w, int[] v, int bag) {
        // 数据有效检查
        if (w == null || v == null || w.length != v.length || w.length < 1) {
            return 0;
        }
        int n = w.length;
        int[][] dp = new int[n + 1][bag + 1];
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {
                int p1 = dp[index + 1][rest];
                // 如果背包容量不够则价值为0，否则价值等于下一个最大价值加上当前下标价值
                int p2 = 0;
                // 剩余背包重量
                int re = rest - w[index];
                if(re >= 0) {
                    // 当前货物价值加上index+1的价值
                    p2 = dp[index + 1][re] + v[index];
                }
                // 赋值
                dp[index][rest] = Math.max(p1, p2);

               // dp[index][rest] = Math.max(dp[index + 1][rest], (rest - w[index]) >= 0 ? dp[index + 1][rest - w[index]] + v[index] : 0);

            }
        }
        return dp[0][bag];
    }
}
