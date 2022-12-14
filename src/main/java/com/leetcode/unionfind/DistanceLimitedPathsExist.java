package com.leetcode.unionfind;

import java.util.Arrays;

/**
 * 1697. 检查边长度限制的路径是否存在
 */
public class DistanceLimitedPathsExist {

    public static void main(String[] args) {
        DistanceLimitedPathsExist pathsExist = new DistanceLimitedPathsExist();
        int[][] edgeList = new int[][]{{0, 1, 10}, {1, 2, 5}, {2, 3, 9}, {3, 4, 13}};
        int[][] queries = new int[][]{{0, 4, 14}, {1, 4, 13}};
        boolean[] booleans = pathsExist.distanceLimitedPathsExist(5, edgeList, queries);
        for (boolean b : booleans) {
            System.out.println(b);
        }
    }

    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        // 按边长度从小到大进行排序
        Arrays.sort(edgeList, (a, b) -> a[2] - b[2]);

        // 按 limit 从小到大进行排序，这里不可以用Arrays.sort(queries, (a, b) -> a[2] - b[2]);方式排序
        // 因为这样排序后的queries本身顺序是错乱的，既入参顺序被改变了，会导致最终的输出结果也是乱序。
        // 所以通过new一个数组记录query的遍历顺序。
        Integer[] index = new Integer[queries.length];
        for (int i = 0; i < queries.length; i++) {
            index[i] = i;
        }
        Arrays.sort(index, (a, b) -> queries[a][2] - queries[b][2]);

        // 初始化，所有的节点父节点指向自己
        int[] uf = new int[n];
        for (int i = 0; i < n; i++) {
            uf[i] = i;
        }
        boolean[] res = new boolean[queries.length];
        int k = 0;
        for (int i : index) {
            // k < edgeList.length 表示不能超出边界
            // edgeList[k][2] < queries[i][2] 表示边的长度小于对应查询的 limit
            while (k < edgeList.length && edgeList[k][2] < queries[i][2]) {
                // 满足条件，进行并操作
                merge(uf, edgeList[k][0], edgeList[k][1]);
                k++;
            }
            // 查询，是否指向同一个父节点
            res[i] = find(uf, queries[i][0]) == find(uf, queries[i][1]);
        }
        return res;
    }

    public int find(int[] uf, int x) {
        if (uf[x] == x) {
            return x;
        }
        return uf[x] = find(uf, uf[x]);
    }

    public void merge(int[] uf, int x, int y) {
        x = find(uf, x);
        y = find(uf, y);
        uf[y] = x;
    }

}
