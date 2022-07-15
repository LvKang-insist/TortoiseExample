/**
 * @name Solution
 * @package
 * @author 345 QQ:1831712732
 * @time 2022/07/04 14:41
 * @description 斐波那契数列：一个数字序列，每个数字等于前两个数字之和，例如 1,1,2,3,5,8。
 * 求：第 n 位的数应该是多少。
 * 解1：前两位的数然后相加就是当前的结果，最简单的方式就是进行递归。
 * 解2：对 2-n 进行遍历，再遍历的时候直接进行求和计算即可
 *      通过获取前两个位置的和来确定当前 n 的大小，重复滚动即可。
 */
class Fib {
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
}