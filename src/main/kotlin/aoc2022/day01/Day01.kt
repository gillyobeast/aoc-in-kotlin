package aoc2022.day01

import aoc2022.utils.appliedTo
import aoc2022.utils.readInput

fun main() {
    fun caloriesPerElf(input: List<String>): MutableList<Int> {
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

    fun maxTotalCalories(input: List<String>): Int {
        return caloriesPerElf(input)
            .max()
    }

    fun totalCaloriesForTop3Elves(input: List<String>): Int {
        return caloriesPerElf(input)
            .sortedDescending()
            .take(3)
//            .also(::println)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput()
    ::maxTotalCalories.appliedTo(testInput, returns = 24000)
    ::totalCaloriesForTop3Elves.appliedTo(testInput, returns = 45000)

    val input = readInput()
    println("Max total calories: ${maxTotalCalories(input)}")
    println("Top 3 elves' total: ${totalCaloriesForTop3Elves(input)}")
}

