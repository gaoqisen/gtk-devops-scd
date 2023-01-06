package com.gqs.algorithm.class003;

import com.alibaba.fastjson.JSONObject;

import java.util.Stack;

// 分区快排
public class T004_PartitionAndQuickSortByQueueV3 {

    public static void main(String[] args) {
        int[] arr = {2,6,9,3,1,0,10,45,5};
        sort(arr);
        System.out.println(JSONObject.toJSONString(arr));
    }

    /**
     * 非递归版本的快排
     */
    private static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r){
        if(l >= r) {
            return;
        }
        swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
        int[] m = partition(arr, l, r);
        // 下面的代码替代了递归
        Stack<StackTmp> stack = new Stack<>();
        stack.push(new StackTmp(l, m[0] -1));
        stack.push(new StackTmp(m[0] + 1, r));

        while (!stack.isEmpty()) {
            StackTmp pop = stack.pop();
            if(pop.l < pop.r) {
                swap(arr, pop.l + (int) (Math.random() * (pop.r - pop.l + 1)), pop.r);
                int[] range = partition(arr, pop.l, pop.r);
                stack.push(new StackTmp(pop.l, range[0] - 1));
                stack.push(new StackTmp(range[1] + 1, pop.r));
            }
        }
    }

    public static int[] partition(int[] arr, int l, int r) {
        if(l == r) {
            return new int[]{l, r};
        }
        if(l > r) {
            return new int[]{-1, -1};
        }

        int leftArea = l -1,rightArea = r,index = l;
        while (index < rightArea) {
            if(arr[index] == arr[r]) {
                index++;
                continue;
            }
            if(arr[index] < arr[r]) {
                swap(arr, index++, ++leftArea);
                continue;
            }
            swap(arr, index, --rightArea);
        }
        swap(arr, rightArea, r);
        return new int[]{leftArea + 1, rightArea};
    }
    private static void swap(int[] arr, int i, int j) {
        if(i == j) {
            return;
        }
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

class StackTmp{
    public int l;
    public int r;
    public StackTmp(int l, int r) {
        this.l = l;
        this.r = r;
    }
}
