import java.util.*

/**
 * @name ReverseLinkPrint
 * @package
 * @author 345 QQ:1831712732
 * @time 2022/07/06 14:29
 * @description 题：从尾到头打印链表
 *  解1：遍历链表，将值 push 到栈中，最后转入数组中即可
 *  解2：使用一个数组即可，无需开辟其他空间，执行速度和空间占用都比较小
 *  如果使用递归，数据太多可能速度回较慢，并且可能堆栈溢出
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

/**
 * 记录上一个 和当前的结点即可
 * 每次循环让当前结点指向上一个结点，然后更新上一个结点即可。
 * */
fun reverseList(head: ListNode?): ListNode? {
    if (head == null) return null
    var cur = head
    var pre: ListNode? = null
    while (cur != null) {
        val temp = cur.next
        cur.next = pre
        pre = cur
        cur = temp
    }
    return pre
}

fun reverseList1(head: ListNode?): ListNode? {
    if (head?.next == null) return head
    val s = reverseList1(head.next)
    head.next?.next = head
    head.next = null
    return s
}


fun reversePrint(head: ListNode?): IntArray {
    val stack = Stack<Int>()
    if (head == null) return intArrayOf()
    var next = head
    while (next != null) {
        stack.push(next.`val`)
        next = next.next
    }
    val intarray = IntArray(stack.size)
    for (i in intarray.indices) {
        intarray[i] = stack.pop()
    }
    return intarray
}

fun reversePrint2(head: ListNode?): IntArray {
    if (head == null) return intArrayOf()
    var head1 = head
    var count = 0
    while (head1 != null) {
        count++
        head1 = head1.next
    }
    var head2 = head
    val intarray = IntArray(count)
    for (i in intarray.size - 1 downTo 0) {
        intarray[i] = head2!!.`val`
        head2 = head2.next
    }
    return intarray
}

fun main() {
    val s1 = ListNode(1)
    val s2 = ListNode(3)
    val s3 = ListNode(2)
    s1.next = s2
    s2.next = s3
    var s = reverseList1(s1)

    var nex = s
    while (nex != null) {
        println(nex.`val`)
        nex = nex.next
    }

}