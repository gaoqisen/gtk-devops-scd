package com.gqs.algorithm.class014_dp;

// 数字且人
public class T018_SplitNumber {

    public static int ways(int n) {
        if(n < 0) {
            return  0;
        }
        if(n == 1) {
             return 1;
        }
        return execute(1, n);
    }

    /**
     * 递归拆分数字
     *
     * @param pre 上一个拆出来的数
     * @param rest 剩余需要拆的数
     * @return 返回拆解的方法数
     */
    private static int execute(int pre, int rest) {
        // 最后剩余0，则是一种有效的方法
        if(rest == 0) {
            return 1;
        }
        // 拆分的时候前面的数不能比后面的大
        if (pre > rest) {
            return 0;
        }

        int ways = 0;
        // 从pre开始
        for (int first = pre; first <= rest; first++) {
            ways += execute(first, rest - first);
        }
        return ways;
    }

    /**
     * 动态规划获取拆分方式
     *
     * @param n 需要拆的数
     * @return 拆分的方法数
     */
    public static int dp(int n) {
        if (n < 0) {
            return 0;
        }
        // 等于就是存在一种有效方法
        if(n == 1) {
            return 1;
        }

        // 基础条件赋初始值
        int[][] dp = new int[n+1][n+1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }

        // 遍历pre
        for (int pre  = n - 1; pre >= 1; pre--) {
            // rest从pre往后一个开始（rest=pre已经填好了）
            for (int rest = pre + 1; rest <=n ; rest++) {
                int ways = 0;
                for (int first = pre; first <= rest; first++) {
                    ways += dp[first][rest - first];
                }
                dp[pre][rest] = ways;
            }
        }


        return dp[1][n];
    }

    /**
     * 动态规划获取拆分方式（空间压缩）
     *
     * @param n 需要拆的数
     * @return 拆分的方法数
     */
    public static int dp1(int n) {
        if (n < 0) {
            return 0;
        }
        // 等于就是存在一种有效方法
        if(n == 1) {
            return 1;
        }

        // 基础条件赋初始值
        int[][] dp = new int[n+1][n+1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }

        // 遍历pre
        for (int pre  = n - 1; pre >= 1; pre--) {
            // rest从pre往后一个开始（rest=pre已经填好了）
            for (int rest = pre + 1; rest <=n ; rest++) {
                // 需要观察二维表找到优化空间
                dp[pre][rest] = dp[pre + 1][rest];
                dp[pre][rest] += dp[pre][rest -pre];
            }
        }
        return dp[1][n];
    }


    public static void main(String[] args) {
        System.out.println(ways(13));
        System.out.println(dp(13));
        System.out.println(dp1(13));


    }

}
