package aoc2022.day11

import aoc2022.Puzzle

data class Monkey(
    val num: Int, val items: ArrayDeque<Int>, val operation: (Int) -> Int,
    val testDivisibleBy: Int, val ifTrueTarget: Int, val ifFalseTarget: Int,
) {
    var activity = 0
        private set

    fun evaluateAndThrowItemsTo(monkeys: List<Monkey>) {
//        num.andLog(": ")
//        items.andLog("; ")
        while (items.isNotEmpty()) {
            val inflatedWorry = operation(items.removeFirst())
//                .andLog(", ")
            val newWorry = inflatedWorry / 3
            val target =
                if (newWorry % testDivisibleBy == 0) ifTrueTarget
                else ifFalseTarget
            throwTo(monkeys[target/*.andLog(" gets ")*/], newWorry/*.andLog("; ")*/)
            activity++
        }
//        "activity = $activity".andLog("\n")
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
    ArrayDeque(
        substringAfter("Starting items: ")
            .substringBefore("Operation:")
            .split(",")
            .map(String::trim)
            .map(String::toInt)
    )


private fun String.extractInt(regex: Regex): Int {
    return extract(regex).toInt()
}

private fun String.extract(regex: Regex): String =
    regex.find(this)!!.destructured.component1().trim()

object Day11 : Puzzle(2022, 11) {
    override fun part1(input: List<String>): Any {
        val monkeys = input.joinToString("")
            .split("Monkey".toRegex()).drop(1)
//            .map { it.andLog() }
            .map { it.trim().toMonkey() }

        repeat(20) {round ->
//            (round + 1 ).andLog(":")
            monkeys.onEach { it.evaluateAndThrowItemsTo(monkeys) }
//            monkeys.onEach { "\t${it.num} has ${it.items}".andLog("\n\t") }
//            "\n".andLog()
        }

        return monkeys
            .sortedBy { it.activity }
            .takeLast(2)/*.andLog()*/ // should be the highest activity
            .fold(1) { product, monkey -> product * monkey.activity }
//            .andLog("\n")

    }

    override fun part2(input: List<String>): Any {
        TODO("Do part 1 first!")
    }
}

private fun <T> T.andLog(suffix: String = ""): T = this.also { print(it.toString() + suffix) }


fun main() {
    Day11.solve(10605, -1)
}


