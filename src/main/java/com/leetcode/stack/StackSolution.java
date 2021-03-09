package com.leetcode.stack;

import java.util.Stack;

public class StackSolution {

    public static void main(String[] args) {
        StackSolution solution = new StackSolution();
        System.out.println(solution.backspaceCompare("ab#c", "ad#c"));
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

}
