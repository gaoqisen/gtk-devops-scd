package com.gqs.algorithm.class004;

import java.util.PriorityQueue;

public class T001_Heap {

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(5);
        maxHeap.push(2);
        maxHeap.push(1);
        maxHeap.push(6);
        maxHeap.push(0);
        maxHeap.push(10);

        System.out.println(maxHeap.pop());
        System.out.println(maxHeap.pop());
        System.out.println(maxHeap.pop());
        maxHeap.push(8);

        System.out.println("小根堆-------------(default)");
        PriorityQueue<Integer> maxHeapQueue = new PriorityQueue<>();
        maxHeapQueue.add(7);
        maxHeapQueue.add(9);
        maxHeapQueue.add(1);
        maxHeapQueue.add(10);

        for (int i = 0; i < 4; i++) {
            System.out.println(maxHeapQueue.poll());
        }

        System.out.println("大根堆-------------");

        PriorityQueue<Integer> minHeapQueue = new PriorityQueue<>((i, j) -> {
            return j - i;
        });
        minHeapQueue.add(7);
        minHeapQueue.add(9);
        minHeapQueue.add(1);
        minHeapQueue.add(10);
        for (int i = 0; i < 4; i++) {
            System.out.println(minHeapQueue.poll());
        }


    }

    public static class MaxHeap{

        public int[] heap;

        public int heapSize;

        public int limit;

        public MaxHeap(int limit) {
            this.heap = new int[limit];
            this.limit = limit;
            this.heapSize = 0;
        }

        public void push(int val) {
            if(heapSize == limit) {
                System.out.println("堆满了");
                return;
            }
            this.heap[this.heapSize] = val;
            insertHeap(this.heap, this.heapSize++);
        }

        public int pop() {
            int root = heap[0];
            swap(heap, 0, --heapSize);
            sink(this.heap, 0);
            return root;
        }


        private void sink(int[] heap, int index) {
            int left = index * 2 + 1;
            while (left < this.heapSize) {
                // 获取index，index的左孩子，index的右孩子中最大的一个下标
                int max = left + 1 < this.heapSize && heap[left + 1] > heap[left] ? left + 1 : left;
                max = heap[index] > heap[max] ? index : max;

                // 当前值就是最大的则跳出循环
                if(max == index) {
                    break;
                }

                swap(heap, index, max);
                index = max;
                left = index * 2 + 1;
            }
        }

        // 将最后一个节点的位置按照大根堆的方式合适的位置，上移
        private void insertHeap(int[] heap, int index){
            // (index-1)/2 就是 index节点的父节点
            while (heap[index] > heap[(index-1)/2]) {
                swap(heap, index, (index-1)/2);
                index = (index-1)/2;
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
