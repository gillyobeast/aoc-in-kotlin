package aoc2022.day01

import Puzzle

object Day01 : Puzzle(2022, 1) {
    override fun part1(input: List<String>): Any {
        return caloriesPerElf(input)
            .max()
    }

    override fun part2(input: List<String>): Any {
        return caloriesPerElf(input)
            .sortedDescending()
            .take(3)
            .sum()
    }

    private fun caloriesPerElf(input: List<String>): MutableList<Int> {
        val elvesCalories = mutableListOf<Int>()
        var total = 0
        input.forEach {
            if (it.isBlank()) {
                elvesCalories.add(total)
                total = 0
            } else {
                total += it.toInt()
            }
        }
        elvesCalories.add(total)
        return elvesCalories
    }
}

fun main() {
    Day01.solve(24000, 45000)
}

