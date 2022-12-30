package aoc2022.day14

import aoc2022.Puzzle
import aoc2022.utils.Matrix
import kotlin.reflect.KProperty1

const val ROCK = '#'
const val AIR = '.'
const val SAND = 'o'

fun <E> Matrix<E>.prettyPrint(path: List<Point>) {
    println(mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, value ->
            (if (path.contains(Point(rowIndex, colIndex))) ">" else " ") + value.toString()
        }.joinToString("")
    }.joinToString("\n"))
}

data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
        return "($x,$y)"
    }
}

fun List<Int>.toPoint(): Point {
    check(this.size == 2) { "Can only make a pair out of two values" }
    return Point(this[0], this[1])
}

fun Pair<Int, Int>.toPoint(): Point {
    return Point(first, second)
}

private val rockOrSand = setOf(ROCK, SAND)

object Day14 : Puzzle(2022, 14) {
    override fun part1(input: List<String>): Any {
        val (xTmp, yTmp) = extracted(input)
        val (minX, maxX) = xTmp
        val (maxY, rocks) = yTmp

        val cave: MutableList<MutableList<Char>> = mutableListOf()
        // rows then columns
        repeat(maxY) { rowIdx ->
            val row = mutableListOf<Char>()
            repeat(maxX - minX) { colIdx ->
                if (Point(colIdx + 1 + minX, rowIdx + 1) in rocks) {
                    row.add(ROCK)
                } else row.add(AIR)
            }
            cave.add(row)
        }
//        cave.prettyPrint()
        try {
            var sands = 0
            while (sands++ < 10_000) {
                // in this loop, one unit of sand is moved through the cave.
                var sand = Point(500, 0)
                val path = mutableListOf(sand)
                var moves = 0
                while (moves++ < maxY) {
                    // in this loop, the unit of sand moves down one unit if it can,
                    //  moving diagonally as necessary
                    var xCandidate = sand.x
                    val yCandidate = sand.y + 1
                    // try below
                    val row = cave[yCandidate]
                    if (row[xCandidate - minX] !in rockOrSand) {
                        // if not blocked
                        sand = Point(xCandidate, yCandidate)
                        path.add(sand)
                        continue
                    }
                    // try diagonally left
                    xCandidate = sand.x - 1
                    if (row[xCandidate - minX] !in rockOrSand) {
                        sand = Point(xCandidate, yCandidate)
                        path.add(sand)
                        continue
                    }
                    // try diagonally right
                    xCandidate = sand.x + 1
                    if (row[xCandidate - minX] !in rockOrSand) {
                        sand = Point(xCandidate, yCandidate)
                        path.add(sand)
                        continue
                    }

                    cave[yCandidate - 1][sand.x - minX] = SAND
                    break
                }
                println("-".repeat((maxX - minX) * 2 - 1))
                cave.prettyPrint(path)
                if (sand.y > maxY)
                    break
            }
        } catch (e: IndexOutOfBoundsException) {
            // consume - means we
            println(e)
        }

//        println("-".repeat((maxX - minX) * 2 - 1))
//        cave.prettyPrint()
        return cave.sumOf { row -> row.count { it == SAND } }
    }

    private fun extracted(input: List<String>): Pair<Pair<Int, Int>, Pair<Int, MutableSet<Point>>> {
        val rocks = mutableSetOf<Point>()
        var maxY = 0
        val updateMinY = { it: Int -> if (maxY < it) maxY = it }
        var (minX, maxX) = 500 to 500
        val updateXRange = { it: Int -> if (it < minX) minX = it; if (maxX < it) maxX = it }
        input.parsePoints()/*.andLog()*/
            .forEach { points ->
                points.windowed(2) { (from, to) ->
                    for (point in pointsBetween(from, to)) {
                        rocks.add(point)
                        updateMinY(point.y)
                        updateXRange(point.x)
                    }
                }
            }

//        println("minX = $minX")
//        println("maxX = $maxX")
//        println("maxY = $maxY")
        return (minX to maxX) to (maxY to rocks)
    }

    private fun pointsBetween(from: Point, to: Point): Set<Point> {
        val set = mutableSetOf<Point>()
        for (x in intRange(to, from, Point::x)) {
            for (y in intRange(to, from, Point::y)) {
                set.add((x to y).toPoint())
            }
        }
        return set.toSet()
    }


    private fun intRange(
        to: Point,
        from: Point,
        property: KProperty1<Point, Int>,
    ): IntRange {

        return if (property(to) > property(from))
            property(from)..property(to)
        else
            property(to)..property(from)
    }

    operator fun Point.minus(from: Point): Point {
        return (this.x - from.x to this.y - from.y).toPoint()
    }

    private fun List<String>.parsePoints(): List<List<Point>> = map { line ->
        line.split(" -> ")
            .map { pair ->
                pair.split(",")
                    .map(String::toInt)
                    .toPoint()
            }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() {
    Day14.solve(24, -1)
}

