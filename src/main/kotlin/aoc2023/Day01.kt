package aoc2023

import Puzzle

object Day01 : Puzzle(2023, 1) {
    // The newly-improved calibration document consists of lines of text; each line originally
    //  contained a specific calibration value that the Elves now need to recover. On each line,
    //  the calibration value can be found by combining the first digit and the last digit
    //  (in that order) to form a single two-digit number.
    //  What is the sum of all of the calibration values?

    override fun part1(input: List<String>): Any {
        return getCalibrationValues(input) { it.extractDigits() }.sum()

    }

    private fun String.extractDigits() =
        filter { char -> char.isDigit() }.map { char -> char.toString() }

    override fun part2(input: List<String>): Any {
        return getCalibrationValues(input) { it.extractNumbers() }.sum()

    }

    private fun getCalibrationValues(input: List<String>, extractor: (String) -> List<String>) =
        input.map(extractor)
            .filter { it.isNotEmpty() }
            .map { it.first() + it.last() }
            .map(String::toInt)

    private val numbers = mapOf(
        "one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5,
        "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9,
        "1" to 1, "2" to 2, "3" to 3, "4" to 4, "5" to 5,
        "6" to 6, "7" to 7, "8" to 8, "9" to 9,
    )

    private fun String.extractNumbers(): List<String> {
        val foundNumbers = mutableListOf<Int>()

        for (idx in this.indices) {
            for ((number, value) in numbers) {
                if (this.indexOf(number, idx) == idx) {
                    foundNumbers += value
                }
            }
        }
        return foundNumbers.map { it.toString() }
    }

}

fun main() {
    Day01.solve(209, 281)
}
