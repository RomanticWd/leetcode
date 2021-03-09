package com.leetcode;

import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = {10, 9, 2, 5, 3, 7};
        System.out.println(solution.lengthOfLIS(nums));

    }

    /**
     * 647. 回文子串
     *
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        int n = s.length();
        int count = 0;
        for (int i = 0; i < 2 * n - 1; i++) {
            int l = i / 2;
            int r = l + i % 2;
            while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                count++;
                l--;
                r++;
            }
        }
        return count;
    }

    /**
     * 344. 反转字符串
     *
     * @param s
     */
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char temp = s[right];
            s[right] = s[left];
            s[left] = temp;
            left++;
            right--;
        }
    }

    /**
     * 459. 重复的子字符串
     *
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern(String s) {
        /*
         * 当 s 没有循环节时：abcd -> abcdabcd,从abcdabcd中找第二个abcd，此时indexof返回值为 s.size()。
         * 当 s 有循环节时，abcabc -> abcabcabcabc,从abcabcabcabc找第二个abcabc，返回值为3。
         * */
        //indexof从指定的索引位置开始，返回指定子字符串首次出现在该字符串中的索引。
        int length = (s + s).indexOf(s, 1);
        return length != s.length();
    }

    /**
     * 491 递增子序列
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> findSubsequences(int[] nums) {
        Set<List<Integer>> set = new HashSet<>();
        findSubList(set, nums, 0, new ArrayList<>());
        return new ArrayList<>(set);
    }

    public void findSubList(Set<List<Integer>> set, int[] nums, int index, List<Integer> tempList) {
        if (tempList.size() >= 2) {
            //注意，这里不能直接add(tempList)，会导致递归时候值传递最后set中所有的tempList都是同一个，值传递导致的问题
            set.add(new ArrayList<>(tempList));
        }
        for (int i = index; i < nums.length; i++) {
            // 后一个条件 start 指针到达边界，没有数字可选了，结束递归
            if (tempList.size() == 0 || tempList.get(tempList.size() - 1) <= nums[i]) {
                // 选择当前的数字
                tempList.add(nums[i]);
                // 继续往下递归
                findSubList(set, nums, i + 1, tempList);
                // 撤销选择当前数字，选择别的数字
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    /**
     * 416. 分割等和子集
     *
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        int len = nums.length;
        //数组的长度 n<2，则不可能将数组分割成元素和相等的两个子集
        if (len < 2) {
            return false;
        }
        int sum = 0, max = 0;
        for (int num : nums) {
            sum += num;
            max = Math.max(num, max);
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        //maxNum>target，则除了 maxNum 以外的所有元素之和一定小于target，因此不可能将数组分割成元素和相等的两个子集
        if (max > target) {
            return false;
        }
        //dp[i][j] 表示nums数组中下标从0-i的数中 和为j的情况是否存在
        boolean[][] dp = new boolean[len][target + 1];
        for (int i = 0; i < len; i++) {
            dp[i][0] = true;
        }
        dp[0][nums[0]] = true;

        for (int i = 1; i < len; i++) {
            for (int j = 0; j <= target; j++) {
                if (j >= nums[i]) {
                    dp[i][j] = dp[i - 1][j] | dp[i - 1][j - nums[i]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[len - 1][target];
    }

    /**
     * 1002. 查找常用字符
     *
     * @param A
     * @return
     */
    public List<String> commonChars(String[] A) {

        int[] minSeq = new int[26];
        Arrays.fill(minSeq, Integer.MAX_VALUE);
        List<String> result = new ArrayList<>();

        for (String s : A) {
            int len = s.length();
            int[] seq = new int[26];
            for (int i = 0; i < len; i++) {
                int index = s.charAt(i) - 'a';
                seq[index]++;
            }
            for (int i = 0; i < seq.length; i++) {
                minSeq[i] = Math.min(minSeq[i], seq[i]);
            }
        }
        for (int i = 0; i < minSeq.length; i++) {
            //i=0时候 就是字母a出现的次数，如果i出现0次，那么不会调用result.add方法，而最小次数如果是2，说明各个字符串中都出现了2次，会调用两次result.add
            for (int j = 0; j < minSeq[i]; j++) {
                result.add(String.valueOf((char) (i + 'a')));
            }
        }
        return result;
    }

    /**
     * 977. 有序数组的平方
     *
     * @param A
     * @return
     */
    public int[] sortedSquares(int[] A) {
        for (int i = 0; i < A.length; i++) {
            int num = A[i];
            A[i] = num * num;
        }
        Arrays.sort(A);
        return A;
    }

    /**
     * 1365. 有多少小于当前数字的数字
     *
     * @param nums
     * @return
     */
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int length = nums.length;
        int[] counts = new int[101];
        // counts存放nums中各个数字出现的次数
        for (int i = 0; i < length; i++) {
            counts[nums[i]]++;
        }
        //counts比自身小的数字出现的次数
        for (int i = 1; i < 101; i++) {
            counts[i] += counts[i - 1];
        }
        int[] res = new int[length];
        for (int i = 0; i < res.length; i++) {
            //防止nums中包含0的情况
            res[i] = nums[i] == 0 ? 0 : counts[nums[i] - 1];
        }
        return res;
    }

    /**
     * 463. 岛屿的周长
     *
     * @param grid
     * @return
     */
    public int islandPerimeter(int[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) {
                    // 题目限制只有一个岛屿，计算一个即可
                    return dfs(grid, r, c);
                }
            }
        }
        return 0;
    }

    public int dfs(int[][] grid, int row, int col) {
        //本地超出网格范围了，即说明上次是在网格边缘，应该+1
        if (!(0 <= row && row < grid.length && 0 <= col && col < grid[0].length)) {
            return 1;
        }
        //本次是水域，说明上次是从岛屿边缘过来的，应该+1
        if (grid[row][col] == 0) {
            return 1;
        }
        //经过等于0判断不成立，且不等于1，说明此处为2，是已经遍历过的岛屿
        if (grid[row][col] != 1) {
            return 0;
        }
        // 将方格标记为"已遍历"
        grid[row][col] = 2;
        return dfs(grid, row - 1, col)
                + dfs(grid, row + 1, col)
                + dfs(grid, row, col - 1)
                + dfs(grid, row, col + 1);
    }

    /**
     * 973. 最接近原点的 K 个点
     *
     * @param points
     * @param K
     * @return
     */
    public int[][] kClosest(int[][] points, int K) {

        Arrays.sort(points, new Comparator<int[]>() {
            public int compare(int[] point1, int[] point2) {
                return (point1[0] * point1[0] + point1[1] * point1[1]) - (point2[0] * point2[0] + point2[1] * point2[1]);
            }
        });
        return Arrays.copyOfRange(points, 0, K);
    }

    /**
     * 31. 下一个排列
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {

        int length = nums.length;
        for (int i = length - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {
                Arrays.sort(nums, i, length);
                for (int j = i; j < length; j++) {
                    if (nums[i - 1] < nums[j]) {
                        int temp = nums[j];
                        nums[j] = nums[i - 1];
                        nums[i - 1] = temp;
                        return;
                    }
                }
            }
        }
        Arrays.sort(nums);
    }

    /**
     * 922. 按奇偶排序数组 II
     *
     * @param A
     * @return
     */
    public int[] sortArrayByParityII(int[] A) {
        int[] result = new int[A.length];
        int index = 0;
        for (int num : A) {
            if (num % 2 == 0) {
                result[index] = num;
                index += 2;
            }
        }
        index = 1;
        for (int num : A) {
            if (num % 2 == 1) {
                result[index] = num;
                index += 2;
            }
        }
        return result;
    }

    /**
     * 406. 根据身高重建队列
     *
     * @param people
     * @return
     */
    public int[][] reconstructQueue(int[][] people) {
        //o2[0] - o1[0]保证按数组首位从大大小排序，o1[1] - o2[1]保证首位相同时，按第二位从小到大排序
        //这样就保证了最大的数字排在最前，且第二位必是0，不然在存放到list中会下标越界
        Arrays.sort(people, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0]);
        LinkedList<int[]> list = new LinkedList<>();
        for (int[] person : people) {
            list.add(person[1], person);
        }
        return list.toArray(new int[people.length][2]);
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     *
     * @param nums
     * @param target
     * @return int[]
     */
    public int[] searchRange(int[] nums, int target) {
        int length = nums.length;

        //第一个大于等于target的数字下标
        int leftIdx = binarySearch(nums, target, true);
        //第一个大于target的数字下标
        int rightIdx = binarySearch(nums, target, false) - 1;

        if (leftIdx <= rightIdx && rightIdx < length && nums[leftIdx] == nums[rightIdx] && nums[rightIdx] == target) {
            return new int[]{leftIdx, rightIdx};
        }
        return new int[]{-1, -1};
    }

    private int binarySearch(int[] nums, int target, boolean flag) {
        int left = 0, right = nums.length - 1, ans = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target || (flag && nums[mid] == target)) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    /**
     * 204. 计数质数
     *
     * @param n
     * @return int
     */
    public int countPrimes(int n) {
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);
        //  换句话说，如果在 [2,sqrt(n)] 这个区间之内没有发现可整除因子，就可以直接断定 n 是素数了，因为在区间 [sqrt(n),n] 也一定不会发现可整除因子。
        //例如n=12，2*6 3*4 sqrt(12)*sqrt(12) 4*3 6*2 后两个乘积就是前面两个反过来，反转临界点就在 sqrt(n)
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (isPrime[i]) {
                // i 的倍数不可能是素数了
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        System.out.println(Arrays.toString(isPrime));
        int count = 0;
        for (int i = 2; i < isPrime.length; i++) {
            if (isPrime[i]) {
                count++;
            }
        }
        return count;
    }

    /**
     * 738. 单调递增的数字
     *
     * @param N
     * @return
     */
    public int monotoneIncreasingDigits(int N) {
        char[] arr = Integer.toString(N).toCharArray();
        int max = -1, idx = -1;

        for (int i = 0; i < arr.length - 1; i++) {
            if (max < arr[i]) {
                max = arr[i];
                idx = i;
            }
            if (arr[i] > arr[i + 1]) {
                arr[idx] -= 1;
                for (int j = idx + 1; j < arr.length; j++) {
                    arr[j] = '9';
                }
                break;
            }
        }
        return Integer.parseInt(new String(arr));

    }

    /**
     * 290. 单词规律
     *
     * @param pattern 规律
     * @param s       单词
     * @return
     */
    public boolean wordPattern(String pattern, String s) {
        String[] strArr = s.split(" ");
        if (pattern.length() != strArr.length) {
            return false;
        }

        Map<Character, String> keyMap = new HashMap<>();
        Map<String, Character> valueMap = new HashMap<>();
        for (int i = 0; i < strArr.length; i++) {
            char key = pattern.charAt(i);
            if (keyMap.containsKey(key) && !keyMap.get(key).equals(strArr[i])) {
                return false;
            }
            //map中包含value，key却不同，如：abba => cat cat cat cat
            if (valueMap.containsKey(strArr[i]) && valueMap.get(strArr[i]).equals(key)) {
                return false;
            }
            keyMap.put(key, strArr[i]);
            valueMap.put(strArr[i], key);
        }
        return true;
    }

    /**
     * 389. 找不同
     *
     * @param s
     * @param t
     * @return
     */
    public char findTheDifference(String s, String t) {
        //为什么选最后一个作为初始ans？
        //因为t的长度比s大1，去掉t最后一位数字后，剩下的长度和s长度相同，只需根据s的length进行遍历即可
        char ans = t.charAt(t.length() - 1);
        for (int i = 0; i < s.length(); i++) {
            //异或操作满足结合律和交换律，且a^a=0,a^0=a;
            //综上a^b^c^a^c^b^d = (a^a)^(b^b)^(c^c)^d = 0^0^0^d = d
            ans ^= s.charAt(i);
            ans ^= t.charAt(i);
        }
        return ans;
    }

    /**
     * 714. 买卖股票的最佳时机含手续费
     *
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit(int[] prices, int fee) {

        int length = prices.length;
        int[][] dp = new int[length][2];
        // 第0天不持有的利润
        dp[0][0] = 0;
        //第0天持有的利润，即刚买入，相当于是负的
        dp[0][1] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            //只有状态从持有变成不持有的时候才会收取手续费
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee);
            dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
        }
        //最后一天不持有的收益，即卖出
        return dp[length - 1][0];

    }

    /**
     * 买卖股票的最佳时机 IV
     *
     * @param k      交易笔数
     * @param prices 股票每天的价格
     * @return
     */
    public int maxProfit(int k, int[] prices) {

        if (prices == null || prices.length == 0) {
            return 0;
        }
        int length = prices.length;
        // 买入+卖出 = 一次k，如果k小于长度的一半，则说明只能买入卖出一次
        if (k >= length / 2) {
            int[][] dp = new int[length][2];
            //第一次卖出
            dp[0][0] = 0;
            //第一次买入的收益
            dp[0][1] = -prices[0];

            for (int i = 1; i < prices.length; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
                dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
            }
            return Math.max(dp[length - 1][0], dp[length - 1][1]);
        }

        int[][][] dp = new int[length][k + 1][2];
        for (int i = 0; i <= k; i++) {
            //初始化第一天第i次交易卖出的收益
            dp[0][i][0] = 0;
            //初始化第一天第i次交易买入的收益,只要是第一天交易，买入收益都是-prices[0]
            dp[0][i][1] = -prices[0];
        }

        /**
         * 第一次买入：从初始状态转换而来，或者第一次买入后保持不动
         * dp[i][0][1] = max(dp[i-1][0][1],dp[i-1][0][0]-prices[i])
         *
         * 第一次卖出：从第一次买入转换而来，或者第一次卖出后保持不动
         * dp[i][1][0] = max(dp[i-1][1][0],dp[i-1][0][1]+prices[i])
         *
         * 第二次买入：从第一次卖出转换而来，或者第二次买入后保持不动
         * dp[i][1][1] = max(dp[i-1][1][1],dp[i-1][1][0]-prices[i])
         *
         * 第二次卖出：从第二次买入转换而来，或者第二次卖出后保持不动
         * dp[i][2][0] = max(dp[i-1][2][0],dp[i-1][1][1]+prices[i])
         */
        for (int i = 1; i < prices.length; i++) {
            for (int j = 1; j <= k; j++) {
                // 处理第k次买入 ：从第k-1次卖出转换而来（dp[i - 1][j - 1][1]），或者第k次买入后保持不动（dp[i - 1][j - 1][0] - prices[i])）
                dp[i][j - 1][1] = Math.max(dp[i - 1][j - 1][1], dp[i - 1][j - 1][0] - prices[i]);
                // 处理第k次卖出 ：从第k次买入后转换而来（dp[i - 1][j][0]），或者是第k次卖出后保持不动（dp[i - 1][j - 1][1] + prices[i]）
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j - 1][1] + prices[i]);
            }
        }
        return dp[length - 1][k][0];
    }

    /**
     * 746. 使用最小花费爬楼梯
     *
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {
        // 一步跨一个台阶 dp[i] = dp[i-1] + cost[i]
        // 一步跨两个台阶 dp[i] = dp[i-2] + cost[i] 两者中的最小值就是dp[i]最小花费
        int length = cost.length;
        int[] dp = new int[length];
        // dp[0] = cost[0]
        dp[0] = cost[0];
        // 一步跨一个台阶 dp[1] = dp[0]+ cost[1] = cost[0] + cost[1]
        // 一步跨两个台阶 dp[1] = cost[1]
        // 所以dp[1]取cost[0] + cost[1]与cost[1]的较小值cost[1]
        dp[1] = cost[1];
        for (int i = 2; i < cost.length; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }
        // 判断最后一步是跨两步还是一步
        return Math.min(dp[length - 1], dp[length - 2]);
    }

    /**
     * 830. 较大分组的位置
     *
     * @param s
     * @return
     */
    public List<List<Integer>> largeGroupPositions(String s) {

        int length = s.length();

        List<List<Integer>> result = new ArrayList<>();
        int count = 1;
        for (int i = 0; i < length; i++) {
            if (i == length - 1 || s.charAt(i) != s.charAt(i + 1)) {
                if (count >= 3) {
                    result.add(Arrays.asList(i - count + 1, i));
                }
                //每次不连续 都需要将count置为1
                count = 1;
            } else {
                count++;
            }
        }
        return result;


    }

    /**
     * 189. 旋转数组
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        int length = nums.length;
        int[] newArr = new int[length];
        for (int i = 0; i < length; i++) {
            newArr[(i + k) % length] = nums[i];
        }
        System.arraycopy(newArr, 0, nums, 0, length);
    }

    /**
     * 674. 最长连续递增序列
     *
     * @param nums
     * @return
     */
    public int findLengthOfLCIS(int[] nums) {
        int temp = 0;
        int start = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] <= nums[i - 1]) {
                start = i;
            }
            temp = Math.max(temp, i - start + 1);
        }
        return temp;
    }

    /**
     * 300. 最长递增子序列
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        // dp[i] 存放了i前包括i的递增子序列最大长度
        int[] dp = new int[nums.length];
        int max = 0;
        dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            // 找出i前，递增子序列的最大长度
            for (int j = 0; j < i; j++) {
                // 如果num[i]>num[j]，说明递增dp[i]应该由max(dp[0..j) +1
                /**
                 * 举例说明：{9, 2, 5, 3, 7}
                 * dp[0] = 1;dp[1] = 1;
                 * dp[2] = 2;因为5>2，而2的下标是1，dp[1]=1, dp[2]= dp[1]+1
                 * dp[3] = 2;因为3>2  而2的下标是1，dp[1]=1, dp[3]= dp[1]+1
                 * dp[4] = 3;因为7>3>2  而3的下标是3，dp[3]=2, dp[3]= dp[3]+1
                 */
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }

    /**
     * 665. 非递减数列
     *
     * @param nums
     * @return
     */
    public boolean checkPossibility(int[] nums) {

        for (int i = 1; i < nums.length; i++) {
            int x = nums[i - 1];
            int y = nums[i];
            // 出现递减的情况，如4，2  要么把4变成2 => 2,2 要么把2变成4  => 4,4
            // 再判断变化后是否满足单调递增的情况，满足则返回true
            if (x > y) {
                nums[i - 1] = y;
                if (isSorted(nums)) {
                    return true;
                }
                nums[i - 1] = x;
                nums[i] = x;
                return isSorted(nums);
            }
        }
        // 一次if条件都没满足，说没有没有递减的情况出现
        return true;
    }

    // 判断数组是否单调递增
    public boolean isSorted(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > nums[i]) {
                return false;
            }
        }
        return true;
    }

    public int subarraysWithKDistinct(int[] A, int K) {
        return atMostKDistinct(A, K) - atMostKDistinct(A, K - 1);
    }

    private int atMostKDistinct(int[] A, int K) {
        int len = A.length;

        //存放不同整数出现的次数
        int[] freq = new int[len + 1];

        int left = 0;
        int right = 0;
        // [left, right) 里不同整数的个数
        int count = 0;
        int res = 0;
        // [left, right) 包含不同整数的个数小于等于 K
        while (right < len) {
            // 如果没有出现过，则整数个数+1
            if (freq[A[right]] == 0) {
                // 不同整数的个数
                count++;
            }
            // 每次出现的整数记录在freq数组中
            freq[A[right]]++;
            // 同时右指针右移
            right++;

            // 超过K的大小后，右指针固定，左指针右移
            while (count > K) {
                // 左指针出现的数量减1，这样右指针在移动过程中如果，freq[A[right]]=0的时候，就证明这个数没出现过。
                freq[A[left]]--;
                if (freq[A[left]] == 0) {
                    count--;
                }
                left++;
            }
            // [left, right) 区间的长度就是对结果的贡献
            res += right - left;
        }
        return res;
    }
}
