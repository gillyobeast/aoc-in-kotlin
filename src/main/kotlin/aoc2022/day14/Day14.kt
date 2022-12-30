package aoc2022.day14

import aoc2022.Puzzle
import aoc2022.utils.prettyPrint
import kotlin.reflect.KProperty1

const val ROCK = '#'
const val AIR = '.'
const val SAND = 'o'

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

object Day14 : Puzzle(2022, 14) {
    override fun part1(input: List<String>): Any {
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

        println("minX = ${minX}")
        println("maxX = ${maxX}")
        println("maxY = ${maxY}")

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
        cave.prettyPrint()


        untilFalse {
            // in this loop, one unit of sand is moved through the cave.


            true
        }



        return 0
    }

    private fun untilFalse(limit: Int = 10_000, block: (Int) -> Boolean) {
        var count = 0
        @Suppress("ControlFlowWithEmptyBody")
        while (block(count) && count++ > limit) {
        }
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

