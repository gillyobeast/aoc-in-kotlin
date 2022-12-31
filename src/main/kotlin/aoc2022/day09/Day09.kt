package aoc2022.day09

import aoc2022.utils.readInput
import aoc2022.utils.readTestInput
import kotlin.math.abs
import kotlin.math.sign

enum class Move(
    val regex: Regex,
    val applyTo: (Pair<Int, Int>) -> Pair<Int, Int>
) {
    UP("U \\d+".toRegex(), { it.first to it.second + 1 }),
    DOWN("D \\d+".toRegex(), { it.first to it.second - 1 }),
    LEFT("L \\d+".toRegex(), { it.first - 1 to it.second }),
    RIGHT("R \\d+".toRegex(), { it.first + 1 to it.second });

    companion object {
        private val digits = "(\\d+)".toRegex()
        fun of(string: String): Pair<Move, Int> {
            val direction = Move.values().first { it.regex.matches(string) }
            return direction to digits.find(string)!!.value.toInt()
        }
    }
}

fun part1(input: List<String>): Int {

    val moves = input.map { Move.of(it) }

//    println(moves)

    var headPosition = 0 to 0 // x to y
    var tailPosition = 0 to 0 // x to y
    val tailVisitedPositions = mutableSetOf(tailPosition)

    moves.forEach { (move, step): Pair<Move, Int> ->
        repeat(step) { _ ->
            headPosition = move.applyTo(headPosition)
//            print("head = $headPosition ")

            tailPosition = tailPosition.moveToward(headPosition)

//            println("tail = $tailPosition")

            tailVisitedPositions.add(tailPosition)
        }
    }
//    println(tailVisitedPositions)

    return tailVisitedPositions.size
}


fun part2(input: List<String>): Int {

    val moves = input.map { Move.of(it) }

//    println(moves)

// head, 1, 2, 3, 4, 5, 6, 7, 8, 9
    val knots = mutableListOf<Pair<Int, Int>>()

    repeat(10) { knots.add(0 to 0) }
    val tailVisitedPositions = mutableSetOf(knots[9])

    moves.forEach { (move, step): Pair<Move, Int> ->
        repeat(step) { _ ->

            knots[0] = move.applyTo(knots[0])
//            print("head = ${knots[0]}, ")
            for (i in 1..9) {
                knots[i] = knots[i].moveToward(knots[i - 1])
//                print("; $i = ${knots[i]}, ")
            }
//            println()

            tailVisitedPositions.add(knots[9])
        }
//        for (i in 0..9)
//            print("$i = ${knots[i]}, ")
//        println()
    }
//    println(tailVisitedPositions)

    return tailVisitedPositions.size
}

private fun Pair<Int, Int>.moveToward(other: Pair<Int, Int>): Pair<Int, Int> {
    this.log("start ")
    other.log(", towards ")
    return if (this.isWithin1Of(other)) this
    else {
        (first.moveToward(other.first) to second.moveToward(other.second)).log(", moving to ")
    }
}

private fun Int.moveToward(first1: Int) = this + (first1 - this).sign


private fun Pair<Int, Int>.isWithin1Of(headPosition: Pair<Int, Int>): Boolean {
    return abs(first - headPosition.first).log(", x ") <= 1 && abs(second - headPosition.second).log(
        ", y "
    ) <= 1
}

private fun <T> T.log(s: String = ""): T = this//.also { print(s + it) }

fun main() {

    val testInput = readTestInput()
    val input = readInput()

    // part 1
//    ::part1.appliedTo(testInput, returns = 88)
    println("Part 1: ${part1(input)}")

    // part 2
//    ::part2.appliedTo(testInput, returns = 36)
    println("Part 2: ${part2(input)}")
}

