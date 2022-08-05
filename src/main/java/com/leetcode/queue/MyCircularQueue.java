package com.leetcode.queue;

/**
 * 622. 设计循环队列
 * 循环队列是一种线性数据结构，其操作表现基于 FIFO（先进先出）原则并且队尾被连接在队首之后以形成一个循环。它也被称为“环形缓冲器”。
 *
 * @author: liudayue
 * @date: 2022-08-05 16:58
 **/
public class MyCircularQueue {

    private int front;

    private int tail;

    private int capacity;

    private int[] elements;

    public MyCircularQueue(int k) {
        this.capacity = k + 1;
        elements = new int[capacity];
        front = tail = 0;
    }

    // 先进先出，增加元素放到队尾。
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        elements[tail] = value;
        // +1，下次enQueue放到再后面的位置。
        tail = (tail + 1) % capacity;
        return true;
    }

    // 先进先出，从队首出数据。
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        // 队首取元素后，指向下一个队首。
        front = (front + 1) % capacity;
        return true;
    }

    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return elements[front];
    }

    // 获取队尾元素
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        // 因为当前tail默认指向下一个插入队尾的位置，所以需要-1
        // + capacity是为了防止出现负数。
        int index = (tail - 1 + capacity) % capacity;
        return elements[index];
    }

    public boolean isEmpty() {
        //头尾一起就是空。
        return front == tail;
    }

    public boolean isFull() {
        // 循环队列，满了的时候，尾部+1=头
        return ((tail + 1) % capacity) == front;
    }
}
