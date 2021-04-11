package com.leetcode.num;

/**
 * 数字相关题目
 */
public class NumSolution {

    public static void main(String[] args) {
        NumSolution solution = new NumSolution();
        System.out.println(solution.isUgly(6));
    }

    /**
     * 263. 丑数
     */
    public boolean isUgly(int n) {
        if (n < 0) {
            return false;
        }

        int[] nums = new int[]{2, 3, 5};

        for (int num : nums) {
            // 能整除就一直除
            while (n % num == 0) {
                n = n / num;
            }
        }
        // 整除到最后如果只剩下2，3，5说明是丑数
        return n == 1;
    }

}
