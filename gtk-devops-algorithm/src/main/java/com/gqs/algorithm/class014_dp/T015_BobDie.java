package com.gqs.algorithm.class014_dp;

public class T015_BobDie {

    /**
     * 获取醉汉的生存概率
     *
     * @param row 区域的行
     * @param col 区域的列
     * @param k 走k步
     * @param n 当前的行位置
     * @param k 当前的列位置
     * @return 生存概率
     */
    public static double liveProbability(int row, int col, int k, int n, int m){
        return (double)process(row, col, k, n, m) / Math.pow(4, k);
    }

    /**
     * 获取醉汉的所有生存情况
     *
     * @param row 区域的行
     * @param col 区域的列
     * @param rest 剩余的步数
     * @param n 当前的行位置
     * @param m 当前的列位置
     * @return 生存总数
     */
    private static int process(int row, int col, int rest, int n, int m) {
        // 超出边界则死亡
        if(row < 0 || row == n || col < 0 || col == m) {
            return 0;
        }
        // 步数走完则生存
        if(rest == 0) {
            return 1;
        }
        // 上下左右的情况都走
        int p1 = process(row, col, rest - 1, n - 1, m);
        int p2 = process(row, col, rest - 1, n + 1, m);
        int p3 = process(row, col, rest - 1, n, m - 1);
        int p4 = process(row, col, rest - 1, n, m + 1);
        return p1 + p2 + p3 + p4;
    }

    /**
     * 获取醉汉的生存概率
     *
     * @param row 区域的行
     * @param col 区域的列
     * @param k 走k步
     * @param n 当前的行位置
     * @param k 当前的列位置
     * @return 生存概率
     */
    public static double dp(int row, int col, int k, int n, int m){
        int[][][] dp = new int[row][col][k + 1];
        // 初始化位置k位置=0时，则生存
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int rest = 1; rest < k; rest++) {
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    // 计算上下左右的值
                    dp[r][c][rest] = pick(dp, row, col, n + 1, m, rest - 1);
                    dp[r][c][rest] += pick(dp, row, col, n - 1, m, rest - 1);
                    dp[r][c][rest] += pick(dp, row, col, n, m + 1, rest - 1);
                    dp[r][c][rest] += pick(dp, row, col, n, m - 1, rest - 1);
                }
            }
        }

        // 计算概率返回
        return (double)dp[n][m][k] / Math.pow(4, k);
    }

    // 超出位置则死亡，否则获取剩余步数的值
    private static int pick(int[][][] dp, int row, int col, int n, int m, int rest) {
        if(row < 0 || row == n || col < 0 || col == m) {
            return 0;
        }
        return dp[n][m][rest];
    }

}
