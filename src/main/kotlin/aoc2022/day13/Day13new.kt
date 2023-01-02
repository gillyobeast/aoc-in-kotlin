package aoc2022.day13

import aoc2022.Puzzle

sealed interface Node : Comparable<Node> {
//    fun close()
}

data class IntNode(val value: Int) : Node {
//    override fun close() {} // noop

    override fun compareTo(other: Node): Int =
        when (other) {
            // both ints - compare values
            is IntNode -> value compareTo other.value
            // first is int, second is array. wrap first in array and try again.
            is ArrayNode -> ArrayNode(mutableListOf(this)) compareTo other

        }

    override fun toString(): String {
        return value.toString()
    }
}

data class ArrayNode(val nodes: MutableList<Node> = mutableListOf()) : Node {

//    var closed: Boolean = false
//
//    override fun close() {
//        closed = true
//    }

    constructor(node: Node) : this(mutableListOf(node))

    override fun compareTo(other: Node): Int {
        return when (other) {
            // this is array, other is int. wrap other in array and try again.
            is IntNode -> this.compareTo(ArrayNode(other))
            // both arrays - compare the first value of each list, then the second value, and so on.
            // If the left list runs out of items first, the inputs are in the right order.
            // If the right list runs out of items first, the inputs are not in the right order.
            // If the lists are the same length and no comparison makes a decision about the order,
            //          continue checking the next part of the input.
            is ArrayNode -> {
                val left = this.nodes.iterator()
                val right = other.nodes.iterator()
                while (left.hasNext() && right.hasNext()) {
                    val comparison = left.next() compareTo right.next()
                    if (comparison != 0) return comparison
                }
                return left.hasNext() compareTo right.hasNext()
            }
        }
    }

    override fun toString(): String {
        return nodes.joinToString(",", prefix = "[", postfix = "]")
    }

    operator fun plusAssign(arrayNode: ArrayNode) {
        nodes += arrayNode
    }

}


@Suppress("DuplicatedCode")
object Day13new : Puzzle(2022, 13) {


    @JvmStatic
    fun main(args: Array<String>) {
        run {
            expectEquals("[]".parse(), ArrayNode())
        }
        run {
            expectEquals("[[]]".parse(), ArrayNode(ArrayNode()))
        }
        run {
            expectEquals("[[[]]]".parse(), ArrayNode(ArrayNode(ArrayNode())))
        }
        run {
            expectEquals("[-100]".parse(), ArrayNode((-100).i))
        }

        checkIsCorrectOrder("[1,1,3,1,1]", "[1,1,5,1,1]", true)
        checkIsCorrectOrder("[[1],[2,3,4]]", "[[1],4]", true)

        checkIsCorrectOrder("[9]", "[[8,7,6]]", false)
        checkIsCorrectOrder("[[4,4],4,4]", "[[4,4],4,4,4]", true)

        checkIsCorrectOrder("[7,7,7,7]", "[7,7,7]", false)
        checkIsCorrectOrder("[]", "[3]", true)

        checkIsCorrectOrder("[[[]]]", "[[]]", false)
        checkIsCorrectOrder("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]", false)

    }

    private val Int.i get() = IntNode(this)

    private fun expectEquals(parse: Node, expected: ArrayNode) {
        check(parse == expected) { "Expected $expected, was $parse" }
    }

    private fun checkIsCorrectOrder(first: String, second: String, expected: Boolean) {
        check((first.parse() to second.parse()).isCorrectOrder() == expected) {
            "Expected $first and $second to${if (expected) "" else " not"} be in order."
        }
    }

    override fun part1(input: List<String>): Any {

        val nodes = input.parseNodes()
        return nodes
            .associate { nodes.indexOf(it) to it.isCorrectOrder() }
            .entries
            .filter { it.value }
            .sumOf { it.key + 1 }
    }

    private fun Pair<Node, Node>.isCorrectOrder(): Boolean {

        return first <= second
    }

    private fun String.parse(): Node {
        val parser =
            DeepRecursiveFunction<IndexedValue<String>, IndexedValue<Node>> { (startIndex, string) ->
                if (string[startIndex] == '[') {
                    var index = startIndex + 1
                    val list = mutableListOf<Node>()
                    while (string[index] != ']') {
                        val (endIndex, entry) = callRecursive(string withIndex index)
                        list.add(entry)
                        index = if (string[endIndex] == ',') endIndex + 1 else endIndex
                    }
                    ArrayNode(list) withIndex index + 1
                } else {
                    var index = startIndex + 1
                    while (index < string.length && string[index] in '0'..'9') index++
                    IntNode(string.substring(startIndex, index).toInt()) withIndex index
                }

            }
        return parser.invoke(this withIndex 0).component2()
    }

    private infix fun <T> T.withIndex(i: Int) = IndexedValue(i, this)

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

    private fun List<String>.parseNodes(): List<Pair<Node, Node>> {

        return joinToString("\n")
            .split("\n\n")
            .map {
                val parts = it.split("\n")
                parts[0].parse() to parts[1].parse()
            }

    }
}


fun main() {
    Day13new.solve(13, -1)
}

