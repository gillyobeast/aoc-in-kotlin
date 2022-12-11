package aoc2022.day11

import aoc2022.Puzzle

data class Monkey(
    val num: Int, val items: List<Int>, val operation: (Int) -> Int,
    val testDivisibleBy: Int, val ifTrueTarget: Int, val ifFalseTarget: Int,
)

fun String.toMonkey() =
    Monkey(
        num = parseNum(),
        items = parseItems(),
        operation = parseOperation(),
        testDivisibleBy = parseTest(),
        ifTrueTarget = parseIfTrue(),
        ifFalseTarget = parseIfFalse()
    )

private fun String.parseNum() = substringBefore(":").toInt()

private fun String.parseIfFalse(): Int {
    TODO("Not yet implemented")

}

private fun String.parseIfTrue(): Int {
    TODO("Not yet implemented")

}

private fun String.parseTest(): Int {
    TODO("Not yet implemented")

}

fun String.parseOperation(): (Int) -> Int {
    TODO("Not yet implemented")
}

private fun String.parseItems() =
    "Starting items: ((\\d+,) )+".toRegex().find(this)!!.value.split(",").map(String::toInt)

object Day11 : Puzzle(2022, 11) {
    override fun part1(input: List<String>): Any {
        input.joinToString("")
            .split("Monkey".toRegex()).drop(1)
            .map { it.trim().log() }

        TODO("Return something, dummy!")
    }

    override fun part2(input: List<String>): Any {
        TODO("Do part 1 first!")
    }
}

private fun <T> T.log(): T = this.also { println(it) }


fun main() {
    Day11.solve(10605, -1)
}


