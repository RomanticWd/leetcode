package com.leetcode.stack;

import java.util.Stack;

public class StackSolution {

    public static void main(String[] args) {
        StackSolution solution = new StackSolution();
        System.out.println(solution.calculateII("3+2*2"));
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

    /**
     * 224. 基本计算器
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        int length = s.length();
        Stack<Integer> stack = new Stack<>();
        // sign表示正负，正即是加，负即为减
        int sign = 1;
        int res = 0;

        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            // 如果是数字
            if (Character.isDigit(c)) {
                // 当前的数字值
                int cur = c - '0';
                // 当下一位还是数字的时候，进位
                while (i + 1 < length && Character.isDigit(s.charAt(i + 1))) {
                    // 同时i自加1
                    cur = cur * 10 + s.charAt(++i) - '0';
                }
                res = res + sign * cur;
            } else if (c == '+') {
                sign = 1;
            } else if (c == '-') {
                sign = -1;
            } else if (c == '(') {
                // 每次遇到（ 时候，将括号前的和与加减符号推入栈中，开始计算括号中的值
                stack.push(res);
                res = 0;
                // 注意是先推入和，再推入符号
                stack.push(sign);
                sign = 1;
            } else if (c == ')') {
                // 遇到 ） 将堆栈中的数和符号出栈，由于之前是先推入数字，后推入符号，所以符号在栈顶
                int sign2 = stack.pop();
                int res2 = stack.pop();
                // 当前值应该为 括号内的和res * 左括号前的符号sign2 + 左括号前的数字和res2
                res = res2 + res * sign2;
            }
        }
        return res;
    }

    /**
     * 227. 基本计算器 II
     *
     * @param s
     * @return
     */
    public int calculateII(String s) {
        int length = s.length();
        Stack<Integer> stack = new Stack<>();
        // 预设第一个数字前是‘+’
        int num = 0;
        char preSign = '+';
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);

            // 如果是数字
            if (Character.isDigit(c)) {
                // 当前的数字值
                int cur = c - '0';
                // 当下一位还是数字的时候，进位
                while (i + 1 < length && Character.isDigit(s.charAt(i + 1))) {
                    // 同时i自加1
                    cur = cur * 10 + s.charAt(++i) - '0';
                }
                num = cur;
            }
            if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == length - 1) {
                // 不是数字时候，c不是空格或者c是最后一位
                if (c != ' ' || i == length - 1) {
                    switch (preSign) {
                        case '+':
                            // 加的时候，不需要优先计算，直接放入stack即可
                            stack.push(num);
                            break;
                        case '-':
                            // 减的时候，不需要优先计算，直接将负值放入stack即可
                            stack.push(-num);
                            break;
                        case '*':
                            // 乘的时候，需要优先计算，将栈顶数字取出做运算后再放入栈中
                            int top = stack.pop();
                            stack.push(top * num);
                            break;
                        case '/':
                            stack.push(stack.pop() / num);
                            break;
                    }
                    // 不是数字的时候，即为符号，将符号位preSign置为当前符号
                    preSign = c;
                    num = 0;
                }
            }
        }
        int res = 0;
        for (int value : stack) {
            res += value;
        }
        return res;
    }

}
