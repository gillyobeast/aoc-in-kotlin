package aoc2022.day02

import Puzzle

object Day02 : Puzzle(2022, 2) {
    enum class Shape(val score: Int) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3), ;
    }

    // X == lose
    // Y == draw
    // Z == win!

    private fun String.toTheirShape(): Shape {
        return when (this) {
            "A" -> Shape.ROCK
            "B" -> Shape.PAPER
            else -> Shape.SCISSORS
        }
    }

    private fun String.toYourShape(theirs: Shape): Shape {
        return when (this) {
            "X" -> when (theirs) {
                Shape.ROCK -> Shape.SCISSORS
                Shape.PAPER -> Shape.ROCK
                Shape.SCISSORS -> Shape.PAPER
            }

            "Y" -> theirs
            else -> when (theirs) {
                Shape.ROCK -> Shape.PAPER
                Shape.PAPER -> Shape.SCISSORS
                Shape.SCISSORS -> Shape.ROCK
            }
        }
    }

    private fun String.toShape(): Shape {
        return if ("X" == this || "A" == this) Shape.ROCK else if ("Y" == this || "B" == this) Shape.PAPER else Shape.SCISSORS
    }

    private fun youWin(you: Shape, opponent: Shape) =
        ((you == Shape.ROCK && opponent == Shape.SCISSORS)
                || (you == Shape.PAPER && opponent == Shape.ROCK)
                || (you == Shape.SCISSORS && opponent == Shape.PAPER))


    private fun outcomeScore(you: Shape, opponent: Shape): Int {
        if (you == opponent) return 3
        return if (youWin(you, opponent)) 6 else 0
    }

    private fun toMap(input: List<String>): List<Pair<Shape, Shape>> =
        input.map { val parts = it.split(' '); parts[0].toShape() to parts[1].toShape() }

    private fun toMapPart2(input: List<String>): List<Pair<Shape, Shape>> =
        input.map {
            val parts = it.split(' ')
            val theirShape = parts[0].toTheirShape()
            theirShape to parts[1].toYourShape(theirShape)
        }

    private fun calculateScore(pairs: List<Pair<Shape, Shape>>): Int {
        var score = 0
        for ((opponent, you) in pairs) {
            val shapeScore = you.score
            val outcomeScore = outcomeScore(you, opponent)
            val thisScore = shapeScore + outcomeScore
            score += thisScore
        }

        return score
    }

    override fun part1(input: List<String>): Any {
        val pairs = toMap(input)
        return calculateScore(pairs)
    }

    override fun part2(input: List<String>): Int {
        return calculateScore(toMapPart2(input))
    }
}

fun main() = Day02.solve(15,12)

