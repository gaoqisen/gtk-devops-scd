package com.gqs.algorithm.class014_dp;

public class T012_CoinsWayEveryPaperDifferent {


    public static int coinWays(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    private static int process(int[] arr, int index, int rest) {
        if(rest < 0) {
            return 0;
        }
        // 数组匹配完
        if(index == arr.length) {
            // 刚好达到目标则方法数为1，否则为0
            return rest == 0 ? 1 : 0;
        }
        // 不要index的钱 + 要index的钱
        return process(arr, index + 1, rest) + process(arr, index + 1, rest - arr[index]);
    }

    public static int dp(int[] arr, int aim) {
        if(aim == 0) {
            return 1;
        }

        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        // index=n时，rest=0时等于1
        dp[n][0] = 1;
        // index都依赖jindex+1的值，则从后往前遍历
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] =
                        // 不要当前值
                        dp[index + 1][rest]
                        // 要当前值，判断是否越界，越界则返回0
                         + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }
        return dp[0][aim];
    }


    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
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
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays(arr, aim);
            int ans2 = dp(arr, aim);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
