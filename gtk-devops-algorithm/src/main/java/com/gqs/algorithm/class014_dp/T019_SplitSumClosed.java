package com.gqs.algorithm.class014_dp;

public class T019_SplitSumClosed {


    public static int splitNum(int[] arr) {
        if(arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return execute(arr, 0, sum / 2);
    }

    /**
     * 递归找到离中间值最接近的值
     *
     * @param arr 原始数组
     * @param index 当前处理到的下标
     * @param rest 剩余的中点数
     * @return 剩余的中点数
     */
    private static int execute(int[] arr, int index, int rest) {
        // 计算到最后了
        if(index == arr.length) {
            return 0;
        }
        // 第一种情况：不获取当前值
        int p1 = execute(arr, index + 1, rest);

        // 第二种情况: 使用当前值
        int p2 = 0;
        // 当前值小于中点数
        if(arr[index] <= rest) {
            p2 = arr[index] + execute(arr, index + 1, rest - arr[index]);
        }
        // 获取更接近中点的数
        return Math.max(p1, p2);
    }


    public static int dp(int[] arr) {
        if(arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        // 获取中间值
        sum /= 2;
        int n = arr.length;
        int[][] dp = new int[n + 1][sum + 1];
        for (int index = n - 1; index  >= 0 ; index--) {
            for (int rest = 0; rest <= sum; rest++) {
                // 第一种情况：不获取当前值
                int p1 = dp[index + 1][rest];
                // 第二种情况: 使用当前值
                int p2 = 0;
                // 当前值小于中点数
                if(arr[index] <= rest) {
                    p2 = arr[index] + dp[index + 1][rest - arr[index]];
                }
                // 获取更接近中点的数
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][sum];
    }

    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = splitNum(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }


}
