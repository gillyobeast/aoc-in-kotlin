package aoc2022.day06

import aoc2022.utils.appliedTo
import aoc2022.utils.readInput

fun main() {

    fun String.findIndexOfUniqueSubstring(ofLength: Int): Int {
        return windowedSequence(ofLength) { it.toSet().size == ofLength }
            .indexOfFirst { it /* == true */ } + ofLength
    }

    fun part1(input: String): Int {
        return input.findIndexOfUniqueSubstring(ofLength = 4)
    }

    fun part2(input: String): Int {
        return input.findIndexOfUniqueSubstring(ofLength = 14)
    }

// test if implementation meets criteria from the description, like:
//    val testInput = aoc2022.utils.readInput("input_test")
    val input = readInput()

// part 1
    ::part1.appliedTo("mjqjpqmgbljsphdztnvjfqwrcgsmlb", returns = 7)
    ::part1.appliedTo("bvwbjplbgvbhsrlpgdmjqwftvncz", returns = 5)
    ::part1.appliedTo("nppdvjthqldpwncqszvftbrmjlhg", returns = 6)
    ::part1.appliedTo("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", returns = 10)
    ::part1.appliedTo("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", returns = 11)
    println("Part 1: ${part1(input.first())}")

// part 2
    ::part2.appliedTo("mjqjpqmgbljsphdztnvjfqwrcgsmlb", returns = 19)
    ::part2.appliedTo("bvwbjplbgvbhsrlpgdmjqwftvncz", returns = 23)
    ::part2.appliedTo("nppdvjthqldpwncqszvftbrmjlhg", returns = 23)
    ::part2.appliedTo("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", returns = 29)
    ::part2.appliedTo("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", returns = 26)
    println("Part 2: ${part2(input.first())}")
}

