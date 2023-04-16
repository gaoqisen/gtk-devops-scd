package com.gqs.algorithm.class014_dp;

public class T009_HorseJump {

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        System.out.println(jump(x, y, step));
        System.out.println(dp(x, y, step));
    }

    public static int jump(int x, int y, int k) {
        return process(0, 0, k, x, y);
    }

    /**
     * 递归获取马跳到目标格子的所有情况
     *
     * 棋盘横9纵10
     *
     * @param a 开始的x位置
     * @param b 开始的y位置
     * @param rest 剩余跳的步数
     * @param x 目标的x位置
     * @param y 目标的y位置
     * @return 所有的情况
     */
    private static int process(int a, int b, int rest, int x, int y) {
        if(a < 0 || b < 0 || a > 9 || b > 8) {
            return 0;
        }
        // 步数结束到达目标位置则存在一种方法
        if(rest == 0) {
            return (a == x && b == y) ? 1 : 0;
        }

        // 格子跳有8种情况汇总就是所有的情况
        int ways = process(a + 2, b + 1, rest - 1, x, y);
        ways += process(a + 1, b + 2, rest - 1, x, y);
        ways += process(a - 1,  b + 2, rest - 1, x, y);
        ways += process(a - 2, b + 1, rest - 1, x, y);
        ways += process(a - 2, b - 1, rest - 1, x, y);
        ways += process(a - 1, b - 2, rest - 1, x, y);
        ways += process(a + 1, b - 2, rest - 1, x, y);
        ways += process(a + 2, b - 1, rest - 1, x, y);
        return ways;
    }

    /**
     * 动态规划获取马的跳转步数
     *
     * @param x x目标位置
     * @param y y目标位置
     * @param k 能跳的步数
     * @return 所有能跳过去的总情况数
     */
    public static int dp(int x, int y, int k) {
        int[][][] dp = new int[10][9][k + 1];
        // 步数结束到达目标位置则存在一种方法
        dp[x][y][0] = 1;

        // 遍历步数，从1开始，0已经赋值了
        for (int rest = 1; rest <= k; rest++) {
            // 从左往右处理x值
            for (int a = 0; a < 10; a++) {
                // 从左往右处理y值
                for (int b = 0; b < 9; b++) {
                    int ways = pick(dp, a + 2, b + 1, rest - 1);
                    ways += pick(dp,a + 1, b + 2, rest - 1);
                    ways += pick(dp,a - 1,  b + 2, rest - 1);
                    ways += pick(dp,a - 2, b + 1, rest - 1);
                    ways += pick(dp,a - 2, b - 1, rest - 1);
                    ways += pick(dp,a - 1, b - 2, rest - 1);
                    ways += pick(dp,a + 1, b - 2, rest - 1);
                    ways += pick(dp,a + 2, b - 1, rest - 1);
                    dp[a][b][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    /**
     * 获取参数值，如果超出边界则返回0
     */
    private static int pick(int[][][] dp, int a, int b, int k) {
        // 边界值处理
        if(a < 0 || b < 0 || a > 9 || b > 8) {
            return 0;
        }
        return dp[a][b][k];
    }
}
