package com.leetcode.other;

import java.util.Arrays;

/**
 * 杂
 *
 * @author yue.liu
 * @since [2021/6/10 19:14]
 */
public class OtherSolution {

    public static void main(String[] args) {
        OtherSolution solution = new OtherSolution();
        int[] cost = new int[]{1, 6, 3, 1, 2, 5};
        System.out.println(solution.maxIceCream(cost, 20));
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

    /**
     * 168. Excel表列名称
     *
     * @return java.lang.String
     * @author yue.liu
     * @since 2021/7/2 10:21
     **/
    public String convertToTitle(int columnNumber) {
        StringBuilder sb = new StringBuilder();

        while (columnNumber > 0) {
            // 比如数字是1，转为字母应该是'A'，应该是'A'+(1-1)，所以在转换前需要自减1
            columnNumber--;
            // 取余
            int i = columnNumber % 26;
            // 不转一次char的话最终存的会是数字
            sb.append((char) (i + 'A'));
            // 取整 最终取整会为0，所以while不能是 >= 0
            columnNumber = columnNumber / 26;
        }
        // 由于是取余，是先从低位开始计算的，append后是放在前面，需要反序输出
        sb.reverse();
        return sb.toString();
    }

    /**
     * 1833. 雪糕的最大数量
     *
     * @return int
     * @author yue.liu
     * @since 2021/7/2 12:28
     **/
    public int maxIceCream(int[] costs, int coins) {
        Arrays.sort(costs);

        int i = 0;
        int length = costs.length;
        while (i < length && costs[i] <= coins) {
            coins = coins - costs[i];
            i++;
        }
        return i;
    }
}
