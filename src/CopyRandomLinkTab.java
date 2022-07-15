import java.util.HashMap;

/**
 * @author 345 QQ:1831712732
 * @name CopyRandomLinkeTab
 * @package
 * @time 2022/07/07 13:53
 * @description
 */


// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}

class Test {


    HashMap<Node, Node> map = new HashMap<>();

    /**
     * 使用 map 对结点进行映射，key 为旧的，value 为新的
     * 最后通过再map中查找旧结点的 random 就可以拿到新的结点
     * */
    public Node copyRandomList2(Node head) {
        Node node = head;
        while (node != null) {
            map.put(node, new Node(node.val));
            node = node.next;
        }
        node = head;
        while (node != null) {
            Node temp = map.get(node);
            temp.next = map.get(node.next);
            temp.random = map.get(node.random);
            node = node.next;
        }
        return map.get(head);
    }


    /**
     * 思路：遍历链表，每次拷贝完成之后将结果插入到当前结点的后面。
     * 这样就可以保证 random 指向的结点下一个就是拷贝之后的新结点
     * 然后遍历链表，拷贝结点 random 指向上一个结点的 random 的 下一个结点，即时最新值
     */
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Node node = head;

        //复制当前结点，并连接到当前结点后面
        while (node != null) {
            Node newNode = new Node(node.val);
            Node temp = node.next;
            node.next = newNode;
            newNode.next = temp;
            node = temp;
        }

        //因为新结点的是复制的上一个结点，
        //所以上一个结点的 random 指向的结点的后面就是新建结点。
        //然后指向即可。
        node = head;
        while (node != null) {
            node.next.random = node.random == null ? null : node.random.next;
            node = node.next.next;
        }

        node = head;
        Node cloneNode = head.next;
        //分离链表
        while (node.next != null) {
            Node temp = node.next;
            node.next = node.next.next;
            node = temp;
        }

        return cloneNode;
    }


    public static void main(String[] args) {
        Node n1 = new Node(2);
        Node n2 = new Node(3);
        Node n3 = new Node(4);

        n1.next = n2;
        n2.next = n3;


//        n3.random = n2;

        Test test = new Test();
        Node head = test.copyRandomList2(n1);
        while (head != null) {
            System.out.println("---- " + head.val);
            head = head.next;
        }
    }
}
