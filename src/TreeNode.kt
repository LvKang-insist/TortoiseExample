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
    return max(l, r) + 1
}

fun maxDepthQueue(root: TreeNode?, pos: Int): Int {
    if (root == null) return pos
    val l = maxDepthQueue(root.left, pos + 1)
    val r = maxDepthQueue(root.right, pos + 1)
    return max(l, r)
}

/**
 * 题：二叉树中序遍历
 * 解1：直接中序遍历保存结果即可，复杂度 O(n)
 * 解2：迭代，使用栈来完成这个过程，过程就是直接入栈，直到左叶子的底部，
 *     然后再循环遍历，复杂度 O(n)
 * */
fun inorderTraversal(root: TreeNode?): List<Int> {
    val list = mutableListOf<Int>()
    inorderTraversal(root, list)
    return list
}

fun inorderTraversal2(root: TreeNode?): List<Int> {
    val list = arrayListOf<Int>()
    val stack = LinkedList<TreeNode>()
    var node = root
    while (node != null || stack.isNotEmpty()) {
        while (node != null) {
            stack.push(node)
            node = node.left
        }
        node = stack.pop()
        list.add(node.`val`)
        node = node.right
    }
    return list
}

fun inorderTraversal(root: TreeNode?, list: MutableList<Int>) {
    if (root == null) return
    inorderTraversal(root.left, list)
    list.add(root.`val`)
    inorderTraversal(root.right, list)
}

/**
 * 题：二叉树的层序遍历，从叶子到根结点，从左到右
 * 解1：迭代，使用队列来完成
 * 解2：递归，递归过程中将结果反着放即可
 * */
fun levelOrderBottom(root: TreeNode?): List<List<Int>> {
    val list = arrayListOf<ArrayList<Int>>()
    if (root == null) return list
    val linkedList = LinkedList<TreeNode>()
    linkedList.offer(root)
    while (linkedList.isNotEmpty()) {
        val row = arrayListOf<Int>()
        val size = linkedList.size - 1
        for (i in 0..size) {
            val node = linkedList.pop()
            row.add(node.`val`)
            node.left?.run { linkedList.offer(this) }
            node.right?.run { linkedList.offer(this) }
        }
        list.add(0, row)
    }
    return list
}


fun levelOrderBottom2(root: TreeNode?): List<List<Int>> {
    val list = mutableListOf<MutableList<Int>>()
    levelOrderBottom21(root, list, 0)
    return list
}

fun levelOrderBottom21(root: TreeNode?, list: MutableList<MutableList<Int>>, pos: Int) {
    if (root == null) return
    if (list.size == pos) {
        list.add(0, mutableListOf())
    }
    list[list.size - pos - 1].add(root.`val`)
    levelOrderBottom21(root.left, list, pos + 1)
    levelOrderBottom21(root.right, list, pos + 1)
}

/**
 * 题：将有序(升序)数组转为 `高度平衡` 二叉搜索树
 *    高度平衡：每个节点的左右子树高度不能超过1
 * 解：采用递归，由于结果必须高度平衡，且是搜索树，
 *    所以，采用二分的思想，每次以数组的中间为根节点，分别对左右两边进行二分。
 *    左右两边的二分中间就是对应的左右叶子节点，然后以此类推即可
 */
fun sortedArrayToBST(nums: IntArray): TreeNode? {
    if (nums.isEmpty()) return null
    return sortedArrayToBSTQueue(nums, 0, nums.size - 1)
}


fun sortedArrayToBSTQueue(nums: IntArray, start: Int, end: Int): TreeNode? {
    if (start == end) return TreeNode(nums[start])
    if (start > end) return null
    val m = (end - start) / 2 + start
    val node = TreeNode(nums[m])
    node.left = sortedArrayToBSTQueue(nums, start, m - 1)
    node.right = sortedArrayToBSTQueue(nums, m + 1, end)
    return node
}

/**
 * 题：有序链表转换平衡二叉搜索树
 * 解：分割链表，递归遍历左右，取中间值作为根节点
 */
fun sortedListToBST(head: ListNode?): TreeNode? {
    var p = head//遍历
    var m = head//中间值
    var cur = head//中间值的上一个
    while (p?.next != null) {
        p = p.next?.next
        cur = m
        m = m?.next
    }
    if (m == null) return null
    if (m == p) return TreeNode(m.`val`)
    val node = TreeNode(m.`val`)
    //从中间分割
    val temp = m.next
    cur?.next = null

    node.left = sortedListToBST(head)
    node.right = sortedListToBST(temp)
    return node
}

/**
 *题：判断是否为平衡二叉树
 *   平衡二叉树：每个节点的左右子树高度不能超过 1
 *解1：自顶向下，判断每个节点左右子树的高度是否大于1即可
 *   从根结点开始递归获取左右高度，然后左右结点在以此递归获取高度计算即可。
 *   时间复杂度：由于每个节点位置都需要重新递归遍历，所以复杂度是 O(n²)
 *   空间复杂度：没有额外的开销，只收到 n 的影响，所以说是 O(n)
 *解2：自底向上，判断左节点高度和右结点的高度差
 *    递归遍历到最后一个左节点，然后就层层判断即可
 *    判断条件，左节点高度-右节点搞定 > 1
 *             大于1：表示高度不满足 返回 -1
 *             小于1：满足条件，拿到最大高度，继续递归比较即可
 *    时间复杂度：由于每个节点只遍历一次，所以是 O(n)
 *    空间复杂度，跟随二叉树大小而改变，所以说 O(n)
 */
fun isBalanced(root: TreeNode?): Boolean {
    if (root == null) return true
    val result = isBalancedMax(root.left) - isBalancedMax(root.right)
    return (result <= 1 && result >= -1) && isBalanced(root.left) && isBalanced(root.right)
}

fun isBalancedMax(root: TreeNode?): Int {
    if (root == null) return 0
    val l = isBalancedMax(root.left)
    val r = isBalancedMax(root.right)
    return kotlin.math.max(l, r) + 1
}


fun isBalanced2(root: TreeNode?): Boolean {
    return isBalanced2Height(root) >= 0
}

fun isBalanced2Height(root: TreeNode?): Int {
    if (root == null) return 0
    //左子树的深度
    val l = isBalanced2Height(root.left)
    //不满足直接退出，不用遍历右结点了
    if (l == -1) return -1
    //右子树的深度
    val r = isBalanced2Height(root.right)
    //不满足直接退出
    if (r == -1) return -1
    //如果 = -1 或者左右深度大于 1，表示不满足条件了
    if (kotlin.math.abs(l - r) > 1) {
        return -1
    }
    return kotlin.math.max(l, r) + 1
}

/**
 * 题：二叉树的最小深度
 * 解：bfs，深度遍历， 获取最小的高度即可
 *    需要注意的：如果没有叶子节点，那么就不计算他的高度
 */
fun minDepth(root: TreeNode?): Int {
    if (root == null) return 0
    var l = minDepth(root.left)
    var r = minDepth(root.right)
    if (l == 0) l = r
    if (r == 0) r = l
    return kotlin.math.min(l, r) + 1
}

/**
 * 题：二叉树展开为链表,r指向下一个节点，展开后应该是二叉树的前序遍历结果
 * 解：判断，左节点不为 null，将左节点插入到根节点与右节点之间即可
 * 时间复杂度：每个节点都得遍历一次，所以 0(n)
 * 空间复杂度：O(n)
 */
fun flatten(root: TreeNode?): Unit {
    if (root == null) return
    if (root.left != null) {
        val temp = root.right
        root.right = root.left
        flattenQueue(root.left).right = temp
        root.left = null
    }
    flatten(root.right)
}

fun flattenQueue(root: TreeNode?): TreeNode {
    var node = root
    while (node?.right != null) {
        node = node.right
    }
    return node!!
}

/**
 * 前序遍历，中 -> 左 -> 右
 * 时间复杂度：O(n)
 * 空间复杂度: O(n)
 * */
fun preorderTraversal(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    val stack = Stack<TreeNode>()
    val list = mutableListOf<Int>()
    var node: TreeNode? = root

    while (stack.isNotEmpty() || node != null) {
        while (node != null) {
            stack.push(node)
            list.add(node.`val`)
            node = node.left
        }
        val l = stack.pop()
        node = l.right
    }
    return list
}

/**
 * 后序遍历：左 -> 右 -> 中
 * 过程，从根开始，遍历到最后一个左子节点，整个过程全部入栈
 * 然后出栈，如果有右结点，就保存左子节点，然后重复遍历右节点的左节点，如此循环
 * pre 的作用是为了标记上一个保存的结点，用来判断右节点是否已经保存过
 * */
fun postorderTraversal(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    val stack = Stack<TreeNode>()
    val list = mutableListOf<Int>()
    var node: TreeNode? = root
    var pre: TreeNode? = root
    while (stack.isNotEmpty() || node != null) {
        while (node != null) {
            stack.push(node)
            node = node.left
        }
        node = stack.pop()
        if (node.right == null || node.right == pre) {
            list.add(node.`val`)
            pre = node
            node = null
        } else {
            stack.push(node)
            node = node.right
        }
    }
    return list
}

/**
 * 中序遍历 ， 左 —> 中 —> 右
 * */
fun middleTree(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    val stack = Stack<TreeNode>()
    val list = mutableListOf<Int>()
    var node: TreeNode? = root

    while (stack.isNotEmpty() || node != null) {
        while (node != null) {
            stack.push(node)
            node = node.left
        }
        val l = stack.pop()
        list.add(l.`val`)
        node = l.right
    }
    return list
}

/**
 * 题： 二叉树的右视图，保存二叉树右侧从顶部到底部的顺序
 * 解：按层级遍历二叉树，保留每一层的最后一个即可
 */
fun rightSideView(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    val list = arrayListOf<Int>()
    val linkedList = LinkedList<TreeNode?>()
    linkedList.offer(root)

    while (linkedList.isNotEmpty()) {
        linkedList.peekLast()?.run {
            list.add(this.`val`)
        }
        var size = linkedList.size - 1
        while (size-- >= 0) {
            val node = linkedList.pop()
            node?.left?.run { linkedList.offer(this) }
            node?.right?.run { linkedList.offer(this) }
        }
    }
    return list
}


/**
 * 题：二叉树的所有路径
 * 解：递归+队列
 *    队列用来记录路径，结点不为空就加入队列
 *    前序遍历，左右子节点都为null则保存路径
 *    最后进行回溯，删除最近一次的结点
 * */
var binaryTreePathList = arrayListOf<List<String>>()
var list = LinkedList<String>()
fun binaryTreePaths(root: TreeNode?): List<String> {
    binaryTreePathsQueue(root)
    val result = mutableListOf<String>()
    binaryTreePathList.forEach {
        val str = StringBuffer()
        it.forEach { s -> str.append(s) }
        result.add(str.toString().removeRange(0, 2))
    }
    return result
}

fun binaryTreePathsQueue(root: TreeNode?) {
    if (root == null) return
    list.offer("->${root.`val`}")
    binaryTreePathsQueue(root.left)
    if (root.left == null && root.right == null) {
        binaryTreePathList.add(ArrayList(list))
    }
    binaryTreePathsQueue(root.right)
    list.removeLast()
}

/**
 * 题：完全二叉树的节点个数
 * 解1：递归，挨个遍历，
 *      时间复杂度0(n)
 *      空间复杂度O(n)
 */
fun countNodes(root: TreeNode?): Int {
    if (root == null) return 0
    return countNodes(root.left) + countNodes(root.right) + 1
}


fun main() {
    val tree1 = TreeNode(1)
    val tree2 = TreeNode(2)
    val tree3 = TreeNode(3)
    val tree4 = TreeNode(4)
    val tree5 = TreeNode(5)



    tree1.right = tree2
    tree2.right = tree3

    tree3.left = tree4
    tree3.right = tree5

    var l1 = ListNode(0)
    var l2 = ListNode(1)
    var l3 = ListNode(2)
    var l4 = ListNode(3)
    var l5 = ListNode(4)
    var l6 = ListNode(5)

    l1.next = l2
    l2.next = l3
    l3.next = l4
    l4.next = l5
    l5.next = l6


    println(binaryTreePaths(tree1).joinToString())
}