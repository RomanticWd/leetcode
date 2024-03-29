package com.leetcode.game;

import java.util.*;

/**
 * 游戏相关
 */
public class GameSolution {

    public static void main(String[] args) {
        GameSolution solution = new GameSolution();

        int[] arr = new int[]{1, 1, 2};

        System.out.println(solution.alphabetBoardPath("leet"));

    }

    //dirX和dirY分别对应着中心位置向上下左右偏移的位置，即遍历一个元素的所有包围他的8个位置
    int[] dirX = {0, 1, 0, -1, 1, 1, -1, -1};
    int[] dirY = {1, 0, -1, 0, 1, -1, -1, 1};

    /**
     * 529-扫雷游戏
     *
     * @param board
     * @param click
     */
    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0]; //横坐标
        int y = click[1]; //纵坐标

        //如果是地雷，改为X，游戏结束
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
        } else {
            dfs(board, x, y);
        }
        return board;
    }

    public void dfs(char[][] board, int x, int y) {
        int count = 0;

        for (int i = 0; i < 8; i++) {
            int tx = x + dirX[i];
            int ty = y + dirY[i];
            //如果在边上，则跳过
            if (tx < 0 || tx >= board.length || ty < 0 || ty >= board[0].length) {
                continue;
            }
            //计算一个元素周边，包围他的元素数量
            if (board[tx][ty] == 'M') {
                count++;
            }
        }
        //如果周围有雷，则显示雷的数量
        if (count > 0) {
            board[x][y] = (char) (count + '0');
        } else {
            //否则表示置为B表示周围没有雷
            board[x][y] = 'B';
            for (int i = 0; i < 8; i++) {
                int tx = x + dirX[i];
                int ty = y + dirY[i];
                //按照扫雷的规则，点一下如果没有雷，会将周围没有雷的都点卡，即置为B，所以需要遍历周围的元素
                if (tx < 0 || tx >= board.length || ty < 0 || ty >= board[0].length || board[tx][ty] != 'E') {
                    continue;
                }
                dfs(board, tx, ty);
            }
        }
    }

    //进入的房间数
    int num;
    //根据下标表示某房间是否进入过，true代表进入过
    boolean[] visit;

    /**
     * 841. 钥匙和房间
     *
     * @param rooms
     * @return
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        num = 0;
        int size = rooms.size();
        visit = new boolean[size];
        //从0号房间开始遍历
        dfs(rooms, num);
        return num == size;
    }

    public void dfs(List<List<Integer>> rooms, int x) {
        visit[x] = true;
        num++;
        for (Integer index : rooms.get(x)) {
            //如果这个房间没进入过
            if (!visit[index]) {
                dfs(rooms, index);
            }
        }
    }

    /**
     * 37. 解数独
     *
     * @param board
     */
    public void solveSudoku(char[][] board) {
        // rowUsed 表示此行内的数字是否被使用过,有9行 每行10个数字
        boolean[][] rowUsed = new boolean[9][10];
        // colUsed 表示此行内的数字是否被使用过
        boolean[][] colUsed = new boolean[9][10];
        // boxUsed 表示此3*3的框内的数字是否被使用过
        boolean[][][] boxUsed = new boolean[3][3][10];
        // 初始化，根据board内的情况进行三个布尔数组初始化
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int num = board[row][col] - '0';
                if (1 <= num && num <= 9) {
                    rowUsed[row][num] = true;
                    colUsed[col][num] = true;
                    boxUsed[row / 3][col / 3][num] = true;
                }

            }
        }
        recusiveSolveSudoku(board, rowUsed, colUsed, boxUsed, 0, 0);
    }

    public boolean recusiveSolveSudoku(char[][] board, boolean[][] rowUsed, boolean[][] colUsed, boolean[][][] boxUsed, int row, int col) {
        //最后一列
        if (col == board[0].length) {
            //col归零 进行下一行的递归处理
            col = 0;
            row++;
            // 已遍历完最后一行
            if (row == board.length) {
                return true;
            }
        }
        if (board[row][col] != '.') return recusiveSolveSudoku(board, rowUsed, colUsed, boxUsed, row, col + 1);
        for (int num = 1; num <= 9; num++) {
            // 如果这个数字，在此位置已经出现过，跳过本次循环
            if (rowUsed[row][num] || colUsed[col][num] || boxUsed[row / 3][col / 3][num]) continue;
            // 反之 说明这个数字可以放在这个位置
            rowUsed[row][num] = true;
            colUsed[col][num] = true;
            boxUsed[row / 3][col / 3][num] = true;
            // 将此数字放在此单元格内
            board[row][col] = (char) ('0' + num);

            //继续向下遍历，如果递归后面都返回true，说明数独填写的数字没有问题
            if (recusiveSolveSudoku(board, rowUsed, colUsed, boxUsed, row, col + 1)) return true;

            //否则说明当前循环处的数字不适合填进数独，将这些位置置为false，内容置为空，进行下次填写
            board[row][col] = '.';
            rowUsed[row][num] = false;
            colUsed[col][num] = false;
            boxUsed[row / 3][col / 3][num] = false;
        }
        return false;
    }

    /**
     * 135. 分发糖果
     *
     * @param ratings 得分
     * @return 最少糖果数
     */
    public int candy(int[] ratings) {

        /**
         * studentA  studentB
         * 左规则：ratingA < ratingB, B的糖果应该比A多，即candyB>candyA
         * 右规则：ratingA > ratingB, A的糖果应该比B多，即candyA>candyB
         * 相邻的学生中，评分高的学生必须获得更多的糖果 等价于 所有学生满足左规则且满足右规则
         */

        int length = ratings.length;
        int result = 0;

        // 满足左规则
        int[] left = new int[length];
        // 满足右规则
        int[] right = new int[length];
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);

        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            }
        }
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            }
        }

        //两者取大的数，这样既满足左规则，又满足右规则
        for (int i = 0; i < length; i++) {
            result += Math.max(left[i], right[i]);
        }
        return result;
    }

    /**
     * 1046. 最后一块石头的重量
     *
     * @param stones
     * @return
     */
    public int lastStoneWeight(int[] stones) {
        // PriorityQueue 优先级队列，默认从小到大排序，小的数字在队列最前，重新comparator方法，将大的数字排在队列最前。
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        for (int i = 0; i < stones.length; i++) {
            queue.offer(stones[i]);
        }
        while (queue.size() >= 2) {
            int y = queue.poll();
            int x = queue.poll();
            if (x != y) {
                queue.offer(y - x);
            }
        }
        return queue.isEmpty() ? 0 : queue.poll();
    }

    /**
     * 1128. 等价多米诺骨牌对的数量
     *
     * @param dominoes
     * @return
     */
    public int numEquivDominoPairs(int[][] dominoes) {
        int length = dominoes.length;
        int[] arr = new int[100];
        int result = 0;
        for (int i = 0; i < length; i++) {
            int[] dominoe = dominoes[i];
            int num = dominoe[0] < dominoe[1] ? dominoe[0] * 10 + dominoe[1] : dominoe[1] * 10 + dominoe[0];
            // 注意{1，2}，{1，2}，{2，1}三个共有1+2=3对，{1，2}，{1，2}，{2，1}，{2，1}四个有1+2+3=6对
            // 注意{1，2}，{1，2}，{2，1}，{1，3}，{3，1}共有1+2+1=4对
            result += arr[num];
            arr[num]++;
        }
        return result;
    }


    /**
     * 1052. 爱生气的书店老板
     *
     * @param customers
     * @param grumpy
     * @param X
     * @return
     */
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {

        // 窗口内的值
        int winSum = 0;
        int rightSum = 0;
        int len = customers.length;
        // 窗口右区间的值
        for (int i = X; i < len; ++i) {
            if (grumpy[i] == 0) {
                rightSum += customers[i];
            }
        }
        // 窗口的值  初始状态，窗口在最右边，左窗口为空
        for (int i = 0; i < X; ++i) {
            winSum += customers[i];
        }
        int leftSum = 0;
        //窗口左边缘
        int left = 1;
        //窗口右边缘
        int right = X;
        // 最大满意度=窗口+左窗口+右窗口
        int max = winSum + leftSum + rightSum;
        while (right < customers.length) {
            //重新计算左区间的值，也可以用 customer 值和 grumpy 值相乘获得
            if (grumpy[left - 1] == 0) {
                leftSum += customers[left - 1];
            }
            //重新计算右区间值
            if (grumpy[right] == 0) {
                rightSum -= customers[right];
            }
            //窗口值
            winSum = winSum - customers[left - 1] + customers[right];
            //保留最大值
            max = Math.max(max, winSum + leftSum + rightSum);
            // 移动窗口
            left++;
            right++;
        }
        return max;
    }

    /**
     * 354. 俄罗斯套娃信封问题
     *
     * @param envelopes
     * @return
     */
    public int maxEnvelopes(int[][] envelopes) {

        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                // 宽度相同，则比较高度
                // 宽度不同，则按宽度升序排序
                return o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0];
            }
        });

        //排序后，根据高度取最长递增子序列
        int[] height = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            height[i] = envelopes[i][1];
        }

        return lengthOfLIS(height);
    }

    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * 781. 森林中的兔子
     *
     * @param answers
     * @return
     */
    public int numRabbits(int[] answers) {
        // 分组将回答相同的兔子分到一个租中，key为回答，value为这个回答的兔子数量
        Map<Integer, Integer> map = new HashMap<>();
        for (int answer : answers) {
            map.put(answer, map.getOrDefault(answer, 0) + 1);
        }

        int count = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            // 回答的数，比如1只兔子回答5，，key=5，那么代表有5只兔子和它颜色相同，那么count最小为6
            int key = entry.getKey();
            // 回答相同的个数，两只兔子回答5，value=5
            int value = entry.getValue();
            // 13只兔子回答5，那么最少有(13/（5+1）)+1 = 3种颜色
            // 12只兔子回答5, 12/(5+1)+1=3种颜色 but two color is enough
            // so (value / (key + 1) + 1) is error
            int color = (key + value) / (key + 1);
            count += color * (key + 1);
        }
        return count;
    }

    /**
     * 810. 黑板异或游戏
     */
    public boolean xorGame(int[] nums) {
        /*异或规则：1. 任何数字和0异或还是数字本身
                  2. 相同数字异或结果为0
          如果是偶数，玩家alice先手，剩下数字是奇数。
            举例: 1,2,3,3 --》1，2，3 --》 1，2 --》1无论如何都可以获胜
          如果是奇数，玩家alice先手，剩下数字是偶数。玩家bob要么剩下偶数直接消除为0，bob获胜，要么剩下一个数字，无法被异或为0，还是bob获胜。
            举例: 1,2,3 --》1，2 --》1 无论如何都是失败
        */
        // alice获胜的两个条件满足任意一个：
        // 1. nums所有数字都能异或成0（根据游戏规则，轮到某个玩家时，如果当前黑板上所有数字异或结果等于 0，则当前玩家获胜）
        // 2. nums是偶数个

        if (nums.length % 2 == 0) {
            return true;
        }
        int result = 0;
        for (int i : nums) {
            result ^= i;
        }
        return result == 0;
    }

    /**
     * 1138. 字母板上的路径
     *
     * @param target
     * @return
     */
    public String alphabetBoardPath(String target) {
        // 初始位置
        int x = 0, y = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < target.length(); i++) {
            char c = target.charAt(i);
            // 5个字母是一行，比如字母‘a’，'a'-'a'=0, 横坐标是0，纵坐标是0, 比如字母‘h’，'h'-'a'=7, 所以横坐标是7%5 = 2,，纵坐标是7/5=1
            // 字母的横坐标
            int cx = (c - 'a') % 5;
            // 字母的纵坐标
            int cy = (c - 'a') / 5;
            // 考虑到字母‘z’所在位置只有一个字母，想输入z时候必须先左移再下移，所以优先左移，
            // 同理想从字母z到别的字母位置，必须先上移，再右移， 所以优先上移。
            // 字符横坐标位置比上次的位置小，说明要左移
            if (cx < x) {
                for (int j = 0; j < x - cx; j++) {
                    sb.append('L');
                }
            }
            // 字符纵坐标位置比上次的位置小，说明要上移
            if (cy < y) {
                for (int j = 0; j < y - cy; j++) {
                    sb.append('U');
                }
            }
            // 字符横坐标位置比上次的位置大，说明要右移
            if (cx > x) {
                for (int j = 0; j < cx - x; j++) {
                    sb.append('R');
                }
            }
            // 字符纵坐标位置比上次的位置大，说明要下移
            if (cy > y) {
                for (int j = 0; j < cy - y; j++) {
                    sb.append('D');
                }
            }
            // 如果和上次的字符相同，直接输入‘！’
            sb.append('!');
            x = cx;
            y = cy;
        }
        return sb.toString();
    }
}
