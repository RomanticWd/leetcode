package com.leetcode;

import java.util.*;

/**
 * 组合相关题
 */
public class CombineSolution {

    public static void main(String[] args) {
        CombineSolution solution = new CombineSolution();
        int[] nums = new int[]{1, 2, 3};
        System.out.println(solution.subsets(nums));
    }

    /**
     * 40. 组合总和Ⅱ
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        int length = candidates.length;
        List<List<Integer>> result = new ArrayList<>();
        if (length == 0) {
            return result;
        }
        //排序后可以在递归中判断连续两个数字是否相同，从而排除掉一个组合中相同的数字
        Arrays.sort(candidates);
        dfs(candidates, 0, length, target, new ArrayDeque<>(), result);
        return result;
    }

    /**
     * @param candidates 候选数组
     * @param begin      搜索起点
     * @param length     冗余变量，是 candidates 里的属性，可以不传
     * @param target     每减去一个元素，目标值变小
     * @param path       从根结点到叶子结点的路径，是一个栈
     * @param result     结果集列表
     */
    public void dfs(int[] candidates, int begin, int length, int target, Deque<Integer> path, List<List<Integer>> result) {
        //candidates不会有比0小的数
        if (target < 0) {
            return;
        }
        //target==0说明前几个数字和刚好等于target
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }

        /*
         * 在for循环中i是一直在递增的，一次for循环结束就代表着从第一个数字递减的所有的可能性都排除了，下次不应该再出现这个数字了
         * 所以i从begin开始递归，这样可以避免出现重复的情况。
         * 如：[2,3,7]会计算出[2, 2, 3], [2, 3, 2], [3, 2, 2], [7]这4种情况，但是[2, 2, 3], [2, 3, 2], [3, 2, 2]是重复的。
         * 就是由于递归中的for循环每次都从0开始。
         */
        for (int i = begin; i < length; i++) {
            /*
            *     1
                 / \
                2   2  这种情况不会发生 但是却允许了不同层级之间的重复
               /     \
              5       5
                例2
                  1
                 /
                2      这种情况确是允许的
               /
              2
            * */
            // 因为题目要求每个元素只使用一次 当需要{1, 2, 2}的时候， 第二个2的 下标一定是 cur == begin;
            // 当出现了一个{1,2,5}的时候，进行到第二次{1,2,5}到{1,2}的时候，第二个未完成的{1,2,5}的2的下标一定是cur>begin的，
            // 直接跳过就不会产生相同的{1,2,5}了，同理如果有第3个2也可以跳过
            if (i > begin && candidates[i] == candidates[i - 1]) {
                continue;
            }

            path.addLast(candidates[i]);
            //在一次for循环中，由于每一个元素可以重复使用，下一轮搜索的起点依然是 i
            dfs(candidates, i + 1, length, target - candidates[i], path, result);
            // 状态重置，如2，2，2，2情况尝试完毕后，会removeLast成为2，2，2，此情况也不满足，
            // 再次removeLast成为2，2这时候尝试数字3，即2，2，3满足，-> 2,2 -> 2,3 ->...
            path.removeLast();
        }
    }

    /**
     * 216. 组合总和Ⅲ
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(1, n, k, new ArrayDeque<>(), result);
        return result;
    }

    /**
     * @param begin  搜索起点
     * @param target 每减去一个元素，目标值变小
     * @param k      子集元素个数
     * @param path   从根结点到叶子结点的路径，是一个栈
     * @param result 结果集列表
     */
    public void dfs(int begin, int target, int k, Deque<Integer> path, List<List<Integer>> result) {
        //candidates不会有比0小的数
        if (target < 0) {
            return;
        }

        //k需要和子集的元素数相等
        if (k < path.size()) {
            return;
        }
        //target==0说明前几个数字和刚好等于target, k == path.size()表示子集中元素个数符合要求
        if (target == 0 && k == path.size()) {
            result.add(new ArrayList<>(path));
            return;
        }

        /*
         * 在for循环中i是一直在递增的，一次for循环结束就代表着从第一个数字递减的所有的可能性都排除了，下次不应该再出现这个数字了
         * 所以i从begin开始递归，这样可以避免出现重复的情况。
         * 如：[2,3,7]会计算出[2, 2, 3], [2, 3, 2], [3, 2, 2], [7]这4种情况，但是[2, 2, 3], [2, 3, 2], [3, 2, 2]是重复的。
         * 就是由于递归中的for循环每次都从0开始。
         */
        for (int i = begin; i <= 9; i++) {
            path.addLast(i);
            //在一次for循环中，由于每一个元素可以重复使用，下一轮搜索的起点依然是 i
            dfs(i + 1, target - i, k, path, result);
            // 状态重置，如2，2，2，2情况尝试完毕后，会removeLast成为2，2，2，此情况也不满足，
            // 再次removeLast成为2，2这时候尝试数字3，即2，2，3满足，-> 2,2 -> 2,3 ->...
            path.removeLast();
        }
    }

    /**
     * 39. 组合总和
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int length = candidates.length;
        List<List<Integer>> result = new ArrayList<>();
        if (length == 0) {
            return result;
        }
        dfs(candidates, 0, length, target, new ArrayDeque<>(), result);
        return result;
    }

    /**
     * 78. 子集
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(0, nums, result, new ArrayList<>());
        return result;
    }

    public void dfs(int begin, int[] nums, List<List<Integer>> result, List<Integer> temp) {
        //注意：这里不能用result.add(temp);，因为按值传递，最后temp会进行remove，导致result中之前存放的temp都是空
        result.add(new ArrayList<>(temp));
        for (int i = begin; i < nums.length; i++) {
            temp.add(nums[i]);
            dfs(i + 1, nums, result, temp);
            temp.remove(temp.size() - 1);
        }
    }
}
