import java.util.*

/**
 * @name Demo
 * @package
 * @author 345 QQ:1831712732
 * @time 2022/07/03 16:35
 * @description 思路：栈：先进后出，队列：先进先出。
 *              写入的时候第一个栈正常压入，读取时将第一个栈元素出栈，压入到第二栈中。就能完成先进先出。
 *              注意读取时需要等到第二个栈为空时重新压入
 */

class CQueue() {
    private val stackOne = Stack<Int>()
    private val stackTwo = Stack<Int>()

    fun appendTail(value: Int) {
        stackOne.push(value)
    }

    fun deleteHead(): Int {
        if (stackTwo.isNotEmpty()) {
            return stackTwo.pop()
        } else if (stackOne.isNotEmpty()) {
            while(stackOne.isNotEmpty()) {
                stackTwo.push(stackOne.pop())
            }
            return stackTwo.pop()
        }
        return -1
    }
}
