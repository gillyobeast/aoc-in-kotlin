package aoc2022.day14

import aoc2022.Puzzle
import aoc2022.utils.Point
import aoc2022.utils.andLog
import aoc2022.utils.toPoint
import kotlin.reflect.KProperty1

object Day14 : Puzzle(2022, 14) {
    override fun part1(input: List<String>): Any {
        val rocks = mutableSetOf<Point>()
        var minX= 500
        val yRange =
        input.parsePoints().andLog()
            .forEach { points ->
                for ((index, from) in points.dropLast(1).withIndex()) {
                    val to = points[index + 1]
                    for (point in pointsBetween(from, to))
                        rocks.add(point)
                }
            }

        println(rocks)

        TODO("Not yet implemented")
    }

    private fun pointsBetween(from: Pair<Int, Int>, to: Pair<Int, Int>): Set<Point> {
        val set = mutableSetOf<Point>()
        for (x in intRange(to, from, Point::first)) {
            for (y in intRange(to, from, Point::second)) {
                set.add(x to y)
            }
        }
        return set.toSet()
    }

    private fun intRange(
        to: Pair<Int, Int>,
        from: Pair<Int, Int>,
        property: KProperty1<Point, Int>,
    ): IntRange {

        return if (property(to) > property(from))
            property(from)..property(to)
        else
            property(to)..property(from)
    }


    operator fun Point.minus(from: Point): Point {
        return this.first - from.first to this.second - from.second
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

