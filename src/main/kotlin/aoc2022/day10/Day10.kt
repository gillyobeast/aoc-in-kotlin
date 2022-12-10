package aoc2022.day10

import aoc2022.Puzzle
import aoc2022.utils.appliedTo
import aoc2022.utils.readInput
import aoc2022.utils.readTestInput

object Day10 : Puzzle(2022, 10) {
    override fun part1(input: List<String>): Int {
        return input.size
    }

    override fun part2(input: List<String>): Int {
        return input.size
    }
    fun solve() {

        val testInput = readTestInput()
        val input = readInput()

        // part 1
        Day10::part1.appliedTo(testInput, returns = 13140)
        println("Part 1: ${part1(input)}")

        // part 2
        Day10::part2.appliedTo(testInput, returns = -1)
        println("Part 2: ${part2(input)}")
    }
}

fun main() {
    Day10.solve()
}


