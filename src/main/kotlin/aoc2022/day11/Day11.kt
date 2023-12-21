package aoc2022.day11

import Puzzle

object Day11 : Puzzle(2022, 11) {
    data class Monkey(
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
            fun of(input: String) = with(input) {
                Monkey(
                    items = ArrayDeque(
                        substringAfter("Starting items: ")
                            .substringBefore("Operation:")
                            .split(",")
                            .map(String::trim)
                            .map(String::toLong)
                    ),
                    operation = parseOperation(),
                    testDivisibleBy = extract("Test: divisible by (\\d+)".toRegex()).toLong(),
                    ifTrueTarget = extract("If true: throw to monkey (\\d+)".toRegex()).toInt(),
                    ifFalseTarget = extract("If false: throw to monkey (\\d+)".toRegex()).toInt()
                )
            }
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

    private fun List<String>.toMonkeys(): List<Monkey> =
        joinToString("")
            .split("Monkey".toRegex())
            .drop(1)
            .map { Monkey.of(it.trim()) }


    override fun part1(input: List<String>): Long =
        input.toMonkeys().calculateMonkeyBusiness(20) { it / 3 }


    override fun part2(input: List<String>): Long {

        val monkeys = input.toMonkeys()

        return monkeys.calculateMonkeyBusiness(10_000) { it % monkeys.productOfTests }
    }

    private fun List<Monkey>.calculateMonkeyBusiness(
        rounds: Int,
        adjust: (Long) -> Long
    ): Long {
        repeat(rounds) { _ ->
            this.onEach { monkey ->
                monkey.evaluateAndThrowTo(this, adjust)
            }
        }

        return calculateMonkeyBusiness()
    }
}

private fun List<Long>.product(): Long {
    return fold(1L) { product, it -> product * it }
}

fun main() {
    Day11.solve(10_605L, 2_713_310_158L)
}
