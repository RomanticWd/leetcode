package com.leetcode.tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 二叉树相关
 */
public class TreeSolution {

    public static void main(String[] args) {
        /*Node left = new Node(2);
        left.left = new Node(4);
        left.right = new Node(5);
        Node right = new Node(3);
        right.left = new Node(6);
        right.right = new Node(7);
        Node root = new Node(1);
        root.left = left;
        root.right = right;*/

        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);

        TreeSolution solution = new TreeSolution();
        System.out.println(solution.preorderTraversal(treeNode));
    }

    /**
     * 94. 二叉树的中序遍历
     *
     * @param root
     * @return List<Integer>
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    public void inorder(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        inorder(root.left, result);
        result.add(root.val);
        inorder(root.right, result);
    }

    /**
     * 111. 二叉树的最小深度
     *
     * @param root
     * @return int
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        int min = Integer.MAX_VALUE;
        if (root.left != null) {
            min = Math.min(minDepth(root.left), min);
        }
        if (root.right != null) {
            min = Math.min(minDepth(root.right), min);
        }
        return min + 1;
    }

    /**
     * 257. 二叉树的所有路径
     *
     * @param root
     * @return List<String>
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        constructPath(root, "", paths);
        return paths;
    }

    public void constructPath(TreeNode root, String path, List<String> paths) {
        if (root != null) {
            StringBuilder sb = new StringBuilder(path);
            sb.append(root.val);
            if (root.left == null && root.right == null) {
                paths.add(sb.toString());
            } else {
                sb.append("->");
                constructPath(root.left, sb.toString(), paths);
                constructPath(root.right, sb.toString(), paths);
            }
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        TreeNode(int x) {
            val = x;
        }
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    /**
     * 538. 把二叉搜索树转换为累加树
     *
     * @param root
     * @return TreeNode
     */
    int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root != null) {
            convertBST(root.right);
            sum += root.val;
            root.val = sum;
            convertBST(root.left);
        }
        return root;
    }

    /**
     * 226. 翻转二叉树
     *
     * @param root
     * @return TreeNode
     */
    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;

            invertTree(root.left);
            invertTree(root.right);
        }
        return root;
    }

    /**
     * 617. 合并二叉树
     *
     * @param t1
     * @param t2
     * @return TreeNode
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        TreeNode merge = new TreeNode(t1.val + t2.val);
        merge.left = mergeTrees(t1.left, t2.left);
        merge.right = mergeTrees(t1.right, t2.right);
        return merge;
    }

    /**
     * 235. 二叉搜索树的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return TreeNode
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        /* 方法一
        List<TreeNode> pathP = getPath(root, p);
        List<TreeNode> pathQ = getPath(root, q);

        TreeNode result = null;
        for (int i = 0; i < pathP.size() && i < pathQ.size(); i++) {
            if (pathP.get(i).val==pathQ.get(i).val) {
                result = pathP.get(i);
            }
        }
        return result;*/
        /*方法二*/
        if (root.val > p.val && root.val > q.val) return lowestCommonAncestor(root.left, p, q);
        if (root.val < p.val && root.val < q.val) return lowestCommonAncestor(root.right, p, q);
        return root;
    }

    public List<TreeNode> getPath(TreeNode root, TreeNode p) {
        TreeNode node = root;
        List<TreeNode> treeNodes = new ArrayList<>();
        while (true) {
            treeNodes.add(node);
            if (p.val < node.val) {
                node = node.left;
            } else if (p.val > node.val) {
                node = node.right;
            } else {
                break;
            }
        }
        return treeNodes;
    }

    /**
     * 145. 二叉树的后序遍历
     *
     * @param root
     * @return List<Integer>
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    public void postorder(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        postorder(root.left, result);
        postorder(root.right, result);
        result.add(root.val);
    }

    /**
     * 530. 二叉搜索树的最小绝对差
     *
     * @param root
     * @return
     */
    int pre = 0;
    int min = 0;

    public int getMinimumDifference(TreeNode root) {
        min = Integer.MAX_VALUE;
        pre = -1;
        dfs(root);
        return min;
    }

    public void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        dfs(root.left);
        if (pre == -1) {
            //left不为空的时候，中序遍历保证左子树的叶子节点为最小值，将最小值赋值给pre
            pre = root.val;
        } else {
            //二叉搜索树的中序遍历保证了每个数都比前一个数大
            min = Math.min(min, root.val - pre);
            pre = root.val;
        }
        dfs(root.right);
    }

    /**
     * 116. 填充每个节点的下一个右侧节点指针
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }

        // 初始化队列同时将第一层节点加入队列中，即根节点
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        // 外层的 while 循环迭代的是层数
        while (!queue.isEmpty()) {
            // 记录当前队列大小
            int size = queue.size();

            // 遍历这一层的所有节点,按层级遍历，注意会先将此层级遍历完毕后，才会重置size的大小。
            // 比如第二层2个元素，第三层四个元素，那么第二层第二次for循环的时候，queue中有3个元素：3，4，5，但是size的大小还是2，所以不会进行连接操作，导致3.next=4
            for (int i = 0; i < size; i++) {
                // 从队首取出元素
                Node node = queue.poll();

                // 连接,即本层的最后一个元素.next=null
                if (i < size - 1) {
                    // 当前取出来的node连接到下一个node，如2，3为左右节点，存放再queue中，poll()取出2，2.next = 3
                    node.next = queue.peek();
                }
                // 拓展下一层节点
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        // 返回根节点
        return root;
    }

    /**
     * 144. 二叉树的前序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        dfs(root, res);
        return res;
    }

    public void dfs(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        res.add(root.val);
        dfs(root.left, res);
        dfs(root.right, res);
    }

    /**
     * 783. 二叉搜索树节点最小距离
     *
     * @param root
     * @return
     */
    public int minDiffInBST(TreeNode root) {
        // 二叉搜索树有个性质为二叉搜索树中序遍历(左中右)得到的值序列是递增有序的
        // 将二叉搜索树进行中序遍历，得到数组，求出相邻元素间的最小差值即为结果
        min = Integer.MAX_VALUE;
        pre = -1;
        dfs(root);
        return min;
    }

    /**
     * 872. 叶子相似的树
     *
     * @param root1
     * @param root2
     * @return
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {

        List<Integer> seq1 = new ArrayList<>();
        if (root1 != null) {
            dfsWithReturn(root1, seq1);
        }

        List<Integer> seq2 = new ArrayList<>();
        if (root2 != null) {
            dfsWithReturn(root2, seq2);
        }

        return seq1.equals(seq2);

    }


    private List<Integer> dfsWithReturn(TreeNode root, List<Integer> seq) {
        // 叶子节点
        if (root.left == null && root.right == null) {
            seq.add(root.val);
        }
        if (root.left != null) {
            dfsWithReturn(root.left, seq);
        }
        if (root.right != null) {
            dfsWithReturn(root.right, seq);
        }
        return seq;
    }

    /**
     * @description 671. 二叉树中第二小的节点
     * @date: 2021/7/27
     */
    Set<Integer> set = new HashSet<>();

    public int findSecondMinimumValue(TreeNode root) {
        dfs2(root);
        if (set.size() < 2) return -1;
        return set.stream().sorted().collect(Collectors.toList()).get(1);
    }

    void dfs2(TreeNode root) {
        if (root == null) return;
        set.add(root.val);
        dfs(root.left);
        dfs(root.right);
    }

    /**
     * @description 863. 二叉树中所有距离为 K 的结点
     * @date: 2021/7/28
     */
    // 存放节点key 和 父节点
    Map<Integer, TreeNode> parents = new HashMap<>();
    List<Integer> result = new ArrayList<>();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        // 从 root 出发 DFS，记录每个结点的父结点
        findParents(root);
        // 从 target 出发 DFS，寻找所有深度为 k 的结点
        findAns(target, null, 0, k);
        return result;
    }

    /**
     * 遍历节点，将节点key和父节点放入map
     */
    private void findParents(TreeNode root) {
        if (root.left != null) {
            parents.put(root.left.val, root);
            findParents(root.left);
        }
        if (root.right != null) {
            parents.put(root.right.val, root);
            findParents(root.right);
        }
    }

    /**
     * 从目标节点target出发，来源节点 from，depth为遍历深度，k为目标深度
     */
    private void findAns(TreeNode target, TreeNode from, int depth, int k) {
        if (target == null) {
            return;
        }
        if (depth == k) {
            result.add(target.val);
            return;
        }
        // 如果目标节点和来源节点相同，则说明此值已经判断过，无需遍历
        // 左节点
        if (target.left != from) {
            findAns(target.left, target, depth + 1, k);
        }
        // 右节点
        if (target.right != from) {
            findAns(target.right, target, depth + 1, k);
        }
        // 父节点
        if (parents.get(target.val) != from) {
            findAns(parents.get(target.val), target, depth + 1, k);
        }
    }
}
