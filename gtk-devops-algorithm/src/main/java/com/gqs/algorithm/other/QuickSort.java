package com.gqs.algorithm.other;

// 快速排序: 采用不断的比较和移动来实现排序
// 对于给定的一组记录，选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，
// 将待排序列分成两部分,一部分比基准元素小,一部分大于等于基准元素,此时基准元素在其排好序
// 后的正确位置,然后再用同样的方法递归地排序划分的两部分，直到序列中的所有记录均有序为止。
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = { 6, 5, 9, 8, 3, 2, 1, 7, 10};
        sort(arr);
        print(arr);
    }

    // 排序
    static void sort(int[] arr) {
        partition(arr, 0, arr.length-1);
    }

    // 分区
    static void partition(int[] arr, int left, int right){
        if(left >= right) {
            return;
        }
        // 指针指向左边的第一个值
        int i = left;
        // 已右边的数为基数(轴)
        int temp = arr[right];
        // 右边的指针就是right-1
        int j = right;

        // 当左边的值小于右边值的时候
        while (i < j) {
            // 从左往右找大于或者基数的数字，并移动到arr.length-1的位置
            while (i<j && arr[i] <= temp) {
                i++;
            }
            if(i < j) {
                arr[j--] = arr[i];
            }
            // 从有右往左找小于或者等于基数的数字，并移动数字到i++的位置
            while (i<j && arr[j] > temp){
                j--;
            }
            if(i < j) {
                arr[i++] = arr[j];
            }
        }
        // 将基数放在i的位置
        arr[i] = temp;
        // 递归循环轴左边的值
        partition(arr, left, i-1);
        // 递归循环轴右边的值
        partition(arr, i+1, right);
    }



    static void swap(int[] a, int start, int end) {
        int temp = a[start];
        a[start] = a[end];
        a[end] = temp;
    }
    static void print(int[] a) {
        // 输出
        for (int i = 0; i < a.length; i++) {
            if(i == (a.length - 1)){
                System.out.print(a[i]);
                break;
            }
            System.out.print(a[i]+ ", ");
        }
        System.out.println("");
    }
}
