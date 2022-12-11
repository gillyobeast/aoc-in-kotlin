package aoc2022.day11

import aoc2022.Puzzle

data class Monkey(
    val num: Int, val items: List<Int>, val operation: (Int) -> Int,
    val ifTrue: Int, val ifFalse: Int,
)

object Day11 : Puzzle(2022, 11) {
    override fun part1(input: List<String>): Any {
        input.joinToString("")
            .split("Monkey \\d+:".toRegex()).drop(1)
            .map { it.trim().log() }

        TODO("Return something, dummy!")
    }

    override fun part2(input: List<String>): Any {
        TODO("Do part 1 first!")
    }
}

private fun <T> T.log(): T = this.also { println(it) }


fun main() {
    Day11.solve(10605, -1)
}


