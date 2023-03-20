package com.gqs.algorithm.class008_tree;

public class T006_PaperFolding {

    public static void paperFolding(int n) {
        process(1, n, true);
    }

    /**
     * true: down
     * false：up
     * i: 当前层数
     * n：所有层数
     * 中序遍历整棵树
     */
    public static void process(int i, int n, boolean down) {
        if(i > n) {
            return;
        }
        process(i + 1, n, true);
        System.out.println(down ? "down" : "up");
        process(i + 1, n, false);
    }

}
