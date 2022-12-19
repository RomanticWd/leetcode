package com.leetcode.unionfind;

/**
 * 路径相关题目
 */
public class PathSolution {

    private int[] p;

    public static void main(String[] args) {
        PathSolution solution = new PathSolution();
        int[][] edges = new int[][]{{0, 1}, {1, 2}, {2, 0}};
        System.out.println(solution.validPath(3, edges, 0, 2));
    }

    /**
     * 1971. 寻找图中是否存在路径
     *
     * @param n
     * @param edges
     * @param source
     * @param destination
     * @return
     */
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        // 初始化并查集
        p = new int[n];
        for (int i = 0; i < n; i++) {
            // 并查集的根节点就是自己
            p[i] = i;
        }

        // 加载并查集
        for (int[] edge : edges) {
            p[find(edge[0])] = p[find(edge[1])];
        }
        return find(source) == find(destination);
    }

    /**
     * 查询并查集的根节点
     *
     * @param i
     * @return
     */
    private int find(int i) {
        if (p[i] != i) {
            p[i] = find(p[i]);
        }
        return p[i];
    }

}
