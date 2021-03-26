package com.leetcode.listnode;

import java.util.HashSet;
import java.util.Set;

/**
 * 链表相关
 */
public class ListNodeSolution {

    public static void main(String[] args) {

        ListNodeSolution solution = new ListNodeSolution();

        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(2);
        ListNode four = new ListNode(2);
        ListNode five = new ListNode(3);
        ListNode six = new ListNode(4);
        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        five.next = six;
        ListNode newHead = solution.deleteDuplicatesII(one);
        while (newHead != null) {
            System.out.println(newHead.val);
            newHead = newHead.next;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 141. 环形链表
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        ListNode cur = head;
        Set<ListNode> set = new HashSet<>();
        while (cur != null) {
            if (set.contains(cur)) {
                return true;
            } else {
                set.add(cur);
                cur = cur.next;
            }
        }
        return false;
    }

    /**
     * 142. 环形链表 II
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        ListNode cur = head;
        Set<ListNode> set = new HashSet<>();
        while (cur != null) {
            if (set.contains(cur)) {
                return cur;
            } else {
                set.add(cur);
                cur = cur.next;
            }
        }
        return null;
    }

    /**
     * 24. 两两交换链表中的节点
     *
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        /**
         * 1,2,3,4 -->  rest = 3, newHead = 2, newHead.next = 1,实现1与2两两交换
         *              1.next = swapPairs(rest) 即3与4交换后放到1.next
         */
        if (head == null || head.next == null) {
            return head;
        }
        ListNode rest = head.next.next;
        ListNode newHead = head.next;
        newHead.next = head;
        head.next = swapPairs(rest);
        return newHead;
    }

    /**
     * 19. 删除链表的倒数第N个节点
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dumpy = new ListNode(0, head);
        //定义快慢两个指针
        ListNode first = head;
        ListNode second = dumpy;
        //快指针先走n步
        for (int i = 0; i < n; i++) {
            first = first.next;
        }
        //快指针走到最后的时候，慢指针走到的位置刚好距离最后节点n个距离
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        //删除倒数第N个节点
        second.next = second.next.next;
        //疑似值传递，second=dumpy，故删除second的某位置的节点，dumpy中也被删除了
        return dumpy.next;
    }

    /**
     * 328. 奇偶链表
     * 单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性
     *
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode evenHead = head.next;
        ListNode odd = head;
        ListNode even = head.next;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    /**
     * 反转链表
     *
     * @param head
     * @param left
     * @param right
     * @return
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode topNode = new ListNode(-1);
        topNode.next = head;

        // 1. pre定位到left前一个位置
        ListNode pre = topNode;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        // 2.继续移动到right后面位置
        ListNode rightNode = pre;
        for (int i = left; i < right + 1; i++) {
            rightNode = rightNode.next;
        }

        // 3. 截取链表
        ListNode leftNode = pre.next;
        ListNode curNode = rightNode.next;

        // 4. 切断连接  这时候leftNode就是中间的一截了
        pre.next = null;
        rightNode.next = null;

        // 5. 中间那段翻转
        reverseLinkedList(leftNode);

        // 6. 翻转后重新链接
        pre.next = rightNode;
        leftNode.next = curNode;
        return topNode.next;

    }

    private void reverseLinkedList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;

        while (cur != null) {
            /**
             * 1<->2<->3
             * cur = 1, next = 2;
             * 1.next = null;
             * pre = 1;  这时候就实现了1和null翻转。
             * cur = 2; 当前节点指向2
             */
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
    }

    /**
     * 83. 删除排序链表中的重复元素
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode cur = head;
        while (cur.next != null) {
            if (cur.val == cur.next.val) {
                // 如果当前值和下一个值相同， 则当前节点不移动，next节点指向下下个节点
                // 如1-1-2，变成1-2,当前节点还是1
                cur.next = cur.next.next;
            } else {
                // 如果当前值和下一个值不同，当前节点向后移动
                // 如1-2-3 当前节点是2 因为节点保持递增，只要相邻的两个节点值不同，就不会有重复的。
                cur = cur.next;
            }
        }
        return head;
    }

    /**
     * 82. 删除排序链表中的重复元素 II
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicatesII(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode topNode = new ListNode(-1);
        topNode.next = head;

        ListNode cur = topNode;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                // 如果两个值相同 则开始删除操作
                int temp = cur.next.val;
                while (cur.next != null && cur.next.val == temp) {
                    // 删除cur.next
                    cur.next = cur.next.next;
                }
            } else {
                // 遍历操作
                cur = cur.next;
            }
        }
        return topNode.next;
    }
}
