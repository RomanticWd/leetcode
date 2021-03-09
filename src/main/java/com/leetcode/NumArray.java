package com.leetcode;

/**
 * 前缀和
 * 303.区域和检索 - 数组不可变
 */
public class NumArray {

    private int[] sums;

    public NumArray(int[] nums) {
        int length = nums.length;
        sums = new int[length + 1];
        /**
         * 举例说明：数组{-2，0，3，-5，2，-1}
         * sum[0] = 0,
         * sum[1] = -2 = sum[0]+(-2),
         * sum[2] = (-2) + (0) = sum[1]+(0),
         * sum[3] = (-2) + (0) + (3) = sum[2]+(3),
         * sum[4] = (-2) + (0) + (3) + (-5) = sum[3]+(-5),
         * sum[5] = (-2) + (0) + (3) + (-5) + (2) = sum[4]+(2),
         * sum[6] = (-2) + (0) + (3) + (-5) + (2) + (-1) = sum[5]+(-1),
         *
         * 统计2-4之间的和：
         * (3) + (-5) + (2) = sum[5] - sum[2]
         *
         * 由此得出：sumRange(i,j) = sum[j+1] - sum[i]
         */
        for (int i = 0; i < nums.length; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
    }

    public int sumRange(int i, int j) {
        return sums[j + 1] - sums[i];
    }

}
