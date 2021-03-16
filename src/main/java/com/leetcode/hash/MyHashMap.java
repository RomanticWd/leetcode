package com.leetcode.hash;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 706. 设计哈希映射
 */
public class MyHashMap {

    // 数组的初始长度 如果过短，会导致hash碰撞（即大量的数add的时候取模值一样）
    private static final int BASE = 769;
    private LinkedList[] data;

    public MyHashMap() {
        data = new LinkedList[BASE];
        for (int i = 0; i < BASE; i++) {
            data[i] = new LinkedList<Pair>();
        }
    }

    public void put(int key, int value) {
        int hash = hash(key);
        // 对当前下标位的链表进行迭代
        Iterator<Pair> iterator = data[hash].iterator();
        while (iterator.hasNext()) {
            Pair pair = iterator.next();
            // 如果链表中存在此key值，进行value替换，循环结束
            if (pair.getKey() == key) {
                pair.setValue(value);
                return;
            }
        }
        // 否则将此键值对放入链表的最后
        data[hash].addLast(new Pair(key, value));
    }

    /**
     * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
     */
    public int get(int key) {
        int hash = hash(key);
        // 对当前下标位的链表进行迭代
        Iterator<Pair> iterator = data[hash].iterator();
        while (iterator.hasNext()) {
            Pair pair = iterator.next();
            // 如果链表中存在此key值，则返回value
            if (pair.getKey() == key) {
                // 存在key 则remove 且返回value
                return pair.getValue();
            }
        }
        // 不存在key则返回-1
        return -1;
    }

    /**
     * Removes the mapping of the specified value key if this map contains a mapping for the key
     */
    public void remove(int key) {
        int hash = hash(key);
        // 对当前下标位的链表进行迭代
        Iterator<Pair> iterator = data[hash].iterator();
        while (iterator.hasNext()) {
            Pair pair = iterator.next();
            // 如果链表中存在此key值，进行remove操作
            if (pair.getKey() == key) {
                data[hash].remove(pair);
                return;
            }
        }
    }

    // 计算hash值，将hash结果作为数组的下标位
    private static int hash(int key) {
        return key % BASE;
    }

    // 定义一个对象用于存放键值对映射值
    private static class Pair {
        private int key;

        private int value;

        public Pair(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}
