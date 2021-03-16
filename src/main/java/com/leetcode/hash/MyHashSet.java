package com.leetcode.hash;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 705. 设计哈希集合
 */
public class MyHashSet {

    // 数组的初始长度 如果过短，会导致hash碰撞（即大量的数add的时候取模值一样）
    private static final int BASE = 769;
    private LinkedList[] data;


    public MyHashSet() {
        // 初始化链表数组
        data = new LinkedList[BASE];
        for (int i = 0; i < BASE; i++) {
            data[i] = new LinkedList<Integer>();
        }
    }

    public void add(int key) {
        int hash = hash(key);
        // 对当前下标位的链表进行迭代
        Iterator<Integer> iterator = data[hash].iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            // 如果链表中存在此key值，循环结束
            if (value == key) {
                return;
            }
        }
        // 否则将此key放入链表的最后
        data[hash].addLast(key);
    }

    public void remove(int key) {
        int hash = hash(key);
        // 对当前下标位的链表进行迭代
        Iterator<Integer> iterator = data[hash].iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            // 如果链表中存在此key值，进行remove操作
            if (value == key) {
                // 注意remove()会removeFirst，而remove(value)才会remove指定key
                data[hash].remove(value);
                return;
            }
        }
    }

    /**
     * Returns true if this set contains the specified element
     */
    public boolean contains(int key) {
        int hash = hash(key);
        // 对当前下标位的链表进行迭代
        Iterator<Integer> iterator = data[hash].iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            // 如果链表中存在此key值，进行remove操作
            if (value == key) {
                return true;
            }
        }
        return false;
    }

    // 计算hash值，将hash结果作为数组的下标位
    private static int hash(int key) {
        return key % BASE;
    }
}
