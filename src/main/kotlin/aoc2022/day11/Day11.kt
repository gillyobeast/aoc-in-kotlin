package aoc2022.day11

import aoc2022.Puzzle
import kotlin.system.measureTimeMillis

class Monkey(
    val items: ArrayDeque<Long>, val operation: (Long) -> Long,
    val testDivisibleBy: Long, val ifTrueTarget: Int, val ifFalseTarget: Int,
) {
    var activity = 0L

    fun evaluateAndThrowTo(monkeys: List<Monkey>, adjust: (Long) -> Long) {
        while (items.isNotEmpty()) {
            val inflatedWorry = operation(items.removeFirst())
            val newWorry = adjust(inflatedWorry)
            val target =
                if (newWorry % testDivisibleBy == 0L) ifTrueTarget
                else ifFalseTarget
            monkeys[target].items.addLast(newWorry)
            activity++
        }
    }

    companion object {
        fun of(input: String) = Monkey(
            items = ArrayDeque(
                input.substringAfter("Starting items: ")
                    .substringBefore("Operation:")
                    .split(",")
                    .map(String::trim)
                    .map(String::toLong)
            ),
            operation = input.parseOperation(),
            testDivisibleBy = input.extract("Test: divisible by (\\d+)".toRegex()).toLong(),
            ifTrueTarget = input.extract("If true: throw to monkey (\\d+)".toRegex()).toInt(),
            ifFalseTarget = input.extract("If false: throw to monkey (\\d+)".toRegex()).toInt()
        )
    }
}

fun String.parseOperation(): (Long) -> Long {
    val operation = extract("Operation: new = old ([*+]) (\\d+|old)".toRegex())
    val operand = extract("Operation: new = old [*+] (\\d+|old)".toRegex())

    return if (operand == "old") { it -> it * it }
    else if (operation == "*") { it -> it * operand.toLong() }
    else { it -> it + operand.toInt() }
}


private fun String.extract(regex: Regex): String =
    regex.find(this)!!.destructured.component1().trim()

private fun List<Monkey>.calculateMonkeyBusiness(): Long =
    sortedBy { it.activity }
        .takeLast(2)// should be the highest activity
        .map(Monkey::activity).product()

private val List<Monkey>.productOfTests: Long
    get() = map(Monkey::testDivisibleBy).product()

private fun List<String>.toMonkeys(): List<Monkey> {
    return joinToString("").split("Monkey".toRegex()).drop(1).map { Monkey.of(it.trim()) }
}

object Day11 : Puzzle(2022, 11) {
    override fun part1(input: List<String>): Long {

        return calculateMonkeyBusiness(20, input.toMonkeys()) { worry -> worry / 3 }
    }

    override fun part2(input: List<String>): Long {

        val monkeys = input.toMonkeys()

        return calculateMonkeyBusiness(10_000, monkeys) { worry -> worry % monkeys.productOfTests }
    }

    private fun calculateMonkeyBusiness(
        repeats: Int,
        monkeys: List<Monkey>,
        adjust: (Long) -> Long
    ): Long {
        repeat(repeats) { _ ->
            monkeys.onEach { monkey ->
                monkey.evaluateAndThrowTo(monkeys, adjust)
            }
        }

        return monkeys.calculateMonkeyBusiness()
    }
}

private fun List<Long>.product(): Long {
    return fold(1L) { product, it -> product * it }
}

fun main() {
    measureTimeMillis {
        Day11.solve(10_605L, 2_713_310_158L)
    }.also { println("Took ${it}ms") }
}
