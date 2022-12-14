package aoc2022.day14

import aoc2022.Puzzle
import aoc2022.utils.Point
import aoc2022.utils.andLog
import aoc2022.utils.toPoint

object Day14 : Puzzle(2022, 14) {
    override fun part1(input: List<String>): Any {
        input.pairs()
            .andLog()
            .forEach { pairs ->

            }

        TODO("Not yet implemented")
    }

    private fun List<String>.pairs(): List<List<Point>> = map { line ->
        line.split(" -> ")
            .map { pair ->
                pair.split(",")
                    .map(String::toInt)
                    .toPoint()
            }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

}

fun main() {
    Day14.solve(24, -1)
}

