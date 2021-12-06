package com.gaoqisen.collection;

import com.alibaba.fastjson.JSONObject;
import com.gaoqisen.collection.other.EnumList;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetTest {

    public static void main(String[] args) {
        // 直接用的TreeMap
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("1");
        treeSet.add("2");
        System.out.println("treeSet: " + JSONObject.toJSONString(treeSet));

        // 直接用的hashMap
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("1");
        hashSet.add("1");
        System.out.println("hashSet: " + JSONObject.toJSONString(hashSet));

        // 直接用的LinkedHashMap
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("1");
        linkedHashSet.add("2");
        System.out.println("linkedHashSet: " + JSONObject.toJSONString(linkedHashSet));

        // 用的CopyOnWriteArrayList
        CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
        copyOnWriteArraySet.add("1");
        copyOnWriteArraySet.add("2");
        System.out.println("copyOnWriteArraySet: " + JSONObject.toJSONString(copyOnWriteArraySet));

        // 用的ConcurrentSkipListMap
        ConcurrentSkipListSet<String> concurrentSkipListSet = new ConcurrentSkipListSet<>();
        concurrentSkipListSet.add("1");
        concurrentSkipListSet.add("2");
        System.out.println("concurrentSkipListSet: " + JSONObject.toJSONString(concurrentSkipListSet));

        // 枚举
        EnumSet<EnumList> enumSet = EnumSet.allOf(EnumList.class);
        System.out.println("enumSet: " + JSONObject.toJSONString(enumSet));
    }

}
