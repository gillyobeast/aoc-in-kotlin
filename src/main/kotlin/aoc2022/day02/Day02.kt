package aoc2022.day02

import aoc2022.utils.appliedTo
import aoc2022.utils.readInput
import aoc2022.utils.readTestInput

enum class Shape(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3), ;
}

// X == lose
// Y == draw
// Z == win!

fun String.toTheirShape(): Shape {
    return when (this) {
        "A" -> Shape.ROCK
        "B" -> Shape.PAPER
        else -> Shape.SCISSORS
    }
}

fun String.toYourShape(theirs: Shape): Shape {
    return when(this) {
        "X" -> when(theirs){
            Shape.ROCK -> Shape.SCISSORS
            Shape.PAPER -> Shape.ROCK
            Shape.SCISSORS -> Shape.PAPER
        }
        "Y" -> theirs
        else -> when(theirs){
            Shape.ROCK -> Shape.PAPER
            Shape.PAPER -> Shape.SCISSORS
            Shape.SCISSORS -> Shape.ROCK
        }
    }
}

fun String.toShape(): Shape {
    return if ("X" == this || "A" == this) Shape.ROCK else if ("Y" == this || "B" == this) Shape.PAPER else Shape.SCISSORS
}

private fun youWin(you: Shape, opponent: Shape) =
    ((you == Shape.ROCK && opponent == Shape.SCISSORS)
            || (you == Shape.PAPER && opponent == Shape.ROCK)
            || (you == Shape.SCISSORS && opponent == Shape.PAPER))

fun main() {


    fun outcomeScore(you: Shape, opponent: Shape): Int {
        if (you == opponent) return 3
        return if (youWin(you, opponent)) 6 else 0
    }

    fun toMap(input: List<String>): List<Pair<Shape, Shape>> =
        input.map { val parts = it.split(' '); parts[0].toShape() to parts[1].toShape() }

    fun toMapPart2(input: List<String>): List<Pair<Shape, Shape>> =
        input.map { val parts = it.split(' ')
            val theirShape = parts[0].toTheirShape()
            theirShape to parts[1].toYourShape(theirShape) }

    fun calculateScore(pairs: List<Pair<Shape, Shape>>): Int {
        var score = 0
        for ((opponent, you) in pairs) {
//            println("$opponent - $you")
            val shapeScore = you.score
            val outcomeScore = outcomeScore(you, opponent)
            val thisScore = shapeScore + outcomeScore
//            println("shapeScore = ${shapeScore}")
//            println("outcomeScore = ${outcomeScore}")
//            println("thisScore = ${thisScore}")
//            println()
            score += thisScore
        }

        return score
    }

    fun totalRpsScore(input: List<String>): Int {
        val pairs = toMap(input)
        return calculateScore(pairs)
    }

    fun part2(input: List<String>): Int {
        return calculateScore(toMapPart2(input))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput()
    val input = readInput()

    // part 1
    ::totalRpsScore.appliedTo(testInput, returns = 15)
    println("Part 1: ${totalRpsScore(input)}")

    // part 2
    ::part2.appliedTo(testInput, returns = 12)
    println("Part 2: ${part2(input)}")
}

