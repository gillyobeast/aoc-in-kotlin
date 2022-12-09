package aoc2022.day04

import aoc2022.utils.appliedTo
import aoc2022.utils.readInput
import aoc2022.utils.readTestInput

fun main() {


    fun toPair(it: String, delimiter: String): Pair<String, String> {
        val halves = it.split(delimiter)
        return halves[0] to halves[1]
    }

    fun Pair<String, String>.toRange(): IntRange = first.toInt()..second.toInt()

    fun String.toRange(): IntRange = toPair(this, "-").toRange()


    fun parsePairs(input: List<String>) = input
        .map { toPair(it, ",") }
        .map { it.first.toRange() to it.second.toRange() }
        .toList()

    infix fun IntRange.fullyContains(other: IntRange) =
        this.all { it in other }

    fun Pair<IntRange, IntRange>.overlapsFully(): Boolean {
        val firstInSecond = second fullyContains first
        val secondInFirst = first fullyContains second
        return (firstInSecond or secondInFirst)
    }

    fun Pair<IntRange, IntRange>.overlaps(): Boolean =
        first.any { it in second }


    fun part1(input: List<String>): Int {

        return parsePairs(input)
            .count { it.overlapsFully() }
    }

    fun part2(input: List<String>): Int {
        return parsePairs(input)
            .count { it.overlaps() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput()
    val input = readInput()

    // part 1
    ::part1.appliedTo(testInput, returns = 2)
    println("Part 1: ${part1(input)}")

    // part 2
    ::part2.appliedTo(testInput, returns = 4)
    println("Part 2: ${part2(input)}")
}



