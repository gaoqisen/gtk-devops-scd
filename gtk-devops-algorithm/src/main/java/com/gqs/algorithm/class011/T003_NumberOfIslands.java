package com.gqs.algorithm.class011;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

// https://leetcode.com/problems/number-of-islands/
// 获取岛屿数量
public class T003_NumberOfIslands {

    // ===================利用递归实现=================================

    /**
     * 递归方式寻找岛
     * 遍历二维数组，遇到一个岛屿就进行递归找上下左右的岛屿
     */
    public static int numIslands(char[][] board) {
        int isLands = 0;
        for (int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == '1') {
                    isLands++;
                    infect(board, i, j);
                }
            }
        }
        return isLands;
    }

    // 找到岛屿之后就将岛屿改为0， 并递归寻找该岛上下左右是否是岛屿
    private static void infect(char[][] board, int i, int j) {
        if(i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != '1'){
            return;
        }
        board[i][j] = '0';
        infect(board, i - 1, j);
        infect(board, i + 1, j);
        infect(board, i, j - 1);
        infect(board, i, j + 1);
    }

    // ===================利用并查集实现 hashmap=================================

    public static void main1(String[] args) {
        char[][] list = new char[][]{"11110".toCharArray(),"11010".toCharArray(),"11000".toCharArray(),"00000".toCharArray()};
        System.out.print(numIslands1(list));
    }

    private static class Dot{}

    // 利用空对象判断，null表示水，对象表示陆地
    public static int numIslands1(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        Dot[][] dots = new Dot[row][col];
        // 将char二维数组以dot二维素组形式组装，路径就是dot对象，水为null。并将dot放到list里面
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(board[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }

        // 先遍历列，判断是陆地则进行合并操作
        UnionHashMap<Dot> unionHashMap = new UnionHashMap<>(dotList);
        for (int i = 1; i < col; i++) {
            if(board[0][i-1] == '1' && board[0][i] == '1') {
                unionHashMap.union(dots[0][i-1], dots[0][i]);
            }
        }

        // 遍历行，判断是陆地则进行合并操作
        for (int i = 1; i < row; i++) {
            if(board[i - 1][0] == '1' && board[i][0] == '1') {
                unionHashMap.union(dots[i - 1][0], dots[i][0]);
            }
        }

        // 遍历中间的其他水和陆地，是陆地则进行合并操作
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if(board[i][j] == '1') {
                    // 判断上边是陆地则合并
                    if(board[i][j-1] == '1') {
                        unionHashMap.union(dots[i][j], dots[i][j-1]);
                    }
                    // 判断左边是陆地则合并
                    if(board[i-1][j] == '1') {
                        unionHashMap.union(dots[i][j], dots[i-1][j]);
                    }
                }
            }
        }
        // 合并后的集合就是陆地的数量
        return unionHashMap.getSize();
    }

    private static class UnionHashMap<V>{

        private HashMap<V, Node<V>> nodes;
        private HashMap<Node<V>, Node<V>> parents;
        private HashMap<Node<V>, Integer> sizes;

        public UnionHashMap(List<V> list) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizes = new HashMap<>();
            for (V v : list) {
                Node node = new Node(v);
                nodes.put(v, node);
                parents.put(node, node);
                sizes.put(node, 1);
            }
        }

        public int getSize() {
            return sizes.size();
        }

        // 合并两个元素
        public void union(V a, V b) {
            Node<V> aNode = findAncestor(nodes.get(a));
            Node<V> bNode = findAncestor(nodes.get(b));
            if(aNode != bNode) {
                int aLength = sizes.get(aNode);
                int bLength = sizes.get(bNode);
                Node maxNode = aLength > bLength ? aNode : bNode;
                Node minNode = maxNode == aNode ? bNode : aNode;
                parents.put(minNode, maxNode);
                sizes.put(maxNode, aLength + bLength);
                sizes.remove(minNode);
            }
        }

        // 获取祖先节点
        private Node<V> findAncestor(Node<V> node){
            Stack<Node<V>> stack = new Stack<>();
            while (node != parents.get(node)) {
                stack.push(node);
                node = parents.get(node);
            }

            // 路径压缩，将所有的
            while (!stack.isEmpty()) {
                Node<V> pop = stack.pop();
                parents.put(pop, node);
            }
            return node;
        }
    }
    private static class Node<T>{
        private T val;

        public Node(T val) {
            this.val = val;
        }
    }

    // ===================利用并查集实现（数组）=================================

    public static void main(String[] args) {
        char[][] list = new char[][]{"11110".toCharArray(),"11010".toCharArray(),"11000".toCharArray(),"00000".toCharArray()};
        System.out.print(numIslands2(list));
    }

    public static int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        UnionArray unionArray = new UnionArray(board);
        // 遍历列board[0][i]
        for (int i = 1; i < col; i++) {
            if(board[0][i-1] == '1' && board[0][i] == '1') {
                unionArray.union(0, i, 0, i-1);
            }
        }

        // 遍历行board[i][0]
        for (int i = 1; i < row; i++) {
            if(board[i -1][0] == '1' && board[i][0] == '1') {
                unionArray.union(i-1, 0, i, 0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if(board[i][j] == '1') {
                    if(board[i][j-1] == '1') {
                        unionArray.union(i, j, i, j-1);
                    }
                    if(board[i-1][j] == '1') {
                        unionArray.union(i, j, i-1, j);
                    }
                }
            }
        }
        return unionArray.getSize();
    }


        // 数组的方式实现并查集
    private static class UnionArray{

        // 节点的父节点
        private int[] parent;
        // 节点的数量
        private int[] sizes;
        // 辅助数组用于代替栈作路径压缩
        private int[] help;
        // 列数
        private int col;
        // 岛数量
        private int size;

        public UnionArray(char[][] board) {
            this.col = board[0].length;
            int row = board.length;
            int length = col * row;
            parent = new int[length];
            sizes = new int[length];
            this.size = 0;
            help = new int[length];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if(board[i][j] == '1') {
                        int index = index(i, j);
                        this.parent[index] = index;
                        this.sizes[index] = 1;
                        this.size++;
                    }
                }
            }
        }

        public void union(int ra, int ca, int rb, int cb) {
            int aIndex = index(ra, ca);
            int bIndex = index(rb, cb);
            // 获取祖先的下标
            int aAncestor = findAncestor(aIndex);
            int bAncestor = findAncestor(bIndex);
            if(aAncestor != bAncestor) {
                // 将短的集合指向长集合，并合并数量
                if(this.sizes[aAncestor] > this.sizes[bAncestor]) {
                    this.parent[bAncestor] = aAncestor;
                    this.sizes[aAncestor] += sizes[bAncestor];
                } else {
                    this.parent[aAncestor] = bAncestor;
                    this.sizes[bAncestor] += sizes[aAncestor];
                }
                this.size--;
            }
        }

        private int findAncestor(int val) {
            int i = 0;
            while (val != parent[val]) {
                help[i++] = val;
                val = parent[val];
            }

            // 路径压缩，将help里面的下标都指向祖先
            for (i--; i >= 0; i--) {
                parent[help[i]] = val;
            }
            return val;
        }

        // 获取数组的下标，行数从0开始*列总数+当前行位置
        private int index(int r, int c) {
            return r * col + c;
        }

        public int getSize() {
            return this.size;
        }
    }
}
