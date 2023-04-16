package com.gqs.algorithm.class014_dp;

// 最小面值不限制
public class T017_MinCoinsNoLimit {

    /**
     * 获取最小面值数量
     *
     * @param arr 所有的面值
     * @param aim 目标金额
     * @return 最小的面值数量
     */
    public static int minCoins(int[] arr, int aim) {
        return execute(arr, 0, aim);
    }

    /**
     * 递归执行获取最小面值数量
     *
     * @param arr 所有的面值
     * @param index 当前面值张数
     * @param rest 剩余金额
     * @return 最小面值
     */
    private static int execute(int[] arr, int index, int rest){
        // 最后一张
        if(index == arr.length) {
            // 没钱了则返回0张
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        // 遍历张数 当前张数*面值<= 剩余金额
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            // 递归处理后面的值
            int next = execute(arr, index + 1, rest - zhang * arr[index]);
            if(next != Integer.MAX_VALUE){
                ans = Math.min(ans, next + zhang);
            }
        }
        return ans;
    }

    /**
     * 获取最小面值数量
     *
     * @param arr 所有的面值
     * @param aim 目标金额
     * @return 最小的面值数量
     */
    public static int dp(int[] arr, int aim) {
        if(aim == 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        // 最后一张处理
        for (int i = 1; i <= aim; i++) {
            dp[n][i] = Integer.MAX_VALUE;
        }
        for (int index = n - 1; index >= 0 ; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ans = Integer.MAX_VALUE;
                // 遍历张数 当前张数*面值<= 剩余金额
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    // 递归处理后面的值
                    int next = dp[index + 1][rest - zhang * arr[index]];
                    if(next != Integer.MAX_VALUE){
                        ans = Math.min(ans, next + zhang);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][aim];
    }


    /**
     * 获取最小面值数量
     *
     * @param arr 所有的面值
     * @param aim 目标金额
     * @return 最小的面值数量
     */
    public static int dp1(int[] arr, int aim) {
        if(aim == 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        // 最后一张处理
        for (int i = 1; i <= aim; i++) {
            dp[n][i] = Integer.MAX_VALUE;
        }
        for (int index = n - 1; index >= 0 ; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 先赋值格子下面的值
                dp[index][rest] = dp[index + 1][rest];
                // 不越界
                if(rest - arr[index] < 0){
                    continue;
                }
                // 过滤无效值
                if(dp[index][rest - arr[index]] == Integer.MAX_VALUE) {
                    continue;
                }
                // 根据公式转化
                dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
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
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp(arr, aim);
            int ans3 = dp1(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");
    }

}
