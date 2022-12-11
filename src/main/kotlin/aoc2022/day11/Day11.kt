package aoc2022.day11

import aoc2022.Puzzle

data class Monkey(
    val num: Int, val items: ArrayDeque<Long>, val operation: (Long) -> Long,
    val testDivisibleBy: Long, val ifTrueTarget: Int, val ifFalseTarget: Int,
) {
    var activity = 0L
        private set
    fun evaluateAndThrowItemsDividingBy3(monkeys: List<Monkey>) {
        while (items.isNotEmpty()) {
            val inflatedWorry = operation(items.removeFirst())
            val newWorry = inflatedWorry / 3L
            val target =
                if (newWorry.mod(testDivisibleBy) == 0L) ifTrueTarget
                else ifFalseTarget
            throwTo(monkeys[target], newWorry)
            activity++
        }
    }

    fun evaluateAndThrowItems(monkeys: List<Monkey>) {
        while (items.isNotEmpty()) {
            val inflatedWorry: Long = operation(items.removeFirst()) % monkeys.productOfTests
            check(inflatedWorry > 0L) { "$inflatedWorry should be positive" }

            val target =
                if (inflatedWorry % testDivisibleBy == 0L) ifTrueTarget
                else ifFalseTarget
            throwTo(monkeys[target], inflatedWorry)
            activity++
        }
    }

    private fun throwTo(other: Monkey, item: Long) {
        other.items.addLast(item)
    }

    private val List<Monkey>.productOfTests: Long
        get() = map(Monkey::testDivisibleBy).product()
}

fun String.toMonkey() = Monkey(
    num = substringBefore(":").toInt(),
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

private fun List<String>.toMonkeys(): List<Monkey> {
    return joinToString("").split("Monkey".toRegex()).drop(1).map { it.trim().toMonkey() }
}

object Day11 : Puzzle(2022, 11) {
    override fun part1(input: List<String>): Long {
        val monkeys = input.toMonkeys()

        repeat(20) { _ ->
            monkeys.onEach { it.evaluateAndThrowItemsDividingBy3(monkeys) }
        }

        return monkeys.calculateMonkeyBusiness()
    }

    override fun part2(input: List<String>): Long {

        val monkeys = input.toMonkeys()

        repeat(10_000) { _ ->
            monkeys.onEach { it.evaluateAndThrowItems(monkeys) }
        }

        return monkeys.calculateMonkeyBusiness()
    }
}

private fun List<Long>.product(): Long {
    return fold(1L) { product, it -> product * it }
}

fun main() {
    Day11.solve(10_605L, 2_713_310_158L)
}


