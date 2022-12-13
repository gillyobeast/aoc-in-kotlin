package aoc2022.day13

import aoc2022.Puzzle
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode

object Day13 : Puzzle(2022, 13) {
    private val objectMapper = ObjectMapper()

    override fun part1(input: List<String>): Any {
        val pairs = input.joinToString("\n").split("\n\n")
            .map { lines -> lines.split("\n").map { it.parse() }.toPair() }


        return pairs
            .associateWith { pairs.indexOf(it) }
            .map { println("\n== Pair ${it.value+1} =="); it.value to it.key.isCorrectOrder() }
            .toMap()
            .filter { it.value }
            .keys
            .sum()

    }

    private fun Pair<JsonNode, JsonNode>.isCorrectOrder(): Boolean {
        val left = first.andLog("First: ")
        val right = second.andLog("Second: ")
        val debug = "\tCompare $first to $second: "

        if (left is IntNode && right is IntNode && left > right) {
            return false.andLog(debug)

        } else if (left is ArrayNode && right is ArrayNode) { // issue is somewhere between here...
            for (i in left.toList().indices) {
                if (!(left[i] to right[i]).isCorrectOrder()) return false.andLog(debug)
            }
            if (right.size() < left.size()) return false.andLog(debug) // ... and here.
        } else {

            if (left is IntNode && right is ArrayNode && !(arrayNode(left) to right).isCorrectOrder()
                || right is IntNode && left is ArrayNode && !(left to arrayNode(right)).isCorrectOrder()
            ) return false.andLog(debug)
        }

        return true.andLog(debug)
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

private fun <E> E.andLog(extra: Any = ""): E {
    return also { println(extra.toString() + this) }
}

private fun <E> List<E>.toPair(): Pair<E, E> {
    check(this.size == 2) { "Can only make a pair out of two values" }
    return this[0] to this[1]
}

fun main() {
    Day13.solve(13, -1)
}

