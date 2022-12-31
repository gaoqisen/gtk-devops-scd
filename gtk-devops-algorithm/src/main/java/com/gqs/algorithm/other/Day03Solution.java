package com.gqs.algorithm.other;

public class Day03Solution {

    /**
     * 给你一个数组 nums和一个值 val，你需要 原地 移除所有数值等于val的元素，并返回移除后数组的新长度。
     *
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     *
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     * @param args
     */
    public static void main(String[] args) {
      int[] arr = {1,3,5,66,4,3,6};
      int let = removeElement(arr, 3);

        for (int i = 0; i < let; i++) {
            System.out.println(arr[i]);
        }
    }

    public static int removeElement(int[] nums, int val) {
        int num = 0;
        for(int i = 0; i<nums.length; i++) {
            if(nums[i] != val) {
                nums[num] = nums[i];
                num++;
            }
        }
        return num;
    }

}
