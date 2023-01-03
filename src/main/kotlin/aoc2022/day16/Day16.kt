package aoc2022.day16

import aoc2022.Puzzle
import aoc2022.utils.andLog
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.builder.GraphBuilder

object Day16 : Puzzle(2022, 0) {
    private data class Valve(val label: String, val flowRate: Int, val leadsTo: Set<String>) {
        var isOpen = false
    }

    private fun String.parse(): Valve {
        val label = substringAfter("Valve ").substringBefore(" has")
        val flowRate = substringAfter("=").substringBefore(";").toInt()
        val leadsTo = substringAfter("valve").removePrefix("s")
            .removePrefix(" ").split(", ").toSet()
        return Valve(label, flowRate, leadsTo)
    }


    private fun List<String>.parse(): Map<String, Valve> =
        map { it.parse() }.associateBy { it.label }

    override fun part1(input: List<String>): Any {
        val valves = input.parse()//.andLog { it.entries.joinToString("\n") }

        val start = valves["AA"] ?: error("No AA found.")

        val graph = buildGraph(valves)
        graph.edgeSet().andLog { it.joinToString("\n") }
        println()



        TODO()
    }

    private fun buildGraph(valves: Map<String, Valve>): DefaultDirectedGraph<Valve, DefaultEdge> {
        val builder =
            GraphBuilder(DefaultDirectedGraph<Valve, DefaultEdge>(DefaultEdge::class.java))
        for (valve in valves.values) {
            for (other in valve.leadsTo) {
                builder.addEdge(valve, valves[other])
            }
        }
        return builder.build()
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

}

fun main() = Day16.solve(1651, -1)


