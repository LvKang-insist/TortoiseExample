import java.util.*

/**
 * @name Solution
 * @package
 * @author 345 QQ:1831712732
 * @time 2022/07/11 17:56
 * @description
 */

/**题：0 ~ n-1 中缺失的数字，这个序列从0开始并且是递增的，再范围内只有一个数字不在该数组中，求该数字：
 * 解：由于是从 0 开始，并且是递增的，所以 index 都会等于数字对应的 value
 *    如果等于，表示该数组在后面，如果不等于，就在前面。
 *    找到的最后一个即为该数字
 * */
fun missingNumber(nums: IntArray): Int {
    var start = 0
    var end = nums.size - 1

    while (start <= end) {
        val missing = (end - start) / 2 + start
        if (nums[missing] == missing) {
            start = missing + 1
        } else {
            end = missing - 1
        }
    }
    return start
}

fun findNumberIn2DArray2(matrix: Array<IntArray>, target: Int): Boolean {
    if (matrix.isEmpty()) return false
    if (matrix.size == 1 && matrix[0].isEmpty()) return false
    val hSize = matrix.size - 1
    var w = matrix[0].size - 1
    var h = 0
    while (w >= 0 && h <= hSize) {
        if (matrix[h][w] == target) return true
        if (matrix[h][w] > target) {
            w -= 1
        } else {
            h += 1
        }
    }
    return false
}

/**
 * 题：在一个从左到右，从上到下递增的二位数组中，判断一个数是否存在
 *  1，3，5，7
 *  2，5，6，10
 *  4，6，12，14
 *
 *  解1：采用二分查找法，从对角线的开始到结束位置的中间进行二分查找
 *      例如：第一次是0，那么就是 1-7进行二分查找，截止就是 1-4进行查找
 *           第二次是1，那即是查找  3-7，,3-6
 *           以此类推，找到直接返回 true，最差的情况就是找不到，返回 false
 *  解2：x 为当前行最后一个，y 为第一个， target 大于x 则 y ++，小于x，x -- 即可
 *
 * */
fun findNumberIn2DArray(matrix: Array<IntArray>, target: Int): Boolean {
    if (matrix.isEmpty()) return false
    if (matrix[0].isEmpty()) return false
    val x = matrix[0].size - 1
    val y = matrix.size - 1
    var curIndex = 0
    while (curIndex <= x && curIndex <= y) {
        if (target <= matrix[curIndex][x]) {
            if (findNumberIntXArray(matrix[curIndex], target, curIndex, x))
                return true
        }
        if (target <= matrix[y][curIndex]) {
            if (findNumberIntYArray(matrix, target, curIndex, y, curIndex))
                return true
        }
        curIndex++
    }
    return false
}

fun findNumberIntYArray(nums: Array<IntArray>, target: Int, start: Int, end: Int, y: Int): Boolean {
    var s = start
    var e = end
    while (s <= e) {
        val cur = (e - s) / 2 + s
        if (nums[cur][y] > target) {
            e = cur - 1
        } else if (nums[cur][y] < target) {
            s = cur + 1
        } else {
            return true
        }
    }
    return false
}

fun findNumberIntXArray(nums: IntArray, target: Int, start: Int, end: Int): Boolean {
    var s = start
    var e = end
    while (s <= e) {
        val cur = (e - s) / 2 + s
        if (nums[cur] > target) {
            e = cur - 1
        } else if (nums[cur] < target) {
            s = cur + 1
        } else {
            return true
        }
    }
    return false
}

/**
 * 题：旋转数组中的最小值，数组原本是有序递增的，然后进行旋转，求最小值
 *    例如 【3,4,5,1,2】 ,[3,3,3,1,2,3] ,[5,1,2,3,4]
 * 解：采用二分查找法，条件是 left < end
 *    如果最后一个值大于中间值，表示 m - end 之间肯定是有序的，所以 end = m
 *    如果最后一个值小于中间值，表示 m - end 之间肯定是无序的，所以 start = m+1
 *    如果相等，表示中间值和最后一个重复了，需要去重
 * */
fun minArray(numbers: IntArray): Int {
    var start = 0
    var end = numbers.size - 1
    while (start < end) {
        val cur = (end - start) / 2 + start
        val mid = numbers[cur]
        //如果最后一个大于中间的，表示后面的肯定有序
        if (numbers[end] > mid) {
            end = cur
            //小于，则表明是无序的
        } else if (numbers[end] < mid) {
            start = cur + 1
        } else {
            end-- //去重
        }
    }
    return numbers[start]
}

/**
 * 题：一个字符串s中，找到第一个只出现一次的字符，s 都是小写字母
 * 解1：采用 map 保存出现的次数即可
 * 解2：创建一个数组，用了保存字符出现的次数即可。可以通过字符的 code 来确定当前字符的index
 *     `字符`.code - 'a'.code 就是索引位置，因为 a 是第一个字母，code 值也是最小的
 * */
fun firstUniqChar(s: String): Char {
    val array = IntArray(26)
    for (i in s) {
        array[i.toInt() - 'a'.toInt()]++
    }
    for (i in s) {
        if (array[i.toInt() - 'a'.toInt()] == 1) {
            return i
        }
    }
    return ' '
}

/**
 * 题：搜索插入位置，再数组找到目标值，并返回索引，不存在返回该插入位置
 * 解：递归二分查找，最后结果只有两种，1，二分的开始和结束相等，2,开始+1 等于 结束
 *      然后计算即可。
 */
fun searchInsert(nums: IntArray, target: Int): Int {
    if (nums.isEmpty()) return 0
    if (nums.size == 1) {
        return if (target <= nums[0]) 0 else 1
    }
    return search(nums, target, 0, nums.size - 1)
}

fun search(nums: IntArray, target: Int, start: Int, end: Int): Int {
    if (start == end) {
        return if (target <= nums[start]) start else start + 1
    }
    if (start + 1 == end) {
        return if (nums[start] < target) {
            search(nums, target, start + 1, end)
        } else {
            start
        }
    }
    val z = (end - start) / 2 + start
    when {
        target < nums[z] -> return search(nums, target, start, z - 1)
        target > nums[z] -> return search(nums, target, z + 1, end)
        target == nums[z] -> return z
    }
    return z
}

fun search2(nums: IntArray, target: Int): Int {
    val sum = nums.size - 1
    var start = 0
    var end = sum
    while (start <= end) {
        val z = (end - start) / 2 + start
        when {
            nums[z] == target -> return z
            nums[z] > target -> end = z - 1
            nums[z] < target -> start = z + 1
        }
    }
    return start
}


/**
 * 题：有一个台阶，青蛙一次可以跳一个台阶，或者两个台阶，求共有多少种跳法
 * 解：设有f(n)种跳法，如果第一次跳一阶，剩下的台阶就是 n-1，跳法是 f(n-1)
 *    如果第一次跳了二阶，剩下的就是 n-2,跳法是 f(n-2)
 *    可得出总跳法为： f(n-1) + f(n-2) 。n=0||n=1 只有一种跳法，所以 n 从2开始
 */
fun numWays(n: Int): Int {
    if (n < 2) return 1
    var up1 = 1
    var up2 = 1
    var current: Int
    for (i in 2..n) {
        current = (up1 + up2) % 1000000007
        up1 = up2
        up2 = current
    }
    return up2
}

/**
 * 题：求 x 的平方根，结果只保留整数
 * 解：采用二分查找法，最后一个值如果不是平方根，说明结果不是整数，所以直接减一去整即可
 */
fun mySqrt(x: Int): Int {
    if (x == 0 || x == 1) return x
    var start = 0
    var end = x / 2
    while (start <= end) {
        //加一是为了防止 end -start == 0 的情况
        val cur = (end - start + 1) / 2 + start
        // cur * cur = x ，x / cur = cur
        val sqrt = x / cur
        if (sqrt < cur) {
            end = cur - 1
        } else if (sqrt > cur) {
            start = cur + 1
        } else {
            return cur
        }
    }
    return start - 1
}

/**
 * 题： 斐波那契数列：一个数字序列，每个数字等于前两个数字之和，例如 1,1,2,3,5,8。
 *     求：第 n 位的数应该是多少。
 * 解1：前两位的数然后相加就是当前的结果，最简单的方式就是进行递归。
 * 解2：对 2-n 进行遍历，再遍历的时候直接进行求和计算即可
 *      通过获取前两个位置的和来确定当前 n 的大小，重复滚动即可。
 */

fun fib(n: Int): Int {
//        return fibOne(n)
    return fibTwo(n)
}

fun fibOne(n: Int): Int {
    if (n < 2) return n
    return (fib(n - 1) + fib(n - 2)) % 1000000007
}

fun fibTwo(n: Int): Int {
    if (n < 2) return n
    var up1 = 0
    var up2 = 1
    var current = 0
    for (i in 2..n) {
        current = (up1 + up2) % 1000000007
        up1 = up2
        up2 = current
    }
    return current
}

/**
 * 题：顺时针打印矩阵
 * 解：对矩阵进行转圈遍历，使用四个变量来控制每个方向的位置，如果大于对应的边，说明已经结束
 *     时间复杂度：只遍历一次，所以是 0(mn)
 *     空间复杂度，没有额外的开销，0(1)
 * */
fun spiralOrder(matrix: Array<IntArray>): IntArray {
    if (matrix.isEmpty()) return intArrayOf()
    var l = 0
    var r = matrix[0].size - 1
    var t = 0
    var b = matrix.size - 1
    val res = IntArray((r + 1) * (b + 1))
    var resIndex = 0
    while (true) {
        //左到右
        for (i in l..r) res[resIndex++] = matrix[t][i]
        if (++t > b) break
        //上到下
        for (i in t..b) res[resIndex++] = matrix[i][r]
        if (--r < l) break
        //右到左
        for (i in r downTo l) res[resIndex++] = matrix[b][i]
        if (--b < t) break
        //下到上
        for (i in b downTo t) res[resIndex++] = matrix[i][l]
        if (++l > r) break
    }
    return res
}

/**
 * 题：栈的压入，和弹出，给定两个序列，第一个是栈压入顺序，第二个是推出序列，判断第二个是否为第一个的推出序列
 *
 * 解：使用栈来模拟压入和弹出操作。使用栈将 pushed 按顺序压入栈中，
 *    压入时判断是否和 popped 的首位相同，如果相同将元素pop，指针移向下一位，
 *    不相同，则继续压入，
 *    最后栈如果为空，说明弹出序列没有问题
 * */
fun validateStackSequences(pushed: IntArray, popped: IntArray): Boolean {
    val stack = Stack<Int>()
    var p = 0
    for (element in pushed){
        stack.push(element)
        while (stack.isNotEmpty() && stack.peek() == popped[p]){
            stack.pop()
            p++
        }
    }
    return stack.isEmpty()
}


/**
 * 题：调整数组顺序使得奇数再前，偶数在后
 * 解：定义 l,r，从左右两边开始遍历
 *    停止条件：l 位置不是奇数，r 的位置不是偶数，最后交换
 *
 *    时间复杂度，由于 l < r，l，r 共同遍历数组，所以 0(n)
 *    没有额外的开销 O(1)
 * */
fun exchange(nums: IntArray): IntArray {
    var l = 0
    var r = nums.size - 1
    while (l < r) {
        while (l < r && nums[l] % 2 != 0) l++
        while (r > l && nums[r] % 2 == 0) r--
        val temp = nums[l]
        nums[l] = nums[r]
        nums[r] = temp
    }
    return nums
}

/**
 * 题：链表中倒数第 k 哥结点
 * 解：快慢指针，快指针先走 k，然后两个指针同步走，走完之后，慢指针位置就是结果
 *    例如长度 10，k 等于 3。
 *    快指针走到 3，然后再一起走，慢指针最后的位置就是 7
 *
 *    时间复杂度：O(n)
 *    空间复杂度：O(1)
 *
 * */
fun getKthFromEnd(head: ListNode?, k: Int): ListNode? {
    var first = head
    var node = head
    var i = k

    while (first != null) {
        first = first.next
        if (i == 0) {
            node = node?.next
        } else {
            i--
        }
    }
    return node
}


fun exist(board: Array<CharArray>, word: String): Boolean {
    val h = board.size
    val w = board[0].size

    val visited = Array<BooleanArray>(h, init = { BooleanArray(w) })

    for (i in 0 until h) {
        for (j in 0 until w) {

        }
    }


    return false
}

fun checkExist(
    board: Array<CharArray>, visited: Array<BooleanArray>,
    i: Int, j: Int, s: String, k: Int
): Boolean {
    if (board[i][j] != s[k]) {
        return false;
    } else if (k == s.length - 1) {
        return true
    }

    visited[i][j] = true;


}


fun main() {
    println("--------")
}
