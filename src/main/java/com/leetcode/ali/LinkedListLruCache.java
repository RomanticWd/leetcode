package com.leetcode.ali;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description TODO
 * @createTime 2021/8/4 22:44
 */
public class LinkedListLruCache implements LruCache<Integer, Integer> {

    private HashMap<Integer, Integer> map;

    private LinkedList<Integer> list;

    private int capacity;


    public LinkedListLruCache(int capacity) {
        setCapacity(capacity);
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.list = new LinkedList<>();
    }

    @Override
    public void put(Integer cacheKey, Integer cacheValue) {
        if (map.containsKey(cacheKey)) {
            list.remove(cacheKey);
            list.addLast(cacheKey);
            map.put(cacheKey, cacheValue);
            return;
        }

        if (list.size() == capacity) {
            Integer first = list.getFirst();
            list.removeFirst();
            list.addLast(cacheKey);
            map.remove(first);
            map.put(cacheKey, cacheValue);
        } else {
            list.addLast(cacheKey);
            map.put(cacheKey, cacheValue);
        }
    }

    public int get(int key) {
        if (map.containsKey(key) && list.contains(key)) {
            list.remove(key);
            list.addLast(key);  // 删除元素，并追加到链表尾部
            return map.get(key);
        }
        return -1;
    }

    public static void main(String[] args) {
        LinkedListLruCache cache = new LinkedListLruCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);
        System.out.println(cache.get(2));
    }
}
