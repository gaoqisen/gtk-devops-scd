package com.gqs.algorithm.class005;

import com.alibaba.fastjson.JSONObject;

public class T003_RadixSort {

    public static void main(String[] args) {
        int[] arr = {118, 0,22, 415, 98,100 ,98,10, 1,0, 5};
        sort2(arr);
        System.out.println(JSONObject.toJSONString(arr));
    }

    // 初始化的数组默认值是0，利用0判断数组里面是否存在数字
    public static void sort(int[] arr) {
        // 获取最大值的位置
        int digit = getMaxDigit(arr);
        // 遍历所有的位数
        for(int j = 1; j <= digit; j++) {
            // 将j位置的数据按照下标放在指定桶中
            int[][] help = new int[10][10];
            for (int i = 0; i < arr.length; i++) {
                int val = getElement(arr[i], j);
                int[] ints = help[val];
                for (int i1 = 0; i1 < ints.length; i1++) {
                    if(ints[i1] == 0) {
                        ints[i1] = arr[i];
                        break;
                    }
                }
            }
            // 将桶中的数字依次取出放回原来的数字
            int index = 0;
            for (int i = 0; i < help.length; i++) {
                int[] ints = help[i];
                if (ints != null && ints.length > 0) {
                    for (int d = 0; d < ints.length; d++) {

                        if(ints[d] == 0) {
                            break;
                        }
                        arr[index] = ints[d];
                        index++;
                    }
                }
            }
        }
    }

    // 利用数组的最后一个数字存储数量, 但是不支持负数，需要支持负数的话。可以将所有数乘以指定数后变为正数，排序完成后在调整为负数
    public static void sort1(int[] arr) {
        // 获取最大值的位置
        int digit = getMaxDigit(arr);

        // 遍历所有的位数
        for(int j = 1; j <= digit; j++) {
            // 将j位置的数据按照下标放在指定桶中
            int[][] help = new int[10][11];
            for (int i = 0; i < arr.length; i++) {
                int val = getElement(arr[i], j);
                int[] ints = help[val];
                int index = ints[10]++;
                ints[index] = arr[i];
            }
            // 将桶中的数字依次取出放回原来的数字
            int index = 0;
            for (int i = 0; i < help.length; i++) {
                int[] ints = help[i];
                if (ints[10] == 0) {
                    continue;
                }
                for (int d = 0; d < ints[10]; d++) {
                    arr[index++] = ints[d];
                }
            }
        }
    }



    // 最优的方式，将二维数组的桶换成了数组。 利用累加和的方式进行基数排序
    public static void sort2(int[] arr) {
       int[] help = new int[arr.length];
        // 获取最大值的位置
        int digit = getMaxDigit(arr);
        for (int i = 1; i <= digit; i++) {
            int[] bucket = new int[10];
            // 将数组i位置的数据出现次数放入桶中
            for (int j = 0; j < arr.length; j++) {
                int index = getElement(arr[j], i);
                bucket[index]++;
            }
            // 将桶变为累加和（累加和的值就是数字出现的次数）
            for (int j = 1; j < bucket.length; j++) {
                bucket[j] = bucket[j] + bucket[j -1];
            }
            // 通过桶中的累加将数组的中的元素按序放入help
            for (int j = arr.length - 1; j >= 0; j--) {
                int index = getElement(arr[j], i);
                // 重点：从右往左获取数组的元素，元素值的位置就是元素出现的次数减一（bucket[index] - 1）
                help[bucket[index] - 1] = arr[j];
                bucket[index]--;
            }
            // 将help数据替换到数组中
            for (int j = 0; j < help.length; j++) {
                arr[j] = help[j];
            }
        }
    }

    // 获取最大值的位数
    private static int getMaxDigit(int[] arr) {
        int max = 0;
        for (int i : arr) {
            max = Math.max(max, i);
        }
        int digit = 0;
        while (max != 0) {
            digit++;
            max /= 10;
        }
        return digit;
    }

    // 获取数组第几位上面的数字
    public static int getElement(int val, int i) {
        String str = String.valueOf(val);
        char[] chars = str.toCharArray();
        if(chars.length < i) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(chars[chars.length - i]));
    }
}
