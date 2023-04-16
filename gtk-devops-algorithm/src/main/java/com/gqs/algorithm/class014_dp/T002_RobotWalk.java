package com.gqs.algorithm.class014_dp;

// 机器人走路
public class T002_RobotWalk {

    public static void main(String[] args) {
        System.out.println(process (5, 2,6,4));
        System.out.println(process1(5, 2,6,4));
        System.out.println(process2(5, 2,6,4));
    }

    /**
     * 机器人递归计算方式
     *
     * 1、机器人只能一步一步走
     * 2、如果机器人在位置1，那么下一步只能到位置2
     * 3、如果机器人在位置n，那么下一步只能到位置n-1
     * 4、如果机器人位置在中间，那么下一步能到n+1和n-1的位置
     *
     * @param n 排成一行的n个位置
     * @param m 机器人的当前位置
     * @param k 机器人能走的步数
     * @param p 需要到达的目标位置
     * @return 有多少种方法能达到目标位置
     */
    public static int process(int n, int m, int k, int p) {
        // 步数走完,如果到达目标位置则当前方法有效
        if(k == 0) {
            return m == p ? 1 : 0;
        }
        // 机器人在位置1只能到m+1位置
        if(m == 1) {
            return process(n, 2, k - 1, p);
        }
        // 机器人在位置n只能到m-1位置
        if(m == n) {
            return process(n, m - 1, k - 1, p);
        }
        // 机器人在中间位置则步数 =（m+1位置的数量） + （m-1位置的数量）
        return process(n, m + 1, k - 1, p) + process(n, m - 1, k - 1, p);
    }

    /**
     * 机器人缓存计算方式
     *
     * 1、机器人只能一步一步走
     * 2、如果机器人在位置1，那么下一步只能到位置2
     * 3、如果机器人在位置n，那么下一步只能到位置n-1
     * 4、如果机器人位置在中间，那么下一步能到n+1和n-1的位置
     *
     * @param n 排成一行的n个位置
     * @param m 机器人的当前位置
     * @param k 机器人能走的步数
     * @param p 需要到达的目标位置
     * @return 有多少种方法能达到目标位置
     */
    public static int process1(int n, int m, int k, int p) {
        // 初始化数据，-1的表示没有缓存
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = -1;
            }
        }

        return processCache(n, m, k, p, dp);
    }

    // 缓存DP
    private static int processCache(int n, int m, int k, int p, int[][] dp) {
        // 如果存在缓存则返回缓存数据
        if(dp[n][k] != -1) {
            return dp[n][k];
        }

        int walks = 0;
        // 步数走完,如果到达目标位置则当前方法有效
        if(k == 0) {
            walks = m == p ? 1 : 0;
        }
        // 机器人在位置1只能到m+1位置
        else if(m == 1) {
            walks = process(n, 2, k - 1, p);
        }
        // 机器人在位置n只能到m-1位置
        else if(m == n) {
            walks = process(n, m - 1, k - 1, p);
        }
        // 机器人在中间位置则步数 =（m+1位置的数量） + （m-1位置的数量）
        else {
            walks = process(n, m + 1, k - 1, p) + process(n, m - 1, k - 1, p);
        }
        dp[n][k] = walks;
        return walks;
    }

    /**
     * 通过递归分析后可以直接生成DP表，生成之后直接获取值即可
     *
     * @param n 排成一行的n个位置
     * @param m 机器人的当前位置
     * @param k 机器人能走的步数
     * @param p 需要到达的目标位置
     * @return 有多少种方法能达到目标位置
     */
    public static int process2(int n, int m, int k, int p) {
        int[][] dp = new int[n + 1][k + 1];
        // 目标位置k=0时候，n=4的值为1，其余的n为0
        dp[p][0] = 1;
        // 遍历步数，一列一列计算，第一列以及获取到值
        for (int i = 1; i <= k; i++) {
            // 机器人当前位置是1时，需要走的总步数依赖n=2，k-1。
            dp[1][i] = dp[2][i -1];
            // 机器人当前位置是n时，需要走的总步数依赖n-1，k-1。
            dp[n][i] = dp[n-1][i-1];
            // 位置n等于0的一直不会有值,n=1的也计算过，故从n=2开始
            for (int j = 2; j < n; j++) {
                // 机器人当前位置是其他值时，需要走的总步数是【n=2，k-1】的步数加上【n-1，k-1】的步数
                dp[j][i] = dp[j-1][i-1] + dp[j+1][i-1];
            }

        }
        // 直接返回位置上的数据即可
        return dp[m][k];
    }

}
