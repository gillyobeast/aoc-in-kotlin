package aoc2022.day11

import aoc2022.Puzzle

data class Monkey(
    val num: Int, val items: ArrayDeque<Int>, val operation: (Int) -> Int,
    val testDivisibleBy: Int, val ifTrueTarget: Int, val ifFalseTarget: Int,
) {
    var activity = 0
        private set

    fun evaluateAndThrowItemsTo(monkeys: List<Monkey>) {
        while (items.isNotEmpty()) {
            val target =
                if (operation(items[0]) % testDivisibleBy == 0) ifTrueTarget
                else ifFalseTarget
            throwTo(monkeys[target], items.removeFirst() / 3)
            activity++
        }
    }

    private fun throwTo(other: Monkey, item: Int) {
        other.catch(item)
    }

    private fun catch(item: Int) {
        items.addLast(item)
    }

}

fun String.toMonkey() =
    Monkey(
        num = parseNum(),
        items = parseItems(),
        operation = parseOperation(),
        testDivisibleBy = parseTestDivisibleBy(),
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


private fun String.parseTestDivisibleBy(): Int {
    return extractInt("Test: divisible by (\\d+)".toRegex())
}

fun String.parseOperation(): (Int) -> Int {
    val operation = extract("Operation: new = old ([*+]) (\\d+|old)".toRegex())
    val operand = extract("Operation: new = old [*+] (\\d+|old)".toRegex())

    return if (operand == "old") { it -> it * it }
    else if (operation == "*") { it -> it * operand.toInt() }
    else { it -> it + operand.toInt() }
}

private fun String.parseItems(): ArrayDeque<Int> =
    ArrayDeque(extract("Starting items: ((\\d+,?)\\s?)+".toRegex()).split(",").map(String::toInt))


private fun String.extractInt(regex: Regex): Int {
    return extract(regex).toInt()
}

private fun String.extract(regex: Regex): String =
    regex.find(this)!!.destructured.component1().trim()

object Day11 : Puzzle(2022, 11) {
    override fun part1(input: List<String>): Any {
        val monkeys = input.joinToString("")
            .split("Monkey".toRegex()).drop(1)
            .map { it.andLog() }
            .map { it.trim().toMonkey().andLog() }
            .sortedBy { it.activity };

        repeat(20) { monkeys.onEach { it.evaluateAndThrowItemsTo(monkeys) } }

        return monkeys
            .takeLast(2).andLog() // should be the highest activity
            .fold(1) { product, monkey -> product * monkey.activity }
            .andLog()

    }

    override fun part2(input: List<String>): Any {
        TODO("Do part 1 first!")
    }
}

private fun <T> T.andLog(): T = this.also { println(it) }


fun main() {
    Day11.solve(10605, -1)
}


