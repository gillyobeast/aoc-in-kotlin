package aoc2022.day11

import aoc2022.Puzzle

private val List<Monkey>.productOfTests: Long
    get() {
        return map(Monkey::testDivisibleBy).product()
    }

data class Monkey(
    val num: Int, val items: ArrayDeque<Long>, val operation: (Long) -> Long,
    val testDivisibleBy: Long, val ifTrueTarget: Int, val ifFalseTarget: Int,
) {
    var activity = 0L
        private set

    fun evaluateAndThrowItemsDividingBy3(monkeys: List<Monkey>) {
//        num.andLog(": ")
//        items.andLog("; ")
        while (items.isNotEmpty()) {
            val inflatedWorry = operation(items.removeFirst())
//                .andLog(", ")
            val newWorry = inflatedWorry / 3L
            val target =
                if (newWorry.mod(testDivisibleBy) == 0L) ifTrueTarget
                else ifFalseTarget
            throwTo(monkeys[target/*.andLog(" gets ")*/], newWorry/*.andLog("; ")*/)
            activity++
        }
//        "activity = $activity".andLog("\n")
    }

    fun evaluateAndThrowItems(monkeys: List<Monkey>) {
//        num.andLog(": ")
//        items.andLog("; ")
        while (items.isNotEmpty()) {
            val inflatedWorry: Long = operation(items.removeFirst()) % monkeys.productOfTests

//                .andLog(", ")

            check(inflatedWorry >= 0L) { "$inflatedWorry should be positive" }
            val target =
                if (inflatedWorry % testDivisibleBy == 0L) ifTrueTarget
                else ifFalseTarget
            throwTo(monkeys[target/*.andLog(" gets ")*/], inflatedWorry/*.andLog("; ")*/)
            activity++
        }
//        "activity = $activity".andLog("\n")
    }

    private fun throwTo(other: Monkey, item: Long) {
        other.catch(item)
    }

    private fun catch(item: Long) {
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


private fun String.parseTestDivisibleBy(): Long {
    return extract("Test: divisible by (\\d+)".toRegex()).toLong()
}

fun String.parseOperation(): (Long) -> Long {
    val operation = extract("Operation: new = old ([*+]) (\\d+|old)".toRegex())
    val operand = extract("Operation: new = old [*+] (\\d+|old)".toRegex())

    return if (operand == "old") { it -> it * it }
    else if (operation == "*") { it -> it * operand.toLong() }
    else { it -> it + operand.toInt() }
}

private fun String.parseItems(): ArrayDeque<Long> =
    ArrayDeque(
        substringAfter("Starting items: ")
            .substringBefore("Operation:")
            .split(",")
            .map(String::trim)
            .map(String::toLong)
    )


private fun String.extractInt(regex: Regex): Int {
    return extract(regex).toInt()
}

private fun String.extract(regex: Regex): String =
    regex.find(this)!!.destructured.component1().trim()

object Day11 : Puzzle(2022, 11) {
    override fun part1(input: List<String>): Long {
        val monkeys = input.toMonkeys()

        repeat(20) { _ ->
//            (round + 1 ).andLog(":")
            monkeys.onEach { it.evaluateAndThrowItemsDividingBy3(monkeys) }
//            monkeys.onEach { "\t${it.num} has ${it.items}".andLog("\n\t") }
//            "\n".andLog()
        }

        return monkeys.calculateMonkeyBusiness()
//            .andLog("\n")

    }

    override fun part2(input: List<String>): Long {

        val monkeys = input.toMonkeys()

        repeat(10_000) { _ ->
//            (round + 1 ).andLog(":")
            monkeys.onEach { it.evaluateAndThrowItems(monkeys) }
//            monkeys.onEach { "\t${it.num} has ${it.items}".andLog("\n\t") }
//            "\n".andLog()
        }

        return monkeys.calculateMonkeyBusiness()
//            .andLog("\n")
    }

    private fun List<Monkey>.calculateMonkeyBusiness(): Long = sortedBy { it.activity }
        .takeLast(2)/*.andLog()*/ // should be the highest activity
        .map(Monkey::activity)
        .product()

    private fun List<String>.toMonkeys(): List<Monkey> {
        return joinToString("")
            .split("Monkey".toRegex()).drop(1)
            //            .map { it.andLog() }
            .map { it.trim().toMonkey() }
    }
}

//private fun List<BigInteger>.product(): BigInteger {
//    return fold(BigInteger.ONE) { product, it -> product * it }
//}

private fun List<Long>.product(): Long {
    return fold(1L) { product, it -> product * it }
}

private fun <T> T.andLog(suffix: String = ""): T = this.also { print(it.toString() + suffix) }


fun main() {//                                    2637600014L
    Day11.solve(10605L, 2713310158L)
}


