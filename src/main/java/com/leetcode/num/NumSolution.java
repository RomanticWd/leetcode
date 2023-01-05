package com.leetcode.num;

import java.util.*;

/**
 * 数字相关题目
 */
public class NumSolution {

    public static void main(String[] args) {
        NumSolution solution = new NumSolution();
        System.out.println(solution.countPairs(new int[]{9, 8, 4, 2, 1}, 5, 14));
    }

    /**
     * 263. 丑数
     */
    public boolean isUgly(int n) {
        if (n < 0) {
            return false;
        }

        int[] nums = new int[]{2, 3, 5};

        for (int num : nums) {
            // 能整除就一直除
            while (n % num == 0) {
                n = n / num;
            }
        }
        // 整除到最后如果只剩下2，3，5说明是丑数
        return n == 1;
    }

    /**
     * 264. 丑数 II
     */
    public int nthUglyNumber(int n) {

        // 1
        // 存2,3,5 队列中1，2，3，5 出2
        // 存2*2，2*3，2*5 => 4，6，10 队列中1，2，3，4，5，6，10 出3
        // 存3*2，3*3，3*5 => 6，9，15 队列中1，2，3，4，5，6，9，10，15 出4
        // 存4*2，4*3，4*5 => 8，12，20 队列中1，2，3，4，5，6，8，9，10，12，15，20 出5

        int[] nums = new int[]{2, 3, 5};

        // 因为题目说明1 <= n <= 1690，用int类型长度不够
        Queue<Long> queue = new PriorityQueue<>();
        Set<Long> set = new HashSet<>();
        queue.add(1l);
        set.add(1l);

        int res = 0;
        for (int i = 0; i < n; i++) {
            // 取出栈顶最小元素
            long curr = queue.poll();
            res = (int) curr;
            for (int num : nums) {
                // set中没有说明不是重复的
                if (!set.contains(curr * num)) {
                    queue.add(curr * num);
                    set.add(curr * num);
                }
            }
        }
        return res;
    }

    /**
     * 179. 最大数
     *
     * @param nums
     * @return
     */
    public String largestNumber(int[] nums) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(nums).boxed().sorted((o1, o2) -> {
            // 首先拼接成的两个字符串一定是等长的。等长的字符串在比较的时候，是按照字符串的各个字符从前向后逐个比较的，
            // 所以相当于先比较了百分位，然后比较十分位，最后比较个位。所以在字符串等长的情况下，字符串大，那么对应的整型也更大。
            String x = o1 + "" + o2;
            String y = o2 + "" + o1;
            // java中没有直接比较两个字符串大小的方法，还是得转为数字
            // 1:表示大于  -1:表示小于
            // 返回-1,比较器会从大到小排列，返回1,比较器会从小到大排列， 这里需要从大到小排列
            return Long.parseLong(x) >= Long.parseLong(y) ? -1 : 1;
        }).forEach(sb::append);
        // 排除'00'情况
        if (sb.charAt(0) == '0') return "0";
        return sb.toString();
    }

    /**
     * 633. 平方数之和
     *
     * @date: 2021/4/28
     */
    public boolean judgeSquareSum(int c) {

        /**
         * 为什么 low^2+high^2<c 时，要让low++而不是high++呢？或者说为什么让low++可以保证不错过正确答案呢？
         * 同理，为什么low^2+high^2>c 时，要让high--而不是low--呢？或者说为什么让high--可以保证不错过正确答案呢？
         *
         * 参考：https://leetcode-cn.com/problems/sum-of-square-numbers/solution/shuang-zhi-zhen-de-ben-zhi-er-wei-ju-zhe-ebn3/
         */
        long left = 0;
        long right = (long) Math.sqrt(c);

        while (left <= right) {
            long sum = left * left + right * right;
            if (sum == c) {
                return true;
            } else if (sum < c) {
                left++;
            } else {
                right--;
            }
        }
        return false;

    }

    /**
     * 剑指 Offer 15. 二进制中1的个数
     **/
    public int hammingWeight(int n) {
        int result = 0;

        while (n != 0) {
            // 与运算，因为1的二进制是0000 0001，n与1进行与运算就是判断最右侧是0还是1
            result += n & 1;
            // n右移一位，即每一位都与1进行与运算。最终和就是1的个数
            // 其中  >> : 右移运算符，num >> 1,相当于num除以2
            //     >>> : 无符号右移，忽略符号位，空位都以0补齐，这样最后n为0的时候就说明n已经右移完毕
            n >>>= 1;
        }
        return result;
    }

    /**
     * 1827. 最少操作使数组递增
     * 严格递增：数组的每一项都比前一项大
     *
     * @param nums
     * @return
     */
    public int minOperations(int[] nums) {
        int res = 0;
        int pre = 0;
        for (int num : nums) {
            // 如果是比之前的数字大：当前数字不变
            // 如果是比之前的小或者等于：当前数字为pre+1
            if (num <= pre) {
                res += pre + 1 - num;
                num = pre + 1;
            }
            pre = num;
        }
        return res;
    }

    /**
     * 1945. 字符串转化后的各位数字之和
     *
     * @param s
     * @param k
     * @return
     */
    public int getLucky(String s, int k) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int num = s.charAt(i) - 'a' + 1;
            int ten = num / 10;
            res += (ten + (num % 10));
        }
        while (--k > 0) {
            res = numParseTotal(res);
        }
        return res;
    }

    public int numParseTotal(int num) {
        int total = 0;
        while (num / 10 > 0) {
            total += (num % 10);
            num = num / 10;
        }
        total += num;
        return total;
    }

    /**
     * 1785. 构成特定和需要添加的最少元素
     * 1 <= nums.length <= 10^5
     * 1 <= limit <= 10^6
     * -limit <= nums[i] <= limit
     * -10^9 <= goal <= 10^9
     *
     * @param nums
     * @param limit
     * @param goal
     * @return
     */
    public int minElements(int[] nums, int limit, int goal) {
        // int会🈶有精度丢失的情况，所以用long型
        long total = 0;
        for (int num : nums) {
            total += num;
        }
        long diff = Math.abs(goal - total);
        if (diff % limit == 0) {
            return (int) (diff / limit);
        } else {
            return (int) ((diff / limit) + 1);
        }
    }

    /**
     * 1764. 通过连接另一个数组的子数组得到一个数组
     *
     * @param groups
     * @param nums
     * @return
     */
    public boolean canChoose(int[][] groups, int[] nums) {
        int i = 0;
        for (int k = 0; k < nums.length && i < groups.length; ) {
            if (checkNum(groups[i], nums, k)) {
                k += groups[i].length;
                i++;
            } else {
                k++;
            }
        }
        // i == groups数组的长度，说明存在
        return i == groups.length;
    }

    public boolean checkNum(int[] group, int[] nums, int k) {
        // groups子数组的长度 + nums数组的下标位超过 nums数组，说明nums中元素数量不够。
        if (group.length + k > nums.length) {
            return false;
        }

        for (int index = 0; index < group.length; index++) {
            if (group[index] != nums[k + index]) {
                return false;
            }
        }
        return true;

    }

    /**
     * 1760. 袋子里最少数目的球
     * 1 <= nums.length <= 105
     * 1 <= maxOperations, nums[i] <= 109
     *
     * @param nums
     * @param maxOperations
     * @return
     */
    public int minimumSize(int[] nums, int maxOperations) {
        int right = Arrays.stream(nums).max().getAsInt();
        int left = 1;
        int res = 0;
        // 开始二分查找
        while (left <= right) {
            // 求中间值
            int y = (left + right) / 2;
            // 这一次二分查找结果的总的操作次数, 由于1 <= nums.length <= 105，所以用long型
            long ops = 0;
            for (int num : nums) {
                // 当 nums[i]≤y时，我们无需进行操作；
                //当 y < nums[i] ≤ 2y 时，我们需要进行 1 次操作；
                //当 2y < nums[i] ≤ 3y 时，我们需要进行 2 次操作；
                // 综上 可以得出 2y < nums[i]-1 < 3y, 同时除以y取整 每个数字的操作次数为（nums[i]-1）/y
                ops += (num - 1) / y;
            }
            // 总操作次数大于规定次数，不符合要求，左边向右移动1位
            if (ops > maxOperations) {
                left = y + 1;
            } else {
                // 否则res = y，这里可以存到一个set中，最后取最小值，但是当while循环继续的时候，如果ops一直小于maxOperations，ops会越来越大，同样的y会越来越小。
                // 即 while循环只要满足条件，最后一次的res一定是最小的y
                res = y;
                right = y - 1;
            }
        }
        return res;
    }

    /**
     * 获取数组中缺少的数字。如{1，3，5，1，3}， 五个数字 缺少的就是{2，4}
     *
     * @param nums
     * @return
     */
    public List<Integer> getNoNumArray(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums.length == 0) {
            return res;
        }
        boolean[] flag = new boolean[nums.length];
        for (int i : nums) {
            if (!flag[--i]) {
                flag[i] = true;
            }
        }
        for (int i = 1; i < flag.length; i++) {
            if (flag[i]) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * 1802. 有界数组中指定下标处的最大值
     *
     * @param n      数组长度
     * @param index  指定下标，该下标处的数字应该最大
     * @param maxSum 所有元素和不能超过这个限制
     * @return
     */
    public int maxValue(int n, int index, int maxSum) {
        for (int ret = maxSum; ret >= 1; ret--) { // 从大到小暴力搜索
            // sum = ret 是因为从最大的数字处开始向左或者向右遍历
            long sum = ret; // 注意用 long，防止整数溢出
            for (int i = index + 1; i < n; i++) {
                // 下标index是最大值的话，右侧应该逐渐递减
                // 假如ret是最大值6，下标位是2，那么右边第一个下标为3的数字应该是5，如果递减到等于 1 后，后面还跟着 0 个或多个 1。
                sum += Math.max(ret - i + index, 1);
            }
            for (int i = index - 1; i >= 0; i--) {
                sum += Math.max(ret - index + i, 1);
            }
            if (sum <= maxSum) return ret;
        }
        return -1;
    }

    /**
     * 1803. 统计异或值在范围内的数对有多少
     * 1 <= nums.length <= 2 * 10^4
     * 1 <= nums[i] <= 2 * 10^4
     * 1 <= low <= high <= 2 * 10^4
     *
     * @param nums
     * @param low
     * @param high
     * @return
     */
    public int countPairs(int[] nums, int low, int high) {
        int req = 0;
        int[] freq = new int[20001];
        Arrays.fill(freq, 0);
        for (int num : nums) {
            freq[num]++;
        }
        for (int num : nums) {
            for (int i = low; i <= high; i++) {
                // 将将当前数字和 [low, high] 范围内的数字进行异或运算，将其结果对应的出现次数相加。 即num^i=num2， num2是异或的结果
                // 题目的要求是数组中的两个数字进行异或后在low-high之间，而现在用low和high之间的数字轮流和数组中的数字进行异或， 如果数字在20000内，说明满足要求
                // num^i=num2 <-> num^num2=i
                int num2 = i ^ num;
                if (num2 <= 20000) {
                    req += freq[num2];
                }
            }
            freq[num]--;
        }
        return req;
    }
}
