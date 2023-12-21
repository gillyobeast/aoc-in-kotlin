package aoc2023

import Puzzle

object Day01 : Puzzle(2023, 1) {
    // The newly-improved calibration document consists of lines of text; each line originally
    //  contained a specific calibration value that the Elves now need to recover. On each line,
    //  the calibration value can be found by combining the first digit and the last digit
    //  (in that order) to form a single two-digit number.
    //  What is the sum of all of the calibration values?

    override fun part1(input: List<String>): Any {
        return input.map { it.extractDigits() }.filter { it.isNotEmpty() }.map {
            it.first() + it.last() // string concat
        }.sumOf { it.toInt() }

    }

    private fun String.extractDigits() =
        filter { char -> char.isDigit() }.map { char -> char.toString() }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

}

fun main() {
    Day01.solve(209, -1)
}
