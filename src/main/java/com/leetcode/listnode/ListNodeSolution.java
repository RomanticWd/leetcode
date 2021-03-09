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
        ListNode three = new ListNode(3);
        ListNode four = new ListNode(4);
        ListNode five = new ListNode(5);
        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        ListNode newHead = solution.oddEvenList(one);
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
}
