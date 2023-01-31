package com.leetcode.graph;

/**
 * 图形相关
 *
 * @author: liudayue
 * @date: 2022-07-29 14:31
 **/
public class GraphSolution {

    public static void main(String[] args) {
        GraphSolution solution = new GraphSolution();
        int[][] grid = new int[][]{{2, 0, 0, 1}, {0, 3, 1, 0}, {0, 5, 2, 0}, {4, 0, 0, 2}};
        System.out.println(solution.checkXMatrix(grid));
    }

    int len = -1;

    /**
     * 593. 有效的正方形
     *
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return
     */
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        return isRightTriangle(p1, p2, p3) && isRightTriangle(p1, p2, p4)
                && isRightTriangle(p1, p3, p4) && isRightTriangle(p2, p3, p4);
    }

    /**
     * 根据三个点判断是否是等边直角三角形：两直角边相等，且两边的平方和等于第三边的平方
     *
     * @return
     */
    public boolean isRightTriangle(int[] p1, int[] p2, int[] p3) {
        // p2点到p1点之间点距离平方
        int l1 = (p2[0] - p1[0]) * (p2[0] - p1[0]) + (p2[1] - p1[1]) * (p2[1] - p1[1]);
        int l2 = (p3[0] - p2[0]) * (p3[0] - p2[0]) + (p3[1] - p2[1]) * (p3[1] - p2[1]);
        int l3 = (p3[0] - p1[0]) * (p3[0] - p1[0]) + (p3[1] - p1[1]) * (p3[1] - p1[1]);

        // 两边的平方和是否等于第三边的平方
        boolean flag = (((l1 == l2) && (l1 + l2) == l3) || ((l1 == l3) && (l1 + l3) == l2) || ((l3 == l2) && (l3 + l2) == l1));
        if (!flag) {
            return false;
        }

        // 如果长度为0 或者最短边长度不等于之前记录的，说明不是所有边长度相同，不是正方形
        if (len == -1) {
            // 记录最短边的长度，取平方小的为直角边的长度
            len = Math.min(l1, l2);
        } else return len != 0 && len == Math.min(l1, l2);
        return true;
    }

    /**
     * 2319. 判断矩阵是否是一个 X 矩阵
     * i横坐标 j纵坐标 i==j或者i+j=n-1 说明在对角线上
     *
     * @param grid
     * @return
     */
    public boolean checkXMatrix(int[][] grid) {
        int length = grid.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                int num = grid[i][j];
                if ((i == j || i + j == length - 1)) {
                    if (num == 0) {
                        return false;
                    }
                } else {
                    if (num != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
