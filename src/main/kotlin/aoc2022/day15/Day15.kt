package aoc2022.day15

import aoc2022.Puzzle
import aoc2022.utils.Point
import aoc2022.utils.andLog
import aoc2022.utils.by
import aoc2022.utils.draw
import kotlin.math.abs

data class Sensor(val position: Point, val beacon: Point) {

    constructor(ints: List<Int>) :
            this(ints[0] by ints[1], ints[2] by ints[3])

    val pointsCloserThanBeacon: Set<Point>
        get() {
            val points = mutableSetOf<Point>()

            for (y in position.y - taxicabDistance..position.y + taxicabDistance) { // rows
                val i = taxicabDistance + (y - position.y)
                for (x in position.x - i..position.x + i) {
                    points.add(Point(position.x + x, position.y + y))
                }
            }

            return points
        }

    private val taxicabDistance by lazy {
        abs(position.x - beacon.x + position.y - beacon.y)
    }

}

private val regex =
    "Sensor at x=([^,]*), y=([^,]*): closest beacon is at x=([^,]*), y=([^,]*)".toRegex()

fun String.toSensor() = Sensor(
    regex.findAll(this)
        .flatMap { it.groupValues.drop(1) } // drop 1 because groupValues[0] is the whole line
        .map { it.toInt() }
        .toList())


private fun parseSensors(input: List<String>) = input.map { it.toSensor() }.toSet()

object Day15 : Puzzle(2022, 15) {

    override fun part1(input: List<String>): Any {

        val points: Set<Point> = parseSensors(input)
            .andLog()
            .flatMapTo(mutableSetOf()) { it.pointsCloserThanBeacon }


        val targetY = if (input.size < 20) 10 else 2_000_000

        draw(points)

        return points.sortedBy { it.y }.count { it.y == targetY }

    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

}

fun main() {
    Day15.solve(26, -1)
}

