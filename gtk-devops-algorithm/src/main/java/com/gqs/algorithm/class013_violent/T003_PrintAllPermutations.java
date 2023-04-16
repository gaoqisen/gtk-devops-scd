package com.gqs.algorithm.class013_violent;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 全排列
 */
public class T003_PrintAllPermutations {

    // 全排序：利用剩余字符处理
    public static List<String> permutations(String s) {
        // 判空
        List<String> result = new ArrayList<>();
        if(s == null || s.length() < 1) {
            return result;
        }

        String path = "";
        char[] chars = s.toCharArray();
        List<Character> rest = new ArrayList<>();
        for (char aChar : chars) {
            rest.add(aChar);
        }
        execute(result, rest, path);
        return result;
    }

    public static void execute(List<String> result, List<Character> rest, String path) {
        if(rest.isEmpty()) {
            result.add(path);
            return;
        }

        int index = rest.size();
        for (int i = 0; i < index; i++) {
            Character character = rest.get(i);
            // 清除现场
            rest.remove(character);
            // 递归往后处理
            execute(result, rest, path + character);
            // 恢复现场，将前面删除的字符恢复
            rest.add(i, character);
        }
    }

    // 全排序优化版本：利用下标+元素交换处理
    public static List<String> permutations1(String s) {
        List<String> result = new ArrayList<>();
        if(s == null || s.length() < 1) {
            return result;
        }

        char[] chars = s.toCharArray();
        execute1(chars, result, 0);
        return result;
    }

    public static void execute1(char[] chars, List<String> res, int index){
        if(index == chars.length) {
            res.add(String.valueOf(chars));
            return;
        }
        // 从index开始遍历字符
        for (int i = index; i < chars.length; i++) {
            // index和后面的其他字符交换位置
            swap(chars, index, i);
            // 递归处理后面的字符
            execute1(chars, res, index + 1);
            // 恢复现场后其他元素继续遍历
            swap(chars, index, i);
        }
    }

    // 全排序优化版本（去除重复）：利用下标+元素交换处理
    public static List<String> permutations2(String s) {
        List<String> result = new ArrayList<>();
        if(s == null || s.length() < 1) {
            return result;
        }

        char[] chars = s.toCharArray();
        execute2(chars, result, 0);
        return result;
    }

    public static void execute2(char[] chars, List<String> res, int index){
        if(index == chars.length) {
            res.add(String.valueOf(chars));
            return;
        }
        // 从index开始遍历字符
        boolean[] visited = new boolean[256];
        for (int i = index; i < chars.length; i++) {
            // 剪枝方式判断字符如果已经匹配过则往后匹配
            if(visited[chars[i]]) {
                continue;
            }
            visited[chars[i]] = true;
            // index和后面的其他字符交换位置
            swap(chars, index, i);
            // 递归处理后面的字符
            execute1(chars, res, index + 1);
            // 恢复现场后其他元素继续遍历
            swap(chars, index, i);
        }
    }

    // 交换字符
    public static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    public static void main(String[] args) {
        List<String> abcd = permutations("abc");
        System.out.println(JSONObject.toJSONString(abcd));
        System.out.println("------");
        List<String> abcd1 = permutations1("abc");
        System.out.println(JSONObject.toJSONString(abcd1));



    }

}
