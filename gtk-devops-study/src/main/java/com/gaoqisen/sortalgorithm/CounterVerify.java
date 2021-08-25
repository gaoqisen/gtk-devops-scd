package com.gaoqisen.sortalgorithm;

import java.util.Arrays;

public class CounterVerify {

    public static void main(String[] args) {
        boolean came = false;
        for (int k = 0; k < 1000; k++) {
            int[] arr = new int[100];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (int)(Math.random()*1000);
            }
            int[] arrs = new int[arr.length];
            System.arraycopy(arr, 0, arrs, 0, arr.length);
            Arrays.sort(arrs);
            QuickSort.sort(arr);
            for (int i = 0; i < arr.length; i++) {
                if(arr[i] != arrs[i]) {
                    came = true;
                }
            }
        }
        if(came) {
            System.out.println("有问题");
        } else {
            System.out.println("没问题");
        }
    }

}
