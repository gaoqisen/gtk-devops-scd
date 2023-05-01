package com.gqs.algorithm.class014_dp;

public class T020_SplitSumClosedSizeHalf {

    public static int splitSum(int[] arr) {
        if(arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        // 偶数
        if((arr.length & 1) == 0) {
            return execute(arr, 0, sum / 2, arr.length / 2);
        }
        // 奇数
        return Math.max(execute(arr, 0, sum / 2, arr.length / 2),
                execute(arr, 0, sum / 2, arr.length / 2 + 1));

    }

    /**
     * 递归获取最接近中间数的累加和
     *
     * @param arr 给定数组
     * @param index 当前处理下标
     * @param rest 剩余累加和
     * @param picks 挑选的值
     * @return 最接近中间数的累加和
     */
    private static int execute(int[] arr, int index, int rest, int picks) {
        // 处理到最后一个值
        if(index == arr.length) {
            // 如果个数刚好结束则返回0，否则返回无效（-1）
            return picks == 0 ? 0 : -1;
        }
        // 不要当前值
        int p1 = execute(arr, index + 1, rest, picks);

        // 要当前值
        int next = -1;
        int p2 = -1;
        // 只有当前值小于等于剩余中间值是才能要当前值
        if(arr[index] <= rest) {
            // 递归要后面的值
            next = execute(arr, index + 1, rest - arr[index], picks - 1);
            // 值有效时才进行值累加(无效值过滤)
            if(next != -1) {
                p2 = arr[index] + next;
            }
        }
        // 获取最接近中间值的最大情况
        return Math.max(p1, p2);
    }


    public static int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        int n = arr.length;
        // 当前已经除以2，下面不用在除
        sum = sum / 2;

        int pick = n + 1 / 2;
        int[][][] dp = new int[n + 1][sum + 1][pick + 1];

        // 将所有值置为无效
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= sum; j++) {
                for (int k = 0; k <= pick; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        // base case赋值
        for (int i = 0; i <= sum; i++) {
            dp[n][i][0] = 0;
        }

        for (int index = n - 1; index >= 0 ; index--) {
            for (int rest = 0; rest <= sum; rest++) {
                for (int picks = 0; picks <= pick; picks++) {

                    // 不要当前值
                    int p1 = dp[index + 1][rest][picks];

                    // 要当前值
                    int next = -1;
                    int p2 = -1;
                    // 只有当前值小于等于剩余中间值是才能要当前值
                    if(picks - 1 >= 0 && arr[index] <= rest) {
                        // 递归要后面的值
                        next = dp[index +1][rest - arr[index]][picks - 1];
                        // 值有效时才进行值累加(无效值过滤)
                        if(next != -1) {
                            p2 = arr[index] + next;
                        }
                    }
                    // 获取最接近中间值的最大情况
                    dp[index][rest][picks] = Math.max(p1, p2);
                }
            }
        }
        // 偶数
        if((arr.length & 1) == 0) {
            return dp[0][sum][arr.length / 2];
        }
        // 奇数
        return Math.max(dp[0][sum][arr.length / 2],
                dp[0][sum][arr.length / 2 + 1]);
    }



    // for test
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = splitSum(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2
                  //  ||  ans1 != ans3
            ) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
            //    System.out.println(ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
