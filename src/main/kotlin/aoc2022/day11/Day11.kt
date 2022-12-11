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
    return extractInt("If false: throw to monkey (\\d+)".toRegex())
}

private fun String.parseIfTrue(): Int {
    return extractInt("If true: throw to monkey (\\d+)".toRegex())
}


private fun String.parseTest(): Int {
    return extractInt("Test: divisible by (\\d+)".toRegex())
}

fun String.parseOperation(): (Int) -> Int {
    val operation = extract("Operation: new = old ([*+]) (\\d+|old)".toRegex())
    val operand = extract("Operation: new = old [*+] (\\d+|old)".toRegex())

    return if (operand == "old") { it -> it * it }
    else if (operation == "*") { it -> it * operand.toInt() }
    else { it -> it + operand.toInt() }
}

private fun String.parseItems(): List<Int> =
    extract("Starting items: ((\\d+,?)\\s?)+".toRegex()).split(",").map(String::toInt)

private fun String.extractInt(regex: Regex): Int {
    return extract(regex).toInt()
}

private fun String.extract(regex: Regex): String =
    regex.find(this)!!.destructured.component1().trim()

object Day11 : Puzzle(2022, 11) {
    override fun part1(input: List<String>): Any {
        input.joinToString("")
            .split("Monkey".toRegex()).drop(1)
            .map { it.trim().toMonkey().andLog() }

        TODO("Return something, dummy!")
    }

    override fun part2(input: List<String>): Any {
        TODO("Do part 1 first!")
    }
}

private fun <T> T.andLog(): T = this.also { println(it) }


fun main() {
    Day11.solve(10605, -1)
}


