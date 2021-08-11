package com.leetcode.ali;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description TODO
 * @createTime 2021/8/4 22:40
 */
public interface LruCache<K, V> {

    /**
     * Set the capacity of the total cache num
     */
    void setCapacity(int capacity);

    /**
     * Update cache according to LRU definition.
     * This time complexity of this method should be O(1).
     */
    void put(K cacheKey, V cacheValue);

}
