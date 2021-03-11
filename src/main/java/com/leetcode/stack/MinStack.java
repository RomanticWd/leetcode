package com.leetcode.stack;

import java.util.Stack;

/**
 * 155. 最小栈
 */
public class MinStack {

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.getMin();
        minStack.pop();
        minStack.top();
        minStack.getMin();
    }

    private Stack<Integer> stack;
    private Stack<Integer> order;

    public MinStack() {
        this.stack = new Stack<>();
        this.order = new Stack<>();
    }

    public void push(int x) {
        stack.push(x);
        if (order.isEmpty()) {
            order.push(x);
        } else {
            int min = order.peek();
            // 如果比当前栈顶值小，则将min放入栈顶。保证栈顶是最小的数字
            if (x < min) {
                order.push(x);
            } else {
                order.push(min);
            }
        }
    }

    public void pop() {
        if (!stack.isEmpty()) {
            order.pop();
            stack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return order.peek();
    }

}
