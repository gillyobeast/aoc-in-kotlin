package aoc2022.day14

import aoc2022.Puzzle
import aoc2022.utils.andLog
import kotlin.reflect.KProperty1

data class Point(val x: Int, val y: Int)

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
        var minY = 500
        val updateMinY = { it: Int -> minY = if (it < minY) it else minY }
        var (minX, maxX) = 0 to 0
        val updateXRange = { it: Int -> if (it < minX) minX = it; if (maxX < it) maxX = it }
        input.parsePoints().andLog()
            .forEach { points ->
                points.windowed(2) { (from, to) ->
                    for (point in pointsBetween(from, to)) {
                        rocks.add(point)
                        updateMinY(point.y)
                        updateXRange(point.x)
                    }
                }
            }

        println(rocks)

        TODO("Not yet implemented")
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

