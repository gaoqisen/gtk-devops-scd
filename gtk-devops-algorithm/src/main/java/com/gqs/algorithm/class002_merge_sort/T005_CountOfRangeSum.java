package com.gqs.algorithm.class002_merge_sort;

public class T005_CountOfRangeSum {

    public static void main(String[] args) {
        int[] arr = {-2, 5, -1};
        System.out.println(countRangeSum(arr, -2, 2));
    }

    public static int countRangeSum(int[] arr, int lower, int upper) {
        if(arr == null || arr.length ==0) {
            return 0;
        }

        // 1、用辅助数组记录所有的位置上面的累加和
        long[] sum = new long[arr.length];
        sum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sum[i] = sum[i-1] + arr[i];
        }

        return process(sum, 0, sum.length - 1, lower, upper);
    }

    private static int process(long[] arr, int l, int r, int lower, int upper){
        if(r == l) {
            // 2、直接判断值是否在lower～upper上面
            return arr[l] >= lower && arr[r] <= upper ? 1 : 0;
        }

        int m = l + ((r - l) >> 1);
        return process(arr, l, m, lower, upper) + process(arr, m + 1, r, lower, upper) + merge(arr, l, m, r, lower, upper);
    }

    private static int merge(long[] arr, int l, int m, int r, int lower, int upper) {
        // 3、用两个下标记录在这个范围上的值就在lower～upper上面
        int windowL = l;
        int windowR = l;
        // 已知：arr[i~j]在lower~upper里面则arr[0~j] - arr[0~(i-1)]，  [0~j]的整体累加和是x
        // 求arr[0~i]中的前缀和在lower~upper上面 等同于 有多个前缀和在[(x-upper) ~ (x-lower)]上
        int res = 0;
        for (int i = m + 1; i <= r; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            while (windowL <= m && arr[windowL] < min) {
                windowL++;
            }
            while (windowR <= m && arr[windowR] <= max) {
                windowR++;
            }
            res += windowR - windowL;
        }

        // 4、归并排序逻辑
        long[] help = new long[r - l + 1];
        int left = l;
        int right = m + 1;
        int i = 0;
        while (left <= m && right <= r) {
            help[i++] = arr[left] <= arr[right] ? arr[left++] : arr[right++];
        }

        // 剩余数字直接赋值
        while (left <= m) {
            help[i++] = arr[left++];
        }
        while (right <= r) {
            help[i++] = arr[right++];
        }

        // 将help里面的数字和arr里面的进行替换
        for (int i1 = 0; i1 < help.length; i1++) {
            arr[l + i1] = help[i1];
        }
        return res;
    }


}
