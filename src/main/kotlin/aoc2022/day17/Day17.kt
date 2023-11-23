package aoc2022.day17

import aoc2022.Puzzle

object Day17 : Puzzle(2022, 0) {
    private val rocks = listOf("####", ".#.\n###\n.#.", "..#\n..#\n###", "#\n#\n#\n#", "##\n##")
    private const val NUMBER_OF_ROCKS = 2022

    override fun part1(input: List<String>): Any {
        for (rockNumber in 1..NUMBER_OF_ROCKS) {
            val rock = rocks(rockNumber)
            println(rock)
        }

        TODO("Not yet implemented")
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

    operator fun <T> List<T>.invoke(int: Int) = get(int % size)
}

fun main() = Day17.solve(3068, -1)


