package com.gqs.algorithm.other;

// 希尔排序(插入排序的升级版本)
public class ShellSort {

    public static void main(String[] args) {
        int[] a = {9,6,5,2,3,4,80,7,1,10};

        // knuth序列
        int h = 1;
        while( h <= a.length / 3) {
            h = h*3 +1;
        }

        for (int gap = h; gap > 0; gap = (gap -1)/3) {
            for (int i = gap; i < a.length; i++) {
                for (int j = i; j > gap -1 ; j-=gap) {
                    if(a[j] < a[j-gap]) {
                        swap(a, j, j-gap);
                    }
                }
            }
        }

        print(a);

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
