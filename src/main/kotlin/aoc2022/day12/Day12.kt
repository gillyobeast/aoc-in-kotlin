package aoc2022.day12

import aoc2022.Puzzle
import aoc2022.utils.*
import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.AStarShortestPath
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.builder.GraphBuilder

object Day12 : Puzzle(2022, 12) {
    const val alphabet = "SabcdefghijklmnopqrstuvwxyzE"

    private val Char.int: Int
        get() = alphabet.indexOf(this)


    /**
     * Strategy:
     *      - parse input
     *      - find a path (somehow)
     *      - tweak the path, check its length and keep if it's shorter
     *
     */
    override fun part1(input: List<String>): Int {
        val map: Matrix<Char> = input.map { it.toList() }
        val (start, end) = map.startAndEndPoints()
        val graph: Graph<Pair<Int, Int>, DefaultEdge> =
            DefaultDirectedGraph(DefaultEdge::class.java)
        val builder = GraphBuilder(graph)
        builder.addVertices(start, end)
        map.iterate(builder.addEdge(Day12::add, Day12::zeroToOnePlus))
        val path =
            AStarShortestPath(graph) { a, b -> (a.first - b.first) + (a.second - b.second).toDouble() }
                .getPath(start, end)
        println()
        println(path)
        map.prettyPrint(path.vertexList!!)

        return path.length
    }

    private fun GraphBuilder<Pair<Int, Int>, DefaultEdge, Graph<Pair<Int, Int>, DefaultEdge>>.addEdge(
        edgeAddFun: (GraphBuilder<Pair<Int, Int>, DefaultEdge, Graph<Pair<Int, Int>, DefaultEdge>>, Pair<Int, Int>, Pair<Int, Int>) -> Unit,
        neighbourhoodFactory: (Int) -> IntRange
    )
            : Matrix<Char>.(Int, Int, Char) -> Unit =
        { rowIndex, colIndex, it ->
            val value = it.int
            val here = rowIndex to colIndex
            addVertex(here)
            val (row, column) = this[rowIndex, colIndex]
            val neighbourhood = neighbourhoodFactory.invoke(value)
            if (rowIndex > 0 && column[rowIndex - 1].int in neighbourhood) {
                val source = rowIndex - 1 to colIndex
                edgeAddFun(this@addEdge, here, source)
            }
            if (rowIndex < column.lastIndex && column[rowIndex + 1].int in neighbourhood) {
                val source = rowIndex + 1 to colIndex
                edgeAddFun(this@addEdge, here, source)
            }
            if (colIndex > 0 && row[colIndex - 1].int in neighbourhood) {
                val source = rowIndex to colIndex - 1
                edgeAddFun(this@addEdge, here, source)
            }
            if (colIndex < row.lastIndex && row[colIndex + 1].int in neighbourhood) {
                val source = rowIndex to colIndex + 1
                edgeAddFun(this@addEdge, here, source)
            }
        }

    private fun zeroToOnePlus(value: Int): IntRange {
        return 0..value + 1
    }

    private fun oneMinusToMax(value: Int): IntRange {
        return value - 1..28
    }

    private fun add(
        graphBuilder: GraphBuilder<Pair<Int, Int>, DefaultEdge, Graph<Pair<Int, Int>, DefaultEdge>>,
        here: Pair<Int, Int>,
        source: Pair<Int, Int>
    ) {
        graphBuilder.addEdge(here, source)
    }

    private fun addReverse(
        graphBuilder: GraphBuilder<Pair<Int, Int>, DefaultEdge, Graph<Pair<Int, Int>, DefaultEdge>>,
        here: Pair<Int, Int>,
        source: Pair<Int, Int>
    ) {
        graphBuilder.addEdge(here, source)
    }

    private fun Matrix<Char>.startAndEndPoints(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        var start: Pair<Int, Int>? = null
        var end: Pair<Int, Int>? = null
        iterate { rowIndex, colIndex, it ->
            if (it == 'S') start = rowIndex to colIndex
            if (it == 'E') end = rowIndex to colIndex
        }
        return start!! to end!!
    }

    private fun `find all 'a's`(map: Matrix<Char>): List<Pair<Int, Int>> {
        val mutableList = mutableListOf<Pair<Int, Int>>()
        map.iterate { rowIndex, colIndex, char ->
            if (char == 'a') {
                mutableList.add(rowIndex to colIndex)
            }
        }
        return mutableList
    }

    override fun part2(input: List<String>): Any {
        val map: Matrix<Char> = input.map { it.toList() }
        val graph: Graph<Pair<Int, Int>, DefaultEdge> =
            DefaultDirectedGraph(DefaultEdge::class.java)
        val builder = GraphBuilder(graph)
        val (start, end) = map.startAndEndPoints()
        builder.addVertices(start, end)
        map.iterate(builder.addEdge(Day12::addReverse, Day12::oneMinusToMax))
        val pathFinder =
            AStarShortestPath(graph) { a, b -> (a.first - b.first) + (a.second - b.second).toDouble() }

        var shortestPath = pathFinder.getPath(end, start)
        for (a in `find all 'a's`(map)) {
            val newPath = pathFinder.getPath(end, a)
            if (newPath != null && newPath.length < shortestPath.length)
                shortestPath = newPath
        }
        println()
        println(shortestPath)
        map.prettyPrint(shortestPath.vertexList!!)

        return shortestPath.length
    }

}


fun main() {
    Day12.solve(31, 29)
}

