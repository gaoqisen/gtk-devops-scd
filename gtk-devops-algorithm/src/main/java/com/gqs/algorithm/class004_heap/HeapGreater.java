package com.gqs.algorithm.class004_heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 加强堆
 */
public class HeapGreater<T> {

    public static void main(String[] args) {
        HeapGreater<Integer> maxHeap = new HeapGreater<>((a, b) -> (int) b - (int)a);
        maxHeap.push(2);
        maxHeap.push(1);
        maxHeap.push(6);
        maxHeap.push(5);
        maxHeap.push(0);
        maxHeap.push(10);

        maxHeap.remove(5);
        System.out.println(maxHeap.pop());
        System.out.println(maxHeap.pop());
        System.out.println(maxHeap.pop());
        maxHeap.push(8);
        System.out.println(maxHeap.pop());
        System.out.println(maxHeap.pop());
        System.out.println(maxHeap.pop());
    }


    // 堆
    private ArrayList<T> heap;

    // 利用hashMap存储下标
    private HashMap<T, Integer> hashMap;

    private int headSize;

    private Comparator<T> comparator;

    public HeapGreater(Comparator<T> comparator) {
        this.comparator = comparator;
        this.heap = new ArrayList<>();
        this.headSize = 0;
        this.hashMap = new HashMap<>();
    }

    public void push(T obj) {
        heap.add(obj);
        hashMap.put(obj, headSize);
        ascend(headSize++);
    }

    public T pop() {
        T t = heap.get(0);
        swap(heap, 0, --headSize);
        sink(0);
        hashMap.remove(t);
        heap.remove(headSize);
        return t;
    }

    public boolean contains(T val) {
        return hashMap.containsKey(val);
    }

    public int size() {
        return headSize;
    }

    public void remove(T obj) {
        // 将最后一个值拿出来
        T val = heap.get(headSize - 1);
        int index = hashMap.get(obj);
        heap.remove(--headSize);
        hashMap.remove(obj);
        if(comparator.compare(val, obj) != 0) {
            heap.set(index, val);
            hashMap.put(val, index);
            adjust(val);
        }
    }

    // 调整位置，向上浮后往下沉
    public void adjust(T obj) {
        ascend(hashMap.get(obj));
        sink(hashMap.get(obj));
    }

    // 下沉
    private void sink(int index) {
        int left = index * 2 + 1;
        while (left < headSize) {
            int max = left + 1 < headSize && comparator.compare(heap.get(left), heap.get(left + 1)) > 0 ? left + 1 : left;
            max = comparator.compare(heap.get(max), heap.get(index)) < 0 ? max : index;
            if(index == max) {
                break;
            }
            swap(heap, index, max);
            index = max;
            left = (max * 2) + 1;
        }
    }

    // 上浮
    private void ascend(int index) {
        while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(heap, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void swap(ArrayList<T> arr, int i, int j) {
        if(i == j) {
            return;
        }
        T tmp = arr.get(i);
        T jTmp = arr.get(j);
        arr.set(i, jTmp);
        arr.set(j, tmp);
        hashMap.put(tmp, j);
        hashMap.put(jTmp, i);
    }
}
