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




fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
    var treeNode = TreeNode(preorder[0])
    var cur = TreeNode(preorder[0])


    return treeNode
}



fun main() {
    println("----")
}