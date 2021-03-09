package com.leetcode.queue;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 队列相关
 */
public class QueueSolution {

    public static void main(String[] args) {

    }

    /**
     * 347. 前 K 个高频元素
     *
     * @param nums
     * @param k
     * @return int[]
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        queue.addAll(map.entrySet());
        // 取出最小堆中的元素
        int[] res = new int[k];
        for (int i = 0; i < k; ++i) {
            res[i] = queue.remove().getKey();
        }
        return res;
    }

}
