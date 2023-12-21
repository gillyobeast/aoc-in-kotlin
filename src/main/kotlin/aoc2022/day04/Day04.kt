package aoc2022.day04

import Puzzle

object Day04 : Puzzle(2022, 4) {


    private fun toPair(it: String, delimiter: String): Pair<String, String> {
        val halves = it.split(delimiter)
        return halves[0] to halves[1]
    }

    private fun Pair<String, String>.toRange(): IntRange = first.toInt()..second.toInt()

    private fun String.toRange(): IntRange = toPair(this, "-").toRange()


    private fun parsePairs(input: List<String>) = input
        .map { toPair(it, ",") }
        .map { it.first.toRange() to it.second.toRange() }
        .toList()

    private infix fun IntRange.fullyContains(other: IntRange) =
        this.all { it in other }

    private fun Pair<IntRange, IntRange>.overlapsFully(): Boolean {
        val firstInSecond = second fullyContains first
        val secondInFirst = first fullyContains second
        return (firstInSecond or secondInFirst)
    }

    private fun Pair<IntRange, IntRange>.overlaps(): Boolean =
        first.any { it in second }


    override fun part1(input: List<String>): Int {

        return parsePairs(input)
            .count { it.overlapsFully() }
    }

    override fun part2(input: List<String>): Int {
        return parsePairs(input)
            .count { it.overlaps() }
    }

}

fun main() = Day04.solve(2, 4)



