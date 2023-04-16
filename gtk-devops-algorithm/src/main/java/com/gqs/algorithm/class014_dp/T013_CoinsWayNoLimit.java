package com.gqs.algorithm.class014_dp;

public class T013_CoinsWayNoLimit {

    public static int coinWays(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    /**
     * 获取index下标的面值组成方法数据
     *
     * @param arr 面值数组
     * @param index 当前处理值
     * @param rest 剩余金额
     * @return 所有组成方法数
     */
    private static int process(int[] arr, int index, int rest) {
        // 到了最后一个下标（没钱了）
        if(index == arr.length) {
            // 刚好达到目标则存在一种方法数
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        // 当前要0张～（张数*当前面值<=剩余金额）
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            // 递归获取后面的所有情况，并进行累加
            ways += process(arr, index + 1, rest - (zhang * arr[index]));
        }
        return ways;
    }

    /**
     * 动态规划处理面值计算
     *
     * @param arr 所有的面值
     * @param aim 需要计算的金额
     * @return 所有的方法数
     */
    public static int dp(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0)  {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;

        for (int index = n - 1; index >= 0 ; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    // 递归获取后面的所有情况，并进行累加
                    ways += dp[index + 1][rest - (zhang * arr[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }


    /**
     * 动态规划处理面值计算（通过观察继续优化减掉for循环）
     *
     * @param arr 所有的面值
     * @param aim 需要计算的金额
     * @return 所有的方法数
     */
    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0)  {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;

        for (int index = n - 1; index >= 0 ; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 获取下一个格子的值
                dp[index][rest] = dp[index + 1][rest];
                // 如果当前左边格子的值>=0
                if(rest - arr[index] >= 0) {
                    // 则加上左边格子的值
                    dp[index][rest] += dp[index][rest - arr[index]];
                }
            }
        }
        return dp[0][aim];
    }



    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays(arr, aim);
            int ans2 = dp(arr, aim);
            int ans3 = dp1(arr, aim);
            if (ans1 != ans2
                    || ans1 != ans3
            ) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
