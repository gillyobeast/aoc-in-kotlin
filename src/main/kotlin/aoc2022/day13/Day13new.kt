package aoc2022.day13

import aoc2022.Puzzle
import aoc2022.utils.andLog

sealed interface Node : Comparable<Node>
class IntNode(val value: Int) : Node {

    override fun compareTo(other: Node): Int =
        when (other) {
            // both ints - compare values
            is IntNode -> value.compareTo(other.value)
            // first is int, second is array. wrap first in array and try again.
            is ArrayNode -> ArrayNode(listOf(this)).compareTo(other)

        }
}

class ArrayNode(var nodes: List<Node> = listOf()) : Node {
    override fun compareTo(other: Node): Int =
        when (other) {
            // this is array, other is int. wrap other in array and try again.
            is IntNode -> this.compareTo(ArrayNode(listOf(other)))
            // both arrays - compare the first value of each list, then the second value, and so on.
            // If the left list runs out of items first, the inputs are in the right order.
            // If the right list runs out of items first, the inputs are not in the right order.
            // If the lists are the same length and no comparison makes a decision about the order,
            //          continue checking the next part of the input.
            is ArrayNode -> {
                nodes.forEach { }
                TODO()
            }
        }

    operator fun plus(node: Node) {
        nodes += node
    }

}


object Day13new : Puzzle(2022, 13) {


    @JvmStatic
    fun main(args: Array<String>) {
        checkIsCorrectOrder("[1,1,3,1,1]", "[1,1,5,1,1]", true)
        checkIsCorrectOrder("[[1],[2,3,4]]", "[[1],4]", true)

        checkIsCorrectOrder("[9]", "[[8,7,6]]", false)
        checkIsCorrectOrder("[[4,4],4,4]", "[[4,4],4,4,4]", true)

        checkIsCorrectOrder("[7,7,7,7]", "[7,7,7]", false)
        checkIsCorrectOrder("[]", "[3]", true)

        checkIsCorrectOrder("[[[]]]", "[[]]]", false)
        checkIsCorrectOrder("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]", false)

    }

    private fun checkIsCorrectOrder(first: String, second: String, expected: Boolean) {
        check((first.parse() to second.parse()).isCorrectOrder() == expected) {
            "Expected $first and $second to${if (expected) "" else " not"} be in order."
        }
    }

    override fun part1(input: List<String>): Any {


        return 0
    }

    private fun Pair<Node, Node>.isCorrectOrder(): Boolean {
        this.andLog()
        return false
    }

    private fun String.parse(): Node {
        val array = ArrayNode()
        val stack = mutableListOf(array)
        map { it: Char ->
            when (it) {
                '[' -> {
                    val newArray = ArrayNode()
                    array + newArray
                    stack[0] = newArray
                }

                ']' -> stack.removeFirst()
                else -> {
                    stack[0] + IntNode(it.digitToInt())
                }
            }
        }
        return array
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}


fun main() {
    Day13new.solve(13, -1)
}

