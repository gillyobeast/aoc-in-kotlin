package aoc2022.day06

import aoc2022.utils.appliedTo
import aoc2022.utils.readInput
import aoc2022.utils.returns

object Day06 {

    private fun String.findIndexOfUniqueSubstring(ofLength: Int): Int {
        return windowedSequence(ofLength) { it.toSet().size == ofLength }
            .indexOfFirst { it /* == true */ } + ofLength
    }

    fun part1(input: String): Int {
        return input.findIndexOfUniqueSubstring(ofLength = 4)
    }

    fun part2(input: String): Int {
        return input.findIndexOfUniqueSubstring(ofLength = 14)
    }


}

fun main() {
    val input = readInput(2022, 6)

// part 1
    Day06::part1 appliedTo "mjqjpqmgbljsphdztnvjfqwrcgsmlb" returns 7
    Day06::part1 appliedTo "bvwbjplbgvbhsrlpgdmjqwftvncz" returns 5
    Day06::part1 appliedTo "nppdvjthqldpwncqszvftbrmjlhg" returns 6
    Day06::part1 appliedTo "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" returns 10
    Day06::part1 appliedTo "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" returns 11
    println("Part 1: ${Day06.part1(input.first())}")

// part 2
    Day06::part2 appliedTo "mjqjpqmgbljsphdztnvjfqwrcgsmlb" returns 19
    Day06::part2 appliedTo "bvwbjplbgvbhsrlpgdmjqwftvncz" returns 23
    Day06::part2 appliedTo "nppdvjthqldpwncqszvftbrmjlhg" returns 23
    Day06::part2 appliedTo "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" returns 29
    Day06::part2 appliedTo "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" returns 26
    println("Part 2: ${Day06.part2(input.first())}")
}

