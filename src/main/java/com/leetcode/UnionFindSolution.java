package com.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 并查集
 */
public class UnionFindSolution {

    public static void main(String[] args) {

        UnionFindSolution solution = new UnionFindSolution();

        int[][] isConnected = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        System.out.println(solution.findCircleNum(isConnected));

    }

    /**
     * 547. 省份数量
     *
     * @param isConnected
     * @return
     */
    public int findCircleNum(int[][] isConnected) {
        UnionFind unionFind = new UnionFind();
        int length = isConnected.length;
        for (int i = 0; i < length; i++) {
            unionFind.add(i);
            // 如果发现两个节点是连通的，那么就要把他们合并，也就是他们的祖先是相同的。这里究竟把谁当做父节点一般是没有区别的。
            // 另外1-2相连，2-1也是相连的，只需遍历一半的数组就行了
            for (int j = 0; j < i; j++) {
                // 如果两者有关联，则进行合并
                if (isConnected[i][j] == 1) {
                    unionFind.merge(i, j);
                }
            }
        }
        return unionFind.getNumOfSets();
    }


    static class UnionFind {
        // 记录父节点 根节点的父节点应该为空
        private Map<Integer, Integer> father;
        // 记录集合的数量
        private int numOfSets = 0;

        public UnionFind() {
            father = new HashMap<>();
            numOfSets = 0;
        }

        /**
         * 添加新节点
         *
         * @param x
         */
        public void add(int x) {
            // 如果父节点中没有，说明是一个新的分支，父节点应该为空
            if (!father.containsKey(x)) {
                father.put(x, null);
                numOfSets++;
            }
        }

        /**
         * 合并两个节点
         *
         * @param x
         * @param y
         */
        public void merge(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                father.put(rootX, rootY);
                //合并后  连通分量的数目-1
                numOfSets--;
            }
        }

        /**
         * 查找根节点 路径压缩
         *
         * @param x
         * @return
         */
        public int find(int x) {
            int root = x;

            while (father.get(root) != null) {
                root = father.get(root);
            }

            while (x != root) {
                int original_father = father.get(x);
                father.put(x, root);
                x = original_father;
            }

            return root;
        }

        /**
         * 判断两节点是否相连
         *
         * @param x
         * @param y
         * @return
         */
        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }

        /**
         * 返回连通分量的数目
         *
         * @return
         */
        public int getNumOfSets() {
            return numOfSets;
        }
    }
}
