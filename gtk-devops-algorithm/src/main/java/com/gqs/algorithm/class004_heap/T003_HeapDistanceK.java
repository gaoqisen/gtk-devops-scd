package com.gqs.algorithm.class004_heap;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.PriorityQueue;

public class T003_HeapDistanceK {

    public static void main(String[] args) {
        int[] arr = randomArrayNoMoveMoreK(8,10,
                3);
        sort(arr, 3);
        System.out.println(JSONObject.toJSONString(arr));
    }

    public static void sort(int[] arr, int k) {
        if(arr == null || k == 0) {
            return;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
        int index = 0;
        // 将k-1里面的值放在队列里面
        for (index = 0; index < Math.min(arr.length - 1, k - 1); index++) {
            queue.add(arr[index]);
        }

        // 将index后面的值也放到队列里面，同时将小根堆里面的数据依次替换到数组里面
        for (int i = 0; i < arr.length; i++, index++) {
            if(index < arr.length) {
                queue.add(arr[index]);
            }
            arr[i] = queue.poll();
        }
    }


    public static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int K) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        // 先排个序
        Arrays.sort(arr);
        // 然后开始随意交换，但是保证每个数距离不超过K
        // swap[i] == true, 表示i位置已经参与过交换
        // swap[i] == false, 表示i位置没有参与过交换
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min(i + (int) (Math.random() * (K + 1)), arr.length - 1);
            if (!isSwap[i] && !isSwap[j]) {
                isSwap[i] = true;
                isSwap[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return arr;
    }

}
