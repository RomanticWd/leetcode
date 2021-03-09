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
}
