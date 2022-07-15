/**
 * @name MySqrt
 * @package
 * @author 345 QQ:1831712732
 * @time 2022/07/06 11:00
 * @description 题：求 x 的平方根，结果只保留整数
 *  解：采用二分查找法，最后一个值如果不是平方根，说明结果不是整数，所以直接减一去整即可
 */
class MySqrt {
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
}

fun main() {
    val sqrt = MySqrt()
    println(sqrt.mySqrt(12))
//    var s = 1073697799L
//    println(s*s)
//    println("2147395599")

}


