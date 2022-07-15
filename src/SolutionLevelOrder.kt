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
 * 解：使用队列来完成，由于队列先进先出的原因
 *    每次循环的时候都将左右叶子添加到队列中，即可保证有序
 * */
fun levelOrder(root: TreeNode?): List<List<Int>> {
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

fun main() {
    var tree1 = TreeNode(3)
    var tree2 = TreeNode(9)
    var tree3 = TreeNode(20)
    var tree4 = TreeNode(15)
    var tree5 = TreeNode(7)
    var tree6 = TreeNode(7)

    tree1.left = tree2
    tree1.right = tree3

    tree2.left = tree6

    tree3.left = tree4
    tree3.right = tree5

    val result = levelOrder(tree1)
    result.forEach {
        println(it.joinToString())
    }
}