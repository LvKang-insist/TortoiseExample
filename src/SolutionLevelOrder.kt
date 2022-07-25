import java.util.*
import kotlin.math.max

/**
 * @name SolutionLevelOrder
 * @package
 * @author 345 QQ:1831712732
 * @time 2022/07/13 21:16
 * @description
 */

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

/**
 * 题：从上到下打印二叉树
 * 解：1,使用队列来完成，由于队列先进先出的原因
 *    每次循环的时候都将左右叶子添加到队列中，即可保证有序
 *    2,可使用 map
 * */

fun levelOrder(root: TreeNode?): IntArray {
    val intArray = mutableListOf<Int>()
    if (root == null) return intArray.toIntArray()
    val linkList = LinkedList<TreeNode>()
    linkList.offer(root)
    while (linkList.isNotEmpty()) {
        val node = linkList.pop()
        intArray.add(node.`val`)
        node.left?.run {
            linkList.offer(this)
        }
        node.right?.run {
            linkList.offer(this)
        }
    }
    return intArray.toIntArray()
}

/**
 * 题：从上到下打印二叉树，结果按层级拜访
 * 解：使用二维的集合。对二叉树进行递归，
 *    递归的时候记录以当前层级的 pos 最为集合的 key 即可。
 */
fun levelOrder2(root: TreeNode?): List<List<Int>> {
    if (root == null) return listOf()
    val list = mutableListOf<MutableList<Int>>()
    queryTree(root, list, 0)
    return list
}

fun queryTree(root: TreeNode?, list: MutableList<MutableList<Int>>, pos: Int) {
    if (root == null) return
    if (pos > list.size - 1) {
        list.add(mutableListOf())
    }
    list[pos].add(root.`val`)
    queryTree(root.left, list, pos + 1)
    queryTree(root.right, list, pos + 1)
}

/**
 * 题：从上到下打印二叉数，结果按照 之 子排列
 * 解：对层级进行判断即可，奇数添加到尾部，偶数添加到头部即可
 * */
fun levelOrder3(root: TreeNode?): List<List<Int>> {
    if (root == null) return listOf()
    val list = mutableListOf<LinkedList<Int>>()
    queryTree3(root, list, 0)
    return list
}

fun queryTree3(root: TreeNode?, list: MutableList<LinkedList<Int>>, pos: Int) {
    if (root == null) return
    if (pos > list.size - 1) {
        list.add(LinkedList())
    }
    if (pos % 2 == 0) {
        list[pos].addLast(root.`val`)
    } else {
        list[pos].addFirst(root.`val`)
    }
    queryTree3(root.left, list, pos + 1)
    queryTree3(root.right, list, pos + 1)
}

/**
 * 题：树的子结构，输入两个二叉树A,B。判断 B 是否存在于 A 中
 * 解：遍历所有的根结点，和 B 进行比较，相同就继续遍历
 *    如果最后 B 结点为 null，说明全部符合，返回 true
 *    如果 a 为空，或者 不相同则当前根不符合条件，返回 false。
 * */
fun isSubStructure(a: TreeNode?, b: TreeNode?): Boolean {
    if (a == null || b == null) return false
    return isSub(a, b) || isSubStructure(a.left, b) || isSubStructure(a.right, b)
}

fun isSub(a: TreeNode?, b: TreeNode?): Boolean {
    if (b == null) return true
    if (a == null || a.`val` != b.`val`) return false
    return isSub(a.left, b.left) && isSub(a.right, b.right)
}

/**
 * 题：二叉搜索树的后序遍历序列，输入数组，判断是不是某二叉树的后序遍历结果
 * 二叉搜索树：又名二叉排序树
 *    1，左子树如果不为空，那么左子树所有值小于根结点的值
 *    2，右子树如果不为空，那么右子树所有值大于根结点的值
 *    3，他的左右子树分别都是二叉搜索树
 *    二叉搜索树的中序遍历结果是有序的
 *解1：递归+循环分解数据，组成对应的二叉树
 *    由搜索树的定义+后序遍历可知，数组的最后一个肯定是树的root结点
 *    接着划分左右结点，寻找到第一个大于 root 结点的位置 m
 *    [i,m-1]就是都是左子树，[m,j-1]都是右子树，遍历的时候 p++，记录次数
 *
 *    最后 p==j ，判断树的结构是否正确
 *
 * 解2：辅助栈
 *     根据定义可知，所有的左子节点必须小于根节点，右子节点必须大于子节点，
 *     将二叉树斜着看，从做上角往右下角看每条序列，就会发现他们都是递增的关系。
 *     所以，每一个结点都必须大于上一个结点，否则就是到了下一个序列，
 *          当前>上一个结点，就说明还是当前序列，使用栈保存值
 *          当前<上一个结点，需要切到下一个序列，循环栈，找到下一个序列的起点(当前必须小于栈顶，直至不满足为止)，
 *          循环完成之后的值就是 parent，
 *     到下一个序列后，如果值大于 parent 就说明他不满足条件(下一个序列肯定是左结点，必须小于 parent)，直接返回false，否则就继续上面的操作
 *     直到循环完成，返回true 即可。
 * */
fun verifyPostorder(postorder: IntArray): Boolean {
    if (postorder.isEmpty()) return true
    return recur(postorder, 0, postorder.size - 1)
}

fun verifyPostorder2(postorder: IntArray): Boolean {
    var parent = Int.MAX_VALUE
    val stack = Stack<Int>()
    for (i in postorder.size - 1 downTo 0) {
        val cur = postorder[i]
        if (cur > parent) return false
        while (stack.isNotEmpty() && stack.peek() > cur) {
            parent = stack.pop()
        }
        stack.push(cur)
    }
    return true
}

fun recur(postorder: IntArray, i: Int, j: Int): Boolean {
    if (i >= j) return true

    var p = i
    while (postorder[p] < postorder[j]) p++

    val m = p
    while (postorder[p] > postorder[j]) p++

    return p == j && recur(postorder, i, m - 1) && recur(postorder, m, j - 1)
}


/**
 * 题：二叉树中和为某一个值
 * 解1：递归前序遍历，每一次递归都使用新的list记录路径，并计算和，
 *      注意，每次都需要使用新的 list，否则递归记录的路径就会出错。
 *      还有一定要遍历到最后一个节点，否则无法确定最终是否为指定值的和。
 * 解2：递归前序遍历，记录当根结点到当前结点的路径，
 *      如果满足条件就进行保存（保存时使用新的集合进行保存，相当于 copy，如果直接保存 path，那么path改变后结果中的数据也会改变），
 *      如果不满足就继续遍历
 *      最后再回溯的时候将路径中的当前结点删除即可。
 *
 * */
var targets: Int = 0
var pathResult: MutableList<MutableList<Int>> = mutableListOf()

var linkPath = LinkedList<Int>()

fun pathSum(root: TreeNode?, target: Int): List<List<Int>> {
    if (root == null) return arrayListOf()
    targets = target
    query(root, mutableListOf(), 0)
    return pathResult
}

fun pathSum2(root: TreeNode?, target: Int): List<List<Int>> {
    query2(root,target)
    return pathResult
}


fun query2(root: TreeNode?, target: Int) {
    if (root == null) return
    linkPath.add(root.`val`)
    val temp = target - root.`val`
    if(root.left == null && root.right == null && temp == 0)
        pathResult.add(LinkedList(linkPath))
    query2(root.left,temp)
    query2(root.right,temp)
    linkPath.pollLast()
}


fun query(root: TreeNode, rootList: MutableList<Int>, sum: Int) {
    val newSum = sum + root.`val`
    val newList = ArrayList(rootList)
    newList.add(root.`val`)
    if (targets == newSum && root.left == null && root.right == null) {
        pathResult.add(newList)
    }
    if (root.left != null) {
        query(root.left!!, newList, newSum)
    }
    if (root.right != null) {
        query(root.right!!, newList, newSum)
    }
}

/**
 * 题：二叉树镜像
 * 解：递归，每次交换左右结点即可
 * */
fun mirrorTree(root: TreeNode?): TreeNode? {
    mirrorTree1(root)
    return root
}

fun mirrorTree1(root: TreeNode?) {
    if (root == null) return
    var temp = root.left
    root.left = root.right
    root.right = temp

    mirrorTree1(root.left)
    mirrorTree1(root.right)
}

/**
 * 题：叶子结点的路径之和
 * 解：递归遍历，每次上一次的值 * 10 +之前的即可。
 *    注意：如果是最后一个结点，直接返回计算结果即可
 *
 * */
fun sumNumbers(root: TreeNode?): Int {
    return querySumBers(root, 0)
}

fun querySumBers(node: TreeNode?, sum: Int): Int {
    if (node == null) return 0
    val temp = sum * 10 + node.`val`

    if (node.left == null && node.right == null)
        return temp
    val left = querySumBers(node.left, temp)
    val right = querySumBers(node.right, temp)
    return left + right
}


/**
 * 题：对称二叉树
 * 解：从中间切开，然后递归左右两边进行对比
 * */
fun isSymmetric(root: TreeNode?): Boolean {
    if (root == null) return true
    return checkSymmetric(root.left,root.right)
}

fun checkSymmetric(left: TreeNode?, right: TreeNode?): Boolean {
    if (left == null && right == null) return true
    if (left == null || right == null) return false
    if (left.`val` == right.`val`) {
        return checkSymmetric(left.left, right.right) && checkSymmetric(left.right, right.left)
    }
    return false
}

/**
 * 题：二叉树的最大深度
 * 解1：递归遍历，按层级即对应者深度，保留最大深度即可。
 * 解2：递归 DFS ，再递归到每一个层级退出的时候 +1，深度优先
 * */
fun maxDepth(root: TreeNode?): Int {
 return maxDepthQueue(root,0)
}

fun maxDepth2(root: TreeNode?):Int{
    if(root == null) return  0
    val l = maxDepth2(root.left)
    val r = maxDepth2(root.right)
    return max(l,r) +1
}

fun maxDepthQueue(root: TreeNode?,pos:Int): Int {
    if(root == null) return pos
    val l = maxDepthQueue(root.left,pos+1)
    val r = maxDepthQueue(root.right,pos +1)
    return max(l,r)
}


fun main() {
    var tree1 = TreeNode(1)
    var tree2 = TreeNode(2)
    var tree3 = TreeNode(3)
    var tree4 = TreeNode(4)
//    var tree5 = TreeNode(3)


    tree1.left = tree2
    tree1.right = tree3


    tree2.left = tree4
//    tree2.left = tree4

    println(maxDepth2(tree1))
}