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
        val graph: Graph<Point2D<Int>, DefaultEdge> =
            DefaultDirectedGraph(DefaultEdge::class.java)
        val builder = GraphBuilder(graph)
        builder.addVertices(start, end)
        map.iterate(builder.addEdge())
        val path =
            AStarShortestPath(graph) { a, b -> (a.first - b.first) + (a.second - b.second).toDouble() }
                .getPath(start, end)
        println()
        println(path)
        map.prettyPrint(path.vertexList!!)

        return path.length
    }

    private fun GraphBuilder<Point2D<Int>, DefaultEdge, Graph<Point2D<Int>, DefaultEdge>>.addEdge()
            : Matrix<Char>.(Int, Int, Char) -> Unit =
        { rowIndex, colIndex, it ->
            val value = it.int
            val here = rowIndex to colIndex
            addVertex(here)
            val (row, column) = this[rowIndex, colIndex]
            val neighbourhood = 0..value + 1
            if (rowIndex > 0 && column[rowIndex - 1].int in neighbourhood) {
                val source = rowIndex - 1 to colIndex
                add(here, source)
            }
            if (rowIndex < column.lastIndex && column[rowIndex + 1].int in neighbourhood) {
                val source = rowIndex + 1 to colIndex
                add(here, source)
            }
            if (colIndex > 0 && row[colIndex - 1].int in neighbourhood) {
                val source = rowIndex to colIndex - 1
                add(here, source)
            }
            if (colIndex < row.lastIndex && row[colIndex + 1].int in neighbourhood) {
                val source = rowIndex to colIndex + 1
                add(here, source)
            }
        }

    private fun GraphBuilder<Point2D<Int>, DefaultEdge, Graph<Point2D<Int>, DefaultEdge>>.add(
        here: Pair<Int, Int>,
        source: Pair<Int, Int>
    ) {
        addEdge(here, source)
//        println("adding edge from $here to $source")
    }

    private fun Matrix<Char>.startAndEndPoints(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        var start: Point2D<Int>? = null
        var end: Point2D<Int>? = null
        iterate { rowIndex, colIndex, it ->
            if (it == 'S') start = rowIndex to colIndex
            if (it == 'E') end = rowIndex to colIndex
        }
        return start!! to end!!
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

}


fun main() {
    Day12.solve(31, -1)
}

