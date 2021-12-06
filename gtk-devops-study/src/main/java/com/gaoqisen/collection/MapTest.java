package com.gaoqisen.collection;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class MapTest {

    public static void main(String[] args) throws InterruptedException {
        // 线程安全
        hashTable();

        // 未排序
        hashMap();

        // 按照put的顺序存储数据
        linkedHashMap();

        // 按照大小排序
        treeMap();

        // 当key没有被引用时，map会自动丢弃此值
        weakHashMap();

        // 相同key的hashMap
        identityHashMap();

        // cas方式的线程同步hashMap
        concurrentHashMap();

        // 跳表方式的并发map
        concurrentSkipListMap();

        Thread.sleep(2000);
    }

    /**
     * 利用跳表实现
     */
    private static void concurrentSkipListMap() {
        Map<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        concurrentSkipListMap.put("1", "1");
        concurrentSkipListMap.put("3", "2");
        concurrentSkipListMap.put("5", "4");
        concurrentSkipListMap.put("2", "7");
        System.out.println("concurrentSkipListMap: " + JSONObject.toJSONString(concurrentSkipListMap));
    }

    /**
     * 利用cas实现
     */
    private static void concurrentHashMap() {
        Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("1", "1");
        concurrentHashMap.put("3", "2");
        concurrentHashMap.put("5", "4");
        concurrentHashMap.put("2", "7");
        System.out.println("concurrentHashMap: " + JSONObject.toJSONString(concurrentHashMap));
    }

    private static void identityHashMap() {
        /**
         * 和hashMap的判断key是否一样的逻辑不同
         */
        Map<String, String> identityHashMap = new IdentityHashMap<>();
        identityHashMap.put(new String("1"), "1");
        identityHashMap.put(new String("1"), "2");
        identityHashMap.put("1", "3");
        identityHashMap.put("1", "4");
        System.out.println("identityHashMap: " + JSONObject.toJSONString(identityHashMap));
    }

    private static void weakHashMap() {
        String a = new String("a");
        String b = new String("b");

        /**
         * 利用的弱引用实现
         */
        Map<String, String> weakHashMap = new WeakHashMap<>();
        weakHashMap.put(a, "1");
        weakHashMap.put(b, "2");
        weakHashMap.put("5", "4");
        weakHashMap.put("2", "7");
        System.out.println("weakHashMap: " + JSONObject.toJSONString(weakHashMap));
        a = null;
        b = null;
        System.gc();
        System.out.println("weakHashMap: " + JSONObject.toJSONString(weakHashMap));
    }

    /**
     * 底层是红黑树
     */
    private static void treeMap() {
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("1", "1");
        treeMap.put("3", "2");
        treeMap.put("5", "4");
        treeMap.put("2", "7");
        System.out.println("treeMap: " + JSONObject.toJSONString(treeMap));
    }

    /**
     * 按照插入数据进行排序
     */
    private static void linkedHashMap() {

        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("1", "1");
        linkedHashMap.put("3", "2");
        linkedHashMap.put("5", "4");
        linkedHashMap.put("2", "7");

        System.out.println("linkedHashMap: " + JSONObject.toJSONString(linkedHashMap));
    }

    /**
     * 原理：在方法上都加了synchronized锁，所以是线程同步的
     */
    private static void hashTable() {

        Hashtable<String, String> hashTable = new Hashtable<>();
        hashTable.put("test", "1");
        hashTable.put("1", "1");
        hashTable.put("3", "2");
        hashTable.put("5", "4");
        hashTable.put("2", "7");
        System.out.println("hashTable: " + JSONObject.toJSONString(hashTable));
    }

    /**
     * 原理：
     * 1. 通过key的hashCode经过扰动函数处理后得到hash值（为了减少碰撞）
     * 2. 判断链表长度是否大于8，如果大于8则扩展为红黑树
     */
    private static void hashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "1");
        hashMap.put(new String("1"), "1");
        hashMap.put("3", "2");
        hashMap.put("5", "4");
        hashMap.put("2", "7");
        System.out.println("hashMap: " + JSONObject.toJSONString(hashMap));
    }

}
