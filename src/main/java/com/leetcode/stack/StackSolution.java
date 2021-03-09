package com.leetcode.stack;

import java.util.Stack;

public class StackSolution {

    public static void main(String[] args) {
        StackSolution solution = new StackSolution();
        System.out.println(solution.removeDuplicates("abbaca"));
    }

    /**
     * 844. 比较含退格的字符串
     *
     * @param S
     * @param T
     * @return
     */
    public boolean backspaceCompare(String S, String T) {
        return build(S).equals(build(T));
    }

    public String build(String s) {
        int length = s.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c != '#') {
                stack.push(c);
            } else {
                if (!stack.empty()) stack.pop();
            }
        }
        return String.valueOf(stack);
    }

    /**
     * 1047. 删除字符串中的所有相邻重复项
     *
     * @param S
     */
    public String removeDuplicates(String S) {
        int length = S.length();

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < length; i++) {
            char c = S.charAt(i);
            if (stack.empty()) {
                stack.push(c);
            } else {
                Character top = stack.peek();
                if (top == c) {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            }
        }
        // 直接String.valueOf(stack)返回的是[c, a]，需要处理一下返回格式
        StringBuilder sb = new StringBuilder();
        for (Character character : stack) {
            sb.append(character);
        }
        return sb.toString();
    }

}
