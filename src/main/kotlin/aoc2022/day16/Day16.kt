package aoc2022.day16

import aoc2022.Puzzle
import aoc2022.utils.andLog

object Day16 : Puzzle(2022, 0) {
    private data class Valve(val flowRate: Int, val leadsTo: Set<String>)

    private fun String.parse(): Pair<String, Valve> {
        val label = substringAfter("Valve ").substringBefore(" has")
        val flowRate = substringAfter("=").substringBefore(";").toInt()
        val leadsTo = substringAfter("valve").removePrefix("s")
            .removePrefix(" ").split(", ").toSet()
        return label to Valve(flowRate, leadsTo)
    }


    private fun List<String>.parse(): Map<String, Valve> =
        associate { it.parse() }

    override fun part1(input: List<String>): Any {
        input.parse().andLog{it.entries.joinToString("\n")}
        println()

        TODO()
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

}

fun main() = Day16.solve(1651, -1)


