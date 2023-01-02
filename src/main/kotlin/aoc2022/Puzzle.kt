package aoc2022

import aoc2022.utils.appliedTo
import aoc2022.utils.readInput
import aoc2022.utils.readTestInput
import aoc2022.utils.returns
import kotlin.system.measureTimeMillis

abstract class Puzzle(private val year: Int, private val day: Int) {
    abstract fun part1(input: List<String>): Any
    abstract fun part2(input: List<String>): Any
    open fun <T, S> solve(part1TestResult: T, part2TestResult: S) {
        val testInput = readTestInput()
        val input = readInput()

        println("$year - day $day\n")
        // part 1
        ::part1 appliedTo testInput returns part1TestResult
        printTimeMillis {
            println("Part 1: ${part1(input)}")
        }

        // part 2
        ::part2 appliedTo testInput returns part2TestResult
        printTimeMillis {
            println("Part 2: ${part2(input)}")
        }

    }
}


private fun printTimeMillis(block: () -> Unit) {
    measureTimeMillis(block).apply { println("Took ${this}ms") }
}
