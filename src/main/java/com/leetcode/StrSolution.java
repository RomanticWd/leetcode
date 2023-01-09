package com.leetcode;

import java.util.*;

/**
 * 字符串相关
 */
public class StrSolution {

    public static void main(String[] args) {
        StrSolution solution = new StrSolution();
        System.out.println(solution.prefixCount(new String[]{"leetcode", "win", "loops", "success"}, "code"));

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

    /**
     * 剑指 Offer 38. 字符串的排列
     **/
    List<String> result = new ArrayList<>();
    char[] chars;

    public String[] permutation(String s) {
        chars = s.toCharArray();
        dfs(0);
        return result.toArray(new String[result.size()]);
    }

    void dfs(int x) {
        // 如果x是最后一位，此次递归结束
        if (x == chars.length - 1) {
            result.add(String.valueOf(chars));      // 添加排列方案
            return;
        }
        // 用于存放此次递归使用过的字符串，使用过就不再使用
        HashSet<Character> set = new HashSet<>();
        for (int i = x; i < chars.length; i++) {
            if (set.contains(chars[i])) continue; // 重复，因此剪枝
            set.add(chars[i]);
            swap(i, x);                      // 交换，将 c[i] 固定在第 x 位
            dfs(x + 1);                      // 开启固定第 x + 1 位字符
            swap(i, x);                      // 恢复交换
        }
    }

    void swap(int a, int b) {
        char tmp = chars[a];
        chars[a] = chars[b];
        chars[b] = tmp;
    }

    /**
     * 1781. 所有子字符串美丽值之和
     * 一个字符串的 美丽值 定义为：出现频率最高字符与出现频率最低字符的出现次数之差。
     *
     * @param s
     * @return
     */
    public int beautySum(String s) {
        // 双重遍历，外循环从头开始向后遍历，内循环从第一层循环开始位置向后遍历，最终会遍历出所有的子字符串。
        // 同时通过-‘a’的方式计算出当前位置字符串的位置的数值，出现一次数字加1，最终得出出现的次数

        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int[] cnt = new int[26];
            int maxFreq = 0;

            // 遍历子字符串
            for (int j = i; j < s.length(); j++) {
                cnt[s.charAt(j) - 'a']++;
                // 计算子字符串的最高出现频率
                maxFreq = Math.max(maxFreq, cnt[s.charAt(j) - 'a']);
                int minFreq = s.length();
                for (int k = 0; k < 26; k++) {
                    // 出现过
                    if (cnt[k] > 0) {
                        minFreq = Math.min(minFreq, cnt[k]);
                    }
                }
                res += maxFreq - minFreq;
            }
        }
        return res;
    }

    /**
     * 1832. 判断句子是否为全字母句
     * 全字母句 指包含英语字母表中每个字母至少一次的句子。
     *
     * @param sentence
     * @return
     */
    public boolean checkIfPangram(String sentence) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < sentence.length(); i++) {
            set.add(sentence.charAt(i));
        }
        return set.size() == 26;
    }

    /**
     * 1759. 统计同构子字符串的数目
     *
     * @param s
     * @return
     */
    public int countHomogenous(String s) {
        long res = 0;
        int mod = 1000000000 + 7;
        char pre = s.charAt(0);
        // 为了处理abbccc中ccc的情况，需要将个数放到for循环外
        int cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            char index = s.charAt(i);
            if (index != pre) {
                long sum = (long) (cnt + 1) * cnt / 2;
                res += sum;
                pre = index;
                // cnt置1，重新计数
                cnt = 1;
            } else {
                cnt++;
            }
        }
        long last = (long) (cnt + 1) * cnt / 2;
        res += last;
        return (int) (res % mod);
    }

    /**
     * 2027. 转换字符串的最少操作次数
     *
     * @param s
     * @return
     */
    public int minimumMoves(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'X') {
                res++;
                i += 2;
            }
        }
        return res;
    }

    /**
     * 2042. 检查句子中的数字是否递增
     *
     * @param s
     * @return
     */
    public boolean areNumbersAscending(String s) {
        String[] strArr = s.split(" ");
        int pre = 0;
        for (String str : strArr) {
            if (!isNumeric(str)) {
                continue;
            }
            int num = Integer.parseInt(str);
            if (num > pre) {
                pre = num;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean isNumeric(String string) {
        int intValue;
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 2185. 统计包含给定前缀的字符串
     *
     * @param words
     * @param pref
     * @return
     */
    public int prefixCount(String[] words, String pref) {
        int result = 0;
        for (String word : words) {
            if (word.startsWith(pref)) {
                result++;
            }
        }
        return result;
    }

}
