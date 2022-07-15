/**
 * @name SearchInsert
 * @package
 * @author 345 QQ:1831712732
 * @time 2022/07/05 21:43
 * @description 题：搜索插入位置，再数组找到目标值，并返回索引，不存在返回该插入位置
 *   解：递归二分查找，最后结果只有两种，1，二分的开始和结束相等，2,开始+1 等于 结束
 *      然后计算即可。
 */
class SearchInsert {
    fun searchInsert(nums: IntArray, target: Int): Int {
        if (nums.isEmpty()) return 0
        if (nums.size == 1) {
            return if (target <= nums[0]) 0 else 1
        }
        return search(nums, target, 0, nums.size - 1)
    }

    private fun search(nums: IntArray, target: Int, start: Int, end: Int): Int {
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
}

fun main() {

    val test = SearchInsert()
    println(test.searchInsert(intArrayOf(3,5,7,9,10),8))

}