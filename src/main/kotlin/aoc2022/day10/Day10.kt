package aoc2022.day10

import aoc2022.Puzzle
import aoc2022.utils.appliedTo
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

    override fun part2(input: List<String>): String {

        var x = 1
        var clock = 0 // also the centre of Sprite

        val crtBuffer = StringBuilder()

        input.forEach { instruction ->
            if (instruction == "noop") {
                clock++
                // do stuff
            } else {
                repeat(2) { _ ->
                    clock++
                    // do stuff
                }
                x += instruction.substringAfter(" ").toInt()
            }
        }

        return ""
    }

    fun solve() {

        val testInput = readTestInput()
        val input = readInput()

        // part 1
        Day10::part1.appliedTo(testInput, returns = 13140)
        println("Part 1: ${part1(input)}")

        // part 2
        Day10::part2.appliedTo(
            testInput, returns = """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....            
            """.trimIndent()
        )
        println("Part 2: ${part2(input)}")
    }
}

fun main() {
    Day10.solve()
}


