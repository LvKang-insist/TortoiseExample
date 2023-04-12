import kotlin.math.min

/**
 * @name Example
 * @package
 * @author 345 QQ:1831712732
 * @time 2023/04/12 16:34
 * @description
 */


/**
 * 重排字符形成目标字符串
 * 解：使用 hash 表保存 target 中每个字符出现的次数，接着保存 s 中 target 字符出现的次数
 *  s 中出现的次数 / target 出现的次数就是可以重排的次数，遍历去最小值即可
 *
 *  时间复杂度：0(m+n) ，需要遍历 s 和 target
 *  空间复杂度：0(m)，最多保存 target 中的字符
 */
fun rearrangeCharacters(s: String, target: String): Int {
    val sMap = HashMap<Char, Int>()
    val tMap = HashMap<Char, Int>()
    for (t in target) {
        tMap[t] = tMap.getOrDefault(t, 0) + 1
    }
    for (c in s) {
        if (tMap.containsKey(c)) {
            sMap[c] = sMap.getOrDefault(c, 0) + 1
        }
    }
    var count = Int.MAX_VALUE
    for (c in tMap.keys) {
        val tCount = tMap[c] ?: 0
        val sCount = sMap[c] ?: 0
        count = min(count, sCount / tCount)
        if (count == 0) {
            return 0
        }
    }
    return count
}


fun main() {
    println(rearrangeCharacters("abcba", "abc"))
}