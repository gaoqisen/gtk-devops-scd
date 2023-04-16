package com.gqs.algorithm.class013_violent;

import java.util.*;

public class T002_PrintAllSubSquences {


    public static void main(String[] args) {
        String test = "abc";
        List<String> ans1 = subs(test);

        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=================");

    }


    // 会出现重复的打印
    public static List<String> subs(String s) {
        char[] str = s.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        // 从0开始
        execute(str, 0, ans, path);
        return ans;
    }

    public static void execute(char[] str, int index, Collection<String> ans, String path) {
        // index结束
        if(index == str.length) {
            ans.add(path);
            return;
        }
        // 下一个元素不放入ans
        execute(str, index + 1, ans, path);

        // 下一个元素放入ans
        execute(str, index + 1, ans, path + String.valueOf(str[index]));
    }

    // 不重复的大于
    public static List<String> subsNoRepeat(String s) {
        char[] chars = s.toCharArray();
        HashSet<String> ans = new HashSet<>();
        String path = "";
        execute(chars, 0, ans, path);
        return new ArrayList<>(ans);
    }


}
