package aoc2022.day16

import aoc2022.Puzzle
import aoc2022.utils.andLog
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.builder.GraphBuilder
import kotlin.coroutines.coroutineContext

object Day16 : Puzzle(2022, 0) {
    private data class Valve(val label: String, val flowRate: Long, val leadsTo: Set<String>)

    private fun String.parse(): Valve {
        val label = substringAfter("Valve ").substringBefore(" has")
        val flowRate = substringAfter("=").substringBefore(";").toLong()
        val leadsTo = substringAfter("valve").removePrefix("s").removePrefix(" ").split(", ").toSortedSet()
        return Valve(label, flowRate, leadsTo)
    }


    private fun List<String>.parse(): Set<Valve> = map { it.parse() }.toSortedSet(compareBy { it.flowRate })

    override fun part1(input: List<String>): Any {
        val valves = input.parse().andLog { it.joinToString("\n") }


        return maximiseFlow(getStart(valves), valves, emptySet(), 0, 30)

    }

    private fun maximiseFlow(
        current: Valve, closedValves: Set<Valve>, openValves: Set<Valve>, pressureReleased: Long, timeLeft: Int
    ): Long {
        if (timeLeft > 10 && pressureReleased > 100)
            println("current = [${current}], pressureReleased = [${pressureReleased}], timeLeft = [${timeLeft}]")

        if (timeLeft == 0) return pressureReleased
        if (closedValves.isEmpty()) return (openValves.sumOf { it.flowRate } * timeLeft) + pressureReleased
        val pressureToNow = openValves.sumOf { it.flowRate } + pressureReleased

        return buildSet {
            closedValves.parallelStream().forEach {
                add(maximiseFlow(it, closedValves, openValves, pressureToNow, timeLeft - 1))
            }
            add(maximiseFlow(current, closedValves - current, openValves + current, pressureToNow, timeLeft - 1))
        }.max()
    }

    private fun getStart(valves: Set<Valve>) = valves.first { it.label == "AA" }

    private fun buildGraph(valves: Map<String, Valve>): DefaultDirectedGraph<Valve, DefaultEdge> {
        val builder = GraphBuilder(DefaultDirectedGraph<Valve, DefaultEdge>(DefaultEdge::class.java))
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


