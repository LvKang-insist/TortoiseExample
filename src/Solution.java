import java.util.Stack;

/**
 * @author 345 QQ:1831712732
 * @name Solution
 * @package PACKAGE_NAME
 * @time 2022/08/09 13:56
 * @description
 */


public class Solution {


    /**
     * 题：将二叉搜索树转为首尾相连的双向链表，返回 head 结点
     * 解：中序遍历二叉树，保存 head 结点和 pre(上一个) 结点
     *    判断条件：pre == null，表示当前结点是 head 结点
     *    否则和当前结点互相指向即可
     * 时间复杂度：只需要遍历一次，O(n)
     * 空间复杂度：开辟了栈，所以也是 0(n)
     */
    public Node treeToDoublyList(Node root) {
        if (root == null) return null;
        Stack<Node> stack = new Stack<>();
        Node node = root;
        Node head = null;
        Node pre = null;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            if (pre == null) {
                head = node;
            } else {
                pre.right = node;
            }
            node.left = pre;
            pre = node;
            node = node.right;
        }
        head.left = pre;
        pre.right = head;
        return head;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    ;
}