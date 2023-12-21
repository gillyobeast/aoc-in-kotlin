package aoc2022.day13

import Puzzle
import aoc2022.utils.andLog
import aoc2022.utils.shouldNotBe
import aoc2022.utils.toPoint
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode

object Day13 : Puzzle(2022, 13) {
    private val objectMapper = ObjectMapper()


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
        check((first.parse() to second.parse()).isCorrectOrder() == expected)
    }

    override fun part1(input: List<String>): Any {
        val pairs = input.joinToString("\n").split("\n\n")
            .map { lines -> lines.split("\n").map { it.parse() }.toPoint() }


        return pairs
            .associateWith { pairs.indexOf(it) + 1 }
            .map { println("\n== Pair ${it.value} =="); it.value to it.key.isCorrectOrder() }
            .toMap()
            .filter { it.value }
            .keys
            .sum() shouldNotBe 5589

    }

    private fun compareInts(left: IntNode, right: IntNode): Int =
        if (left.intValue() < right.intValue()) {
            // If the left integer is lower than the right integer, the inputs are in the right order.
            1
        } else if (left.intValue() > right.intValue()) {
            // If the left integer is higher than the right integer, the inputs are not in the right order.
            -1
        } else 0

    private fun Pair<JsonNode, JsonNode>.isCorrectOrder(): Boolean {
        val left = first.andLog("First: ")
        val right = second.andLog("Second: ")
        fun <T> T.andLog() = andLog("\tCompare $first to $second: ")
        // If both values are integers, the lower integer should come first.

        if (left is IntNode && right is IntNode) {
            val order = compareInts(left, right)
            if (order > 0) return true.andLog()
            if (order < 0) return false.andLog()
            // Otherwise, the inputs are the same integer; continue checking the next part of the input.
        }
        if (left is ArrayNode && right is ArrayNode) {
            // If both values are lists,
            for (index in left.toList().indices) {
                // If the right list runs out of items first, the inputs are not in the right order.
                if (right[index] == null) {
                    return false.andLog()
                }
                // compare the first value of each list, then the second value,and so on.
                if (left[index] != right[index])
                    return (left[index] to right[index]).isCorrectOrder().andLog()
                // If the left list runs out of items first, the inputs are in the right order.
                if (index == left.toList().lastIndex && right.toList().lastIndex > index) {
                    return true.andLog()
                }

            }
            // If the lists are the same length and no comparison makes a decision about the
            //      order, continue checking the next part of the input.
        }


//        If exactly one value is an integer, convert the integer to a list which contains that
//        integer as its only value, then retry the comparison.
        if (left is ArrayNode && right is IntNode) {
            return (left to arrayNode(right)).isCorrectOrder().andLog()
        }
        if (left is IntNode && right is ArrayNode) {
            return (arrayNode(left) to right).isCorrectOrder().andLog()
        }

        return true.andLog()
    }

    private fun arrayNode(left: JsonNode): JsonNode {
        val arrayNode = objectMapper.nodeFactory.arrayNode()
        arrayNode.add(left)
        return arrayNode
    }

    operator fun IntNode.compareTo(
        other: IntNode
    ): Int {
        return this.intValue().compareTo(other.intValue())
    }

    private fun String.parse(): JsonNode {
        return objectMapper.readTree(this)!!
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}


fun main() {
    Day13.solve(13, -1)
}

