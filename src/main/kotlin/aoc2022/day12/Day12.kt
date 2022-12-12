package aoc2022.day12

import aoc2022.Puzzle
import aoc2022.utils.Matrix

object Day12 : Puzzle(2022, 12) {
    /**
     * Strategy:
     *      - parse input
     *      - find a path (somehow)
     *      - tweak the path, check its length and keep if it's shorter
     *
     */
    override fun part1(input: List<String>): Any {
        val map: Matrix<Char> = input.map { it.toList() }


        TODO("Not yet implemented")
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

}

fun main() {
    Day12.solve(31, -1)
}

