package com.gqs.algorithm.class004_heap;

import com.alibaba.fastjson.JSONObject;

/**
 * 堆排序
 */
public class T002_HeapSort {

    public static void main(String[] args) {
        int[] arr = {2,6,9,3,1,0,10,45,5};
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.sortMax(arr);
        System.out.println(JSONObject.toJSONString(arr));

        int[] arr1 = {2,6,9,3,1,0,10,45,5};
        maxHeap.sortSink(arr1);
        System.out.println(JSONObject.toJSONString(arr1));


    }

    public static class MaxHeap{

        private int[] heap;

        private int heapSize;

        // 方式一：通过上浮的方式组装大根堆O(N*logN)
        public void sortMax(int[] arr) {
            for (int i = 0; i < arr.length; i++) {
                ascendHeap(arr, i);
            }

            int heapSize = arr.length;
            swap(arr, 0, --heapSize);
            while (heapSize > 0) {
                sink(arr, 0, heapSize);
                swap(arr, 0, --heapSize);
            }
        }

        // 方式二：通过下沉的方式组装大根堆O(N)
        public void sortSink(int[] arr) {
            for(int i = arr.length - 1; i >= 0; i--) {
                sink(arr, i, arr.length);
            }

            int heapSize = arr.length;
            swap(arr, 0, --heapSize);
            while (heapSize > 0) {
                sink(arr, 0, heapSize);
                swap(arr, 0, --heapSize);
            }
        }

        // 上浮
        private void ascendHeap(int[] heap, int index) {
            while (heap[index] > heap[(index-1)/2]){
                swap(heap, index, (index-1)/2);
                index = (index-1)/2;
            }
        }

        // 下沉
        private void sink(int[] heap, int index, int heapSize) {
            // 将第一个下沉
            int left = index * 2 + 1;
            while (left < heapSize) {
                // 获取index、left、right中最大的下标
                int largest = left + 1 < heapSize && heap[left + 1] > heap[left] ? left + 1 : left;
                largest = heap[index] > heap[largest] ? index : largest;
                if(index == largest) {
                    break;
                }
                swap(heap, index, largest);
                left = (largest * 2) + 1;
                index = largest;
            }
        }
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
