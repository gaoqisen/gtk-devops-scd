package com.gqs.algorithm.class_020_bfprt;

import java.util.Arrays;

public class T003_MaxTopk {


    // 先排序，之后获取前第k的数组
    public static int[] maxTopk1(int[] arr, int k) {
        if(arr == null || arr.length < 1 || k < 1){
            return new int[0];
        }
        int n = arr.length;
        k = Math.min(k, n);
        Arrays.sort(arr);

        int[] result = new int[k];
        for (int i = 0, j = n - 1; i < k; i++, j--) {
            result[i] = arr[j];
        }
        return result;
    }

    // 大根堆方式
    public static int[] maxTopk2(int[] arr, int k) {
        if (arr == null || arr.length < 1 || k < 1) {
            return new int[0];
        }
        int n = arr.length;
        k = Math.min(k, n);

        // 建堆，从下往上
        for (int i = n - 1; i >= 0; i--) {
            heapify(arr, i, n);
        }

        // 弹出大根堆并将值放到最后面
        int heapSize = n;
        swap(arr, 0, --heapSize);
        int count = 1;
        while (heapSize > 0 && count < k) {
            // 弹出值后重新调整堆
            heapify(arr, 0, heapSize);
            // 弹出值并放到最后
            swap(arr, 0, --heapSize);
            count++;
        }

        // 将arr从后往前取k个数
        int[] result = new int[k];
        for (int i = n - 1, j = 0; j < k; i--, j++) {
            result[j] = arr[i];
        }
        return result;
    }

    private static void heapify(int[] arr, int index, int heapSize) {
        // 左孩子的下标
        int left = index * 2 + 1;

        while (left < heapSize) {
            // 右孩子存在与左孩子比较，去最大值的下标
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            // 和当前值比，那个大取那个值的下标
            largest = arr[largest] > arr[index] ? largest : index;
            // 没办法往上走则结束流程
            if(largest == index) {
                break;
            }
            // 大的值往前调整
            swap(arr, largest, index);
            // 下标往上移动
            index = largest;
            // 重新计算左孩子下标
            left = index * 2 + 1;
        }

    }

    // 通过下标交换值
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 通过荷兰国旗方式，找到top最大的值
    public static int[] maxTopk3(int[] arr, int k) {
        if (arr == null || arr.length < 1 || k < 1) {
            return new int[0];
        }
        int n = arr.length;
        k = Math.min(k, n);

        // 找到第n-k小的数
        int num = minKth(arr, n - k);
        int index = 0;
        int[] result = new int[k];
        // 将所有大于num的值放到result里面
        for (int i = 0; i < n; i++) {
            if(arr[i] > num) {
                result[index++] = arr[i];
            }
        }

        // result剩余没有填的则等于当前最小值
        for (; index < k; index++) {
            result[index] = num;
        }
        Arrays.sort(result);

        // 逆序
        for (int i = 0, j = k - 1; i < j; i++, j--) {
            swap(result, i, j);
        }

        return result;
    }

    // 找到第i小的数
    private static int minKth(int[] arr, int i) {
        int l = 0, r = arr.length - 1, pivot = 0;
        int[] range = null;

        // 左边的值没有抵达到右边时
        while (l < r) {
            // 随机获取中位数
            pivot = arr[l + (int)(Math.random() * (r - l + 1))];
            range = partition(arr, l, r, pivot);
            // 第i小的数在左边则右下标移动到中间值下标的左边
            if(i < range[0]) {
                r = range[0] - 1;
            }
            // 第i小的数在右边则左下标移动到中间值下标的右边
            else if(i > range[1]) {
                l = range[1] + 1;
            }
            // 刚好就是中间值
            else {
                return pivot;
            }
        }
        // 返回最小的数
        return arr[l];
    }

    // 分区获取中间值的左右边界
    private static int[] partition(int[] arr, int l, int r, int pivot) {
        int less = l - 1, more = r + 1, cur = l;
        while (cur < more) {
            // 当前值小于中间值，小于区域往后移，当前下标往后移
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            }
            // 当前值大于中间值，当前值和右边的值替换后移动下标
            else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            }
            // 等于则当前下标往后移
            else {
                cur++;
            }
        }
        // 返回左边和右边的区域下标
        return new int[] { less + 1, more - 1 };
    }


    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 生成随机数组测试
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean pass = true;
        System.out.println("测试开始，没有打印出错信息说明测试通过");
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = generateRandomArray(maxSize, maxValue);

            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);

            int[] ans1 = maxTopk1(arr1, k);
            int[] ans2 = maxTopk2(arr2, k);
            int[] ans3 = maxTopk3(arr3, k);
            if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
                pass = false;
                System.out.println("出错了！");
                printArray(ans1);
                printArray(ans2);
                printArray(ans3);
                break;
            }
        }
        System.out.println("测试结束了，测试了" + testTime + "组，是否所有测试用例都通过？" + (pass ? "是" : "否"));
    }

}
