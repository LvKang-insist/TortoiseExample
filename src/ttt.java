import java.util.HashMap;

/**
 * @author 345 QQ:1831712732
 * @name ttt
 * @package PACKAGE_NAME
 * @time 2022/07/07 23:21
 * @description
 */
public class ttt {
    HashMap<Node,Node> cachedNode=new HashMap<>();
    Node head=null;
    public Node copyRandomList(Node node){
        head=node;
        return copyLinkedList(node);
    }
    public  Node copyLinkedList(Node node){
        if (node==null) return cachedNode.get(head);
        cachedNode.put(node,new Node(node.val));
        copyLinkedList(node.next);
        cachedNode.get(node).next=cachedNode.get(node.next);
        cachedNode.get(node).random=cachedNode.get(node.random);
        return cachedNode.get(head);
    }
}
