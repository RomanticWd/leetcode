package com.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串相关
 */
public class StrSolution {

    public static void main(String[] args) {
        StrSolution solution = new StrSolution();
        System.out.println(solution.isIsomorphic("paper", "title"));
    }

    /**
     * 205. 同构字符串
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Character> sTot = new HashMap<>();
        Map<Character, Character> tTos = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char sChar = s.charAt(i);
            char tChar = t.charAt(i);
            if (sTot.containsKey(sChar) && sTot.get(sChar) != tChar) {
                return false;
            }
            if (tTos.containsKey(tChar) && tTos.get(tChar) != sChar) {
                return false;
            }
            sTot.put(sChar, tChar);
            tTos.put(tChar, sChar);
        }
        return true;
    }

    /**
     * 132. 分割回文串 II
     */
    public int minCut(String s) {
        int length = s.length();
        char[] chars = s.toCharArray();

        // st[i][j]表示下标i-j是不是回文字符串
        boolean[][] st = new boolean[length][length];

        // 初始化数组st[][]
        for (int j = 0; j < length; j++) {
            for (int i = j; i >= 0; i--) {
                // 只有一个字符肯定是回文串
                if (i == j) {
                    st[i][j] = true;
                } else {
                    // 2个字符时候，且相同字符是回文串
                    if (j - i + 1 == 2) {
                        st[i][j] = (chars[i] == chars[j]);
                    } else {
                        // 长度大于2时候，满足st[i+1][j-1]=true && char[i]==char[j]时候，st[i][j]=true
                        st[i][j] = (chars[i] == chars[j]) && (st[i + 1][j - 1]);
                    }
                }
            }
        }

        // dp[i]表示下标为i的时候的回文串最小分割次数
        int[] dp = new int[length];
        for (int j = 1; j < length; j++) {
            // 如果0-i是回文串，则无需分割
            if (st[0][j]) {
                dp[j] = 0;
            } else {
                // 类似于这种情况 aac,dp[0]=0,dp[1]=0,dp[2]=1; 这时候的c是占用一次分割次数的
                dp[j] = dp[j - 1] + 1;

                // 第 j 个字符本身不独立使用分割次数
                // 举例：abbc,
                // dp[0]=0,
                // dp[1]=dp[0]+1=1
                // 循环前：dp[2]=dp[1]+1=2,  循环后：dp[2] = min(2,2) = 2
                // 循环前：dp[3]=dp[2]+1=3,  循环后：dp[2] = min(3,2) = 2
                for (int i = 1; i < j; i++) {
                    if (st[i][j]) {
                        // 因为abbc中，bb是回文串，所以abb的回文串长度应该是2,
                        // 同理 abcbd ，bcb是回文串，所以abcb的回文串长度应该是2
                        dp[j] = Math.min(dp[j], dp[i - 1] + 1);
                    }
                }
            }
        }
        // 最后一个字符的最小分割次数
        return dp[length - 1];
    }
}
