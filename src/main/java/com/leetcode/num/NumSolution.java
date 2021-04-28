package com.leetcode.num;

import java.util.*;

/**
 * 数字相关题目
 */
public class NumSolution {

    public static void main(String[] args) {
        NumSolution solution = new NumSolution();
        int[] nums = new int[]{3, 30, 34, 5, 9};
        System.out.println(solution.largestNumber(nums));
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

    /**
     * 264. 丑数 II
     */
    public int nthUglyNumber(int n) {

        // 1
        // 存2,3,5 队列中1，2，3，5 出2
        // 存2*2，2*3，2*5 => 4，6，10 队列中1，2，3，4，5，6，10 出3
        // 存3*2，3*3，3*5 => 6，9，15 队列中1，2，3，4，5，6，9，10，15 出4
        // 存4*2，4*3，4*5 => 8，12，20 队列中1，2，3，4，5，6，8，9，10，12，15，20 出5

        int[] nums = new int[]{2, 3, 5};

        // 因为题目说明1 <= n <= 1690，用int类型长度不够
        Queue<Long> queue = new PriorityQueue<>();
        Set<Long> set = new HashSet<>();
        queue.add(1l);
        set.add(1l);

        int res = 0;
        for (int i = 0; i < n; i++) {
            // 取出栈顶最小元素
            long curr = queue.poll();
            res = (int) curr;
            for (int num : nums) {
                // set中没有说明不是重复的
                if (!set.contains(curr * num)) {
                    queue.add(curr * num);
                    set.add(curr * num);
                }
            }
        }
        return res;
    }

    /**
     * 179. 最大数
     *
     * @param nums
     * @return
     */
    public String largestNumber(int[] nums) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(nums).boxed().sorted((o1, o2) -> {
            // 首先拼接成的两个字符串一定是等长的。等长的字符串在比较的时候，是按照字符串的各个字符从前向后逐个比较的，
            // 所以相当于先比较了百分位，然后比较十分位，最后比较个位。所以在字符串等长的情况下，字符串大，那么对应的整型也更大。
            String x = o1 + "" + o2;
            String y = o2 + "" + o1;
            // java中没有直接比较两个字符串大小的方法，还是得转为数字
            // 1:表示大于  -1:表示小于
            // 返回-1,比较器会从大到小排列，返回1,比较器会从小到大排列， 这里需要从大到小排列
            return Long.parseLong(x) >= Long.parseLong(y) ? -1 : 1;
        }).forEach(sb::append);
        // 排除'00'情况
        if (sb.charAt(0) == '0') return "0";
        return sb.toString();
    }

    /**
     * 633. 平方数之和
     *
     * @date: 2021/4/28
     */
    public boolean judgeSquareSum(int c) {

        /**
         * 为什么 low^2+high^2<c 时，要让low++而不是high++呢？或者说为什么让low++可以保证不错过正确答案呢？
         * 同理，为什么low^2+high^2>c 时，要让high--而不是low--呢？或者说为什么让high--可以保证不错过正确答案呢？
         *
         * 参考：https://leetcode-cn.com/problems/sum-of-square-numbers/solution/shuang-zhi-zhen-de-ben-zhi-er-wei-ju-zhe-ebn3/
         */
        long left = 0;
        long right = (long) Math.sqrt(c);

        while (left <= right) {
            long sum = left * left + right * right;
            if (sum == c) {
                return true;
            } else if (sum < c) {
                left++;
            } else {
                right--;
            }
        }
        return false;

    }
}
