package aoc2022.day10

import Puzzle
import aoc2022.utils.readInput
import aoc2022.utils.readTestInput

object Day10 : Puzzle(2022, 10) {
    override fun part1(input: List<String>): Int {

        var x = 1
        var clock = 0
        val checkpoints = setOf(20, 60, 100, 140, 180, 220)
        val signalStrengths = mutableListOf<Int>()

        input.forEach { instruction ->
            if (instruction == "noop") {
                clock++
                if (clock in checkpoints) {
                    signalStrengths.add(x * clock)
                }
            } else {
                repeat(2) { _ ->
                    clock++
                    if (clock in checkpoints) {
                        signalStrengths.add(x * clock)
                    }
                }
                x += instruction.substringAfter(" ").toInt()
            }
        }

        return signalStrengths.sum()
    }

    private fun StringBuilder.draw(clock: Int, x: Int) {
        if ((clock % 40) - x in 0..2) append("[]") else append("  ")
        if (clock % 40 == 0) append("|\n")
    }

    override fun part2(input: List<String>): String {

        var x = 1
        var clock = 0 // clock % 40 == centre of Sprite

        val crtBuffer = StringBuilder()

        input.forEach { instruction ->
            if (instruction == "noop") {
                clock++
                crtBuffer.draw(clock, x)
            } else {
                repeat(2) { _ ->
                    clock++
                    crtBuffer.draw(clock, x)
                }
                x += instruction.substringAfter(" ").toInt()
            }
        }

        return crtBuffer.toString()
    }


    override fun <T, S> solve(part1TestResult: T, part2TestResult: S) {

        val testInput = readTestInput()
        val input = readInput()

        // part 1
//        ::part1.appliedTo(testInput, returns = part1TestResult)
        println("Part 1: ${part1(input)}")

        // part 2
        val output = part2(testInput)
        check(output.trim() == part2TestResult) {
            "expected \n${part2TestResult} but was \n$output"
        }
        println("Part 2: \n${part2(input)}")
        // ZKJFBJFZ
    }
}

fun main() {
    Day10.solve(
        13140, """
[][]    [][]    [][]    [][]    [][]    [][]    [][]    [][]    [][]    [][]    |
[][][]      [][][]      [][][]      [][][]      [][][]      [][][]      [][][]  |
[][][][]        [][][][]        [][][][]        [][][][]        [][][][]        |
[][][][][]          [][][][][]          [][][][][]          [][][][][]          |
[][][][][][]            [][][][][][]            [][][][][][]            [][][]  |
[][][][][][][]              [][][][][][][]              [][][][][][][]          |
                    """.trimIndent().trim()
    )
}


