package com.leetcode.other;

/**
 * 杂
 *
 * @author yue.liu
 * @since [2021/6/10 19:14]
 */
public class OtherSolution {

    public static void main(String[] args) {
        OtherSolution solution = new OtherSolution();
        System.out.println(solution.climbStairs(2));
    }

    /**
     * 70. 爬楼梯
     *
     * @return int
     * @author yue.liu
     * @since 2021/6/10 19:15
     **/
    public int climbStairs(int n) {
        // 数组用于存放每个台阶的n种可能，dp[0]=1表示0级台阶有1种方法可以到达
        int[] dp = new int[n + 1];
        // 第一级
        dp[0] = 1;
        dp[1] = 1;

        // dp[2] = 2; dp[0] + dp[1] = 2; 可以从第0级胯2步到达，也可以从第1级跨1步到达。
        for (int i = 2; i < n + 1; i++) {
            //
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

}
