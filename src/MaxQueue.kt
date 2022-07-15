import java.util.*
import kotlin.math.max

/**
 * @name MaxQueue
 * @package
 * @author 345 QQ:1831712732
 * @time 2022/07/09 19:43
 * @description 队列中的最大值
 * 解：采用双端队列来完成，用一个单独的队列来维护最大值，
 *     一个重要的性质就是，如果插入的比之前小，就不会有任何问题，所以，
 *     每次插入到队列之后，需要去和前面插入的进行一个比较，大于就删除再添加，否则直接添加
 *     但是相较于栈来实现，没有这个问题，因为先进先出，每次只需要和栈顶的比较即可，
 */
class MaxQueue {

    private var maxQueue = LinkedList<Int>()
    private var curQueue = LinkedList<Int>()


    fun max_value(): Int {
        if (maxQueue.isEmpty()) return -1
        return maxQueue.peekFirst()
    }

    fun push_back(value: Int) {
        curQueue.offer(value)
        while (maxQueue.isNotEmpty() && value > maxQueue.peekLast()){
                maxQueue.pollLast()
        }
        maxQueue.offerLast(value)
    }

    fun pop_front(): Int {
        if (curQueue.size == 0) return -1
        val res = curQueue.pop()
        if(maxQueue.peek() == res)
            maxQueue.pop()
        return res
    }

}

fun main() {
    val maxQueue = MaxQueue()

    println(maxQueue.max_value())
    println(maxQueue.pop_front())
    println(maxQueue.pop_front())
    maxQueue.push_back(16)
    maxQueue.push_back(89)
    maxQueue.push_back(94)
    maxQueue.push_back(95)
    println(maxQueue.pop_front())
    maxQueue.push_back(22)
    println(maxQueue.pop_front())

    println(maxQueue.max_value())

}