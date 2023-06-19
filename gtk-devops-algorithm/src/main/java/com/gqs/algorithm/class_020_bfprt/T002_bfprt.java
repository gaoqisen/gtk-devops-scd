package com.gqs.algorithm.class_020_bfprt;

public class T002_bfprt {

    // 获取数组第k小的数
    public static int minKth(int[] arr, int k) {
        // 下标从0开始则第k小的是k-1
        return bfprt(arr, 0, arr.length - 1, k-1);
    }

    /**
     * bfprt算法
     *
     * @param arr 数组
     * @param l 左边值下标
     * @param r 右边值下标
     * @param index 需要找的第几小的数
     * @return
     */
    private static int bfprt(int[] arr, int l, int r, int index) {
        if(l == r) {
            return arr[l];
        }

        // 获取中位数的中位数
        int pivot = medianOfMedians(arr, l, r);
        // 进行排序分区（左中右）
        int[] range = T001_FindMinKth.partition(arr, l, r, pivot);
        // 中位数刚好在中间
        if(index >= range[0] && index <= range[1]) {
            return arr[index];
        }
        // 中位数在左边
        else if(index < range[0]) {
            return bfprt(arr, 0, range[0] - 1, index);
        }
        // 中位数在右边
        else {
            return bfprt(arr, range[1] + 1, r, index);
        }
    }

    /**
     * 获取数组中位数的中位数
     * 1、每5个一组
     * 2、小组内部排序
     * 3、获取每个小组的中位数组成mArr
     * 4、获取mArr的中位数
     *
     * @param arr 数组
     * @param l 左边界值下标
     * @param r 右边界值下标
     * @return
     */
    private static int medianOfMedians(int[] arr, int l, int r) {
        int size = r - l + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        // 中位数的数组
        int[] mArr = new int[size / 5 + offset];
        // 每5个为一组，进行遍历获取中位数
        for (int team = 0; team < mArr.length; team++) {
            // 当前组的开始位置
            int first = l + team * 5;
            // 获取中位数
            mArr[team] = getMedian(arr, first, Math.min(r, first + 4));
        }

        // 获取中位数的中间值
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    // 获取中位数
    private static int getMedian(int[] arr, int l, int r) {
        // 先快速排序
        for (int i = l + 1; i <= r; i++) {
            for (int j = i - 1; j >= l && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
        // 获取中位数
        return arr[(l + r) / 2];
    }
    public static void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }


    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = T001_FindMinKth.generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = T001_FindMinKth.minKth(arr, k);
            int ans2 = minKth(arr, k);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
