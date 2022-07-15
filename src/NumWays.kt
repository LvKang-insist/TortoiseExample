/**
 * @name NumWays
 * @package
 * @author 345 QQ:1831712732
 * @time 2022/07/04 15:40
 * @description 题：有一个台阶，青蛙一次可以跳一个台阶，或者两个台阶，求共有多少种跳法
 * 解：设有f(n)种跳法，如果第一次跳一阶，剩下的台阶就是 n-1，跳法是 f(n-1)
 *    如果第一次跳了二阶，剩下的就是 n-2,跳法是 f(n-2)
 *    可得出总跳法为： f(n-1) + f(n-2) 。n=0||n=1 只有一种跳法，所以 n 从2开始
 */
class NumWays {

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

}