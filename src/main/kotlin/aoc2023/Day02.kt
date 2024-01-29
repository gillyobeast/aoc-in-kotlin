package aoc2023

import Puzzle

object Day02 : Puzzle(2023, 1) {

//    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
//    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
//    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
//    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
//    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green

    val totalCubes = mapOf("red" to 12, "green" to 13, "blue" to 14)

    data class Game(val hands: List<Hand>) {
        data class Hand(val cubes: Map<String, Int>)

        val isPossible: Boolean =
            !hands.any { hand ->
                hand.cubes.entries.any {
                    it.value > totalCubes[it.key]!!
                }
            }

    }

    override fun part1(input: List<String>): Any {
        val games = input.associate {
            it.extractGameId() to it.substringAfter(": ")
        }.mapValues { it.toGame() }.onEach { println(it) }

        return games.mapValues { it.value.isPossible }.filter { it.value }.keys.sum()

    }

    private fun Map.Entry<Int, String>.toGame(): Game {
        return Game(this.value.split("; ?".toRegex()).map { it.toHand() })
    }

    private fun String.toHand(): Game.Hand {
        //            6 red, 1 blue, 3 green
        val map = this.split(", ")
            .map { it.split(" ") }.associate { it[1] to it[0].toInt() }
        return Game.Hand(map)
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

private val gameIdRegex = "(\\d+)".toRegex()

private fun String.extractGameId(): Int {
    return gameIdRegex.find(this)?.groupValues?.get(0)?.toInt() ?: throw IllegalArgumentException()
}

fun main() {
    Day02.solve(8, -1)
}
